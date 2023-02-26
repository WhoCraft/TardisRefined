package whocraft.tardis_refined.compat;

public class ModCompatChecker {

    public static boolean immersivePortals() {
        try {
            Class.forName("qouteall.q_misc_util.MiscHelper");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
