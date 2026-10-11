package whocraft.tardis_refined.client.screen.upgrades;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.tardis.upgrades.Upgrade;
import whocraft.tardis_refined.common.capability.tardis.upgrades.UpgradeHandler;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.ArrayList;
import java.util.List;

public class UpgradesScreen extends Screen {

    public static final Identifier WINDOW = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/upgrades/window.png");
    public static final Identifier OVERLAY = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/upgrades/upgrades_overlay.png");
    public static final Identifier TABS = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/upgrades/tabs.png");
    public static final Identifier BACKDROP = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/upgrades/upgrades.png");

    public static final Identifier MAIN_UPGRADE_LOCKED = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/sprites/upgrades/gallifreyan_frame_unobtained.png");
    public static final Identifier SUB_UPGRADE_LOCKED = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/sprites/upgrades/upgrade_frame_unobtained.png");
    public static final Identifier SUB_UPGRADE = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/sprites/upgrades/upgrade_frame_obtained.png");
    public static final Identifier MAIN_UPGRADE = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/sprites/upgrades/gallifreyan_frame_obtained.png");
    public static final Identifier LOCKED = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/sprites/upgrades/lock.png");
    public static final Identifier UNOBTAINED_BOX = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/sprites/upgrades/box_unobtained.png");
    public static final Identifier OBTAINED_BOX = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/sprites/upgrades/box_obtained.png");
    public static final Identifier WIDGETS = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/sprites/upgrades/widgets.png");


    public static final int WINDOW_WIDTH = 256;
    public static final int WINDOW_HEIGHT = 173;
    public static final int WINDOW_INSIDE_WIDTH = 234 - 10;
    public static final int WINDOW_INSIDE_HEIGHT = 169 - 46;
    private static final int WINDOW_INSIDE_X = 10;
    private static final int WINDOW_INSIDE_Y = 18;
    private static int tabPage;
    private static int maxPages;
    private final List<UpgradeTab> tabs = new ArrayList<>();
    private final UpgradeHandler upgradeHandler;
    @Nullable
    public UpgradeTab selectedTab;
    public Screen overlayScreen = null;
    private boolean isScrolling;
    private boolean dragMoved;
    @Nullable
    private Upgrade pendingUpgradeClick;

    public UpgradesScreen(UpgradeHandler upgradeHandler) {
        super(Component.empty());
        this.upgradeHandler = upgradeHandler;
    }

    public static boolean isPotentialParentUnlocked(Upgrade upgrade, UpgradeHandler upgradeHandler) {
        if (upgrade.getParent() == null) {
            return true;
        }
        return upgrade.getParent().isUnlocked(upgradeHandler);
    }

    public static Identifier getFrame(Upgrade.UpgradeType upgradeType, boolean isUnlocked) {
        if (isUnlocked) {
            return upgradeType == Upgrade.UpgradeType.MAIN_UPGRADE ? MAIN_UPGRADE : SUB_UPGRADE;
        }
        return upgradeType == Upgrade.UpgradeType.MAIN_UPGRADE ? MAIN_UPGRADE_LOCKED : SUB_UPGRADE_LOCKED;
    }

    public static Identifier getBox(boolean isUnlocked) {
        return isUnlocked ? OBTAINED_BOX : UNOBTAINED_BOX;
    }

