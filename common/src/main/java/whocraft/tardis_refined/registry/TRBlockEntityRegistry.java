package whocraft.tardis_refined.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.device.*;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.RootShellDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;
import whocraft.tardis_refined.common.blockentity.life.EyeBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.RootPlantBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.RootedShellBlockEntity;

public class TRBlockEntityRegistry {
    public static final DeferredRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegistry.create(TardisRefined.MODID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<RootedShellBlockEntity>> ROOT_SHELL = BLOCK_ENTITY_TYPES.register("root_shell", () -> new BlockEntityType<>(RootedShellBlockEntity::new, java.util.Set.of(TRBlockRegistry.ROOT_SHELL_BLOCK.get())));

    public static final RegistrySupplier<BlockEntityType<GlobalConsoleBlockEntity>> GLOBAL_CONSOLE_BLOCK = BLOCK_ENTITY_TYPES.register("tardis_console", () -> new BlockEntityType<>(GlobalConsoleBlockEntity::new, java.util.Set.of(TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get())));

    public static final RegistrySupplier<BlockEntityType<GlobalShellBlockEntity>> GLOBAL_SHELL_BLOCK = BLOCK_ENTITY_TYPES.register("tardis_shell", () -> new BlockEntityType<>(GlobalShellBlockEntity::new, java.util.Set.of(TRBlockRegistry.GLOBAL_SHELL_BLOCK.get())));
    public static final RegistrySupplier<BlockEntityType<GlobalDoorBlockEntity>> GLOBAL_DOOR_BLOCK = BLOCK_ENTITY_TYPES.register("tardis_door", () -> new BlockEntityType<>(GlobalDoorBlockEntity::new, java.util.Set.of(TRBlockRegistry.GLOBAL_DOOR_BLOCK.get())));

    public static final RegistrySupplier<BlockEntityType<RootShellDoorBlockEntity>> ROOT_SHELL_DOOR = BLOCK_ENTITY_TYPES.register("root_shell_door", () -> new BlockEntityType<>(RootShellDoorBlockEntity::new, java.util.Set.of(TRBlockRegistry.ROOT_SHELL_DOOR.get())));

    public static final RegistrySupplier<BlockEntityType<RootPlantBlockEntity>> ROOT_PLANT = BLOCK_ENTITY_TYPES.register("root_plant", () -> new BlockEntityType<>(RootPlantBlockEntity::new, java.util.Set.of(TRBlockRegistry.ROOT_PLANT_BLOCK.get())));

    public static final RegistrySupplier<BlockEntityType<ArsEggBlockEntity>> ARS_EGG = BLOCK_ENTITY_TYPES.register("ars_egg", () -> new BlockEntityType<>(ArsEggBlockEntity::new, java.util.Set.of(TRBlockRegistry.ARS_EGG.get())));
    public static final RegistrySupplier<BlockEntityType<BulkHeadDoorBlockEntity>> BULK_HEAD_DOOR = BLOCK_ENTITY_TYPES.register("bulk_head_door", () -> new BlockEntityType<>(BulkHeadDoorBlockEntity::new, java.util.Set.of(TRBlockRegistry.BULK_HEAD_DOOR.get())));
    public static final RegistrySupplier<BlockEntityType<ConsoleConfigurationBlockEntity>> CONSOLE_CONFIGURATION = BLOCK_ENTITY_TYPES.register("console_configuration", () -> new BlockEntityType<>(ConsoleConfigurationBlockEntity::new, java.util.Set.of(TRBlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get())));
    public static final RegistrySupplier<BlockEntityType<FlightDetectorBlockEntity>> FLIGHT_DETECTOR = BLOCK_ENTITY_TYPES.register("flight_detector", () -> new BlockEntityType<>(FlightDetectorBlockEntity::new, java.util.Set.of(TRBlockRegistry.FLIGHT_DETECTOR.get())));
    public static final RegistrySupplier<BlockEntityType<ArtronPillarBlockEntity>> ARTRON_PILLAR = BLOCK_ENTITY_TYPES.register("artron_pillar", () -> new BlockEntityType<>(ArtronPillarBlockEntity::new, java.util.Set.of(TRBlockRegistry.ARTRON_PILLAR.get())));
    public static final RegistrySupplier<BlockEntityType<AstralManipulatorBlockEntity>> ASTRAL_MANIPULATOR = BLOCK_ENTITY_TYPES.register("astral_manipulator", () -> new BlockEntityType<>(AstralManipulatorBlockEntity::new, java.util.Set.of(TRBlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get())));
    public static final RegistrySupplier<BlockEntityType<EyeBlockEntity>> THE_EYE = BLOCK_ENTITY_TYPES.register("the_eye", () -> new BlockEntityType<>(EyeBlockEntity::new, java.util.Set.of(TRBlockRegistry.THE_EYE.get())));
    public static final RegistrySupplier<BlockEntityType<CorridorTeleporterBlockEntity>> CORRIDOR_TELEPORTER = BLOCK_ENTITY_TYPES.register("corridor_teleporter", () -> new BlockEntityType<>(CorridorTeleporterBlockEntity::new, java.util.Set.of(TRBlockRegistry.CORRIDOR_TELEPORTER.get())));


}
