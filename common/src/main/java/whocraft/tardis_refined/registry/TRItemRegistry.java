package whocraft.tardis_refined.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.items.*;
import whocraft.tardis_refined.common.util.PlatformWarning;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TRItemRegistry {
    public static final DeferredRegistry<CreativeModeTab> TABS = DeferredRegistry.create(TardisRefined.MODID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> MAIN_TAB = TABS.register("main_tab", TRItemRegistry::getCreativeTab);
    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(TardisRefined.MODID, Registries.ITEM);
    public static List<RegistrySupplier<Item>> TAB_ITEMS = new ArrayList<>();
    public static final RegistrySupplier<Item> KEY = register("tardis_key", () -> new KeyItem(itemProperties("tardis_key").stacksTo(1)), true);
    public static final RegistrySupplier<Item> SCREWDRIVER = register("amethyst_screwdriver", () -> new ScrewdriverItem(itemProperties("amethyst_screwdriver").stacksTo(1)), true);
    public static final RegistrySupplier<Item> PATTERN_MANIPULATOR = register("pattern_manipulator", () -> new Item(itemProperties("pattern_manipulator").stacksTo(1)), true);
    public static final RegistrySupplier<Item> DRILL = register("drill", () -> new DrillItem(itemProperties("drill").stacksTo(1)), true);

    public static final RegistrySupplier<Item> RAW_ZEITON = register("raw_zeiton", () -> new Item(itemProperties("raw_zeiton")), true);
    public static final RegistrySupplier<ZeitonIngotItem> ZEITON_INGOT = register("zeiton_ingot", () -> new ZeitonIngotItem(itemProperties("zeiton_ingot")), true);
    public static final RegistrySupplier<Item> ZEITON_NUGGET = register("zeiton_nugget", () -> new Item(itemProperties("zeiton_nugget")), true);
    public static final RegistrySupplier<GlassesItem> GLASSES = register("glasses", () -> new GlassesItem(itemProperties("glasses").equippable(net.minecraft.world.entity.EquipmentSlot.HEAD)), true);
    public static final RegistrySupplier<MalletItem> MALLET = register("mallet", () -> new MalletItem(itemProperties("mallet").stacksTo(1).durability(50)), true);
    public static final RegistrySupplier<DimensionSamplerItem> TEST_TUBE = register("test_tube", () -> new DimensionSamplerItem(itemProperties("test_tube").stacksTo(1)), true);


    private static <T extends Item> RegistrySupplier<T> register(String id, Supplier<T> itemSupplier, boolean addToTab) {
        RegistrySupplier<T> item = ITEMS.register(id, itemSupplier);
        if (addToTab) {
            TAB_ITEMS.add((RegistrySupplier<Item>) item);
        }
        return item;
    }

    public static CreativeModeTab getCreativeTab(){
        return whocraft.tardis_refined.registry.fabric.TRItemRegistryImpl.getCreativeTab();
    }

    public static Item.Properties itemProperties(String id) {
        return new Item.Properties().setId(ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(TardisRefined.MODID, id)
        ));
    }


}
