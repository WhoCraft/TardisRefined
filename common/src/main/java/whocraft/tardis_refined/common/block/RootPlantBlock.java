package whocraft.tardis_refined.common.block;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.blockentity.shell.RootPlantBlockEntity;
import whocraft.tardis_refined.registry.BlockRegistry;

import java.util.List;


public class RootPlantBlock extends BaseEntityBlock  {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    public RootPlantBlock(Properties properties) {

        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, AGE);
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 5;
    }

    protected int getAge(BlockState blockState) {
        return blockState.getValue(this.getAgeProperty());
    }

    public boolean isMaxAge(BlockState blockState) {
        return blockState.getValue(this.getAgeProperty()) >= this.getMaxAge();
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(FACING, context.getHorizontalDirection()).setValue(this.getAgeProperty(), getAge(state));
    }

    private BlockState getStateForAging(int ageValue, Direction facing) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), ageValue).setValue(FACING, facing);
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return !this.isMaxAge(blockState);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        int i = this.getAge(blockState);
        Direction facing = blockState.getValue(FACING);
        if (i < this.getMaxAge()) {
            if (serverLevel.getBlockState(blockPos.below()).getBlock() == Blocks.MAGMA_BLOCK) {

                if (randomSource.nextInt(6) == 0) {
                    if (i + 1 == this.getMaxAge()) {
                        serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                        serverLevel.setBlock(blockPos, BlockRegistry.ROOT_SHELL_BLOCK.get().defaultBlockState().setValue(RootedShellBlock.FACING, facing), 3);
                    } else {
                        serverLevel.setBlock(blockPos, this.getStateForAging(i + 1, facing), 3);
                    }

                    serverLevel.playSound(null, blockPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1, 1);
                }
            }
        }
    }
    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        return super.getDrops(blockState, builder);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        ItemStack itemStack = new ItemStack(BlockRegistry.ROOT_PLANT_BLOCK.get());
        ItemEntity itemEntity = new ItemEntity(level, (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D, itemStack);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);

        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RootPlantBlockEntity(blockPos, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {

        return Block.box(5f, 0f, 5f, 11f, 5f, 11f);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);

        if (level.getBlockState(blockPos.below()).getBlock() == Blocks.MAGMA_BLOCK) {
            level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1.25f);
        }
    }
}
