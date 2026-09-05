package whocraft.tardis_refined.common.tardis.manager;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.tardis.upgrades.Upgrade;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRUpgrades;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class SettingsHandler extends BaseHandler {

    public static final ResourceKey<Registry<SettingKey<?>>> SETTINGS_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "tardis_settings"));

    public static final DeferredRegistry<SettingKey<?>> SETTINGS_DEFERRED_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, SETTINGS_REGISTRY_KEY, true);
    public static final Supplier<Registry<SettingKey<?>>> SETTINGS_REGISTRY = SETTINGS_DEFERRED_REGISTRY::getRegistry;
    public static final String SETTINGS = "settings";

    public static final RegistrySupplier<SettingKey<Boolean>> MATERIALIZE_AROUND = register(
            "materialize_around",
            SimpleSettingKey.forUpgrade(
                    FriendlyByteBuf::writeBoolean,
                    FriendlyByteBuf::readBoolean,
                    Codec.BOOL, TRUpgrades.MATERIALIZE_AROUND, false
            )
    );

    private static <T extends SettingKey<?>> RegistrySupplier<T> register(String id, T entry) {
        return SETTINGS_DEFERRED_REGISTRY.register(id, () -> entry);
    }

    private final Map<SettingKey<?>, Object> settings = new HashMap<>();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<TardisLevelOperator> tardis;

    public SettingsHandler(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<TardisLevelOperator> tardis) {
        this.tardis = tardis;
    }

    private <T> Tag encodeSetting(SettingKey<T> setting) {
        return setting.codec().encodeStart(NbtOps.INSTANCE, getSetting(setting).orElseThrow()).getOrThrow(false, err -> {});
    }

    @Override
    public CompoundTag saveData(CompoundTag tag) {
        CompoundTag settingsTag = new CompoundTag();
        for (var setting : settings.keySet()) {
            settingsTag.put(
                    SettingsHandler.SETTINGS_REGISTRY.get().getKey(setting).toString(),
                    encodeSetting(setting)
            );
        }
        tag.put(SETTINGS, settingsTag);
        return tag;
    }

    @Override
    public void loadData(CompoundTag tag) {
        var settingsTag = tag.getCompound(SETTINGS);
        SettingsHandler.SETTINGS_REGISTRY.get().entrySet().forEach(setting -> {
            var stringKey = setting.getKey().location().toString();
            if (settingsTag.contains(stringKey)) {
                setSettingFromTag(setting.getValue(), settingsTag.get(stringKey));
            }
        });
    }

    public <T> Optional<T> getSetting(SettingKey<T> setting) {
	    //noinspection unchecked
	    return Optional.ofNullable((T) settings.get(setting)).map(
                v -> setting.validate(v, tardis)
        );
    }

    private <T> void setSettingFromTag(SettingKey<T> setting, Tag tag) {
        setting.codec().parse(NbtOps.INSTANCE, tag).resultOrPartial(
                TardisRefined.LOGGER::error
        ).ifPresent(value -> setSetting(setting, value));
    }

    public <T> void setSetting(SettingKey<T> setting, T value) {
        settings.put(setting, setting.validate(value, tardis));
    }

    public interface SettingKey<T> {
        void writeToPacket(FriendlyByteBuf buf, T value);
        T readFromPacket(FriendlyByteBuf buf);
        Codec<T> codec();
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        T validate(T value, Optional<TardisLevelOperator> tardis);
    }

    public record SimpleSettingKey<T>(
            BiConsumer<FriendlyByteBuf, T> packetWriter,
            Function<FriendlyByteBuf, T> packetReader,
            Codec<T> codec,
            BiFunction<T, Optional<TardisLevelOperator>, T> valueValidator
    ) implements SettingKey<T> {

        public static <T> SettingKey<T> forUpgrade(
                BiConsumer<FriendlyByteBuf, T> packetWriter,
                Function<FriendlyByteBuf, T> packetReader, Codec<T> codec,
                Supplier<Upgrade> upgrade, T valueIfUpgradeMissing
        ) {
            return new SimpleSettingKey<>(
                    packetWriter, packetReader, codec,
                    (value, tardis) -> tardis.map(
                            t -> t.getUpgradeHandler().isUpgradeUnlocked(upgrade.get())
                    ).map(isUnlocked -> isUnlocked ? value : valueIfUpgradeMissing).orElse(value)
            );
        }

        @Override
        public void writeToPacket(FriendlyByteBuf buf, T value) {
            packetWriter.accept(buf, value);
        }

        @Override
        public T readFromPacket(FriendlyByteBuf buf) {
            return packetReader.apply(buf);
        }

        @Override
        public T validate(T value, Optional<TardisLevelOperator> tardis) {
            return valueValidator.apply(value, tardis);
        }
    }
}
