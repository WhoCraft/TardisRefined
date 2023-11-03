package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.selections.DesktopSelectionScreen;
import whocraft.tardis_refined.client.screen.selections.SelectionScreen;
import whocraft.tardis_refined.client.screen.selections.ShellSelectionScreen;
import whocraft.tardis_refined.client.screen.waypoints.CoordInputType;
import whocraft.tardis_refined.client.screen.waypoints.WaypointManageScreen;
import whocraft.tardis_refined.common.network.messages.waypoints.RequestWaypointsMessage;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.constants.ModMessages;

import java.awt.*;
import java.util.ArrayList;


public class MonitorScreen extends SelectionScreen {

    private final TardisNavLocation currentLocation;
    private final TardisNavLocation targetLocation;


    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/gui/monitor.png");


    public MonitorScreen(TardisNavLocation currentLocation, TardisNavLocation targetLocation) {
        super(Component.translatable(ModMessages.UI_MONITOR_MAIN_TITLE));
        this.currentLocation = currentLocation;
        this.targetLocation = targetLocation;
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        //Super method already creates the list, we don't need to create it a second time.
        super.init();
    }

    @Override
    public GenericMonitorSelectionList createSelectionList() {
        int leftPos = this.width / 2 - 75;
        GenericMonitorSelectionList<SelectionListEntry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, 250, 80, leftPos - 70, this.topPos + 30, this.topPos + this.imageHeight - 45 , 12);
        selectionList.setRenderBackground(false);
        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_EXTERNAL_SHELL), entry -> Minecraft.getInstance().setScreen(new ShellSelectionScreen()), leftPos));
        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_DESKTOP_CONFIGURATION), entry -> Minecraft.getInstance().setScreen(new DesktopSelectionScreen()), leftPos));

        ArrayList<ResourceKey<Level>> testLevels = new ArrayList<>();
        testLevels.add(ServerLevel.OVERWORLD);
        testLevels.add(ServerLevel.NETHER);
        testLevels.add(ServerLevel.END);

        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_MONITOR_UPLOAD_WAYPOINTS), entry -> Minecraft.getInstance().setScreen(new WaypointManageScreen(testLevels,  CoordInputType.TRAVEL, currentLocation)), leftPos));
        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_MONITOR_UPLOAD_COORDS), entry -> Minecraft.getInstance().setScreen(new WaypointManageScreen(testLevels,  CoordInputType.TRAVEL, currentLocation)), leftPos));
        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_MONITOR_WAYPOINTS), entry -> new RequestWaypointsMessage().send(), leftPos));

        return selectionList;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);

        this.renderTransparentBackground(guiGraphics);

        /*Render Back drop*/
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        guiGraphics.blit(MONITOR_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        int textOffset = height / 2 - 35;
        int textScale = 40;

        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(ModMessages.UI_MONITOR_GPS).getString() + ":", width / 2 - 96, textOffset + 50, Color.WHITE.getRGB());
        ScreenHelper.renderWidthScaledText(currentLocation.getDirection().getName().toUpperCase() + " @ " + currentLocation.getPosition().toShortString(), guiGraphics, Minecraft.getInstance().font, width / 2 - 96, textOffset + 60, Color.LIGHT_GRAY.getRGB(), textScale * 2, 0.75F, false);
        ScreenHelper.renderWidthScaledText(MiscHelper.getCleanDimensionName(currentLocation.getDimensionKey()), guiGraphics, Minecraft.getInstance().font, width / 2 - 96, textOffset + 70, Color.LIGHT_GRAY.getRGB(), textScale - 3, 1.5F, false);

        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(ModMessages.UI_MONITOR_DESTINATION).getString() + ":", width / 2 + 10, textOffset + 50, Color.WHITE.getRGB());
        ScreenHelper.renderWidthScaledText(targetLocation.getDirection().getName().toUpperCase() + " @ " + targetLocation.getPosition().toShortString(), guiGraphics, Minecraft.getInstance().font, width / 2 + 10, textOffset + 60, Color.LIGHT_GRAY.getRGB(), textScale * 2, 0.75F, false);
        ScreenHelper.renderWidthScaledText(MiscHelper.getCleanDimensionName(targetLocation.getDimensionKey()), guiGraphics, Minecraft.getInstance().font, width / 2 + 10, textOffset + 70, Color.LIGHT_GRAY.getRGB(), textScale - 3, 1.5F, false);

    }
}
