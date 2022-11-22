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
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.door.InternalDoorBlock;
import whocraft.tardis_refined.common.block.door.RootShellDoorBlock;
import whocraft.tardis_refined.common.block.device.TerraformerBlock;
import whocraft.tardis_refined.common.block.RootPlantBlock;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;

import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(TardisRefined.MODID, Registry.BLOCK_REGISTRY);

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


    // Shell Blocks
    public static final RegistrySupplier<ShellBaseBlock> ROOT_SHELL_BLOCK = register("root_shell", () -> new RootedShellBlock( BlockBehaviour.Properties.of(Material.BARRIER).noOcclusion().strength(1000,1000).sound(SoundType.CORAL_BLOCK)), ItemRegistry.MAIN_TAB, true);
    public static final RegistrySupplier<ShellBaseBlock> GLOBAL_SHELL_BLOCK = register("tardis_shell", () -> new GlobalShellBlock( BlockBehaviour.Properties.of(Material.BARRIER).noOcclusion().strength(1000,1000).sound(SoundType.STONE)), null, false);
    public static final RegistrySupplier<GlobalDoorBlock> GLOBAL_DOOR_BLOCK = register("tardis_door", () -> new GlobalDoorBlock( BlockBehaviour.Properties.of(Material.BARRIER).noOcclusion().strength(10,10).sound(SoundType.STONE)), ItemRegistry.MAIN_TAB, true);

    // Interior
    public static final RegistrySupplier<InternalDoorBlock> INTERNAL_DOOR_BLOCK = register("internal_door_block", () -> new InternalDoorBlock( BlockBehaviour.Properties.of(Material.STONE).noOcclusion()), ItemRegistry.MAIN_TAB, true);
    public static final RegistrySupplier<RootShellDoorBlock> ROOT_SHELL_DOOR = register("root_shell_door", () -> new RootShellDoorBlock( BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(1000,1000)), null, true);

    // Roots
    public static final RegistrySupplier<RootPlantBlock> ROOT_PLANT_BLOCK = register("root_plant", () -> new RootPlantBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(3,3).sound(SoundType.CORAL_BLOCK)), ItemRegistry.MAIN_TAB, true);

    // Devices
    public static final RegistrySupplier<TerraformerBlock> TERRAFORMER_BLOCK = register("terraformer", () -> new TerraformerBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3,3).sound(SoundType.ANVIL).noOcclusion()), ItemRegistry.MAIN_TAB, true);

    // Console
    public static final RegistrySupplier<GlobalConsoleBlock> GLOBAL_CONSOLE_BLOCK = register("tardis_console", () -> new GlobalConsoleBlock(BlockBehaviour.Properties.of(Material.METAL).strength(1000,1000).sound(SoundType.ANVIL).noOcclusion()), null, true);

}
