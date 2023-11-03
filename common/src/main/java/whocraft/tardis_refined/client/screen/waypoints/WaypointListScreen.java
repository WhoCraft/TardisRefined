package whocraft.tardis_refined.client.screen.waypoints;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.selections.SelectionScreen;
import whocraft.tardis_refined.common.network.messages.waypoints.TravelToWaypointMessage;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.ArrayList;
import java.util.Collection;


public class WaypointListScreen extends SelectionScreen {

    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/ui/monitor.png");
    Collection<TardisNavLocation> WAYPOINTS = new ArrayList<>();
    TardisNavLocation tardisNavLocation = null;

    public WaypointListScreen(Collection<TardisNavLocation> waypoints) {
        super(Component.translatable(ModMessages.UI_MONITOR_MAIN_TITLE));
        this.WAYPOINTS = waypoints;
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

        setEvents(() -> {
            new TravelToWaypointMessage(tardisNavLocation.getName()).send();
            Minecraft.getInstance().setScreen(null);
        }, new SelectionScreenRun() {
            @Override
            public void onPress() {

            }
        });


        addSubmitButton(width / 2 + 90, (height) / 2 + 35);


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
    public void render(PoseStack poseStack, int i, int j, float f) {

        this.renderBackground(poseStack);
        RenderSystem.setShaderTexture(0, MONITOR_TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        super.render(poseStack, i, j, f);


    }
}
