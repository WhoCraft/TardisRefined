package whocraft.tardis_refined.client.model.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

import java.util.List;

public interface IConsoleUnit {
    void renderConsole(Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
    default ResourceLocation getTexture(GlobalConsoleBlockEntity entity) {
        ConsoleTheme console = entity.getBlockState().getValue(GlobalConsoleBlock.CONSOLE);
        return entity.pattern().textureLocation();
    }
    ResourceLocation getDefaultTexture();
}
