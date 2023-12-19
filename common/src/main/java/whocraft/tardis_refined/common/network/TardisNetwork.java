package whocraft.tardis_refined.common.network;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.network.messages.*;
import whocraft.tardis_refined.common.network.messages.upgrades.C2SDisplayUpgradeScreen;
import whocraft.tardis_refined.common.network.messages.upgrades.S2CDisplayUpgradeScreen;
import whocraft.tardis_refined.common.network.messages.upgrades.UnlockUpgradeMessage;
import whocraft.tardis_refined.common.network.messages.waypoints.*;

public class TardisNetwork {

    public static final NetworkManager NETWORK = NetworkManager.create(new ResourceLocation(TardisRefined.MODID, "channel"));

    public static MessageType OPEN_WAYPOINTS_DISPLAY, DEL_WAYPOINT, CLIENT_OPEN_COORDS_DISPLAY, SERVER_OPEN_COORDS_DISPLAY, UPGRADE_SCREEN_S2C, UPGRADE_SCREEN_C2S, CLIENT_OPEN_COORDS_SCREEN, SERVER_OPEN_COORDS_SCREEN, UPLOAD_WAYPOINT, SET_WAYPOINT, OPEN_WAYPOINTS_SCREEN, REQUEST_WAYPOINTS, SYNC_DESKTOPS, SYNC_CONSOLE_PATTERNS, SYNC_SHELL_PATTERNS, SYNC_LEVELS, INT_REACTION, OPEN_MONITOR, CHANGE_SHELL, CHANGE_DESKTOP, CANCEL_CHANGE_DESKTOP, UNLOCK_UPGRADE;

    public static void init() {
        SYNC_LEVELS = NETWORK.registerS2C("sync_levels", SyncLevelListMessage::new);
        INT_REACTION = NETWORK.registerS2C("int_reaction", SyncTardisClientDataMessage::new);
        OPEN_MONITOR = NETWORK.registerS2C("open_monitor", OpenMonitorMessage::new);
        SYNC_CONSOLE_PATTERNS = NETWORK.registerS2C("sync_console_patterns", SyncConsolePatternsMessage::new);
        SYNC_SHELL_PATTERNS = NETWORK.registerS2C("sync_shell_patterns", SyncShellPatternsMessage::new);
        CHANGE_SHELL = NETWORK.registerC2S("change_shell", ChangeShellMessage::new);
        CHANGE_DESKTOP = NETWORK.registerC2S("change_desktop", ChangeDesktopMessage::new);
        SYNC_DESKTOPS = NETWORK.registerS2C("sync_desktop", SyncDesktopsMessage::new);
        CANCEL_CHANGE_DESKTOP = NETWORK.registerC2S("cancel_change_desktop", CancelDesktopChangeMessage::new);
        REQUEST_WAYPOINTS = NETWORK.registerC2S("request_waypoints", RequestWaypointsMessage::new);
        OPEN_WAYPOINTS_SCREEN = NETWORK.registerS2C("open_waypoints_screen", WaypointsListScreenMessage::new);
        SET_WAYPOINT = NETWORK.registerC2S("set_waypoint", TravelToWaypointMessage::new);
        UPLOAD_WAYPOINT = NETWORK.registerC2S("upload_waypoint", UploadWaypointMessage::new);
        SERVER_OPEN_COORDS_DISPLAY = NETWORK.registerS2C("server_open_coords_display", S2COpenCoordinatesDisplayMessage::new);
        CLIENT_OPEN_COORDS_DISPLAY = NETWORK.registerC2S("client_open_coords_display", C2SOpenCoordinatesDisplayMessage::new);
        OPEN_WAYPOINTS_DISPLAY = NETWORK.registerS2C("open_waypoints_display", WaypointsListScreenMessage::new);
        DEL_WAYPOINT = NETWORK.registerC2S("del_waypoint", RemoveWaypointEntryMessage::new);
        SERVER_OPEN_COORDS_SCREEN = NETWORK.registerS2C("server_open_coords_screen", S2COpenCoordinatesDisplayMessage::new);
        CLIENT_OPEN_COORDS_SCREEN = NETWORK.registerC2S("client_open_coords_screen", C2SOpenCoordinatesDisplayMessage::new);
        UNLOCK_UPGRADE = NETWORK.registerC2S("unlock_upgrade", UnlockUpgradeMessage::new);
        UPGRADE_SCREEN_C2S = NETWORK.registerC2S("upgrade_screen_c2s", C2SDisplayUpgradeScreen::new);
        UPGRADE_SCREEN_S2C = NETWORK.registerS2C("upgrade_screen_s2c", S2CDisplayUpgradeScreen::new);

    }

}
