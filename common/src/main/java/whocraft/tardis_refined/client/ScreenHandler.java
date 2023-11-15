package whocraft.tardis_refined.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.client.screen.waypoints.CoordInputType;
import whocraft.tardis_refined.client.screen.waypoints.WaypointListScreen;
import whocraft.tardis_refined.client.screen.waypoints.WaypointManageScreen;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

import java.util.Collection;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ScreenHandler {


    @Environment(EnvType.CLIENT)
    public static void setWaypointScreen(Collection<TardisNavLocation> waypoints) {
        Minecraft.getInstance().setScreen(new WaypointListScreen(waypoints));
    }

    @Environment(EnvType.CLIENT)
    public static void setCoordinatesScreen(List<ResourceKey<Level>> levels, CoordInputType coordInputType, TardisNavLocation tardisNavLocation) {
        Minecraft.getInstance().setScreen(new WaypointManageScreen(levels, coordInputType, tardisNavLocation));
    }
}
