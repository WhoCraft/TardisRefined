package whocraft.tardis_refined.common.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.registry.TRConsoleThemes;

/** Helper for creating common objects.
 * Allows for smoother transition to future versions where registries change*/
public class RegistryHelper {

    public static ResourceLocation makeKey(String id){
        return new ResourceLocation(TardisRefined.MODID, id);
    }

    public static ResourceKey<ConfiguredFeature<?,?>> makeConfiguredFeatureKey(ResourceLocation rl){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, rl);
    }

    public static ResourceKey<PlacedFeature> makePlacedFeatureKey(ResourceLocation rl){
        return ResourceKey.create(Registries.PLACED_FEATURE, rl);
    }

    public static TagKey<Biome> makeGenericBiomeTagCollection(String name){
        return TagKey.create(Registries.BIOME, new ResourceLocation(TardisRefined.MODID, "collections/" + name));
    }

    public static TagKey<Biome> makeBiomeTagForFeature(String name){
        return TagKey.create(Registries.BIOME, new ResourceLocation(TardisRefined.MODID, "has_structure/" + name));
    }

    public static ResourceLocation getKey(ConsoleTheme theme){
        return TRConsoleThemes.CONSOLE_THEME_REGISTRY.getKey(theme);
    }

    public static ResourceLocation getKey(ShellTheme theme){
        return ShellTheme.SHELL_THEME_REGISTRY.getKey(theme);
    }
}
