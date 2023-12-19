package whocraft.tardis_refined.client.model.blockentity.life;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.life.ArsEggBlock;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;

public class ArsEggModel extends HierarchicalModel {

	private final ModelPart Lamp;
	private final ModelPart root;

	public static final AnimationDefinition SWINGING = AnimationDefinition.Builder.withLength(8.0F).looping()
			.addAnimation("Lamp", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(0.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(8.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

	public static final AnimationDefinition CRASHING = AnimationDefinition.Builder.withLength(7.875F).looping()
			.addAnimation("Lamp", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -6.7F, -0.79F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.4217F, 22.6276F, 2.087F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(-0.106F, -0.9725F, -7.5631F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(4.5F, KeyframeAnimations.degreeVec(-3.3138F, -8.9145F, 9.1782F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(-0.102F, -10.1874F, 1.7066F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(7.5F, KeyframeAnimations.degreeVec(0.0F, -6.7F, -0.79F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();
	private final ModelPart clamp;


	public ArsEggModel(ModelPart root) {
		this.Lamp = root.getChild("Lamp");
		this.clamp = Lamp.getChild("clamp");
		this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Lamp = partdefinition.addOrReplaceChild("Lamp", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition clamp = Lamp.addOrReplaceChild("clamp", CubeListBuilder.create().texOffs(18, 20).addBox(-4.0F, -10.0F, -1.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 17).addBox(-3.0F, -11.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(20, 23).addBox(-2.0F, -13.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

		PartDefinition cube_r1 = clamp.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 17).addBox(-4.0F, -10.0F, -1.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bulb = Lamp.addOrReplaceChild("bulb", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void renderToBuffer(ArsEggBlockEntity arsEggBlockEntity, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		BlockState blockState = arsEggBlockEntity.getBlockState();

		if (blockState.hasProperty(ArsEggBlock.ALIVE)) {
			clamp.visible = blockState.getValue(ArsEggBlock.ALIVE);
		}

		Lamp.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Lamp.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}

	public void doAnimation(AnimationState liveliness, AnimationDefinition animationDefinition, int animationCounter) {
		this.animate(liveliness, animationDefinition, animationCounter);
	}
}