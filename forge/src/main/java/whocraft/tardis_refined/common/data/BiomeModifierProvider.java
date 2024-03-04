package whocraft.tardis_refined.common.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.Features;
import whocraft.tardis_refined.common.world.feature.NbtTemplateFeature;
import whocraft.tardis_refined.common.world.feature.config.NbtTemplateFeatureConfig;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.FeatureKeys;
import whocraft.tardis_refined.registry.TagKeys;

import java.util.List;
import java.util.Set;

import static net.minecraft.data.worldgen.features.FeatureUtils.createKey;
import static whocraft.tardis_refined.common.data.ProviderConfiguredFeatures.TARDIS_ROOT_CLUSTER_CONF_FEATURE;
import static whocraft.tardis_refined.common.data.ProviderPlacedFeatures.TARDIS_ROOT_CLUSTER_PLACED_FEATUE;

public class BiomeModifierProvider {


    private static final ResourceKey<BiomeModifier> ADD_TARDIS_ROOT_CLUSTER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FeatureKeys.TARDIS_ROOT_CLUSTER_RL);

    private static final ResourceKey<BiomeModifier> ZEITON = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TardisRefined.MODID, "zeiton"));
    private static final ResourceKey<BiomeModifier> ZEITON_SMALL = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(TardisRefined.MODID, "zeiton_small"));


    public static void bootstrap(BootstapContext<BiomeModifier> context) {

        var overworldTags = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD);

        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);

        BiomeModifiers.AddFeaturesBiomeModifier oreModifer = new BiomeModifiers.AddFeaturesBiomeModifier(overworldTags, HolderSet.direct(placed.getOrThrow(ProviderPlacedFeatures.ORE_ZEITON)), GenerationStep.Decoration.UNDERGROUND_ORES);
        BiomeModifiers.AddFeaturesBiomeModifier oreModiferSmall = new BiomeModifiers.AddFeaturesBiomeModifier(overworldTags, HolderSet.direct(placed.getOrThrow(ProviderPlacedFeatures.ORE_ZEITON_SMALL)), GenerationStep.Decoration.UNDERGROUND_ORES);

        context.register(ZEITON, oreModifer);
        context.register(ZEITON_SMALL, oreModiferSmall);

        var tardisRoot = context.lookup(Registries.BIOME).getOrThrow(TagKeys.TARDIS_ROOT_CLUSTER);

        context.register(ADD_TARDIS_ROOT_CLUSTER, new BiomeModifiers.AddFeaturesBiomeModifier(
                tardisRoot,
                HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(TARDIS_ROOT_CLUSTER_PLACED_FEATUE)),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));
    }
}