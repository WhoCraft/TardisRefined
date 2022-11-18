package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.desktop.InternalDoorBlock;
import whocraft.tardis_refined.common.block.desktop.door.RootShellDoorBlock;
import whocraft.tardis_refined.common.block.shell.RootPlantBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;

import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(TardisRefined.MODID, Registry.BLOCK_REGISTRY);

    // Shell Blocks
    public static final RegistrySupplier<ShellBaseBlock> ROOT_SHELL_BLOCK = register("root_shell", () -> new RootedShellBlock( BlockBehaviour.Properties.of(Material.BARRIER).noOcclusion().strength(1000,1000).sound(SoundType.CORAL_BLOCK)), ItemRegistry.MAIN_TAB, true);

    // Interior
    public static final RegistrySupplier<InternalDoorBlock> INTERNAL_DOOR_BLOCK = register("internal_door_block", () -> new InternalDoorBlock( BlockBehaviour.Properties.of(Material.STONE).noOcclusion()), ItemRegistry.MAIN_TAB, true);
    public static final RegistrySupplier<RootShellDoorBlock> ROOT_SHELL_DOOR = register("root_shell_door", () -> new RootShellDoorBlock( BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(1000,1000)), null, true);

    // Roots
    public static final RegistrySupplier<RootPlantBlock> ROOT_PLANT_BLOCK = register("root_plant", () -> new RootPlantBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(3,3).sound(SoundType.CORAL_BLOCK)), ItemRegistry.MAIN_TAB, true);

    private static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> blockSupplier, CreativeModeTab itemGroup, boolean registerItem) {
        RegistrySupplier<T> registryObject = BLOCKS.register(id, blockSupplier);
        if(registerItem) {
            if (itemGroup != null) {
                ItemRegistry.ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(itemGroup)));
            } else {
                ItemRegistry.ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties()));
            }

        }
        return registryObject;
    }

    private static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> blockSupplier, CreativeModeTab itemGroup) {
        return register(id, blockSupplier, itemGroup, true);
    }

}
