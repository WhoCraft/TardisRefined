package whocraft.tardis_refined.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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
        BlockState blockState = useOnContext.getLevel().getBlockState(useOnContext.getClickedPos());

        if (blockState.getBlock() == BlockRegistry.FOOLS_STONE.get()) {
            Player player = useOnContext.getPlayer();
            Level level = useOnContext.getLevel();
            BlockPos clickedPos = useOnContext.getClickedPos();

            player.getCooldowns().addCooldown(this, 5);
            destroyGrowthBlock(level, clickedPos);

            if (player.isShiftKeyDown()) {
                return super.useOn(useOnContext);
            }

            destroyGrowthBlock(level, clickedPos.above());
            destroyGrowthBlock(level, clickedPos.below());
        }

        return super.useOn(useOnContext);
    }



    private void destroyGrowthBlock(Level level, BlockPos pos) {
        if (level.getBlockState(pos).getBlock() == BlockRegistry.FOOLS_STONE.get()) {
            level.destroyBlock(pos, true);
        }
    }
}
