package whocraft.tardis_refined.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.life.GrowthStoneBlock;
import whocraft.tardis_refined.registry.BlockRegistry;


public class DrillItem extends Item {
    public DrillItem(Properties properties) {
        super(properties);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        return 0f;
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {

        if (useOnContext.getLevel().getBlockState(useOnContext.getClickedPos()).getBlock() == BlockRegistry.GROWTH_STONE.get()) {

            var player = useOnContext.getPlayer();

            player.getCooldowns().addCooldown(this, 5);

            destroyGrowthBlock(useOnContext.getLevel(), useOnContext.getClickedPos());

            if (useOnContext.getPlayer().isShiftKeyDown()) {
                return super.useOn(useOnContext);
            }

            destroyGrowthBlock(useOnContext.getLevel(), useOnContext.getClickedPos().above());
            destroyGrowthBlock(useOnContext.getLevel(), useOnContext.getClickedPos().below());
        }


        return super.useOn(useOnContext);
    }


    private void destroyGrowthBlock(Level level, BlockPos pos) {
        if (level.getBlockState(pos).getBlock() == BlockRegistry.GROWTH_STONE.get()) {
            level.destroyBlock(pos, true);
        }
    }
}
