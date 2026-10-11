package whocraft.tardis_refined.fabric.client;

import com.geckolib.renderer.GeoEntityRenderer;
import com.mojang.math.Axis;
import com.geckolib.constant.DataTickets;
import com.geckolib.animatable.manager.AnimatableManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.model.blockentity.shell.ThemeShellGeoModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.client.renderer.DeferredGeometryBuffer;
import whocraft.tardis_refined.client.TRShaders;
import whocraft.tardis_refined.client.overlays.VortexOverlay;
import whocraft.tardis_refined.client.renderer.vortex.ShellRenderer;
import whocraft.tardis_refined.client.renderer.vortex.VortexRenderer;
import whocraft.tardis_refined.common.entity.ShellPreviewEntity;
import whocraft.tardis_refined.common.VortexRegistry;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Uses the same GeckoLib shell geometry and texture selection as the in-world exterior renderer. */
public final class ShellPreviewRenderer extends GeoEntityRenderer<ShellPreviewEntity, ShellPreviewRenderState> {
    private final ThemeShellGeoModel<ShellPreviewEntity> previewModel;
    // GuiEntityRenderer's picture-in-picture handoff can create a render state
    // that omits custom GeckoLib data. Keep the selected appearance alongside
    // the model so submit() can still choose and render the correct shell.
    private Identifier previewTheme;
    private Identifier previewPattern;
    // The legacy renderer kept the tube radius at one model unit and used the
    // configured row count for its length. A GUI-sized multiplier here makes
    // the camera sit inside enormous panels instead of looking down the tube.
    private static final float VORTEX_RADIUS = 1.0f;
    // The legacy mesh extended by one configured row in either direction.
    private static final float VORTEX_AXIAL_SCALE = 1.0f;
    // The legacy profile narrows to a point at o=1. The PIP's model-view
    // matrix flips Z, so positive model Z points into the camera. Keep the
    // original mesh through o=(rows-1)/rows, then continue that cross-section
    // past the far clip so the visible end cannot read as a black cap.
    // Coordinates are restored to the legacy 100-unit scale below, so this
    // endpoint sits just beyond the 9999-unit far plane.
    private static final float VORTEX_FORWARD_END = 110.0f;
    private static final int VORTEX_FORWARD_SEGMENTS = 512;
    // The legacy GUI renderer used a perspective projection and scaled the
    // tunnel by 100 before drawing it. Keep the model-space radius above at 1
    // and apply that scale only to the vortex, leaving the shell at normal size.
    private static final float LEGACY_VORTEX_SCALE = 100.0f;
    private static final Random LIGHTNING_RANDOM = new Random();
    private static final long LIGHTNING_FLASH_MILLIS = 550L;
    private static long nextVortexThunderAt;
    private final List<LegacyVortexDecal> vortexDecals = new ArrayList<>();
    private VortexRegistry lastVortex;

    public ShellPreviewRenderer(EntityRendererProvider.Context context) {
        this(context, new ThemeShellGeoModel<>());
    }

    private ShellPreviewRenderer(EntityRendererProvider.Context context, ThemeShellGeoModel<ShellPreviewEntity> model) {
        super(context, model);
        this.previewModel = model;
        shadowRadius = 0;
    }

    @Override public ShellPreviewRenderState createRenderState(ShellPreviewEntity entity, Void context) {
        return new ShellPreviewRenderState();
    }

    @Override public long getInstanceId(ShellPreviewEntity entity, Void context) {
        // Preview entities are never inserted into the level, so Minecraft has
        // not assigned them an entity ID. GeckoLib still needs a stable cache key.
        return Integer.toUnsignedLong(System.identityHashCode(entity));
    }

