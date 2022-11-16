package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.desktop.InternalDoorBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;

import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(TardisRefined.MODID, Registry.BLOCK_REGISTRY);

    // Shell Blocks
    public static final RegistrySupplier<ShellBaseBlock> SHELL_BASE_BLOCK = register("shell_base_block", () -> new RootedShellBlock( BlockBehaviour.Properties.of(Material.STONE)), ItemRegistry.MAIN_TAB, true);
    public static final RegistrySupplier<InternalDoorBlock> INTERNAL_DOOR_BLOCK = register("internal_door_block", () -> new InternalDoorBlock( BlockBehaviour.Properties.of(Material.STONE).noOcclusion()), ItemRegistry.MAIN_TAB, true);

    private static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> blockSupplier, CreativeModeTab itemGroup, boolean registerItem) {
        RegistrySupplier<T> registryObject = BLOCKS.register(id, blockSupplier);
        if(registerItem) {
            ItemRegistry.ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(itemGroup)));
        }
        return registryObject;
    }

    private static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> blockSupplier, CreativeModeTab itemGroup) {
        return register(id, blockSupplier, itemGroup, true);
    }

}
