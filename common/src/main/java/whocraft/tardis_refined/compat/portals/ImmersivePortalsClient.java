package whocraft.tardis_refined.compat.portals;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.lwjgl.glfw.GLFW;
import qouteall.imm_ptl.core.compat.IPPortingLibCompat;
import qouteall.imm_ptl.core.render.context_management.PortalRendering;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.registry.TRDimensionTypes;

public class ImmersivePortalsClient {

    @FunctionalInterface
    public interface RendererRegistrator {
        <T extends Entity> void register(EntityType<? extends T> type, EntityRendererProvider<T> renderer);
    }

    @Environment(EnvType.CLIENT)
    public static void doClientRenderers(RendererRegistrator entityRendererRegistrator) {
        if (ImmersivePortals.BOTI_PORTAL == null) return;
        entityRendererRegistrator.register(ImmersivePortals.BOTI_PORTAL.get(), BotiPortalRenderer::new);
    }

    @Environment(EnvType.CLIENT)
    public static boolean isStencilEnabled(RenderTarget renderTarget){
        return IPPortingLibCompat.getIsStencilEnabled(renderTarget);
    }

    @Environment(EnvType.CLIENT)
    public static void setStencilEnabled(RenderTarget renderTarget, boolean cond){
        IPPortingLibCompat.setIsStencilEnabled(renderTarget, cond);
    }

    @Environment(EnvType.CLIENT)
    public static boolean shouldStopRenderingInPortal() {
        if (PortalRendering.isRendering()) return true;
        return false;
    }
}
