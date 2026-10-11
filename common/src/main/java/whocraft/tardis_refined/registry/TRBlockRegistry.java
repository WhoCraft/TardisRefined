package whocraft.tardis_refined.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.RootPlantBlock;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.device.*;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.door.RootShellDoorBlock;
import whocraft.tardis_refined.common.block.life.ARSLeavesBlock;
import whocraft.tardis_refined.common.block.life.ArsEggBlock;
import whocraft.tardis_refined.common.block.life.EyeBlock;
import whocraft.tardis_refined.common.block.life.GrowthStoneBlock;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;

import java.util.function.Supplier;

public class TRBlockRegistry {

    public static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(TardisRefined.MODID, Registries.BLOCK);
    // Shell Blocks
    public static final RegistrySupplier<ShellBaseBlock> ROOT_SHELL_BLOCK = register("root_shell", () -> new RootedShellBlock(blockProperties("root_shell").noOcclusion().strength(1000, 1000).sound(SoundType.CORAL_BLOCK)), true, true);
    public static final RegistrySupplier<ShellBaseBlock> GLOBAL_SHELL_BLOCK = register("tardis_shell", () -> new GlobalShellBlock(blockProperties("tardis_shell").noOcclusion().strength(1000, 1000).sound(SoundType.STONE).lightLevel((blocksState) -> {
        return blocksState.getValue(GlobalShellBlock.LIT) ? 13 : 0;
    })), false, false);
    // Interior
    public static final RegistrySupplier<GlobalDoorBlock> GLOBAL_DOOR_BLOCK = register("tardis_door", () -> new GlobalDoorBlock(blockProperties("tardis_door").noOcclusion().strength(10, 10).sound(SoundType.STONE)), true, true);
    public static final RegistrySupplier<RootShellDoorBlock> ROOT_SHELL_DOOR = register("root_shell_door", () -> new RootShellDoorBlock(blockProperties("root_shell_door").noOcclusion().strength(1000, 1000)), false, true);
    // Generation Blocks
    public static final RegistrySupplier<Block> FOOLS_STONE = register("fools_stone", () -> new GrowthStoneBlock(blockProperties("fools_stone").strength(3)), true, true);
    // Roots
    public static final RegistrySupplier<RootPlantBlock> ROOT_PLANT_BLOCK = register("root_plant", () -> new RootPlantBlock(blockProperties("root_plant").noOcclusion().strength(3, 3).sound(SoundType.CORAL_BLOCK)), true, true);
    public static final RegistrySupplier<BulkHeadDoorBlock> BULK_HEAD_DOOR = register("bulk_head_door", () -> new BulkHeadDoorBlock(blockProperties("bulk_head_door").noOcclusion().strength(3, 3).sound(SoundType.CORAL_BLOCK)), true, true);
    // ARS Tree
    public static final RegistrySupplier<ArsEggBlock> ARS_EGG = register("ars_egg", () -> new ArsEggBlock(blockProperties("ars_egg").noOcclusion().strength(3, 3).sound(SoundType.AZALEA_LEAVES).lightLevel((x) -> 12)), true, true);
    public static final RegistrySupplier<Block> ARS_LEAVES = register("ars_leaves", () -> new ARSLeavesBlock(blockProperties("ars_leaves").noOcclusion().strength(3, 3).sound(SoundType.AZALEA_LEAVES)), false, true);

    //////////// REMOVE THESE BLOCKS FROM CREATIVE TABS BEFORE PRODUCTION
    public static final RegistrySupplier<SlabBlock> ARS_LEAVES_SLAB = register("ars_leaves_slab", () -> new SlabBlock(blockProperties("ars_leaves_slab").noOcclusion().strength(3, 3).sound(SoundType.AZALEA_LEAVES)), false, true);
    public static final RegistrySupplier<FenceBlock> ARS_LEAVES_FENCE = register("ars_leaves_fence", () -> new FenceBlock(blockProperties("ars_leaves_fence").noOcclusion().strength(3, 3).sound(SoundType.AZALEA_LEAVES)), false, true);
    // Devices
    public static final RegistrySupplier<TerraformerBlock> TERRAFORMER_BLOCK = register("terraformer", () -> new TerraformerBlock(blockProperties("terraformer").strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), true, true);
    public static final RegistrySupplier<AirLockGenerationBlock> AIR_LOCK_GENERATION_BLOCK = register("air_lock_generator", () -> new AirLockGenerationBlock(blockProperties("air_lock_generator").strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), false, true);

