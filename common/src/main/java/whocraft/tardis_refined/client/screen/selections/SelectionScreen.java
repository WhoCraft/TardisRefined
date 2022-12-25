package whocraft.tardis_refined.client.screen.selections;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.client.screen.ScreenHelper;

import java.awt.Color;

public class SelectionScreen extends Screen {

    private SelectionScreenRun onSubmit;
    private SelectionScreenRun onCancel;
    private final Component title;

    private Button selectShellButton;
    private Button cancelButton;

    public SelectionScreen(Component title) {
        super(Component.translatable(ModMessages.UI_SHELL_SELECTION));
        this.title = title;
    }

    public void setEvents(SelectionScreenRun onSubmit, SelectionScreenRun onCancel) {
        this.onSubmit = onSubmit;
        this.onCancel = onCancel;
    }

    protected int listWidth = 200;
    protected int listHeight = this.height;

    @Override
    protected void init() {
        super.init();

        this.selectShellButton = this.addRenderableWidget(new Button(this.width - 135, this.height - 30, 60, 20, CommonComponents.GUI_DONE, (button) -> {
            this.onSubmit.onPress();
        }));
        this.cancelButton = this.addRenderableWidget(new Button(this.width - 70, this.height - 30, 60, 20, CommonComponents.GUI_CANCEL, (button) -> {
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


    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        ScreenHelper.renderWidthScaledText(this.getSelectedDisplayName().getString(), poseStack, Minecraft.getInstance().font, width / 2, height / 2 - 30, Color.LIGHT_GRAY.getRGB(), 300, true);
        ScreenHelper.renderWidthScaledText(title.getString(), poseStack, Minecraft.getInstance().font, width / 2, 25, Color.LIGHT_GRAY.getRGB(), 300, true);
        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_LIST_SELECTION, this.getSelectedDisplayName().getString()).toString(), poseStack, Minecraft.getInstance().font, width / 2, 45, Color.LIGHT_GRAY.getRGB(), 130, true);
        super.render(poseStack, i, j, f);
    }


    public interface SelectionScreenRun {
        void onPress();
    }
}
