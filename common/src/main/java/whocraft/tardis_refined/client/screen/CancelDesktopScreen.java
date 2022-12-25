package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.common.network.messages.CancelDesktopChangeMessage;

import java.awt.*;

public class CancelDesktopScreen extends Screen {

    public CancelDesktopScreen() {
        super(Component.translatable(ModMessages.UI_DESKTOP_CANCEL_DESKTOP));
    }

    @Override
    public boolean isPauseScreen() {
        return true;
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

        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_DESKTOP_CANCEL_TITLE).getString(), poseStack, Minecraft.getInstance().font, width / 2, 25, Color.LIGHT_GRAY.getRGB(), 300, true);
        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_DESKTOP_CANCEL_DESCRIPTION).getString(), poseStack, Minecraft.getInstance().font, width / 2,this.height / 2 - 20 , Color.WHITE.getRGB(), 500, true);
        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_DESKTOP_CANCEL_DESKTOP).getString(), poseStack, Minecraft.getInstance().font, width / 2,this.height / 2 - 10 , Color.WHITE.getRGB(), 500, true);
        super.render(poseStack, i, j, f);
    }
}
