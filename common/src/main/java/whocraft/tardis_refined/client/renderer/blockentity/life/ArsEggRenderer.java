package whocraft.tardis_refined.client.renderer.blockentity.life;

import whocraft.tardis_refined.client.renderer.LegacyBlockEntityRenderer;
import whocraft.tardis_refined.client.renderer.DeferredGeometryBuffer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.model.blockentity.life.ArsEggModel;
import whocraft.tardis_refined.common.block.life.ArsEggBlock;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;

import static whocraft.tardis_refined.client.model.blockentity.life.ArsEggModel.CRASHING;
import static whocraft.tardis_refined.client.model.blockentity.life.ArsEggModel.SWINGING;

public class ArsEggRenderer extends LegacyBlockEntityRenderer<ArsEggBlockEntity> {

    private final ArsEggModel arsEggModel;
    private final Identifier arsEggTexture = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/life/ars_egg.png");
    private final Identifier arsEggTextureEmissive = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/life/ars_egg_emissive.png");

    public ArsEggRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.arsEggModel = new ArsEggModel(context.bakeLayer(ModelRegistry.ARS_EGG));
    }

    @Override
    protected void renderLegacy(ArsEggBlockEntity blockEntity, float f, PoseStack poseStack, DeferredGeometryBuffer multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.translate(0, 0.3, 0);

        BlockState blockState = blockEntity.getBlockState();

        if (!blockState.getValue(ArsEggBlock.HANGING)) {
            poseStack.translate(0, -0.3, 0);
        }

        poseStack.rotateDegrees(Axis.ZP, 180F);

        arsEggModel.root().getAllParts().forEach(ModelPart::resetPose);


        if (!blockEntity.getLiveliness().isStarted()) {
            blockEntity.getLiveliness().start(12);
        }

        if (blockState.getValue(ArsEggBlock.HANGING)) {
            TardisClientData tardisClientData = TardisClientData.getInstance(Minecraft.getInstance().level.dimension());
            boolean crashing = tardisClientData.isCrashing();
            int animationCounter = Minecraft.getInstance().player.tickCount;
            arsEggModel.doAnimation(blockEntity.getLiveliness(), crashing ? CRASHING : SWINGING, animationCounter);
        }

        arsEggModel.renderToBuffer(blockEntity, poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucent(arsEggTexture)),
                i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        if (blockState.getValue(ArsEggBlock.ALIVE)) {
            arsEggModel.renderToBuffer(blockEntity, poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucentEmissive(arsEggTextureEmissive)),
                    i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1F);
        }

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

}
