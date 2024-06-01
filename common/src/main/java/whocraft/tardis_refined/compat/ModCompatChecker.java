package whocraft.tardis_refined.compat;

import whocraft.tardis_refined.common.util.Platform;

public class ModCompatChecker {

    public static boolean immersivePortals() {
        try {
            Class.forName("qouteall.q_misc_util.MiscHelper");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean create() {
        return Platform.isModLoaded("create");
    }
}
