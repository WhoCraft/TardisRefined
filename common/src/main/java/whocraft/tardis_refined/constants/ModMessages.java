package whocraft.tardis_refined.constants;

import whocraft.tardis_refined.TardisRefined;

public class ModMessages {

    /* Related Links*/

    public static String GITHUB_RELEASE_PAGE = "https://github.com/CommandrMoose/TardisRefined/releases";

    /*Item Special cases*/
    public static String ITEM_KEYCHAIN = "item."+ TardisRefined.MODID + ".keychain";

    /*Messages*/
    public static String MSG_EXTERIOR_COOLDOWN = message("exterior_cooldown");
    public static String MSG_KEY_BOUND = message("key_bound");
    public static String MSG_KEY_CYCLED = message("key_cycled");
    public static String CONTROL_DIMENSION_SELECTED = message("selected");

    public static String TOOLTIP_TARDIS_LIST_TITLE = tooltip("tardis_list");

    /*UI Messages*/
    public static final String UI_DESKTOP_CANCEL_DESKTOP = ui("monitor.cancel_desktop");
    public static final String UI_DESKTOP_CANCEL_TITLE = ui("monitor.desktop_cancel.title");
    public static final String UI_DESKTOP_CANCEL = ui("monitor.desktop_cancel");
    public static final String UI_DESKTOP_CANCEL_DESCRIPTION = ui("monitor.desktop_cancel_description");
    public static final String UI_EXTERNAL_SHELL = ui("monitor.external_shell");
    public static final String UI_LIST_SELECTION = ui("monitor.list.selection");
    public static final String UI_DESKTOP_CONFIGURATION = ui("monitor.desktop");
    public static final String UI_SHELL_SELECTION = ui("shell_selection");
    public static final String UI_DESKTOP_SELECTION = ui("desktop_selection");
    public static final String UI_MONITOR_GPS = ui("monitor.main.gps");
    public static final String UI_MONITOR_DESTINATION = ui("monitor.main.destination");
    public static final String UI_MONITOR_MAIN_TITLE = ui("monitor.main_title");
    public static final String UI_MONITOR_WAYPOINTS = ui("monitor.waypoints");
    public static final String UI_MONITOR_UPGRADES = ui("monitor.upgrades");
    public static final String UI_MONITOR_UPLOAD_WAYPOINTS = ui("monitor.upload.waypoints");
    public static final String UI_MONITOR_UPLOAD_COORDS = ui("monitor.upload.coords");

    public static final String CONFIG_IP_COMPAT = config("immersive_portals");
    public static final String CONFIG_CONTROL_NAMES = config("control_names");
    public static final String CONFIG_IDLE_CONSOLE_ANIMS = config("console_idle_animations");

    public static final String CONFIG_BANNED_DIMENSIONS = config("banned_dimensions");

    public static final String CMD_DIM_NOT_A_TARDIS = cmdOutput("dim_not_a_tardis");
    public static final String CMD_NO_INTERNAL_DOOR = cmdOutput("no_internal_door");
    public static final String CMD_EXPORT_DESKTOP_IN_PROGRESS = cmdOutput("export_desktop.in_progress");
    public static final String CMD_EXPORT_DESKTOP_SUCCESS = cmdOutput("export_desktop.success");
    public static final String CMD_EXPORT_DESKTOP_RESOURCE_PACK = cmdOutput("export_desktop.resource_pack");
    public static final String CMD_EXPORT_DESKTOP_FAIL = cmdOutput("export_desktop.fail");

    public static String message(String translationKey){
        return "message." + TardisRefined.MODID + "." + translationKey;
    }

    public static String ui(String translationKey){
        return "ui." + TardisRefined.MODID + "." + translationKey;
    }

    public static String desktop(String translationKey) {
        return "desktop." + TardisRefined.MODID + "." + translationKey;
    }

    public static String shell(String translationKey) {
        return "shell." + TardisRefined.MODID + "." + translationKey;
    }


    public static String tooltip(String translationKey) {
        return "tooltip." + TardisRefined.MODID + "." + translationKey;
    }

    public static String cmdOutput(String translationKey) {
        return "command." + TardisRefined.MODID + "." + translationKey;
    }

    public static String config(String translationKey) {
        return "config." + TardisRefined.MODID + "." + translationKey;
    }

}
