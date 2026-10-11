package whocraft.tardis_refined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

/** Adapts a renderer's established draw method to the 26.3 extract/submit lifecycle. */
public abstract class LegacyBlockEntityRenderer<T extends BlockEntity>
        implements BlockEntityRenderer<T, LegacyBlockEntityRenderState<T>>, BlockEntityRendererProvider<T, LegacyBlockEntityRenderState<T>> {

    protected LegacyBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override public final LegacyBlockEntityRenderState<T> createRenderState() { return new LegacyBlockEntityRenderState<>(); }

    @Override public void extractRenderState(T blockEntity, LegacyBlockEntityRenderState<T> state, float partialTick,
                                             Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTick, cameraPosition, crumblingOverlay);
        state.blockEntity = blockEntity;
        state.partialTick = partialTick;
    }

    @Override public final void submit(LegacyBlockEntityRenderState<T> state, PoseStack poseStack,
                                       SubmitNodeCollector collector, CameraRenderState cameraState) {
        if (state.blockEntity == null) return;
        DeferredGeometryBuffer buffers = new DeferredGeometryBuffer();
        // Legacy model renderers produce vertices in block-local coordinates. Keep the
        // renderer's incoming pose for the queued submission, and build the vertices
        // from a fresh stack so the model's own transforms are applied exactly once.
        renderLegacy(state.blockEntity, state.partialTick, new PoseStack(), buffers, state.lightCoords);
        buffers.submit(collector, poseStack);
    }

    protected abstract void renderLegacy(T blockEntity, float partialTick, PoseStack poseStack,
                                         DeferredGeometryBuffer bufferSource, int packedLight);

    @Override public final BlockEntityRenderer<T, LegacyBlockEntityRenderState<T>> create(BlockEntityRendererProvider.Context context) {
        return this;
    }
}
