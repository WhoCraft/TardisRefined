package whocraft.tardis_refined.client.model.blockentity.shell.internal.door;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import whocraft.tardis_refined.client.model.TardisModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class RootShellDoorModel extends TardisModel {

    private final ModelPart root;
    private final ModelPart stage7;

    public RootShellDoorModel(ModelPart root) {

        this.root = root;
        this.stage7 = root.getChild("stage7");
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        stage7.render(poseStack, vertexConsumer, packedLight, packedOverlay, net.minecraft.util.ARGB.colorFromFloat(alpha, red, green, blue));
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

    }
}