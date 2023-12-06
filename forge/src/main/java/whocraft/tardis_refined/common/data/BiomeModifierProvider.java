package whocraft.tardis_refined.common.data;

import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.ForgeRegistries;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.Features;
import whocraft.tardis_refined.common.world.feature.NbtTemplateFeature;
import whocraft.tardis_refined.common.world.feature.config.NbtTemplateFeatureConfig;
import whocraft.tardis_refined.registry.FeatureKeys;
import whocraft.tardis_refined.registry.TagKeys;

import java.util.List;
import java.util.Set;

public class BiomeModifierProvider {
    public static final ResourceKey<PlacedFeature> TARDIS_ROOT_CLUSTER_PLACED_FEATUE = ResourceKey.create(Registries.PLACED_FEATURE, FeatureKeys.TARDIS_ROOT_CLUSTER_RL);
    public static final ResourceKey<ConfiguredFeature<?, ?>> TARDIS_ROOT_CLUSTER_CONF_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, FeatureKeys.TARDIS_ROOT_CLUSTER_RL);
    private static final ResourceKey<BiomeModifier> ADD_TARDIS_ROOT_CLUSTER = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, FeatureKeys.TARDIS_ROOT_CLUSTER_RL);

    public static void genBiomeModifiers(GatherDataEvent e) {
        final DataGenerator generator = e.getGenerator();
        final RegistrySetBuilder builder = new RegistrySetBuilder();

        ResourceLocation templateLocation = new ResourceLocation(TardisRefined.MODID, "cave/tardis_root_cluster_deepslate");
        ConfiguredFeature<NbtTemplateFeatureConfig, NbtTemplateFeature> tardisRootCluster = new ConfiguredFeature<>(Features.NBT_FEATURE.get(), new NbtTemplateFeatureConfig(templateLocation, 0));

        builder.add(Registries.CONFIGURED_FEATURE, context -> context.register(TARDIS_ROOT_CLUSTER_CONF_FEATURE, tardisRootCluster));
        builder.add(Registries.PLACED_FEATURE, context -> context.register(TARDIS_ROOT_CLUSTER_PLACED_FEATUE, new PlacedFeature
                (context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(TARDIS_ROOT_CLUSTER_CONF_FEATURE),
                        List.of(
                                RarityFilter.onAverageOnceEvery(25),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(20))))
        ));
        builder.add(ForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
            var tardisRoot = context.lookup(Registries.BIOME).getOrThrow(TagKeys.TARDIS_ROOT_CLUSTER);

            context.register(ADD_TARDIS_ROOT_CLUSTER, new BiomeModifiers.AddFeaturesBiomeModifier(
                    tardisRoot,
                    HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(TARDIS_ROOT_CLUSTER_PLACED_FEATUE)),
                    GenerationStep.Decoration.LOCAL_MODIFICATIONS
            ));
        });

        final DataProvider biomeModifierProvider = new DatapackBuiltinEntriesProvider(generator.getPackOutput(), e.getLookupProvider(), builder, Set.of(TardisRefined.MODID));
        generator.addProvider(e.includeServer(), biomeModifierProvider);
    }
}