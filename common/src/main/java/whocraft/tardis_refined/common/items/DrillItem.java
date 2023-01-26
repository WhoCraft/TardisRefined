package whocraft.tardis_refined.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.life.GrowthStoneBlock;


public class DrillItem extends Item {
    public DrillItem(Properties properties) {
        super(properties);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        if (blockState.getBlock() instanceof GrowthStoneBlock) { return 10f;}
        return 0f;
    }
}
