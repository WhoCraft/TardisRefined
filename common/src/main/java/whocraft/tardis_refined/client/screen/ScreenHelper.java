package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class ScreenHelper {

    /**
     * @param text  - The text you'd like to draw
     * @param width - The max width of the text, scales to maintain this width if larger than it
     */
    public static void renderWidthScaledText(String text, GuiGraphics guiGraphics, Font font, float x, float y, int color, int width, float scale, boolean centered) {

        PoseStack matrix = guiGraphics.pose();
        matrix.pushPose();
        int textWidth = font.width(text);
        float inputScale = width / (float) textWidth;
        inputScale = Mth.clamp(inputScale, 0.0F, scale);
        matrix.translate(x, y, 0);
        matrix.scale(inputScale, inputScale, inputScale);
        if (centered) {
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, text, 0, 0, color);
        } else {
            guiGraphics.drawString(Minecraft.getInstance().font, text, 0, 0, color);
        }

        matrix.popPose();
    }

    public static void renderWidthScaledText(String text, GuiGraphics guiGraphics, Font font, float x, float y, int color, int width, boolean centered) {
        renderWidthScaledText(text, guiGraphics, font, x, y, color, width, 1.0F, centered);
    }

}
