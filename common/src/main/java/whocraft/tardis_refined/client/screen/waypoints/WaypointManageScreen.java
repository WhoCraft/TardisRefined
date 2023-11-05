package whocraft.tardis_refined.client.screen.waypoints;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.ScreenHelper;
import whocraft.tardis_refined.client.screen.components.CommonTRWidgets;
import whocraft.tardis_refined.client.screen.selections.SelectionScreen;
import whocraft.tardis_refined.common.network.messages.waypoints.UploadWaypointMessage;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.MiscHelper;

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
    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/gui/monitor.png");

    private TardisNavLocation tardisNavLocation = TardisNavLocation.ORIGIN;
    private CycleButton<Direction> directionButton;
    private CycleButton<ResourceKey<Level>> levelButton;

    private HashMap<String, String> issues = new HashMap<>();
    private SpriteIconButton onSubmitButton, onSaveWaypoint;

    public WaypointManageScreen(List<ResourceKey<Level>> worlds, CoordInputType coordInputType, TardisNavLocation tardisNavLocation) {
        super(Component.translatable("waypoints")); //todo translatable
        this.worlds = worlds;
        this.coordInputType = coordInputType;
        this.tardisNavLocation = tardisNavLocation;
        tardisNavLocation.setName("Placeholder");
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        int yPosition = leftPos + 34 + 15;
        int xPosition = this.width / 2 + 15;

        int widgetLengths = 90;

        int widgetHeight = 17;

        int uploadTextWidth = Minecraft.getInstance().font.width("Upload") * 2;


        if (coordInputType == CoordInputType.WAYPOINT) {
            onSaveWaypoint = this.addRenderableWidget(CommonTRWidgets.imageButton(uploadTextWidth, Component.translatable("Save"), (arg) -> {
                if (issues.isEmpty()) {
                    new UploadWaypointMessage(tardisNavLocation, coordInputType).send();
                    Minecraft.getInstance().setScreen(null);
                }
            }, false, SelectionScreen.BUTTON_LOCATION));
            onSaveWaypoint.setPosition(width / 2 - 90, yPosition);
            onSaveWaypoint.setHeight(widgetHeight);
            addWidget(onSaveWaypoint);
        } else {
            onSubmitButton = this.addRenderableWidget(CommonTRWidgets.imageButton(uploadTextWidth, Component.translatable("Upload"), (arg) -> {
                new UploadWaypointMessage(tardisNavLocation, coordInputType).send();
            }, false, SelectionScreen.BUTTON_LOCATION));
            onSubmitButton.setPosition(width / 2 - 90, yPosition);
            onSubmitButton.setHeight(widgetHeight);
            addWidget(onSubmitButton);
        }


        Collection<Direction> directionCollection = new ArrayList<>();
        directionCollection.addAll(List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));


        CycleButton.Builder<Direction> builder = CycleButton.builder(direction -> {
            tardisNavLocation.setDirection(direction);
            return Component.literal(direction.getName().substring(0, 1).toUpperCase() + direction.getName().substring(1));
        });
        builder.withValues(directionCollection);
        directionButton = builder.create(xPosition, yPosition, widgetLengths, widgetHeight, Component.literal("Direction"));
        addWidget(directionButton);

        directionButton.setValue(tardisNavLocation.getDirection());

        yPosition -= 20;

        CycleButton.Builder<ResourceKey<Level>> dimBuilder = CycleButton.builder(dimension -> {
            tardisNavLocation.setDimensionKey(dimension);
            return Component.literal(MiscHelper.getCleanDimensionName(dimension));
        });
        dimBuilder.withValues(this.worlds);
        levelButton = dimBuilder.create(xPosition, yPosition, widgetLengths, widgetHeight, Component.literal("Level"));
        levelButton.setValue(tardisNavLocation.getDimensionKey());
        this.addWidget(levelButton);


        yPosition -= 20;
        this.zCoord = new EditBox(this.font, xPosition, yPosition, widgetLengths, widgetHeight, this.zCoord, Component.translatable("selectWorld.search"));
        this.zCoord.setResponder((string) -> {
            try {
                int newValue = Integer.parseInt(string);
                tardisNavLocation.setZ(newValue);
                issues.remove("z_error");
            } catch (NumberFormatException e) {
                issues.put("z_error", "Invalid Z Value!");
            }
        });

        zCoord.setValue(String.valueOf(tardisNavLocation.getPosition().getZ()));

        yPosition -= 20;
        this.yCoord = new EditBox(this.font, xPosition, yPosition, widgetLengths, widgetHeight, this.yCoord, Component.translatable("selectWorld.search"));
        this.yCoord.setResponder((string) -> {
            try {
                int newValue = Integer.parseInt(string);
                tardisNavLocation.setY(newValue);
                issues.remove("y_error");
            } catch (NumberFormatException e) {
                issues.put("y_error", "Invalid Y Value!");
            }
        });

        yCoord.setValue(String.valueOf(tardisNavLocation.getPosition().getY()));


        yPosition -= 20;
        this.xCoord = new EditBox(this.font, xPosition, yPosition, widgetLengths, widgetHeight, this.xCoord, Component.translatable("selectWorld.search"));
        this.xCoord.setResponder((string) -> {
            try {
                int newValue = Integer.parseInt(string);
                tardisNavLocation.setX(newValue);
                issues.remove("x_error");
            } catch (NumberFormatException e) {
                issues.put("x_error", "Invalid X Value!");
            }
        });

        xCoord.setValue(String.valueOf(tardisNavLocation.getPosition().getX()));


        if (coordInputType == CoordInputType.WAYPOINT) {
            yPosition -= 20;
            this.waypointName = new EditBox(this.font, xPosition, yPosition, widgetLengths, widgetHeight, this.waypointName, Component.translatable("selectWorld.search"));
            this.waypointName.setResponder((string) -> {
                if (string.isEmpty()) {
                    issues.put("waypoint_error", "Invalid Waypoint name");
                    return;
                } else {
                    tardisNavLocation.setName(string);
                    issues.remove("waypoint_error");
                }
            });
            //Waypoint Stuff
            this.addWidget(waypointName);
        }

        this.addWidget(this.xCoord);
        this.addWidget(this.yCoord);
        this.addWidget(this.zCoord);


    }


    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderTransparentBackground(guiGraphics);
        super.render(guiGraphics, i, j, f);
        guiGraphics.blit(MONITOR_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        this.xCoord.render(guiGraphics, i, j, f);
        this.yCoord.render(guiGraphics, i, j, f);
        this.zCoord.render(guiGraphics, i, j, f);
        this.levelButton.render(guiGraphics, i, j, f);
        this.directionButton.render(guiGraphics, i, j, f);

        if (coordInputType == CoordInputType.WAYPOINT) {
            this.waypointName.render(guiGraphics, i, j, f);
            this.onSaveWaypoint.render(guiGraphics, i, j, f);
        } else {
            this.onSubmitButton.render(guiGraphics, i, j, f);
        }
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        int yPosition = this.height / 2;
        int xPosition = this.width / 2 + 10;
        int textScale = 200;


        ScreenHelper.renderWidthScaledText("Z", guiGraphics, Minecraft.getInstance().font, xPosition, yPosition, Color.LIGHT_GRAY.getRGB(), textScale * 2, 0.75F, true);

        yPosition -= 20;
        ScreenHelper.renderWidthScaledText("Y", guiGraphics, Minecraft.getInstance().font, xPosition, yPosition, Color.LIGHT_GRAY.getRGB(), textScale * 2, 0.75F, true);

        yPosition -= 20;
        ScreenHelper.renderWidthScaledText("X", guiGraphics, Minecraft.getInstance().font, xPosition, yPosition, Color.LIGHT_GRAY.getRGB(), textScale * 2, 0.75F, true);


        yPosition -= 20;
        if (CoordInputType.WAYPOINT == coordInputType) {
            ScreenHelper.renderWidthScaledText("Waypoint name:", guiGraphics, Minecraft.getInstance().font, xPosition - font.width("Waypoint name:") + 21, yPosition, Color.LIGHT_GRAY.getRGB(), textScale * 2, 0.75F, false);
        }

        int textXPosition = xPosition - 60;
        int textYPosition = yPosition - 5;

        if (issues.isEmpty()) return;
        ScreenHelper.renderWidthScaledText("Issues: ", guiGraphics, Minecraft.getInstance().font, textXPosition - Minecraft.getInstance().font.width("Issues: ") - 5, textYPosition, Color.RED.getRGB(), 100, 0.75F, false);
        textYPosition += 10;
        for (String issueKey : issues.keySet()) {
            String issueValue = issues.get(issueKey);
            ScreenHelper.renderWidthScaledText(issueValue, guiGraphics, Minecraft.getInstance().font, textXPosition, textYPosition, Color.RED.getRGB(), 100, 0.75F, true);
            textYPosition += 10;
        }

    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        return super.keyPressed(i, j, k);
    }

    @Override
    public boolean charTyped(char c, int i) {
        return super.charTyped(c, i);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {

    }

}
