package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.shell.RootedShellBlockEntity;

public class RootedShellBlock extends ShellBaseBlock {

    public RootedShellBlock(BlockBehaviour.Properties properties) {

        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, true).setValue(REGEN, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RootedShellBlockEntity(blockPos, blockState);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState state = super.getStateForPlacement(blockPlaceContext);
        return state.setValue(FACING, blockPlaceContext.getHorizontalDirection()).setValue(OPEN, true).setValue(REGEN, false);
    }
}
