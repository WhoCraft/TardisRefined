package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.ShellBaseBlockEntity;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;
import whocraft.tardis_refined.registry.TRUpgrades;

import java.util.Optional;

public class GlobalShellBlock extends ShellBaseBlock {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    //The collision box for the briefcase shell
    //overrides the default collision shape from ShellBaseBlock.java
    protected static final VoxelShape BRIEFCASE_COLLISION_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public GlobalShellBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(WATERLOGGED, false).setValue(LOCKED, false).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    private Optional<TardisLevelOperator> getTARDIS(Level level, BlockPos blockPos) {
        if (!level.isClientSide() && level.getBlockEntity(blockPos) instanceof GlobalShellBlockEntity shell) {
            var interior = level.getServer().getLevel(shell.getTardisId());
            if (interior != null) {
                return TardisLevelOperator.get(interior);
            }
        }
        return Optional.empty();
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        level.scheduleTick(blockPos, blockState.getBlock(), 1); // The shell won't know which TARDIS it's linked to yet, so we check landing on the next tick.
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        var tardis = getTARDIS(level, pos);
        if (tardis.isEmpty()) return;
        // We only check for the presence of the upgrade to allow players to enable it in the settings menu while they're landing.
        if (tardis.get().getPilotingManager().isLanding() && tardis.get().getPilotingManager().isInFlight() && TRUpgrades.MATERIALIZE_AROUND.get().isUnlocked(tardis.get().getUpgradeHandler())) {
            // TODO This fallback can probably be removed in 26.1+
            if (!level.getChunkSource().chunkMap.anyPlayerCloseEnoughForSpawning(new ChunkPos(pos))) {
                level.getEntitiesOfClass(
                        Entity.class,
                        state.getCollisionShape(level, pos).bounds().move(pos)
                ).forEach(entity -> entityInside(state, level, pos, entity));
            }
            // Scheduled ticks are a useful and simple way to check for entities in unloaded chunks.
            // This also won't enable mob spawning (unlike a forced ticket), which makes accidental landings around hostile mobs less likely.
            level.scheduleTick(pos, state.getBlock(), 1);
        }
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        return super.getStateForPlacement(blockPlaceContext);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (blockGetter.getBlockEntity(blockPos) instanceof GlobalShellBlockEntity shellBlockEntity) {
            //noinspection ConstantValue IntelliJ got confused by this for some reason...
            if (
                    !TRConfig.COMMON.IP_VS_COLLISION.get() &&
                    ModCompatChecker.immersivePortals() && ImmersivePortals.isTeleportingPortalPresent(shellBlockEntity.getTardisId()) &&
                    ModCompatChecker.valkyrienSkies() && VSHelper.isBlockInShipyard(shellBlockEntity.getLevel(), blockPos)
            ) {
                return Shapes.empty();
            }
            if (shellBlockEntity.theme() == ShellTheme.BRIEFCASE.getId())
                return BRIEFCASE_COLLISION_SHAPE;
        }
        return super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (blockGetter.getBlockEntity(blockPos) instanceof GlobalShellBlockEntity shellBlockEntity) {
            if (shellBlockEntity.theme() == ShellTheme.BRIEFCASE.getId())
                return BRIEFCASE_COLLISION_SHAPE;
        }
        return super.getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Nullable
    @Override // Always assume it's placed by another mod. That setting is set to false when the TARDIS_ID is set.
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GlobalShellBlockEntity(blockPos, blockState).setPlacedByOtherMod(true);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (level1, blockPos, block, t) -> {
            if (t instanceof ShellBaseBlockEntity shellBaseBlockEntity) {
                shellBaseBlockEntity.tick(level1, blockPos, blockState, shellBaseBlockEntity);
            }
        };
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!player.level().isClientSide()) {
            if (level instanceof ServerLevel serverLevel) {

                if (blockHitResult.getDirection().getOpposite() == blockState.getValue(FACING)) {
                    if (serverLevel.getBlockEntity(blockPos) instanceof GlobalShellBlockEntity entity) {
                        ItemStack itemStack = player.getItemInHand(interactionHand);
                        entity.onRightClick(blockState, itemStack, level, blockPos, player);
                        return InteractionResult.sidedSuccess(false); //Use InteractionResult.sidedSuccess(false) for non-client side. Stops hand swinging twice. We don't want to use InteractionResult.SUCCESS because the client calls SUCCESS, so the server side calling it too sends the hand swinging packet twice.
                    }

                }
            }
        }

        return InteractionResult.sidedSuccess(true); //Use InteractionResult.sidedSuccess(true) for client side. Stops hand swinging twice. We don't want to use InteractionResult.SUCCESS because the client calls SUCCESS, so the server side calling it too sends the hand swinging packet twice.
    }
}
