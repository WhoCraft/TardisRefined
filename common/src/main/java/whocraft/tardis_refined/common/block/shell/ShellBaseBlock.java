package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.shell.ExteriorShell;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.TRTeleporter;
import whocraft.tardis_refined.registry.TRBlockRegistry;

public abstract class ShellBaseBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, Fallable, RedirectBlock.RedirectTarget {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty REGEN = BooleanProperty.create("regen");
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ShellBaseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(LOCKED, false).setValue(REGEN, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, OPEN, REGEN, LOCKED, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState state = super.getStateForPlacement(blockPlaceContext);
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        boolean waterlogged = fluidState.getType() == Fluids.WATER;
        return state.setValue(FACING, blockPlaceContext.getHorizontalDirection()).setValue(OPEN, false).setValue(LOCKED, false).setValue(REGEN, false).setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
/*
        levelAccessor.scheduleTick(blockPos, this, this.getDelayAfterPlace());
*/

        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel pLevel, T pBlockEntity) {
        return super.getListener(pLevel, pBlockEntity);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return ShellTheme.getShape(ShellTheme.DEFAULT_EXTERIOR_SHAPES, blockState.getValue(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return this.getShape(blockState, blockGetter, blockPos, collisionContext);
    }


    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;
            if (serverLevel.getBlockEntity(blockPos) instanceof ExteriorShell shellEntity) {
                VoxelShape teleportShape = this.getCollisionShape(blockState, level, blockPos, CollisionContext.of(entity));
                if (teleportShape.isEmpty()) return;
                AABB teleportAABB = teleportShape.bounds().move(blockPos);
                if (TRTeleporter.teleportIfCollided(serverLevel, blockPos, entity, teleportAABB)) {
                    shellEntity.onAttemptEnter(blockState, serverLevel, blockPos, entity);
                }
            }
        }
    }


    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public boolean shouldHaveRedirectBlock(BlockGetter level, BlockPos redirectBlockPos, BlockState redirectBlockState, BlockPos targetPos) {
        if (level.getBlockEntity(targetPos) instanceof GlobalShellBlockEntity shell) {
            return shell.isValidRedirectBlock(redirectBlockPos, redirectBlockState);
        }
        return true;
    }

}
