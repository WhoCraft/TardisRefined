package whocraft.tardis_refined.client.model.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

public interface IShellModel {

    void setDoorPosition(boolean open);
    void renderShell(GlobalShellBlockEntity entity, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
    ResourceLocation texture();
    default ResourceLocation lightTexture() {
        return null;
    }
}
