package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.block.door.InternalDoorBlock;

import java.util.Optional;

public class RedirectBlock extends Block {
    public RedirectBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public InteractionResult use(
            @NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos,
            @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult
    ) {
        return findSource(level, blockPos, blockState).map(
                shellPos -> level.getBlockState(shellPos).use(level, player, interactionHand, blockHitResult.withPosition(shellPos))
        ).orElseGet(() -> {
            level.removeBlock(blockPos, false);
            return InteractionResult.FAIL;
        });
    }

    @Override
    public BlockState updateShape(
            @NotNull BlockState blockState, @NotNull Direction direction, @NotNull BlockState neighborState,
            @NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockPos neighborPos
    ) {
        if (findSource(levelAccessor, blockPos, blockState).isEmpty()) return Blocks.AIR.defaultBlockState();
        return super.updateShape(blockState, direction, neighborState, levelAccessor, blockPos, neighborPos);
    }

    @Override
    public VoxelShape getShape(
            @NotNull BlockState blockState, @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext
    ) {
        return findSource(blockGetter, blockPos, blockState).map(
                shellPos -> blockGetter.getBlockState(shellPos).getShape(blockGetter, shellPos, collisionContext).move(
                        shellPos.getX() - blockPos.getX(), 
                        shellPos.getY() - blockPos.getY(),
                        shellPos.getZ() - blockPos.getZ()
                )
        ).orElse(Shapes.block());
    }
    
    public Optional<BlockPos> findSource(BlockGetter level, BlockPos pos, BlockState state) {
        var below = pos.below();
        var blockBelow = level.getBlockState(below).getBlock();
        if (blockBelow instanceof ShellBaseBlock || blockBelow instanceof InternalDoorBlock) {
            return Optional.of(below);
        }
        return Optional.empty();
    }

    public static void tryPlace(Level level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).isAir()) {
            level.setBlock(pos, state, Block.UPDATE_ALL);
        }
    }
}
