package whocraft.tardis_refined.client.model.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
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


public class MysticShellModel extends HierarchicalModel implements IShellModel {

    private final ModelPart right_door;
    private final ModelPart left_door;
    private final ModelPart bone3;
    private final ModelPart bone6;
    private final ModelPart bone7;
    private final ModelPart bone10;
    private final ModelPart bone11;
    private final ModelPart bone;
    private final ModelPart bb_main;
    private final ModelPart root;

    public MysticShellModel(ModelPart root) {
        this.root = root;
        this.right_door = root.getChild("right_door");
        this.left_door = root.getChild("left_door");
        this.bone3 = root.getChild("bone3");
        this.bone6 = root.getChild("bone6");
        this.bone7 = root.getChild("bone7");
        this.bone10 = root.getChild("bone10");
        this.bone11 = root.getChild("bone11");
        this.bone = root.getChild("bone");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition right_door = partdefinition.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(0, 0).addBox(-6.975F, -3.0F, -2.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(62, 11).addBox(-7.0F, -16.0F, -1.0F, 7.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 5.0F, -7.0F));

        PartDefinition left_door = partdefinition.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(62, 11).mirror().addBox(0.0F, -16.0F, -1.0F, 7.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(3.975F, -3.0F, -2.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, 5.0F, -7.0F));

        PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(31, 32).addBox(-7.0F, -35.0F, -8.0F, 14.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(31, 32).addBox(-7.0F, -35.0F, -8.0F, 14.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(31, 32).addBox(-7.0F, -35.0F, -8.0F, 14.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone6 = partdefinition.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(79, 11).addBox(-5.0F, -3.0F, -9.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 66).addBox(-5.0F, -38.0F, -9.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone7 = partdefinition.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(79, 11).addBox(-5.0F, -3.0F, -9.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 66).addBox(-5.0F, -38.0F, -9.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone8 = bone7.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(79, 11).addBox(-5.0F, -3.0F, -9.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 66).addBox(-5.0F, -38.0F, -9.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone9 = bone8.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(79, 11).addBox(-5.0F, -3.0F, -9.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 66).addBox(-5.0F, -38.0F, -9.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone10 = partdefinition.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(73, 77).addBox(5.0F, -26.0F, -10.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(73, 77).mirror().addBox(20.0F, -26.0F, -10.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(77, 40).mirror().addBox(20.0F, 9.0F, -10.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(77, 40).addBox(5.0F, 9.0F, -10.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(29, 72).addBox(5.0F, 9.0F, 5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(29, 72).mirror().addBox(20.0F, 9.0F, 5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-15.0F, 10.0F, 0.0F));

        PartDefinition bone11 = partdefinition.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(73, 66).addBox(5.0F, -26.0F, 5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(73, 66).mirror().addBox(20.0F, -26.0F, 5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-15.0F, 10.0F, 0.0F));

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(62, 45).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.0F, -11.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r2 = bone2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(62, 45).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.0F, -11.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bone12 = bone2.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r3 = bone12.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(62, 45).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.0F, -11.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bone13 = bone12.addOrReplaceChild("bone13", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r4 = bone13.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(62, 45).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.0F, -11.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(9, 77).addBox(-10.0F, -23.0F, -10.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 66).addBox(-9.0F, -35.0F, -9.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 66).mirror().addBox(7.0F, -35.0F, -9.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 66).mirror().addBox(7.0F, -35.0F, 7.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 66).addBox(-9.0F, -35.0F, 7.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(9, 77).mirror().addBox(6.0F, -23.0F, -10.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(43, 16).addBox(-10.0F, -23.0F, 6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(43, 16).mirror().addBox(6.0F, -23.0F, 6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 16).addBox(-7.0F, -37.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(9, 66).addBox(-3.0F, -45.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(65, 57).addBox(-3.0F, -44.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(0, 32).addBox(-7.0F, -35.0F, 1.0F, 14.0F, 32.0F, 1.0F, new CubeDeformation(-0.025F))
                .texOffs(54, 58).addBox(6.9F, -35.0F, -7.0F, 1.0F, 32.0F, 8.0F, new CubeDeformation(-0.025F))
                .texOffs(54, 58).mirror().addBox(-7.9F, -35.0F, -7.0F, 1.0F, 32.0F, 8.0F, new CubeDeformation(-0.025F)).mirror(false)
                .texOffs(44, 0).addBox(-6.9F, -35.9F, -7.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(-0.025F))
                .texOffs(44, 0).addBox(-6.9F, -4.1F, -7.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(-0.025F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        right_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_door.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone10.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone11.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void setDoorPosition(boolean open) {
        if (open) {
            this.left_door.yRot = 250f;
            this.right_door.yRot = -250f;
        } else {
            this.left_door.yRot = 0;
            this.right_door.yRot = 0;
        }
    }

    @Override
    public void renderShell(GlobalShellBlockEntity entity, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(entity.id == null) return;

        TardisClientData reactions = TardisClientData.getInstance(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(TardisRefined.MODID, entity.id.toString())));
        // Use sine wave to oscillate the value of currentAlpha between 0 and 1.0
        double elapsedTime = entity.getLevel().getGameTime() / 50.0;
        float currentAlpha = reactions.isFlying() ? (float) ((1.0 - Math.abs(Math.sin(elapsedTime))) / 2.0) : alpha;
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, reactions.isFlying() ? currentAlpha : alpha);    }

    @Override
    public ResourceLocation texture() {
        return ShellTheme.MYSTIC.getExternalShellTexture();
    }

    @Override
    public ResourceLocation lightTexture() {
        return ShellTheme.MYSTIC.emmissiveExternal();
    }
}