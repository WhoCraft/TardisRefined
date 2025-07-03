package whocraft.tardis_refined.client.renderer.blockentity.door;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.joml.Matrix4f;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.door.interior.BulkHeadDoorModel;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;

public class BulkHeadDoorRenderer implements BlockEntityRenderer<BulkHeadDoorBlockEntity>, BlockEntityRendererProvider<BulkHeadDoorBlockEntity> {

    private final BulkHeadDoorModel bulkHeadDoorModel;

    public BulkHeadDoorRenderer(BlockEntityRendererProvider.Context context) {
        bulkHeadDoorModel = new BulkHeadDoorModel(context.bakeLayer((ModelRegistry.BULK_HEAD_DOOR)));
    }

    @Override
    public void render(BulkHeadDoorBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {

        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(GlobalDoorBlock.FACING).toYRot();
        boolean isOpen = blockstate.getValue(GlobalDoorBlock.OPEN);

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180F));

        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        bulkHeadDoorModel.setDoorPosition(blockstate);
        bulkHeadDoorModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(getTextureForState(blockstate))), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        if (blockEntity.getDoorName() != null && !isOpen) {
            Matrix4f textMatrix = poseStack.last().pose();
            poseStack.scale(-0.025F, 0.025F, 0.025F);
            int verticalTextOffset = 0;
            float offDoorOffset = 10f;

            Font font = Minecraft.getInstance().font;

            Component name = Component.literal(blockEntity.getDoorName());

            float textHorizontalPosition = (float) -(font.width(name) / 2);

            FormattedCharSequence sequence = name.getVisualOrderText();

            poseStack.translate(0, 10f, offDoorOffset -2.5f);


            font.drawInBatch8xOutline(sequence, textHorizontalPosition, (float) verticalTextOffset, 16777215, 1, textMatrix, multiBufferSource, 255);

            poseStack.mulPose(Axis.YP.rotationDegrees(180f));
            poseStack.translate(0, 0, offDoorOffset*2 + 1.5);

            font.drawInBatch8xOutline(sequence, textHorizontalPosition, (float) verticalTextOffset, 16777215, 1, textMatrix, multiBufferSource, 255);
        }

        poseStack.popPose();
    }

    private ResourceLocation getTextureForState(BlockState blockstate) {
        return  ResourceLocation.tryBuild(TardisRefined.MODID, "textures/blockentity/door/bulk_head_door_"+ blockstate.getValue(BulkHeadDoorBlock.TYPE).getSerializedName() +".png");
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
