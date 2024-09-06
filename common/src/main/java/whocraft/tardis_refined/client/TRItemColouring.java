package whocraft.tardis_refined.client;

import net.minecraft.client.color.item.ItemColor;
import whocraft.tardis_refined.common.items.ScrewdriverItem;

public class TRItemColouring {

    // Sonic Colouring - changes the colour of any face thats blank and white and has a tintindex of 0
    public static ItemColor SCREWDRIVER_COLORS = (itemStack, tintIndex) -> {
        if (tintIndex == 0) {
            if (itemStack.getItem() instanceof ScrewdriverItem dyeableLeatherItem) {
                return dyeableLeatherItem.getColor(itemStack);
            }
        }
        return 0; // We do not want to tint, so we let the usual colour take over
    };

}
