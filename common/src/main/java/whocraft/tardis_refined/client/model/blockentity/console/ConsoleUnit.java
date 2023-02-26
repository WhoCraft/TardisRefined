package whocraft.tardis_refined.client.model.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public interface ConsoleUnit {
    void renderConsole(GlobalConsoleBlockEntity globalConsoleBlock, Level level, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);

    default ResourceLocation getTexture(GlobalConsoleBlockEntity entity) {

        if (entity == null) return getDefaultTexture();

        ConsoleTheme console = entity.getBlockState().getValue(GlobalConsoleBlock.CONSOLE);
        return entity.pattern().textureLocation();
    }

    default ModelPart findPart(HierarchicalModel hierarchicalModel, String string) {
        return hierarchicalModel.root().getAllParts().filter((modelPart) -> {
            return modelPart.hasChild(string);
        }).findFirst().map((modelPart) -> {
            return modelPart.getChild(string);
        }).get();
    }

    ResourceLocation getDefaultTexture();

}
