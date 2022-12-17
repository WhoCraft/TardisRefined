package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.desktop.InternalDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.desktop.door.RootShellDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.device.ConsoleConfigurationBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.RootPlantBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.RootedShellBlockEntity;

public class BlockEntityRegistry {
    public static final DeferredRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegistry.create(TardisRefined.MODID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<RootedShellBlockEntity>> ROOT_SHELL = BLOCK_ENTITY_TYPES.register("root_shell", () -> BlockEntityType.Builder.of(RootedShellBlockEntity::new, BlockRegistry.ROOT_SHELL_BLOCK.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<GlobalConsoleBlockEntity>> GLOBAL_CONSOLE_BLOCK = BLOCK_ENTITY_TYPES.register("tardis_console", () -> BlockEntityType.Builder.of(GlobalConsoleBlockEntity::new, BlockRegistry.GLOBAL_CONSOLE_BLOCK.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<GlobalShellBlockEntity>> GLOBAL_SHELL_BLOCK = BLOCK_ENTITY_TYPES.register("tardis_shell", () -> BlockEntityType.Builder.of(GlobalShellBlockEntity::new, BlockRegistry.GLOBAL_SHELL_BLOCK.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<GlobalDoorBlockEntity>> GLOBAL_DOOR_BLOCK = BLOCK_ENTITY_TYPES.register("tardis_door", () -> BlockEntityType.Builder.of(GlobalDoorBlockEntity::new, BlockRegistry.GLOBAL_DOOR_BLOCK.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<InternalDoorBlockEntity>> TARDIS_INTERNAL_DOOR = BLOCK_ENTITY_TYPES.register("internal_door_block", () -> BlockEntityType.Builder.of(InternalDoorBlockEntity::new, BlockRegistry.INTERNAL_DOOR_BLOCK.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<RootShellDoorBlockEntity>> ROOT_SHELL_DOOR = BLOCK_ENTITY_TYPES.register("root_shell_door", () -> BlockEntityType.Builder.of(RootShellDoorBlockEntity::new, BlockRegistry.ROOT_SHELL_DOOR.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<RootPlantBlockEntity>> ROOT_PLANT = BLOCK_ENTITY_TYPES.register("root_plant", () -> BlockEntityType.Builder.of(RootPlantBlockEntity::new, BlockRegistry.ROOT_PLANT_BLOCK.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<ArsEggBlockEntity>> ARS_EGG = BLOCK_ENTITY_TYPES.register("ars_egg", () -> BlockEntityType.Builder.of(ArsEggBlockEntity::new, BlockRegistry.ARS_EGG.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BulkHeadDoorBlockEntity>> BULK_HEAD_DOOR = BLOCK_ENTITY_TYPES.register("bulk_head_door", () -> BlockEntityType.Builder.of(BulkHeadDoorBlockEntity::new, BlockRegistry.BULK_HEAD_DOOR.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<ConsoleConfigurationBlockEntity>> CONSOLE_CONFIGURATION = BLOCK_ENTITY_TYPES.register("console_configuration", () -> BlockEntityType.Builder.of(ConsoleConfigurationBlockEntity::new, BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get()).build(null));

}
