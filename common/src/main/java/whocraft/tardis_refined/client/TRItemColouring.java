package whocraft.tardis_refined.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.DyeableLeatherItem;

public class TRItemColouring {

    public static ItemColor SCREWDRIVER_COLORS = (itemStack, tintIndex) -> {
        if (tintIndex == 0) {
            if (itemStack.getItem() instanceof DyeableLeatherItem dyeableLeatherItem) {
                return dyeableLeatherItem.getColor(itemStack);
            }
        }
        return -1;
    };

}
