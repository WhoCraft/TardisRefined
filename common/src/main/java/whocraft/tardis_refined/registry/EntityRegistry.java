package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.entity.ControlEntity;

public class EntityRegistry {

    public static final DeferredRegistry<EntityType<?>> ENTITY_TYPES = DeferredRegistry.create(TardisRefined.MODID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<ControlEntity>> CONTROL_ENTITY = ENTITY_TYPES.register("console_control", () -> EntityType.Builder.of((EntityType.EntityFactory<ControlEntity>) (entityType, level) -> new ControlEntity(level), MobCategory.AMBIENT).sized(0.125F, 0.125F).build(TardisRefined.MODID + ":console_control"));

}
