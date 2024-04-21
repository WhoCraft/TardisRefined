package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.selections.DesktopSelectionScreen;
import whocraft.tardis_refined.client.screen.selections.HumSelectionScreen;
import whocraft.tardis_refined.client.screen.selections.SelectionScreen;
import whocraft.tardis_refined.client.screen.waypoints.CoordInputType;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;
import whocraft.tardis_refined.common.network.messages.screens.C2SRequestShellSelection;
import whocraft.tardis_refined.common.network.messages.waypoints.C2SOpenCoordinatesDisplayMessage;
import whocraft.tardis_refined.common.network.messages.waypoints.RequestWaypointsMessage;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.constants.ModMessages;

import java.awt.*;


public class MonitorScreen extends SelectionScreen {

    private final TardisNavLocation currentLocation;
    private final TardisNavLocation targetLocation;
    private final UpgradeHandler upgradeHandler;


    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/gui/monitor.png");


    public MonitorScreen(TardisNavLocation currentLocation, TardisNavLocation targetLocation, UpgradeHandler upgradeHandler) {
        super(Component.translatable(ModMessages.UI_MONITOR_MAIN_TITLE));
        this.currentLocation = currentLocation;
        this.targetLocation = targetLocation;
        this.upgradeHandler = upgradeHandler;
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

    private boolean noUpgrades = false;

    @Override
    public GenericMonitorSelectionList createSelectionList() {
        int leftPos = this.width / 2 - 75;
        GenericMonitorSelectionList<SelectionListEntry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, 250, 80, leftPos - 70, this.topPos + 30, this.topPos + this.imageHeight - 45, 12);
        selectionList.setRenderBackground(false);

        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_EXTERNAL_SHELL), entry -> new C2SRequestShellSelection().send(), leftPos, Upgrades.CHAMELEON_CIRCUIT_SYSTEM.get().isUnlocked(upgradeHandler)));


        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_DESKTOP_CONFIGURATION), entry -> Minecraft.getInstance().setScreen(new DesktopSelectionScreen()), leftPos, Upgrades.INSIDE_ARCHITECTURE.get().isUnlocked(upgradeHandler)));

        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_MONITOR_UPLOAD_WAYPOINTS), entry -> new C2SOpenCoordinatesDisplayMessage(CoordInputType.WAYPOINT).send(), leftPos, Upgrades.WAYPOINTS.get().isUnlocked(upgradeHandler)));
        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_MONITOR_WAYPOINTS), entry -> new RequestWaypointsMessage().send(), leftPos, Upgrades.WAYPOINTS.get().isUnlocked(upgradeHandler)));

        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_MONITOR_UPLOAD_COORDS), entry -> new C2SOpenCoordinatesDisplayMessage(CoordInputType.TRAVEL).send(), leftPos, Upgrades.COORDINATE_INPUT.get().isUnlocked(upgradeHandler)));

        selectionList.children().add(new SelectionListEntry(Component.translatable(ModMessages.UI_MONITOR_SELECT_HUM), entry -> Minecraft.getInstance().setScreen(new HumSelectionScreen()), leftPos));


        if (selectionList.children().isEmpty()) {
            noUpgrades = true;
        }

        return selectionList;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        int textOffset = height / 2 - 35;

        int upgradesLeftPos = this.width / 2 - 75;


        if (noUpgrades) {
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(ModMessages.UI_NO_INSTALLED_SUBSYSTEMS).getString(), upgradesLeftPos, this.topPos + 30, ChatFormatting.GOLD.getColor());
        }

        this.renderTransparentBackground(guiGraphics);

        /*Render Back drop*/
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        guiGraphics.blit(MONITOR_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        int textScale = 40;

        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(ModMessages.UI_MONITOR_GPS).getString() + ":", width / 2 - 96, textOffset + 50, Color.WHITE.getRGB());
        ScreenHelper.renderWidthScaledText(currentLocation.getDirection().getName().toUpperCase() + " @ " + currentLocation.getPosition().toShortString(), guiGraphics, Minecraft.getInstance().font, width / 2 - 96, textOffset + 60, Color.LIGHT_GRAY.getRGB(), textScale * 2, 0.75F, false);
        ScreenHelper.renderWidthScaledText(MiscHelper.getCleanDimensionName(currentLocation.getDimensionKey()), guiGraphics, Minecraft.getInstance().font, width / 2 - 96, textOffset + 70, Color.LIGHT_GRAY.getRGB(), textScale - 3, 1.5F, false);

        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(ModMessages.UI_MONITOR_DESTINATION).getString() + ":", width / 2 + 10, textOffset + 50, Color.WHITE.getRGB());
        ScreenHelper.renderWidthScaledText(targetLocation.getDirection().getName().toUpperCase() + " @ " + targetLocation.getPosition().toShortString(), guiGraphics, Minecraft.getInstance().font, width / 2 + 10, textOffset + 60, Color.LIGHT_GRAY.getRGB(), textScale * 2, 0.75F, false);
        ScreenHelper.renderWidthScaledText(MiscHelper.getCleanDimensionName(targetLocation.getDimensionKey()), guiGraphics, Minecraft.getInstance().font, width / 2 + 10, textOffset + 70, Color.LIGHT_GRAY.getRGB(), textScale - 3, 1.5F, false);

    }
}
