package whocraft.tardis_refined.common.block.life;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.items.DrillItem;
import whocraft.tardis_refined.registry.BlockRegistry;

public class GrowthStoneBlock extends Block {
    public GrowthStoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        if (player.isShiftKeyDown()) {
            return;
        }

        if (itemStack.getItem() instanceof DrillItem) {
            destroyGrowthBlock(level, blockPos.above());
            destroyGrowthBlock(level, blockPos.below());
        }

        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
    }


    private void destroyGrowthBlock(Level level, BlockPos pos) {
        if (level.getBlockState(pos).getBlock() == BlockRegistry.GROWTH_STONE.get()) {
            level.destroyBlock(pos, true);
        }
    }

}
