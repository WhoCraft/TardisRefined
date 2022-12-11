package whocraft.tardis_refined.client.screen.selections;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.awt.*;

public class SelectionScreen extends Screen {



    private SelectionScreenRun onSubmit;
    private SelectionScreenRun onCancel;
    private Component title;

    private Button selectShellButton;
    private Button cancelButton;

    public SelectionScreen(Component title) {
        super(Component.translatable("tardis_refined.gui.shell_selection"));
        this.title = title;
    }

    public void setEvents(SelectionScreenRun onSubmit, SelectionScreenRun onCancel) {
        this.onSubmit = onSubmit;
        this.onCancel = onCancel;
    }

    public int listWidth = 200;
    public int listHeight = this.height;

    @Override
    protected void init() {
        super.init();

        this.selectShellButton = (net.minecraft.client.gui.components.Button) this.addRenderableWidget(new net.minecraft.client.gui.components.Button(this.width - 135, this.height - 30, 60, 20, CommonComponents.GUI_DONE, (button) -> {
            this.onSubmit.onPress();
        }));
        this.cancelButton = (net.minecraft.client.gui.components.Button) this.addRenderableWidget(new net.minecraft.client.gui.components.Button(this.width - 70, this.height - 30, 60, 20, CommonComponents.GUI_CANCEL, (button) -> {
            this.onCancel.onPress();
        }));

        this.addRenderableWidget(createSelectionList());

    }

    public ObjectSelectionList createSelectionList() {
        return null;
    }

    public Component getSelectedDisplayName() {
        return null;
    }

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
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        renderWidthScaledText(this.getSelectedDisplayName().getString(), poseStack, Minecraft.getInstance().font, width / 2, height / 2 - 30, Color.LIGHT_GRAY.getRGB(), 300, true);


        renderWidthScaledText(title.getString(), poseStack, Minecraft.getInstance().font, width / 2, 25, Color.LIGHT_GRAY.getRGB(), 300, true);
        renderWidthScaledText(Component.translatable("tardis_refined.monitor.list.selection").toString() + ": " + this.getSelectedDisplayName().getString(), poseStack, Minecraft.getInstance().font, width / 2, 45, Color.LIGHT_GRAY.getRGB(), 130, true);
        super.render(poseStack, i, j, f);
    }


    public interface SelectionScreenRun {
        void onPress();
    }
}
