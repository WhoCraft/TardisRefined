package whocraft.tardis_refined.common.block.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.manager.AestheticHandler;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;

public class GlobalDoorBlock extends InternalDoorBlock {

    public GlobalDoorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OFFSET, false).setValue(OPEN, true).setValue(LOCKED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        this.setBlockEntity(new GlobalDoorBlockEntity(blockPos, blockState));
        return super.newBlockEntity(blockPos, blockState);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (level instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
                BlockEntity block = level.getBlockEntity(blockPos);
                AestheticHandler aesthetics = tardisLevelOperator.getAestheticHandler();
                if (block instanceof GlobalDoorBlockEntity globalDoorBlockEntity) {
                    globalDoorBlockEntity.setShellTheme(aesthetics.getShellTheme());
                    globalDoorBlockEntity.setPattern(aesthetics.shellPattern());
                    globalDoorBlockEntity.sendUpdates();
                }
            });
        }
        super.onPlace(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        return super.getStateForPlacement(blockPlaceContext);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (interactionHand == InteractionHand.MAIN_HAND) {
            if (!player.level().isClientSide()) {
                if (level instanceof ServerLevel serverLevel) {
                    if (TardisLevelOperator.get(serverLevel).isPresent()) {
                        if (serverLevel.getBlockEntity(blockPos) instanceof GlobalDoorBlockEntity entity) {
                            entity.onRightClick(blockState, entity, player);
                            return InteractionResult.sidedSuccess(false); //Use InteractionResult.sidedSuccess(false) for non-client side. Stops hand swinging twice. We don't want to use InteractionResult.SUCCESS because the client calls SUCCESS, so the server side calling it too sends the hand swinging packet twice.
                        }
                    }
                }
            }
        }

        return InteractionResult.sidedSuccess(true); //Use InteractionResult.sidedSuccess(true) for client side. Stops hand swinging twice. We don't want to use InteractionResult.SUCCESS because the client calls SUCCESS, so the server side calling it too sends the hand swinging packet twice.
    }

    public static VoxelShape getShapeFromTheme(ResourceLocation theme, BlockState blockState) {
        return offsetShape(
                ShellTheme.SHELL_THEME_DEFERRED_REGISTRY.get(theme).getShape(
                        blockState.getValue(OPEN) ? ShellTheme.ShapeType.OPEN_INTERIOR : ShellTheme.ShapeType.CLOSED_INTERIOR, blockState.getValue(FACING)
                ),
                blockState
        );
    }

    public static VoxelShape offsetShape(VoxelShape shape, BlockState blockState) {
        if (blockState.getValue(OFFSET)) {
            return switch (blockState.getValue(FACING)) {
                case EAST -> shape.move(0, 0, -0.5);
                case SOUTH -> shape.move(0.5, 0, 0);
                case WEST -> shape.move(0, 0, 0.5);
                case NORTH -> shape.move(-0.5, 0, 0);
                default -> shape;
            };
        }
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (blockGetter.getBlockEntity(blockPos) instanceof GlobalDoorBlockEntity shell) {
            return getShapeFromTheme(shell.theme(), blockState);
        } else {
            return offsetShape(ShellTheme.getShape(ShellTheme.DEFAULT_INTERIOR_SHAPES, blockState.getValue(FACING)), blockState);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
        //noinspection ConstantValue IntelliJ got confused by this for some reason...
        if (
                !TRConfig.COMMON.IP_VS_COLLISION.get() && blockEntity != null && blockEntity.getLevel() != null &&
                ModCompatChecker.immersivePortals() && ImmersivePortals.isTeleportingPortalPresent(blockEntity.getLevel().dimension()) &&
                ModCompatChecker.valkyrienSkies() && VSHelper.isBlockInShipyard(blockEntity.getLevel(), blockPos)
        ) {
            return Shapes.empty();
        }
        return this.getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (level1, blockPos, stage, t) -> {
            if (t instanceof GlobalDoorBlockEntity globalDoorBlockEntity) {
                globalDoorBlockEntity.tick(level1, blockPos, stage, globalDoorBlockEntity);
            }
        };
    }

    @Override
    public boolean shouldHaveRedirectBlock(BlockGetter level, BlockPos redirectBlockPos, BlockState redirectBlockState, BlockPos targetPos) {
        if (level.getBlockEntity(targetPos) instanceof GlobalDoorBlockEntity door) {
            return door.isValidRedirectBlock(redirectBlockPos, redirectBlockState);
        }
        return true;
    }
}
