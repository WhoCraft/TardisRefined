package whocraft.tardis_refined.client.renderer.vortex;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import whocraft.tardis_refined.client.renderer.RenderHelper;
import whocraft.tardis_refined.common.VortexRegistry;

/** GUI fallback renderer for the vortex texture on the 26.3 extracted GUI path. */
public class VortexRenderer {
    public VortexRegistry vortexType;
    public final RenderHelper.DynamicTimeKeep time = new RenderHelper.DynamicTimeKeep(2);
    public float opacity = 1;
    public float lightning_strike = 0;

    public VortexRenderer(VortexRegistry type) {
        this.vortexType = type;
    }

    public void renderVortex(GuiGraphicsExtractor graphics, float opacity, boolean half) {
        renderVortex(graphics, 0, 0, graphics.guiWidth(), graphics.guiHeight(), opacity);
    }

    /** Draws the animated, isolated vortex surface into a specific GUI viewport. */
    public void renderVortex(GuiGraphicsExtractor graphics, int x, int y, int width, int height,
                             float opacity) {
        if (vortexType == null) return;
        this.opacity = Math.min(opacity, 1);
        time.update();
        if (vortexType.isMovingGradient()) vortexType.getGradient().offset = time.getFloat() * 2.0f;
        Identifier atlasTexture = vortexType.getTexture();
        // The original files are packed Bedrock-style atlases. The GUI
        // renderer needs a dedicated cloud-surface texture so its sampler
        // cannot expose the atlas' mask/lightning cells as a purple grid.
        Identifier texture = Identifier.fromNamespaceAndPath(atlasTexture.getNamespace(),
                atlasTexture.getPath().replaceFirst("\\.png$", "_surface.png"));
        // Pan a square window through the isolated cloud texture to retain
        // the vortex motion without sampling any unrelated atlas cells.
        final int surfaceWidth = 256;
        final int surfaceHeight = 512;
        final int viewportHeight = 256;
        float sourceY = time.getFloat() * (surfaceHeight - viewportHeight);
        float[] tint = vortexType.getGradient().getRGBf(0.5f);
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, 0, sourceY, width, height,
                surfaceWidth, viewportHeight, surfaceWidth, surfaceHeight,
                ARGB.colorFromFloat(this.opacity, tint[0], tint[1], tint[2]));
    }

    public void renderVortex(GuiGraphicsExtractor graphics, float opacity) {
        renderVortex(graphics, opacity, false);
    }
}
