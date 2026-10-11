package whocraft.tardis_refined.client.screen.components;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class SelectionListEntry extends ObjectSelectionList.Entry<SelectionListEntry> {

    private final Component itemDisplayName;
    private final GenericListSelection press;
    private boolean checked = false;
    private boolean enabled = true;
    private int listLeft;
    private Component tooltip;

    /**
     * @param name
     * @param onSelection
     * @param listLeft    - the left position for the master {@link GenericMonitorSelectionList} so we can left align our text
     */
    public SelectionListEntry(Component name, GenericListSelection onSelection, int listLeft) {
        this.itemDisplayName = name;
        this.press = onSelection;
        this.listLeft = listLeft;
    }

    /**
     * @param name
     * @param onSelection
     * @param listLeft    - the left position for the master {@link GenericMonitorSelectionList} so we can left align our text
     * @param enabled     - Should the functionality of the button work?
     */
    public SelectionListEntry(Component name, GenericListSelection onSelection, int listLeft, boolean enabled) {
        this.itemDisplayName = name;
        this.press = onSelection;
        this.listLeft = listLeft;
        this.enabled = enabled;
    }

    public void setTooltip(Component tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public Component getNarration() {
        return itemDisplayName;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (enabled) {
            press.onClick(this);
        }
        return super.mouseClicked(event, doubleClick);
    }

    /**
     * @param guiGraphics
     * @param index
     * @param top         - the top most y position for this particular entry's row from getRowTop
     * @param left        - the left most x position for this particular entry's row in the list from getRowLeft
     * @param width       - width for this particular entry's row
     * @param height      - height for this particular entry's row
     * @param mouseX
     * @param mouseY
     * @param isMouseOver
     * @param partialTick
     */
    @Override
    public void extractContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
        int top = this.getY();
        int left = this.getX();
        int width = this.getWidth();
        int height = this.getHeight();
        int rgb = isMouseOver ? 0xFFFF55 :
                (this.checked ? 0xFFFF55 :
                        this.itemDisplayName.getStyle().getColor() != null ?
                                this.itemDisplayName.getStyle().getColor().getValue() :
                                0xFFAA00);
        int colour = 0xFF000000 | (rgb & 0xFFFFFF);
        Component text = Component.literal((this.checked ? "> " : "") + this.itemDisplayName.getString());
        this.renderText(guiGraphics, 0, top, left, width, height, mouseX, mouseY, isMouseOver, partialTick, text, this.enabled ? colour : 0xFF555555);

        // Render tooltip if mouse is over
        if (isMouseOver && this.tooltip != null) {
            renderTooltip(guiGraphics, mouseX, mouseY);
        }
    }

    private void renderTooltip(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gui.screen() != null) {
            guiGraphics.setTooltipForNextFrame(minecraft.font, this.tooltip, mouseX, mouseY);
        }
    }

    public void renderText(GuiGraphicsExtractor guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick, Component text, int textColour) {
        int heightCentre = top + height / 2;
        int xPos = this.listLeft + 2;
        int yPos = heightCentre - 9 / 2;
        guiGraphics.text(Minecraft.getInstance().font, text, xPos, yPos, textColour);
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
