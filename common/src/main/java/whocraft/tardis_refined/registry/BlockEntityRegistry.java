package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.blockentity.desktop.InternalDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.RootedShellBlockEntity;

public class BlockEntityRegistry {
    public static final DeferredRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegistry.create(TardisRefined.MODID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<BlockEntityType<RootedShellBlockEntity>> TARDIS_SHELL = BLOCK_ENTITY_TYPES.register("tardis_root_shell", () -> BlockEntityType.Builder.of(RootedShellBlockEntity::new, BlockRegistry.SHELL_BASE_BLOCK.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<InternalDoorBlockEntity>> TARDIS_INTERNAL_DOOR = BLOCK_ENTITY_TYPES.register("internal_door_block", () -> BlockEntityType.Builder.of(InternalDoorBlockEntity::new, BlockRegistry.INTERNAL_DOOR_BLOCK.get()).build(null));

}
