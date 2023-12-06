package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class GlobalShellBlock extends ShellBaseBlock{


    public GlobalShellBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        return super.getStateForPlacement(blockPlaceContext);
    }

    protected static final VoxelShape COLLISION = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);


    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return COLLISION;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (blockGetter.getBlockEntity(blockPos) instanceof GlobalShellBlockEntity shellBlockEntity) {
            if(shellBlockEntity.theme() == ShellTheme.BRIEFCASE.getId())
                return COLLISION;
        }
        return super.getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GlobalShellBlockEntity(blockPos, blockState);
    }

    @Override
    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        super.onProjectileHit(level, blockState, blockHitResult, projectile);
        activateHads(level, blockHitResult.getBlockPos());
    }

    private static void activateHads(Level level, BlockPos blockPos) {
        if(level instanceof ServerLevel serverLevel){
            BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
            if(blockEntity instanceof GlobalShellBlockEntity globalShellBlockEntity) {
                if(globalShellBlockEntity.TARDIS_ID == null) return;
                ServerLevel interior = DimensionHandler.getExistingLevel(serverLevel, globalShellBlockEntity.TARDIS_ID.toString());
                TardisLevelOperator.get(interior).ifPresent(tardisLevelOperator -> {
                    RandomSource random = serverLevel.random;
                    UpgradeHandler upgradeHandler = tardisLevelOperator.getUpgradeHandler();

                    if(!Upgrades.HOSTILE_DISPLACEMENT.get().isUnlocked(upgradeHandler)){
                        return;
                    }

                    TardisNavLocation lastKnown = tardisLevelOperator.getExteriorManager().getLastKnownLocation();
                    BlockPos newLocation = new BlockPos(random.nextInt((int) lastKnown.getLevel().getWorldBorder().getMaxX() - 1000), lastKnown.getPosition().getY(), (int) lastKnown.getLevel().getWorldBorder().getMaxZ()  - 1000);
                    tardisLevelOperator.getPilotingManager().setTargetPosition(newLocation);
                    tardisLevelOperator.getPilotingManager().beginFlight(true);
                });
            }
        }
    }

    @Override
    public void wasExploded(Level level, BlockPos blockPos, Explosion explosion) {
        activateHads(level, blockPos);
        super.wasExploded(level, blockPos, explosion);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (level instanceof ServerLevel serverLevel) {
            if (blockHitResult.getDirection().getOpposite() == blockState.getValue(FACING)) {
                if (serverLevel.getBlockEntity(blockPos) instanceof GlobalShellBlockEntity entity) {
                    ItemStack itemStack = player.getItemInHand(interactionHand);
                    entity.onRightClick(blockState, itemStack);
                    return InteractionResult.SUCCESS;
                }

            }
        }

        return InteractionResult.SUCCESS;
    }
}
