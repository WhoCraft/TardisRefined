package whocraft.tardis_refined.common.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.common.world.forge.TRForgeBiomeModifiers;
import whocraft.tardis_refined.registry.FeatureKeys;
import whocraft.tardis_refined.registry.TagKeys;

import java.util.HashMap;
import java.util.Map;

public class BiomeModifierProvider {

    public static Map<ResourceLocation, BiomeModifier> BIOME_MODIFIERS = new HashMap<>();

    /** Define biome modifiers to be generated here*/
    private static void addBiomeModifiers(RegistryAccess registries, RegistryOps<JsonElement> ops){

        //Root Cluster Biome Modifier
        //Create a Standalone Reference Holder as we don't want to inline the entire contents of our placed feature
        Holder<PlacedFeature> rootHolder = Holder.Reference.createStandAlone(registries.registry(Registry.PLACED_FEATURE_REGISTRY).get(), RegistryHelper.makePlacedFeatureKey(FeatureKeys.TARDIS_ROOT_CLUSTER_RL));
        //Add the placed feature(s) to the HolderSet
        final HolderSet<PlacedFeature> rootHolderSet = HolderSet.direct(rootHolder);
        //Use Named HolderSet to reference our Biome Tag
        final HolderSet.Named<Biome> rootBiomesTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), TagKeys.TARDIS_ROOT_CLUSTER);
        final BiomeModifier rootClusterModifier = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                rootBiomesTag, //Valid biomes to spawn in
                rootHolderSet,
                GenerationStep.Decoration.LOCAL_MODIFICATIONS //Occurs just before underground structures phase, similar to vanilla Amethyst Geodes
        );

        BIOME_MODIFIERS.put(TRForgeBiomeModifiers.ADD_TARDIS_CORAL_CLUSTER, rootClusterModifier);
    }

    public static void genBiomeModifiers(GatherDataEvent e) {
        final DataGenerator generator = e.getGenerator();
        /* Using builtinCopy() averts the need to register json-only objects before we datagen them.
         * DO NOT CALL THIS MORE THAN ONCE.
         * Each builtinCopy() makes a copy of the registry which will fail when the TagKey's registry tries to check against the RegistryOps
         * TODO: 1.19.3: Use RegistrySetBuilder
         */
        final RegistryAccess registries = RegistryAccess.builtinCopy();
        final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registries);
        registries.registry(Registry.PLACED_FEATURE_REGISTRY).get();

        addBiomeModifiers(registries, ops);

        //TODO: 1.19.3: Use DatapackBuiltinEntriesProvider
        final DataProvider biomeModifierProvider = JsonCodecProvider.forDatapackRegistry(generator, e.getExistingFileHelper(), TardisRefined.MODID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, BIOME_MODIFIERS);
        generator.addProvider(e.includeServer(), biomeModifierProvider);
    }
}