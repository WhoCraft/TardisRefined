package whocraft.tardis_refined.client.screen.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;

public class GenericMonitorSelectionList<T extends ObjectSelectionList.Entry<T>> extends ObjectSelectionList<T> {
    /**
     * Creates a scrollable list with entries defined by a separate class
     *
     * @param minecraft
     * @param width
     * @param height
     * @param xLeftPos   - the x coordinate for the start position of the scrollable list area
     * @param yStart     - the y coordinate for the top of the scrollable list area
     * @param yEnd       - the y coordinate for the bottom of the scrollable list area
     * @param itemHeight - height of each item in the list
     */
    public GenericMonitorSelectionList(Minecraft minecraft, int width, int height, int xLeftPos, int yStart, int yEnd, int itemHeight) {
        super(minecraft, width, yEnd - yStart, yStart, itemHeight);
        this.updateSizeAndPosition(width, yEnd - yStart, xLeftPos, yStart);
    }

    public void addEntryPublic(T entry) {
        super.addEntry(entry);
    }

    @Override
    protected void extractListSeparators(GuiGraphicsExtractor guiGraphics) {
    }

    @Override
    protected void extractSelection(GuiGraphicsExtractor guiGraphics, T entry, int top) {
    }


}
