package whocraft.tardis_refined.registry.neoforge;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRBlockRegistry;

public class TRItemRegistryImpl {
    public static CreativeModeTab getCreativeTab() {
        return CreativeModeTab.builder().m_257941_(Component.m_130674_(ModMessages.ITEM_GROUP)).m_257737_(() -> new ItemStack(TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get())).m_257652_();
    }

}