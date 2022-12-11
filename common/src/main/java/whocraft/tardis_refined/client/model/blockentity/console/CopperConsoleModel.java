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

public class CopperConsoleModel extends HierarchicalModel {

	private final ModelPart root;
	private final ModelPart rotor;
	private final ModelPart misc;
	private final ModelPart misc2;
	private final ModelPart misc3;
	private final ModelPart misc4;
	private final ModelPart misc5;
	private final ModelPart north_left;
	private final ModelPart north_right;
	private final ModelPart east;
	private final ModelPart south_right;
	private final ModelPart south_left;
	private final ModelPart west;


	public static final AnimationDefinition COPPER_FLIGHT_LOOP = AnimationDefinition.Builder.withLength(2.2916765f).looping()
			.addAnimation("rotor",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, -5f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.5416767f, KeyframeAnimations.posVec(0f, 2f, 0f),
									AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(2.2916765f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();

	public CopperConsoleModel(ModelPart root) {
		this.root = root.getChild("root");
		this.rotor = root.getChild("rotor");
		this.misc = root.getChild("misc");
		this.misc2 = root.getChild("misc2");
		this.misc3 = root.getChild("misc3");
		this.misc4 = root.getChild("misc4");
		this.misc5 = root.getChild("misc5");
		this.north_left = root.getChild("north_left");
		this.north_right = root.getChild("north_right");
		this.east = root.getChild("east");
		this.south_right = root.getChild("south_right");
		this.south_left = root.getChild("south_left");
		this.west = root.getChild("west");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 46).addBox(-2.0F, -72.0F, -2.0F, 4.0F, 53.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone56 = base.addOrReplaceChild("bone56", CubeListBuilder.create().texOffs(43, 28).addBox(-11.5F, -6.5F, 2.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(43, 28).addBox(-11.5F, -6.5F, -5.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(114, 72).addBox(-10.5F, -6.5F, -3.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.5F, 0.0F));

		PartDefinition bone49 = base.addOrReplaceChild("bone49", CubeListBuilder.create(), PartPose.offset(0.25F, -7.5F, 0.0F));

		PartDefinition bone55 = bone49.addOrReplaceChild("bone55", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, 9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone51 = bone55.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, -3.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, 1.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone50 = bone49.addOrReplaceChild("bone50", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, -9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone52 = bone50.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, 0.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, -1.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone57 = base.addOrReplaceChild("bone57", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone58 = bone57.addOrReplaceChild("bone58", CubeListBuilder.create().texOffs(0, 106).addBox(-6.75F, 0.0F, -7.25F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(92, 114).addBox(-4.5F, -12.75F, -4.25F, 7.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -4.5F, 4.25F, 0.0F, 0.0F, -0.7418F));

		PartDefinition bone53 = base.addOrReplaceChild("bone53", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition bone54 = bone53.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(43, 30).addBox(0.0F, -1.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone40 = base.addOrReplaceChild("bone40", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone200 = bone40.addOrReplaceChild("bone200", CubeListBuilder.create().texOffs(108, 49).addBox(0.0F, -0.25F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone123 = base.addOrReplaceChild("bone123", CubeListBuilder.create().texOffs(67, 18).addBox(-15.25F, -2.425F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition base2 = base.addOrReplaceChild("base2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone59 = base2.addOrReplaceChild("bone59", CubeListBuilder.create().texOffs(43, 28).addBox(-11.5F, -6.5F, 2.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(43, 28).addBox(-11.5F, -6.5F, -5.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(114, 72).addBox(-10.5F, -6.5F, -3.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.5F, 0.0F));

		PartDefinition bone60 = base2.addOrReplaceChild("bone60", CubeListBuilder.create(), PartPose.offset(0.25F, -7.5F, 0.0F));

		PartDefinition bone61 = bone60.addOrReplaceChild("bone61", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, 9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone66 = bone61.addOrReplaceChild("bone66", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, -3.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, 1.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone67 = bone60.addOrReplaceChild("bone67", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, -9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone68 = bone67.addOrReplaceChild("bone68", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, 0.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, -1.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone69 = base2.addOrReplaceChild("bone69", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone70 = bone69.addOrReplaceChild("bone70", CubeListBuilder.create().texOffs(0, 106).addBox(-6.75F, 0.0F, -7.25F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(92, 114).addBox(-4.5F, -12.75F, -4.25F, 7.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -4.5F, 4.25F, 0.0F, 0.0F, -0.7418F));

		PartDefinition bone71 = base2.addOrReplaceChild("bone71", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition bone72 = bone71.addOrReplaceChild("bone72", CubeListBuilder.create().texOffs(43, 30).addBox(0.0F, -1.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone73 = base2.addOrReplaceChild("bone73", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone74 = bone73.addOrReplaceChild("bone74", CubeListBuilder.create().texOffs(108, 49).addBox(0.0F, -0.25F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone75 = base2.addOrReplaceChild("bone75", CubeListBuilder.create().texOffs(67, 18).addBox(-15.25F, -2.425F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition base3 = base2.addOrReplaceChild("base3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone76 = base3.addOrReplaceChild("bone76", CubeListBuilder.create().texOffs(43, 28).addBox(-11.5F, -6.5F, 2.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(43, 28).addBox(-11.5F, -6.5F, -5.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(114, 72).addBox(-10.5F, -6.5F, -3.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.5F, 0.0F));

		PartDefinition bone77 = base3.addOrReplaceChild("bone77", CubeListBuilder.create(), PartPose.offset(0.25F, -7.5F, 0.0F));

		PartDefinition bone78 = bone77.addOrReplaceChild("bone78", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, 9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone79 = bone78.addOrReplaceChild("bone79", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, -3.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, 1.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone80 = bone77.addOrReplaceChild("bone80", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, -9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone81 = bone80.addOrReplaceChild("bone81", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, 0.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, -1.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone82 = base3.addOrReplaceChild("bone82", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone83 = bone82.addOrReplaceChild("bone83", CubeListBuilder.create().texOffs(0, 106).addBox(-6.75F, 0.0F, -7.25F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(92, 114).addBox(-4.5F, -12.75F, -4.25F, 7.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -4.5F, 4.25F, 0.0F, 0.0F, -0.7418F));

		PartDefinition bone84 = base3.addOrReplaceChild("bone84", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition bone85 = bone84.addOrReplaceChild("bone85", CubeListBuilder.create().texOffs(43, 30).addBox(0.0F, -1.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone86 = base3.addOrReplaceChild("bone86", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone87 = bone86.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(108, 49).addBox(0.0F, -0.25F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone88 = base3.addOrReplaceChild("bone88", CubeListBuilder.create().texOffs(67, 18).addBox(-15.25F, -2.425F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition base4 = base3.addOrReplaceChild("base4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone89 = base4.addOrReplaceChild("bone89", CubeListBuilder.create().texOffs(43, 28).addBox(-11.5F, -6.5F, 2.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(43, 28).addBox(-11.5F, -6.5F, -5.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(114, 72).addBox(-10.5F, -6.5F, -3.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.5F, 0.0F));

		PartDefinition bone90 = base4.addOrReplaceChild("bone90", CubeListBuilder.create(), PartPose.offset(0.25F, -7.5F, 0.0F));

		PartDefinition bone91 = bone90.addOrReplaceChild("bone91", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, 9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone92 = bone91.addOrReplaceChild("bone92", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, -3.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, 1.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone93 = bone90.addOrReplaceChild("bone93", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, -9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone94 = bone93.addOrReplaceChild("bone94", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, 0.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, -1.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone95 = base4.addOrReplaceChild("bone95", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone96 = bone95.addOrReplaceChild("bone96", CubeListBuilder.create().texOffs(0, 106).addBox(-6.75F, 0.0F, -7.25F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(92, 114).addBox(-4.5F, -12.75F, -4.25F, 7.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -4.5F, 4.25F, 0.0F, 0.0F, -0.7418F));

		PartDefinition bone97 = base4.addOrReplaceChild("bone97", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition bone98 = bone97.addOrReplaceChild("bone98", CubeListBuilder.create().texOffs(43, 30).addBox(0.0F, -1.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone99 = base4.addOrReplaceChild("bone99", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone100 = bone99.addOrReplaceChild("bone100", CubeListBuilder.create().texOffs(108, 49).addBox(0.0F, -0.25F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone101 = base4.addOrReplaceChild("bone101", CubeListBuilder.create().texOffs(67, 18).addBox(-15.25F, -2.425F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition base5 = base4.addOrReplaceChild("base5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone102 = base5.addOrReplaceChild("bone102", CubeListBuilder.create().texOffs(43, 28).addBox(-11.5F, -6.5F, 2.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(43, 28).addBox(-11.5F, -6.5F, -5.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(114, 72).addBox(-10.5F, -6.5F, -3.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.5F, 0.0F));

		PartDefinition bone103 = base5.addOrReplaceChild("bone103", CubeListBuilder.create(), PartPose.offset(0.25F, -7.5F, 0.0F));

		PartDefinition bone104 = bone103.addOrReplaceChild("bone104", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, 9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone105 = bone104.addOrReplaceChild("bone105", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, -3.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, 1.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone106 = bone103.addOrReplaceChild("bone106", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, -9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone107 = bone106.addOrReplaceChild("bone107", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, 0.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, -1.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone108 = base5.addOrReplaceChild("bone108", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone109 = bone108.addOrReplaceChild("bone109", CubeListBuilder.create().texOffs(0, 106).addBox(-6.75F, 0.0F, -7.25F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(92, 114).addBox(-4.5F, -12.75F, -4.25F, 7.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -4.5F, 4.25F, 0.0F, 0.0F, -0.7418F));

		PartDefinition bone110 = base5.addOrReplaceChild("bone110", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition bone111 = bone110.addOrReplaceChild("bone111", CubeListBuilder.create().texOffs(43, 30).addBox(0.0F, -1.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone112 = base5.addOrReplaceChild("bone112", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone113 = bone112.addOrReplaceChild("bone113", CubeListBuilder.create().texOffs(108, 49).addBox(0.0F, -0.25F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone114 = base5.addOrReplaceChild("bone114", CubeListBuilder.create().texOffs(67, 18).addBox(-15.25F, -2.425F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition base6 = base5.addOrReplaceChild("base6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone115 = base6.addOrReplaceChild("bone115", CubeListBuilder.create().texOffs(43, 28).addBox(-11.5F, -6.5F, 2.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(43, 28).addBox(-11.5F, -6.5F, -5.5F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(114, 72).addBox(-10.5F, -6.5F, -3.0F, 1.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.5F, 0.0F));

		PartDefinition bone116 = base6.addOrReplaceChild("bone116", CubeListBuilder.create(), PartPose.offset(0.25F, -7.5F, 0.0F));

		PartDefinition bone117 = bone116.addOrReplaceChild("bone117", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, 9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone118 = bone117.addOrReplaceChild("bone118", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, -3.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, 1.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone119 = bone116.addOrReplaceChild("bone119", CubeListBuilder.create().texOffs(132, 11).addBox(0.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offsetAndRotation(-21.5F, -1.0F, -9.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone120 = bone119.addOrReplaceChild("bone120", CubeListBuilder.create().texOffs(100, 18).addBox(0.0F, 4.0F, 0.0F, 13.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, -1.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone121 = base6.addOrReplaceChild("bone121", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone122 = bone121.addOrReplaceChild("bone122", CubeListBuilder.create().texOffs(0, 106).addBox(-6.75F, 0.0F, -7.25F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(92, 114).addBox(-4.5F, -12.75F, -4.25F, 7.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -4.5F, 4.25F, 0.0F, 0.0F, -0.7418F));

		PartDefinition bone124 = base6.addOrReplaceChild("bone124", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition bone125 = bone124.addOrReplaceChild("bone125", CubeListBuilder.create().texOffs(43, 30).addBox(0.0F, -1.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone126 = base6.addOrReplaceChild("bone126", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone127 = bone126.addOrReplaceChild("bone127", CubeListBuilder.create().texOffs(108, 49).addBox(0.0F, -0.25F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition bone128 = base6.addOrReplaceChild("bone128", CubeListBuilder.create().texOffs(67, 18).addBox(-15.25F, -2.425F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition panels = root.addOrReplaceChild("panels", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition bone20 = panels.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offset(0.25F, -9.0F, 0.0F));

		PartDefinition bone21 = bone20.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(123, 129).addBox(-1.2287F, -0.8604F, 7.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(101, 129).addBox(-1.2287F, -0.8604F, -12.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(67, 115).addBox(9.7713F, -0.8854F, -4.5F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -5.5F, 1.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone32 = bone21.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(100, 13).addBox(0.0F, 0.05F, -3.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, 10.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition bone33 = bone21.addOrReplaceChild("bone33", CubeListBuilder.create().texOffs(38, 18).addBox(0.0F, 0.05F, 0.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, -12.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone7 = panels.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone13 = bone7.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(38, 0).addBox(0.0F, 0.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.25F, -0.05F, -8.0F, 13.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone132 = panels.addOrReplaceChild("bone132", CubeListBuilder.create().texOffs(27, 106).addBox(0.5F, -4.75F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-10.875F, -22.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition panels2 = panels.addOrReplaceChild("panels2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone2 = panels2.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.25F, -9.0F, 0.0F));

		PartDefinition bone3 = bone2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(123, 129).addBox(-1.2287F, -0.8604F, 7.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(101, 129).addBox(-1.2287F, -0.8604F, -12.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(67, 115).addBox(9.7713F, -0.8854F, -4.5F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -5.5F, 1.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(100, 13).addBox(0.0F, 0.05F, -3.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, 10.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition bone5 = bone3.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(38, 18).addBox(0.0F, 0.05F, 0.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, -12.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone6 = panels2.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone8 = bone6.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(38, 0).addBox(0.0F, 0.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.25F, -0.05F, -8.0F, 13.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone9 = panels2.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(41, 48).addBox(0.5F, -4.75F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-10.875F, -22.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition panels3 = panels2.addOrReplaceChild("panels3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone10 = panels3.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(0.25F, -9.0F, 0.0F));

		PartDefinition bone11 = bone10.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(123, 129).addBox(-1.2287F, -0.8604F, 7.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(101, 129).addBox(-1.2287F, -0.8604F, -12.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(67, 115).addBox(9.7713F, -0.8854F, -4.5F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -5.5F, 1.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(100, 13).addBox(0.0F, 0.05F, -3.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, 10.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition bone14 = bone11.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(38, 18).addBox(0.0F, 0.05F, 0.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, -12.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone15 = panels3.addOrReplaceChild("bone15", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(38, 0).addBox(0.0F, 0.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.25F, -0.05F, -8.0F, 13.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone17 = panels3.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(27, 106).addBox(0.5F, -4.75F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-10.875F, -22.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition panels4 = panels3.addOrReplaceChild("panels4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone18 = panels4.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offset(0.25F, -9.0F, 0.0F));

		PartDefinition bone22 = bone18.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(123, 129).addBox(-1.2287F, -0.8604F, 7.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(101, 129).addBox(-1.2287F, -0.8604F, -12.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(67, 115).addBox(9.7713F, -0.8854F, -4.5F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -5.5F, 1.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone23 = bone22.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(100, 13).addBox(0.0F, 0.05F, -3.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, 10.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition bone24 = bone22.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(38, 18).addBox(0.0F, 0.05F, 0.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, -12.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone25 = panels4.addOrReplaceChild("bone25", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone26 = bone25.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(38, 0).addBox(0.0F, 0.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.25F, -0.05F, -8.0F, 13.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone27 = panels4.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(41, 48).addBox(0.5F, -4.75F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-10.875F, -22.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition panels5 = panels4.addOrReplaceChild("panels5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone28 = panels5.addOrReplaceChild("bone28", CubeListBuilder.create(), PartPose.offset(0.25F, -9.0F, 0.0F));

		PartDefinition bone29 = bone28.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(123, 129).addBox(-1.2287F, -0.8604F, 7.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(101, 129).addBox(-1.2287F, -0.8604F, -12.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(67, 115).addBox(9.7713F, -0.8854F, -4.5F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -5.5F, 1.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone30 = bone29.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(100, 13).addBox(0.0F, 0.05F, -3.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, 10.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition bone31 = bone29.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(38, 18).addBox(0.0F, 0.05F, 0.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, -12.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone34 = panels5.addOrReplaceChild("bone34", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone35 = bone34.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(38, 0).addBox(0.0F, 0.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.25F, -0.05F, -8.0F, 13.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone36 = panels5.addOrReplaceChild("bone36", CubeListBuilder.create().texOffs(27, 106).addBox(0.5F, -4.75F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-10.875F, -22.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition panels6 = panels5.addOrReplaceChild("panels6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone37 = panels6.addOrReplaceChild("bone37", CubeListBuilder.create(), PartPose.offset(0.25F, -9.0F, 0.0F));

		PartDefinition bone38 = bone37.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(123, 129).addBox(-1.2287F, -0.8604F, 7.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(101, 129).addBox(-1.2287F, -0.8604F, -12.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.025F))
		.texOffs(67, 115).addBox(9.7713F, -0.8854F, -4.5F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -5.5F, 1.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone39 = bone38.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(100, 13).addBox(0.0F, 0.05F, -3.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, 10.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition bone41 = bone38.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(38, 18).addBox(0.0F, 0.05F, 0.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7615F, -0.903F, -12.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone42 = panels6.addOrReplaceChild("bone42", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone43 = bone42.addOrReplaceChild("bone43", CubeListBuilder.create().texOffs(38, 0).addBox(0.0F, 0.0F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.25F, -0.05F, -8.0F, 13.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone44 = panels6.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(41, 48).addBox(0.5F, -4.75F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-10.875F, -22.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition border = root.addOrReplaceChild("border", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition bone = border.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(73, 78).addBox(-21.0F, -5.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone19 = border.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(73, 85).addBox(-21.25F, -6.0F, 8.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 75).addBox(-21.25F, -6.0F, -11.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -8.5F, 0.0F));

		PartDefinition bone63 = border.addOrReplaceChild("bone63", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone62 = bone63.addOrReplaceChild("bone62", CubeListBuilder.create().texOffs(100, 23).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.25F, -14.5F, 0.0F, 0.0F, 0.0F, -0.5672F));

		PartDefinition border2 = border.addOrReplaceChild("border2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone64 = border2.addOrReplaceChild("bone64", CubeListBuilder.create().texOffs(73, 78).addBox(-21.0F, -5.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone65 = border2.addOrReplaceChild("bone65", CubeListBuilder.create().texOffs(73, 85).addBox(-21.25F, -6.0F, 8.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 75).addBox(-21.25F, -6.0F, -11.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -8.5F, 0.0F));

		PartDefinition bone131 = border2.addOrReplaceChild("bone131", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone134 = bone131.addOrReplaceChild("bone134", CubeListBuilder.create().texOffs(100, 23).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.25F, -14.5F, 0.0F, 0.0F, 0.0F, -0.5672F));

		PartDefinition border3 = border2.addOrReplaceChild("border3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone135 = border3.addOrReplaceChild("bone135", CubeListBuilder.create().texOffs(73, 78).addBox(-21.0F, -5.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone136 = border3.addOrReplaceChild("bone136", CubeListBuilder.create().texOffs(73, 85).addBox(-21.25F, -6.0F, 8.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 75).addBox(-21.25F, -6.0F, -11.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -8.5F, 0.0F));

		PartDefinition bone137 = border3.addOrReplaceChild("bone137", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone138 = bone137.addOrReplaceChild("bone138", CubeListBuilder.create().texOffs(100, 23).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.25F, -14.5F, 0.0F, 0.0F, 0.0F, -0.5672F));

		PartDefinition border4 = border3.addOrReplaceChild("border4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone238 = border4.addOrReplaceChild("bone238", CubeListBuilder.create().texOffs(73, 78).addBox(-21.0F, -5.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone239 = border4.addOrReplaceChild("bone239", CubeListBuilder.create().texOffs(73, 85).addBox(-21.25F, -6.0F, 8.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 75).addBox(-21.25F, -6.0F, -11.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -8.5F, 0.0F));

		PartDefinition bone240 = border4.addOrReplaceChild("bone240", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone241 = bone240.addOrReplaceChild("bone241", CubeListBuilder.create().texOffs(100, 23).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.25F, -14.5F, 0.0F, 0.0F, 0.0F, -0.5672F));

		PartDefinition border5 = border4.addOrReplaceChild("border5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone242 = border5.addOrReplaceChild("bone242", CubeListBuilder.create().texOffs(73, 78).addBox(-21.0F, -5.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone243 = border5.addOrReplaceChild("bone243", CubeListBuilder.create().texOffs(73, 85).addBox(-21.25F, -6.0F, 8.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 75).addBox(-21.25F, -6.0F, -11.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -8.5F, 0.0F));

		PartDefinition bone244 = border5.addOrReplaceChild("bone244", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone245 = bone244.addOrReplaceChild("bone245", CubeListBuilder.create().texOffs(100, 23).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.25F, -14.5F, 0.0F, 0.0F, 0.0F, -0.5672F));

		PartDefinition border6 = border5.addOrReplaceChild("border6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone246 = border6.addOrReplaceChild("bone246", CubeListBuilder.create().texOffs(73, 78).addBox(-21.0F, -5.0F, -8.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition bone247 = border6.addOrReplaceChild("bone247", CubeListBuilder.create().texOffs(73, 85).addBox(-21.25F, -6.0F, 8.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(48, 75).addBox(-21.25F, -6.0F, -11.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -8.5F, 0.0F));

		PartDefinition bone248 = border6.addOrReplaceChild("bone248", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone250 = bone248.addOrReplaceChild("bone250", CubeListBuilder.create().texOffs(100, 23).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.25F, -14.5F, 0.0F, 0.0F, 0.0F, -0.5672F));

		PartDefinition rotorcolumn = root.addOrReplaceChild("rotorcolumn", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone151 = rotorcolumn.addOrReplaceChild("bone151", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone152 = bone151.addOrReplaceChild("bone152", CubeListBuilder.create().texOffs(62, 48).addBox(0.25F, -52.5F, -2.0F, 1.0F, 52.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(17, 123).addBox(-0.75F, -19.5F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.75F, -11.5F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition bone153 = bone151.addOrReplaceChild("bone153", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone154 = bone153.addOrReplaceChild("bone154", CubeListBuilder.create().texOffs(62, 48).addBox(0.25F, -52.5F, -2.0F, 1.0F, 52.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(38, 0).addBox(0.15F, -14.5F, -2.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(142, 8).addBox(-0.35F, -8.0F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(141, 141).addBox(-0.6F, -8.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.75F, -11.5F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition bone139 = rotorcolumn.addOrReplaceChild("bone139", CubeListBuilder.create().texOffs(73, 64).addBox(-10.4F, -13.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

		PartDefinition bone140 = bone139.addOrReplaceChild("bone140", CubeListBuilder.create().texOffs(73, 64).addBox(-10.4F, -13.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone141 = bone140.addOrReplaceChild("bone141", CubeListBuilder.create().texOffs(73, 64).addBox(-10.4F, -13.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone142 = bone141.addOrReplaceChild("bone142", CubeListBuilder.create().texOffs(73, 64).addBox(-10.4F, -13.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone143 = bone142.addOrReplaceChild("bone143", CubeListBuilder.create().texOffs(73, 64).addBox(-10.4F, -13.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone144 = bone143.addOrReplaceChild("bone144", CubeListBuilder.create().texOffs(73, 64).addBox(-10.4F, -13.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bands = rotorcolumn.addOrReplaceChild("bands", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone169 = bands.addOrReplaceChild("bone169", CubeListBuilder.create().texOffs(0, 0).addBox(-8.65F, -15.0F, -5.0F, 1.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -48.0F, 0.0F, 0.0F, -0.5672F, 0.0F));

		PartDefinition bone170 = bone169.addOrReplaceChild("bone170", CubeListBuilder.create().texOffs(0, 0).addBox(-8.65F, -15.0F, -5.0F, 1.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone171 = bone170.addOrReplaceChild("bone171", CubeListBuilder.create().texOffs(0, 0).addBox(-8.65F, -15.0F, -5.0F, 1.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone172 = bone171.addOrReplaceChild("bone172", CubeListBuilder.create().texOffs(0, 0).addBox(-8.65F, -15.0F, -5.0F, 1.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone173 = bone172.addOrReplaceChild("bone173", CubeListBuilder.create().texOffs(0, 0).addBox(-8.65F, -15.0F, -5.0F, 1.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone174 = bone173.addOrReplaceChild("bone174", CubeListBuilder.create().texOffs(0, 0).addBox(-8.65F, -15.0F, -5.0F, 1.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone129 = bands.addOrReplaceChild("bone129", CubeListBuilder.create().texOffs(17, 46).addBox(-7.8F, -15.0F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, 0.0F, 0.0F, -0.5672F, 0.0F));

		PartDefinition bone130 = bone129.addOrReplaceChild("bone130", CubeListBuilder.create().texOffs(17, 46).addBox(-7.8F, -15.0F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone163 = bone130.addOrReplaceChild("bone163", CubeListBuilder.create().texOffs(17, 46).addBox(-7.8F, -15.0F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone164 = bone163.addOrReplaceChild("bone164", CubeListBuilder.create().texOffs(17, 46).addBox(-7.8F, -15.0F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone165 = bone164.addOrReplaceChild("bone165", CubeListBuilder.create().texOffs(17, 46).addBox(-7.8F, -15.0F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone166 = bone165.addOrReplaceChild("bone166", CubeListBuilder.create().texOffs(17, 46).addBox(-7.8F, -15.0F, -4.5F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone312 = bands.addOrReplaceChild("bone312", CubeListBuilder.create().texOffs(93, 98).addBox(-9.525F, -15.0F, -5.5F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(94, 78).addBox(-11.525F, -13.0F, -5.5F, 3.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 0.0F, 0.0F, 0.5672F, 0.0F));

		PartDefinition bone314 = bone312.addOrReplaceChild("bone314", CubeListBuilder.create().texOffs(93, 98).addBox(-9.525F, -15.0F, -5.5F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-16.525F, -13.0F, -11.5F, 7.0F, 4.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(84, 13).addBox(-16.525F, -7.0F, -6.5F, 1.0F, 7.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(0, 116).addBox(-15.525F, -3.0F, -3.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition monitor_control = bone314.addOrReplaceChild("monitor_control", CubeListBuilder.create().texOffs(39, 95).addBox(-17.525F, -37.0F, -5.5F, 2.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 32.0F, 0.0F));

		PartDefinition bone319 = bone314.addOrReplaceChild("bone319", CubeListBuilder.create().texOffs(111, 107).addBox(-0.5F, -3.5F, -3.5F, 1.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.775F, -8.5F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition bone315 = bone314.addOrReplaceChild("bone315", CubeListBuilder.create().texOffs(93, 98).addBox(-9.525F, -15.0F, -5.5F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(94, 78).addBox(-11.525F, -13.0F, -5.5F, 3.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone316 = bone315.addOrReplaceChild("bone316", CubeListBuilder.create().texOffs(93, 98).addBox(-9.525F, -15.0F, -5.5F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(94, 78).addBox(-11.525F, -13.0F, -5.5F, 3.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone317 = bone316.addOrReplaceChild("bone317", CubeListBuilder.create().texOffs(93, 98).addBox(-9.525F, -15.0F, -5.5F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-16.525F, -13.0F, -11.5F, 7.0F, 4.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(17, 46).addBox(-15.525F, -13.0F, -10.5F, 1.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(82, 98).addBox(-14.525F, -15.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition pulley_control5 = bone317.addOrReplaceChild("pulley_control5", CubeListBuilder.create().texOffs(12, 132).addBox(-14.525F, -30.0F, 1.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 139).addBox(-14.275F, -30.75F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(49, 0).addBox(-14.025F, -42.0F, 1.5F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 32.0F, 0.0F));

		PartDefinition bone318 = bone317.addOrReplaceChild("bone318", CubeListBuilder.create().texOffs(93, 98).addBox(-9.525F, -15.0F, -5.5F, 1.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(94, 78).addBox(-11.525F, -13.0F, -5.5F, 3.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone145 = bands.addOrReplaceChild("bone145", CubeListBuilder.create().texOffs(110, 56).addBox(-6.925F, -15.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0F, -0.5672F, 0.0F));

		PartDefinition bone146 = bone145.addOrReplaceChild("bone146", CubeListBuilder.create().texOffs(110, 56).addBox(-6.925F, -15.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone147 = bone146.addOrReplaceChild("bone147", CubeListBuilder.create().texOffs(110, 56).addBox(-6.925F, -15.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone148 = bone147.addOrReplaceChild("bone148", CubeListBuilder.create().texOffs(110, 56).addBox(-6.925F, -15.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone149 = bone148.addOrReplaceChild("bone149", CubeListBuilder.create().texOffs(110, 56).addBox(-6.925F, -15.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone150 = bone149.addOrReplaceChild("bone150", CubeListBuilder.create().texOffs(110, 56).addBox(-6.925F, -15.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone175 = rotorcolumn.addOrReplaceChild("bone175", CubeListBuilder.create().texOffs(73, 48).addBox(-10.65F, -15.0F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -62.0F, 0.0F));

		PartDefinition bone176 = bone175.addOrReplaceChild("bone176", CubeListBuilder.create().texOffs(73, 48).addBox(-10.65F, -15.0F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone177 = bone176.addOrReplaceChild("bone177", CubeListBuilder.create().texOffs(73, 48).addBox(-10.65F, -15.0F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone178 = bone177.addOrReplaceChild("bone178", CubeListBuilder.create().texOffs(73, 48).addBox(-10.65F, -15.0F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone179 = bone178.addOrReplaceChild("bone179", CubeListBuilder.create().texOffs(73, 48).addBox(-10.65F, -15.0F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone180 = bone179.addOrReplaceChild("bone180", CubeListBuilder.create().texOffs(73, 48).addBox(-10.65F, -15.0F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone181 = root.addOrReplaceChild("bone181", CubeListBuilder.create().texOffs(41, 48).addBox(-11.725F, -15.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -62.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone182 = bone181.addOrReplaceChild("bone182", CubeListBuilder.create().texOffs(41, 48).addBox(-11.725F, -15.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone183 = bone182.addOrReplaceChild("bone183", CubeListBuilder.create().texOffs(41, 48).addBox(-11.725F, -15.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone184 = bone183.addOrReplaceChild("bone184", CubeListBuilder.create().texOffs(41, 48).addBox(-11.725F, -15.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone185 = bone184.addOrReplaceChild("bone185", CubeListBuilder.create().texOffs(41, 48).addBox(-11.725F, -15.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone186 = bone185.addOrReplaceChild("bone186", CubeListBuilder.create().texOffs(41, 48).addBox(-11.725F, -15.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bottombase = root.addOrReplaceChild("bottombase", CubeListBuilder.create().texOffs(79, 0).addBox(-9.0F, -13.0F, -5.5F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

		PartDefinition bone188 = bottombase.addOrReplaceChild("bone188", CubeListBuilder.create().texOffs(79, 0).addBox(-9.0F, -13.0F, -5.5F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone189 = bone188.addOrReplaceChild("bone189", CubeListBuilder.create().texOffs(79, 0).addBox(-9.0F, -13.0F, -5.5F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone190 = bone189.addOrReplaceChild("bone190", CubeListBuilder.create().texOffs(79, 0).addBox(-9.0F, -13.0F, -5.5F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone191 = bone190.addOrReplaceChild("bone191", CubeListBuilder.create().texOffs(79, 0).addBox(-9.0F, -13.0F, -5.5F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone192 = bone191.addOrReplaceChild("bone192", CubeListBuilder.create().texOffs(79, 0).addBox(-9.0F, -13.0F, -5.5F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition rotor = partdefinition.addOrReplaceChild("rotor", CubeListBuilder.create().texOffs(17, 75).addBox(-5.0F, -69.0F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(17, 75).addBox(-5.0F, -31.5F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(17, 91).addBox(-4.0F, -43.5F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(17, 91).addBox(-4.0F, -58.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(109, 122).addBox(-2.0F, -49.5F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F))
		.texOffs(76, 126).addBox(-2.0F, -61.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(1.0F))
		.texOffs(76, 126).addBox(-2.0F, -35.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition misc = partdefinition.addOrReplaceChild("misc", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, -2.618F, 0.0F));

		PartDefinition bone198 = misc.addOrReplaceChild("bone198", CubeListBuilder.create().texOffs(129, 81).addBox(3.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(124, 141).addBox(7.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(129, 76).addBox(3.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition pulley_control10 = bone198.addOrReplaceChild("pulley_control10", CubeListBuilder.create().texOffs(94, 86).addBox(-12.0F, -15.25F, -2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(7, 140).addBox(-13.0F, -15.75F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(14.5F, 13.0F, 2.0F));

		PartDefinition misc2 = partdefinition.addOrReplaceChild("misc2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bone226 = misc2.addOrReplaceChild("bone226", CubeListBuilder.create().texOffs(129, 81).addBox(5.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(124, 141).addBox(9.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(129, 76).addBox(5.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition pulley_control9 = bone226.addOrReplaceChild("pulley_control9", CubeListBuilder.create().texOffs(94, 86).addBox(-12.0F, -15.25F, -2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(7, 140).addBox(-13.0F, -15.75F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(16.5F, 13.0F, 2.0F));

		PartDefinition misc3 = partdefinition.addOrReplaceChild("misc3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone256 = misc3.addOrReplaceChild("bone256", CubeListBuilder.create().texOffs(129, 81).addBox(6.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(124, 141).addBox(10.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(129, 76).addBox(6.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition pulley_control6 = bone256.addOrReplaceChild("pulley_control6", CubeListBuilder.create().texOffs(94, 86).addBox(-12.0F, -15.25F, -2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(7, 140).addBox(-13.0F, -15.75F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(17.5F, 13.0F, 2.0F));

		PartDefinition misc4 = partdefinition.addOrReplaceChild("misc4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone277 = misc4.addOrReplaceChild("bone277", CubeListBuilder.create().texOffs(129, 81).addBox(5.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(124, 141).addBox(9.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(129, 76).addBox(5.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition pulley_control7 = bone277.addOrReplaceChild("pulley_control7", CubeListBuilder.create().texOffs(94, 86).addBox(-12.0F, -15.25F, -2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(7, 140).addBox(-13.0F, -15.75F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(16.5F, 13.0F, 2.0F));

		PartDefinition misc5 = partdefinition.addOrReplaceChild("misc5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition bone296 = misc5.addOrReplaceChild("bone296", CubeListBuilder.create().texOffs(129, 81).addBox(5.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(124, 141).addBox(9.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(129, 76).addBox(5.5F, -3.25F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition pulley_control8 = bone296.addOrReplaceChild("pulley_control8", CubeListBuilder.create().texOffs(94, 86).addBox(-12.0F, -15.25F, -2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(7, 140).addBox(-13.0F, -15.75F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(16.5F, 13.0F, 2.0F));

		PartDefinition north_left = partdefinition.addOrReplaceChild("north_left", CubeListBuilder.create().texOffs(40, 138).addBox(-21.55F, -4.5F, 8.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(66, 98).addBox(-16.55F, -1.5F, -5.5F, 2.0F, 5.0F, 11.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

		PartDefinition bone46 = north_left.addOrReplaceChild("bone46", CubeListBuilder.create().texOffs(125, 0).addBox(5.0F, -0.75F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(73, 78).addBox(10.9069F, -0.8824F, -2.4743F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(141, 99).addBox(11.4069F, -1.3824F, -1.4743F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition ball_rotate_control = bone46.addOrReplaceChild("ball_rotate_control", CubeListBuilder.create().texOffs(24, 134).addBox(-15.0F, -16.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(21.0F, 15.0F, 0.0F));

		PartDefinition bone48 = ball_rotate_control.addOrReplaceChild("bone48", CubeListBuilder.create(), PartPose.offsetAndRotation(-14.0F, -17.25F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone133 = bone48.addOrReplaceChild("bone133", CubeListBuilder.create().texOffs(0, 138).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition bone47 = ball_rotate_control.addOrReplaceChild("bone47", CubeListBuilder.create().texOffs(0, 138).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.975F, -17.25F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition bone155 = ball_rotate_control.addOrReplaceChild("bone155", CubeListBuilder.create().texOffs(124, 118).addBox(-1.75F, -0.5F, -2.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -15.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone157 = bone46.addOrReplaceChild("bone157", CubeListBuilder.create().texOffs(110, 136).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.75F, 2.5F, 0.0F, 0.3491F, 0.0F));

		PartDefinition button_control = bone157.addOrReplaceChild("button_control", CubeListBuilder.create().texOffs(122, 37).addBox(-18.5F, -17.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(18.0F, 15.75F, -2.5F));

		PartDefinition bone156 = bone46.addOrReplaceChild("bone156", CubeListBuilder.create().texOffs(133, 36).addBox(-1.0F, -1.375F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(33, 136).addBox(-1.0F, -0.125F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(3.0F, -0.375F, -3.5F, 0.0F, -0.3491F, 0.0F));

		PartDefinition bone167 = bone46.addOrReplaceChild("bone167", CubeListBuilder.create().texOffs(121, 107).addBox(0.0F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -1.5F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition bone161 = bone46.addOrReplaceChild("bone161", CubeListBuilder.create().texOffs(129, 65).addBox(3.055F, -1.3926F, -2.7243F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(135, 137).addBox(5.305F, -1.3926F, -2.7243F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(7, 136).addBox(1.805F, -0.6426F, -2.4743F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(123, 26).addBox(7.305F, -0.1926F, -3.4743F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(136, 70).addBox(12.055F, -2.1926F, -1.4743F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(140, 4).addBox(9.305F, -0.4426F, -2.6993F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(137, 129).addBox(9.555F, -2.1926F, -2.6993F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, 11.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition main_lever_control = bone161.addOrReplaceChild("main_lever_control", CubeListBuilder.create().texOffs(82, 98).addBox(-0.25F, -2.75F, -1.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(83, 78).addBox(-0.25F, -2.75F, 0.1987F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.305F, -0.8926F, -1.7243F, 0.0F, 0.0F, -0.2182F));

		PartDefinition bone187 = bone46.addOrReplaceChild("bone187", CubeListBuilder.create().texOffs(41, 63).addBox(3.055F, -0.6426F, 0.4743F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(79, 13).addBox(3.055F, -0.6676F, 1.0993F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(126, 124).addBox(7.805F, -0.1926F, 0.2757F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(124, 95).addBox(11.555F, -1.9426F, 1.2757F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(94, 91).addBox(8.805F, -0.6926F, 0.6257F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(53, 48).addBox(3.055F, -0.9926F, 0.5243F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, -11.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone197 = bone187.addOrReplaceChild("bone197", CubeListBuilder.create().texOffs(10, 116).addBox(-0.25F, -1.75F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.305F, -0.4426F, 1.1257F, 0.0F, 0.0F, 0.5672F));

		PartDefinition bone196 = bone187.addOrReplaceChild("bone196", CubeListBuilder.create().texOffs(141, 91).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.805F, -0.1426F, 1.4743F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone195 = bone196.addOrReplaceChild("bone195", CubeListBuilder.create().texOffs(141, 80).addBox(0.0F, -0.75F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.6545F));

		PartDefinition bone193 = bone187.addOrReplaceChild("bone193", CubeListBuilder.create().texOffs(83, 85).addBox(-0.25F, 0.0F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.805F, -1.0926F, 1.7493F, 0.0F, -0.9599F, 0.0F));

		PartDefinition bone194 = bone187.addOrReplaceChild("bone194", CubeListBuilder.create().texOffs(83, 85).addBox(-0.25F, 0.0F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.305F, -1.0926F, 1.7493F, 0.0F, -0.9599F, 0.0F));

		PartDefinition bone45 = north_left.addOrReplaceChild("bone45", CubeListBuilder.create().texOffs(111, 0).addBox(-0.75F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -2.5F, 6.0F, 0.0F, 0.2618F, 0.2182F));

		PartDefinition pulley_control = bone45.addOrReplaceChild("pulley_control", CubeListBuilder.create().texOffs(51, 28).addBox(-13.0F, -15.25F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(31, 106).addBox(-14.0F, -15.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(11.75F, 14.25F, -0.5F));

		PartDefinition bone159 = north_left.addOrReplaceChild("bone159", CubeListBuilder.create().texOffs(111, 0).addBox(-0.75F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -2.5F, -6.0F, 0.0F, -0.2618F, 0.2182F));

		PartDefinition pulley_control2 = bone159.addOrReplaceChild("pulley_control2", CubeListBuilder.create().texOffs(51, 28).addBox(-13.0F, -15.25F, -1.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(31, 106).addBox(-14.0F, -15.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(11.75F, 14.25F, 0.5F));

		PartDefinition valve_control8 = north_left.addOrReplaceChild("valve_control8", CubeListBuilder.create().texOffs(128, 137).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.75F, -3.5F, -9.5F, -0.2618F, 0.0F, 0.0F));

		PartDefinition bone223 = north_left.addOrReplaceChild("bone223", CubeListBuilder.create().texOffs(137, 114).addBox(-0.5F, -3.2F, 2.2F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(117, 139).addBox(-0.5F, -2.45F, -3.8F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-0.25F, -4.2F, -3.05F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(73, 55).addBox(-2.5F, -3.95F, -0.55F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(137, 86).addBox(-0.1F, -4.45F, -1.05F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(123, 31).addBox(-2.0F, -1.95F, -4.55F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(27, 112).addBox(-2.0F, -1.95F, -2.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.875F, -13.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition bone225 = bone223.addOrReplaceChild("bone225", CubeListBuilder.create().texOffs(102, 139).addBox(-1.5F, -0.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -3.7F, 0.45F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone224 = bone223.addOrReplaceChild("bone224", CubeListBuilder.create().texOffs(81, 137).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.15F, -3.45F, 3.2F, -0.7854F, 0.0F, 0.0F));

		PartDefinition north_right = partdefinition.addOrReplaceChild("north_right", CubeListBuilder.create().texOffs(73, 48).addBox(-22.525F, -5.0F, -11.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(141, 64).addBox(-21.8F, -4.5F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone292 = north_right.addOrReplaceChild("bone292", CubeListBuilder.create().texOffs(111, 0).addBox(-3.75F, -4.5F, -3.5F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.75F, -2.85F, 0.0F, 0.0F, 0.0F, -1.0036F));

		PartDefinition bone295 = bone292.addOrReplaceChild("bone295", CubeListBuilder.create().texOffs(48, 115).addBox(0.0F, -1.0F, -2.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.75F, -4.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition bone203 = north_right.addOrReplaceChild("bone203", CubeListBuilder.create().texOffs(141, 45).addBox(4.25F, -0.6F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(41, 134).addBox(2.25F, -0.6F, 2.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(121, 135).addBox(2.25F, -0.6F, -4.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F))
		.texOffs(131, 132).addBox(2.0F, -0.1F, -5.25F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(109, 140).addBox(8.25F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(140, 123).addBox(11.3404F, -1.3868F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(109, 140).addBox(7.0F, -0.6F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(109, 140).addBox(7.0F, -0.6F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition button_control2 = bone203.addOrReplaceChild("button_control2", CubeListBuilder.create().texOffs(140, 134).addBox(-18.25F, -15.85F, -4.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(21.0F, 15.0F, 0.0F));

		PartDefinition valve_control = bone203.addOrReplaceChild("valve_control", CubeListBuilder.create().texOffs(133, 16).addBox(-0.75F, -0.5F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.25F, -0.2F, 3.25F, 0.0F, -0.5236F, 0.0F));

		PartDefinition valve_control2 = bone203.addOrReplaceChild("valve_control2", CubeListBuilder.create().texOffs(62, 139).addBox(0.0F, -1.0F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.8404F, -1.3868F, 0.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone202 = bone203.addOrReplaceChild("bone202", CubeListBuilder.create().texOffs(10, 116).addBox(-2.5F, 0.15F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -0.25F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone211 = bone203.addOrReplaceChild("bone211", CubeListBuilder.create().texOffs(103, 91).addBox(7.055F, -0.6426F, -1.9743F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(17, 96).addBox(1.055F, -1.1426F, -1.9743F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(95, 139).addBox(1.555F, -1.1426F, -2.4743F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(135, 108).addBox(5.555F, -0.1426F, -2.4743F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, 11.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition valve_control3 = bone211.addOrReplaceChild("valve_control3", CubeListBuilder.create().texOffs(88, 139).addBox(0.0F, -0.5F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.055F, -1.8926F, -1.4743F, 0.0F, -0.7418F, 0.0F));

		PartDefinition bone216 = bone211.addOrReplaceChild("bone216", CubeListBuilder.create().texOffs(83, 132).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 133).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.055F, -1.1426F, -1.7243F, 0.0F, -0.3054F, 0.0F));

		PartDefinition lever_control = bone216.addOrReplaceChild("lever_control", CubeListBuilder.create().texOffs(140, 74).addBox(-0.625F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 131).addBox(-0.375F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, -1.5F, -0.2182F, 0.0F, 0.0F));

		PartDefinition bone219 = bone211.addOrReplaceChild("bone219", CubeListBuilder.create().texOffs(73, 78).addBox(0.0F, -2.75F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(139, 55).addBox(-0.5F, -2.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.055F, -0.1426F, -2.9743F, 0.0F, -0.7418F, 0.0F));

		PartDefinition bone212 = bone211.addOrReplaceChild("bone212", CubeListBuilder.create().texOffs(140, 58).addBox(0.0F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.055F, -0.6426F, -1.4743F, 0.0F, 0.0F, 0.6109F));

		PartDefinition bone213 = bone203.addOrReplaceChild("bone213", CubeListBuilder.create().texOffs(129, 60).addBox(3.055F, -1.8926F, 0.6993F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(90, 129).addBox(4.055F, -2.3926F, 0.1993F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 38).addBox(2.555F, -2.4176F, 0.6993F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, -11.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition main_lever_control2 = bone213.addOrReplaceChild("main_lever_control2", CubeListBuilder.create().texOffs(79, 0).addBox(-0.5F, -3.5F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.055F, -1.3926F, 1.6993F, 0.0F, 0.0F, 1.0472F));

		PartDefinition bone208 = bone203.addOrReplaceChild("bone208", CubeListBuilder.create().texOffs(140, 35).addBox(0.0F, -0.75F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -0.2F, -1.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone209 = bone203.addOrReplaceChild("bone209", CubeListBuilder.create().texOffs(140, 35).addBox(0.0F, -1.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -0.2F, 2.0F, 0.0F, -0.6545F, 0.0F));

		PartDefinition bone204 = bone203.addOrReplaceChild("bone204", CubeListBuilder.create().texOffs(34, 140).addBox(0.0F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.75F, -0.6F, 1.0F, 0.0F, 0.0F, 0.6545F));

		PartDefinition bone205 = bone203.addOrReplaceChild("bone205", CubeListBuilder.create().texOffs(14, 140).addBox(0.0F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.75F, -0.6F, 0.25F, 0.0F, 0.0F, 0.6545F));

		PartDefinition bone206 = bone203.addOrReplaceChild("bone206", CubeListBuilder.create().texOffs(14, 140).addBox(0.0F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -0.6F, 1.75F, 0.0F, 0.0F, 0.6545F));

		PartDefinition bone207 = bone203.addOrReplaceChild("bone207", CubeListBuilder.create().texOffs(14, 140).addBox(0.0F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -0.6F, -1.25F, 0.0F, 0.0F, 0.6545F));

		PartDefinition twist_control = north_right.addOrReplaceChild("twist_control", CubeListBuilder.create().texOffs(55, 139).addBox(-0.5F, -0.75F, -1.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.05F, -4.0F, 9.5F, -0.48F, 0.0F, 0.0F));

		PartDefinition bone221 = north_right.addOrReplaceChild("bone221", CubeListBuilder.create().texOffs(140, 11).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(115, 70).addBox(-0.5F, -2.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(140, 11).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.9335F, -13.0456F, 3.5257F, 0.0F, 0.48F, 0.0F));

		PartDefinition bone220 = bone221.addOrReplaceChild("bone220", CubeListBuilder.create().texOffs(69, 139).addBox(-0.75F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(139, 55).addBox(-1.75F, -1.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -1.25F, -0.5F, 0.0F, -0.5672F, 0.0F));

		PartDefinition bone222 = north_right.addOrReplaceChild("bone222", CubeListBuilder.create().texOffs(94, 78).addBox(-0.1F, -3.15F, -4.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.875F, -13.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition east = partdefinition.addOrReplaceChild("east", CubeListBuilder.create().texOffs(29, 46).addBox(-16.0F, -13.1F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(13, 0).addBox(-16.0F, -13.6F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.5F))
		.texOffs(139, 41).addBox(-16.0F, -12.6F, -2.25F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 99).addBox(-16.5F, -13.1F, -2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 116).addBox(-22.55F, -4.5F, -10.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(66, 98).addBox(-13.55F, -1.5F, -5.5F, 2.0F, 5.0F, 11.0F, new CubeDeformation(1.5F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition bone228 = east.addOrReplaceChild("bone228", CubeListBuilder.create().texOffs(124, 89).addBox(5.5F, -0.35F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(134, 46).addBox(5.0F, -0.6F, -4.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 115).addBox(5.5F, -1.6F, -4.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(135, 104).addBox(2.5F, -0.6F, 3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(135, 104).addBox(1.5F, -0.6F, -5.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(40, 127).addBox(2.5F, -0.1F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(81, 115).addBox(3.0F, -0.2F, -1.75F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition twist_control2 = bone228.addOrReplaceChild("twist_control2", CubeListBuilder.create().texOffs(38, 121).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -0.85F, 4.0F, 0.0F, -2.2253F, 0.0F));

		PartDefinition button_control3 = bone228.addOrReplaceChild("button_control3", CubeListBuilder.create().texOffs(29, 139).addBox(-19.0F, -16.1F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(21.0F, 15.0F, 0.0F));

		PartDefinition valve_control5 = bone228.addOrReplaceChild("valve_control5", CubeListBuilder.create().texOffs(101, 135).addBox(-0.75F, -0.475F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -0.85F, -3.75F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone230 = bone228.addOrReplaceChild("bone230", CubeListBuilder.create().texOffs(135, 95).addBox(1.055F, -0.6426F, -2.4743F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(135, 95).addBox(9.055F, -0.6426F, -2.4743F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(138, 31).addBox(1.555F, -1.1426F, -1.9743F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, 11.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition bone255 = bone230.addOrReplaceChild("bone255", CubeListBuilder.create(), PartPose.offsetAndRotation(5.555F, -0.1426F, -1.4743F, 0.0F, -0.3927F, 0.0F));

		PartDefinition bone254 = bone255.addOrReplaceChild("bone254", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone253 = bone230.addOrReplaceChild("bone253", CubeListBuilder.create().texOffs(110, 95).addBox(0.0F, -1.5F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.055F, -1.1426F, -1.4743F, 0.0F, 0.0F, 0.7418F));

		PartDefinition bone261 = bone230.addOrReplaceChild("bone261", CubeListBuilder.create().texOffs(66, 105).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.555F, -0.1426F, -1.4743F, 0.0F, -0.3927F, 0.0F));

		PartDefinition lever_control3 = bone261.addOrReplaceChild("lever_control3", CubeListBuilder.create().texOffs(73, 73).addBox(0.0F, -1.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition bone263 = bone230.addOrReplaceChild("bone263", CubeListBuilder.create().texOffs(138, 20).addBox(-1.0F, -4.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(137, 119).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.055F, -1.6426F, -1.4743F, 0.0F, -0.6981F, 0.0F));

		PartDefinition bone264 = bone263.addOrReplaceChild("bone264", CubeListBuilder.create().texOffs(92, 135).addBox(-3.0F, -2.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.25F, 0.0F, 0.0F, 0.8727F));

		PartDefinition bone236 = bone228.addOrReplaceChild("bone236", CubeListBuilder.create().texOffs(42, 91).addBox(0.055F, -0.6426F, 0.4743F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(139, 23).addBox(2.805F, 0.0824F, 0.2243F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, -11.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone235 = bone236.addOrReplaceChild("bone235", CubeListBuilder.create().texOffs(134, 55).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(7.9885F, -0.197F, 1.75F, 0.0F, 0.1745F, 0.0F));

		PartDefinition lever_control2 = bone235.addOrReplaceChild("lever_control2", CubeListBuilder.create().texOffs(13, 46).addBox(-3.75F, -1.75F, 0.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -0.5F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition bone249 = east.addOrReplaceChild("bone249", CubeListBuilder.create().texOffs(48, 137).addBox(-0.5F, -2.15F, -3.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(74, 137).addBox(-0.5F, -4.65F, -1.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(131, 129).addBox(-0.5F, -3.9F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 137).addBox(-0.5F, -2.15F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 137).addBox(-0.5F, -2.15F, 1.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(110, 91).addBox(-0.5F, -4.65F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(90, 13).addBox(-2.0F, -4.15F, 2.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.875F, -13.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition bone260 = bone249.addOrReplaceChild("bone260", CubeListBuilder.create().texOffs(24, 129).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -3.15F, -0.25F, 0.0F, 0.5236F, -0.5236F));

		PartDefinition bone259 = bone249.addOrReplaceChild("bone259", CubeListBuilder.create().texOffs(138, 0).addBox(-0.75F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -4.15F, -0.5F, 0.0F, 0.0F, 0.5672F));

		PartDefinition bone237 = bone249.addOrReplaceChild("bone237", CubeListBuilder.create().texOffs(71, 130).addBox(0.0F, -0.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.525F, -1.15F, 2.5F, -2.1817F, 0.0F, 0.0F));

		PartDefinition bone251 = bone249.addOrReplaceChild("bone251", CubeListBuilder.create().texOffs(71, 130).addBox(0.0F, -0.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.525F, -1.15F, 0.0F, 0.6545F, 0.0F, 0.0F));

		PartDefinition bone252 = bone249.addOrReplaceChild("bone252", CubeListBuilder.create().texOffs(71, 130).addBox(0.0F, -0.75F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.525F, -1.15F, -2.5F, 0.6545F, 0.0F, 0.0F));

		PartDefinition bone229 = east.addOrReplaceChild("bone229", CubeListBuilder.create().texOffs(17, 61).addBox(-2.0F, -0.5F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.25F, -10.6F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition twist_control3 = east.addOrReplaceChild("twist_control3", CubeListBuilder.create().texOffs(98, 129).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 31).addBox(-1.0F, 0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.3F, -4.0F, 9.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition bone278 = twist_control3.addOrReplaceChild("bone278", CubeListBuilder.create().texOffs(108, 52).addBox(-0.25F, -1.75F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition south_right = partdefinition.addOrReplaceChild("south_right", CubeListBuilder.create().texOffs(84, 34).addBox(-18.55F, -1.5F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition spinthing_control = south_right.addOrReplaceChild("spinthing_control", CubeListBuilder.create().texOffs(88, 18).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -10.6F, 0.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition spinthingP2_control = spinthing_control.addOrReplaceChild("spinthingP2_control", CubeListBuilder.create().texOffs(74, 132).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(129, 86).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(49, 127).addBox(-0.5F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 123).addBox(0.0F, -1.75F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.75F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition bone265 = south_right.addOrReplaceChild("bone265", CubeListBuilder.create().texOffs(63, 124).addBox(5.5F, -0.35F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(108, 70).addBox(11.0F, -0.9F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(67, 135).addBox(3.5F, -0.85F, 3.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(58, 135).addBox(2.5F, -0.35F, 2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition valve_control4 = bone265.addOrReplaceChild("valve_control4", CubeListBuilder.create().texOffs(0, 108).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(129, 6).addBox(-1.5F, -0.25F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(53, 127).addBox(-1.25F, -1.0F, -1.25F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.6F, -3.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition bone267 = bone265.addOrReplaceChild("bone267", CubeListBuilder.create().texOffs(0, 128).addBox(2.055F, -1.6426F, -2.4743F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, 11.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition lever_control5 = bone267.addOrReplaceChild("lever_control5", CubeListBuilder.create().texOffs(52, 40).addBox(-15.8431F, -18.8324F, 7.0257F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(19.3981F, 15.6898F, -11.0F));

		PartDefinition lever_control6 = bone267.addOrReplaceChild("lever_control6", CubeListBuilder.create().texOffs(16, 16).addBox(0.25F, -2.25F, -1.3F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(4.055F, -1.8926F, -0.9243F, 0.0F, 0.0F, 0.9599F));

		PartDefinition bone269 = bone267.addOrReplaceChild("bone269", CubeListBuilder.create().texOffs(51, 132).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(53, 52).addBox(-0.25F, -0.025F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.055F, -0.1426F, -1.4743F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone275 = bone265.addOrReplaceChild("bone275", CubeListBuilder.create().texOffs(112, 131).addBox(3.055F, -1.6426F, 0.4743F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(61, 26).addBox(6.055F, -0.2426F, 0.9743F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(121, 60).addBox(2.805F, -2.8926F, 1.4743F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, -11.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition lever_control4 = bone275.addOrReplaceChild("lever_control4", CubeListBuilder.create().texOffs(134, 51).addBox(-3.0F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(87, 123).addBox(-3.5F, 0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.555F, -1.6426F, 1.4743F, 0.0F, 0.0F, 0.6545F));

		PartDefinition bone280 = bone265.addOrReplaceChild("bone280", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5F, -0.85F, 4.0F, 0.0F, -2.2253F, 0.0F));

		PartDefinition bone281 = south_right.addOrReplaceChild("bone281", CubeListBuilder.create().texOffs(122, 37).addBox(-1.5F, -2.15F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.875F, -13.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition bone270 = bone281.addOrReplaceChild("bone270", CubeListBuilder.create().texOffs(63, 130).addBox(-2.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.9F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition bone287 = south_right.addOrReplaceChild("bone287", CubeListBuilder.create().texOffs(0, 116).addBox(-0.25F, -0.5F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(42, 95).addBox(-0.25F, -1.25F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-22.3F, -4.0F, -9.5F));

		PartDefinition bone290 = bone287.addOrReplaceChild("bone290", CubeListBuilder.create().texOffs(0, 104).addBox(0.0F, -1.25F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, 0.0F, 0.0F, 0.0F, 0.6545F));

		PartDefinition south_left = partdefinition.addOrReplaceChild("south_left", CubeListBuilder.create().texOffs(66, 98).addBox(-17.55F, -1.5F, -5.5F, 2.0F, 5.0F, 11.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

		PartDefinition bone271 = south_left.addOrReplaceChild("bone271", CubeListBuilder.create().texOffs(107, 91).addBox(1.5F, -1.85F, -4.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(121, 52).addBox(7.0F, -0.85F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(25, 121).addBox(6.75F, -0.95F, -2.75F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(134, 46).addBox(2.0F, -0.6F, 4.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(109, 27).addBox(3.0F, -2.85F, -3.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(108, 37).addBox(4.5F, -3.35F, -4.5F, 2.0F, 2.0F, 9.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition bone272 = bone271.addOrReplaceChild("bone272", CubeListBuilder.create().texOffs(111, 0).addBox(-3.0F, 0.0F, -3.5F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.35F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition bone233 = bone271.addOrReplaceChild("bone233", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -3.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -3.1F, 0.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition bone273 = bone271.addOrReplaceChild("bone273", CubeListBuilder.create().texOffs(41, 134).addBox(1.6481F, -0.9103F, -2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(41, 134).addBox(4.6481F, -0.9103F, -2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, 11.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition bone298 = bone273.addOrReplaceChild("bone298", CubeListBuilder.create().texOffs(130, 31).addBox(-1.0F, -0.5F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(130, 20).addBox(-0.75F, -0.6F, -1.75F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.055F, -0.1426F, -1.4743F, 0.0F, -0.3491F, 0.0F));

		PartDefinition bone299 = bone298.addOrReplaceChild("bone299", CubeListBuilder.create().texOffs(123, 70).addBox(-0.75F, 0.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -0.25F, -0.75F, 0.0F, 0.0F, -0.1745F));

		PartDefinition valve_control7 = bone273.addOrReplaceChild("valve_control7", CubeListBuilder.create().texOffs(133, 16).addBox(-0.75F, -1.5F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6481F, 0.4898F, -1.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition valve_control6 = bone273.addOrReplaceChild("valve_control6", CubeListBuilder.create().texOffs(133, 16).addBox(-0.75F, -1.5F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6481F, 0.4898F, -1.5F, 0.0F, -1.2654F, 0.0F));

		PartDefinition bone283 = bone271.addOrReplaceChild("bone283", CubeListBuilder.create().texOffs(107, 102).addBox(1.055F, -0.2426F, 0.4743F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, -11.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone284 = bone283.addOrReplaceChild("bone284", CubeListBuilder.create().texOffs(98, 98).addBox(0.0F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.305F, -0.2426F, 1.7243F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bone285 = bone271.addOrReplaceChild("bone285", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5F, -0.85F, 4.0F, 0.0F, -2.2253F, 0.0F));

		PartDefinition bone286 = south_left.addOrReplaceChild("bone286", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -4.15F, -2.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-1.1F, -4.15F, -0.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 64).addBox(-1.5F, -3.15F, -1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.875F, -13.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition bone232 = bone286.addOrReplaceChild("bone232", CubeListBuilder.create().texOffs(69, 48).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.15F, -1.0F, -0.3054F, -0.7418F, -0.3491F));

		PartDefinition bone300 = south_left.addOrReplaceChild("bone300", CubeListBuilder.create().texOffs(111, 0).addBox(-0.75F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -2.5F, 6.0F, 0.0F, 0.2618F, 0.2182F));

		PartDefinition pulley_control3 = bone300.addOrReplaceChild("pulley_control3", CubeListBuilder.create().texOffs(51, 28).addBox(-13.0F, -15.25F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(31, 106).addBox(-14.0F, -15.75F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(10.25F, 14.25F, -0.5F));

		PartDefinition bone302 = south_left.addOrReplaceChild("bone302", CubeListBuilder.create().texOffs(111, 0).addBox(-0.75F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -2.5F, -6.0F, 0.0F, -0.2618F, 0.2182F));

		PartDefinition pulley_control4 = bone302.addOrReplaceChild("pulley_control4", CubeListBuilder.create().texOffs(51, 28).addBox(-13.0F, -15.25F, -1.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(31, 106).addBox(-14.0F, -15.75F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(11.75F, 14.25F, 0.5F));

		PartDefinition west = partdefinition.addOrReplaceChild("west", CubeListBuilder.create().texOffs(73, 48).addBox(-22.525F, -5.0F, -11.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.25F))
		.texOffs(66, 98).addBox(-16.55F, -1.5F, -5.5F, 2.0F, 5.0F, 11.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition bone293 = west.addOrReplaceChild("bone293", CubeListBuilder.create().texOffs(0, 16).addBox(2.0F, -0.1F, -5.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(53, 91).addBox(2.25F, -1.1F, -2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(128, 114).addBox(4.25F, -0.6F, 2.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(121, 107).addBox(1.75F, -0.1F, 0.5F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(122, 9).addBox(4.75F, -0.35F, -4.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(117, 52).addBox(6.25F, -0.6F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(54, 36).addBox(5.25F, -1.85F, -4.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition twist_control4 = bone293.addOrReplaceChild("twist_control4", CubeListBuilder.create().texOffs(109, 37).addBox(0.0F, -2.0F, -0.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.25F, -0.6F, -1.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition bone306 = bone293.addOrReplaceChild("bone306", CubeListBuilder.create().texOffs(0, 128).addBox(2.055F, -1.6426F, -2.4743F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(17, 50).addBox(9.055F, -1.6426F, -2.4743F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, 11.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition lever_control7 = bone306.addOrReplaceChild("lever_control7", CubeListBuilder.create().texOffs(52, 40).addBox(-15.8431F, -18.8324F, 7.0257F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(19.3981F, 15.6898F, -11.0F));

		PartDefinition lever_control8 = bone306.addOrReplaceChild("lever_control8", CubeListBuilder.create().texOffs(16, 16).addBox(0.25F, -2.25F, -1.3F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(4.055F, -1.8926F, -0.9243F, 0.0F, 0.0F, 0.9599F));

		PartDefinition bone305 = bone306.addOrReplaceChild("bone305", CubeListBuilder.create().texOffs(17, 91).addBox(0.0F, -1.25F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.055F, -1.6426F, -1.7243F, 0.0F, 0.0F, 0.6545F));

		PartDefinition bone310 = bone293.addOrReplaceChild("bone310", CubeListBuilder.create().texOffs(21, 106).addBox(1.055F, -0.2426F, -0.0257F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(62, 115).addBox(5.055F, -0.6426F, 0.4743F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(17, 75).addBox(9.055F, -2.3926F, 0.4743F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(61, 23).addBox(6.555F, -1.3926F, 0.9743F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(17, 81).addBox(1.305F, -0.7426F, 0.4743F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(54, 63).addBox(1.555F, -0.8426F, 0.4743F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(55, 100).addBox(2.555F, -2.2426F, 0.7243F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6019F, -0.6898F, -11.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition twist_control5 = bone310.addOrReplaceChild("twist_control5", CubeListBuilder.create().texOffs(55, 95).addBox(-1.5F, -1.5F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.055F, -3.3926F, 1.4743F, 0.0F, -0.6981F, 0.0F));

		PartDefinition bone304 = bone293.addOrReplaceChild("bone304", CubeListBuilder.create().texOffs(83, 85).addBox(-0.25F, 0.0F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.1569F, -0.7824F, 3.4993F, 0.0F, 1.1345F, 0.0F));

		PartDefinition bone294 = bone293.addOrReplaceChild("bone294", CubeListBuilder.create().texOffs(53, 91).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(3.75F, -0.35F, -3.75F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bone313 = west.addOrReplaceChild("bone313", CubeListBuilder.create().texOffs(84, 34).addBox(-0.5F, -3.25F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 28).addBox(-0.5F, -3.75F, -3.75F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(9, 28).addBox(-0.5F, -3.75F, 1.75F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.875F, -13.1F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition bone309 = bone313.addOrReplaceChild("bone309", CubeListBuilder.create().texOffs(73, 64).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -3.5F, 0.0F, -0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rotor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		north_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		north_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		east.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		south_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		south_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		west.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}

	public void renderConsole(Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.getAllParts().forEach(ModelPart::resetPose);
		rotor.getAllParts().forEach(ModelPart::resetPose);
		misc.getAllParts().forEach(ModelPart::resetPose);
		misc2.getAllParts().forEach(ModelPart::resetPose);
		misc3.getAllParts().forEach(ModelPart::resetPose);
		misc4.getAllParts().forEach(ModelPart::resetPose);
		misc5.getAllParts().forEach(ModelPart::resetPose);
		north_left.getAllParts().forEach(ModelPart::resetPose);
		north_right.getAllParts().forEach(ModelPart::resetPose);
		east.getAllParts().forEach(ModelPart::resetPose);
		south_right.getAllParts().forEach(ModelPart::resetPose);
		south_left.getAllParts().forEach(ModelPart::resetPose);
		west.getAllParts().forEach(ModelPart::resetPose);

		TardisIntReactions reactions = TardisIntReactions.getInstance(level.dimension());
		this.animate(reactions.ROTOR_ANIMATION, COPPER_FLIGHT_LOOP, Minecraft.getInstance().player.tickCount);

		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rotor.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		misc5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		north_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		north_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		east.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		south_right.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		south_left.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		west.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}