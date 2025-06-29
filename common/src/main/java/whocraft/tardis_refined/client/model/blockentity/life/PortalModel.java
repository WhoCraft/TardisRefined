package whocraft.tardis_refined.client.model.blockentity.life;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface PortalModel<T extends BlockEntity> {
    void renderPortalMask(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
}
