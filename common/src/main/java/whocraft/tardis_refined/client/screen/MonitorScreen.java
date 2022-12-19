package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.ModMessages;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.selections.DesktopSelectionScreen;
import whocraft.tardis_refined.client.screen.selections.SelectionScreen;
import whocraft.tardis_refined.client.screen.selections.ShellSelectionScreen;
import java.awt.Color;


public class MonitorScreen extends Screen {

    private BlockPos currentPosition;
    private Direction currentDirection;
    private BlockPos targetPosition;
    private Direction targetDirection;

    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/ui/monitor.png");


    public MonitorScreen(BlockPos exteriorPosition, Direction exteriorDirection, BlockPos targetPosition, Direction targetDirection) {
        super(Component.translatable("tardis_refined.gui.monitor"));
        this.currentPosition = exteriorPosition;
        this.currentDirection = exteriorDirection;
        this.targetPosition = targetPosition;
        this.targetDirection = targetDirection;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new Button(this.width / 2 - (175/2) , this.height / 2 - 30, 175, 20, Component.translatable(ModMessages.UI_EXTERNAL_SHELL), (button) -> {
            Minecraft.getInstance().setScreen(new ShellSelectionScreen());
        }));

        this.addRenderableWidget(new Button(this.width / 2 - (175/2) , this.height / 2 - 10, 175, 20, Component.translatable(ModMessages.UI_DESKTOP_CONFIGURATION), (button) -> {
            Minecraft.getInstance().setScreen(new DesktopSelectionScreen());
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
        RenderSystem.setShaderTexture(0, MONITOR_TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);


        int minGPSY = height - 60;
        int minGPSDestY = height - 25;
        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_MONITOR_GPS).getString() + ":", poseStack, Minecraft.getInstance().font, 25, minGPSY - 30, Color.WHITE.getRGB(), 75, false);
        ScreenHelper.renderWidthScaledText(currentDirection.getName().toUpperCase() + " @ " + currentPosition.toShortString(), poseStack, Minecraft.getInstance().font, 25, minGPSY - 20, Color.LIGHT_GRAY.getRGB(), 100, false);
        ScreenHelper.renderWidthScaledText("OVERWORLD", poseStack, Minecraft.getInstance().font, 25, minGPSY - 10, Color.LIGHT_GRAY.getRGB(), 100, false);

        ScreenHelper.renderWidthScaledText( Component.translatable(ModMessages.UI_MONITOR_DESTINATION).getString() + ":", poseStack, Minecraft.getInstance().font, 25, minGPSDestY - 30, Color.WHITE.getRGB(), 75, false);
        ScreenHelper.renderWidthScaledText(targetDirection.getName().toUpperCase() + " @ " + targetPosition.toShortString(), poseStack, Minecraft.getInstance().font, 25, minGPSDestY - 20, Color.LIGHT_GRAY.getRGB(), 100, false);
        ScreenHelper.renderWidthScaledText("OVERWORLD", poseStack, Minecraft.getInstance().font, 25, minGPSDestY - 10, Color.LIGHT_GRAY.getRGB(), 100, false);

        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_MONITOR_MAIN_TITLE).getString(), poseStack, Minecraft.getInstance().font, width / 2, 25, Color.LIGHT_GRAY.getRGB(), 300, true);
        super.render(poseStack, i, j, f);
    }
}
