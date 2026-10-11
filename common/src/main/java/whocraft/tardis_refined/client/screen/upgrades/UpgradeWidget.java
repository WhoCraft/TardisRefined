package whocraft.tardis_refined.client.screen.upgrades;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.advancements.AdvancementWidgetType;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.tardis.upgrades.Upgrade;
import whocraft.tardis_refined.common.capability.tardis.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.registry.TRUpgrades;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class UpgradeWidget {


    private static final int[] TEST_SPLIT_OFFSETS = new int[]{0, 10, -10, 25, -25};
    private static final Identifier TITLE_BOX_SPRITE = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/sprites/title_box.png");
    public final Upgrade upgradeEntry;
    private final UpgradeTab tab;
    private final UpgradeHandler upgradeHandler;
    private final FormattedCharSequence title;
    private final int width;
    private final List<FormattedCharSequence> description;
    private final Minecraft minecraft;
    public double gridX, gridY;
    public boolean fixedPosition = false;
    List<UpgradeWidget> parents = new LinkedList<>();
    List<UpgradeWidget> children = new LinkedList<>();
    private int x;
    private int y;

    public UpgradeWidget(UpgradeTab tab, Minecraft mc, UpgradeHandler upgradeHandler, Upgrade upgradeEntry) {
        this.tab = tab;
        this.upgradeHandler = upgradeHandler;
        this.upgradeEntry = upgradeEntry;
        this.minecraft = mc;
        this.title = Language.getInstance().getVisualOrder(mc.font.substrByWidth(upgradeEntry.getDisplayName(), 163));
        int l = 29 + mc.font.width(this.title);
        var description = upgradeEntry.getDisplayDescription();
        this.description = Language.getInstance()
                .getVisualOrder(
                        this.findOptimalLines(ComponentUtils.mergeStyles(description != null ? description.copy() : Component.empty(), Style.EMPTY.withColor(ChatFormatting.WHITE)), l)
                );



        for (FormattedCharSequence formattedCharSequence : this.description) {
            l = Math.max(l, minecraft.font.width(formattedCharSequence));
        }

        this.width = l + 3 + 5;
    }

    private static float getMaxWidth(StringSplitter manager, List<FormattedText> text) {
        return (float) text.stream().mapToDouble(manager::stringWidth).max().orElse(0.0);
    }

    public UpgradeWidget updatePosition(double x, double y, UpgradeTab tab) {
        this.gridX = x;
        this.gridY = y;
        this.x = (int) (x * UpgradeTab.GRID_SIZE) - 16;
        this.y = (int) (tab.getFreeYPos(x, y) * UpgradeTab.GRID_SIZE) - 13;

        for (UpgradeWidget child : this.children) {
            child.updatePosition(this.gridX + 1, y, tab);
        }

        return this;
    }

    public UpgradeWidget setPosition(double x, double y) {
        this.gridX = x;
        this.gridY = y;
        this.x = (int) (x * UpgradeTab.GRID_SIZE) - 16;
        this.y = (int) (y * UpgradeTab.GRID_SIZE) - 13;
        return this;
    }

    public UpgradeWidget setPositionFixed(double x, double y) {
        this.fixedPosition = true;
        return this.setPosition(x, y);
    }

    public UpgradeWidget updateRelatives(Collection<UpgradeWidget> list) {
        this.parents.clear();
        this.children.clear();

        Upgrade parent = this.upgradeEntry.getParent();
        List<Upgrade> children = this.upgradeEntry.getDirectChildren();

        for (UpgradeWidget widget : list) {
            if (parent != null && widget.upgradeEntry == parent) {
                this.parents.add(widget);
            }

            if (!children.isEmpty()) {
                if (children.contains(widget.upgradeEntry)) {
                    this.children.add(widget);
                }
            }
        }

        return this;
    }

    private List<FormattedText> findOptimalLines(Component component, int maxWidth) {
        StringSplitter stringSplitter = this.minecraft.font.getSplitter();
        List<FormattedText> list = null;
        float f = Float.MAX_VALUE;

        for (int i : TEST_SPLIT_OFFSETS) {
            List<FormattedText> list2 = stringSplitter.splitLines(component, maxWidth - i, Style.EMPTY);
            float g = Math.abs(getMaxWidth(stringSplitter, list2) - (float) maxWidth);
            if (g <= 10.0F) {
                return list2;
            }

            if (g < f) {
                f = g;
                list = list2;
            }
        }

        return list;
    }

    public void drawDisplayIcon(Minecraft mc, GuiGraphicsExtractor guiGraphics, int x, int y) {
        if (this.upgradeEntry.isUnlocked(upgradeHandler)) {
            guiGraphics.fakeItem(this.upgradeEntry.getIcon(), x, y);
        } else {


            //Display Lock
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, UpgradesScreen.LOCKED, x - 5, y - 5, 0, 0, 26, 26, 26, 26);
        }
    }

    public void drawIcon(Minecraft mc, GuiGraphicsExtractor guiGraphics, int x, int y) {


        boolean isUnlocked = this.upgradeEntry.isUnlocked(upgradeHandler);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, UpgradesScreen.getFrame(upgradeEntry.getUpgradeType(), isUnlocked), x - 13, y - 13, 0, 0, 26, 26, 26, 26);
        this.drawDisplayIcon(mc, guiGraphics, x - 8, y - 8);
    }

    public int getWidth() {
        return this.width;
    }

    public void drawHover(GuiGraphicsExtractor guiGraphics, int x, int y, float fade, int width, int height) {
        boolean bl = width + x + this.x + this.width + 26 >= this.tab.getScreen().width;
        String string = null;
        int i = string == null ? 0 : this.minecraft.font.width(string);
        boolean bl2 = 113 - y - this.y - 26 <= 6 + this.description.size() * 9;
        float f = 0.0F;
        int j = Mth.floor(f * (float) this.width);
        AdvancementWidgetType advancementWidgetType;
        AdvancementWidgetType advancementWidgetType2;
        AdvancementWidgetType advancementWidgetType3;
        if (f >= 1.0F) {
            j = this.width / 2;
            advancementWidgetType = AdvancementWidgetType.OBTAINED;
            advancementWidgetType2 = AdvancementWidgetType.OBTAINED;
            advancementWidgetType3 = AdvancementWidgetType.OBTAINED;
        } else if (j < 2) {
            j = this.width / 2;
            advancementWidgetType = AdvancementWidgetType.UNOBTAINED;
            advancementWidgetType2 = AdvancementWidgetType.UNOBTAINED;
            advancementWidgetType3 = AdvancementWidgetType.UNOBTAINED;
        } else if (j > this.width - 2) {
            j = this.width / 2;
            advancementWidgetType = AdvancementWidgetType.OBTAINED;
            advancementWidgetType2 = AdvancementWidgetType.OBTAINED;
            advancementWidgetType3 = AdvancementWidgetType.UNOBTAINED;
        } else {
            advancementWidgetType = AdvancementWidgetType.OBTAINED;
            advancementWidgetType2 = AdvancementWidgetType.UNOBTAINED;
            advancementWidgetType3 = AdvancementWidgetType.UNOBTAINED;
        }

        int k = this.width - j;


        int l = y + this.y;
        int m;
        if (bl) {
            m = x + this.x - this.width + 26 + 6;
        } else {
            m = x + this.x;
        }

        int n = 32 + this.description.size() * 17;
        if (!this.description.isEmpty()) {
            if (bl2) {
                guiGraphics.fill(m + 2, l + 26 - n, m + 2 + this.width, l + 26, 0xFF202020);
            } else {
                guiGraphics.fill(m, l, m + this.width, l + n, 0xFF202020);
            }
        }

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, advancementWidgetType.boxSprite(), m, l, j, 26);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, advancementWidgetType2.boxSprite(), m + j, l, k, 26);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, UpgradesScreen.getFrame(upgradeEntry.getUpgradeType(), upgradeEntry.isUnlocked(upgradeHandler)), x + this.x + 3, y + this.y, 0, 0, 26, 26, 26, 26);

        if (bl) {
            guiGraphics.text(this.minecraft.font, this.title, m + 5, y + this.y + 9, -1);
            if (string != null) {
                guiGraphics.text(this.minecraft.font, string, x + this.x - i, y + this.y + 9, -1);
            }
        } else {
            guiGraphics.text(this.minecraft.font, this.title, x + this.x + 32, y + this.y + 9, -1);
            if (string != null) {
                guiGraphics.text(this.minecraft.font, string, x + this.x + this.width - i - 5, y + this.y + 9, -1);
            }
        }

        if (bl2) {
            for (int o = 0; o < this.description.size(); ++o) {
                guiGraphics.text(this.minecraft.font, this.description.get(o), m + 5, l + 26 - n + 7 + o * 9, -5592406);
            }
            Identifier key = TRUpgrades.UPGRADE_DEFERRED_REGISTRY.getKey(upgradeEntry);
            String owner = Platform.getModName(key.getNamespace());
            guiGraphics.text(this.minecraft.font, ChatFormatting.BLUE + owner, m + 5, l + 26 + 9, -1);
        } else {
            for (int o = 0; o < this.description.size(); ++o) {
                guiGraphics.text(this.minecraft.font, this.description.get(o), m + 5, y + this.y + 9 + 17 + o * 9, -5592406);
            }
            Identifier key = TRUpgrades.UPGRADE_DEFERRED_REGISTRY.getKey(upgradeEntry);
            String owner = Platform.getModName(key.getNamespace());
            guiGraphics.text(this.minecraft.font, ChatFormatting.BLUE +owner, m + 5, y + this.y + 9 + 17 + this.description.size() * 9 + 5, -1);
        }

        this.drawDisplayIcon(this.minecraft, guiGraphics, x + this.x + 8, y + this.y + 5);
    }


    public boolean isMouseOver(int x, int y, int mouseX, int mouseY) {
        // Match the rendered frame: drawIcon places its 26px frame at widget x + 3.
        int i = x + this.x + 3;
        int j = i + 26;
        int k = y + this.y;
        int l = k + 26;
        return mouseX >= i && mouseX <= j && mouseY >= k && mouseY <= l;
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

}
