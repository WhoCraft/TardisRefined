package whocraft.tardis_refined.client.renderer.blockentity.device;

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
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleModelCollection;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleUnit;
import whocraft.tardis_refined.common.block.device.ConsoleConfigurationBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.device.ConsoleConfigurationBlockEntity;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;

import java.util.List;

public class ConsoleConfigurationRenderer extends LegacyBlockEntityRenderer<ConsoleConfigurationBlockEntity> {

    public ConsoleConfigurationRenderer(Context context) {
        super(context);

    }


    @Override
    protected void renderLegacy(ConsoleConfigurationBlockEntity blockEntity, float partialTick, PoseStack poseStack, DeferredGeometryBuffer bufferSource, int packedLight) {

        var facing = blockEntity.getBlockState().getValue(ConsoleConfigurationBlock.FACING);
        var adjacentConsolePos = ConsoleConfigurationBlock.findAdjacentConsolePos(
                blockEntity.getLevel(), blockEntity.getBlockPos(), facing);
        if (adjacentConsolePos != null
                && blockEntity.getLevel().getBlockEntity(adjacentConsolePos) instanceof GlobalConsoleBlockEntity) {
            return; // The real console is already rendered in this position.
        }

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.rotateDegrees(Axis.ZP, 180F);

        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(ConsoleConfigurationBlock.FACING).getOpposite().toYRot();
        poseStack.rotateDegrees(Axis.YP, rotation);

        if (blockEntity.getLevel() != null) {
            poseStack.scale(0.25f, 0.25f, 0.25f);
            poseStack.translate(0, 1.5f, 0);
            poseStack.rotateDegrees(Axis.YP, (blockEntity.getLevel().getGameTime() + partialTick) % 360);

            Identifier theme = blockEntity.theme();
            if (theme != null) {
                List<ConsolePattern> patterns = ConsolePatterns.getPatternsForTheme(theme);
                if (patterns != null && !patterns.isEmpty()) {
                    ConsolePattern themePattern = blockEntity.pattern();
                    if (themePattern == null || !theme.equals(themePattern.getThemeId())) {
                        themePattern = patterns.get(0);
                    }
                    var modelEntry = ConsoleModelCollection.getInstance().getConsoleEntry(theme);
                    ConsoleUnit consoleModel = modelEntry == null ? null : modelEntry.getConsoleModel(themePattern);

                    if (consoleModel != null) {
                        consoleModel.renderConsole(
                                null,
                                blockEntity.getLevel(),
                                poseStack,
                                bufferSource.getBuffer(RenderTypes.entityTranslucent(themePattern.texture())),
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                0.635f, 0.392f, 0.878f, 0.5f
                        );
                    }
                }
            }
        }


        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }
}