    ///////////////////////////////////////////////////////////////////////////////
    public static final RegistrySupplier<ConsoleConfigurationBlock> CONSOLE_CONFIGURATION_BLOCK = register("console_configuration", () -> new ConsoleConfigurationBlock(blockProperties("console_configuration").strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), true, true);
    public static final RegistrySupplier<AstralManipulatorBlock> ASTRAL_MANIPULATOR_BLOCK = register("astral_manipulator", () -> new AstralManipulatorBlock(blockProperties("astral_manipulator").strength(3, 3).sound(SoundType.ANVIL).noOcclusion().lightLevel((x) -> {
        return x.getValue(AstralManipulatorBlock.POWERED) ? 15 : 0;
    })), true, true);
    public static final RegistrySupplier<AntiGravityBlock> GRAVITY_WELL = register("gravity_well", () -> new AntiGravityBlock(blockProperties("gravity_well").strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), true, true);
    public static final RegistrySupplier<CorridorTeleporterBlock> CORRIDOR_TELEPORTER = register("corridor_teleporter", () -> new CorridorTeleporterBlock(blockProperties("corridor_teleporter").strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), true, true);
    public static final RegistrySupplier<LandingPadBlock> LANDING_PAD = register("landing_pad", () -> new LandingPadBlock(blockProperties("landing_pad").strength(3, 3).sound(SoundType.ANVIL).noOcclusion().lightLevel((x) -> {
        return 12;
    })), true, true);
    public static final RegistrySupplier<FlightDetectorBlock> FLIGHT_DETECTOR = register("flight_detector", () -> new FlightDetectorBlock(blockProperties("flight_detector").strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), true, true);
    public static final RegistrySupplier<ArtronPillarBlock> ARTRON_PILLAR = register("artron_pillar", () -> new ArtronPillarBlock(blockProperties("artron_pillar").strength(3, 3).sound(SoundType.ANVIL).noOcclusion()), false, false);
    // Console
    public static final RegistrySupplier<GlobalConsoleBlock> GLOBAL_CONSOLE_BLOCK = register("tardis_console", () -> new GlobalConsoleBlock(blockProperties("tardis_console").strength(1000, 1000).sound(SoundType.ANVIL).noOcclusion().lightLevel((x) -> {
        return x.getValue(GlobalConsoleBlock.POWERED) ? 15 : 0;
    })), true, true);
    // Blocks
    public static final RegistrySupplier<Block> ZEITON_BLOCK = register("zeiton_block", () -> new Block(blockProperties("zeiton_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)).sound(SoundType.METAL)), true, true);
    public static final RegistrySupplier<Block> ZEITON_FUSED_IRON_BLOCK = register("zeiton_fused_iron_block", () -> new Block(blockProperties("zeiton_fused_iron_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)).sound(SoundType.METAL)), true, true);
    public static final RegistrySupplier<Block> ZEITON_FUSED_COPPER_BLOCK = register("zeiton_fused_copper_block", () -> new Block(blockProperties("zeiton_fused_copper_block", BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.weathering().unaffected())).sound(SoundType.COPPER)), true, true);
    public static final RegistrySupplier<Block> ZEITON_ORE = register("zeiton_ore", () -> new Block(blockProperties("zeiton_ore").requiresCorrectToolForDrops().strength(3.0F, 3.0F)), true, true);
    public static final RegistrySupplier<Block> ZEITON_ORE_DEEPSLATE = register("deepslate_zeiton_ore", () -> new Block(blockProperties("deepslate_zeiton_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE)).requiresCorrectToolForDrops()), true, true);
    public static final RegistrySupplier<Block> THE_EYE = register("the_eye", () -> new EyeBlock(blockProperties("the_eye", BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK))), false, false);
    public static final RegistrySupplier<LanternBlock> ZEITON_LANTERN = register("zeiton_lantern", () -> new LanternBlock(blockProperties("zeiton_lantern", BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)).requiresCorrectToolForDrops()), true, true);
    public static final RegistrySupplier<Block> ARTRON_PILLAR_PORT = register("artron_pillar_port", () -> new Block(blockProperties("artron_pillar_port", BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK))), false, false);
  //  public static final RegistrySupplier<Block> ASTRAL_MAP = register("astral_map", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)), true, true);

    private static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> blockSupplier, boolean addToTab, boolean registerItem) {
        RegistrySupplier<T> registryObject = BLOCKS.register(id, blockSupplier);
        if (registerItem) {
            RegistrySupplier<Item> itemSupplier = TRItemRegistry.ITEMS.register(id, () -> new BlockItem(registryObject.get(), TRItemRegistry.itemProperties(id)));
            if (addToTab) {
                TRItemRegistry.TAB_ITEMS.add(itemSupplier);
            }
        }
        return registryObject;
    }

    private static BlockBehaviour.Properties blockProperties(String id) {
        return blockProperties(id, BlockBehaviour.Properties.of());
    }

    private static BlockBehaviour.Properties blockProperties(String id, BlockBehaviour.Properties properties) {
        return properties.setId(ResourceKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(TardisRefined.MODID, id)
        ));
    }

    private static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> blockSupplier, boolean addToTab) {
        return register(id, blockSupplier, addToTab, true);
    }


}
