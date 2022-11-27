package whocraft.tardis_refined.common.block.device;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.registry.DimensionTypes;

import java.util.List;

public class TerraformerBlock extends Block {

    public static BooleanProperty ACTIVE = BlockStateProperties.POWERED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public TerraformerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ACTIVE, FACING);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        if (blockState.getValue(ACTIVE)) {
            return;
        }

        if (level instanceof ServerLevel serverLevel) {

            if (checkIfStructure(serverLevel, blockPos)) {
                System.out.println("Is a valid structure");

                TardisLevelOperator.get(serverLevel).ifPresent(cap -> {

                    if (cap.getInteriorManager().isWaitingToGenerate()) {
                        level.destroyBlock(blockPos, true);
                    } else {
                        cap.getInteriorManager().prepareDesktop(TardisDesktops.FACTORY_THEME);
                        destroyStructure(serverLevel, blockPos);
                        serverLevel.setBlock(blockPos, (BlockState)blockState.setValue(ACTIVE, true), 3);
                    }


                });

            } else {
                blockState.setValue(ACTIVE, false);
                System.out.println("Is not a valid structure");
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        return super.getDrops(blockState, builder);
    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {

        if (blockState.getValue(ACTIVE)) {
            if (randomSource.nextInt(3) == 0) {
                level.playLocalSound((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 1, (double)blockPos.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 5.0F + randomSource.nextFloat(), randomSource.nextFloat() * 0.7F + 0.3F, false);
            }

            if (randomSource.nextInt(10) == 0) {
                level.playLocalSound((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 1, (double)blockPos.getZ() + 0.5D, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 15.0F + randomSource.nextFloat(), 0.1f, false);
            }

            int i;
            double d;
            double e;
            double f;
            for(i = 0; i < 3; ++i) {
                d = (double)blockPos.getX() + randomSource.nextDouble();
                e = (double)blockPos.getY() + randomSource.nextDouble() * 0.5D + 0.5D;
                f = (double)blockPos.getZ() + randomSource.nextDouble();
                level.addParticle(ParticleTypes.FLASH, d, e, f, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.CLOUD, d, e, f, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);

        if (level instanceof ServerLevel serverLevel) {
            if (blockState.getValue(ACTIVE)) {
                TardisLevelOperator.get(serverLevel).ifPresent(cap -> {
                    if (cap.getInteriorManager().isWaitingToGenerate()) {
                        cap.getInteriorManager().cancelDesktopChange();
                    }
                });
            }
        }


    }

    private boolean checkIfStructure(Level level, BlockPos blockPos) {

        if (level.dimensionTypeId() != DimensionTypes.TARDIS) {
            return false;
        }

        BlockPos startingCorner = new BlockPos(blockPos.getX() - 1, blockPos.getY()-1, blockPos.getZ()-1);
        for (int x = startingCorner.getX(); x < startingCorner.getX() + 3; x++) {
            for (int z = startingCorner.getZ(); z < startingCorner.getZ() + 3; z++) {

                BlockState state = level.getBlockState(new BlockPos(x, startingCorner.getY(), z));

                if (x == startingCorner.getX() + 1 && z == startingCorner.getZ() + 1) {
                    if (state.getBlock() != Blocks.REDSTONE_BLOCK) {
                        return false;
                    }
                } else {
                    if (state.getBlock() != Blocks.COPPER_BLOCK) {
                        return false;
                    }
                }
            }
        }

        return true;

    }
    private void destroyStructure(Level level, BlockPos blockPos) {
        if (level instanceof ServerLevel serverLevel) {
            BlockPos startingCorner = new BlockPos(blockPos.getX() - 1, blockPos.getY()-1, blockPos.getZ()-1);
            for (int x = startingCorner.getX(); x < startingCorner.getX() + 3; x++) {
                for (int z = startingCorner.getZ(); z < startingCorner.getZ() + 3; z++) {
                    serverLevel.destroyBlock(new BlockPos(x, startingCorner.getY(), z), false);
                }
            }

            serverLevel.setBlockAndUpdate(blockPos.below(), Blocks.STONE.defaultBlockState());
            serverLevel.playSound(null, blockPos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 10, 1.75f);


        }
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return super.getShape(blockState, blockGetter, blockPos, collisionContext);
    }
}
