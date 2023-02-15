package whocraft.tardis_refined.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderPanorama {


    public static void drawCube(PoseStack matrixStack, int x, int y, int width, int height, float rotation) {
        matrixStack.pushPose();
        matrixStack.translate(x + width / 2, y + height / 2, 100);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(rotation));
        matrixStack.translate(-(x + width / 2), -(y + height / 2), -100);

        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();

        drawQuad(matrixStack, buffer.getBuffer(RenderType.text(new ResourceLocation("your_front_texture_path"))), x, y, width, height, 0, 0, 1, 1);
        drawQuad(matrixStack, buffer.getBuffer(RenderType.text(new ResourceLocation("your_back_texture_path"))), x, y, width, height, 0, 0, 1, 1);
        drawQuad(matrixStack, buffer.getBuffer(RenderType.text(new ResourceLocation("your_left_texture_path"))), x, y, width, height, 0, 0, 1, 1);
        drawQuad(matrixStack, buffer.getBuffer(RenderType.text(new ResourceLocation("your_right_texture_path"))), x, y, width, height, 0, 0, 1, 1);
        drawQuad(matrixStack, buffer.getBuffer(RenderType.text(new ResourceLocation("your_top_texture_path"))), x, y, width, height, 0, 0, 1, 1);
        drawQuad(matrixStack, buffer.getBuffer(RenderType.text(new ResourceLocation("your_bottom_texture_path"))), x, y, width, height, 0, 0, 1, 1);

        buffer.endBatch();

        matrixStack.popPose();
    }

    private static void drawQuad(PoseStack matrixStack, VertexConsumer builder, int x, int y, int width, int height, float u, float v, float u2, float v2) {
        int color = 0xFFFFFFFF;

        builder.vertex(matrixStack.last().pose(), x, y + height, 0.0F)
                .color(color, color, color, color)
                .uv(u, v2)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .normal(1.0F, 0.0F, 0.0F)
                .endVertex();

        builder.vertex(matrixStack.last().pose(), x, y, 0.0F)
                .color(color, color, color, color)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .normal(1.0F, 0.0F, 0.0F)
                .endVertex();

        builder.vertex(matrixStack.last().pose(), x + width, y, 0.0F)
                .color(color, color, color, color)
                .uv(u2, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .normal(1.0F, 0.0F, 0.0F)
                .endVertex();

        builder.vertex(matrixStack.last().pose(), x + width, y + height, 0.0F)
                .color(color, color, color, color)
                .uv(u2, v2)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .normal(1.0F, 0.0F, 0.0F)
                .endVertex();
    }
    
}
