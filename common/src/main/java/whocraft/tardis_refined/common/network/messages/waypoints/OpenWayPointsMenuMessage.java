package whocraft.tardis_refined.common.network.messages.waypoints;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.screen.waypoints.WaypointListScreen;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

import java.util.ArrayList;
import java.util.Collection;

public class OpenWayPointsMenuMessage extends MessageS2C {

    private Collection<TardisNavLocation> waypoints;

    public OpenWayPointsMenuMessage(Collection<TardisNavLocation> waypoints){
        this.waypoints = waypoints;
    }

    public OpenWayPointsMenuMessage(FriendlyByteBuf friendlyByteBuf){
        waypoints = new ArrayList<>();
        int size = friendlyByteBuf.readInt();
        for (int i = 0; i < size; i++) {
            CompoundTag tardisNav = friendlyByteBuf.readNbt();
            TardisNavLocation tardisNavLocation = TardisNavLocation.deserialise(tardisNav);
            waypoints.add(tardisNavLocation);
        }
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.OPEN_WAYPOINTS_SCREEN;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(waypoints.size());
        for (TardisNavLocation waypoint : waypoints) {
            buf.writeNbt(waypoint.serialise());
        }
    }

    @Override
    public void handle(MessageContext context) {
        Minecraft.getInstance().setScreen(new WaypointListScreen(waypoints));
    }
}
