package whocraft.tardis_refined.client.screen.waypoints;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.selections.SelectionScreen;
import whocraft.tardis_refined.common.network.messages.waypoints.RemoveWaypointEntryMessage;
import whocraft.tardis_refined.common.network.messages.waypoints.TravelToWaypointMessage;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.ArrayList;
import java.util.Collection;


public class WaypointListScreen extends SelectionScreen {

    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/gui/monitor.png");
    Collection<TardisNavLocation> WAYPOINTS = new ArrayList<>();
    TardisNavLocation tardisNavLocation = TardisNavLocation.ORIGIN;
    private static final ResourceLocation TRASH_LOCATION = new ResourceLocation(TardisRefined.MODID, "textures/ui/trash.png");

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


        addSubmitButton(width / 2 + 85, (height) / 2 - 35);
        addTrashIcon(width / 2 + 85, (height) / 2 - 55);


    }

    public void addTrashIcon(int x, int y) {
        this.addRenderableWidget(new ImageButton(x, y, 20, 18, 0, 0, 19, TRASH_LOCATION, 20, 37, (arg) -> {
            new RemoveWaypointEntryMessage(tardisNavLocation.getName()).send();

        }));
    }

    @Override
    public GenericMonitorSelectionList createSelectionList() {
        int leftPos = this.width / 2 - 75;
        GenericMonitorSelectionList<SelectionListEntry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, 175, 80, leftPos - 70, this.topPos + 30, this.topPos + this.imageHeight - 45 , 12);
        selectionList.setRenderBackground(false);

        for (TardisNavLocation waypoint : WAYPOINTS) {
            selectionList.children().add(new SelectionListEntry(Component.literal(waypoint.getName()), entry -> {
                entry.setChecked(true);
                tardisNavLocation = waypoint;

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

        this.renderTransparentBackground(guiGraphics);

        guiGraphics.blit(MONITOR_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        if(WAYPOINTS.isEmpty()) {
            Font font = this.font;
            Component literal = Component.literal("No Waypoints Saved");
            guiGraphics.drawCenteredString(font, literal, width / 2, height / 2 - 10, ChatFormatting.GOLD.getColor());
        }

        super.render(guiGraphics, i, j, f);
    }
}
