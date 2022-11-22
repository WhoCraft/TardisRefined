package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.entity.ControlEntity;

public class EntityRegistry {

    public static final DeferredRegistry<EntityType<?>> ENTITY_TYPES = DeferredRegistry.create(TardisRefined.MODID, Registry.ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<EntityType<ControlEntity>> CONTROL_ENTITY = ENTITY_TYPES.register("console_control", () -> EntityType.Builder.of((EntityType.EntityFactory<ControlEntity>) (entityType, level) -> new ControlEntity(entityType, level), MobCategory.MISC).sized(1, 1).build(TardisRefined.MODID + ":console_control"));

}
