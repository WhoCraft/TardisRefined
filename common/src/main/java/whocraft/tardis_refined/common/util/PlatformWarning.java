package whocraft.tardis_refined.common.util;

public class PlatformWarning {

    public static String addWarning(Class obj) {
        return "CRITICAL PLATFORM ERROR IN " + obj.getClass().getName() + ". IF YOU ARE SEEING THIS, PLEASE CONTACT THE AUTHOR!!!!";
    }

}
