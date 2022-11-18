package whocraft.tardis_refined.client.renderer.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
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
import whocraft.tardis_refined.client.model.blockentity.shell.rootplant.RootShellModel;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.blockentity.shell.RootPlantBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.RootedShellBlockEntity;

public class RootShellRenderer implements BlockEntityRenderer<RootedShellBlockEntity>, BlockEntityRendererProvider<RootedShellBlockEntity> {

    private static RootShellModel rootShellModel;

    private static ResourceLocation rootShellTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/root_shell.png");

    public RootShellRenderer(BlockEntityRendererProvider.Context context) {
        rootShellModel = new RootShellModel(context.bakeLayer((ModelRegistry.ROOT_SHELL)));
    }

    @Override
    public void render(RootedShellBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        BlockState blockstate = blockEntity.getBlockState();
        float rotation = ((Direction)blockstate.getValue(RootedShellBlock.FACING)).toYRot();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation));

        rootShellModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(rootShellTexture)),
                i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        poseStack.popPose();
    }

    @Override
    public BlockEntityRenderer<RootedShellBlockEntity> create(Context context) {
        return new RootShellRenderer(context);
    }
}