    @Override
    public void extractRenderState(ShellPreviewEntity entity, ShellPreviewRenderState state, float partialTick) {
        // Do not invoke GeckoEntityRenderer's generic extraction: in this
        // never-spawned GUI preview, its controller manager lookup returns null
        // inside AnimationProcessor before GeckoLib can submit any geometry.
        state.entityType = entity.getType();
        state.x = entity.getX();
        state.y = entity.getY();
        state.z = entity.getZ();
        state.ageInTicks = entity.tickCount + partialTick;
        state.boundingBoxWidth = entity.getBbWidth();
        state.boundingBoxHeight = entity.getBbHeight();
        state.eyeHeight = entity.getEyeHeight();
        state.distanceToCameraSq = 0;
        state.isInvisible = false;
        state.vortexOnly = entity.vortexOnly();
        state.vortexBackgroundPreview = entity.vortexBackgroundPreview();
        state.vortexPreviewType = entity.vortexPreviewType();
        state.vortexViewportWidth = entity.vortexViewportWidth();
        state.vortexViewportHeight = entity.vortexViewportHeight();
        previewTheme = entity.shellTheme();
        previewPattern = entity.shellPattern();
        state.shellFlightRotation.set(entity.shellFlightRotation());
        state.shellFlightOffset.set(entity.shellFlightOffset());
        state.displayFireAnimation = false;
        state.lightCoords = 0x00F000F0;
        state.outlineColor = net.minecraft.client.renderer.entity.state.EntityRenderState.NO_OUTLINE;
        state.shadowRadius = 0;

        long instanceId = getInstanceId(entity, null);
        AnimatableManager<?> manager = entity.getAnimatableInstanceCache().getManagerForId(instanceId);
        if (manager == null) manager = new AnimatableManager<>(entity);
        state.addGeckolibData(DataTickets.ANIMATABLE_INSTANCE_ID, instanceId);
        state.addGeckolibData(DataTickets.ANIMATABLE_MANAGER, manager);
        state.addGeckolibData(DataTickets.ANIMATABLE_CLASS, entity.getClass());
        state.addGeckolibData(DataTickets.PARTIAL_TICK, partialTick);
        state.addGeckolibData(DataTickets.TICK, (double) state.ageInTicks);
        state.addGeckolibData(DataTickets.RENDER_COLOR, ARGB.white(1.0f));
        state.addGeckolibData(DataTickets.PACKED_OVERLAY, 0);
        state.addGeckolibData(DataTickets.PACKED_LIGHT, 0x00F000F0);
        state.addGeckolibData(DataTickets.IS_MOVING, false);
        state.addGeckolibData(DataTickets.ANIMATION_CONTROLLER_STATES,
                new com.geckolib.animation.state.ControllerState[0]);
        state.addGeckolibData(DataTickets.VELOCITY, Vec3.ZERO);
        state.addGeckolibData(DataTickets.POSITION, entity.position());
        state.addGeckolibData(DataTickets.ENTITY_BODY_YAW, entity.getYRot());
        state.addGeckolibData(ThemeShellGeoModel.SHELL_THEME, entity.shellTheme());
        state.addGeckolibData(ThemeShellGeoModel.SHELL_PATTERN, entity.shellPattern());
        previewModel.setPreviewAppearance(entity.shellTheme(), entity.shellPattern());
        whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("vortex-preview-entity",
                "instance=" + Long.toUnsignedString(instanceId)
                        + ", theme=" + entity.shellTheme() + ", pattern=" + entity.shellPattern()
                        + ", vortexOnly=" + entity.vortexOnly());
    }

    @Override
    public void submit(ShellPreviewRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        Identifier stateTheme = state.getGeckolibData(ThemeShellGeoModel.SHELL_THEME);
        Identifier theme = stateTheme != null ? stateTheme : previewTheme;
        boolean geckoShell = ThemeShellGeoModel.supports(theme);
        if (state.vortexOnly) {
            whocraft.tardis_refined.client.ShellPreviewDiagnostics.reportEvery("takeoff-preview-submit",
                    "renderer=" + getClass().getSimpleName()
                            + ", theme=" + theme
                            + ", shellPath=" + (geckoShell ? "gecko" : "legacy")
                            + ", flightOffset=" + state.shellFlightOffset
                            + ", flightRotation=" + state.shellFlightRotation,
                    1000);
        }
        whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("computer-bank-shell-renderer",
                "theme=" + theme + ", stateTheme=" + stateTheme + ", path=" + (geckoShell ? "gecko" : "legacy"));
        if (state.vortexBackgroundPreview) {
            submitFlightVortex(poseStack, collector, state, false);
            return;
        }
        if (state.vortexOnly) {
            submitFlightVortex(poseStack, collector, state, true);
            // Flight uses the original ShellModel path, matching the 1.20.2
            // vortex renderer. The Gecko preview model is useful for the
            // selection screen, but its entity-facing transform/culling makes
            // it disappear in the perspective PIP scene.
            submitLegacyShellPreview(state, poseStack, collector, true);
            return;
        }
        if (!geckoShell) {
            submitLegacyShellPreview(state, poseStack, collector, false);
            return;
        }
        if (!state.vortexOnly) {
            // Ordinary GUI entity previews already have their own scale and
            // centered viewport. The vortex normalization below would shift
            // these previews off-screen and shrink them almost to zero.
            poseStack.pushPose();
            // Match the exterior shell renderer's upright block orientation.
            poseStack.translate(0.5f, 1.5f, 0.5f);
            poseStack.rotate(Axis.ZP.rotationDegrees(180.0f));
            poseStack.rotate(state.shellFlightRotation);
            super.submit(state, poseStack, collector, cameraState);
            poseStack.popPose();
            return;
        }
    }

