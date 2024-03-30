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

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(19, 41).mirror().addBox(8.5F, -32.0F, 5.5F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).addBox(-8.5F, -32.0F, 6.5F, 17.0F, 32.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(19, 41).addBox(-10.5F, -32.0F, 5.5F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 34).addBox(-10.5F, -35.25F, 5.5F, 21.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.5F));

		PartDefinition right_door = root.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(50, 51).mirror().addBox(-7.0F, -8.0F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(28, 41).addBox(-9.0F, -31.25F, -0.775F, 1.0F, 31.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(56, 27).addBox(-9.0F, -0.75F, -1.025F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(50, 51).addBox(-7.0F, -15.5F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(35, 41).addBox(-7.0F, -23.0F, -2.475F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 41).addBox(-8.0F, -31.25F, -0.475F, 8.0F, 31.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 0).mirror().addBox(-8.0F, -1.0F, -0.475F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(52, 42).addBox(-7.0F, -30.75F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 33).addBox(-7.0F, -8.0F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 33).mirror().addBox(-7.0F, -15.5F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(50, 33).mirror().addBox(-7.0F, -23.0F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 3).addBox(-6.5F, -22.5F, 0.05F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 23).addBox(-6.0F, -28.25F, -0.525F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(8.5F, 0.0F, 6.25F));

		PartDefinition bone3 = right_door.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(35, 51).addBox(-3.25F, -6.25F, 18.975F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 19).addBox(-2.25F, -3.75F, 19.05F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.75F, -24.5F, -19.0F));

		PartDefinition left_door = root.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(50, 51).addBox(1.0F, -8.0F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 51).addBox(1.0F, -15.5F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 51).addBox(1.0F, -23.0F, -0.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(37, 0).addBox(0.0F, -31.25F, -0.475F, 8.0F, 31.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 0).addBox(0.0F, -1.0F, -0.475F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 11).addBox(1.5F, -22.5F, 0.05F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 33).addBox(1.0F, -23.0F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 33).addBox(1.0F, -15.5F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 33).mirror().addBox(1.0F, -8.0F, 0.025F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 60).addBox(4.0F, -21.0F, -1.475F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.5F, 0.0F, 6.25F));

		PartDefinition bone2 = left_door.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(35, 51).mirror().addBox(-2.75F, -6.25F, 18.975F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 19).mirror().addBox(-1.75F, -3.75F, 19.05F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.75F, -24.5F, -19.0F));

		PartDefinition bone5 = left_door.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(52, 42).mirror().addBox(-3.25F, -8.25F, 1.0F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 23).mirror().addBox(-2.25F, -5.75F, 0.975F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.25F, -22.5F, -1.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderInteriorDoor(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
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