    @Override
    protected void init() {
        this.tabs.clear();
        this.selectedTab = null;

        UpgradeTab upgradeTab = UpgradeTab.create(this.minecraft, this, 0, upgradeHandler);
        this.tabs.add(upgradeTab);

        if (this.tabs.size() > UpgradeTabType.MAX_TABS) {
            int guiLeft = (this.width - WINDOW_WIDTH) / 2;
            int guiTop = (this.height - WINDOW_HEIGHT) / 2;
            this.addRenderableWidget(Button.builder(Component.literal("<"), (b) -> {
                tabPage = Math.max(tabPage - 1, 0);
            }).bounds(guiLeft, guiTop - 50, 20, 20).build());
            this.addRenderableWidget(Button.builder(Component.literal(">"), (b) -> {
                tabPage = Math.min(tabPage + 1, maxPages);
            }).bounds(guiLeft + WINDOW_WIDTH - 20, guiTop - 50, 20, 20).build());
            maxPages = this.tabs.size() / UpgradeTabType.MAX_TABS;
        }

        if (!this.tabs.isEmpty()) {
            this.selectedTab = tabs.get(0);
        }

        if (this.overlayScreen != null) {
            this.overlayScreen.init(this.width, this.height);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();
        int i = (this.width - WINDOW_WIDTH) / 2;
        int j = (this.height - WINDOW_HEIGHT) / 2;
        TardisRefined.LOGGER.info("Upgrade tree mouse event: button={}, mouse=({},{}), window=({},{}), screenSize=({},{}), selectedTab={}, overlay={}",
                event.button(), mouseX, mouseY, i, j, this.width, this.height,
                this.selectedTab == null ? "none" : this.selectedTab.getTitle(), this.overlayScreen != null);

        // Some input setups report the secondary mouse button for this GUI action;
        // treat either primary button as selecting a node.
        if (event.button() == 0 || event.button() == 1) {

            if (this.isOverOverlayScreen(mouseX, mouseY)) {
                return this.overlayScreen.mouseClicked(event, doubleClick);
            } else {
                for (UpgradeTab powerTab : this.tabs) {
                    if (powerTab.isMouseOver(i, j, mouseX, mouseY)) {
                        this.selectedTab = powerTab;
                        break;
                    }
                }

                boolean insideTree = mouseX >= i + WINDOW_INSIDE_X
                        && mouseX < i + WINDOW_INSIDE_X + WINDOW_INSIDE_WIDTH
                        && mouseY >= j + WINDOW_INSIDE_Y
                        && mouseY < j + WINDOW_INSIDE_Y + WINDOW_INSIDE_HEIGHT;
                if (selectedTab != null && insideTree) {
                    // UpgradeTab content is rendered from (windowLeft + 10, windowTop + 18).
                    // Use floor so fractional GUI coordinates map to the same pixel as rendering.
                    UpgradeWidget entry = this.selectedTab.getUpgradeHoveredOver(
                            Mth.floor(mouseX - i - WINDOW_INSIDE_X),
                            Mth.floor(mouseY - j - WINDOW_INSIDE_Y), i, j);

                    TardisRefined.LOGGER.info("Upgrade tree hit test: local=({},{}), matched={}",
                            Mth.floor(mouseX - i - WINDOW_INSIDE_X), Mth.floor(mouseY - j - WINDOW_INSIDE_Y),
                            entry == null ? "none" : entry.upgradeEntry);

                    this.pendingUpgradeClick = entry != null && !entry.upgradeEntry.isUnlocked(upgradeHandler)
                            ? entry.upgradeEntry : null;
                    this.isScrolling = true;
                    this.dragMoved = false;
                    // Screen only receives drag callbacks after its container handler
                    // has been marked as dragging by the initiating press.
                    this.setDragging(true);
                    return true;
                }
            }
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        return this.overlayScreen == null ? super.keyPressed(event) : this.overlayScreen.keyPressed(event);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.overlayScreen != null) {
            return this.overlayScreen.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        int windowLeft = (this.width - WINDOW_WIDTH) / 2;
        int windowTop = (this.height - WINDOW_HEIGHT) / 2;
        boolean insideTree = mouseX >= windowLeft + WINDOW_INSIDE_X
                && mouseX < windowLeft + WINDOW_INSIDE_X + WINDOW_INSIDE_WIDTH
                && mouseY >= windowTop + WINDOW_INSIDE_Y
                && mouseY < windowTop + WINDOW_INSIDE_Y + WINDOW_INSIDE_HEIGHT;

        if (this.selectedTab != null && insideTree) {
            // Nodes are arranged on a 30px grid. Wheel movement by one grid unit
            // makes the remaining upgrade rows reachable while preserving drag scroll.
            this.selectedTab.scroll(scrollX * UpgradeTab.GRID_SIZE, scrollY * UpgradeTab.GRID_SIZE);
            return true;
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int i = (this.width - WINDOW_WIDTH) / 2;
        int j = (this.height - WINDOW_HEIGHT) / 2;
        guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
//        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        this.renderWindow(guiGraphics, i, j);
        this.renderInside(guiGraphics, mouseX, mouseY, i, j);

        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        if (this.selectedTab != null && this.overlayScreen != null) {
            guiGraphics.nextStratum();
            this.overlayScreen.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
            this.selectedTab.fade = Mth.clamp(this.selectedTab.fade + 0.02F, 0, 0.5F);
            guiGraphics.nextStratum();
        }


        this.renderFinalOverlay(guiGraphics, i, j);
        this.renderTooltips(guiGraphics, mouseX, mouseY, i, j);

        if (upgradeHandler.getUpgradePoints() > 0) {
            guiGraphics.text(this.minecraft.font, "Points: " + upgradeHandler.getUpgradePoints(), width / 2 - font.width("Points: " + upgradeHandler.getUpgradePoints()) / 2, j + WINDOW_HEIGHT - 15, 0xFF000000, false);
        }

        String xpText = "XP: " + upgradeHandler.getUpgradeXP() + " / " + UpgradeHandler.XP_PER_UPGRADE_POINT;
        guiGraphics.text(this.minecraft.font, xpText, width / 2 - font.width(xpText) / 2, j + 6, 0xFF000000, false);


    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        // In this Minecraft input API the primary mouse button is 1 (the
        // container event handler uses the same value when capturing drags).
        if ((event.button() != 0 && event.button() != 1) || !this.isScrolling) {
            return false;
        }
        if (dragX != 0 || dragY != 0) {
            this.dragMoved = true;
            this.pendingUpgradeClick = null;
            if (this.selectedTab != null) this.selectedTab.scroll(dragX, dragY);
        }
        return true;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if ((event.button() == 0 || event.button() == 1) && this.isScrolling) {
            Upgrade clickedUpgrade = this.pendingUpgradeClick;
            this.isScrolling = false;
            this.dragMoved = false;
            this.pendingUpgradeClick = null;
            this.setDragging(false);
            if (clickedUpgrade != null) {
                boolean available = isPotentialParentUnlocked(clickedUpgrade, upgradeHandler)
                        && upgradeHandler.getUpgradePoints() >= clickedUpgrade.getSkillPointsRequired();
                this.openOverlayScreen(new BuyUpgradeScreen(clickedUpgrade, available, this));
                TardisRefined.LOGGER.info("Upgrade tree click opened unlock prompt: upgrade={}, available={}, points={}",
                        clickedUpgrade, available, upgradeHandler.getUpgradePoints());
            }
            return true;
        }
        return super.mouseReleased(event);
    }

    private void renderInside(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, int offsetX, int offsetY) {
        UpgradeTab tab = this.selectedTab;
        if (tab == null) {
            guiGraphics.fill(offsetX + WINDOW_INSIDE_X, offsetY + WINDOW_INSIDE_Y, offsetX + WINDOW_INSIDE_X + WINDOW_INSIDE_WIDTH, offsetY + WINDOW_INSIDE_Y + WINDOW_INSIDE_HEIGHT, -16777216);
        } else {
            tab.drawContents(guiGraphics, offsetX + WINDOW_INSIDE_X, offsetY + WINDOW_INSIDE_Y);
        }
    }

    public void renderWindow(GuiGraphicsExtractor guiGraphics, int offsetX, int offsetY) {
        // These images are 256x256. Keep the GUI-space window at 256x173 while
        // using the real texture dimensions for UV normalization.
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKDROP, offsetX, offsetY, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 256, 256);
        if (this.tabs.size() > 1) {

            for (UpgradeTab tab : this.tabs) {
                tab.drawTab(guiGraphics, offsetX, offsetY, tab == this.selectedTab);
            }


            for (UpgradeTab tab : this.tabs) {
                tab.drawIcon(guiGraphics, offsetX, offsetY);
            }

        }
    }

    public void renderFinalOverlay(GuiGraphicsExtractor guiGraphics, int offsetX, int offsetY) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, OVERLAY, offsetX, offsetY, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 256, 256);

    }

