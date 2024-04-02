package whocraft.tardis_refined.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockStateBlockPos {

    public BlockState blockState;
    public BlockPos blockPos;

    public BlockStateBlockPos(BlockState blockState, BlockPos blockPos) {
        this.blockState = blockState;
        this.blockPos = blockPos;
    }

}
