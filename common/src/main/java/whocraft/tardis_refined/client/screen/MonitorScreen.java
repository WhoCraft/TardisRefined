package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.client.screen.selections.DesktopSelectionScreen;
import whocraft.tardis_refined.client.screen.selections.SelectionScreen;
import whocraft.tardis_refined.client.screen.selections.ShellSelectionScreen;
import whocraft.tardis_refined.constants.ModMessages;

import java.awt.*;


public class MonitorScreen extends SelectionScreen {

    private BlockPos currentPosition;
    private Direction currentDirection;
    private String currentDimension;
    private BlockPos targetPosition;
    private Direction targetDirection;
    private String targetDimension;

    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/ui/monitor.png");


    public MonitorScreen(BlockPos exteriorPosition, Direction exteriorDirection, String currentDimension, BlockPos targetPosition, Direction targetDirection, String targetDimension) {
        super(Component.translatable(ModMessages.UI_MONITOR_MAIN_TITLE));
        this.currentPosition = exteriorPosition;
        this.currentDirection = exteriorDirection;
        this.currentDimension = currentDimension;
        this.targetPosition = targetPosition;
        this.targetDirection = targetDirection;
        this.targetDimension = targetDimension;
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
        GenericMonitorSelectionList<GenericMonitorSelectionList.Entry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, width / 2 - 140, height / 2 - 55, 150, 80, 12);

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

        int textOffset = height / 2 - 35;
        int textScale = 40;

        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_MONITOR_GPS).getString() + ":", poseStack, Minecraft.getInstance().font, width / 2 - 96, textOffset + 50, Color.WHITE.getRGB(), textScale / 2, false);
        ScreenHelper.renderWidthScaledText(currentDirection.getName().toUpperCase() + " @ " + currentPosition.toShortString(), poseStack, Minecraft.getInstance().font, width / 2 - 96, textOffset + 60, Color.LIGHT_GRAY.getRGB(), textScale + 4, false);
        ScreenHelper.renderWidthScaledText(this.currentDimension, poseStack, Minecraft.getInstance().font, width / 2 - 96, textOffset + 70, Color.LIGHT_GRAY.getRGB(), textScale - 3, false);

        ScreenHelper.renderWidthScaledText( Component.translatable(ModMessages.UI_MONITOR_DESTINATION).getString() + ":", poseStack, Minecraft.getInstance().font, width / 2 - 40, textOffset + 50, Color.WHITE.getRGB(), textScale, false);
        ScreenHelper.renderWidthScaledText(targetDirection.getName().toUpperCase() + " @ " + targetPosition.toShortString(), poseStack, Minecraft.getInstance().font, width / 2  - 40, textOffset + 60, Color.LIGHT_GRAY.getRGB(), textScale + 4, false);
        ScreenHelper.renderWidthScaledText(this.targetDimension, poseStack, Minecraft.getInstance().font, width / 2  - 40, textOffset + 70, Color.LIGHT_GRAY.getRGB(), textScale - 3, false);

        super.render(poseStack, i, j, f);
    }
}
