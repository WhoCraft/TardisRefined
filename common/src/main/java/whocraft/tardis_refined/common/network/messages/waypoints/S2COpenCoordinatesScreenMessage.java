package whocraft.tardis_refined.common.network.messages.waypoints;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.screen.waypoints.CoordInputType;
import whocraft.tardis_refined.client.screen.waypoints.WaypointManageScreen;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

import java.util.ArrayList;
import java.util.List;

public class S2COpenCoordinatesScreenMessage extends MessageS2C {

    private TardisNavLocation tardisNavLocation;
    private List<ResourceKey<Level>> levels;
    CoordInputType coordInputType;

    public S2COpenCoordinatesScreenMessage(List<ResourceKey<Level>> waypoints, CoordInputType coordInputType, TardisNavLocation tardisNavLocation) {
        this.levels = waypoints;
        this.coordInputType = coordInputType;
        this.tardisNavLocation = tardisNavLocation;
    }

    public S2COpenCoordinatesScreenMessage(FriendlyByteBuf friendlyByteBuf) {
        CompoundTag tardisNav = friendlyByteBuf.readNbt();
        tardisNavLocation = TardisNavLocation.deserialise(tardisNav);
        coordInputType = CoordInputType.valueOf(friendlyByteBuf.readUtf());
        levels = new ArrayList<>();
        int size = friendlyByteBuf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceKey<Level> levelResourceKey = friendlyByteBuf.readResourceKey(Registries.DIMENSION);
            levels.add(levelResourceKey);
        }
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.SERVER_OPEN_COORDS_SCREEN;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(tardisNavLocation.serialise());
        buf.writeUtf(coordInputType.name());
        buf.writeInt(levels.size());
        for (ResourceKey<Level> levelResourceKey : levels) {
            buf.writeResourceKey(levelResourceKey);
        }
    }

    @Override
    public void handle(MessageContext context) {
        Minecraft.getInstance().setScreen(new WaypointManageScreen(levels, coordInputType, tardisNavLocation));
    }
}
