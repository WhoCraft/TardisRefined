package whocraft.tardis_refined.common.block.console;

import net.minecraft.core.BlockPos;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.player.Player;
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
import whocraft.tardis_refined.common.block.properties.ConsoleProperty;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

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
            globalConsoleBlock.markDirty();
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
        }
    }
}
