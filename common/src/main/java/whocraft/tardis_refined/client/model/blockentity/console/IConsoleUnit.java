package whocraft.tardis_refined.client.model.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;

public interface IConsoleUnit {
    void renderConsole(Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
    ResourceLocation getTexture(GlobalConsoleBlockEntity entity);
}
