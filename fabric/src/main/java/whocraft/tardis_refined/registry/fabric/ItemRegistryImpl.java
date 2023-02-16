package whocraft.tardis_refined.registry.fabric;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;

public class ItemRegistryImpl {

    public static final CreativeModeTab TAB = FabricItemGroupBuilder.build(new ResourceLocation(TardisRefined.MODID, TardisRefined.MODID), () -> new ItemStack(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get()));

    public static CreativeModeTab getCreativeTab() {
        return TAB;
    }

}
