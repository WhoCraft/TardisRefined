package whocraft.tardis_refined.registry.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ItemRegistryImpl {

    public static final CreativeModeTab TAB = FabricItemGroup.builder(new ResourceLocation(TardisRefined.MODID, TardisRefined.MODID))
            .icon(() -> new ItemStack(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get())).displayItems((itemDisplayParameters, output) -> {
                for (RegistrySupplier<Item> item : ItemRegistry.TAB_ITEMS) {
                    output.accept(item.get());
                }
            }).title(Component.literal("TARDIS Refined")).build();

    public static CreativeModeTab getCreativeTab() {
        return TAB;
    }

}
