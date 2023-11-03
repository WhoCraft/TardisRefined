package whocraft.tardis_refined.common.network.messages.waypoints;

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

public class TravelToWaypointMessage extends MessageC2S {

    String tardisNavName;

    public TravelToWaypointMessage(String tardisNavName) {
        this.tardisNavName = tardisNavName;
    }


    public TravelToWaypointMessage(FriendlyByteBuf buf) {
        tardisNavName = buf.readUtf();
    }


    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.SET_WAYPOINT;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(tardisNavName);
    }

    @Override
    public void handle(MessageContext context) {
        ServerPlayer player = context.getPlayer();
        ServerLevel serverLevel = player.getLevel();

        TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
            TardisWaypointManager tardisWaypointManager = tardisLevelOperator.getTardisWaypointManager();
            TardisNavLocation waypoint = tardisWaypointManager.getWaypointByName(tardisNavName);
            tardisLevelOperator.getControlManager().setTargetLocation(waypoint);
        });
    }
}
