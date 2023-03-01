package whocraft.tardis_refined.common.block.console;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.common.block.properties.ConsoleProperty;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.ClientHelper;
import whocraft.tardis_refined.patterns.Pattern;


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
                Pattern<ConsoleTheme> defaultPattern = ConsolePatterns.getPatternFromString(blockState2.getValue(GlobalConsoleBlock.CONSOLE), new ResourceLocation(TardisRefined.MODID, "default"));
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

            var xCord = (double) blockPos.getX() + randomSource.nextFloat() * 1.25;
            var yCord = (double) blockPos.getY() + randomSource.nextDouble() + 1D;
            var zCord = (double) blockPos.getZ() + randomSource.nextFloat() * 1.25;

            if (clientData.isFlying() && level.random.nextInt(4) == 0) {

                ClientHelper.playParticle((ClientLevel) level, ParticleTypes.CLOUD, new Vec3(xCord, yCord, zCord), 0.0D, 0.1D, 0.0D);
            }

            if (clientData.isOnCooldown() || clientData.isCrashing()) {

                ClientHelper.playParticle((ClientLevel) level, ParticleTypes.CAMPFIRE_COSY_SMOKE, new Vec3(xCord, yCord, zCord), 0.0D, 0.1D, 0.0D);

                for (int i = 0; i < 5; i++) {
                    ClientHelper.playParticle((ClientLevel) level, ParticleTypes.LARGE_SMOKE, new Vec3(xCord, yCord, zCord), 0.0D, 0.1D, 0.0D);
                }

                if (level.random.nextInt(10) == 0) {
                    for (int i = 0; i < 3; i++) {
                        ClientHelper.playParticle((ClientLevel) level, ParticleTypes.LAVA, new Vec3(xCord, yCord, zCord), -0.5 + level.random.nextFloat(), 0.05D, -0.5 + level.random.nextFloat());
                    }

                    level.playLocalSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1, level.getRandom().nextFloat() + 1f, false);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        // Creative only: Quickly complete the cooldown.
        if (level instanceof ServerLevel serverLevel) {
            if (player.isCreative() && player.getItemInHand(interactionHand).getItem() == Items.ICE) {
                var operatorOptional = TardisLevelOperator.get(serverLevel);
                if (operatorOptional.isPresent()) {
                    var operator = operatorOptional.get();
                    if (operator.getControlManager().isOnCooldown()) {
                        operator.getControlManager().endCoolDown();
                        return InteractionResult.CONSUME_PARTIAL;
                    }
                }

            }
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}
