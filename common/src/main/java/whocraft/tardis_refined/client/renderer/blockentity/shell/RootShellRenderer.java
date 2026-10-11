package whocraft.tardis_refined.client.renderer.blockentity.shell;

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
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.GenericModel;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.RootedShellBlockEntity;

public class RootShellRenderer extends LegacyBlockEntityRenderer<RootedShellBlockEntity> {

    private static final Identifier rootShellTexture = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/shell/root/root_shell.png");
    private static final Identifier rootShellClosed = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/shell/root/root_shell_closed.png");
    private static GenericModel rootShellModel;

    public RootShellRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        rootShellModel = new GenericModel(context.bakeLayer((ModelRegistry.ROOT_SHELL)));
    }

    @Override
    protected void renderLegacy(RootedShellBlockEntity blockEntity, float f, PoseStack poseStack, DeferredGeometryBuffer multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.rotateDegrees(Axis.ZP, 180F);
        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(RootedShellBlock.FACING).toYRot();
        poseStack.rotateDegrees(Axis.YP, rotation);

        boolean isOpen = blockEntity.getBlockState().getValue(ShellBaseBlock.OPEN);

        rootShellModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucent((isOpen) ? rootShellTexture : rootShellClosed)),
                i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        float sine = 0;
        if (blockstate.getValue(ShellBaseBlock.REGEN)) {
            sine = (float) ((Math.sin(0.1 * blockEntity.getLevel().getGameTime()) * 1));
            if (sine < 0) {
                sine = 0;
            }

            rootShellModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucentEmissive(rootShellClosed)),
                    i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, sine);
        }


        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(RootedShellBlockEntity blockEntity, Vec3 vec3) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

}
