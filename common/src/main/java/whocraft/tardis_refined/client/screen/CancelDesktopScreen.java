package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import whocraft.tardis_refined.client.screen.selections.DesktopSelectionScreen;
import whocraft.tardis_refined.client.screen.selections.ShellSelectionScreen;
import whocraft.tardis_refined.common.network.messages.CancelDesktopChangeMessage;

import java.awt.*;

public class CancelDesktopScreen extends Screen {

    public CancelDesktopScreen() {
        super(Component.translatable("tardis_refined.gui.cancel_desktop"));
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
        this.addRenderableWidget(new Button(this.width / 2 - (175/2) , this.height / 2 + 10, 175, 20, CommonComponents.GUI_CANCEL, (button) -> {
            new CancelDesktopChangeMessage(Minecraft.getInstance().player.getLevel().dimension()).send();
            Minecraft.getInstance().setScreen(null);
        }));

        this.addRenderableWidget(new Button(this.width / 2 - (175/2) , this.height / 2 + 30, 175, 20, CommonComponents.GUI_BACK, (button) -> {
            Minecraft.getInstance().setScreen(null);
        }));
    }


    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {

        this.renderBackground(poseStack);

        renderWidthScaledText(Component.translatable("tardis_refined.monitor.desktop_cancel.title").getString(), poseStack, Minecraft.getInstance().font, width / 2, 25, Color.LIGHT_GRAY.getRGB(), 300, true);
        renderWidthScaledText(Component.translatable("tardis_refined.monitor.desktop_cancel_description").getString(), poseStack, Minecraft.getInstance().font, width / 2,this.height / 2 - 20 , Color.WHITE.getRGB(), 500, true);
        renderWidthScaledText(Component.translatable("tardis_refined.monitor.desktop_cancel_confirmation").getString(), poseStack, Minecraft.getInstance().font, width / 2,this.height / 2 - 10 , Color.WHITE.getRGB(), 500, true);
        super.render(poseStack, i, j, f);
    }
}
