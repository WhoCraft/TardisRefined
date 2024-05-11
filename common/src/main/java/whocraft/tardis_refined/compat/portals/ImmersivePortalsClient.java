package whocraft.tardis_refined.compat.portals;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import qouteall.imm_ptl.core.render.PortalEntityRenderer;

public class ImmersivePortalsClient {

    @Environment(EnvType.CLIENT)
    public static void doClientRenderers() {
        EntityRendererRegistry.register(ImmersivePortals.BOTI_PORTAL.get(), PortalEntityRenderer::new);
    }
}
