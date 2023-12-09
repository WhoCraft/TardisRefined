package whocraft.tardis_refined.client.model.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

public class PoliceBoxModel extends ShellModel {
	private final ModelPart root;
	private final ModelPart left_door;
	private final ModelPart right_door;
	private final ModelPart frame;

	public PoliceBoxModel(ModelPart root) {
		super(root);
		this.root = root;
		this.frame = root.getChild("frame");
		this.left_door = root.getChild("left_door");
		this.right_door = root.getChild("right_door");
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

		ShellModel.addMaterializationPart(partdefinition);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.scale(1.05f, 1.05f, 1.05f);
		poseStack.translate(0, -0.07, 0);
		frame.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
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
	public void renderShell(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (isBaseModel) {
			poseStack.scale(1.05f, 1.05f, 1.05f);
			poseStack.translate(0, -0.07, 0);
		}
		handleAllAnimations(entity,frame,isBaseModel, open, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

		frame.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
		left_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
		right_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, this.getCurrentAlpha());
	}

}