package whocraft.tardis_refined.client.renderer.blockentity.device;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.device.ArtronPillarBlockModel;
import whocraft.tardis_refined.client.model.blockentity.door.interior.BulkHeadDoorModel;
import whocraft.tardis_refined.client.model.blockentity.life.ArsEggModel;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.blockentity.device.ArtronPillarBlockEntity;

public class ArtronPillarRenderer implements BlockEntityRenderer<ArtronPillarBlockEntity>, BlockEntityRendererProvider<ArtronPillarBlockEntity> {

    private final ArtronPillarBlockModel artronPillarBlockModel;
    private final ResourceLocation texture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/artron_pillar.png");

    public ArtronPillarRenderer(Context context) {
        artronPillarBlockModel = new ArtronPillarBlockModel(context.bakeLayer((ModelRegistry.ARTRON_PILLAR)));
    }


    @Override
    public void render(ArtronPillarBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180F));
        artronPillarBlockModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(texture)), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        poseStack.popPose();
    }

    @Override
    public BlockEntityRenderer<ArtronPillarBlockEntity> create(Context context) {
        return new ArtronPillarRenderer(context);
    }
}
