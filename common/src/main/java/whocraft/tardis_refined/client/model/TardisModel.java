package whocraft.tardis_refined.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

/** Shared model helpers for block-entity and custom models that are not vanilla entity models. */
public abstract class TardisModel {
    private static final Map<ModelPart, Map<AnimationDefinition, KeyframeAnimation>> BAKED_ANIMATIONS = new WeakHashMap<>();

    public abstract ModelPart root();

    public void animate(AnimationState state, AnimationDefinition definition, float ageInTicks) {
        animate(state, definition, ageInTicks, 1.0F);
    }

    public void animate(AnimationState state, AnimationDefinition definition, float ageInTicks, float speed) {
        if (state == null || definition == null) return;
        KeyframeAnimation baked;
        synchronized (BAKED_ANIMATIONS) {
            baked = BAKED_ANIMATIONS.computeIfAbsent(root(), ignored -> new IdentityHashMap<>())
                    .computeIfAbsent(definition, animationDefinition -> animationDefinition.bake(root()));
        }
        baked.apply(state, ageInTicks, speed);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        root().render(poseStack, vertexConsumer, packedLight, packedOverlay, 0xFFFFFFFF);
    }

    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
    }
}
