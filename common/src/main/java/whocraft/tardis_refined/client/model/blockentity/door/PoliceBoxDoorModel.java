package whocraft.tardis_refined.client.model.blockentity.door;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.client.model.blockentity.shell.IShellModel;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class PoliceBoxDoorModel extends HierarchicalModel implements IShellModel {

	private final ModelPart left_door;
	private final ModelPart right_door;
	private final ModelPart frame;

	public PoliceBoxDoorModel(ModelPart root) {
		this.frame = root.getChild("frame");
		this.left_door = root.getChild("left_door");
		this.right_door = root.getChild("right_door");

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition left_door = partdefinition.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(43, 9).addBox(-6.25F, -8.0F, -0.25F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(43, 9).addBox(-6.25F, -22.0F, -0.25F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(43, 9).addBox(-6.25F, -15.0F, -0.25F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 70).mirror().addBox(-8.0F, -30.0F, 0.0F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(28, 46).addBox(-8.5F, -30.0F, -0.525F, 1.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 23.5F, 6.5F));

		PartDefinition bone5 = left_door.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(43, 17).mirror().addBox(-2.5F, -6.0F, 0.4F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.75F, -23.0F, -0.75F));

		PartDefinition right_door = partdefinition.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(43, 9).addBox(1.25F, -7.5F, -0.25F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(43, 9).addBox(1.25F, -21.5F, -0.25F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(43, 9).addBox(1.25F, -14.5F, -0.25F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(0.0F, -29.5F, 0.0F, 8.0F, 30.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(43, 17).addBox(1.25F, -28.5F, -0.35F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 23.0F, 6.5F));

		PartDefinition frame = partdefinition.addOrReplaceChild("frame", CubeListBuilder.create().texOffs(43, 4).addBox(-8.0F, -33.5F, -11.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(19, 38).addBox(-9.0F, -37.1F, -12.0F, 18.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(43, 0).addBox(-10.0F, -2.0F, -11.0F, 20.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-10.0F, -37.0F, -9.75F, 20.0F, 36.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(19, 46).addBox(-10.0F, -37.0F, -11.0F, 2.0F, 35.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(19, 46).mirror().addBox(8.0F, -37.0F, -11.0F, 2.0F, 35.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 25.0F, 16.75F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		left_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		frame.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.frame;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}

	@Override
	public void setDoorPosition(boolean open) {
		this.right_door.yRot = (open) ? -275f : 0;
	}

	@Override
	public ResourceLocation texture() {
		return ShellTheme.POLICE_BOX.getInternalDoorTexture();
	}
}