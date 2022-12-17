package whocraft.tardis_refined.client.model.blockentity.device;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class ConsoleConfigurationModel extends HierarchicalModel {

	private final ModelPart root;
	private final ModelPart group2;
	private final ModelPart bone;

	public ConsoleConfigurationModel(ModelPart root) {
		this.root = root;
		this.group2 = root.getChild("group2");
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition group2 = partdefinition.addOrReplaceChild("group2", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition group = group2.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offset(-8.0F, -7.0F, 8.0F));

		PartDefinition cube_r1 = group.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-6.0F, -4.0F, 0.0F, 12.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r2 = group.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 26).addBox(-6.0F, -4.0F, 0.0F, 12.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(18, 31).addBox(-3.0F, -2.0F, -8.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(18, 16).addBox(-3.0F, -9.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(24, 22).addBox(-10.0F, -2.0F, 0.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 31).addBox(0.0F, -2.0F, 0.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		group2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}
}