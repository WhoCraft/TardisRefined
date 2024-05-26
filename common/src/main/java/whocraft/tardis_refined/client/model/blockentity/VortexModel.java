package whocraft.tardis_refined.client.model.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class VortexModel extends EntityModel {

    public final ModelPart vortex;

    public VortexModel(ModelPart root) {
        this.vortex = root.getChild("vortex");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition vortex = partdefinition.addOrReplaceChild("vortex", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = vortex.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(29, 0).addBox(1.0F, -8.0F, -7.0F, 0.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 2.5F, 1.25F, 0.0F, -0.4712F, 1.5708F));

        PartDefinition cube_r2 = vortex.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(29, 0).addBox(1.0F, -7.0F, -7.0F, 0.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.5F, 1.25F, 0.0F, -0.4712F, 3.1416F));

        PartDefinition cube_r3 = vortex.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(29, 0).addBox(1.0F, -8.0F, -7.0F, 0.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -2.5F, 1.25F, 0.0F, -0.4712F, -1.5708F));

        PartDefinition cube_r4 = vortex.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(29, 0).addBox(1.0F, -8.0F, -7.0F, 0.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.5F, 1.25F, 0.0F, -0.4712F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        vortex.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

    }
}