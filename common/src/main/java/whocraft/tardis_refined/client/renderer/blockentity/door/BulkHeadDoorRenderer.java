package whocraft.tardis_refined.client.renderer.blockentity.door;

import whocraft.tardis_refined.client.renderer.LegacyBlockEntityRenderer;
import whocraft.tardis_refined.client.renderer.DeferredGeometryBuffer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.door.interior.BulkHeadDoorModel;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;

public class BulkHeadDoorRenderer extends LegacyBlockEntityRenderer<BulkHeadDoorBlockEntity> {

    private final BulkHeadDoorModel bulkHeadDoorModel;

    public BulkHeadDoorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        bulkHeadDoorModel = new BulkHeadDoorModel(context.bakeLayer((ModelRegistry.BULK_HEAD_DOOR)));
    }

    @Override
    protected void renderLegacy(BulkHeadDoorBlockEntity blockEntity, float f, PoseStack poseStack, DeferredGeometryBuffer multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.rotateDegrees(Axis.ZP, 180F);
        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(GlobalDoorBlock.FACING).toYRot();
        poseStack.rotateDegrees(Axis.YP, rotation);
        bulkHeadDoorModel.setDoorPosition(blockstate);
        bulkHeadDoorModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucent(getTextureForState(blockstate))), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        poseStack.popPose();
    }

    private Identifier getTextureForState(BlockState blockstate) {
        return  Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/door/bulk_head_door_"+ blockstate.getValue(BulkHeadDoorBlock.TYPE).getSerializedName() +".png");
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

}
