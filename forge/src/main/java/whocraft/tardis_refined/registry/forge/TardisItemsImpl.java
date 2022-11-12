package whocraft.tardis_refined.registry.forge;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import whocraft.tardis_refined.TardisRefined;

public class TardisItemsImpl {

    public static CreativeModeTab TAB = new CreativeModeTab(TardisRefined.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.REDSTONE_WIRE);
        }
    };

    public static CreativeModeTab getCreativeTab() {
        return TAB;
    }


}