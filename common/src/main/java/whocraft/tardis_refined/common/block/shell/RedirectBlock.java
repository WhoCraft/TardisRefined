package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.block.door.InternalDoorBlock;
import whocraft.tardis_refined.registry.TRBlockRegistry;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Shadow block that users will usually never see.
 * Causes the game to target the source block so you can click the entire hitbox of blocks larger than a single block.
 * Use {@link RedirectBlock#tryPlace(Level, BlockPos, BlockState)} to place it and {@link RedirectBlock#tryRemove(Level, BlockPos, BlockState)} to remove it.
 * You may want to use {@link RedirectBlock.Entry} to handle multiple redirect blocks.
 */
public class RedirectBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RedirectBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
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
        var targetPos = new BlockPos.MutableBlockPos();
        targetPos.set(pos);
        BlockState currentState = state;
        while (currentState.getBlock() instanceof RedirectBlock) {
            targetPos.move(currentState.getValue(FACING));
            currentState = level.getBlockState(targetPos);
            // Infinite loop detected.
            if (pos.equals(targetPos)) {
                return Optional.empty();
            }
        }
        var blockBelow = level.getBlockState(targetPos).getBlock();
        if (blockBelow instanceof RedirectTarget targetBlock && targetBlock.shouldHaveRedirectBlock(level, pos, state, targetPos)) {
            return Optional.of(targetPos);
        }
        return Optional.empty();
    }

    public static void tryPlace(Level level, BlockPos pos, BlockState state) {
        var existing = level.getBlockState(pos);
        if (existing.isAir()) {
            level.setBlock(pos, state, Block.UPDATE_ALL);
        } else if (existing.is(Blocks.WATER)) {
            level.setBlock(pos, state.setValue(WATERLOGGED, true), Block.UPDATE_ALL);
        }
    }

    public static void tryRemove(Level level, BlockPos pos, BlockState state) {
        var existingState = level.getBlockState(pos);
        if (existingState.is(state.getBlock()) && existingState.setValue(WATERLOGGED, false) == state) {
            level.removeBlock(pos, false);
        }
    }

    /**
     * Add this interface to a block to indicate that it is a valid target for a redirect block.
     */
    public interface RedirectTarget {
        /**
         * Validates if a given redirect block pointing to this block (either directly, or via other redirect blocks) should continue existing.
         * If this returns false, the redirect block calling it will delete itself in the next block update.
         * @param level The level
         * @param redirectBlockPos The position of the redirect block, should always be the original position even if it is not directly connected
         * @param redirectBlockState The state of the redirect block, should always be the original state even if it is not directly connected
         * @param targetPos The position of the source non-redirect block that the redirect block is pointing towards.
         * @return true if the redirect block should continue existing, false otherwise
         */
        boolean shouldHaveRedirectBlock(BlockGetter level, BlockPos redirectBlockPos, BlockState redirectBlockState, BlockPos targetPos);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    public record Entry(BlockPos offset, BlockState state) {
        /**
         * Create a set of redirect block entries from the given voxel shape.
         * @param shape The shape
         * @return The set of shapes
         */
        public static Set<Entry> getRedirectsForShape(VoxelShape shape) {
            if (shape.isEmpty()) return Set.of();
            // If we don't deflate it might grab blocks around the bounding box when it's a full block, which we don't want.
            return BlockPos.betweenClosedStream(shape.bounds().deflate(0.01)).filter(pos -> !pos.equals(BlockPos.ZERO)).map(
                    pos -> new Entry(
                            pos.immutable(), TRBlockRegistry.REDIRECT_BLOCK.get().defaultBlockState().setValue(
                                    RedirectBlock.FACING, Direction.getNearest(pos.getX(), pos.getY(), pos.getZ()).getOpposite()
                            )
                    )
            ).collect(Collectors.toSet());
        }

        /**
         * Places the entire set of redirect blocks in the correct order.
         * @param level The level
         * @param pos The source block position that the redirect blocks will be pointing to
         * @param redirectBlocks The redirect blocks to place
         */
        public static void place(Level level, BlockPos pos, Set<Entry> redirectBlocks) {
            redirectBlocks.stream().sorted(
                    Comparator.comparingInt(
                            redirect -> Math.abs(redirect.offset().getX()) + Math.abs(redirect.offset().getY()) + Math.abs(redirect.offset.getZ())
                    )
            ).forEach(block -> {
                RedirectBlock.tryPlace(level, pos.offset(block.offset()), block.state());
            });
        }

        /**
         * Removes all the given redirect blocks pointing to the given source.
         * @param level The level
         * @param pos The source block position that the redirect blocks are pointing to
         * @param redirectBlocks The redirect blocks to remove
         */
        public static void remove(Level level, BlockPos pos, Set<Entry> redirectBlocks) {
            for (var block : redirectBlocks) {
                RedirectBlock.tryRemove(level, pos.offset(block.offset()), block.state());
            }
        }

        /**
         * Checks if the given redirect block should exist.
         * @param pos The position of the redirect block.
         * @param state The state of the redirect block.
         * @param sourcePos The position of the source block that the redirect block is pointing to.
         * @param redirectBlocks The redirect blocks.
         * @return true if it should continue existing, false otherwise
         */
        public static boolean isValidRedirect(BlockPos pos, BlockState state, BlockPos sourcePos, Set<Entry> redirectBlocks) {
            var offset = pos.subtract(sourcePos);
            return redirectBlocks.contains(new Entry(offset, state));
        }
    }
}
