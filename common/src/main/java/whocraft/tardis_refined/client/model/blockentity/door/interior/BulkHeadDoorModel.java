package whocraft.tardis_refined.client.model.blockentity.door.interior;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;

public class BulkHeadDoorModel extends HierarchicalModel {

	private final ModelPart root;
	private final ModelPart right;
	private final ModelPart left;


	public BulkHeadDoorModel(ModelPart root) {
		this.root = root.getChild("root");
		this.right = this.root.getChild("right");
		this.left = this.root.getChild("left");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

		PartDefinition right = root.addOrReplaceChild("right", CubeListBuilder.create().texOffs(64, 54).addBox(0.0F, -48.0F, -19.0F, 24.0F, 48.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 56).addBox(0.0F, -48.0F, -20.0F, 24.0F, 48.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left = root.addOrReplaceChild("left", CubeListBuilder.create().texOffs(64, 0).addBox(-24.0F, -48.0F, -19.0F, 24.0F, 48.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-24.0F, -48.0F, -20.0F, 24.0F, 48.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

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

	public void setDoorPosition(BlockState state) {
		if (state.getValue(BulkHeadDoorBlock.OPEN)) {
			right.x = 20f;
			left.x = -20f;
		} else {
			right.x = 0f;
			left.x = 0f;
		}
	}
}