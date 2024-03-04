package whocraft.tardis_refined.common.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import whocraft.tardis_refined.registry.BlockRegistry;

public class ZeitonIngotItem extends Item {
    public ZeitonIngotItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {

        Level level = useOnContext.getLevel();
        ItemStack itemInHand = useOnContext.getItemInHand();

        if (level.getBlockState(useOnContext.getClickedPos()).getBlock() == Blocks.IRON_BLOCK) {
            level.setBlockAndUpdate(useOnContext.getClickedPos(), BlockRegistry.ZEITON_FUSED_IRON_BLOCK.get().defaultBlockState());
            itemInHand.shrink(1);
            level.playSound(null, useOnContext.getClickedPos(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1, 1 );
        }

        if (level.getBlockState(useOnContext.getClickedPos()).getBlock() == Blocks.COPPER_BLOCK) {
            level.setBlockAndUpdate(useOnContext.getClickedPos(), BlockRegistry.ZEITON_FUSED_COPPER_BLOCK.get().defaultBlockState());
            itemInHand.shrink(1);
            level.playSound(null, useOnContext.getClickedPos(), SoundEvents.COPPER_BREAK, SoundSource.BLOCKS, 1, 1);

        }

        return super.useOn(useOnContext);
    }
}
