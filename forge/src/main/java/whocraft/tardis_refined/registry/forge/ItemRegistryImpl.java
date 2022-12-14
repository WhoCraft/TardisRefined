package whocraft.tardis_refined.registry.forge;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.ItemRegistry;

public class ItemRegistryImpl {

    public static CreativeModeTab TAB = new CreativeModeTab(TardisRefined.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.KEY.get());
        }
    };

    public static CreativeModeTab getCreativeTab() {
        return TAB;
    }


}