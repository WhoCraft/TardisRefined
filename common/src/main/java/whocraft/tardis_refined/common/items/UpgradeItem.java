package whocraft.tardis_refined.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class UpgradeItem extends Item {

    public UpgradeItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return false;
    }
}
