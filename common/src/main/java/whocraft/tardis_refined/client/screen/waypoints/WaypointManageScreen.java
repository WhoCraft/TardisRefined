package whocraft.tardis_refined.client.screen.waypoints;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.ScreenHelper;
import whocraft.tardis_refined.common.network.messages.waypoints.UploadWaypointMessage;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.constants.ModMessages;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class WaypointManageScreen extends Screen {

    private final List<ResourceKey<Level>> worlds;
    private final CoordInputType coordInputType;

    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    protected EditBox xCoord, yCoord, zCoord, waypointName;
    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/ui/monitor.png");

    private TardisNavLocation tardisNavLocation = TardisNavLocation.ORIGIN;
    private CycleButton<Direction> directionButton;
    private CycleButton<ResourceKey<Level>> levelButton;

    private HashMap<String, String> issues = new HashMap<>();
    private Button onSaveWaypoint;

    public WaypointManageScreen(List<ResourceKey<Level>> worlds, CoordInputType coordInputType, TardisNavLocation tardisNavLocation) {
        super(Component.translatable(coordInputType == CoordInputType.WAYPOINT ? ModMessages.UI_MONITOR_UPLOAD_WAYPOINTS : ModMessages.UI_MONITOR_UPLOAD_COORDS)); //todo translatable
        this.worlds = worlds;
        this.coordInputType = coordInputType;
        this.tardisNavLocation = tardisNavLocation;
        tardisNavLocation.setName("Waypoint");
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        int yPosition = 30; // Move yPosition to the top of the screen
        int xPosition = this.width / 2 + 15;

        int widgetLengths = 90;
        int widgetHeight = 20;

        // Move x, y, and z sections to the top of the screen
        yPosition += 30;
        xPosition = this.width / 2 - 95;

        // Upload Data Button
        Component uploadLiteral = Component.translatable(ModMessages.UI_MONITOR_UPLOAD);
        onSaveWaypoint = this.addRenderableWidget(new Button(xPosition, yPosition + 100, this.width / 2 - 40, widgetHeight, uploadLiteral, (arg) -> {
            if (issues.isEmpty()) {
                prepareForUpload();
                new UploadWaypointMessage(tardisNavLocation, coordInputType).send();
                Minecraft.getInstance().setScreen(null);
            }
        }));

        if (coordInputType == CoordInputType.WAYPOINT) {
            this.waypointName = new EditBox(this.font, xPosition, yPosition, this.width / 2 - 40, widgetHeight, this.waypointName, Component.translatable("selectWorld.search"));
            this.waypointName.setResponder((string) -> {
                if (string.isEmpty()) {
                    issues.put("waypoint_error", "Invalid Waypoint name");
                    return;
                } else {
                    tardisNavLocation.setName(string);
                    issues.remove("waypoint_error");
                }
            });
            // Waypoint Stuff
            this.addWidget(waypointName);
            yPosition += 25;
        }


        this.xCoord = new EditBox(this.font, xPosition, yPosition, widgetLengths - 30, widgetHeight, this.xCoord, Component.translatable("selectWorld.search"));
        this.xCoord.setResponder((string) -> {
            try {
                int newValue = Integer.parseInt(string);
                tardisNavLocation.setX(newValue);
                issues.remove("x_error");
            } catch (NumberFormatException e) {
                issues.put("x_error", "Invalid X Value!");
            }
        });

        xPosition += widgetLengths - 35 + 15;

        this.yCoord = new EditBox(this.font, xPosition, yPosition, widgetLengths - 30, widgetHeight, this.yCoord, Component.translatable("selectWorld.search"));
        this.yCoord.setResponder((string) -> {
            try {
                int newValue = Integer.parseInt(string);
                tardisNavLocation.setY(newValue);
                issues.remove("y_error");
            } catch (NumberFormatException e) {
                issues.put("y_error", "Invalid Y Value!");
            }
        });

        xPosition += widgetLengths - 35 + 15;

        this.zCoord = new EditBox(this.font, xPosition, yPosition, widgetLengths - 30, widgetHeight, this.zCoord, Component.translatable("selectWorld.search"));
        this.zCoord.setResponder((string) -> {
            try {
                int newValue = Integer.parseInt(string);
                tardisNavLocation.setZ(newValue);
                issues.remove("z_error");
            } catch (NumberFormatException e) {
                issues.put("z_error", "Invalid Z Value!");
            }
        });


//Buttons
        xPosition = this.width / 2 - 95;

        // Set Dimension Button
        CycleButton.Builder<ResourceKey<Level>> dimBuilder = CycleButton.builder(dimension -> {
            tardisNavLocation.setDimensionKey(dimension);
            return Component.literal(MiscHelper.getCleanDimensionName(dimension));
        });
        dimBuilder.withValues(this.worlds);
        levelButton = dimBuilder.create(xPosition, yPosition + 100 - 45, this.width / 2 - 40, widgetHeight, Component.literal("Dimension"));
        this.addWidget(levelButton);

        // Direction Button
        Collection<Direction> directionCollection = new ArrayList<>();
        directionCollection.addAll(List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));

        CycleButton.Builder<Direction> builder = CycleButton.builder(direction -> {
            tardisNavLocation.setDirection(direction);
            return Component.literal(direction.getName().substring(0, 1).toUpperCase() + direction.getName().substring(1));
        });
        builder.withValues(directionCollection);
        directionButton = builder.create(xPosition, yPosition + 100 - 65, this.width / 2 - 40, widgetHeight, Component.literal("Direction"));
        addWidget(directionButton);


        this.addWidget(this.xCoord);
        this.addWidget(this.yCoord);
        this.addWidget(this.zCoord);

        directionButton.setValue(tardisNavLocation.getDirection());
        xCoord.setValue(String.valueOf(tardisNavLocation.getPosition().getX()));
        yCoord.setValue(String.valueOf(tardisNavLocation.getPosition().getY()));
        zCoord.setValue(String.valueOf(tardisNavLocation.getPosition().getZ()));
        levelButton.setValue(tardisNavLocation.getDimensionKey());
    }


    private void prepareForUpload() {
        tardisNavLocation.setDirection(directionButton.getValue());
        tardisNavLocation.setX(Integer.parseInt(xCoord.getValue()));
        tardisNavLocation.setY(Integer.parseInt(yCoord.getValue()));
        tardisNavLocation.setZ(Integer.parseInt(zCoord.getValue()));
        tardisNavLocation.setDimensionKey(levelButton.getValue());
    }


    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        RenderSystem.setShaderTexture(0, MONITOR_TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        this.xCoord.render(poseStack, i, j, f);
        this.yCoord.render(poseStack, i, j, f);
        this.zCoord.render(poseStack, i, j, f);
        this.levelButton.render(poseStack, i, j, f);
        this.directionButton.render(poseStack, i, j, f);

        if (coordInputType == CoordInputType.WAYPOINT) {
            this.waypointName.render(poseStack, i, j, f);
        }

        this.onSaveWaypoint.render(poseStack, i, j, f);


        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        int yPosition = this.height / 2;
        int xPosition = this.width / 2 + 10;

        int textXPosition = xPosition - 200;
        int textYPosition = leftPos - 100;

        if (issues.isEmpty()) return;
        ScreenHelper.renderWidthScaledText(ModMessages.UI_MONITOR_ISSUES, poseStack, Minecraft.getInstance().font, textXPosition - font.width(Component.translatable(ModMessages.UI_MONITOR_ISSUES).toString()) - 5, textYPosition, Color.RED.getRGB(), 100, 0.75F, false);
        textYPosition += 10;
        for (String issueKey : issues.keySet()) {
            String issueValue = issues.get(issueKey);
            ScreenHelper.renderWidthScaledText(issueValue, poseStack, Minecraft.getInstance().font, textXPosition, textYPosition, Color.RED.getRGB(), 100, 0.75F, true);
            textYPosition += 10;
        }

    }

}
