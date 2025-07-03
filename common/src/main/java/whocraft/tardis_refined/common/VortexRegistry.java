package whocraft.tardis_refined.common;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.renderer.vortex.VortexGradientTint;
import whocraft.tardis_refined.common.tardis.themes.Theme;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplierHolder;

public class VortexRegistry implements Theme {

    /**
     * Registry Key for the Vortex registry.
     */
    public static final ResourceKey<Registry<VortexRegistry>> VORTEX_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.tryBuild(TardisRefined.MODID, "vortex"));

    /**
     * Deferred Registry for Vortex entries.
     */
    public static final DeferredRegistry<VortexRegistry> VORTEX_DEFERRED_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, VORTEX_REGISTRY_KEY, true);

    /**
     * Registry instance containing all Vortex entries.
     */
    public static final Registry<VortexRegistry> VORTEX_REGISTRY = VORTEX_DEFERRED_REGISTRY.getRegistry().get();

    // Vortex entries
    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> CLOUDS = registerVortex(ResourceLocation.tryBuild(TardisRefined.MODID, "clouds"), ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"), 9, 12, 10f, true, true, VortexGradientTint.BlueOrngGradient, false);
    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> WAVES = registerVortex(ResourceLocation.tryBuild(TardisRefined.MODID, "waves"), ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/waves.png"), 9, 12, 20f, true, true, VortexGradientTint.BlueOrngGradient, false);
    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> STARS = registerVortex(ResourceLocation.tryBuild(TardisRefined.MODID, "stars"), ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/stars.png"), 9, 12, 5f, true, true, VortexGradientTint.PASTEL_GRADIENT, true);
    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> FLOW = registerVortex(ResourceLocation.tryBuild(TardisRefined.MODID, "flow"), ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"), 9, 12, 5f, true, true, VortexGradientTint.MODERN_VORTEX, true);
    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> SPACE = registerVortex(ResourceLocation.tryBuild(TardisRefined.MODID, "space"), ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/stars_2.png"), 9, 12, 5f, true, true, VortexGradientTint.MODERN_VORTEX, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> TWILIGHT_GLOW = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "twilight_glow"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.TWILIGHT_GLOW, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> AURORA_DREAMS = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "aurora_dreams"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.AURORA_DREAMS, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> DESERT_MIRAGE = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "desert_mirage"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.DESERT_MIRAGE, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> NEON_PULSE = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "neon_pulse"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.NEON_PULSE, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> OCEAN_BREEZE = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "ocean_breeze"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.OCEAN_BREEZE, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> SOLAR_FLARE = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "solar_flare"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.SOLAR_FLARE, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> CRYSTAL_LAGOON = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "crystal_lagoon"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.CRYSTAL_LAGOON, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> VELVET_NIGHT = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "velvet_night"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.VELVET_NIGHT, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> CANDY_POP = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "candy_pop"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.CANDY_POP, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> EMERALD_FOREST = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "emerald_forest"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.EMERALD_FOREST, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> LGBT_RAINBOW = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "lgbt_rainbow"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.LGBT_RAINBOW, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> TRANSGENDER_FLAG = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "transgender_flag"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.TRANSGENDER_FLAG, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> BISEXUAL_FLAG = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "bisexual_flag"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.BISEXUAL_FLAG, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> LESBIAN_FLAG = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "lesbian_flag"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.LESBIAN_FLAG, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> NON_BINARY_FLAG = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "non_binary_flag"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.NON_BINARY_FLAG, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> AGENDER_FLAG = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "agender_flag"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.AGENDER_FLAG, false);

    public static final RegistrySupplierHolder<VortexRegistry, VortexRegistry> GAY_FLAG = registerVortex(
            ResourceLocation.tryBuild(TardisRefined.MODID, "gay_flag"),
            ResourceLocation.tryBuild(TardisRefined.MODID, "textures/vortex/clouds.png"),
            9, 12, 10f, true, true, VortexGradientTint.GAY_FLAG, false);


    private final ResourceLocation texture;
    private final int sides;
    private final int rows;
    private final float twist;
    private final boolean lightning;
    private final boolean decals;
    private final VortexGradientTint gradient;
    private final boolean movingGradient;
    private ResourceLocation translationKey;

    public VortexRegistry(ResourceLocation id, ResourceLocation texture, int sides, int rows, float twist, boolean lightning, boolean decals, VortexGradientTint gradient, boolean movingGradient) {
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

    public ResourceLocation getTexture() {
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

    private static RegistrySupplierHolder<VortexRegistry, VortexRegistry> registerVortex(ResourceLocation id, ResourceLocation texturePath, int sides, int rows, float twist, boolean lightning, boolean decals, VortexGradientTint gradient, boolean movingGradient) {
        return VORTEX_DEFERRED_REGISTRY.registerHolder(id.getPath(), () -> new VortexRegistry(
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

    @Override
    public String getTranslationKey() {
        return Util.makeDescriptionId("vortex", this.translationKey);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getTranslationKey());
    }
}
