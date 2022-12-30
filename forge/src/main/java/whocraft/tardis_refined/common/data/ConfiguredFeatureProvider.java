package whocraft.tardis_refined.common.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.Features;
import whocraft.tardis_refined.common.world.feature.NbtTemplateFeature;
import whocraft.tardis_refined.common.world.feature.config.NbtTemplateFeatureConfig;
import whocraft.tardis_refined.registry.FeatureKeys;

import java.util.HashMap;
import java.util.Map;

public class ConfiguredFeatureProvider {

    public static Map<ResourceLocation, ConfiguredFeature<?,?>> FEATURES = new HashMap<>();

    /** Define features to be generated here*/
    private static void addConfiguredFeatures(){

        //Root Cluster feature
        //Location of the nbt template file under data/modid/structures
        ResourceLocation templateLocation = new ResourceLocation(TardisRefined.MODID, "cave/tardis_root_cluster_deepslate");
        ConfiguredFeature<NbtTemplateFeatureConfig, NbtTemplateFeature> tardisRootCluster = new ConfiguredFeature<>(Features.NBT_FEATURE.get(), new NbtTemplateFeatureConfig(templateLocation, 0));
        FEATURES.put(FeatureKeys.TARDIS_ROOT_CLUSTER_RL, tardisRootCluster);
    }

    public static void genConfiguredFeatures(GatherDataEvent e) {
        final DataGenerator generator = e.getGenerator();
        /* Using builtinCopy() averts the need to register json-only objects before we datagen them.
         * DO NOT CALL THIS MORE THAN ONCE.
         * Each builtinCopy() makes a copy of the registry which will fail when the TagKey's registry tries to check against the RegistryOps
         * TODO: 1.19.3: Use RegistrySetBuilder
         */
        final RegistryAccess registries = RegistryAccess.builtinCopy();
        final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registries);

        addConfiguredFeatures();

        //TODO: 1.19.3: Use DatapackBuiltinEntriesProvider
        final DataProvider configuredFeatureProvider = JsonCodecProvider.forDatapackRegistry(generator, e.getExistingFileHelper(),TardisRefined.MODID, ops, Registry.CONFIGURED_FEATURE_REGISTRY, FEATURES);
        generator.addProvider(e.includeServer(), configuredFeatureProvider);
    }
}