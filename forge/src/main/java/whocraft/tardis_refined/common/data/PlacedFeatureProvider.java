package whocraft.tardis_refined.common.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.FeatureKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacedFeatureProvider {

    public static Map<ResourceLocation, PlacedFeature> FEATURES = new HashMap<>();

    /** Define features to be generated here*/
    private static void addPlacedFeatures(){

        //Root Cluster
        //Workaround for not being able to find Configured features since we don't register them by code
        final Holder<ConfiguredFeature<?,?>> rootClusterHolder = Holder.direct(ConfiguredFeatureProvider.FEATURES.get(FeatureKeys.TARDIS_ROOT_CLUSTER_RL));
        final PlacedFeature rootClusterPlacedFeature = new PlacedFeature(rootClusterHolder, List.of(
                RarityFilter.onAverageOnceEvery(15), //Chance to place
                InSquarePlacement.spread(), //In square
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(20)))); //Between y level -50 and 20 (absolute values)
        FEATURES.put(FeatureKeys.TARDIS_ROOT_CLUSTER_RL, rootClusterPlacedFeature);
    }

    public static void genPlacedFeatures(GatherDataEvent e) {

        final DataGenerator generator = e.getGenerator();
        /* Using builtinCopy() averts the need to register json-only objects before we datagen them.
         * DO NOT CALL THIS MORE THAN ONCE.
         * Each builtinCopy() makes a copy of the registry which will fail when the TagKey's registry tries to check against the RegistryOps
         * TODO: 1.19.3: Use RegistrySetBuilder
         */
        final RegistryAccess registries = RegistryAccess.builtinCopy();
        final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registries);

        addPlacedFeatures();
        //TODO: 1.19.3: Use DatapackBuiltinEntriesProvider
        final DataProvider placedFeatureProvider = JsonCodecProvider.forDatapackRegistry(generator, e.getExistingFileHelper(),TardisRefined.MODID, ops, Registry.PLACED_FEATURE_REGISTRY, FEATURES);
        generator.addProvider(e.includeServer(), placedFeatureProvider);
    }
}
