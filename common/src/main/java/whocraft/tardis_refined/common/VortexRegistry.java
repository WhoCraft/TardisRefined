package whocraft.tardis_refined.common;

import net.minecraft.util.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.renderer.vortex.VortexGradientTint;
import whocraft.tardis_refined.common.tardis.themes.Theme;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.RegistrySupplierHolder;

public class VortexRegistry implements Theme {

    /**
     * Registry Key for the Vortex registry.
     */
    public static final ResourceKey<Registry<VortexRegistry>> VORTEX_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "vortex"));

    /**
     * Deferred Registry for Vortex entries.
     */
    public static final DeferredRegistry<VortexRegistry> VORTEX_DEFERRED_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, VORTEX_REGISTRY_KEY, true);

  
    // Vortex entries
    public static final RegistrySupplier<VortexRegistry> CLOUDS = registerVortex(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "clouds"), Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"), 9, 12, 10f, true, true, VortexGradientTint.BlueOrngGradient, false);
    public static final RegistrySupplier<VortexRegistry> WAVES = registerVortex(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "waves"), Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/waves.png"), 9, 12, 20f, true, true, VortexGradientTint.BlueOrngGradient, false);
    public static final RegistrySupplier<VortexRegistry> STARS = registerVortex(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "stars"), Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/stars.png"), 9, 12, 5f, true, true, VortexGradientTint.PASTEL_GRADIENT, true);
    public static final RegistrySupplier<VortexRegistry> FLOW = registerVortex(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "flow"), Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"), 9, 12, 5f, true, true, VortexGradientTint.MODERN_VORTEX, true);
    public static final RegistrySupplier<VortexRegistry> SPACE = registerVortex(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "space"), Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/stars_2.png"), 9, 12, 5f, true, true, VortexGradientTint.MODERN_VORTEX, false);

    public static final RegistrySupplier<VortexRegistry> TWILIGHT_GLOW = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "twilight_glow"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.TWILIGHT_GLOW, false);

    public static final RegistrySupplier<VortexRegistry> AURORA_DREAMS = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "aurora_dreams"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.AURORA_DREAMS, false);

    public static final RegistrySupplier<VortexRegistry> DESERT_MIRAGE = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "desert_mirage"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.DESERT_MIRAGE, false);

    public static final RegistrySupplier<VortexRegistry> NEON_PULSE = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "neon_pulse"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.NEON_PULSE, false);

    public static final RegistrySupplier<VortexRegistry> OCEAN_BREEZE = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "ocean_breeze"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.OCEAN_BREEZE, false);

    public static final RegistrySupplier<VortexRegistry> SOLAR_FLARE = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "solar_flare"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.SOLAR_FLARE, false);

    public static final RegistrySupplier<VortexRegistry> CRYSTAL_LAGOON = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "crystal_lagoon"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.CRYSTAL_LAGOON, false);

    public static final RegistrySupplier<VortexRegistry> VELVET_NIGHT = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "velvet_night"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.VELVET_NIGHT, false);

    public static final RegistrySupplier<VortexRegistry> CANDY_POP = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "candy_pop"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.CANDY_POP, false);

    public static final RegistrySupplier<VortexRegistry> EMERALD_FOREST = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "emerald_forest"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.EMERALD_FOREST, false);

    public static final RegistrySupplier<VortexRegistry> LGBT_RAINBOW = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "lgbt_rainbow"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.LGBT_RAINBOW, false);

    public static final RegistrySupplier<VortexRegistry> TRANSGENDER_FLAG = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "transgender_flag"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.TRANSGENDER_FLAG, false);

    public static final RegistrySupplier<VortexRegistry> BISEXUAL_FLAG = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "bisexual_flag"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.BISEXUAL_FLAG, false);

    public static final RegistrySupplier<VortexRegistry> LESBIAN_FLAG = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "lesbian_flag"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.LESBIAN_FLAG, false);

    public static final RegistrySupplier<VortexRegistry> NON_BINARY_FLAG = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "non_binary_flag"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.NON_BINARY_FLAG, false);

    public static final RegistrySupplier<VortexRegistry> AGENDER_FLAG = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "agender_flag"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.AGENDER_FLAG, false);

    public static final RegistrySupplier<VortexRegistry> GAY_FLAG = registerVortex(
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "gay_flag"),
            Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.GAY_FLAG, false);


    private final Identifier texture;
    private final int sides;
    private final int rows;
    private final float twist;
    private final boolean lightning;
    private final boolean decals;
    private final VortexGradientTint gradient;
    private final boolean movingGradient;
    private Identifier translationKey;

    public VortexRegistry(Identifier id, Identifier texture, int sides, int rows, float twist, boolean lightning, boolean decals, VortexGradientTint gradient, boolean movingGradient) {
        this.texture = texture;
        this.sides = sides;
        this.rows = rows;
        this.twist = twist;
        this.lightning = lightning;
        this.decals = decals;
        this.gradient = gradient;
        this.movingGradient = movingGradient;
        this.translationKey = id;
    }

    private static RegistrySupplier<VortexRegistry> registerVortex(Identifier id, Identifier texturePath, int sides, int rows, float twist, boolean lightning, boolean decals, VortexGradientTint gradient, boolean movingGradient) {
        return VORTEX_DEFERRED_REGISTRY.register(id.getPath(), () -> new VortexRegistry(
                id,
                texturePath,
                sides,
                rows,
                twist,
                lightning,
                decals,
                gradient,
                movingGradient
        ));
    }

    public Identifier getTexture() {
        return texture;
    }

    public int getSides() {
        return sides;
    }

    public int getRows() {
        return rows;
    }

    public float getTwist() {
        return twist;
    }

    public boolean hasLightning() {
        return lightning;
    }

    public boolean hasDecals() {
        return decals;
    }

    public VortexGradientTint getGradient() {
        return gradient;
    }

    public boolean isMovingGradient() {
        return movingGradient;
    }

    @Override
    public String getTranslationKey() {
        return Util.makeDescriptionId("vortex", this.translationKey);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getTranslationKey());
    }
}
