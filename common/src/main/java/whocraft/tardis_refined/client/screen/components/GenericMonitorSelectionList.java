package whocraft.tardis_refined.client.screen.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.navigation.ScreenDirection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        super(minecraft, width, height, yStart, yEnd, itemHeight); //Don't add anything to the y1 variable otherwise the entry button will be slighter taller than expected
        this.setLeftPos(xLeftPos);
        this.setRenderHeader(false, 0);

        this.setRenderTopAndBottom(false);
        this.setRenderSelection(false);

        this.setRenderBackground(true);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.x1 - 6;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    @Nullable
    protected T nextEntry(@NotNull ScreenDirection direction) {
        return this.nextEntry(direction, KeyboardSelectionAware::isSelectable);
    }

    /**
     * Optional interface for controlling if selection list elements can be selected by keyboard navigation.
     * If this interface is applied to type parameter T of {@link GenericMonitorSelectionList},
     * then the keyboard navigation cursor will skip any element in the list where {@link KeyboardSelectionAware#isSelectable()} returns
     * false and try to select the one after it in the direction of movement.
     * If this interface is not applied, all elements are assumed to be selectable.
     * You should apply this interface if "disabled" or otherwise unselectable elements will not be rendered any
     * differently when selected or not selected by the game.
     * If you want elements to still have a custom hover effect or some form of feedback when selecting them (e.g. a toast),
     * then you won't need this interface (same if all elements that will be present in the list are always "enabled").
     */
    public interface KeyboardSelectionAware {

        /**
         * Whether this entry can be selected by keyboard navigation.
         * @return true if it can be selected, false if the cursor should skip this one and move to the next one.
         */
        boolean isSelectable();

        /**
         * Queries whether the given object is selectable. Basically just {@link KeyboardSelectionAware#isSelectable()} but does not require a manual instanceof check.
         * @param entry The entry to check
         * @return true if it can be selected, false if the cursor should skip this one and move to the next one.
         */
        static boolean isSelectable(Object entry) {
            return !(entry instanceof GenericMonitorSelectionList.KeyboardSelectionAware keyboardSelectionAware) || keyboardSelectionAware.isSelectable();
        }
    }

}


