package whocraft.tardis_refined.client.model.blockentity.door;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

public class FactoryDoorModel extends ShellModel {

	private final ModelPart root;
	private final ModelPart leftDoor;
	private final ModelPart rightDoor;

	public FactoryDoorModel(ModelPart root) {
		super(root);
		this.root = root.getChild("root");
		this.leftDoor = this.root.getChild("left_door");
		this.rightDoor = this.root.getChild("right_door");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(18, 45).addBox(-9.0F, -4.0F, -8.0F, 18.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(96, 2).addBox(-7.0F, -37.0F, -6.0F, 14.0F, 33.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(19, 17).addBox(-9.0F, -39.0F, -8.0F, 18.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(54, 57).addBox(-5.0F, -37.0F, -8.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(49, 66).addBox(-9.0F, -37.0F, -7.0F, 2.0F, 33.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(13, 66).addBox(7.0F, -37.0F, -7.0F, 2.0F, 33.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 52).addBox(-7.0F, -37.0F, -6.1F, 14.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 13.0F));

		PartDefinition left_door = root.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(70, 96).addBox(0.0F, -15.0F, -0.6F, 7.0F, 31.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -20.0F, -5.6F));

		PartDefinition right_door = root.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(95, 81).addBox(-7.0F, -15.0F, -0.6F, 7.0F, 31.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -20.0F, -5.6F));

		ShellModel.splice(partdefinition);


		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}

	@Override
	public void setDoorPosition(boolean open) {
		if (open) {
			this.leftDoor.yRot = -250f;
			this.rightDoor.yRot = 250f;
		} else {
			this.leftDoor.yRot = 0;
			this.rightDoor.yRot = 0;
		}
	}

	@Override
	public boolean isDoorModel() {
		return true;
	}

	@Override
	public void renderShell(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

	}
}