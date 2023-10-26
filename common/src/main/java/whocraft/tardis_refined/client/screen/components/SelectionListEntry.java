package whocraft.tardis_refined.client.screen.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public class SelectionListEntry extends ObjectSelectionList.Entry<SelectionListEntry> {

    private final Component itemDisplayName;
    private final GenericListSelection press;
    private boolean checked = false;

    private int listLeft;

    /**
     *
     * @param name
     * @param onSelection
     * @param listLeft - the left position for the master {@link GenericMonitorSelectionList} so we can left align our text
     */
    public SelectionListEntry(Component name, GenericListSelection onSelection, int listLeft) {
        this.itemDisplayName = name;
        this.press = onSelection;
        this.listLeft = listLeft;
    }

    @Override
    public Component getNarration() {
        return itemDisplayName;
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        press.onClick(this);
        return super.mouseClicked(d, e, i);
    }

    /**
     *
     * @param guiGraphics
     * @param index
     * @param top - the top most y position for this particular entry's row from getRowTop
     * @param left - the left most x position for this particular entry's row in the list from getRowLeft
     * @param width - width for this particular entry's row
     * @param height - height for this particular entry's row
     * @param mouseX
     * @param mouseY
     * @param isMouseOver
     * @param partialTick
     */
    @Override
    public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
        int colour = isMouseOver ? ChatFormatting.YELLOW.getColor() : (this.checked ? ChatFormatting.YELLOW.getColor() :  this.itemDisplayName.getStyle().getColor() != null ? this.itemDisplayName.getStyle().getColor().getValue() : ChatFormatting.GOLD.getColor());
        Component text = Component.literal((this.checked ? "> " : "") + this.itemDisplayName.getString());
        this.renderText(guiGraphics, index, top, left, width, height, mouseX, mouseY, isMouseOver, partialTick, text, colour);
    }

    public void renderText(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick, Component text, int textColour){
        int textWidth = Minecraft.getInstance().font.width(text);

        /*
        Centre aligned option
        int heightCentre = top + height / 2;
        int leftMax = left + width - 8; //Add some borders between sides of the scrollbar
        int leftCentre = (left + leftMax - textWidth) / 2; //Get start position for each text to render as centre aligned, taking into account of the text width
        int xPos = leftCentre;
        int yPos = heightCentre - 9 / 2;
         */

        /* Left Aligned */
        int heightCentre = top + height / 2;
        int xPos = this.listLeft + 2;
        int yPos = heightCentre - 9 / 2;
        guiGraphics.drawString(Minecraft.getInstance().font, text, xPos, yPos, textColour);
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}