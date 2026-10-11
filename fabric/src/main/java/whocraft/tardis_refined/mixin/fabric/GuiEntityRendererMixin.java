package whocraft.tardis_refined.mixin.fabric;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.render.pip.GuiEntityRenderer;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.gui.pip.GuiEntityRenderState;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.fabric.client.ShellPreviewRenderState;

@Mixin(GuiEntityRenderer.class)
public abstract class GuiEntityRendererMixin {
    private static float tardisRefined$lastWidth = -1.0f;
    private static float tardisRefined$lastHeight = -1.0f;
    private static float tardisRefined$lastFov = -1.0f;
    private static final Matrix4f tardisRefined$baseProjection = new Matrix4f();

    @Inject(method = "renderToTexture(Lnet/minecraft/client/renderer/state/gui/pip/GuiEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V", at = @At("HEAD"))
    private void tardisRefined$usePerspectiveForVortex(GuiEntityRenderState state, PoseStack poseStack,
                                                       SubmitNodeCollector collector, CallbackInfo ci) {
        if (!(state.renderState() instanceof ShellPreviewRenderState preview) || !preview.vortexOnly) return;

        PictureInPictureRendererAccessor accessor = (PictureInPictureRendererAccessor) this;
        Projection projection = accessor.tardisRefined$getProjection();
        float width = Math.max(1, state.x1() - state.x0());
        float height = Math.max(1, state.y1() - state.y0());
        // Match the legacy projection after ShellPreviewRenderer normalizes
        // the PIP's viewportHeight * 32 scene scale back to legacy coordinates.
        float fov = Minecraft.getInstance().options.fov().get().floatValue();
        projection.setupPerspective(1.0f, 9999.0f, fov, width, height);
        if (width != tardisRefined$lastWidth || height != tardisRefined$lastHeight || fov != tardisRefined$lastFov) {
            projection.getMatrix(tardisRefined$baseProjection);
            tardisRefined$lastWidth = width;
            tardisRefined$lastHeight = height;
            tardisRefined$lastFov = fov;
        }
        ProjectionAccessor projectionAccessor = (ProjectionAccessor) (Object) projection;
        projectionAccessor.tardisRefined$getMatrix().set(tardisRefined$baseProjection);
        // Keep the clip transform unshifted. Applying the legacy 11000-unit
        // projection offset here clips every submitted PIP vertex, regardless
        // of the mirrored Z direction (confirmed by the all-black preview).
        projectionAccessor.tardisRefined$setMatrixVersion(projectionAccessor.tardisRefined$getMatrixVersion() + 1L);
        whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("vortex-pip-camera",
                "viewport=" + width + "x" + height + ", near=1.0, far=9999.0, fov=" + fov
                        + ", legacyProjectionZ=0.0"
                        + ", translation=" + state.translation() + ", rotation=" + state.rotation()
                        + ", cameraOverride=" + state.overrideCameraAngle());
        RenderSystem.setProjectionMatrix(
                accessor.tardisRefined$getProjectionMatrixBuffer().getBuffer(projection),
                ProjectionType.PERSPECTIVE);
    }
}
