package whocraft.tardis_refined.common.network.messages.waypoints;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisWaypointManager;

public class UploadWaypointMessage extends MessageC2S {

    TardisNavLocation tardisNavLocation;

    public UploadWaypointMessage(TardisNavLocation tardisNavLocation) {
        this.tardisNavLocation = tardisNavLocation;
    }


    public UploadWaypointMessage(FriendlyByteBuf buf) {
        CompoundTag tardisNav = buf.readNbt();
        this.tardisNavLocation = TardisNavLocation.deserialise(tardisNav);
    }


    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.UPLOAD_WAYPOINT;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(tardisNavLocation.serialise());
    }

    @Override
    public void handle(MessageContext context) {
        ServerPlayer player = context.getPlayer();
        ServerLevel serverLevel = player.serverLevel();

        TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
            TardisWaypointManager tardisWaypointManager = tardisLevelOperator.getTardisWaypointManager();
            tardisWaypointManager.addWaypoint(tardisNavLocation, tardisNavLocation.getName());
        });
    }
}
