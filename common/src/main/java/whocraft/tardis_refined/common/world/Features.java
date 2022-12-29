package whocraft.tardis_refined.common.world;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.feature.NbtTemplateFeature;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class Features {

    public static final DeferredRegistry<Feature<?>> FEATURES = DeferredRegistry.create(TardisRefined.MODID, Registry.FEATURE_REGISTRY);

    public static final RegistrySupplier<NbtTemplateFeature> NBT_FEATURE = FEATURES.register("nbt_feature", NbtTemplateFeature::new);
}
