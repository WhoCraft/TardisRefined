package whocraft.tardis_refined.common.network.messages.screens;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

public class MonitorPositionDataMessage extends MessageS2C {

    public static TardisNavLocation lastLocation = TardisNavLocation.ORIGIN;

    TardisNavLocation location;

    public MonitorPositionDataMessage(TardisNavLocation location) {
        this.location = location;
    }

    public MonitorPositionDataMessage(FriendlyByteBuf friendlyByteBuf) {
        CompoundTag tardisNav = friendlyByteBuf.readNbt();
        this.location = TardisNavLocation.deserialize(tardisNav);
    }

    @Override
    public @NotNull MessageType getType() {
        return TardisNetwork.MONITOR_POSITION_DATA;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(location.serialise());
    }

    @Override
    public void handle(MessageContext context) {
        lastLocation = location;
    }
}
