package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import whocraft.tardis_refined.common.dimension.DimensionHandler;

public abstract class ShellBaseBlock extends Block {

    public ShellBaseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        // Fun times happen here.
        if (!level.isClientSide()) {
            // Load the dimension stuff here:
            DimensionHandler.getOrCreateInterior(level, "test_interior_id");
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

}
