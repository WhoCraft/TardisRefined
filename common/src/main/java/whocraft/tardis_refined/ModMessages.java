package whocraft.tardis_refined;

public class ModMessages {

    /*Item Special cases*/
    public static String ITEM_KEYCHAIN = "item."+ TardisRefined.MODID + ".keychain";

    /*Messages*/
    public static String MSG_EXTERIOR_COOLDOWN = message("exterior_cooldown");
    public static String MSG_KEY_BOUND = message("key_bound");

    public static String TOOLTIP_TARDIS_LIST_TITLE = tooltip("tardis_list");

    /*UI Messages*/
    public static final String UI_DESKTOP_CANCEL_DESKTOP = ui("monitor.cancel_desktop");
    public static final String UI_DESKTOP_CANCEL_TITLE = ui("monitor.desktop_cancel.title");
    public static final String UI_DESKTOP_CANCEL_DESCRIPTION = ui("monitor.desktop_cancel_description");
    public static final String UI_EXTERNAL_SHELL = ui("monitor.external_shell");
    public static final String UI_LIST_SELECTION = ui("monitor.list.selection");
    public static final String UI_DESKTOP_CONFIGURATION = ui("monitor.desktop");
    public static final String UI_SHELL_SELECTION = ui("shell_selection");
    public static final String UI_DESKTOP_SELECTION = ui("desktop_selection");
    public static final String UI_MONITOR_GPS = ui("monitor.main.gps");
    public static final String UI_MONITOR_DESTINATION = ui("monitor.main.destination");
    public static final String UI_MONITOR_MAIN_TITLE = ui("monitor.main_title");

    public static String message(String translationKey){
        return "message." + TardisRefined.MODID + "." + translationKey;
    }

    public static String ui(String translationKey){
        return "ui." + TardisRefined.MODID + "." + translationKey;
    }

    public static String desktop(String translationKey){
        return "desktop." + TardisRefined.MODID + "." + translationKey;
    }

    public static String shell(String translationKey){
        return "shell." + TardisRefined.MODID + "." + translationKey;
    }


    public static String tooltip(String translationKey){
        return "tooltip." + TardisRefined.MODID + "." + translationKey;
    }

}
