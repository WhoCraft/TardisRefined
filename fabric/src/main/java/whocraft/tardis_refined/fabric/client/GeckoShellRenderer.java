package whocraft.tardis_refined.fabric.client;

import com.geckolib.renderer.GeoBlockRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.model.blockentity.shell.ThemeShellGeoModel;
import whocraft.tardis_refined.client.renderer.LegacyBlockEntityRenderState;
import whocraft.tardis_refined.client.renderer.blockentity.shell.GlobalShellRenderer;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

/** Uses GeckoLib for built-in exterior shell geometry and the legacy path for addon shells. */
public final class GeckoShellRenderer extends GeoBlockRenderer<GlobalShellBlockEntity, BlockEntityRenderState> {
    private static final float FLIGHT_FADE_TICKS = 256.0F; // 12.8 seconds, matching tardis_land.ogg
    // Opacity samples adapted from ShellModel.MODEL_TAKEOFF / MODEL_LAND.
    // Their original keyframe pulses are retimed to the longer GeckoLib fade.
    private static final float[][] TAKEOFF_FADE_KEYS = {
            {0.0F, 0.95F}, {1.0F, 0.95F}, {2.125F, 0.5F}, {3.0F, 1.0F},
            {4.0F, 0.3F}, {5.0F, 0.6F}, {5.791677F, 0.3F}, {6.916767F, 0.5F},
            {8.0F, 0.2F}, {9.167666F, 0.4F}, {12.0F, 0.0F}
    };
    private static final float[][] LANDING_FADE_KEYS = {
            {0.0F, 0.0F}, {1.0F, 0.1F}, {2.0F, 0.0F}, {3.0F, 0.2F},
            {4.0F, 0.0F}, {5.0F, 0.4F}, {6.0F, 0.0F}, {7.0F, 0.6F},
            {8.0F, 0.2F}, {9.5F, 1.0F}, {11.0F, 1.0F}
    };
    private final GlobalShellRenderer legacy;

