package whocraft.tardis_refined.client.renderer.vortex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.ShellPreviewDiagnostics;
import whocraft.tardis_refined.client.overlays.VortexOverlay;
import whocraft.tardis_refined.common.VortexRegistry;
import whocraft.tardis_refined.client.model.blockentity.shell.ThemeShellGeoModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.entity.ShellPreviewEntity;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.TREntityRegistry;


import static whocraft.tardis_refined.client.screen.screens.ShellSelectionScreen.GLOBALSHELL_BLOCKENTITY;

/** GUI texture preview fallback while 3D GUI model submissions are being ported. */
public final class ShellRenderer {
    private static ShellPreviewEntity previewEntity;
    private static Level previewLevel;
    public static final VortexRenderer PREVIEW_VORTEX = new VortexRenderer(VortexRegistry.CLOUDS.get());
    public static int previewCenterX(int guiWidth) { return guiWidth / 2 - 75; }
    public static int previewCenterY(int guiHeight) { return guiHeight / 2 - 5; }
    private ShellRenderer() {}

    /** Render the selected/saved vortex as the original perspective tunnel in the monitor. */
    public static void renderVortexPreview(GuiGraphicsExtractor graphics, Identifier vortexId,
                                          int x, int y, int width, int height) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        if (level == null) return;
        VortexRegistry vortex = VortexRegistry.VORTEX_DEFERRED_REGISTRY.get(vortexId);
        if (vortex == null) vortex = VortexRegistry.CLOUDS.get();
        PREVIEW_VORTEX.time.speed = 0.3;
        PREVIEW_VORTEX.time.update();

