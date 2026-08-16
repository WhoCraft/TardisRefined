package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;

public class GlobalShellBlock extends ShellBaseBlock {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public GlobalShellBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(WATERLOGGED, false).setValue(LOCKED, false).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
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
        }
        return super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
    }

    public static VoxelShape getShapeFromTheme(ResourceLocation theme, BlockState blockState) {
        return ShellTheme.SHELL_THEME_DEFERRED_REGISTRY.get(theme).getShape(
                blockState.getValue(OPEN) ? ShellTheme.ShapeType.OPEN_EXTERIOR : ShellTheme.ShapeType.CLOSED_EXTERIOR, blockState.getValue(FACING)
        );
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (blockGetter.getBlockEntity(blockPos) instanceof GlobalShellBlockEntity shell) {
            return getShapeFromTheme(shell.theme(), blockState);
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

    protected boolean canOpenFromSide(Level level, BlockPos blockPos, BlockState blockState, Direction side) {
        if (side == Direction.UP) {
            return blockState.getShape(level, blockPos).max(Direction.Axis.Y) < 1.5;
        }
        return side.getOpposite() == blockState.getValue(FACING);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!player.level().isClientSide()) {
            if (level instanceof ServerLevel serverLevel) {
                if (canOpenFromSide(level, blockPos, blockState, blockHitResult.getDirection())) {
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
