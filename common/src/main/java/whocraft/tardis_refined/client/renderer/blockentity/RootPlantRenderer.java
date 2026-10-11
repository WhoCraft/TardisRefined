package whocraft.tardis_refined.client.renderer.blockentity;

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
import whocraft.tardis_refined.client.model.GenericModel;
import whocraft.tardis_refined.common.block.RootPlantBlock;
import whocraft.tardis_refined.common.blockentity.shell.RootPlantBlockEntity;

public class RootPlantRenderer extends LegacyBlockEntityRenderer<RootPlantBlockEntity> {

    private static final Identifier rootPlantOneTexture = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_one.png");
    private static final Identifier rootPlantTwoTexture = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_two.png");
    private static final Identifier rootPlantThreeTexture = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_three.png");
    private static final Identifier rootPlantFourTexture = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_four.png");
    private static final Identifier rootPlantFiveTexture = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/root/root_plant/stage_five.png");
    private static GenericModel rootPlantStateOneModel;
    private static GenericModel rootPlantStateTwoModel;
    private static GenericModel rootPlantStateThreeModel;
    private static GenericModel rootPlantStateFourModel;
    private static GenericModel rootPlantStateFiveModel;

    public RootPlantRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        rootPlantStateOneModel = new GenericModel(context.bakeLayer(ModelRegistry.ROOT_PLANT_STATE_ONE));
        rootPlantStateTwoModel = new GenericModel(context.bakeLayer(ModelRegistry.ROOT_PLANT_STATE_TWO));
        rootPlantStateThreeModel = new GenericModel(context.bakeLayer(ModelRegistry.ROOT_PLANT_STATE_THREE));
        rootPlantStateFourModel = new GenericModel(context.bakeLayer(ModelRegistry.ROOT_PLANT_STATE_FOUR));
        rootPlantStateFiveModel = new GenericModel(context.bakeLayer(ModelRegistry.ROOT_PLANT_STATE_FIVE));
    }

    @Override
    protected void renderLegacy(RootPlantBlockEntity blockEntity, float f, PoseStack poseStack, DeferredGeometryBuffer multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.rotateDegrees(Axis.ZP, 180F);

        BlockState state = blockEntity.getBlockState();

        switch (state.getValue(RootPlantBlock.AGE)) {
            case 0:
                rootPlantStateOneModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucent(rootPlantOneTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;
            case 1:
                rootPlantStateTwoModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucent(rootPlantTwoTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;
            case 2:
                rootPlantStateThreeModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucent(rootPlantThreeTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;

            case 3:
                rootPlantStateFourModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucent(rootPlantFourTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;

            case 4:
                rootPlantStateFiveModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucent(rootPlantFiveTexture)),
                        i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                break;
        }

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

}
