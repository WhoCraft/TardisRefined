package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.RootPlantBlock;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.device.*;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.door.InternalDoorBlock;
import whocraft.tardis_refined.common.block.door.RootShellDoorBlock;
import whocraft.tardis_refined.common.block.life.ArsEggBlock;
import whocraft.tardis_refined.common.block.life.GrowthStoneBlock;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;


import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(TardisRefined.MODID, Registry.BLOCK_REGISTRY);

    private static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> blockSupplier, boolean addToTab, boolean registerItem) {
        RegistrySupplier<T> registryObject = BLOCKS.register(id, blockSupplier);
        if (registerItem) {
            if(addToTab) {
                ItemRegistry.ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(ItemRegistry.MAIN_TAB)));
            }
            else {
                ItemRegistry.ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties()));
            }
        }
        return registryObject;
    }

    private static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> blockSupplier, boolean addToTab) {
        return register(id, blockSupplier, addToTab, true);
    }


    // Shell Blocks
    public static final RegistrySupplier<ShellBaseBlock> ROOT_SHELL_BLOCK = register("root_shell", () -> new RootedShellBlock(BlockBehaviour.Properties.of(Material.BARRIER).noOcclusion().strength(1000, 1000).sound(SoundType.CORAL_BLOCK)), true, true);
    public static final RegistrySupplier<ShellBaseBlock> GLOBAL_SHELL_BLOCK = register("tardis_shell", () -> new GlobalShellBlock(BlockBehaviour.Properties.of(Material.BARRIER).noOcclusion().strength(1000, 1000).sound(SoundType.STONE)), false, false);
    public static final RegistrySupplier<GlobalDoorBlock> GLOBAL_DOOR_BLOCK = register("tardis_door", () -> new GlobalDoorBlock(BlockBehaviour.Properties.of(Material.BARRIER).noOcclusion().strength(10, 10).sound(SoundType.STONE)), true, true);

    // Interior
    public static final RegistrySupplier<InternalDoorBlock> INTERNAL_DOOR_BLOCK = register("internal_door_block", () -> new InternalDoorBlock(BlockBehaviour.Properties.of(Material.STONE).noOcclusion()), true, false);
    public static final RegistrySupplier<RootShellDoorBlock> ROOT_SHELL_DOOR = register("root_shell_door", () -> new RootShellDoorBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(1000, 1000)), false, true);

    // Generation Blocks
    public static final RegistrySupplier<Block> GROWTH_STONE = register("growth_stone", () -> new GrowthStoneBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3)), true, true);
    public static final RegistrySupplier<Block> HARDENED_GROWTH_STONE = register("hardened_growth_stone", () -> new Block(BlockBehaviour.Properties.of(Material.BARRIER).strength(10000, 10000)), true, true);


    // Roots
    public static final RegistrySupplier<RootPlantBlock> ROOT_PLANT_BLOCK = register("root_plant", () -> new RootPlantBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(3, 3).sound(SoundType.CORAL_BLOCK)), true, true);

    public static final RegistrySupplier<BulkHeadDoorBlock> BULK_HEAD_DOOR = register("bulk_head_door", () -> new BulkHeadDoorBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(3, 3).sound(SoundType.CORAL_BLOCK)), true, true);

    //////////// REMOVE THESE BLOCKS FROM CREATIVE TABS BEFORE PRODUCTION

    // ARS Tree
    public static final RegistrySupplier<ArsEggBlock> ARS_EGG = register("ars_egg", () -> new ArsEggBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(3, 3).sound(SoundType.AZALEA_LEAVES).lightLevel((x) -> {
        return 12;
    })), false, true);
    public static final RegistrySupplier<Block> ARS_LEAVES = register("ars_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(3, 3).sound(SoundType.AZALEA_LEAVES)), false, true);
    public static final RegistrySupplier<SlabBlock> ARS_LEAVES_SLAB = register("ars_leaves_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(3, 3).sound(SoundType.AZALEA_LEAVES)), false, true);
    public static final RegistrySupplier<FenceBlock> ARS_LEAVES_FENCE = register("ars_leaves_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().strength(3, 3).sound(SoundType.AZALEA_LEAVES)), false, true);

    ///////////////////////////////////////////////////////////////////////////////

    // Devices
    public static final RegistrySupplier<TerraformerBlock> TERRAFORMER_BLOCK = register("terraformer", () -> new TerraformerBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), true, true);
    public static final RegistrySupplier<AirLockGenerationBlock> AIR_LOCK_GENERATION_BLOCK = register("air_lock_generator", () -> new AirLockGenerationBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), false, true);
    public static final RegistrySupplier<ConsoleConfigurationBlock> CONSOLE_CONFIGURATION_BLOCK = register("console_configuration", () -> new ConsoleConfigurationBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), true, true);

    public static final RegistrySupplier<LandingPad> LANDING_PAD = register("landing_pad", () -> new LandingPad(BlockBehaviour.Properties.of(Material.METAL).strength(3, 3).sound(SoundType.ANVIL).noOcclusion().lightLevel((x) -> {
        return 12;
    })), true, true);


    public static final RegistrySupplier<FlightDetectorBlock> FLIGHT_DETECTOR = register("flight_detector", () -> new FlightDetectorBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), true, true);


    // Console
    public static final RegistrySupplier<GlobalConsoleBlock> GLOBAL_CONSOLE_BLOCK = register("tardis_console", () -> new GlobalConsoleBlock(BlockBehaviour.Properties.of(Material.METAL).strength(1000, 1000).sound(SoundType.ANVIL).noOcclusion().lightLevel((x) -> {
        return 15;
    })), true, true);



}
