package whocraft.tardis_refined.common.network.messages.waypoints;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.ScreenHandler;
import whocraft.tardis_refined.client.screen.waypoints.CoordInputType;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.TardisWaypoint;

import java.util.ArrayList;
import java.util.List;

public class S2COpenEditCoordinatesDisplayMessage extends MessageS2C {

    TardisWaypoint waypoint;

    public S2COpenEditCoordinatesDisplayMessage(TardisWaypoint waypoint) {
        this.waypoint = waypoint;

    }

    public S2COpenEditCoordinatesDisplayMessage(FriendlyByteBuf friendlyByteBuf) {
        CompoundTag tardisNav = friendlyByteBuf.readNbt();
        waypoint = TardisWaypoint.deserialise(tardisNav);
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.SERVER_OPEN_EDIT_COORDS_SCREEN;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(waypoint.serialise());

    }

    @Override
    public void handle(MessageContext context) {
        handleDisplay();
    }

    @Environment(EnvType.CLIENT)
    private void handleDisplay() {
        ScreenHandler.openEditCoordinatesScreen(waypoint);
    }

}
