package whocraft.tardis_refined.compat;

import whocraft.tardis_refined.common.util.Platform;

public class ModCompatChecker {

    public static boolean immersivePortals() {
        // The 1.20.1 portal integration targets an incompatible API. Keep it disabled
        // until a dedicated 26.3 adapter is available.
        return false;
    }

    public static boolean create() {
        // Create's old Fabric integration is not compatible with the 26.3 target.
        return false;
    }

    public static boolean valkyrienSkies() {
        // The upstream integration targets pre-26.3 Valkyrien Skies APIs.
        return false;
    }
}
