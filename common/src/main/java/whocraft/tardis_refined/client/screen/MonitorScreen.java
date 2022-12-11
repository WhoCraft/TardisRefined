package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import whocraft.tardis_refined.client.screen.selections.DesktopSelectionScreen;
import whocraft.tardis_refined.client.screen.selections.ShellSelectionScreen;

import java.awt.*;

public class MonitorScreen extends Screen {

    private BlockPos currentPosition;
    private Direction currentDirection;
    private BlockPos targetPosition;
    private Direction targetDirection;
    protected Button doneButton;

    public MonitorScreen(BlockPos exteriorPosition, Direction exteriorDirection, BlockPos targetPosition, Direction targetDirection) {
        super(Component.translatable("tardis_refined.gui.monitor"));
        this.currentPosition = exteriorPosition;
        this.currentDirection = exteriorDirection;
        this.targetPosition = targetPosition;
        this.targetDirection = targetDirection;
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    /**
     * @param text  - The text you'd like to draw
     * @param width - The max width of the text, scales to maintain this width if larger than it
     */
    public static void renderWidthScaledText(String text, PoseStack matrix, Font font, float x, float y, int color, int width, boolean centered) {
        matrix.pushPose();
        int textWidth = font.width(text);
        float scale = width / (float) textWidth;
        scale = Mth.clamp(scale, 0.0F, 1.0F);
        matrix.translate(x, y, 0);
        matrix.scale(scale, scale, scale);
        if (centered) {
            drawCenteredString(matrix, Minecraft.getInstance().font, text, 0, 0, color);
        } else {
            drawString(matrix, Minecraft.getInstance().font, text, 0, 0, color);
        }

        matrix.popPose();
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new Button(this.width / 2 - (175/2) , this.height / 2 - 30, 175, 20, Component.translatable("tardis_refined.monitor.external_shell"), (button) -> {
            Minecraft.getInstance().setScreen(new ShellSelectionScreen());
        }));

        this.addRenderableWidget(new Button(this.width / 2 - (175/2) , this.height / 2 - 10, 175, 20, Component.translatable("tardis_refined.monitor.desktop"), (button) -> {
            Minecraft.getInstance().setScreen(new DesktopSelectionScreen());
        }));
    }


    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {

        this.renderBackground(poseStack);


        int minGPSY = height - 60;
        int minGPSDestY = height - 25;
        renderWidthScaledText("GPS:", poseStack, Minecraft.getInstance().font, 25, minGPSY - 30, Color.WHITE.getRGB(), 75, false);
        renderWidthScaledText(currentDirection.getName().toUpperCase() + " @ " + currentPosition.toShortString(), poseStack, Minecraft.getInstance().font, 25, minGPSY - 20, Color.LIGHT_GRAY.getRGB(), 100, false);
        renderWidthScaledText("OVERWORLD", poseStack, Minecraft.getInstance().font, 25, minGPSY - 10, Color.LIGHT_GRAY.getRGB(), 100, false);

        renderWidthScaledText("Destination:", poseStack, Minecraft.getInstance().font, 25, minGPSDestY - 30, Color.WHITE.getRGB(), 75, false);
        renderWidthScaledText(targetDirection.getName().toUpperCase() + " @ " + targetPosition.toShortString(), poseStack, Minecraft.getInstance().font, 25, minGPSDestY - 20, Color.LIGHT_GRAY.getRGB(), 100, false);
        renderWidthScaledText("OVERWORLD", poseStack, Minecraft.getInstance().font, 25, minGPSDestY - 10, Color.LIGHT_GRAY.getRGB(), 100, false);

        renderWidthScaledText("INTERNAL MONITOR READOUT", poseStack, Minecraft.getInstance().font, width / 2, 25, Color.LIGHT_GRAY.getRGB(), 300, true);
        super.render(poseStack, i, j, f);
    }
}
