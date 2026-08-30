package whocraft.tardis_refined.common.capability.player.neoforge;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

import java.util.Optional;
import java.util.function.Supplier;

import static net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion.MOD_ID;

public class TardisPlayerInfoImpl {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MOD_ID);

    private static final Supplier<AttachmentType<Optional<TardisPlayerInfo>>> TARDIS_PLAYER_INFO = ATTACHMENT_TYPES.register(
            "tardis_player_info", () -> AttachmentType.<Optional<TardisPlayerInfo>>builder(
                    player -> player instanceof Player p ? Optional.of(new TardisPlayerInfo(p)) : Optional.empty()
            ).serialize(new TardisPlayerInfoSerializer()).build());


    public static Optional<TardisPlayerInfo> get(LivingEntity player) {
        return player.getData(TARDIS_PLAYER_INFO);
    }

    public static void init(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }

    public static class TardisPlayerInfoSerializer implements IAttachmentSerializer<CompoundTag, Optional<TardisPlayerInfo>> {

        @Override
        public @NotNull Optional<TardisPlayerInfo> read(
                @NotNull IAttachmentHolder iAttachmentHolder, @NotNull CompoundTag arg, HolderLookup.@NotNull Provider arg2
        ) {
            if (iAttachmentHolder instanceof Player p) {
                var data = new TardisPlayerInfo(p);
                data.loadData(arg);
                return Optional.of(data);
            }
            return Optional.empty();
        }

        @Override
        public @Nullable CompoundTag write(@NotNull Optional<TardisPlayerInfo> object, HolderLookup.@NotNull Provider arg) {
            return object.map(TardisPlayerInfo::saveData).orElse(null);
        }
    }

}