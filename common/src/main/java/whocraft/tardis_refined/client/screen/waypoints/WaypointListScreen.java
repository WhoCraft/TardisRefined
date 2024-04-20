package whocraft.tardis_refined.client.screen.waypoints;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.ScreenHelper;
import whocraft.tardis_refined.client.screen.components.CommonTRWidgets;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.selections.SelectionScreen;
import whocraft.tardis_refined.common.network.messages.waypoints.C2SOpenCoordinatesDisplayMessage;
import whocraft.tardis_refined.common.network.messages.waypoints.RemoveWaypointEntryMessage;
import whocraft.tardis_refined.common.network.messages.waypoints.TravelToWaypointMessage;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.constants.ModMessages;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;


public class WaypointListScreen extends SelectionScreen {

    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    private SpriteIconButton loadButton;

    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/gui/monitor.png");
    Collection<TardisNavLocation> WAYPOINTS = new ArrayList<>();
    TardisNavLocation tardisNavLocation = TardisNavLocation.ORIGIN;
    public static final ResourceLocation TRASH_LOCATION = new ResourceLocation(TardisRefined.MODID, "trash");
    public static final ResourceLocation OKAY_TEXTURE = new ResourceLocation(TardisRefined.MODID, "okay");

    public WaypointListScreen(Collection<TardisNavLocation> waypoints) {
        super(Component.translatable(ModMessages.UI_MONITOR_MAIN_TITLE));
        this.WAYPOINTS = waypoints;
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    Component noWaypointsLabel = Component.translatable(ModMessages.UI_MONITOR_NO_WAYPOINTS);

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        //Super method already creates the list, we don't need to create it a second time.
        super.init();

        setEvents(() -> {
            if(tardisNavLocation != TardisNavLocation.ORIGIN) {
                new TravelToWaypointMessage(tardisNavLocation.getName()).send();
                Minecraft.getInstance().setScreen(null);
            }
            Minecraft.getInstance().setScreen(null);
        }, new SelectionScreenRun() {
            @Override
            public void onPress() {
                if (tardisNavLocation != TardisNavLocation.ORIGIN) {
                    new RemoveWaypointEntryMessage(tardisNavLocation.getName()).send();
                }
            }
        });


        SpriteIconButton spriteButtonNew = this.addRenderableWidget(CommonTRWidgets.imageButton(20, Component.translatable("Submit"), (arg) -> {
            new C2SOpenCoordinatesDisplayMessage(CoordInputType.WAYPOINT).send();
        }, true, BUTTON_LOCATION));
        spriteButtonNew.setPosition(width / 2 + 85, (height) / 2 - 60);

        this.loadButton = this.addRenderableWidget(CommonTRWidgets.imageButton(20, Component.translatable("Submit"), (arg) -> {
            if(tardisNavLocation != TardisNavLocation.ORIGIN) {
                new TravelToWaypointMessage(tardisNavLocation.getName()).send();
                Minecraft.getInstance().setScreen(null);
            }
        }, true, OKAY_TEXTURE));
        this.loadButton.setPosition(width / 2 + 85, (height) / 2 - 20);

        this.loadButton.active = false;



        addTrashIcon(width / 2 + 85, (height) / 2 - 40);

    }


    public void addTrashIcon(int x, int y) {
        SpriteIconButton spriteiconbutton = this.addRenderableWidget(CommonTRWidgets.imageButton(20, Component.translatable("Submit"), (arg) -> {
            new RemoveWaypointEntryMessage(tardisNavLocation.getName()).send();
            this.loadButton.active = false;
        }, true, TRASH_LOCATION));
        spriteiconbutton.setPosition(x, y);
    }

    @Override
    public GenericMonitorSelectionList createSelectionList() {
        int leftPos = this.width / 2 - 100;
        GenericMonitorSelectionList<SelectionListEntry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, 250, 80, leftPos - 70, this.topPos + 45, this.topPos + this.imageHeight - 45 , 12);
        selectionList.setRenderBackground(false);

        for (TardisNavLocation waypoint : WAYPOINTS) {
            selectionList.children().add(new SelectionListEntry(Component.literal(waypoint.getName()), entry -> {
                entry.setChecked(true);
                tardisNavLocation = waypoint;
                this.loadButton.active = true;

                for (SelectionListEntry child : selectionList.children()) {
                    if(child != entry) {
                        child.setChecked(false);
                    }
                }

            }, leftPos));
        }

        return selectionList;
    }


    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        // super.renderBackground(guiGraphics, i, j, f);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {

        int textOffset = height / 2 - 60;
        int textScale = 40;

        this.renderTransparentBackground(guiGraphics);

        guiGraphics.blit(MONITOR_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        ScreenHelper.renderWidthScaledText(Component.translatable(ModMessages.UI_MONITOR_WAYPOINTS).getString(), guiGraphics, Minecraft.getInstance().font, width / 2 - 96, textOffset, Color.LIGHT_GRAY.getRGB(), textScale * 2, 1F, false);

        if (WAYPOINTS.isEmpty()) {
            ScreenHelper.renderWidthScaledText(noWaypointsLabel.getString(), guiGraphics, Minecraft.getInstance().font, width / 2 - 96, textOffset + 15, Color.LIGHT_GRAY.getRGB(), textScale * 2, 1F, false);
        }

        super.render(guiGraphics, i, j, f);

    }
}
