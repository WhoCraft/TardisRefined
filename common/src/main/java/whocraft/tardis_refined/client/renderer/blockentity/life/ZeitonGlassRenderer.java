package whocraft.tardis_refined.client.renderer.blockentity.life;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.model.blockentity.life.ArsEggModel;
import whocraft.tardis_refined.client.model.blockentity.life.ZeitonGlassModel;
import whocraft.tardis_refined.common.VortexRegistry;
import whocraft.tardis_refined.common.block.life.ArsEggBlock;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;
import whocraft.tardis_refined.common.blockentity.life.ZeitonGlassBlockEntity;

import static whocraft.tardis_refined.client.overlays.VortexOverlay.VORTEX;


public class ZeitonGlassRenderer implements BlockEntityRenderer<ZeitonGlassBlockEntity>, BlockEntityRendererProvider<ZeitonGlassBlockEntity> {

    private final ZeitonGlassModel zeitonGlassModel;
    private final ResourceLocation backgroundBlack = new ResourceLocation(TardisRefined.MODID, "textures/black_portal.png");

    public ZeitonGlassRenderer(Context context) {
        this.zeitonGlassModel = new ZeitonGlassModel(context.bakeLayer(ModelRegistry.ZEITON_GLASS));
    }

    @Override
    public void render(ZeitonGlassBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        poseStack.pushPose();

        BlockState blockState = blockEntity.getBlockState();

        zeitonGlassModel.renderToBuffer(blockEntity, poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(backgroundBlack)),
                i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);


        poseStack.popPose();
    }

    @Override
    public BlockEntityRenderer<ZeitonGlassBlockEntity> create(Context context) {
        return new ZeitonGlassRenderer(context);
    }
}
