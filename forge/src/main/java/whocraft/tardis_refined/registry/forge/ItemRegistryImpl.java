package whocraft.tardis_refined.registry.forge;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.registry.BlockRegistry;

public class ItemRegistryImpl {
    public static CreativeModeTab getCreativeTab() {
        return CreativeModeTab.builder().title(Component.translatable("Tardis Refined")).icon(() -> new ItemStack(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get())).build();
    }

}