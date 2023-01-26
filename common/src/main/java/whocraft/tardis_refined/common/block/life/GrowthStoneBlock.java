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

public class GrowthStoneBlock extends Block {
    public GrowthStoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        if (player.isCrouching()) {return;}

        if (itemStack.getItem() instanceof DrillItem drillItem) {
            var otherBlocks = new BlockPos[] {blockPos.above(), blockPos.below() };

            for (BlockPos otherBlock : otherBlocks) {
                if (level.getBlockState(otherBlock).getBlock() instanceof GrowthStoneBlock) {
                    level.destroyBlock(otherBlock, true);
                }
            }
        }

        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
    }


}
