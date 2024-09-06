package whocraft.tardis_refined.client.model.blockentity.door.interior;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;

public class PoliceBoxDoorModel extends ShellDoorModel {

	private final ModelPart left_door;
	private final ModelPart root;

	public PoliceBoxDoorModel(ModelPart root) {
		this.root = root;
		this.left_door =  (ModelPart) getAnyDescendantWithName("left_door").get();
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(41, 11).addBox(-7.5F, -32.25F, -2.525F, 17.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 35).addBox(-10.5F, -37.25F, -2.775F, 23.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(41, 5).addBox(-8.5F, -36.25F, -3.775F, 19.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(19, 44).addBox(-10.5F, -33.0F, -2.775F, 2.0F, 33.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(41, 0).addBox(-10.5F, 0.0F, -2.775F, 23.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.5F, -33.0F, -0.775F, 19.0F, 33.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(19, 44).mirror().addBox(10.5F, -33.0F, -2.775F, 2.0F, 33.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 44).addBox(9.5F, -33.0F, -2.525F, 1.0F, 33.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(30, 44).mirror().addBox(-8.5F, -33.0F, -2.525F, 1.0F, 33.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 23.0F, 7.775F));

		PartDefinition right_door = bone.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(44, 53).mirror().addBox(-7.0F, -8.0F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(37, 44).addBox(-9.0F, -31.25F, -1.025F, 1.0F, 31.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(53, 62).addBox(-9.0F, -1.0F, -1.025F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(44, 53).addBox(-7.0F, -15.5F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(41, 15).addBox(-7.0F, -23.0F, -2.475F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 44).addBox(-8.0F, -31.25F, -0.475F, 8.0F, 31.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 25).addBox(-8.0F, -1.0F, -0.475F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(53, 34).addBox(-7.0F, -30.75F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 44).addBox(-7.0F, -8.0F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 44).mirror().addBox(-7.0F, -15.5F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 44).mirror().addBox(-7.0F, -23.0F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(59, 43).addBox(-6.5F, -22.5F, 0.05F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 28).addBox(-6.0F, -28.25F, -0.525F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, -1.5F));

		PartDefinition bone4 = right_door.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(41, 25).addBox(-3.25F, -6.25F, 18.975F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(59, 51).addBox(-2.25F, -3.75F, 19.05F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.75F, -24.5F, -19.0F));

		PartDefinition left_door = bone.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(44, 53).addBox(1.0F, -8.0F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 53).mirror().addBox(1.0F, -15.5F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 53).mirror().addBox(1.0F, -23.25F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 62).addBox(5.0F, -19.25F, -1.475F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 44).mirror().addBox(0.0F, -31.25F, -0.475F, 8.0F, 31.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 25).mirror().addBox(0.0F, -1.0F, -0.475F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(53, 34).mirror().addBox(1.0F, -30.75F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 44).mirror().addBox(1.0F, -8.0F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 44).addBox(1.0F, -15.5F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 44).addBox(1.0F, -23.0F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(58, 15).addBox(1.5F, -22.5F, 0.05F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(59, 55).addBox(7.5F, -21.5F, -0.25F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(56, 28).mirror().addBox(2.0F, -28.25F, -0.525F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.5F, 0.0F, -1.5F));

		PartDefinition bone6 = left_door.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(41, 25).mirror().addBox(-2.75F, -6.25F, 18.975F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(59, 51).mirror().addBox(-1.75F, -3.75F, 19.05F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.75F, -24.5F, -19.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderInteriorDoor(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
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
		this.left_door.yRot = (open) ? -300f : 0;
	}

}