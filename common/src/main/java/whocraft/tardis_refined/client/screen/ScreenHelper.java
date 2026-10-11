package whocraft.tardis_refined.client.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;

public class ScreenHelper {

    /**
     * @param text  - The text you'd like to draw
     * @param width - The max width of the text, scales to maintain this width if larger than it
     */
    public static void renderWidthScaledText(String text, GuiGraphicsExtractor guiGraphics, Font font, float x, float y, int color, int width, float scale, boolean centered) {

        Matrix3x2fStack matrix = guiGraphics.pose();
        matrix.pushMatrix();
        int textWidth = font.width(text);
        float inputScale = width / (float) textWidth;
        inputScale = Mth.clamp(inputScale, 0.0F, scale);
        matrix.translate(x, y);
        matrix.scale(inputScale, inputScale);
        if (centered) {
            guiGraphics.text(font, text, -textWidth / 2, 0, color);
        } else {
            guiGraphics.text(font, text, 0, 0, color);
        }

        matrix.popMatrix();
    }

    public static void renderWidthScaledText(String text, GuiGraphicsExtractor guiGraphics, Font font, float x, float y, int color, int width, boolean centered) {
        renderWidthScaledText(text, guiGraphics, font, x, y, color, width, 1.0F, centered);
    }

}
