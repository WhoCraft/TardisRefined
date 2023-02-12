package whocraft.tardis_refined.common.block.device;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.Platform;

public class LandingPad extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public LandingPad(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
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
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (level instanceof ServerLevel serverLevel) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (itemStack.getItem() instanceof KeyItem) {
                var keyChain = KeyItem.getKeychain(itemStack);
                if (keyChain.size() > 0) {
                    ResourceKey<Level> dimension = KeyItem.getKeychain(itemStack).get(0);

                    if (serverLevel.isEmptyBlock(blockPos.above())) {
                        var tardisLevel = Platform.getServer().getLevel(dimension);

                        var operatorOptional = TardisLevelOperator.get(tardisLevel);
                        if (operatorOptional.isEmpty()) {
                            return InteractionResult.PASS;
                        }

                        var operator = operatorOptional.get();

                        if (operator.getControlManager().beginFlight(true) || !operator.getControlManager().isOnCooldown()) {
                            operator.getControlManager().setTargetLocation(new TardisNavLocation(blockPos.above(), player.getDirection().getOpposite(), serverLevel));
                            serverLevel.playSound(null, blockPos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1f, 1f);
                            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
                        }
                    }

                    serverLevel.playSound(null, blockPos, SoundEvents.NOTE_BLOCK_BIT, SoundSource.BLOCKS, 100, (float) (0.1 + (serverLevel.getRandom().nextFloat() * 0.25)));
                }
            }
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}
