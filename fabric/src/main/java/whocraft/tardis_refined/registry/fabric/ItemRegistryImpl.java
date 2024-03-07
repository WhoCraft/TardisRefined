package whocraft.tardis_refined.registry.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ItemRegistryImpl {

    public static final CreativeModeTab ITEM_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get())).displayItems((enabledFeatures, entries) -> {
        for (RegistrySupplier<Item> item : ItemRegistry.TAB_ITEMS) {
            entries.accept(item.get());
        }
    }).title(Component.translatable(ModMessages.ITEM_GROUP)).build();

    public static final CreativeModeTab DYED_ITEM_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(ItemRegistry.SCREWDRIVER.get())).displayItems((enabledFeatures, entries) -> {

        for (DyeColor value : DyeColor.values()) {
            ItemStack stack = new ItemStack(ItemRegistry.SCREWDRIVER.get());
            ScrewdriverItem.forceColor(stack, value.getTextColor());
            entries.accept(stack);
        }
    }).title(Component.translatable(ModMessages.DYED_ITEM_GROUP)).build();

    public static CreativeModeTab getCreativeTab() {
        return ITEM_GROUP;
    }

    public static CreativeModeTab getDyedCreativeTab() {
        return DYED_ITEM_GROUP;
    }

}
