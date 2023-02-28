package whocraft.tardis_refined.client.renderer.blockentity.device;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleModelCollection;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleUnit;
import whocraft.tardis_refined.common.block.device.ConsoleConfigurationBlock;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.blockentity.device.ConsoleConfigurationBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class ConsoleConfigurationRenderer implements BlockEntityRenderer<ConsoleConfigurationBlockEntity>, BlockEntityRendererProvider<ConsoleConfigurationBlockEntity> {

    public ConsoleConfigurationRenderer(Context context) {

    }

    @Override
    public BlockEntityRenderer<ConsoleConfigurationBlockEntity> create(Context context) {
        return new ConsoleConfigurationRenderer(context);
    }

    @Override
    public void render(ConsoleConfigurationBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180F));

        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(GlobalDoorBlock.FACING).getOpposite().toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        if (blockEntity.getLevel().random.nextInt(20) != 0) {
            poseStack.scale(0.25f, 0.25f, 0.25f);
            poseStack.translate(0, 1.5f + blockEntity.getLevel().random.nextFloat() * 0.01, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getLevel().getGameTime() % 360));

            ConsoleTheme theme = blockstate.getValue(ConsoleConfigurationBlock.CONSOLE);

            ConsoleUnit consoleModel = ConsoleModelCollection.getInstance().getConsoleModel(theme);
            consoleModel.renderConsole(null, blockEntity.getLevel(), poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(consoleModel.getDefaultTexture())), packedLight, OverlayTexture.NO_OVERLAY, 1f, 0.64f, 0f, 0.5f);
        }

        poseStack.popPose();
    }
}