    private static float pipScale() {
        return Math.max(1.0f, (float) (Minecraft.getInstance().getWindow().getGuiScale() * 32.0));
    }

    private static void centerFlightScene(PoseStack poseStack, float pipScale,
                                          float viewportWidth, float viewportHeight) {
        // GuiEntityRenderer adds a viewport-centering translation before
        // rendering PIP geometry. Cancel it for both tunnel and shell.
        poseStack.translate(-viewportWidth * 2.0f / pipScale,
                -viewportHeight * 2.0f / pipScale, 0.0f);
    }

    private void submitFlightVortex(PoseStack poseStack, SubmitNodeCollector collector,
                                    ShellPreviewRenderState state, boolean playLightningSound) {
        VortexRegistry vortex = state.vortexBackgroundPreview && state.vortexPreviewType != null
                ? VortexRegistry.VORTEX_DEFERRED_REGISTRY.get(state.vortexPreviewType)
                : VortexOverlay.VORTEX.vortexType;
        if (vortex == null) {
            whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("vortex-black-screen-diagnostic",
                    "cause=no-active-vortex-type; shell preview will still render");
            return;
        }
        VortexRenderer vortexClock = state.vortexBackgroundPreview
                ? ShellRenderer.PREVIEW_VORTEX : VortexOverlay.VORTEX;
        float speed = (float) vortexClock.time.speed;
        float time = vortexClock.time.getFloat();
        if (vortex.isMovingGradient()) vortex.getGradient().offset = time * 2.0f;
        poseStack.pushPose();
        // Keep the flight scene centered in the PIP and retain the tested
        // legacy vortex scale, independent of the selected shell model.
        float pipScale = pipScale();
        float viewportWidth = state.vortexViewportWidth > 0
                ? state.vortexViewportWidth : Minecraft.getInstance().getWindow().getGuiScaledWidth();
        float viewportHeight = state.vortexViewportHeight > 0
                ? state.vortexViewportHeight : Minecraft.getInstance().getWindow().getGuiScaledHeight();
        centerFlightScene(poseStack, pipScale, viewportWidth, viewportHeight);
        poseStack.scale(LEGACY_VORTEX_SCALE / pipScale,
                LEGACY_VORTEX_SCALE / pipScale, LEGACY_VORTEX_SCALE / pipScale);
        // Keep the tunnel's axis fixed. Its animated texture and lightning
        // provide motion without the clock-like rotation of the whole mesh.
        poseStack.rotate(Axis.ZP.rotationDegrees(VortexOverlay.flightVortexRotationDegrees()));
        whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("vortex-black-screen-diagnostic",
                "cause=tunnel-and-shell-submitted; texture=" + vortex.getTexture()
                        + ", pipScale=" + pipScale + ", shellPath="
                        + (ThemeShellGeoModel.supports(previewTheme) ? "gecko" : "legacy"));
        submitLegacyVortex(vortex, vortexClock, poseStack, collector, time, speed, playLightningSound);
        poseStack.popPose();
    }

