package whocraft.tardis_refined.client.renderer.blockentity.door;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import whocraft.tardis_refined.client.renderer.DeferredGeometryBuffer;
import whocraft.tardis_refined.client.renderer.LegacyBlockEntityRenderer;
import whocraft.tardis_refined.client.renderer.vortex.RenderTargetHelper;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;

public class GlobalDoorRenderer extends LegacyBlockEntityRenderer<GlobalDoorBlockEntity> {
    public GlobalDoorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderLegacy(GlobalDoorBlockEntity blockEntity, float partialTick,
                                com.mojang.blaze3d.vertex.PoseStack poseStack,
                                DeferredGeometryBuffer bufferSource, int packedLight) {
        poseStack.pushPose();
        RenderTargetHelper.renderVortex(blockEntity, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }
}
