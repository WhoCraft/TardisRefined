package whocraft.tardis_refined.common.block.device;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.device.EngineInterfaceBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class EngineInterfaceBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final BooleanProperty ENABLED = BooleanProperty.create("enabled");


    public EngineInterfaceBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(ENABLED, false));
    }


    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            EngineInterfaceBlockEntity engineInterfaceBlockEntity = (EngineInterfaceBlockEntity) worldIn.getBlockEntity(pos);

            if (handIn != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

            if (!player.isShiftKeyDown()) {
                assert engineInterfaceBlockEntity != null;
                if(engineInterfaceBlockEntity.getComponent() != ItemStack.EMPTY){
                    engineInterfaceBlockEntity.dropComponentIfPresent(player);
                    engineInterfaceBlockEntity.sendUpdates();
                } else {
                    engineInterfaceBlockEntity.setComponent(player.getMainHandItem());
                    engineInterfaceBlockEntity.sendUpdates();
                }
                return super.use(state, worldIn, pos, player, handIn, hit);
            }

            worldIn.setBlock(pos, state.cycle(ENABLED), Block.UPDATE_ALL);

            return super.use(state, worldIn, pos, player, handIn, hit);
        }

        return super.use(state, worldIn, pos, player, handIn, hit);
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HorizontalDirectionalBlock.FACING);
        builder.add(WATERLOGGED);
        builder.add(ENABLED);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);

        if(level instanceof ServerLevel serverLevel){
            TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
                tardisLevelOperator.getUpgradeHandler().addInterfacePosition(blockPos);
            });
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);

        if(level instanceof ServerLevel serverLevel){
            TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
                tardisLevelOperator.getUpgradeHandler().removeInterfacePosition(blockPos);
            });
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EngineInterfaceBlockEntity(blockPos, blockState);
    }
}