    public GeckoShellRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new ThemeShellGeoModel());
        this.legacy = new GlobalShellRenderer(context);
    }

    @Override
    public int getRenderColor(GlobalShellBlockEntity entity, Void relatedObject, float partialTick) {
        return ARGB.colorFromFloat(getFlightAlpha(entity, partialTick), 1.0F, 1.0F, 1.0F);
    }

    @Override
    public RenderType getRenderType(BlockEntityRenderState state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    @Override
    public void preRenderPass(RenderPassInfo<BlockEntityRenderState> info, SubmitNodeCollector collector) {
        super.preRenderPass(info, collector);
        boolean open = info.getOrDefaultGeckolibData(ThemeShellGeoModel.SHELL_OPEN, false);
        GlobalShellBlockEntity entity = info.getGeckolibData(ThemeShellGeoModel.BLOCK_ENTITY);
        String shell = entity == null ? "factory" : ThemeShellGeoModel.selectedTheme(info.renderState()).getPath();

        // The old model renderer drove door poses from the shell's OPEN
        // blockstate. Apply the matching pose to GeckoLib's immutable render
        // snapshots so the visual doors track that same state.
        if (shell.equals("factory") || shell.equals("half_baked") || shell.equals("mystic") || shell.equals("nuka")) {
            setBoneRotY(info, "left_door", open ? (float) Math.toRadians(-250) : 0);
            setBoneRotY(info, "right_door", open ? (float) Math.toRadians(250) : 0);
        } else if (shell.equals("police_box")) {
            setBoneRotY(info, "right_door", open ? (float) Math.toRadians(275) : 0);
        } else if (shell.equals("pathfinder")) {
            setBoneRotY(info, "l_door", open ? (float) Math.toRadians(275) : 0);
            setBoneRotY(info, "r_door", open ? (float) Math.toRadians(-275) : 0);
        } else if (shell.equals("castle")) {
            setBoneRotY(info, "l_door", open ? (float) Math.toRadians(90) : 0);
            setBoneRotY(info, "r_door", open ? (float) Math.toRadians(-90) : 0);
        } else if (shell.equals("briefcase")) {
            setBoneRotX(info, "door", open ? 2f : 0);
        } else if (shell.equals("present")) {
            setBoneRotX(info, "door_rotate_neg_92dot5", open ? -1.5f : 0);
        } else if (shell.equals("vending")) {
            setBoneRotX(info, "door", open ? -1.6f : 0);
        } else if (shell.equals("portaloo")) {
            setBoneRotY(info, "door", open ? -1.75f : 0);
        } else if (shell.equals("phone_booth")) {
            setBoneRotY(info, "Door", open ? -1.75f : 0);
        } else if (shell.equals("pagoda") || shell.equals("groening")) {
            setBoneRotY(info, "door", open ? (float) Math.toRadians(275) : 0);
        }

        setBoneVisibility(info, "door_open", open);
        setBoneVisibility(info, "door_closed", !open);
    }

    private static void setBoneRotY(RenderPassInfo<BlockEntityRenderState> info, String name, float angle) {
        info.addBoneUpdater((pass, snapshots) -> snapshots.ifPresent(name,
                bone -> bone.setRotY(bone.getBone().baseRotY() + angle)));
    }

    private static void setBoneRotX(RenderPassInfo<BlockEntityRenderState> info, String name, float angle) {
        info.addBoneUpdater((pass, snapshots) -> snapshots.ifPresent(name,
                bone -> bone.setRotX(bone.getBone().baseRotX() + angle)));
    }

    private static void setBoneVisibility(RenderPassInfo<BlockEntityRenderState> info, String name, boolean visible) {
        info.addBoneUpdater((pass, snapshots) -> snapshots.ifPresent(name, bone -> bone.skipRender(!visible)));
    }

    @Override
    public void extractRenderState(GlobalShellBlockEntity entity, BlockEntityRenderState state, float partialTick,
                                   Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        super.extractRenderState(entity, state, partialTick, cameraPosition, crumblingOverlay);
        GeoRenderState geoState = (GeoRenderState) state;
        geoState.addGeckolibData(ThemeShellGeoModel.BLOCK_ENTITY, entity);

        // The exterior block entity is the persisted source of truth for its
        // appearance. Client-side TARDIS data can be created with default
        // values before its sync packet arrives, which would briefly replace
        // a player's selected shell with the factory appearance.
        Identifier theme = entity.theme();
        Identifier pattern = entity.pattern().id();
        geoState.addGeckolibData(ThemeShellGeoModel.SHELL_THEME, theme);
        geoState.addGeckolibData(ThemeShellGeoModel.SHELL_PATTERN, pattern);
        geoState.addGeckolibData(ThemeShellGeoModel.SHELL_OPEN, entity.getBlockState().getValue(GlobalShellBlock.OPEN));
    }

    @Override
    public void submit(BlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        GlobalShellBlockEntity entity = ((GeoRenderState) state).getGeckolibData(ThemeShellGeoModel.BLOCK_ENTITY);
        if (entity != null && ThemeShellGeoModel.supports(ThemeShellGeoModel.selectedTheme((GeoRenderState) state))) {
            // GeoLib's Y rotation convention reverses the horizontal direction
            // used by the shell block. Negating the block yaw keeps the model's
            // local doorway on the same side as the physical entrance.
            float rotation = -entity.getBlockState().getValue(ShellBaseBlock.FACING).toYRot();
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.0F, 0.5F);
            poseStack.rotateDegrees(Axis.YP, rotation);
            poseStack.translate(-0.5F, 0.0F, -0.5F);
            super.submit(state, poseStack, collector, cameraState);
            poseStack.popPose();
            return;
        }

        LegacyBlockEntityRenderState<GlobalShellBlockEntity> legacyState = legacy.createRenderState();
        legacy.extractRenderState(entity, legacyState, ((GeoRenderState) state).getPartialTick(), Vec3.ZERO, state.breakProgress);
        legacy.submit(legacyState, poseStack, collector, cameraState);
    }

    /** Pulses the shell through the legacy fade keyframes over 6.5 seconds. */
    private static float getFlightAlpha(GlobalShellBlockEntity entity, float partialTick) {
        if (entity.getTardisId() == null) return 1.0F;

        TardisClientData data = TardisClientData.getInstance(entity.getTardisId());
        if (data.isTakingOff()) {
            float progress = Mth.clamp((data.takeOffTime + partialTick) / FLIGHT_FADE_TICKS, 0.0F, 1.0F);
            return sampleFadeKeys(TAKEOFF_FADE_KEYS, progress * 12.0F);
        }
        if (data.isLanding()) {
            float progress = Mth.clamp((data.landingTime + partialTick) / FLIGHT_FADE_TICKS, 0.0F, 1.0F);
            return sampleFadeKeys(LANDING_FADE_KEYS, progress * 11.0F);
        }
        return 1.0F;
    }

    private static float sampleFadeKeys(float[][] keys, float time) {
        int segment = 0;
        while (segment < keys.length - 2 && time > keys[segment + 1][0]) {
            segment++;
        }

        float[] start = keys[segment];
        float[] end = keys[segment + 1];
        float amount = Mth.clamp((time - start[0]) / (end[0] - start[0]), 0.0F, 1.0F);
        float before = keys[Math.max(0, segment - 1)][1];
        float after = keys[Math.min(keys.length - 1, segment + 2)][1];
        float amount2 = amount * amount;
        float amount3 = amount2 * amount;

        // Catmull-Rom matches the rounded transitions used by the original model animation.
        float value = 0.5F * ((2.0F * start[1])
                + (-before + end[1]) * amount
                + (2.0F * before - 5.0F * start[1] + 4.0F * end[1] - after) * amount2
                + (-before + 3.0F * start[1] - 3.0F * end[1] + after) * amount3);
        return Mth.clamp(value, 0.0F, 1.0F);
    }

}
