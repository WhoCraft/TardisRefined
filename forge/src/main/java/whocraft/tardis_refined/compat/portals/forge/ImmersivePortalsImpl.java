package whocraft.tardis_refined.compat.portals.forge;

import net.minecraft.world.entity.EntityType;
import qouteall.imm_ptl.core.platform_specific.IPRegistry;
import qouteall.imm_ptl.core.portal.Portal;

public class ImmersivePortalsImpl {
    public static EntityType<Portal> retrievePortalType() {
        return IPRegistry.PORTAL.get();
    }
}
