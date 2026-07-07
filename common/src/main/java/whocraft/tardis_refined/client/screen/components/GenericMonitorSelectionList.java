package whocraft.tardis_refined.client.screen.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import org.jetbrains.annotations.NotNull;

public class GenericMonitorSelectionList<T extends ObjectSelectionList.Entry<T>> extends ObjectSelectionList<T> {

    private boolean renderBackground = true;

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
        super(minecraft, width, height, yStart, itemHeight); //Don't add anything to the y1 variable otherwise the entry button will be slighter taller than expected
        setX(xLeftPos);
        this.setRenderHeader(false, 0);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.getX() - 6;
    }

    @Override
    protected void renderListBackground(@NotNull GuiGraphics guiGraphics) {
        if (renderBackground) {
            super.renderListBackground(guiGraphics);
        }
    }

    @Override
    protected void renderListSeparators(GuiGraphics guiGraphics) {

    }

    @Override
    protected void renderSelection(GuiGraphics guiGraphics, int i, int j, int k, int l, int m) {

    }

    public void setRenderBackground(boolean renderBackground) {
        this.renderBackground = renderBackground;
    }


}


