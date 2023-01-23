package whocraft.tardis_refined.common.block.console;

import net.minecraft.core.BlockPos;
D
import net.minecraft.core.particles.ParticleTypes;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns;
import whocraft.tardis_refined.common.block.properties.ConsoleProperty;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

import java.util.concurrent.atomic.AtomicBoolean;

public class GlobalConsoleBlock extends BaseEntityBlock {

    public static final ConsoleProperty CONSOLE = ConsoleProperty.create("console");

    public GlobalConsoleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CONSOLE, ConsoleTheme.FACTORY));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GlobalConsoleBlockEntity(blockPos, blockState);
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
        //No Operation
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CONSOLE);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        return super.getStateForPlacement(blockPlaceContext).setValue(CONSOLE, ConsoleTheme.FACTORY);
    }


    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {

        if (level.getBlockEntity(blockPos) instanceof GlobalConsoleBlockEntity globalConsoleBlock) {
            if (blockState2.hasProperty(GlobalConsoleBlock.CONSOLE)) {
                ConsolePatterns.Pattern defaultPattern = ConsolePatterns.getPatternFromString(blockState2.getValue(GlobalConsoleBlock.CONSOLE), new ResourceLocation(TardisRefined.MODID, "default"));
                globalConsoleBlock.setPattern(defaultPattern);
                globalConsoleBlock.markDirty();
            }
        }

        super.onPlace(blockState, level, blockPos, blockState2, bl);

    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof GlobalConsoleBlockEntity globalConsoleBlock) {
                globalConsoleBlock.tick(level, blockPos, blockState, globalConsoleBlock);
            }
        };
    }


    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {

        if (levelAccessor.getBlockEntity(blockPos) instanceof GlobalConsoleBlockEntity globalConsoleBlock) {
            globalConsoleBlock.killControls();
        }

        super.destroy(levelAccessor, blockPos, blockState);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        super.animateTick(blockState, level, blockPos, randomSource);

        TardisClientData clientData = TardisClientData.getInstance(level.dimension());
        if (clientData != null) {
            if (clientData.isFlying() && level.random.nextInt(4) == 0) {
                var d = (double)blockPos.getX() + randomSource.nextFloat() * 1.25;
                var e = (double)blockPos.getY() + randomSource.nextDouble() * 1D + 1D;
                var f = (double)blockPos.getZ()  + randomSource.nextFloat()* 1.25;
                level.addParticle(ParticleTypes.CLOUD, d, e, f, 0.0D, 0.1D, 0.0D);
            }

            if (clientData.isOnCooldown() || clientData.isCrashing()) {

                var d = (double)blockPos.getX() + level.getRandom().nextFloat() * 1.25;
                var e = (double)blockPos.getY() + level.getRandom().nextDouble() * 1D + 1D;
                var f = (double)blockPos.getZ()  + level.getRandom().nextFloat()* 1.25;
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, d, e, f, 0.0D, 0.1D, 0.0D);

                for (int i = 0; i < 5; i++) {
                     d = (double)blockPos.getX() + level.getRandom().nextFloat() * 1.25;
                     e = (double)blockPos.getY() + level.getRandom().nextDouble() * 1D + 1D;
                     f = (double)blockPos.getZ()  + level.getRandom().nextFloat()* 1.25;
                    level.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0D, 0.1D, 0.0D);
                }

                if (level.random.nextInt(10) == 0) {
                     d = (double)blockPos.getX() + level.getRandom().nextFloat() * 1.25;
                     e = (double)blockPos.getY() + level.getRandom().nextDouble() * 1D + 1D;
                     f = (double)blockPos.getZ()  + level.getRandom().nextFloat()* 1.25;
                    level.addParticle(ParticleTypes.LAVA, d, e, f, -0.5 + level.random.nextFloat(), 0.05D, -0.5 + level.random.nextFloat());
                    level.addParticle(ParticleTypes.LAVA, d, e, f, -0.5 + level.random.nextFloat(), 0.05D, -0.5 + level.random.nextFloat());
                    level.addParticle(ParticleTypes.LAVA, d, e, f, -0.5 + level.random.nextFloat(), 0.05D, -0.5 + level.random.nextFloat());
                    level.playLocalSound(blockPos.getX(),blockPos.getY(),blockPos.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1, level.getRandom().nextFloat() + 1f, false);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        AtomicBoolean shouldConsume = new AtomicBoolean(false);
        if (level instanceof ServerLevel serverLevel) {
            if (player.isCreative() && player.getItemInHand(interactionHand).getItem() == Items.ICE) {
                TardisLevelOperator.get(serverLevel).ifPresent(x -> {
                    if (x.getControlManager().isOnCooldown()) {
                        x.getControlManager().endCoolDown();
                        shouldConsume.set(true);
                    }
                });

                if (shouldConsume.get()) {return InteractionResult.CONSUME_PARTIAL;}

            }
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}
