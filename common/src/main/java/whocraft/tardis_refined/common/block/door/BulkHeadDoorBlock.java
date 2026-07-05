package whocraft.tardis_refined.common.block.door;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorExtensionBlockEntity;
import whocraft.tardis_refined.registry.TRBlockRegistry;

public class BulkHeadDoorBlock extends BaseEntityBlock {

    public enum BulkHeadType implements StringRepresentable {
        ROUGH("rough"), MODERN("modern"), SMOOTH("smooth");

        private final String name;

        BulkHeadType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = GlobalDoorBlock.OPEN;
    public static final BooleanProperty LOCKED = GlobalDoorBlock.LOCKED;
    public static final EnumProperty<BulkHeadType> TYPE = EnumProperty.create("bulkhead", BulkHeadType.class);

    protected static final VoxelShape EMPTY = Block.box(0.0, 0.0, 0, 0, 0, 0);
    protected static final VoxelShape NS_COLLISION = Block.box(0.0, 0.0, 4.0, 16.0, 16.0, 12.0);
    protected static final VoxelShape WE_COLLISION = Block.box(4.0, 0.0, 0.0, 12.0, 16.0, 16.0);

    public BulkHeadDoorBlock(Properties properties) {
        super(properties.sound(SoundType.ANVIL));

        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, BulkHeadType.MODERN).setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(LOCKED, true));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        boolean isOpen = blockState.getValue(OPEN);
        if (isOpen) {
            return EMPTY;
        }
        return switch (blockState.getValue(FACING)) {
            case EAST, WEST -> WE_COLLISION;
            default -> NS_COLLISION;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return this.getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, OPEN, LOCKED, TYPE);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {

        BlockState state = super.getStateForPlacement(blockPlaceContext);
        if (canSurvive(state, blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) {
            return state.setValue(FACING, blockPlaceContext.getHorizontalDirection()).setValue(OPEN, false).setValue(LOCKED, false).setValue(TYPE, BulkHeadType.MODERN);
        }
        return null;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BulkHeadDoorBlockEntity(blockPos, blockState);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);

        changeBlockStates(level, blockPos, blockState, blockState.getValue(OPEN), true);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(blockPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
            return bulkHeadDoorBlockEntity.onRightClick(blockState, level, blockPos, player, blockHitResult);
        }

        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult);
    }


    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        destroy(level, blockPos, blockState);
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        clearDoor((Level) levelAccessor, blockPos, blockState);
        super.destroy(levelAccessor, blockPos, blockState);
    }

    private void changeBlockStates(Level level, BlockPos blockPos, BlockState blockState, boolean isOpen, boolean isInitialPlacement) {

        updateDoorPosition(level, blockPos.above(), isOpen, blockPos, isInitialPlacement);
        updateDoorPosition(level, blockPos.above(2), isOpen, blockPos, isInitialPlacement);

        if (blockState.getValue(FACING) == Direction.NORTH || blockState.getValue(FACING) == Direction.SOUTH) {

            updateDoorPosition(level, blockPos.east(), isOpen, blockPos, isInitialPlacement);
            updateDoorPosition(level, blockPos.above().east(), isOpen, blockPos, isInitialPlacement);
            updateDoorPosition(level, blockPos.above(2).east(), isOpen, blockPos, isInitialPlacement);

            updateDoorPosition(level, blockPos.west(), isOpen, blockPos, isInitialPlacement);
            updateDoorPosition(level, blockPos.above().west(), isOpen, blockPos, isInitialPlacement);
            updateDoorPosition(level, blockPos.above(2).west(), isOpen, blockPos, isInitialPlacement);

        }

        if (blockState.getValue(FACING) == Direction.EAST || blockState.getValue(FACING) == Direction.WEST) {
            updateDoorPosition(level, blockPos.north(), isOpen, blockPos, isInitialPlacement);
            updateDoorPosition(level, blockPos.above().north(), isOpen, blockPos, isInitialPlacement);
            updateDoorPosition(level, blockPos.above(2).north(), isOpen, blockPos, isInitialPlacement);

            updateDoorPosition(level, blockPos.south(), isOpen, blockPos, isInitialPlacement);
            updateDoorPosition(level, blockPos.above().south(), isOpen, blockPos, isInitialPlacement);
            updateDoorPosition(level, blockPos.above(2).south(), isOpen, blockPos, isInitialPlacement);
        }
    }

    public static void clearDoor(Level level, BlockPos blockPos, BlockState blockState) {

        // Somthing has gone wrong if we don't have this property. Be safe and don't grief just in case.
        boolean hasFacingDir = blockState.hasProperty(FACING);
        if (!hasFacingDir) {
            return;
        }

        level.setBlock(blockPos.above(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        level.setBlock(blockPos.above(2), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

        if (blockState.getValue(FACING) == Direction.NORTH || blockState.getValue(FACING) == Direction.SOUTH) {
            level.setBlock(blockPos.east(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(blockPos.above().east(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(blockPos.above(2).east(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

            level.setBlock(blockPos.west(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(blockPos.above().west(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(blockPos.above(2).west(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }

        if (blockState.getValue(FACING) == Direction.EAST || blockState.getValue(FACING) == Direction.WEST) {

            level.setBlock(blockPos.north(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(blockPos.above().north(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(blockPos.above(2).north(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

            level.setBlock(blockPos.south(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(blockPos.above().south(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            level.setBlock(blockPos.above(2).south(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }
    }

    private void updateDoorPosition(Level level, BlockPos pos, boolean isOpen, BlockPos originPos, boolean isInitialPlacement) {
        BlockState currentState = level.getBlockState(pos);
        BlockState originState = level.getBlockState(originPos);

        if ((!hasProperty(currentState, BulkHeadDoorExtensionBlock.FACING ) || !hasProperty(originState, FACING )) && !isInitialPlacement) { return;}

        if (currentState.getBlock() instanceof BulkHeadDoorExtensionBlock || pos == originPos) {
            level.setBlock(pos, currentState.setValue(BulkHeadDoorExtensionBlock.OPEN, isOpen), Block.UPDATE_CLIENTS);
        } else {

            // If the position isn't a bulkhead door extension, set it up and start again.
            level.setBlock(pos, TRBlockRegistry.BULK_HEAD_DOOR_EXT.get().defaultBlockState().setValue(FACING, originState.getValue(FACING)), Block.UPDATE_CLIENTS);

            if (level.getBlockEntity(pos) instanceof BulkHeadDoorExtensionBlockEntity bex && level.getBlockEntity(originPos) instanceof BulkHeadDoorBlockEntity be) {
                bex.setMasterDoorBlock(be);
                updateDoorPosition(level, pos, isOpen, originPos, isInitialPlacement);
            }
        }

    }

    private boolean hasProperty(BlockState blockState, Property property) {
        return blockState.hasProperty(property);
    }


    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);

    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return checkAirBlockStates(world, pos) && super.canSurvive(state, world, pos);
    }


    private boolean checkAirBlockStates(LevelReader world, BlockPos pos) {
        for (int y = 0; y < 3; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos checkPos = pos.offset(x, y, z);
                    BlockState checkState = world.getBlockState(checkPos);
                    if (!checkState.isAir() && !checkState.is(this)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                bulkHeadDoorBlockEntity.tick(pLevel, blockPos, blockState, bulkHeadDoorBlockEntity);
            }
        };
    }
}
