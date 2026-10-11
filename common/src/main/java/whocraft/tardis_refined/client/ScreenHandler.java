package whocraft.tardis_refined.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.client.screen.ponder.PonderListScreen;
import whocraft.tardis_refined.client.screen.screens.CancelDesktopScreen;
import whocraft.tardis_refined.client.screen.main.MonitorOS;
import whocraft.tardis_refined.client.screen.main.MonitorScreen;
import whocraft.tardis_refined.client.screen.screens.ShellSelectionScreen;
import whocraft.tardis_refined.client.screen.upgrades.UpgradesScreen;
import whocraft.tardis_refined.client.screen.waypoints.CoordInputType;
import whocraft.tardis_refined.client.screen.screens.WaypointListScreen;
import whocraft.tardis_refined.client.screen.screens.WaypointManageScreen;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.tardis.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.TardisWaypoint;

import java.util.Collection;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ScreenHandler {


    @Environment(EnvType.CLIENT)
    public static void setWaypointScreen(Collection<TardisWaypoint> waypoints) {
        if (Minecraft.getInstance().gui.screen() instanceof MonitorOS monitorOS) {
            monitorOS.switchScreenToRight(new WaypointListScreen(waypoints));
            return;
        }
        Minecraft.getInstance().setScreenAndShow(new WaypointListScreen(waypoints));
    }

    @Environment(EnvType.CLIENT)
    public static void setCoordinatesScreen(List<ResourceKey<Level>> levels, CoordInputType coordInputType, TardisNavLocation tardisNavLocation) {
        if (Minecraft.getInstance().gui.screen() instanceof MonitorOS monitorOS) {
            monitorOS.switchScreenToRight(new WaypointManageScreen(levels, coordInputType, tardisNavLocation));
            return;
        }
        Minecraft.getInstance().setScreenAndShow(new WaypointManageScreen(levels, coordInputType, tardisNavLocation));
    }

    @Environment(EnvType.CLIENT)
    public static void setEditCoordinatesScreen(TardisWaypoint waypoint) {
        Minecraft.getInstance().setScreenAndShow(new WaypointManageScreen(waypoint));
    }


    @Environment(EnvType.CLIENT)
    public static void openMonitorScreen(boolean desktopGenerating, CompoundTag upgradeHandlerNbt, TardisNavLocation currentLocation, TardisNavLocation targetLocation, Identifier currentShellTheme, Identifier currentShellPattern) {
        if (desktopGenerating) {
            Minecraft.getInstance().setScreenAndShow(new CancelDesktopScreen());
        } else {
            assert Minecraft.getInstance().level != null;
            UpgradeHandler upgradeHandlerClient = new UpgradeHandler(new TardisLevelOperator(Minecraft.getInstance().level));
            upgradeHandlerClient.loadData(upgradeHandlerNbt);
            Minecraft.getInstance().setScreenAndShow(new MonitorScreen(currentLocation, targetLocation, upgradeHandlerClient, currentShellTheme, currentShellPattern));
        }
    }

    @Environment(EnvType.CLIENT)
    public static void openCoordinatesScreen(List<ResourceKey<Level>> levels, CoordInputType coordInputType, TardisNavLocation tardisNavLocation) {
        ScreenHandler.setCoordinatesScreen(levels, coordInputType, tardisNavLocation);
    }

    @Environment(EnvType.CLIENT)
    public static void openEditCoordinatesScreen(TardisWaypoint waypoint) {
        ScreenHandler.setEditCoordinatesScreen(waypoint);
    }

    @Environment(EnvType.CLIENT)
    public static void displayUpgradesScreen(CompoundTag upgradeTag) {
        assert Minecraft.getInstance().level != null;
        UpgradeHandler upgradeHandlerClient = new UpgradeHandler(new TardisLevelOperator(Minecraft.getInstance().level));
        upgradeHandlerClient.loadData(upgradeTag);

        if (Minecraft.getInstance().gui.screen() instanceof UpgradesScreen screen && screen.selectedTab != null) {
            screen.selectedTab.populate(upgradeHandlerClient);
        } else {
            Minecraft.getInstance().setScreenAndShow(new UpgradesScreen(upgradeHandlerClient));
        }
    }

    @Environment(EnvType.CLIENT)
    public static void openShellSelection(Identifier currentShell) {
        if (Minecraft.getInstance().gui.screen() instanceof MonitorOS monitorOS) {
            monitorOS.switchScreenToRight(new ShellSelectionScreen(currentShell));
            return;
        }
        Minecraft.getInstance().setScreenAndShow(new ShellSelectionScreen(currentShell));
    }

    @Environment(EnvType.CLIENT)
    public static void openCraftingScreen() {
        Minecraft.getInstance().setScreenAndShow(new PonderListScreen());
    }
}
