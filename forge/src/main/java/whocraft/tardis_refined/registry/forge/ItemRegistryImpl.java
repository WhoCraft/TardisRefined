package whocraft.tardis_refined.registry.forge;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;

public class ItemRegistryImpl {

    public static CreativeModeTab TAB = new CreativeModeTab(TardisRefined.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get());
        }
    };

    public static CreativeModeTab getCreativeTab() {
        return TAB;
    }


}