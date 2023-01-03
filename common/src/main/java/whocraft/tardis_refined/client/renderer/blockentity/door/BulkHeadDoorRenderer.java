package whocraft.tardis_refined.client.renderer.blockentity.door;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.door.BulkHeadDoorModel;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;

public class BulkHeadDoorRenderer implements BlockEntityRenderer<BulkHeadDoorBlockEntity>, BlockEntityRendererProvider<BulkHeadDoorBlockEntity> {

    private final BulkHeadDoorModel bulkHeadDoorModel;
    private final ResourceLocation bulkHeadDoorTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/door/bulk_head_door.png");

    public BulkHeadDoorRenderer(BlockEntityRendererProvider.Context context) {
        bulkHeadDoorModel = new BulkHeadDoorModel(context.bakeLayer((ModelRegistry.BULK_HEAD_DOOR)));
    }

    @Override
    public void render(BulkHeadDoorBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180F));
        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(GlobalDoorBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        bulkHeadDoorModel.setDoorPosition(blockstate);
        bulkHeadDoorModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(bulkHeadDoorTexture)), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(BulkHeadDoorBlockEntity blockEntity) {
        return true;
    }

    @Override
    public BlockEntityRenderer<BulkHeadDoorBlockEntity> create(Context context) {
        return new BulkHeadDoorRenderer(context);
    }
}
