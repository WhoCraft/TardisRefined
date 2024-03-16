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


public class AstralManipulatorBlock extends Block implements EntityBlock {
    public AstralManipulatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (level instanceof ServerLevel && interactionHand == InteractionHand.MAIN_HAND) {

            if (level.getBlockEntity(blockPos) instanceof AstralManipulatorBlockEntity astralManipulatorBlockEntity) {
                ItemStack itemStack = player.getItemInHand(interactionHand);

                if (itemStack == ItemStack.EMPTY) {
                    astralManipulatorBlockEntity.clearDisplay();
                    return InteractionResult.CONSUME;
                } else {

                    if (itemStack.getItem() instanceof ScrewdriverItem) {

                        astralManipulatorBlockEntity.OnRightClick(itemStack);
                    }

                    // TO BE LEFT ALONE FOR WHEN CORRIDORS NEED TO BE GENERATED.

//                    if (itemStack.getItem() == Items.COMMAND_BLOCK_MINECART) {
//
//                        String name = "gs_r" + level.getRandom().nextInt(1000);
//                        StructureTemplateManager manager = level.getServer().getStructureManager();
//                        StructureTemplate template = manager.getOrCreate(new ResourceLocation(name));
//                        template.fillFromWorld(level, blockPos.above(), new BlockPos(48, 28, 48), false, Blocks.STRUCTURE_VOID);
//                        template.setAuthor("");
//                        manager.save(new ResourceLocation(name));
//
//                        player.displayClientMessage(Component.translatable("Generated structure at: " + name), false);
//                    }
//
//                    if (itemStack.getItem() instanceof InkSacItem) {
//                        player.displayClientMessage(Component.translatable("Attempting to generate structure."), false);
//                        CorridorGenerator.generateFromPosition(level, blockPos, blockPos);
//
//                    }
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
