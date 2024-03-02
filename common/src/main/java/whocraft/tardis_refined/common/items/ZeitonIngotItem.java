package whocraft.tardis_refined.common.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import whocraft.tardis_refined.registry.BlockRegistry;

public class ZeitonIngotItem extends Item {
    public ZeitonIngotItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {



        if (useOnContext.getLevel().getBlockState(useOnContext.getClickedPos()).getBlock() == Blocks.IRON_BLOCK) {
            useOnContext.getLevel().setBlockAndUpdate(useOnContext.getClickedPos(), BlockRegistry.ZEITON_FUSED_IRON_BLOCK.get().defaultBlockState());
            useOnContext.getItemInHand().shrink(1);
            useOnContext.getLevel().playSound(null, useOnContext.getClickedPos(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1, 1 );
        }

        if (useOnContext.getLevel().getBlockState(useOnContext.getClickedPos()).getBlock() == Blocks.COPPER_BLOCK) {
            useOnContext.getLevel().setBlockAndUpdate(useOnContext.getClickedPos(), BlockRegistry.ZEITON_FUSED_COPPER_BLOCK.get().defaultBlockState());
            useOnContext.getItemInHand().shrink(1);
            useOnContext.getLevel().playSound(null, useOnContext.getClickedPos(), SoundEvents.COPPER_BREAK, SoundSource.BLOCKS, 1, 1);

        }

        return super.useOn(useOnContext);
    }
}
