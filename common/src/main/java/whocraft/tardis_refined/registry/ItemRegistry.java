package whocraft.tardis_refined.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.util.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemRegistry {

    public static final ArrayList<Item> CREATIVE_ITEMS = new ArrayList<>();
    // Forge can't use registry suppliers during startup
    public static final ArrayList<RegistrySupplier<?>> FORGE_CREATIVE_ITEMS = new ArrayList<>();

    public static CreativeModeTab MAIN_TAB = null;

    static {
        if (MAIN_TAB == null) {
            MAIN_TAB = getCreativeTab();
        }
    }

    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(TardisRefined.MODID, Registries.ITEM);

    public static final RegistrySupplier<Item> KEY = register("tardis_key", () -> new KeyItem(new Item.Properties().stacksTo(1)), MAIN_TAB);

    private static <T extends Item> RegistrySupplier<T> register(String id, Supplier<T> itemSupplier, CreativeModeTab itemGroup) {
        RegistrySupplier<T> registryObject = ITEMS.register(id, itemSupplier);
        if (itemGroup != null) {
            ItemRegistry.CREATIVE_ITEMS.add(registryObject.get());
        } else if(Platform.isModLoaded("forge")) {
            ItemRegistry.FORGE_CREATIVE_ITEMS.add(registryObject);
        }
        return registryObject;
    }

    @ExpectPlatform
    public static CreativeModeTab getCreativeTab() {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }

}
