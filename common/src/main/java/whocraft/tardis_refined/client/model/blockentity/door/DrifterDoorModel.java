package whocraft.tardis_refined.client.model.blockentity.door;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

public class DrifterDoorModel extends ShellModel {

	private final ModelPart door_closed;
	private final ModelPart door_open;
	private final ModelPart main;
	private final ModelPart root;

	public DrifterDoorModel(ModelPart root) {
		super(root);
		this.root = root;
		this.door_closed = root.getChild("door_closed");
		this.door_open = root.getChild("door_open");
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition door_closed = partdefinition.addOrReplaceChild("door_closed", CubeListBuilder.create().texOffs(33, 77).addBox(-7.0F, -32.0F, 6.25F, 14.0F, 30.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 77).addBox(-7.5F, -32.5F, 6.75F, 15.0F, 31.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -0.5F));

		PartDefinition door_open = partdefinition.addOrReplaceChild("door_open", CubeListBuilder.create().texOffs(33, 36).addBox(-7.0F, -32.0F, 6.25F, 14.0F, 30.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 36).addBox(-7.5F, -32.5F, 6.75F, 15.0F, 31.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -0.5F));

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(41, 9).addBox(-7.4F, -2.5F, 4.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-9.5F, -34.025F, 7.025F, 19.0F, 34.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 46).addBox(-7.0F, -2.0F, 4.5F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(41, 9).addBox(6.4F, -2.5F, 4.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(64, 9).mirror().addBox(7.5F, -34.0F, 6.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(41, 0).addBox(-9.5F, -34.0F, 6.0F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 9).addBox(-9.5F, -34.0F, 6.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone = main.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(41, 5).addBox(-9.5F, 0.0F, -2.0F, 19.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.0F, 6.0F, 0.7418F, 0.0F, 0.0F));

		ShellModel.addMaterializationPart(partdefinition);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public boolean isDoorModel() {
		return true;
	}

	boolean isDoorOpen = false;
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

		door_open.visible = isDoorOpen;
		door_closed.visible = !isDoorOpen;

		door_open.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		door_closed.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setDoorPosition(boolean open) {
		this.isDoorOpen = open;
	}

	@Override
	public void renderShellOrInteriorDoor(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}
}