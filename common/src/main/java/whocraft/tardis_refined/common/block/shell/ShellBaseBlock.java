package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.shell.ShellBaseBlockEntity;

public abstract class ShellBaseBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty REGEN = BooleanProperty.create("regen");
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");
    protected static final VoxelShape SOUTH_AABB, NORTH_AABB, WEST_AABB, EAST_AABB;

    public ShellBaseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(LOCKED, false).setValue(REGEN, false));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        if (level.getBlockEntity(blockPos) instanceof ShellBaseBlockEntity entity) {
            entity.onBlockInit();
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, OPEN, REGEN, LOCKED);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState state = super.getStateForPlacement(blockPlaceContext);
        return state.setValue(FACING, blockPlaceContext.getHorizontalDirection()).setValue(OPEN, false).setValue(LOCKED, false).setValue(REGEN, false);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel pLevel, T pBlockEntity) {
        return super.getListener(pLevel, pBlockEntity);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        switch (blockState.getValue(FACING)) {
            case SOUTH -> {
                return SOUTH_AABB;
            }
            case NORTH -> {return NORTH_AABB;}
            case WEST -> {return WEST_AABB;}
            case EAST -> {return EAST_AABB;}
        }

        return SOUTH_AABB;
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {

        if (level.getBlockEntity(blockPos) instanceof ShellBaseBlockEntity shellEntity) {
            if (entity instanceof Player player) {
                shellEntity.onAttemptEnter(blockState, level, shellEntity.getBlockPos(), player);
            }
        }
    }

    static {
        NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 13.0D);
        SOUTH_AABB = Block.box(0.0D, 0.0D, 3.0D, 16.0D, 16.0D, 16.0D);
        WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 13.0D, 16.0D, 16.0D);
        EAST_AABB = Block.box(3.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}
