package whocraft.tardis_refined.client.model.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class PoliceBoxModel extends HierarchicalModel implements IShellModel {
	private final ModelPart root;
	private final ModelPart left_door;
	private final ModelPart right_door;
	private final ModelPart frame;
	private final ModelPart fade_value;

	public static final AnimationDefinition MODEL_LAND = AnimationDefinition.Builder.withLength(8.834334f).looping()
			.addAnimation("fade_value",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 3f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(3f, KeyframeAnimations.posVec(0f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(5f, KeyframeAnimations.posVec(0f, 6f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6f, KeyframeAnimations.posVec(0f, 4f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(6.834333f, KeyframeAnimations.posVec(0f, 8f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(7.458343f, KeyframeAnimations.posVec(0f, 5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(8.834334f, KeyframeAnimations.posVec(0f, 10f, 0f),
									AnimationChannel.Interpolations.CATMULLROM))).build();

	private float initAlpha = 0;

	public PoliceBoxModel(ModelPart root) {

		this.root = root;
		this.frame = root.getChild("frame");
		this.left_door = root.getChild("left_door");
		this.right_door = root.getChild("right_door");
		this.fade_value = root.getChild("fade_value");
		this.initAlpha = this.fade_value.y;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition left_door = partdefinition.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(0, 27).addBox(1.25F, -8.0F, -0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(61, 36).addBox(1.25F, -22.0F, -1.0F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(5.15F, -20.5F, -1.75F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(1.25F, -15.0F, -0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 52).addBox(0.0F, -30.0F, 0.0F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 64).addBox(7.5F, -30.0F, -0.5F, 1.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 22.5F, -10.25F));

		PartDefinition bone5 = left_door.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(0, 15).addBox(-2.5F, -6.0F, 0.65F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, -23.0F, -0.75F));

		PartDefinition right_door = partdefinition.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(-6.25F, -8.0F, -0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 35).addBox(-6.25F, -22.0F, -0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).mirror().addBox(-6.25F, -15.0F, -0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(13, 52).mirror().addBox(-8.0F, -30.0F, 0.0F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 15).mirror().addBox(-6.25F, -29.0F, -0.1F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 22.5F, -10.25F));

		PartDefinition frame = partdefinition.addOrReplaceChild("frame", CubeListBuilder.create().texOffs(32, 59).addBox(-8.0F, -33.5F, -10.75F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-10.0F, -41.0F, -10.0F, 20.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(61, 27).addBox(-3.0F, -43.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 7).addBox(-2.0F, -46.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.5F, -47.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(32, 52).addBox(-9.0F, -37.5F, -12.0F, 18.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-12.0F, -2.0F, -12.0F, 24.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(0, 97).mirror().addBox(-9.5F, -31.5F, -1.25F, 19.0F, 30.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 117).mirror().addBox(-9.5F, -32.5F, -8.25F, 19.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone2 = frame.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 52).mirror().addBox(8.0F, -39.0F, -11.0F, 3.0F, 37.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 52).mirror().addBox(8.0F, -39.0F, 8.0F, 3.0F, 37.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 52).addBox(-11.0F, -39.0F, 8.0F, 3.0F, 37.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 52).addBox(-11.0F, -39.0F, -11.0F, 3.0F, 37.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone6 = frame.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(32, 59).addBox(-8.0F, -33.5F, -10.75F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 52).addBox(-9.0F, -37.5F, -12.0F, 18.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone7 = bone6.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0, 27).addBox(-6.75F, -9.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-6.75F, -23.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-6.75F, -16.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 52).addBox(-8.0F, -31.5F, -10.25F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 64).addBox(-0.5F, -31.5F, -10.75F, 1.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone8 = bone7.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(0, 15).addBox(-2.5F, -6.0F, 0.65F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.25F, -24.5F, -11.0F));

		PartDefinition bone9 = bone6.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(1.75F, -9.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 27).mirror().addBox(1.75F, -23.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 27).mirror().addBox(1.75F, -16.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(13, 52).mirror().addBox(0.0F, -31.5F, -10.25F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(32, 64).mirror().addBox(-0.5F, -31.5F, -10.75F, 1.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 15).mirror().addBox(1.75F, -30.5F, -10.35F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone10 = frame.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(32, 59).addBox(-8.0F, -33.5F, -10.75F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 52).addBox(-9.0F, -37.5F, -12.0F, 18.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone11 = bone10.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(0, 27).addBox(-6.75F, -9.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-6.75F, -23.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-6.75F, -16.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 52).addBox(-8.0F, -31.5F, -10.25F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 64).addBox(-0.5F, -31.5F, -10.75F, 1.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(0, 15).addBox(-2.5F, -6.0F, 0.65F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.25F, -24.5F, -11.0F));

		PartDefinition bone13 = bone10.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(1.75F, -9.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 27).mirror().addBox(1.75F, -23.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 27).mirror().addBox(1.75F, -16.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(13, 52).mirror().addBox(0.0F, -31.5F, -10.25F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(32, 64).mirror().addBox(-0.5F, -31.5F, -10.75F, 1.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 15).mirror().addBox(1.75F, -30.5F, -10.35F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone14 = frame.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(32, 59).addBox(-8.0F, -33.5F, -10.75F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 52).addBox(-9.0F, -37.5F, -12.0F, 18.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone15 = bone14.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(0, 27).addBox(-6.75F, -9.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-6.75F, -23.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-6.75F, -16.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 52).addBox(-8.0F, -31.5F, -10.25F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 64).addBox(-0.5F, -31.5F, -10.75F, 1.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(0, 15).addBox(-2.5F, -6.0F, 0.65F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.25F, -24.5F, -11.0F));

		PartDefinition bone17 = bone14.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(1.75F, -9.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 27).mirror().addBox(1.75F, -23.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 27).mirror().addBox(1.75F, -16.5F, -10.75F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(13, 52).mirror().addBox(0.0F, -31.5F, -10.25F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(32, 64).mirror().addBox(-0.5F, -31.5F, -10.75F, 1.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 15).mirror().addBox(1.75F, -30.5F, -10.35F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition fade_value = partdefinition.addOrReplaceChild("fade_value", CubeListBuilder.create().texOffs(60, 13).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}

	@Override
	public void setDoorPosition(boolean open) {
		this.right_door.yRot = (open) ? -275f : 0;
	}


	@Override
	public void renderShell(GlobalShellBlockEntity entity, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

		if(entity.id == null) return;
		this.root().getAllParts().forEach(ModelPart::resetPose);
		TardisClientData reactions = TardisClientData.getInstance(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(TardisRefined.MODID, entity.id.toString())));

		this.animate(reactions.ROTOR_ANIMATION, MODEL_LAND, Minecraft.getInstance().player.tickCount);
		// Use sine wave to oscillate the value of currentAlpha between 0 and 1.0
		double elapsedTime = entity.getLevel().getGameTime() / 50.0;
		//float currentAlpha = reactions.isFlying() ? (float)((1.0 - Math.abs(Math.sin(elapsedTime))) / 2.0) : alpha;
		float currentAlpha = (this.initAlpha - this.fade_value.y) * 0.05f;
		System.out.println(currentAlpha);


		frame.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, currentAlpha);
		left_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, currentAlpha);
		right_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, currentAlpha);
		fade_value.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, currentAlpha);
	}

	@Override
	public ResourceLocation texture() {
		return ShellTheme.POLICE_BOX.getExternalShellTexture();
	}

	@Override
	public ResourceLocation lightTexture() {
		return ShellTheme.POLICE_BOX.emmissiveExternal();
	}
}