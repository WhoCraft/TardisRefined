package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.ModMessages;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.selections.DesktopSelectionScreen;
import whocraft.tardis_refined.client.screen.selections.SelectionScreen;
import whocraft.tardis_refined.client.screen.selections.ShellSelectionScreen;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.awt.Color;


public class MonitorScreen extends SelectionScreen {

    private BlockPos currentPosition;
    private Direction currentDirection;
    private BlockPos targetPosition;
    private Direction targetDirection;

    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/ui/monitor.png");


    public MonitorScreen(BlockPos exteriorPosition, Direction exteriorDirection, BlockPos targetPosition, Direction targetDirection) {
        super(Component.translatable(ModMessages.UI_MONITOR_MAIN_TITLE));
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

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        addRenderableWidget(createSelectionList());

    }

    @Override
    public GenericMonitorSelectionList createSelectionList() {
        GenericMonitorSelectionList<GenericMonitorSelectionList.Entry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, width / 2 - 45 - (Minecraft.getInstance().options.guiScale().get() * 10), height / 2 - 45, 150, 80, 12);

        selectionList.setRenderBackground(false);
        selectionList.setRenderTopAndBottom(false);

        selectionList.children().add(new GenericMonitorSelectionList.Entry(Component.translatable(ModMessages.UI_EXTERNAL_SHELL), entry -> Minecraft.getInstance().setScreen(new ShellSelectionScreen())));
        selectionList.children().add(new GenericMonitorSelectionList.Entry(Component.translatable(ModMessages.UI_DESKTOP_CONFIGURATION), entry -> Minecraft.getInstance().setScreen(new DesktopSelectionScreen())));

        return selectionList;
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

        int heightOffset = 70, widthOffset = 90;

        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_MONITOR_GPS).getString() + ":", poseStack, Minecraft.getInstance().font, width / 2 - 96, minGPSY - 30 - heightOffset, Color.WHITE.getRGB(), 75, false);
        ScreenHelper.renderWidthScaledText(currentDirection.getName().toUpperCase() + " @ " + currentPosition.toShortString(), poseStack, Minecraft.getInstance().font, width / 2 - 96, minGPSY - 20- heightOffset, Color.LIGHT_GRAY.getRGB(), 100, false);
        ScreenHelper.renderWidthScaledText("OVERWORLD", poseStack, Minecraft.getInstance().font, width / 2 - 96, minGPSY - 10 - heightOffset, Color.LIGHT_GRAY.getRGB(), 100, false);

        ScreenHelper.renderWidthScaledText( Component.translatable(ModMessages.UI_MONITOR_DESTINATION).getString() + ":", poseStack, Minecraft.getInstance().font, width / 2 - 96, minGPSDestY - 30 - heightOffset, Color.WHITE.getRGB(), 75, false);
        ScreenHelper.renderWidthScaledText(targetDirection.getName().toUpperCase() + " @ " + targetPosition.toShortString(), poseStack, Minecraft.getInstance().font, width / 2 - 96, minGPSDestY - 20 - heightOffset, Color.LIGHT_GRAY.getRGB(), 100, false);
        ScreenHelper.renderWidthScaledText("OVERWORLD", poseStack, Minecraft.getInstance().font, width / 2 - 96, minGPSDestY - 10 - heightOffset, Color.LIGHT_GRAY.getRGB(), 100, false);

        super.render(poseStack, i, j, f);
    }
}
