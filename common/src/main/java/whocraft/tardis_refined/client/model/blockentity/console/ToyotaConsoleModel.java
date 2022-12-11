package whocraft.tardis_refined.client.model.blockentity.console;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.client.TardisIntReactions;

public class ToyotaConsoleModel extends HierarchicalModel {

	public static final AnimationDefinition MODEL_FLIGHT_LOOP = AnimationDefinition.Builder.withLength(2f).looping()
			.addAnimation("rotor_bottom_translate_2",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, -6f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("rotor_top_translate_2",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1f, KeyframeAnimations.posVec(0f, 6f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.CATMULLROM))).build();

	private final ModelPart bone181;
	private final ModelPart bb_main;

	public ToyotaConsoleModel(ModelPart root) {
		this.bone181 = root.getChild("bone181");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone181 = partdefinition.addOrReplaceChild("bone181", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition components = bone181.addOrReplaceChild("components", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition north = components.addOrReplaceChild("north", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -13.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone182 = north.addOrReplaceChild("bone182", CubeListBuilder.create().texOffs(18, 67).addBox(5.475F, -0.475F, -1.375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 67).addBox(5.225F, -0.475F, -0.375F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(51, 4).addBox(3.875F, -0.975F, -2.125F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(16, 51).addBox(-3.0F, -0.725F, -0.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 51).addBox(-1.5F, -0.725F, -0.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(26, 65).addBox(-3.5F, -0.125F, -1.625F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(16, 51).addBox(2.0F, -0.725F, -0.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 51).addBox(0.5F, -0.725F, -0.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(32, 56).addBox(-3.0F, -0.575F, -2.375F, 6.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(24, 73).addBox(-1.0F, -0.475F, -2.375F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(72, 40).addBox(-3.25F, -0.05F, -5.625F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 71).addBox(-5.5F, -0.55F, -3.875F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 72).addBox(0.75F, -0.55F, -7.375F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 69).addBox(-2.75F, -0.55F, -7.375F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 64).addBox(-0.5F, -0.55F, -6.625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
				.texOffs(21, 61).addBox(-0.5F, -0.9F, -7.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(88, 90).addBox(-7.25F, -0.125F, -2.125F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(88, 90).mirror().addBox(5.25F, -0.125F, -2.125F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(92, 69).addBox(-7.5F, -0.625F, -1.375F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(51, 4).mirror().addBox(-4.875F, -0.975F, -2.125F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, 15.875F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone180 = bone182.addOrReplaceChild("bone180", CubeListBuilder.create().texOffs(68, 86).addBox(-2.5F, 0.0F, -0.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.05F, -4.625F, 0.5236F, 0.0F, 0.0F));

		PartDefinition bone178 = bone182.addOrReplaceChild("bone178", CubeListBuilder.create().texOffs(26, 51).addBox(0.1935F, -0.5F, -1.4809F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(26, 51).addBox(-0.75F, -0.5F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(26, 51).addBox(-7.2091F, -0.5F, 1.2322F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.25F, -0.55F, -4.375F, 0.0F, -0.48F, 0.0F));

		PartDefinition bone175 = bone182.addOrReplaceChild("bone175", CubeListBuilder.create().texOffs(92, 13).addBox(0.0F, -1.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.45F, -1.875F, 0.0F, 0.7854F, 0.0F));

		PartDefinition bone179 = bone182.addOrReplaceChild("bone179", CubeListBuilder.create().texOffs(21, 59).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, -0.55F, -3.125F, -0.6109F, 0.0F, 0.0F));

		PartDefinition bone210 = bone182.addOrReplaceChild("bone210", CubeListBuilder.create().texOffs(58, 72).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 0.0F, -0.125F, 0.4363F, 0.0F, 0.0F));

		PartDefinition bone211 = bone182.addOrReplaceChild("bone211", CubeListBuilder.create().texOffs(58, 72).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.375F, 0.0F, -0.125F, 0.4363F, 0.0F, 0.0F));

		PartDefinition north_left = components.addOrReplaceChild("north_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -13.5F, 0.0F, 0.0F, 2.0944F, 0.0F));

		PartDefinition bone183 = north_left.addOrReplaceChild("bone183", CubeListBuilder.create().texOffs(62, 32).addBox(-3.25F, 0.45F, -6.125F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.5F))
				.texOffs(0, 59).addBox(-3.5F, -0.2F, -6.375F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(91, 82).addBox(0.5F, -0.8F, -5.875F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 15.875F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone187 = bone183.addOrReplaceChild("bone187", CubeListBuilder.create().texOffs(79, 77).addBox(-2.25F, -0.5F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.25F, 0.4F, -1.375F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone209 = bone183.addOrReplaceChild("bone209", CubeListBuilder.create().texOffs(76, 0).addBox(-1.25F, -0.5F, -3.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.75F, 0.4F, -1.375F));

		PartDefinition bone188 = bone183.addOrReplaceChild("bone188", CubeListBuilder.create().texOffs(38, 62).addBox(-1.0F, -0.25F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.75F, -0.25F, -6.875F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone189 = bone188.addOrReplaceChild("bone189", CubeListBuilder.create().texOffs(64, 44).addBox(-1.25F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.3491F, 0.0F));

		PartDefinition bone190 = bone183.addOrReplaceChild("bone190", CubeListBuilder.create().texOffs(38, 62).mirror().addBox(-1.0F, -0.25F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.75F, -0.25F, -6.875F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone191 = bone190.addOrReplaceChild("bone191", CubeListBuilder.create().texOffs(62, 36).addBox(-0.75F, -0.25F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -0.3491F, 0.0F));

		PartDefinition bone186 = bone183.addOrReplaceChild("bone186", CubeListBuilder.create().texOffs(60, 91).addBox(-1.25F, -0.5F, -0.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(51, 8).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
				.texOffs(91, 25).addBox(-0.75F, -0.5F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, 0.05F, -0.625F, 0.0F, -0.5672F, 0.0F));

		PartDefinition bone192 = bone183.addOrReplaceChild("bone192", CubeListBuilder.create().texOffs(0, 91).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(51, 10).addBox(0.25F, -1.25F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-6.0F, 0.05F, -0.375F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bone = bone183.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(64, 48).addBox(-2.0F, -0.5F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, -0.3F, -4.125F, 0.0F, 0.829F, 0.0F));

		PartDefinition bone184 = bone183.addOrReplaceChild("bone184", CubeListBuilder.create().texOffs(64, 46).addBox(-2.0F, -0.75F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -0.3F, -5.625F));

		PartDefinition bone185 = bone183.addOrReplaceChild("bone185", CubeListBuilder.create().texOffs(64, 46).addBox(-1.25F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -0.55F, -1.625F, 0.0F, -0.6981F, 0.0F));

		PartDefinition south = components.addOrReplaceChild("south", CubeListBuilder.create(), PartPose.offset(0.0F, -13.5F, 1.0F));

		PartDefinition bone197 = south.addOrReplaceChild("bone197", CubeListBuilder.create().texOffs(33, 90).addBox(3.35F, -0.225F, -2.275F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(50, 86).addBox(-2.0F, -0.475F, -2.375F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(2.0F, -0.075F, -2.375F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(34, 34).addBox(-3.0F, -0.075F, -2.375F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(86, 9).addBox(-2.25F, -0.575F, -2.625F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(86, 59).addBox(-2.5F, -0.475F, -4.875F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(9, 90).addBox(-6.0F, -0.075F, -5.125F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(69, 89).addBox(2.75F, -0.075F, -5.375F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(83, 36).addBox(-2.75F, -0.125F, -7.575F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(90, 66).addBox(-6.5F, -0.975F, -1.375F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(38, 59).addBox(-6.0F, -0.775F, -1.375F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 3.0F, 14.875F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone202 = bone197.addOrReplaceChild("bone202", CubeListBuilder.create().texOffs(54, 90).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(5.25F, -0.475F, -0.375F, 0.0F, -0.6109F, 0.0F));

		PartDefinition bone199 = bone202.addOrReplaceChild("bone199", CubeListBuilder.create().texOffs(18, 67).addBox(-0.75F, -0.25F, -5.25F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.25F))
				.texOffs(54, 47).addBox(-1.0F, -0.25F, -5.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
				.texOffs(26, 49).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.75F, 0.5F, -0.1309F, 0.0F, 0.0F));

		PartDefinition bone201 = bone197.addOrReplaceChild("bone201", CubeListBuilder.create().texOffs(64, 53).addBox(-4.5F, 0.0F, -2.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, -0.125F, -6.075F, -0.4363F, 0.0F, 0.0F));

		PartDefinition bone200 = bone197.addOrReplaceChild("bone200", CubeListBuilder.create().texOffs(0, 58).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 58).addBox(-3.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 58).addBox(-2.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.025F, -3.975F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone198 = bone197.addOrReplaceChild("bone198", CubeListBuilder.create().texOffs(62, 32).addBox(-1.0F, -2.5F, -0.75F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 62).addBox(-1.0F, -2.5F, -0.25F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(32, 50).addBox(-1.5F, -3.25F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -0.475F, -0.375F, 1.0908F, 0.0F, 0.0F));

		PartDefinition south_left = components.addOrReplaceChild("south_left", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -13.5F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition bone203 = south_left.addOrReplaceChild("bone203", CubeListBuilder.create().texOffs(83, 31).addBox(-0.25F, -0.475F, -3.375F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(83, 16).addBox(-0.25F, -0.725F, -3.375F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 55).addBox(-0.75F, -0.475F, -4.275F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(89, 0).addBox(-1.25F, -0.225F, -7.375F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(87, 39).addBox(-1.5F, -0.325F, -7.125F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(62, 40).addBox(-1.25F, -0.425F, -6.625F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(89, 0).addBox(-4.75F, -0.225F, -6.625F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(32, 47).addBox(-4.25F, -1.225F, -6.125F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 44).addBox(-4.25F, -1.225F, -6.125F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(82, 44).addBox(3.5F, -0.075F, -3.125F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(42, 69).addBox(3.75F, -0.175F, -2.875F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(6, 14).addBox(5.25F, -0.575F, -0.375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(6, 14).addBox(3.75F, -0.575F, -0.375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(82, 44).mirror().addBox(-7.5F, -0.075F, -3.125F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(66, 81).addBox(-7.5F, -0.225F, -3.125F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(6, 14).mirror().addBox(-7.0F, -0.475F, -0.625F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(34, 38).addBox(-2.5F, -0.725F, -2.875F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.25F))
				.texOffs(60, 86).addBox(-3.0F, -0.225F, -3.375F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 15.875F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone206 = bone203.addOrReplaceChild("bone206", CubeListBuilder.create().texOffs(54, 44).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(44, 18).addBox(-1.25F, -0.75F, -0.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -0.475F, -5.875F, 0.0F, 0.48F, 0.0F));

		PartDefinition bone204 = bone203.addOrReplaceChild("bone204", CubeListBuilder.create().texOffs(54, 50).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -0.575F, 0.125F, -0.4363F, 0.0F, 0.0F));

		PartDefinition bone207 = bone203.addOrReplaceChild("bone207", CubeListBuilder.create().texOffs(16, 49).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-5.25F, 0.125F, -0.875F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone205 = bone203.addOrReplaceChild("bone205", CubeListBuilder.create().texOffs(78, 90).addBox(-1.5F, 0.0F, 0.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -0.725F, 0.375F, 0.6646F, -0.2494F, -0.0804F));

		PartDefinition north_right = components.addOrReplaceChild("north_right", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -13.5F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone193 = north_right.addOrReplaceChild("bone193", CubeListBuilder.create().texOffs(75, 26).addBox(-3.0F, -0.8F, -5.475F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(51, 8).addBox(-3.5F, -0.3F, -5.475F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(21, 59).addBox(-3.0F, -0.3F, -5.975F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.25F))
				.texOffs(86, 6).addBox(-3.0F, -0.05F, -7.625F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(78, 69).addBox(-8.0F, -0.05F, -3.125F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 57).addBox(-2.0F, -0.05F, -4.125F, 10.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 15.875F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone194 = bone193.addOrReplaceChild("bone194", CubeListBuilder.create().texOffs(44, 14).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-5.25F, -0.05F, -2.375F, 0.0F, -0.3054F, 0.0F));

		PartDefinition south_right = components.addOrReplaceChild("south_right", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -13.5F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone195 = south_right.addOrReplaceChild("bone195", CubeListBuilder.create().texOffs(0, 34).addBox(-6.5F, -0.05F, -7.475F, 13.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 25).addBox(-6.5F, -0.3F, -7.475F, 13.0F, 1.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 3.0F, 15.875F, -0.4974F, 0.0F, 0.0F));

		PartDefinition Monitor_1 = components.addOrReplaceChild("Monitor_1", CubeListBuilder.create().texOffs(110, 0).addBox(-4.0F, -8.0F, 10.2F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 12).addBox(-3.5F, -7.5F, 10.45F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(108, 7).addBox(-5.0F, -7.5F, 9.95F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(107, 13).addBox(4.0F, -7.5F, 9.95F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(114, 7).addBox(-3.0F, -7.0F, 9.2F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone216 = Monitor_1.addOrReplaceChild("bone216", CubeListBuilder.create().texOffs(112, 18).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.75F, 9.2F, 0.6981F, 0.0F, 0.0F));

		PartDefinition monitor_2 = components.addOrReplaceChild("monitor_2", CubeListBuilder.create().texOffs(110, 0).addBox(-4.0F, -8.0F, 10.2F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(112, 12).addBox(-3.5F, -7.5F, 10.45F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(108, 7).addBox(-5.0F, -7.5F, 9.95F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(107, 13).addBox(4.0F, -7.5F, 9.95F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(114, 7).addBox(-3.0F, -7.0F, 9.2F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 2.6616F, 0.0F));

		PartDefinition bone218 = monitor_2.addOrReplaceChild("bone218", CubeListBuilder.create().texOffs(112, 18).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.75F, 9.2F, 0.6981F, 0.0F, 0.0F));

		PartDefinition bone173 = bone181.addOrReplaceChild("bone173", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rotor_bottom_translate_2 = bone173.addOrReplaceChild("rotor_bottom_translate_2", CubeListBuilder.create().texOffs(81, 120).addBox(-3.0F, -30.25F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 117).addBox(-2.5F, -29.25F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(22, 118).addBox(-4.0F, -26.75F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(91, 101).addBox(-3.5F, -23.25F, -3.5F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(21, 90).addBox(-0.5F, -35.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -4.75F, 0.0F));

		PartDefinition rotorbottom_glow2 = rotor_bottom_translate_2.addOrReplaceChild("rotorbottom_glow2", CubeListBuilder.create().texOffs(66, 119).addBox(-1.5F, -35.25F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rotor_top_translate_2 = bone173.addOrReplaceChild("rotor_top_translate_2", CubeListBuilder.create().texOffs(65, 110).addBox(-3.0F, 27.75F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(108, 116).addBox(-2.5F, 23.25F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(96, 88).addBox(-4.0F, 24.25F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(25, 106).addBox(-3.5F, 19.25F, -3.5F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -75.25F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition rotortop_glow2 = rotor_top_translate_2.addOrReplaceChild("rotortop_glow2", CubeListBuilder.create().texOffs(67, 98).addBox(-1.5F, 29.25F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone213 = rotor_top_translate_2.addOrReplaceChild("bone213", CubeListBuilder.create().texOffs(21, 90).addBox(-0.5F, -5.5F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 29.5F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition base_console2 = bone173.addOrReplaceChild("base_console2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition centre_column2 = base_console2.addOrReplaceChild("centre_column2", CubeListBuilder.create().texOffs(25, 85).addBox(-1.0F, -28.25F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -4.75F, 0.0F));

		PartDefinition rotorbeam_glow2 = centre_column2.addOrReplaceChild("rotorbeam_glow2", CubeListBuilder.create().texOffs(46, 81).addBox(-0.5F, -25.0F, 3.625F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -18.75F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone174 = rotorbeam_glow2.addOrReplaceChild("bone174", CubeListBuilder.create().texOffs(46, 81).addBox(-0.5F, -25.0F, 3.625F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone2 = bone174.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(46, 81).addBox(-0.5F, -25.0F, 3.625F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone170 = bone2.addOrReplaceChild("bone170", CubeListBuilder.create().texOffs(46, 81).addBox(-0.5F, -25.0F, 3.625F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone171 = bone170.addOrReplaceChild("bone171", CubeListBuilder.create().texOffs(46, 81).addBox(-0.5F, -25.0F, 3.625F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone172 = bone171.addOrReplaceChild("bone172", CubeListBuilder.create().texOffs(46, 81).addBox(-0.5F, -25.0F, 3.625F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition top_column2 = centre_column2.addOrReplaceChild("top_column2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone5 = top_column2.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, -33.25F, 0.0F));

		PartDefinition bone196 = bone5.addOrReplaceChild("bone196", CubeListBuilder.create().texOffs(16, 77).addBox(-3.5F, -6.5F, 5.075F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition bone212 = bone196.addOrReplaceChild("bone212", CubeListBuilder.create().texOffs(16, 77).addBox(-3.5F, -6.5F, 5.075F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone6 = bone212.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(16, 77).addBox(-3.5F, -6.5F, 5.075F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone7 = bone6.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(16, 77).addBox(-3.5F, -6.5F, 5.075F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone215 = bone7.addOrReplaceChild("bone215", CubeListBuilder.create().texOffs(16, 77).addBox(-3.5F, -6.5F, 5.075F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone8 = bone215.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(16, 77).addBox(-3.5F, -6.5F, 5.075F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone9 = bone5.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(48, 57).addBox(-0.5F, -6.5F, 6.875F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone10 = bone9.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(48, 57).addBox(-0.5F, -6.5F, 6.875F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone219 = bone10.addOrReplaceChild("bone219", CubeListBuilder.create().texOffs(48, 57).addBox(-0.5F, -6.5F, 6.875F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone220 = bone219.addOrReplaceChild("bone220", CubeListBuilder.create().texOffs(48, 57).addBox(-0.5F, -6.5F, 6.875F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone221 = bone220.addOrReplaceChild("bone221", CubeListBuilder.create().texOffs(48, 57).addBox(-0.5F, -6.5F, 6.875F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone222 = bone221.addOrReplaceChild("bone222", CubeListBuilder.create().texOffs(48, 57).addBox(-0.5F, -6.5F, 6.875F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone3 = top_column2.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, -36.75F, 0.0F));

		PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(44, 14).addBox(-4.0F, -4.5F, -0.075F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition bone157 = bone4.addOrReplaceChild("bone157", CubeListBuilder.create().texOffs(44, 14).addBox(-4.0F, -4.5F, -0.075F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone158 = bone157.addOrReplaceChild("bone158", CubeListBuilder.create().texOffs(44, 14).addBox(-4.0F, -4.5F, -0.075F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone159 = bone158.addOrReplaceChild("bone159", CubeListBuilder.create().texOffs(44, 14).addBox(-4.0F, -4.5F, -0.075F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone160 = bone159.addOrReplaceChild("bone160", CubeListBuilder.create().texOffs(44, 14).addBox(-4.0F, -4.5F, -0.075F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone161 = bone160.addOrReplaceChild("bone161", CubeListBuilder.create().texOffs(44, 14).addBox(-4.0F, -4.5F, -0.075F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone162 = bone3.addOrReplaceChild("bone162", CubeListBuilder.create().texOffs(9, 8).addBox(-0.5F, -4.5F, 7.865F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone163 = bone162.addOrReplaceChild("bone163", CubeListBuilder.create().texOffs(9, 8).addBox(-0.5F, -4.5F, 7.865F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone164 = bone163.addOrReplaceChild("bone164", CubeListBuilder.create().texOffs(9, 8).addBox(-0.5F, -4.5F, 7.865F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone165 = bone164.addOrReplaceChild("bone165", CubeListBuilder.create().texOffs(9, 8).addBox(-0.5F, -4.5F, 7.865F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone166 = bone165.addOrReplaceChild("bone166", CubeListBuilder.create().texOffs(9, 8).addBox(-0.5F, -4.5F, 7.865F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone167 = bone166.addOrReplaceChild("bone167", CubeListBuilder.create().texOffs(9, 8).addBox(-0.5F, -4.5F, 7.865F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone223 = top_column2.addOrReplaceChild("bone223", CubeListBuilder.create(), PartPose.offset(0.0F, -32.75F, 0.0F));

		PartDefinition bone224 = bone223.addOrReplaceChild("bone224", CubeListBuilder.create().texOffs(0, 67).addBox(-3.0F, -3.5F, 0.2F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition bone225 = bone224.addOrReplaceChild("bone225", CubeListBuilder.create().texOffs(0, 67).addBox(-3.0F, -3.5F, 0.2F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone11 = bone225.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(0, 67).addBox(-3.0F, -3.5F, 0.2F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(0, 67).addBox(-3.0F, -3.5F, 0.2F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone176 = bone12.addOrReplaceChild("bone176", CubeListBuilder.create().texOffs(0, 67).addBox(-3.0F, -3.5F, 0.2F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone177 = bone176.addOrReplaceChild("bone177", CubeListBuilder.create().texOffs(0, 67).addBox(-3.0F, -3.5F, 0.2F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone13 = bone223.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(26, 43).addBox(-0.5F, -3.5F, 5.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone14 = bone13.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(26, 43).addBox(-0.5F, -3.5F, 5.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone15 = bone14.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(26, 43).addBox(-0.5F, -3.5F, 5.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone233 = bone15.addOrReplaceChild("bone233", CubeListBuilder.create().texOffs(26, 43).addBox(-0.5F, -3.5F, 5.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone234 = bone233.addOrReplaceChild("bone234", CubeListBuilder.create().texOffs(26, 43).addBox(-0.5F, -3.5F, 5.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone235 = bone234.addOrReplaceChild("bone235", CubeListBuilder.create().texOffs(26, 43).addBox(-0.5F, -3.5F, 5.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone208 = centre_column2.addOrReplaceChild("bone208", CubeListBuilder.create().texOffs(25, 85).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -46.75F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition bone236 = centre_column2.addOrReplaceChild("bone236", CubeListBuilder.create().texOffs(88, 49).addBox(-1.0F, -6.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, -45.25F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone237 = bone236.addOrReplaceChild("bone237", CubeListBuilder.create().texOffs(88, 49).addBox(-1.0F, -6.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone16 = bone237.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(88, 49).addBox(-1.0F, -6.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone17 = bone16.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(88, 49).addBox(-1.0F, -6.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone168 = bone17.addOrReplaceChild("bone168", CubeListBuilder.create().texOffs(88, 49).addBox(-1.0F, -6.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone169 = bone168.addOrReplaceChild("bone169", CubeListBuilder.create().texOffs(88, 49).addBox(-1.0F, -6.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone18 = centre_column2.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -7.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, -19.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone19 = bone18.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -7.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone20 = bone19.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -7.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone21 = bone20.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -7.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone22 = bone21.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -7.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone23 = bone22.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -7.0F, 3.125F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition base2 = base_console2.addOrReplaceChild("base2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition base_glow2 = base2.addOrReplaceChild("base_glow2", CubeListBuilder.create().texOffs(0, 43).addBox(-3.0F, -7.0F, 4.95F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition bone24 = base_glow2.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(0, 43).addBox(-3.0F, -7.0F, 4.95F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone25 = bone24.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(0, 43).addBox(-3.0F, -7.0F, 4.95F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone26 = bone25.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(0, 43).addBox(-3.0F, -7.0F, 4.95F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone27 = bone26.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(0, 43).addBox(-3.0F, -7.0F, 4.95F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone28 = bone27.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(0, 43).addBox(-3.0F, -7.0F, 4.95F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone29 = base2.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -7.0F, 6.3F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone30 = bone29.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -7.0F, 6.3F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone31 = bone30.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -7.0F, 6.3F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone32 = bone31.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -7.0F, 6.3F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone33 = bone32.addOrReplaceChild("bone33", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -7.0F, 6.3F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone34 = bone33.addOrReplaceChild("bone34", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -7.0F, 6.3F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone35 = base2.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(76, 22).addBox(-4.0F, -2.0F, -1.05F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 7.0F));

		PartDefinition bone36 = bone35.addOrReplaceChild("bone36", CubeListBuilder.create().texOffs(76, 22).addBox(-4.0F, -2.0F, 5.95F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone37 = bone36.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(76, 22).addBox(-4.0F, -2.0F, 5.95F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone38 = bone37.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(76, 22).addBox(-4.0F, -2.0F, 5.95F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone39 = bone38.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(76, 22).addBox(-4.0F, -2.0F, 5.95F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone40 = bone39.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(76, 22).addBox(-4.0F, -2.0F, 5.95F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone41 = base2.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(32, 33).addBox(-5.0F, -1.0F, -8.325F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

		PartDefinition bone42 = bone41.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(32, 33).addBox(-5.0F, -1.0F, -1.325F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone43 = bone42.addOrReplaceChild("bone43", CubeListBuilder.create().texOffs(32, 33).addBox(-5.0F, -1.0F, -1.325F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone44 = bone43.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(32, 33).addBox(-5.0F, -1.0F, -1.325F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone45 = bone44.addOrReplaceChild("bone45", CubeListBuilder.create().texOffs(32, 33).addBox(-5.0F, -1.0F, -1.325F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone46 = bone45.addOrReplaceChild("bone46", CubeListBuilder.create().texOffs(32, 33).addBox(-5.0F, -1.0F, -1.325F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone47 = base_console2.addOrReplaceChild("bone47", CubeListBuilder.create(), PartPose.offset(0.0F, -1.75F, 0.0F));

		PartDefinition bone48 = bone47.addOrReplaceChild("bone48", CubeListBuilder.create().texOffs(51, 0).addBox(-4.5F, -2.75F, 0.945F, 9.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.75F, 0.0F));

		PartDefinition bone49 = bone48.addOrReplaceChild("bone49", CubeListBuilder.create().texOffs(51, 0).addBox(-4.5F, -2.75F, 0.945F, 9.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone50 = bone49.addOrReplaceChild("bone50", CubeListBuilder.create().texOffs(51, 0).addBox(-4.5F, -2.75F, 0.945F, 9.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone51 = bone50.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(51, 0).addBox(-4.5F, -2.75F, 0.945F, 9.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone52 = bone51.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(51, 0).addBox(-4.5F, -2.75F, 0.945F, 9.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone53 = bone52.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(51, 0).addBox(-4.5F, -2.75F, 0.945F, 9.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone54 = bone47.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(68, 16).addBox(-0.5F, -1.775F, 7.89F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.75F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone55 = bone54.addOrReplaceChild("bone55", CubeListBuilder.create().texOffs(68, 16).addBox(-0.5F, -1.775F, 7.89F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone56 = bone55.addOrReplaceChild("bone56", CubeListBuilder.create().texOffs(68, 16).addBox(-0.5F, -1.775F, 7.89F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone57 = bone56.addOrReplaceChild("bone57", CubeListBuilder.create().texOffs(68, 16).addBox(-0.5F, -1.775F, 7.89F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone58 = bone57.addOrReplaceChild("bone58", CubeListBuilder.create().texOffs(68, 16).addBox(-0.5F, -1.775F, 7.89F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone59 = bone58.addOrReplaceChild("bone59", CubeListBuilder.create().texOffs(68, 16).addBox(-0.5F, -1.775F, 7.89F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone60 = base_console2.addOrReplaceChild("bone60", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition bone61 = bone60.addOrReplaceChild("bone61", CubeListBuilder.create().texOffs(0, 14).addBox(-8.5F, 0.775F, -12.0F, 17.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 19.425F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone62 = bone60.addOrReplaceChild("bone62", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone63 = bone62.addOrReplaceChild("bone63", CubeListBuilder.create().texOffs(0, 14).addBox(-8.5F, 0.775F, -12.0F, 17.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 19.425F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone64 = bone62.addOrReplaceChild("bone64", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone65 = bone64.addOrReplaceChild("bone65", CubeListBuilder.create().texOffs(0, 14).addBox(-8.5F, 0.775F, -12.0F, 17.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 19.425F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone66 = bone64.addOrReplaceChild("bone66", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone67 = bone66.addOrReplaceChild("bone67", CubeListBuilder.create().texOffs(0, 14).addBox(-8.5F, 0.775F, -12.0F, 17.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 19.425F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone68 = bone66.addOrReplaceChild("bone68", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone69 = bone68.addOrReplaceChild("bone69", CubeListBuilder.create().texOffs(0, 14).addBox(-8.5F, 0.775F, -12.0F, 17.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 19.425F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone70 = bone68.addOrReplaceChild("bone70", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone71 = bone70.addOrReplaceChild("bone71", CubeListBuilder.create().texOffs(0, 14).addBox(-8.5F, 0.775F, -12.0F, 17.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 19.425F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone72 = base_console2.addOrReplaceChild("bone72", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition bone73 = bone72.addOrReplaceChild("bone73", CubeListBuilder.create().texOffs(34, 30).addBox(-10.0F, 0.1375F, -1.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(78, 74).addBox(-4.0F, 0.1375F, -11.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 17.975F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone74 = bone73.addOrReplaceChild("bone74", CubeListBuilder.create().texOffs(34, 56).addBox(0.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.6F, 0.1F, -8.75F, 0.0F, -0.48F, 0.0F));

		PartDefinition bone75 = bone73.addOrReplaceChild("bone75", CubeListBuilder.create().texOffs(50, 44).addBox(-1.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6F, 0.1F, -8.75F, 0.0F, 0.48F, 0.0F));

		PartDefinition bone76 = bone72.addOrReplaceChild("bone76", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone77 = bone76.addOrReplaceChild("bone77", CubeListBuilder.create().texOffs(34, 30).addBox(-10.0F, 0.1375F, -1.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(78, 74).addBox(-4.0F, 0.1375F, -11.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 17.975F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone78 = bone77.addOrReplaceChild("bone78", CubeListBuilder.create().texOffs(34, 56).addBox(0.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.6F, 0.1F, -8.75F, 0.0F, -0.48F, 0.0F));

		PartDefinition bone79 = bone77.addOrReplaceChild("bone79", CubeListBuilder.create().texOffs(50, 44).addBox(-1.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6F, 0.1F, -8.75F, 0.0F, 0.48F, 0.0F));

		PartDefinition bone80 = bone76.addOrReplaceChild("bone80", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone81 = bone80.addOrReplaceChild("bone81", CubeListBuilder.create().texOffs(34, 30).addBox(-10.0F, 0.1375F, -1.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(78, 74).addBox(-4.0F, 0.1375F, -11.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 17.975F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone82 = bone81.addOrReplaceChild("bone82", CubeListBuilder.create().texOffs(34, 56).addBox(0.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.6F, 0.1F, -8.75F, 0.0F, -0.48F, 0.0F));

		PartDefinition bone83 = bone81.addOrReplaceChild("bone83", CubeListBuilder.create().texOffs(50, 44).addBox(-1.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6F, 0.1F, -8.75F, 0.0F, 0.48F, 0.0F));

		PartDefinition bone84 = bone80.addOrReplaceChild("bone84", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone85 = bone84.addOrReplaceChild("bone85", CubeListBuilder.create().texOffs(34, 30).addBox(-10.0F, 0.1375F, -1.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(78, 74).addBox(-4.0F, 0.1375F, -11.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 17.975F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone86 = bone85.addOrReplaceChild("bone86", CubeListBuilder.create().texOffs(34, 56).addBox(0.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.6F, 0.1F, -8.75F, 0.0F, -0.48F, 0.0F));

		PartDefinition bone87 = bone85.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(50, 44).addBox(-1.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6F, 0.1F, -8.75F, 0.0F, 0.48F, 0.0F));

		PartDefinition bone88 = bone84.addOrReplaceChild("bone88", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone89 = bone88.addOrReplaceChild("bone89", CubeListBuilder.create().texOffs(34, 30).addBox(-10.0F, 0.1375F, -1.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(78, 74).addBox(-4.0F, 0.1375F, -11.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 17.975F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone90 = bone89.addOrReplaceChild("bone90", CubeListBuilder.create().texOffs(34, 56).addBox(0.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.6F, 0.1F, -8.75F, 0.0F, -0.48F, 0.0F));

		PartDefinition bone91 = bone89.addOrReplaceChild("bone91", CubeListBuilder.create().texOffs(50, 44).addBox(-1.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6F, 0.1F, -8.75F, 0.0F, 0.48F, 0.0F));

		PartDefinition bone92 = bone88.addOrReplaceChild("bone92", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone93 = bone92.addOrReplaceChild("bone93", CubeListBuilder.create().texOffs(34, 30).addBox(-10.0F, 0.1375F, -1.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(78, 74).addBox(-4.0F, 0.1375F, -11.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 17.975F, -0.4974F, 0.0F, 0.0F));

		PartDefinition bone94 = bone93.addOrReplaceChild("bone94", CubeListBuilder.create().texOffs(34, 56).addBox(0.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.6F, 0.1F, -8.75F, 0.0F, -0.48F, 0.0F));

		PartDefinition bone95 = bone93.addOrReplaceChild("bone95", CubeListBuilder.create().texOffs(50, 44).addBox(-1.0F, 0.0375F, -3.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6F, 0.1F, -8.75F, 0.0F, 0.48F, 0.0F));

		PartDefinition bone96 = base_console2.addOrReplaceChild("bone96", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone97 = bone96.addOrReplaceChild("bone97", CubeListBuilder.create().texOffs(16, 44).addBox(-0.5F, 0.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 20.875F, -0.4363F, 0.0F, 0.0F));

		PartDefinition bone98 = bone96.addOrReplaceChild("bone98", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone99 = bone98.addOrReplaceChild("bone99", CubeListBuilder.create().texOffs(16, 44).addBox(-0.5F, 0.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 20.875F, -0.4363F, 0.0F, 0.0F));

		PartDefinition bone100 = bone98.addOrReplaceChild("bone100", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone101 = bone100.addOrReplaceChild("bone101", CubeListBuilder.create().texOffs(16, 44).addBox(-0.5F, 0.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 20.875F, -0.4363F, 0.0F, 0.0F));

		PartDefinition bone102 = bone100.addOrReplaceChild("bone102", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone103 = bone102.addOrReplaceChild("bone103", CubeListBuilder.create().texOffs(16, 44).addBox(-0.5F, 0.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 20.875F, -0.4363F, 0.0F, 0.0F));

		PartDefinition bone104 = bone102.addOrReplaceChild("bone104", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone105 = bone104.addOrReplaceChild("bone105", CubeListBuilder.create().texOffs(16, 44).addBox(-0.5F, 0.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 20.875F, -0.4363F, 0.0F, 0.0F));

		PartDefinition bone106 = bone104.addOrReplaceChild("bone106", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone107 = bone106.addOrReplaceChild("bone107", CubeListBuilder.create().texOffs(16, 44).addBox(-0.5F, 0.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 20.875F, -0.4363F, 0.0F, 0.0F));

		PartDefinition bone108 = base_console2.addOrReplaceChild("bone108", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition bone109 = bone108.addOrReplaceChild("bone109", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -1.0F, -13.0F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 17.375F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone110 = bone108.addOrReplaceChild("bone110", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone111 = bone110.addOrReplaceChild("bone111", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -1.0F, -13.0F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 17.375F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone112 = bone110.addOrReplaceChild("bone112", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone113 = bone112.addOrReplaceChild("bone113", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -1.0F, -13.0F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 17.375F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone114 = bone112.addOrReplaceChild("bone114", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone115 = bone114.addOrReplaceChild("bone115", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -1.0F, -13.0F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 17.375F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone116 = bone114.addOrReplaceChild("bone116", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone117 = bone116.addOrReplaceChild("bone117", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -1.0F, -13.0F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 17.375F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone118 = bone116.addOrReplaceChild("bone118", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone119 = bone118.addOrReplaceChild("bone119", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -1.0F, -13.0F, 19.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 17.375F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone120 = base_console2.addOrReplaceChild("bone120", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -13.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone121 = bone120.addOrReplaceChild("bone121", CubeListBuilder.create().texOffs(0, 43).addBox(-0.5F, -1.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.375F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone122 = bone120.addOrReplaceChild("bone122", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone123 = bone122.addOrReplaceChild("bone123", CubeListBuilder.create().texOffs(0, 43).addBox(-0.5F, -1.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.375F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone124 = bone122.addOrReplaceChild("bone124", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone125 = bone124.addOrReplaceChild("bone125", CubeListBuilder.create().texOffs(0, 43).addBox(-0.5F, -1.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.375F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone126 = bone124.addOrReplaceChild("bone126", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone127 = bone126.addOrReplaceChild("bone127", CubeListBuilder.create().texOffs(0, 43).addBox(-0.5F, -1.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.375F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone128 = bone126.addOrReplaceChild("bone128", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone129 = bone128.addOrReplaceChild("bone129", CubeListBuilder.create().texOffs(0, 43).addBox(-0.5F, -1.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.375F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone130 = bone128.addOrReplaceChild("bone130", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone131 = bone130.addOrReplaceChild("bone131", CubeListBuilder.create().texOffs(0, 43).addBox(-0.5F, -1.0F, -14.0F, 1.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 20.375F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone132 = base_console2.addOrReplaceChild("bone132", CubeListBuilder.create().texOffs(16, 43).addBox(-0.5F, -3.5F, 19.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone133 = bone132.addOrReplaceChild("bone133", CubeListBuilder.create().texOffs(16, 43).addBox(-0.5F, -3.5F, 19.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone134 = bone133.addOrReplaceChild("bone134", CubeListBuilder.create().texOffs(16, 43).addBox(-0.5F, -3.5F, 19.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone135 = bone134.addOrReplaceChild("bone135", CubeListBuilder.create().texOffs(16, 43).addBox(-0.5F, -3.5F, 19.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone136 = bone135.addOrReplaceChild("bone136", CubeListBuilder.create().texOffs(16, 43).addBox(-0.5F, -3.5F, 19.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone137 = bone136.addOrReplaceChild("bone137", CubeListBuilder.create().texOffs(16, 43).addBox(-0.5F, -3.5F, 19.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone138 = base_console2.addOrReplaceChild("bone138", CubeListBuilder.create().texOffs(34, 25).addBox(-10.0F, -3.5F, 9.33F, 20.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.5F, 7.0F));

		PartDefinition bone139 = bone138.addOrReplaceChild("bone139", CubeListBuilder.create().texOffs(34, 25).addBox(-10.0F, -3.5F, 16.33F, 20.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone140 = bone139.addOrReplaceChild("bone140", CubeListBuilder.create().texOffs(34, 25).addBox(-10.0F, -3.5F, 16.33F, 20.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone141 = bone140.addOrReplaceChild("bone141", CubeListBuilder.create().texOffs(34, 25).addBox(-10.0F, -3.5F, 16.33F, 20.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone142 = bone141.addOrReplaceChild("bone142", CubeListBuilder.create().texOffs(34, 25).addBox(-10.0F, -3.5F, 16.33F, 20.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone143 = bone142.addOrReplaceChild("bone143", CubeListBuilder.create().texOffs(34, 25).addBox(-10.0F, -3.5F, 16.33F, 20.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone144 = base_console2.addOrReplaceChild("bone144", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition bone145 = bone144.addOrReplaceChild("bone145", CubeListBuilder.create().texOffs(53, 63).addBox(-3.0F, -2.0F, -0.8F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition bone146 = bone145.addOrReplaceChild("bone146", CubeListBuilder.create().texOffs(53, 63).addBox(-3.0F, -2.0F, -0.8F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone147 = bone146.addOrReplaceChild("bone147", CubeListBuilder.create().texOffs(53, 63).addBox(-3.0F, -2.0F, -0.8F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone148 = bone147.addOrReplaceChild("bone148", CubeListBuilder.create().texOffs(53, 63).addBox(-3.0F, -2.0F, -0.8F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone149 = bone148.addOrReplaceChild("bone149", CubeListBuilder.create().texOffs(53, 63).addBox(-3.0F, -2.0F, -0.8F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone150 = bone149.addOrReplaceChild("bone150", CubeListBuilder.create().texOffs(53, 63).addBox(-3.0F, -2.0F, -0.8F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone151 = bone144.addOrReplaceChild("bone151", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, -1.975F, 5.875F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone152 = bone151.addOrReplaceChild("bone152", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, -1.975F, 5.875F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone153 = bone152.addOrReplaceChild("bone153", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, -1.975F, 5.875F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone154 = bone153.addOrReplaceChild("bone154", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, -1.975F, 5.875F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone155 = bone154.addOrReplaceChild("bone155", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, -1.975F, 5.875F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone156 = bone155.addOrReplaceChild("bone156", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, -1.975F, 5.875F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(13, 83).addBox(-6.5F, -20.0F, 13.75F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 86).addBox(3.75F, -18.5F, 14.25F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(32, 44).addBox(-15.0F, -20.0F, 2.75F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(64, 44).addBox(-14.5F, -20.0F, -9.5F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(64, 44).addBox(8.5F, -19.25F, -9.5F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(16, 49).addBox(-6.75F, -17.75F, -18.25F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 34).addBox(10.25F, -20.5F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(30, 79).addBox(8.25F, -18.25F, -14.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(33, 85).addBox(14.75F, -17.75F, -4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(90, 62).addBox(10.5F, -18.5F, 8.25F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(51, 0).addBox(1.5F, -21.0F, 9.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(51, 0).addBox(-0.5F, -21.0F, 9.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(51, 0).addBox(-2.5F, -21.0F, 9.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 63).addBox(9.0F, -18.25F, 10.75F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 51).addBox(-12.25F, -18.25F, -13.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 8).addBox(14.5F, -18.25F, 1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(50, 81).addBox(-2.5F, -19.75F, -14.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(16, 43).addBox(3.75F, -17.75F, -18.25F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone181.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.bone181;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}

	public void renderConsole(Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root().getAllParts().forEach(ModelPart::resetPose);

		TardisIntReactions reactions = TardisIntReactions.getInstance(level.dimension());
		this.animate(reactions.ROTOR_ANIMATION, MODEL_FLIGHT_LOOP, Minecraft.getInstance().player.tickCount);
		bone181.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}