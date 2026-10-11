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
import whocraft.tardis_refined.client.model.blockentity.shell.internal.door.RootShellDoorModel;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.blockentity.door.RootShellDoorBlockEntity;

public class RootShellDoorRenderer extends LegacyBlockEntityRenderer<RootShellDoorBlockEntity> {

    private static final Identifier rootShellDoorTexture = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/blockentity/root/root_plant/internal_door.png");
    private static RootShellDoorModel rootShellDoorModel;

    public RootShellDoorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        rootShellDoorModel = new RootShellDoorModel(context.bakeLayer((ModelRegistry.ROOT_SHELL_DOOR)));
    }

    @Override
    protected void renderLegacy(RootShellDoorBlockEntity blockEntity, float f, PoseStack poseStack, DeferredGeometryBuffer multiBufferSource, int i) {
        poseStack.pushPose();

        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.rotateDegrees(Axis.ZP, 180F);

        BlockState state = blockEntity.getBlockState();
        float rotation = state.getValue(RootedShellBlock.FACING).toYRot();
        poseStack.rotateDegrees(Axis.YP, rotation);
        rootShellDoorModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderTypes.entityTranslucent(rootShellDoorTexture)), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

}
