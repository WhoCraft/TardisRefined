package whocraft.tardis_refined.common.network.messages.player;

import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.manager.SettingsHandler;
import whocraft.tardis_refined.registry.TRDimensionTypes;

public class C2SSetSystemSetting<T> extends MessageC2S {

    private final SettingsHandler.SettingKey<T> key;
    private final T value;

    public C2SSetSystemSetting(SettingsHandler.SettingKey<T> key, T value) {
        this.key = key;
        this.value = value;
    }

    public C2SSetSystemSetting(FriendlyByteBuf byteBuf) {
        //noinspection unchecked
        this.key = (SettingsHandler.SettingKey<T>) SettingsHandler.SETTINGS_REGISTRY.get().byId(byteBuf.readVarInt());
        if (this.key != null) {
            this.value = this.key.readFromPacket(byteBuf);
        } else {
            this.value = null;
        }
    }

    @Override
    public @NotNull MessageType getType() {
        return TardisNetwork.SET_SYSTEM_SETTING;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarInt(SettingsHandler.SETTINGS_REGISTRY.get().getId(key));
        key.writeToPacket(buf, value);
    }

    @Override
    public void handle(MessageContext context) {
        if (context.getPlayer().serverLevel().dimensionTypeId() != TRDimensionTypes.TARDIS) return;
        if (value == null) return;
        TardisLevelOperator.get(context.getPlayer().serverLevel()).ifPresent(tardis -> {
            tardis.getSettingsManager().setSetting(key, value);
            tardis.tardisClientData().sync();
        });
    }
}
