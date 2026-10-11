package whocraft.tardis_refined.client.model.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;

public interface ConsoleUnit {
    void renderConsole(GlobalConsoleBlockEntity globalConsoleBlock, Level level, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);

    default Identifier getTexture(GlobalConsoleBlockEntity entity, boolean emissiveTexture) {
        if (entity == null || entity.pattern() == null) return getDefaultTexture();
        return emissiveTexture ? entity.pattern().emissiveTexture() : entity.pattern().texture();
    }

    default Identifier getTexture(GlobalConsoleBlockEntity entity) {
        return getTexture(entity, false);
    }

    Identifier getDefaultTexture();

}
