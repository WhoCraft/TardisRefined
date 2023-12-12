package whocraft.tardis_refined.client.model.blockentity.life;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.block.life.ArsEggBlock;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;

public class ArsEggModel extends HierarchicalModel {

	private final ModelPart Egg;
	private final ModelPart root;

	public static final AnimationDefinition SWING_ANIMATION  = AnimationDefinition.Builder.withLength(10.0F).looping()
			.addAnimation("Egg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, -5.0377F, -1.015F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(6.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(9.9583F, KeyframeAnimations.degreeVec(0.0F, 5.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();


	public ArsEggModel(ModelPart root) {
		this.Egg = root.getChild("Egg");
		this.root = root;
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Egg = partdefinition.addOrReplaceChild("Egg", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(18, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(10, 14).addBox(-1.0F, 1.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

		PartDefinition cube_r1 = Egg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -8.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	public void renderToBuffer(ArsEggBlockEntity arsEggBlockEntity, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root().getAllParts().forEach(ModelPart::resetPose);


		BlockState blockState = arsEggBlockEntity.getBlockState();
		Block block = blockState.getBlock();

		if(!arsEggBlockEntity.getLiveliness().isStarted()) {
			LocalPlayer player = Minecraft.getInstance().player;
			arsEggBlockEntity.getLiveliness().start(Minecraft.getInstance().cameraEntity.tickCount - player.level().random.nextInt(1000, 2000));
		}

		if(blockState.getValue(ArsEggBlock.HANGING)){
			TardisClientData tardisClientData = TardisClientData.getInstance(Minecraft.getInstance().level.dimension());
			boolean crashingOrDanger = tardisClientData.isCrashing() || tardisClientData.isInDangerZone();
			int animationCounter = Minecraft.getInstance().player.tickCount;
			if(crashingOrDanger){
				animationCounter = animationCounter * 15;
			}

			this.animate(arsEggBlockEntity.getLiveliness(), SWING_ANIMATION, animationCounter);
		}


		root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}
}