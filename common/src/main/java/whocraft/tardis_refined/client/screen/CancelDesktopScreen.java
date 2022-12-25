package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.network.messages.CancelDesktopChangeMessage;
import whocraft.tardis_refined.constants.ModMessages;

import java.awt.*;

public class CancelDesktopScreen extends Screen {

    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;


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
        this.addRenderableWidget(new Button(this.width / 2 - (175/2) , this.height / 2 + 10, 175, 20, Component.translatable(ModMessages.UI_DESKTOP_CANCEL), (button) -> {
            new CancelDesktopChangeMessage(Minecraft.getInstance().player.getLevel().dimension()).send();
            Minecraft.getInstance().setScreen(null);
        }));

        this.addRenderableWidget(new Button(this.width / 2 - (175/2) , this.height / 2 + 30, 175, 20, CommonComponents.GUI_ACKNOWLEDGE, (button) -> {
            Minecraft.getInstance().setScreen(null);
        }));

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }


    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {

        this.renderBackground(poseStack);

        /*Render Back drop*/
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MonitorScreen.MONITOR_TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/chat_tags.png"));
        blit(poseStack, width / 2 - minecraft.font.width(Component.translatable(ModMessages.UI_DESKTOP_CANCEL_TITLE)) + 45, height / 2 -30, 0, 0, 9, 9, 32, 32);
        blit(poseStack, width / 2 + minecraft.font.width(Component.translatable(ModMessages.UI_DESKTOP_CANCEL_TITLE)) - 55, height / 2 -30, 0, 0, 9, 9, 32, 32);

        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_DESKTOP_CANCEL_TITLE).getString(), poseStack, Minecraft.getInstance().font, width / 2, height / 2 -30, Color.LIGHT_GRAY.getRGB(), 300, true);
        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_DESKTOP_CANCEL_DESCRIPTION).getString(), poseStack, Minecraft.getInstance().font, width / 2, this.height / 2 - 20, Color.WHITE.getRGB(), 210, true);
        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_DESKTOP_CANCEL_DESKTOP).getString(), poseStack, Minecraft.getInstance().font, width / 2, this.height / 2 - 10, Color.WHITE.getRGB(), 210, true);
        super.render(poseStack, i, j, f);
    }
}
