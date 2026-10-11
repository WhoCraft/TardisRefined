package whocraft.tardis_refined.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

/**
 * Shared render type selection for shell and console textures.
 * Minecraft 26.3 replaced the older mutable RenderStateShard pipeline, so these
 * helpers use the equivalent built-in render types until custom RenderPipelines
 * are defined for the optional snow overlay.
 */
public final class TRShaders {
    private TRShaders() {}

    @Environment(EnvType.CLIENT)
    public static boolean shouldUseCompatMode() {
        return true;
    }

    public static RenderType translucentWithSnow(Identifier texture, boolean show) {
        // The legacy multi-texture shader state is no longer part of the 26.3 API.
        // Keep the shell texture visible; snow blending can return after its custom
        // RenderPipeline is migrated.
        return RenderTypes.entityTranslucent(texture);
    }

    public static RenderType glow(Identifier texture, float intensity) {
        return RenderTypes.entityTranslucentEmissive(texture);
    }
}