    private void renderTooltips(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, int offsetX, int offsetY) {
        if (this.selectedTab != null) {
            // UpgradeTab draws in content-local coordinates; restore the panel origin
            // that the 1.20.2 screen applied before extracting tooltip geometry.
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate((float) (offsetX + WINDOW_INSIDE_X), (float) (offsetY + WINDOW_INSIDE_Y));
            guiGraphics.nextStratum();
            this.selectedTab.drawTooltips(guiGraphics, mouseX - offsetX - WINDOW_INSIDE_X, mouseY - offsetY - WINDOW_INSIDE_Y, offsetX, offsetY, this.overlayScreen != null);
            guiGraphics.pose().popMatrix();

        }

        if (this.tabs.size() > 1) {
            for (UpgradeTab tab : this.tabs) {
                if (tab.isMouseOver(offsetX, offsetY, mouseX, mouseY)) {
                    guiGraphics.setTooltipForNextFrame(this.font, tab.getTitle(), mouseX, mouseY);
                }
            }
        }
    }

    public void closeOverlayScreen() {
        this.overlayScreen = null;
    }

    public void openOverlayScreen(Screen screen) {
        this.closeOverlayScreen();
        this.overlayScreen = screen;
        this.overlayScreen.init(this.width, this.height);
    }

    public boolean isOverOverlayScreen(double mouseX, double mouseY) {
        return overlayScreen != null;
    }


}
