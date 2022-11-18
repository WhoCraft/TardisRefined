package whocraft.tardis_refined.client.model.blockentity.shell.rootplant;// Made with Blockbench 4.5.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class RootShellModel extends HierarchicalModel  {

	private final ModelPart stage6;

	public RootShellModel(ModelPart root) {
		this.stage6 = root.getChild("stage6");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition stage6 = partdefinition.addOrReplaceChild("stage6", CubeListBuilder.create(), PartPose.offset(-19.0F, 24.0F, 0.0F));

		PartDefinition bone23 = stage6.addOrReplaceChild("bone23", CubeListBuilder.create(), PartPose.offset(19.0F, -19.0F, 0.0F));

		PartDefinition bone29 = bone23.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(31, 40).addBox(-7.0F, -38.0F, 0.0F, 14.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -12.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition bone24 = bone23.addOrReplaceChild("bone24", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone30 = bone24.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(0, 40).addBox(-7.0F, -38.0F, 0.0F, 14.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -12.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition bone25 = bone24.addOrReplaceChild("bone25", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone26 = bone25.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(31, 0).addBox(-7.0F, -38.0F, 0.0F, 14.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -12.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition bone27 = bone25.addOrReplaceChild("bone27", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone28 = bone27.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(31, 0).addBox(-7.0F, -38.0F, 0.0F, 14.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -12.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition bone31 = bone27.addOrReplaceChild("bone31", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone32 = bone31.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(31, 0).addBox(-7.0F, -38.0F, 0.0F, 14.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -12.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition bone33 = bone31.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone34 = bone33.addOrReplaceChild("bone34", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -38.0F, 0.0F, 14.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -12.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition bone35 = stage6.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(62, 40).addBox(-6.0F, -19.0F, -10.4F, 12.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, -19.0F, 0.0F));

		PartDefinition bone36 = bone35.addOrReplaceChild("bone36", CubeListBuilder.create().texOffs(62, 0).addBox(-6.0F, -19.0F, -10.4F, 12.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone37 = bone36.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(62, 0).addBox(-6.0F, -19.0F, -10.4F, 12.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone38 = bone37.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(62, 0).addBox(-6.0F, -19.0F, -10.4F, 12.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone39 = bone38.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(62, 0).addBox(-6.0F, -19.0F, -10.4F, 12.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone40 = bone39.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(62, 0).addBox(-6.0F, -19.0F, -10.4F, 12.0F, 38.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone114 = stage6.addOrReplaceChild("bone114", CubeListBuilder.create(), PartPose.offset(19.0F, -19.0F, 0.0F));

		PartDefinition bone120 = bone114.addOrReplaceChild("bone120", CubeListBuilder.create().texOffs(78, 69).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -10.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone115 = bone114.addOrReplaceChild("bone115", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone121 = bone115.addOrReplaceChild("bone121", CubeListBuilder.create().texOffs(78, 69).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -10.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone116 = bone115.addOrReplaceChild("bone116", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone117 = bone116.addOrReplaceChild("bone117", CubeListBuilder.create().texOffs(78, 69).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -10.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone118 = bone116.addOrReplaceChild("bone118", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone119 = bone118.addOrReplaceChild("bone119", CubeListBuilder.create().texOffs(78, 69).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -10.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone122 = bone118.addOrReplaceChild("bone122", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone123 = bone122.addOrReplaceChild("bone123", CubeListBuilder.create().texOffs(78, 69).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -10.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone124 = bone122.addOrReplaceChild("bone124", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone125 = bone124.addOrReplaceChild("bone125", CubeListBuilder.create().texOffs(78, 69).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -10.4F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bone2 = stage6.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(78, 82).addBox(-6.0F, -20.0F, -10.4F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, 19.0F, 0.0F));

		PartDefinition bone3 = bone2.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(78, 82).addBox(-6.0F, -20.0F, -10.4F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(78, 82).addBox(-6.0F, -20.0F, -10.4F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(78, 82).addBox(-6.0F, -20.0F, -10.4F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(78, 82).addBox(-6.0F, -20.0F, -10.4F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone7 = bone6.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(78, 82).addBox(-6.0F, -20.0F, -10.4F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone41 = stage6.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(0, 80).addBox(-6.0F, 13.0F, -10.4F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, -57.0F, 0.0F));

		PartDefinition bone42 = bone41.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(0, 80).addBox(-6.0F, 13.0F, -10.4F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone43 = bone42.addOrReplaceChild("bone43", CubeListBuilder.create().texOffs(0, 80).addBox(-6.0F, 13.0F, -10.4F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone44 = bone43.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(0, 80).addBox(-6.0F, 13.0F, -10.4F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone45 = bone44.addOrReplaceChild("bone45", CubeListBuilder.create().texOffs(0, 80).addBox(-6.0F, 13.0F, -10.4F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition bone46 = bone45.addOrReplaceChild("bone46", CubeListBuilder.create().texOffs(0, 80).addBox(-6.0F, 13.0F, -10.4F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public ModelPart root() {
		return stage6;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

	}
}