package whocraft.tardis_refined.client.model.blockentity.life;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.common.blockentity.life.ZeitonGlassBlockEntity;

public class ZeitonGlassModel extends EntityModel implements PortalModel<ZeitonGlassBlockEntity> {

    private final ModelPart root;

    public ZeitonGlassModel(ModelPart root) {
        this.root = root;
    }

    @Override
    public void renderPortalMask(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }


    @Override
    public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {

    }
}