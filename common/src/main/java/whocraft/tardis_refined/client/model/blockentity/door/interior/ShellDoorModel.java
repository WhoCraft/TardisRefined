package whocraft.tardis_refined.client.model.blockentity.door.interior;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import whocraft.tardis_refined.client.model.TardisModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.patterns.ShellPattern;

public abstract class ShellDoorModel extends TardisModel {

    public abstract void renderFrame(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);

    public abstract void renderPortalMask(GlobalDoorBlockEntity doorBlockEntity, boolean open, boolean isBaseModel, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);

    @Override
    public ModelPart root() {
        return null;
    }

    public abstract void setDoorPosition(boolean open);

    @Override
    public void setupAnim(Entity entity, float f, float g, float h, float i, float j) {
        throw new RuntimeException("Woah! You should not be calling this...how are you calling this?");
    }

    public Identifier getInteriorDoorTexture(GlobalDoorBlockEntity globalDoorBlockEntity) {
        return getInteriorDoorTexture(globalDoorBlockEntity.pattern());
    }

    public Identifier getInteriorDoorTexture(ShellPattern pattern) {
        return pattern.doorTexture().texture();
    }
}