        if (previewEntity == null || previewLevel != level) {
            previewEntity = TREntityRegistry.SHELL_PREVIEW.get().create(level, EntitySpawnReason.COMMAND);
            previewLevel = level;
        }
        if (previewEntity == null) return;
        previewEntity.setPos(0, 0, 0);
        previewEntity.setVortexOnly(true);
        previewEntity.setVortexBackgroundPreview(true);
        previewEntity.setVortexPreviewType(vortexId);
        previewEntity.setVortexViewport(width, height);
        previewEntity.setShellFlightOffset(0, 0, 0);
        previewEntity.setShellFlightRotation(new Quaternionf());
        EntityRenderState renderState = minecraft.getEntityRenderDispatcher().extractEntity(
                previewEntity, minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false));
        graphics.entity(renderState, 32.0f, new Vector3f(0, 0, 0), new Quaternionf(),
                new Quaternionf().rotationY((float) Math.PI), x, y, x + width, y + height);
    }

    /** Renders the selected shell as an assembled, slowly rotating 3D preview in a GUI. */
    public static void renderSelectedShell(GuiGraphicsExtractor graphics, Identifier shellTheme,
                                           Identifier shellPattern, int x, int y, float scale) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        if (level == null || shellTheme == null) return;

        ShellPattern pattern = ShellPatterns.getPatternOrDefault(shellTheme, shellPattern);
        ShellPreviewDiagnostics.report("computer-bank-shell-submit",
                "theme=" + shellTheme + ", pattern=" + pattern.id() + ", scale=" + scale
                        + ", center=" + x + "," + y
                        + ", gecko=" + ThemeShellGeoModel.supports(shellTheme));
        if (previewEntity == null || previewLevel != level) {
            previewEntity = TREntityRegistry.SHELL_PREVIEW.get().create(level, EntitySpawnReason.COMMAND);
            previewLevel = level;
        }
        if (previewEntity == null) return;

        previewEntity.setAppearance(shellTheme, pattern.id());
        previewEntity.setPos(0, 0, 0);
        previewEntity.setVortexOnly(false);
        previewEntity.setVortexBackgroundPreview(false);
        previewEntity.setVortexViewport(0, 0);
        // One revolution takes about 24 seconds, matching the slow idle spin
        // used for the shell display without borrowing the flight animation.
        float rotation = (System.currentTimeMillis() % 24_000L) / 24_000.0F
                * (float) (Math.PI * 2.0);
        previewEntity.setShellFlightRotation(new Quaternionf().rotationY(rotation));

        EntityRenderState renderState = minecraft.getEntityRenderDispatcher()
                .extractEntity(previewEntity, minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false));
        int halfWidth = 48;
        int halfHeight = 48;
        graphics.entity(renderState, scale, new Vector3f(0, 0, 0), new Quaternionf(), null,
                x - halfWidth, y - halfHeight, x + halfWidth, y + halfHeight);
    }

    public static void renderShell(GuiGraphicsExtractor graphics, float control, float alpha, int throttle) {
        TardisPlayerInfo.get(Minecraft.getInstance().player).ifPresent(playerInfo -> {
            TardisClientData data = TardisClientData.getInstance(playerInfo.getPlayerPreviousPos().getDimensionKey());
            if (data == null) {
                ShellPreviewDiagnostics.report("vortex-source",
                        "previousDimension=" + playerInfo.getPlayerPreviousPos().getDimensionKey().identifier()
                                + ", data=null");
                return;
            }
            Identifier shellPattern = data.getShellPattern();
            Identifier shellTheme = data.getShellTheme();
            ShellPattern pattern = ShellPatterns.getPatternOrDefault(shellTheme, shellPattern);
            ShellModel model = ShellModelCollection.getInstance().getShellEntry(shellTheme).getShellModel(pattern);
            ShellPreviewDiagnostics.report("vortex-source",
                    "previousDimension=" + playerInfo.getPlayerPreviousPos().getDimensionKey().identifier()
                            + ", renderDimension=" + Minecraft.getInstance().level.dimension().identifier()
                            + ", tardis=" + data.getLevelKey().identifier()
                            + ", theme=" + shellTheme + ", pattern=" + shellPattern
                            + ", resolvedPattern=" + pattern.id()
                            + ", model=" + (model == null ? "null" : model.getClass().getSimpleName())
                            + ", geckoPreview=" + ThemeShellGeoModel.supports(shellTheme));
            if (model == null) return;
            GLOBALSHELL_BLOCKENTITY.setTardisId(data.getLevelKey());
            // Shell model implementations also inspect the block entity for
            // pattern/appearance data. This preview entity is shared with the
            // monitor and starts with a placeholder shell, so refresh both
            // values for every vortex render.
            GLOBALSHELL_BLOCKENTITY.setPreviewAppearance(shellTheme, pattern);

            Identifier texture = model.getShellTexture(pattern, false);
            float materializationScale = 0.72f + (1.0f - Mth.clamp(control, 0.0f, 1.0f)) * 0.28f;
            int width = 112;
            int height = 144;
            if (ThemeShellGeoModel.supports(shellTheme)) {
                Level level = Minecraft.getInstance().level;
                if (level == null) return;
                if (previewEntity == null || previewLevel != level) {
                    previewEntity = TREntityRegistry.SHELL_PREVIEW.get().create(level, EntitySpawnReason.COMMAND);
                    previewLevel = level;
                }
                if (previewEntity == null) return;
                previewEntity.setAppearance(shellTheme, shellPattern);
                previewEntity.setPos(0, 0, 0);
                // The legacy scene renders the tunnel during takeoff, flight,
                // and landing, with the shell moving and recoiling within it.
                previewEntity.setVortexOnly(VortexOverlay.isFlightScene(data));
                previewEntity.setVortexBackgroundPreview(false);
                previewEntity.setVortexViewport(0, 0);
                Vector3f flightOffset = VortexOverlay.shellFlightOffset();
                previewEntity.setShellFlightOffset(flightOffset.x, flightOffset.y, flightOffset.z);
                previewEntity.setShellFlightRotation(VortexOverlay.shellFlightRotation(throttle));
                EntityRenderState renderState = Minecraft.getInstance().getEntityRenderDispatcher()
                        .extractEntity(previewEntity, Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false));
                // The legacy renderer turns the vortex and shell together by
                // rotating their scene pose. Rotating the PIP camera itself
                // moves its view away from the centered geometry and can leave
                // the replacement scene black.
                // Keep the PIP's GUI zoom for the shell, while the custom
                // renderer cancels this scale for the legacy-sized tunnel.
                graphics.entity(renderState, 32.0f,
                        new Vector3f(0, 0, 0), VortexOverlay.flightCameraAngle(),
                        // GuiEntityRenderer turns the override into the camera
                        // orientation by conjugating it and adding 180 degrees
                        // of yaw. Cancel that built-in yaw so the camera looks
                        // down the tunnel's mirrored -Z axis, not out through
                        // its tapered opening.
                        new Quaternionf().rotationY((float) Math.PI), 0, 0,
                        graphics.guiWidth(), graphics.guiHeight());
            } else {
                // Keep a moving atlas fallback for external shells that do not have built-in GeckoLib geometry.
                var pose = graphics.pose();
                pose.pushMatrix();
                pose.translate(graphics.guiWidth() * 0.5f, graphics.guiHeight() * 0.5f);
                pose.scale(materializationScale, materializationScale);
                graphics.blit(RenderPipelines.GUI_TEXTURED, texture, -width / 2, -height / 2,
                        0, 0, width, height, 256, 256, 256, 256,
                        ARGB.colorFromFloat(alpha, 1, 1, 1));
                pose.popMatrix();
            }
        });
    }

}
