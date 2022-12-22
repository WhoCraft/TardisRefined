package whocraft.tardis_refined.client.model.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class FactoryShellModel extends IShellModel {

	private final ModelPart realRoot;
	private final ModelPart root;
	private final ModelPart leftDoor;
	private final ModelPart rightDoor;

	public FactoryShellModel(ModelPart root) {
		super(root);
		this.realRoot = root;
		this.root = root.getChild("root");
		this.leftDoor = this.root.getChild("left_door");
		this.rightDoor = this.root.getChild("right_door");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 28).addBox(-10.0F, -3.0F, -8.0F, 20.0F, 3.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-10.0F, -44.0F, -8.0F, 20.0F, 7.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(54, 57).addBox(-5.0F, -37.0F, -8.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(54, 52).addBox(-5.0F, -5.0F, -8.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(35, 52).addBox(-8.0F, -37.0F, -6.0F, 1.0F, 34.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(95, 45).addBox(-7.0F, -37.0F, 9.0F, 14.0F, 34.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 52).addBox(7.0F, -37.0F, -6.0F, 1.0F, 34.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(19, 52).addBox(-7.0F, -37.0F, -6.0F, 14.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(19, 57).addBox(-7.0F, -5.0F, -6.0F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(81, 0).mirror().addBox(8.0F, -37.0F, -3.0F, 2.0F, 34.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(81, 0).addBox(-10.0F, -37.0F, -3.0F, 2.0F, 34.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -2.0F));

		PartDefinition left_door = root.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(70, 97).addBox(0.0F, -15.0F, -0.5F, 7.0F, 30.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -20.0F, -5.6F));

		PartDefinition right_door = root.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(95, 81).addBox(-7.0F, -15.0F, -0.5F, 7.0F, 30.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -20.0F, -5.6F));

		PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(70, 52).addBox(-1.0F, -17.0F, -5.0F, 2.0F, 34.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.0F, 11.0F, 0.0F, 1.5708F, 0.0F));

		IShellModel.splice(partdefinition);


		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.realRoot;
	}

	@Override
	public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {
	}

	@Override
	public void setDoorPosition(boolean open) {
		if (open) {
			this.leftDoor.yRot = 250f;
			this.rightDoor.yRot = -250f;
		} else {
			this.leftDoor.yRot = 0;
			this.rightDoor.yRot = 0;
		}
	}

	private float landingTime = 0;
	private float takingOffTime = 0;

	@Override
	public void renderShell(GlobalShellBlockEntity entity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

		if (entity.id == null) return;
		this.root().getAllParts().forEach(ModelPart::resetPose);
		TardisClientData reactions = TardisClientData.getInstance(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(TardisRefined.MODID, entity.id.toString())));

		if (reactions.isLanding()) {
			this.animate(reactions.LANDING_ANIMATION, MODEL_LAND, landingTime * animationTimeMultiplier);
			if (isBaseModel) {
				landingTime++;
			}
		} else {
			landingTime = 0;
		}
		if (reactions.isTakingOff()) {
			this.animate(reactions.TAKEOFF_ANIMATION, MODEL_TAKEOFF, takingOffTime * animationTimeMultiplier);
			if (isBaseModel) {
				takingOffTime++;
			}
		} else {
			takingOffTime = 0;
		}

		float currentAlpha = (reactions.isFlying()) ? (this.initAlpha() - this.fadeValue().y) * 0.1f : 1f;
		setDoorPosition(open);

		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, currentAlpha);
	}

	@Override
	public ResourceLocation texture() {
		return ShellTheme.FACTORY.getExternalShellTexture();
	}

	@Override
	public ResourceLocation lightTexture() {
		return null;
	}
}