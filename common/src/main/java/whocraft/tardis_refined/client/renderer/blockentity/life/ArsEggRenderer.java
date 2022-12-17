package whocraft.tardis_refined.client.renderer.blockentity.life;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.life.ArsEggModel;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;

public class ArsEggRenderer implements BlockEntityRenderer<ArsEggBlockEntity>, BlockEntityRendererProvider<ArsEggBlockEntity> {

    private ArsEggModel arsEggModel;
    private ResourceLocation arsEggTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/life/ars_egg.png");
    private ResourceLocation arsEggTextureEmissive = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/life/ars_egg_emissive.png");

    public ArsEggRenderer(BlockEntityRendererProvider.Context context) {
        this.arsEggModel = new ArsEggModel(context.bakeLayer(ModelRegistry.ARS_EGG));
    }


    @Override
    public void render(ArsEggBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));

        arsEggModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(arsEggTexture)),
                i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        arsEggModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.eyes(arsEggTextureEmissive)),
                i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.1f);

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(ArsEggBlockEntity blockEntity) {
        return true;
    }

    @Override
    public BlockEntityRenderer<ArsEggBlockEntity> create(Context context) {
        return new ArsEggRenderer(context);
    }
}
