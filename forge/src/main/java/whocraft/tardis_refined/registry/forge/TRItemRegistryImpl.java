package whocraft.tardis_refined.registry.neoforge;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRBlockRegistry;

public class TRItemRegistryImpl {
    public static CreativeModeTab getCreativeTab() {
        return CreativeModeTab.builder().title(Component.translatable(ModMessages.ITEM_GROUP)).icon(() -> new ItemStack(TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get())).build();
    }

}