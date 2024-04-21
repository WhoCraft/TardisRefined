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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;;
import net.minecraft.world.phys.BlockHitResult;
import whocraft.tardis_refined.common.blockentity.device.AstralManipulatorBlockEntity;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.tardis.CorridorGenerator;


public class AstralManipulatorBlock extends Block implements EntityBlock {
    public AstralManipulatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (level instanceof ServerLevel serverLevel && interactionHand == InteractionHand.MAIN_HAND) {

            if (level.getBlockEntity(blockPos) instanceof AstralManipulatorBlockEntity astralManipulatorBlockEntity) {
                ItemStack itemStack = player.getItemInHand(interactionHand);

                if (itemStack == ItemStack.EMPTY) {
                    astralManipulatorBlockEntity.clearDisplay();
                    return InteractionResult.CONSUME;
                } else {

                    if (itemStack.getItem() instanceof ScrewdriverItem) {

                        astralManipulatorBlockEntity.onRightClick(itemStack, player);
                    }

                    CorridorGenerator.onAttemptToUse(serverLevel, itemStack, blockPos, player);
                }

            }


        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AstralManipulatorBlockEntity(blockPos, blockState);
    }
}
