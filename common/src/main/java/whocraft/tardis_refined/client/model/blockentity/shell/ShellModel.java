package whocraft.tardis_refined.client.model.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import whocraft.tardis_refined.client.model.TardisModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.ShellPreviewDiagnostics;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.patterns.ShellPattern;

public abstract class ShellModel extends TardisModel {

    public static final AnimationDefinition MODEL_TAKEOFF = AnimationDefinition.Builder.withLength(12f)
            .addAnimation("fade_value",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 9.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, 9.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.125f, KeyframeAnimations.posVec(0f, 5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3f, KeyframeAnimations.posVec(0f, 10f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5f, KeyframeAnimations.posVec(0f, 6f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.791677f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.916767f, KeyframeAnimations.posVec(0f, 5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(8f, KeyframeAnimations.posVec(0f, 2f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(9.167666f, KeyframeAnimations.posVec(0f, 4f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(12f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM))).build();
    ModelPart fade_value;
    float initAlpha = 0;
    float ANIMATION_SPEED = 1.1f;
    boolean ignoreAnmationAlpha = false;
    AnimationDefinition MODEL_LAND = AnimationDefinition.Builder.withLength(11f)
            .addAnimation("fade_value",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3f, KeyframeAnimations.posVec(0f, 2f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5f, KeyframeAnimations.posVec(0f, 4f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7f, KeyframeAnimations.posVec(0f, 6f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(8f, KeyframeAnimations.posVec(0f, 2f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(9.5F, KeyframeAnimations.posVec(0f, 10f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    private float currentAlpha = 0;
    public ShellModel(ModelPart root) {
        this.fade_value = root.getChild("fade_value");
        this.initAlpha = this.fade_value.y;
    }

    public static void addMaterializationPart(PartDefinition partDefinition) {
        partDefinition.addOrReplaceChild("fade_value", CubeListBuilder.create().texOffs(128, 128), PartPose.offset(-24.0F, 24.0F, 0.0F));
    }

    public abstract void setDoorPosition(boolean open);

    public abstract void renderShell(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);

    public Identifier getShellTexture(ShellPattern pattern, boolean isEmmissive) {
        return texture(pattern, isEmmissive);
    }

    public Identifier getShellTexture(GlobalShellBlockEntity shellBlockEntitys, boolean isEmmissive) {
        ShellPattern pattern = shellBlockEntitys.pattern();
        return texture(pattern, isEmmissive);
    }

    private Identifier texture(ShellPattern pattern, boolean isEmmissive) {
        return isEmmissive ? pattern.shellTexture().emissiveTexture() : pattern.shellTexture().texture();
    }

    public boolean isIgnoreAnmationAlpha() {
        return ignoreAnmationAlpha;
    }

    public void setIgnoreAnmationAlpha(boolean ignoreAnmationAlpha) {
        this.ignoreAnmationAlpha = ignoreAnmationAlpha;
    }

    public float initAlpha() {
        return initAlpha;
    }

    public ModelPart fadeValue() {
        return fade_value;
    }

    public float getCurrentAlpha() {
        return currentAlpha;
    }

    public void handleAllAnimations(GlobalShellBlockEntity entity, ModelPart root, boolean isBaseModel, boolean isDoorOpen, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float baseAlpha) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        setDoorPosition(isDoorOpen);
        boolean flying = false;
        boolean takingOff = false;
        if (entity.getTardisId() != null) {
            entity.liveliness.start(12);
            TardisClientData reactions = TardisClientData.getInstance(entity.getTardisId());
            flying = reactions.isFlying();
            takingOff = reactions.isTakingOff();
            if (reactions.isLanding()) {
                this.animate(reactions.LANDING_ANIMATION, MODEL_LAND, reactions.landingTime * ANIMATION_SPEED);
            }
            if (takingOff) {
                // The rotor state only starts once steady flight begins. Drive
                // the takeoff pose from its own state so the shell animates
                // from the first takeoff tick, including its in-vortex copy.
                this.animate(reactions.TAKEOFF_ANIMATION, MODEL_TAKEOFF, reactions.takeOffTime * ANIMATION_SPEED);
            }
            currentAlpha = (flying || takingOff) && !ignoreAnmationAlpha
                    ? (this.initAlpha() - this.fadeValue().y) * 0.1f
                    : baseAlpha;
            if (takingOff) {
                ShellPreviewDiagnostics.reportEvery("physical-shell-takeoff",
                        "tardis=" + entity.getTardisId().identifier() + ", model=" + getClass().getSimpleName()
                                + ", takeOffTime=" + reactions.takeOffTime
                                + ", takeoffAnimationStarted=" + reactions.TAKEOFF_ANIMATION.isStarted()
                                + ", fadeY=" + fadeValue().y + ", initFadeY=" + initAlpha
                                + ", currentAlpha=" + currentAlpha + ", ignoreAnimationAlpha="
                                + ignoreAnmationAlpha, 1000);
            }
            handleSpecialAnimation(entity, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, baseAlpha);
        } else {
            // Unlinked shells still need their model; only their TARDIS-specific animation is absent.
            currentAlpha = baseAlpha;
        }

        boolean useAnimationAlpha = (flying || takingOff) && !ignoreAnmationAlpha;
        this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, net.minecraft.util.ARGB.colorFromFloat(useAnimationAlpha ? this.getCurrentAlpha() : baseAlpha, red, green, blue));
    }

    public void handleSpecialAnimation(GlobalShellBlockEntity entity, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float baseAlpha) {

    }
}
