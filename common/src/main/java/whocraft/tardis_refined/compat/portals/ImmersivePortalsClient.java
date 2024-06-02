package whocraft.tardis_refined.compat.portals;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import qouteall.imm_ptl.core.render.context_management.PortalRendering;

public class ImmersivePortalsClient {

    @Environment(EnvType.CLIENT)
    public static void doClientRenderers() {
        if (ImmersivePortals.BOTI_PORTAL == null) return;
        EntityRendererRegistry.register(ImmersivePortals.BOTI_PORTAL.get(), BotiPortalRenderer::new);
    }


    public static boolean shouldStopRenderingInPortal() {
        if (PortalRendering.isRendering()) return true;
        return false;
    }
}