    private void submitLegacyShellPreview(ShellPreviewRenderState state, PoseStack poseStack,
                                          SubmitNodeCollector collector, boolean flight) {
        Identifier theme = state.getGeckolibData(ThemeShellGeoModel.SHELL_THEME);
        if (theme == null) theme = previewTheme;
        Identifier patternId = state.getGeckolibData(ThemeShellGeoModel.SHELL_PATTERN);
        if (patternId == null) patternId = previewPattern;
        if (theme == null) return;
        ShellPattern pattern = ShellPatterns.getPatternOrDefault(theme, patternId);
        ShellModel model = ShellModelCollection.getInstance().getShellEntry(theme).getShellModel(pattern);
        GlobalShellBlockEntity previewBlockEntity =
                whocraft.tardis_refined.client.screen.screens.ShellSelectionScreen.GLOBALSHELL_BLOCKENTITY;
        if (model == null || previewBlockEntity == null) return;
        previewBlockEntity.setPreviewAppearance(theme, pattern);

        PoseStack modelPose = new PoseStack();
        modelPose.translate(0.5F, 1.5F, 0.5F);
        modelPose.rotate(Axis.ZP.rotationDegrees(180.0F));
        DeferredGeometryBuffer geometry = new DeferredGeometryBuffer();
        Identifier shellTexture = model.getShellTexture(pattern, false);
        // The parked exterior shell uses the takeoff/landing opacity animation.
        // Its in-vortex copy stays solid throughout those transitions.
        // Match the 1.20.2 shell renderer: the copy is submitted throughout
        // takeoff and shares the legacy takeoff/landing fade animation.
        model.setIgnoreAnmationAlpha(flight);
        model.renderShell(previewBlockEntity, false, !flight, modelPose,
                geometry.getBuffer(TRShaders.translucentWithSnow(shellTexture, false)),
                0x00F000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        if (flight) {
            // The vortex is rendered in a dark PIP scene. Keep the shell's
            // original texture and animations, but render its flight copy at
            // full brightness so it does not disappear into the tunnel.
            model.renderShell(previewBlockEntity, false, false, modelPose,
                    geometry.getBuffer(RenderTypes.entityTranslucentEmissive(shellTexture), 2.25f),
                    0x00F000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
        model.setIgnoreAnmationAlpha(false);
        if (flight && pattern.shellTexture().emissive()) {
            model.renderShell(previewBlockEntity, false, false, modelPose,
                    geometry.getBuffer(RenderTypes.entityTranslucentEmissive(model.getShellTexture(pattern, true))),
                    0x00F000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
        whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("computer-bank-legacy-geometry",
                "theme=" + theme + ", pattern=" + pattern.id() + ", model="
                        + model.getClass().getSimpleName() + ", " + geometry.geometrySummary());

        poseStack.pushPose();
        if (flight) {
            float pipScale = pipScale();
            centerFlightScene(poseStack, pipScale,
                    Minecraft.getInstance().getWindow().getGuiScaledWidth(),
                    Minecraft.getInstance().getWindow().getGuiScaledHeight());
            poseStack.scale(1.0f / pipScale, 1.0f / pipScale, 1.0f / pipScale);
            poseStack.translate(state.shellFlightOffset.x, state.shellFlightOffset.y, state.shellFlightOffset.z);
            // GlobalShellRenderer already centers and turns legacy shell models
            // upright in modelPose above. Keep only the forward depth offset here.
            poseStack.translate(0.0f, 0.0f, 20.0f);
        }
        poseStack.rotate(state.shellFlightRotation);
        geometry.submit(collector, poseStack);
        poseStack.popPose();
    }

    /** Ports the legacy row-by-row tube and its moving, atlas-backed decals. */
    private void submitLegacyVortex(VortexRegistry vortex, VortexRenderer vortexClock, PoseStack poseStack,
                                    SubmitNodeCollector collector, float time, float speed,
                                    boolean playLightningSound) {
        Identifier atlas = vortex.getTexture();
        int rows = Math.max(2, vortex.getRows());
        int sides = Math.max(3, vortex.getSides());
        float vortexOpacity = VortexOverlay.vortexOpacity();
        whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("legacy-vortex-geometry",
                "texture=" + atlas + ", rows=" + rows + ", sides=" + sides
                        + ", modelRadius=" + VORTEX_RADIUS + ", legacyScale=" + LEGACY_VORTEX_SCALE
                        + ", axialLength=" + (2 * rows * VORTEX_AXIAL_SCALE * LEGACY_VORTEX_SCALE)
                        + ", vertices=" + (rows * sides * 6) + ", cameraAtShellOrigin=true");

        collector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(atlas, false), (pose, output) -> {
            int emittedVertices = 0;
            float lowerO = -1.0f;
            float lowerTwist = (float) Math.toRadians(-rows * vortex.getTwist());
            float[][] lowerRing = legacyTubeRing(lowerO, lowerTwist, speed, rows, sides);
            // The positive-Z end faces the PIP camera. Omit only its final
            // taper row; the continuation below joins the same open ring.
            for (int row = -rows; row < rows - 1; row++) {
                float upperO = (row + 1.0f) / rows;
                float lowerV = lowerO + time;
                float upperV = upperO + time;
                // Generate each ring once so adjacent panels and rows reuse
                // identical vertex values instead of independently rounding
                // the same trig calculations.
                float upperTwist = (float) Math.toRadians((row + 1) * vortex.getTwist());
                float[][] upperRing = legacyTubeRing(upperO, upperTwist, speed, rows, sides);
                for (int side = 0; side < sides; side++) {
                    float u0 = side / (float) sides * 0.5f;
                    float u1 = (side + 1.0f) / sides * 0.5f;
                    float[] p0 = lowerRing[side];
                    float[] p1 = upperRing[side];
                    float[] p2 = upperRing[side + 1];
                    float[] p3 = lowerRing[side + 1];
                    float[] tint0 = vortex.getGradient().getRGBf(lowerO);
                    float[] tint1 = vortex.getGradient().getRGBf(upperO);
                    addVortexVertex(output, pose, p0, u0, lowerV, tint0, vortexOpacity);
                    addVortexVertex(output, pose, p1, u0, upperV, tint1, vortexOpacity);
                    addVortexVertex(output, pose, p2, u1, upperV, tint1, vortexOpacity);
                    addVortexVertex(output, pose, p3, u1, lowerV, tint0, vortexOpacity);
                    emittedVertices += 4;
                }
                lowerRing = upperRing;
                lowerO = upperO;
            }
            emittedVertices += emitForwardContinuation(vortex, pose, output, time, speed,
                    rows, sides, vortexOpacity, lowerRing);
            whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("vortex-mesh-emission",
                    "callback=executed, texture=" + atlas + ", emittedVertices=" + emittedVertices
                            + ", forwardEndModelZ=" + VORTEX_FORWARD_END
                            + ", forwardEndViewDepth="
                            + Math.abs(VORTEX_FORWARD_END * pose.pose().m22())
                            + ", farClip=9999, continuationSegments=" + VORTEX_FORWARD_SEGMENTS
                            + ", modelView=" + pose.pose());
        });

        if (!vortex.hasDecals()) {
            vortexDecals.clear();
            lastVortex = vortex;
            return;
        }
        int decalCount = Math.max(1, rows / 2);
        if (lastVortex != vortex || vortexDecals.size() != decalCount) {
            vortexDecals.clear();
            for (int i = 0; i < decalCount; i++) vortexDecals.add(new LegacyVortexDecal());
            lastVortex = vortex;
        }
        List<float[]> frames = new ArrayList<>(decalCount);
        long now = System.currentTimeMillis();
        float lightningContribution = 0.0f;
        for (int i = 0; i < decalCount; i++) {
            float o = 1.0f - 2.0f * vortexClock.time.getFloat(i / (float) decalCount);
            LegacyVortexDecal decal = vortexDecals.get(i);
            if (!decal.update(o, vortex, now)) continue;
            if (decal.consumeStrikeSound()) {
                whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("vortex-lightning-strike",
                        "vortex=" + vortex.getTranslationKey() + ", depth=" + o
                                + ", soundEnabled=" + playLightningSound
                                + ", visibleFlash=" + (decal.flashAlpha > 0.1f));
                // Strike timers are independent for each decal. Play one
                // explosion cue for each strike only when its flash is being
                // rendered in front of the camera.
                if (playLightningSound && decal.lightning && decal.flashAlpha > 0.1f
                        && o > 0.0f) {
                    playVortexLightningSound();
                    maybePlayVortexThunderSound();
                }
            }
            lightningContribution += decal.lightningStrike * decal.lightningStrike;
            float radius = decal.lightning ? 1.0f : legacyRadiusFunc(o);
            // The atlas lightning is already an alpha-masked decal. Applying the tunnel's
            // opacity again made its white pixels look gray against the colored vortex.
            float alpha = decal.lightning ? Math.min(1.0f, decal.flashAlpha)
                    : Math.min(1.0f, radius) * vortexOpacity;
            if (alpha <= 0.005f) continue;
            // Lightning decals in the 1.20.2 renderer bypassed the vortex gradient.
            // Tinting them with the red/orange gradient made every strike appear colored.
            float[] tint = decal.lightning ? new float[]{1.0f, 1.0f, 1.0f}
                    : vortex.getGradient().getRGBf(o);
            float centerX = VORTEX_RADIUS * legacyXWobble(o, speed) * (float) Math.sin(o);
            float centerY = VORTEX_RADIUS * legacyZWobble(o, speed) * (float) Math.sin(o);
            float half = VORTEX_RADIUS * legacyWobbleRadius(o) * radius;
            float u = 0.5f + decal.u;
            float v = decal.v + (decal.lightning ? 0.5f : 0.0f);
            frames.add(new float[]{o, centerX, centerY, half, u, v, alpha,
                    tint[0], tint[1], tint[2]});
        }
        vortexClock.lightning_strike = (vortexClock.lightning_strike
                + lightningContribution / decalCount) * 0.9f;
        if (frames.isEmpty()) return;
        collector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(atlas, false), (pose, output) -> {
            for (float[] frame : frames) {
                float o = frame[0];
                float centerX = frame[1];
                float centerY = frame[2];
                float half = frame[3];
                float u = frame[4];
                float v = frame[5];
                float[] tint = {frame[7], frame[8], frame[9]};
                // The PIP camera looks from the preview entity's origin. Keep
                // that origin inside the tube; offsetting the rows by one unit
                // moved the viewpoint toward one end and exposed the tunnel tip.
                // GuiEntityRenderer mirrors submitted geometry on Z, so the
                // symmetric span keeps the camera surrounded on both sides.
                float z = vortexAxialPosition(o, vortex.getRows());
                float[] p0 = {-centerX - half, centerY + half, z, 0, 0, 1};
                float[] p1 = {-centerX + half, centerY + half, z, 0, 0, 1};
                float[] p2 = {-centerX + half, centerY - half, z, 0, 0, 1};
                float[] p3 = {-centerX - half, centerY - half, z, 0, 0, 1};
                float alpha = frame[6];
                addVortexVertex(output, pose, p0, u, v, tint, alpha);
                addVortexVertex(output, pose, p1, u + 0.125f, v, tint, alpha);
                addVortexVertex(output, pose, p2, u + 0.125f, v + 0.125f, tint, alpha);
                addVortexVertex(output, pose, p3, u, v + 0.125f, tint, alpha);
            }
        });
    }

    private static int emitForwardContinuation(VortexRegistry vortex,
                                              com.mojang.blaze3d.vertex.PoseStack.Pose pose,
                                              com.mojang.blaze3d.vertex.VertexConsumer output,
                                              float time, float speed, int rows, int sides,
                                              float opacity, float[][] continuationSeam) {
        float start = rows - 1.0f;
        float end = VORTEX_FORWARD_END;
        float profileO = start / rows;
        float radius = VORTEX_RADIUS * legacyWobbleRadius(profileO);
        float centerX = VORTEX_RADIUS * legacyXWobble(profileO, speed) * (float) Math.sin(profileO);
        float centerY = VORTEX_RADIUS * legacyZWobble(profileO, speed) * (float) Math.sin(profileO);
        float[] tint = vortex.getGradient().getRGBf(profileO);
        int emitted = 0;
        float[][] lowerRing = continuationSeam;
        for (int segment = 0; segment < VORTEX_FORWARD_SEGMENTS; segment++) {
            float lowerO = (start + (end - start) * segment / VORTEX_FORWARD_SEGMENTS) / rows;
            float upperO = (start + (end - start) * (segment + 1.0f) / VORTEX_FORWARD_SEGMENTS) / rows;
            float lowerV = lowerO + time;
            float upperV = upperO + time;
            float lowerTwist = (float) Math.toRadians((start + (end - start) * segment / VORTEX_FORWARD_SEGMENTS)
                    * vortex.getTwist());
            float upperTwist = (float) Math.toRadians((start + (end - start) * (segment + 1.0f) / VORTEX_FORWARD_SEGMENTS)
                    * vortex.getTwist());
            if (lowerRing == null) {
                lowerRing = forwardTubeRing(lowerO, lowerTwist, centerX, centerY, radius, rows, sides);
            }
            float[][] upperRing = forwardTubeRing(upperO, upperTwist, centerX, centerY, radius, rows, sides);
            for (int side = 0; side < sides; side++) {
                float u0 = side / (float) sides * 0.5f;
                float u1 = (side + 1.0f) / sides * 0.5f;
                float[] p0 = lowerRing[side];
                float[] p1 = upperRing[side];
                float[] p2 = upperRing[side + 1];
                float[] p3 = lowerRing[side + 1];
                addVortexVertex(output, pose, p0, u0, lowerV, tint, opacity);
                addVortexVertex(output, pose, p1, u0, upperV, tint, opacity);
                addVortexVertex(output, pose, p2, u1, upperV, tint, opacity);
                addVortexVertex(output, pose, p3, u1, lowerV, tint, opacity);
                emitted += 4;
            }
            lowerRing = upperRing;
        }
        return emitted;
    }

    private static float[][] legacyTubeRing(float o, float twist, float speed, int rows, int sides) {
        float[][] ring = new float[sides + 1][];
        for (int side = 0; side <= sides; side++) {
            float baseAngle = (float) (2.0 * Math.PI * side / sides);
            ring[side] = legacyTubePoint(o, baseAngle - twist, speed, rows);
        }
        return ring;
    }

    private static float[][] forwardTubeRing(float o, float twist, float centerX, float centerY,
                                             float radius, int rows, int sides) {
        float[][] ring = new float[sides + 1][];
        for (int side = 0; side <= sides; side++) {
            float baseAngle = (float) (2.0 * Math.PI * side / sides);
            ring[side] = forwardTubePoint(o, baseAngle - twist, centerX, centerY, radius, rows);
        }
        return ring;
    }

    private static float[] forwardTubePoint(float o, float angle, float centerX, float centerY,
                                            float radius, int rows) {
        // Keep the extension inexpensive. The previous per-vertex easing
        // recomputed several time-dependent trig functions across a very long
        // tube and caused noticeable frame stalls without removing the seam.
        return new float[]{
                -radius * (float) Math.cos(angle) - centerX,
                radius * (float) Math.sin(angle) + centerY,
                rows * VORTEX_AXIAL_SCALE * o,
                -(float) Math.cos(angle), (float) Math.sin(angle), 0.0f
        };
    }

    private static float[] legacyTubePoint(float o, float angle, float speed, int rows) {
        float radius = VORTEX_RADIUS * legacyWobbleRadius(o);
        float wobble = (float) Math.sin(o);
        float x = radius * (float) Math.cos(angle)
                + VORTEX_RADIUS * legacyXWobble(o, speed) * wobble;
        float y = radius * (float) Math.sin(angle)
                + VORTEX_RADIUS * legacyZWobble(o, speed) * wobble;
        // Center the symmetric tunnel on the PIP camera at the preview origin.
        // GuiEntityRenderer mirrors Z; its camera override is oriented to look
        // down this mirrored axis.
        return new float[]{-x, y, vortexAxialPosition(o, rows),
                -(float) Math.cos(angle), (float) Math.sin(angle), 0.0f};
    }

    private static float vortexAxialPosition(float o, int rows) {
        return rows * VORTEX_AXIAL_SCALE * o;
    }

    private static void addVortexVertex(com.mojang.blaze3d.vertex.VertexConsumer output,
                                        com.mojang.blaze3d.vertex.PoseStack.Pose pose,
                                        float[] point, float u, float v, float[] tint, float alpha) {
        output.addVertex(pose.pose(), point[0], point[1], point[2]).setUv(u, v)
                .setColor(ARGB.colorFromFloat(alpha, tint[0], tint[1], tint[2]))
                .setUv1(0, 0).setUv2(240, 240).setUv3(0, 0)
                .setNormal(point[3], point[4], point[5]);
    }

    private static void playVortexLightningSound() {
        Minecraft minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        var level = minecraft.level;
        if (player == null || level == null) return;

        // This effect is local to the rendered tunnel, so dispatch it through
        // the client sound manager instead of the level's local positional
        // sound hook. Use MASTER to keep it audible when the Blocks slider is
        // lowered, while anchoring it at the listener for full volume.
        float pitch = 0.8f + LIGHTNING_RANDOM.nextFloat() * 0.4f;
        minecraft.getSoundManager().play(new SimpleSoundInstance(
                SoundEvents.GENERIC_EXPLODE.value(), SoundSource.MASTER, 0.4f, pitch,
                RandomSource.create(), player.blockPosition()));
        whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("vortex-lightning-sound",
                "played=generic_explode, source=MASTER, volume=0.4, pitch=" + pitch
                        + ", listenerPos=" + player.blockPosition());
    }

    private static void maybePlayVortexThunderSound() {
        Minecraft minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player == null) return;

        long now = System.currentTimeMillis();
        if (nextVortexThunderAt == 0L) {
            // Let the explosion cue establish the strike first; thunder is a
            // separate, occasional accent rather than a sound on every flash.
            nextVortexThunderAt = now + 12_000L + LIGHTNING_RANDOM.nextInt(12_000);
            return;
        }
        if (now < nextVortexThunderAt) return;

        float pitch = 0.9f + LIGHTNING_RANDOM.nextFloat() * 0.2f;
        minecraft.getSoundManager().play(new SimpleSoundInstance(
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.MASTER, 0.35f, pitch,
                RandomSource.create(), player.blockPosition()));
        whocraft.tardis_refined.client.ShellPreviewDiagnostics.report("vortex-thunder-sound",
                "played=lightning_bolt_thunder, source=MASTER, volume=0.35, pitch=" + pitch
                        + ", listenerPos=" + player.blockPosition());
        nextVortexThunderAt = now + 15_000L + LIGHTNING_RANDOM.nextInt(15_000);
    }

    private static float legacyRadiusFunc(float o) {
        return 1.0f - o * o;
    }

    private static float legacyWobbleRadius(float o) {
        return legacyRadiusFunc(o) * (1.0f + 0.05f
                * (float) Math.sin(2.0 * Math.PI * (o + timingSeconds(0.687f)))
                * (float) Math.sin(2.0 * Math.PI * (o + timingSeconds(9.852f))));
    }

    private static float legacyXWobble(float o, float speed) {
        float a = (float) Math.sin(o + timingSeconds(1.999f) * (float) (2.0 * Math.PI));
        float b = (float) Math.sin(o * 0.5f + timingSeconds(3.778f) * (float) (2.0 * Math.PI));
        return (a + b) * speed * 2.0f;
    }

    private static float legacyZWobble(float o, float speed) {
        float a = (float) Math.cos(o + timingSeconds(2.256f) * (float) (2.0 * Math.PI));
        float b = (float) Math.cos(o * 0.5f + timingSeconds(3.271f) * (float) (2.0 * Math.PI));
        return (a + b) * speed * 2.0f;
    }

    private static float timingSeconds(float periodSeconds) {
        long periodMillis = (long) (periodSeconds * 1000.0f);
        return (System.currentTimeMillis() % periodMillis) / (periodSeconds * 1000.0f);
    }

    private static final class LegacyVortexDecal {
        private boolean valid;
        private boolean lightning;
        private float previousO = Float.POSITIVE_INFINITY;
        private float u;
        private float v;
        private float flashAlpha;
        private float lightningStrike;
        private boolean strikeSoundPending;
        private long nextStrikeAt;
        private long strikeStartedAt;

        private boolean update(float o, VortexRegistry vortex, long now) {
            if (!valid) {
                valid = true;
                previousO = Float.POSITIVE_INFINITY;
                u = LIGHTNING_RANDOM.nextInt(4) * 0.125f;
                v = LIGHTNING_RANDOM.nextInt(4) * 0.125f;
                lightning = vortex.hasLightning();
                flashAlpha = 0.0f;
                lightningStrike = 0.0f;
                strikeStartedAt = 0L;
                nextStrikeAt = now + 1800L + LIGHTNING_RANDOM.nextInt(3200);
            } else if (o > previousO) {
                // A decal wraps to the far end of the tube. Move its texture
                // tile, but keep its wall-clock strike schedule; resetting the
                // cooldown here could postpone a strike forever.
                u = LIGHTNING_RANDOM.nextInt(4) * 0.125f;
                v = LIGHTNING_RANDOM.nextInt(4) * 0.125f;
            }
            if (lightning && now >= nextStrikeAt) {
                strikeStartedAt = now;
                flashAlpha = 1.0f;
                strikeSoundPending = true;
                if (o > 0.0f) lightningStrike = 1.0f - o * o;
                nextStrikeAt = now + 2800L + LIGHTNING_RANDOM.nextInt(4200);
            }
            previousO = o;
            flashAlpha = strikeStartedAt == 0L ? 0.0f
                    : Math.max(0.0f, 1.0f - (now - strikeStartedAt) / (float) LIGHTNING_FLASH_MILLIS);
            lightningStrike *= 0.9f;
            return true;
        }

        private boolean consumeStrikeSound() {
            boolean pending = strikeSoundPending;
            strikeSoundPending = false;
            return pending;
        }
    }

}
