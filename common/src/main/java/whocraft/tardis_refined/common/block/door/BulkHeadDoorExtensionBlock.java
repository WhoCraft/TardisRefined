package whocraft.tardis_refined.common.block.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorExtensionBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;

public class BulkHeadDoorExtensionBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    protected static final VoxelShape EMPTY = Block.box(0.0, 0.0, 0, 0, 0, 0);
    protected static final VoxelShape NS_COLLISION = Block.box(0.0, 0.0, 3.0, 16.0, 16.0, 13.0);
    protected static final VoxelShape WE_COLLISION = Block.box(3.0, 0.0, 0.0, 13.0, 16.0, 16.0);

    public BulkHeadDoorExtensionBlock(Properties properties) {
        super(properties.sound(SoundType.ANVIL));

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, OPEN);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {

        BlockState state = super.getStateForPlacement(blockPlaceContext);
        if (canSurvive(state, blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) {
            return state.setValue(FACING, blockPlaceContext.getHorizontalDirection()).setValue(OPEN, false);
        }
        return null;
    }


    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        boolean isOpen = blockState.getValue(OPEN);
        if (isOpen) {
            return EMPTY;
        }
        return switch (blockState.getValue(FACING)) {
            case EAST -> WE_COLLISION;
            case SOUTH -> NS_COLLISION;
            case WEST -> WE_COLLISION;
            case NORTH -> NS_COLLISION;
            default -> NS_COLLISION;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return this.getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BulkHeadDoorExtensionBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(blockPos) instanceof BulkHeadDoorExtensionBlockEntity bulkHeadDoorBlockEntity) {
            return bulkHeadDoorBlockEntity.onRightClick(blockState, level, blockPos, player, interactionHand, blockHitResult);
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        destroy(level, blockPos, blockState);
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        super.playerWillDestroy(level, blockPos, blockState, player);
        destroy(level, blockPos, blockState);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
;
        if (((Level) levelAccessor).getBlockEntity(blockPos) instanceof BulkHeadDoorExtensionBlockEntity bulkHeadDoorBlockEntity) {

            bulkHeadDoorBlockEntity.onDestroy((Level) levelAccessor, blockPos, blockState);
        }
        super.destroy(levelAccessor, blockPos, blockState);
    }


}
