package whocraft.tardis_refined.client.model.blockentity.shell.rootplant;// Made with Blockbench 4.5.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class RootPlantStateTwoModel extends HierarchicalModel {

	private final ModelPart stage2;


	public RootPlantStateTwoModel(ModelPart root) {

		this.stage2 = root.getChild("stage2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition stage2 = partdefinition.addOrReplaceChild("stage2", CubeListBuilder.create().texOffs(0, 27).addBox(-79.5F, -11.0F, -3.5F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(36, 12).addBox(-78.5F, -7.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(29, 27).addBox(-79.5F, -14.0F, -3.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(76.0F, 24.0F, 0.0F));

		PartDefinition bone56 = stage2.addOrReplaceChild("bone56", CubeListBuilder.create(), PartPose.offset(-20.0F, 0.0F, 0.0F));

		PartDefinition bone57 = bone56.addOrReplaceChild("bone57", CubeListBuilder.create().texOffs(0, 17).addBox(-62.0F, 0.0F, -8.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-62.0F, 0.0F, -8.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition bone58 = bone56.addOrReplaceChild("bone58", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone59 = bone58.addOrReplaceChild("bone59", CubeListBuilder.create().texOffs(0, 17).addBox(-6.0F, -12.1206F, 46.6726F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-6.0F, -12.1206F, 46.6726F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition bone60 = bone58.addOrReplaceChild("bone60", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone61 = bone60.addOrReplaceChild("bone61", CubeListBuilder.create().texOffs(0, 17).addBox(50.0F, 0.0F, -8.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(50.0F, 0.0F, -8.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition bone62 = bone60.addOrReplaceChild("bone62", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone63 = bone62.addOrReplaceChild("bone63", CubeListBuilder.create().texOffs(0, 17).addBox(-6.0F, 12.1206F, -62.6726F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-6.0F, 12.1206F, -62.6726F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition bone22 = stage2.addOrReplaceChild("bone22", CubeListBuilder.create(), PartPose.offset(-37.75F, 0.0F, 0.0F));

		PartDefinition bone20 = bone22.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offset(0.75F, 0.0F, 0.0F));

		PartDefinition bone21 = bone20.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(0, 0).addBox(-45.0F, -7.5F, -8.0F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition bone4 = bone20.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -15.9411F, 30.0755F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition bone6 = bone4.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone17 = bone6.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(0, 0).addBox(33.0F, -7.5F, -8.0F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition bone18 = bone6.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone19 = bone18.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.9411F, -46.0755F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart root() {
		return stage2;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}
}