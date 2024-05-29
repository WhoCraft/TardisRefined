package whocraft.tardis_refined.common.network.messages.waypoints;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.screen.waypoints.CoordInputType;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.TardisWaypoint;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.manager.TardisWaypointManager;
import whocraft.tardis_refined.common.util.DimensionUtil;

import java.util.List;
import java.util.UUID;

public class C2SOpenEditCoordinatesDisplayMessage extends MessageC2S {

    private UUID waypointId;

    public C2SOpenEditCoordinatesDisplayMessage(UUID waypointId) {
        this.waypointId = waypointId;
    }

    public C2SOpenEditCoordinatesDisplayMessage(FriendlyByteBuf friendlyByteBuf) {
        this.waypointId = friendlyByteBuf.readUUID();
    }


    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.CLIENT_OPEN_EDIT_COORDS_SCREEN;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(waypointId);
    }

    @Override
    public void handle(MessageContext context) {
        ServerPlayer serverPlayer = context.getPlayer();
        ;
        ServerLevel level = serverPlayer.serverLevel();
        TardisLevelOperator.get(level).ifPresent(tardisLevelOperator -> {
            TardisWaypointManager waypointManager = tardisLevelOperator.getTardisWaypointManager();

            TardisWaypoint waypoint = waypointManager.getWaypointById(waypointId);
            if (waypoint != null) {
                new S2COpenEditCoordinatesDisplayMessage(waypoint).send(serverPlayer);
            }
        });
    }
}
