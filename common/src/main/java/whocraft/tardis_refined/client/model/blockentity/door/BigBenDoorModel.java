package whocraft.tardis_refined.client.model.blockentity.door;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class BigBenDoorModel extends ShellModel {
	private final ModelPart root;
	private final ModelPart door;
	private final ModelPart bone27;
	private final ModelPart bone5;
	private final ModelPart bone2;
	private final ModelPart bb_main;

	@Override
	public void setDoorPosition(boolean open) {
		this.door.yRot = (open) ? -275f : 0;

	}

	@Override
	public void renderShell(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

	}

	@Override
	public ResourceLocation texture() {
		return ShellTheme.BIG_BEN.getInternalDoorTexture();
	}

	@Override
	public ResourceLocation lightTexture() {
		return null;
	}

	public BigBenDoorModel(ModelPart root) {
		super(root);
		this.root = root;
		this.door = root.getChild("door");
		this.bone27 = root.getChild("bone27");
		this.bone5 = root.getChild("bone5");
		this.bone2 = root.getChild("bone2");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition door = partdefinition.addOrReplaceChild("door", CubeListBuilder.create().texOffs(0, 61).addBox(-0.025F, -17.0F, 0.0F, 14.0F, 34.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(35, 37).addBox(-0.025F, -17.0F, 0.5F, 14.0F, 34.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.975F, 7.0F, 5.5F));

		PartDefinition bone27 = partdefinition.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(66, 21).addBox(-6.0F, -6.0F, 0.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 5.0F));

		PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(81, 72).addBox(-11.0F, -38.0F, -9.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 13.0F));

		PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(66, 35).addBox(-10.5F, -18.0F, -9.0F, 4.0F, 18.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(31, 73).addBox(-10.0F, -34.0F, -8.0F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone7 = bone5.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(44, 73).addBox(-10.0F, -47.0F, -8.0F, 4.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone8 = bone5.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(62, 72).addBox(-11.0F, -51.0F, -9.0F, 5.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(81, 72).mirror().addBox(6.0F, -38.0F, -9.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 13.0F));

		PartDefinition bone3 = bone2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(66, 35).mirror().addBox(6.5F, -18.0F, -9.0F, 4.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(31, 73).mirror().addBox(7.0F, -34.0F, -8.0F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone4 = bone2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(44, 73).mirror().addBox(6.0F, -47.0F, -8.0F, 4.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone9 = bone2.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(62, 72).mirror().addBox(6.0F, -51.0F, -9.0F, 5.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(66, 65).addBox(-6.0F, -36.975F, 4.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(66, 58).addBox(-6.0F, -52.0F, 4.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(66, 0).addBox(-8.0F, -71.0F, 5.0F, 16.0F, 19.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-7.0F, -57.0F, 5.0F, 14.0F, 57.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(35, 0).addBox(-7.0F, -35.0F, 6.25F, 14.0F, 35.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		ShellModel.splice(partdefinition);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone27.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}
}