package whocraft.tardis_refined.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.shell.rootplant.*;
import whocraft.tardis_refined.common.block.RootPlantBlock;
import whocraft.tardis_refined.common.blockentity.shell.RootPlantBlockEntity;

public class RootPlantRenderer implements BlockEntityRenderer<RootPlantBlockEntity>, BlockEntityRendererProvider<RootPlantBlockEntity> {

    private static RootPlantStateOneModel rootPlantStateOneModel;
    private static RootPlantStateTwoModel rootPlantStateTwoModel;
    private static RootPlantStateThreeModel rootPlantStateThreeModel;
    private static RootPlantStateFourModel rootPlantStateFourModel;
    private static RootPlantStateFiveModel rootPlantStateFiveModel;
    private static ResourceLocation rootPlantOneTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_one.png");
    private static ResourceLocation rootPlantTwoTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_two.png");
    private static ResourceLocation rootPlantThreeTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_three.png");
    private static ResourceLocation rootPlantFourTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_four.png");
    private static ResourceLocation rootPlantFiveTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_five.png");

    public RootPlantRenderer(BlockEntityRendererProvider.Context context) {
        rootPlantStateOneModel = new RootPlantStateOneModel(context.bakeLayer((ModelRegistry.ROOT_PLANT_STATE_ONE)));
        rootPlantStateTwoModel = new RootPlantStateTwoModel(context.bakeLayer((ModelRegistry.ROOT_PLANT_STATE_TWO)));
        rootPlantStateThreeModel = new RootPlantStateThreeModel(context.bakeLayer((ModelRegistry.ROOT_PLANT_STATE_THREE)));
        rootPlantStateFourModel = new RootPlantStateFourModel(context.bakeLayer((ModelRegistry.ROOT_PLANT_STATE_FOUR)));
        rootPlantStateFiveModel = new RootPlantStateFiveModel(context.bakeLayer((ModelRegistry.ROOT_PLANT_STATE_FIVE)));
    }

    @Override
    public void render(RootPlantBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));

        BlockState state = blockEntity.getBlockState();

        switch (state.getValue(RootPlantBlock.AGE)) {
            case 0:
                rootPlantStateOneModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(rootPlantOneTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;
            case 1:
                rootPlantStateTwoModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(rootPlantTwoTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;
            case 2:
                rootPlantStateThreeModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(rootPlantThreeTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;

                case 3:
                    rootPlantStateFourModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(rootPlantFourTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;

            case 4:
                rootPlantStateFiveModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(rootPlantFiveTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;
        }

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(RootPlantBlockEntity blockEntity) {
        return true;
    }

    @Override
    public BlockEntityRenderer<RootPlantBlockEntity> create(Context context) {
        return new RootPlantRenderer(context);
    }
}
