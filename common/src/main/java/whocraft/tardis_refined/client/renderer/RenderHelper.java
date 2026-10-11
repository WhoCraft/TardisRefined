package whocraft.tardis_refined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class RenderHelper {

    public static void renderFilledBox(PoseStack stack, VertexConsumer vertexConsumer, AABB box, float red, float green, float blue, float alpha, int combinedLightIn) {
        add(vertexConsumer, stack, box.minX, box.maxY, box.minZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.minX, box.maxY, box.maxZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.maxY, box.minZ, red, green, blue, alpha, combinedLightIn);

        add(vertexConsumer, stack, box.minX, box.minY, box.minZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.minY, box.minZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.minY, box.maxZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.minX, box.minY, box.maxZ, red, green, blue, alpha, combinedLightIn);

        add(vertexConsumer, stack, box.minX, box.minY, box.minZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.minX, box.maxY, box.minZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.maxY, box.minZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.minY, box.minZ, red, green, blue, alpha, combinedLightIn);

        add(vertexConsumer, stack, box.minX, box.minY, box.maxZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.minY, box.maxZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.minX, box.maxY, box.maxZ, red, green, blue, alpha, combinedLightIn);

        add(vertexConsumer, stack, box.maxX, box.minY, box.minZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.maxY, box.minZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.maxX, box.minY, box.maxZ, red, green, blue, alpha, combinedLightIn);

        add(vertexConsumer, stack, box.minX, box.minY, box.minZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.minX, box.minY, box.maxZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.minX, box.maxY, box.maxZ, red, green, blue, alpha, combinedLightIn);
        add(vertexConsumer, stack, box.minX, box.maxY, box.minZ, red, green, blue, alpha, combinedLightIn);
    }

    private static void add(VertexConsumer consumer, PoseStack stack, double x, double y, double z,
                            float red, float green, float blue, float alpha, int light) {
        consumer.addVertex(stack.last(), (float) x, (float) y, (float) z)
                .setColor(red, green, blue, alpha).setLight(light);
    }


    public static void drawGlowingBox(PoseStack poseStack, VertexConsumer consumer, float length, float height, float width, float red, float green, float blue, float alpha, int combinedLightIn) {
        AABB box = new AABB(-length / 2F, -height / 2f, -width / 2F, length / 2F, height / 2f, width / 2F);
        renderFilledBox(poseStack, consumer, box, 1F, 1F, 1F, alpha, combinedLightIn);

        for (int i = 0; i < 3; i++) {
            renderFilledBox(poseStack, consumer, box.inflate(i * 0.5F * 0.0625F), red, green, blue, alpha, combinedLightIn);
        }
    }

    public static void rotateZYX(PoseStack poseStack, float x, float y, float z) {
        poseStack.rotate((new Quaternionf()).rotationZYX(Mth.DEG_TO_RAD * z, Mth.DEG_TO_RAD * y, Mth.DEG_TO_RAD * x));
    }

    public static void vertexUVColor(@NotNull VertexConsumer consumer, @NotNull PoseStack pose, float x, float y, float z, float u, float v, float r, float g, float b, float a) {
        consumer.addVertex(pose.last(), x, y, z).setUv(u, v).setColor(r, g, b, a);
    }

    public static class CustomProgressBar {
        private final Identifier texture;

        private final int height, width, textureWidth, textureHeight;
        private final int framesU, framesV, mspf;
        public boolean animate = true;
        private int anim = 1;
        private long last_frame;
        private boolean started = false;

        public CustomProgressBar(Identifier texture, int texWidth, int texHeight, int height, int width, int fps) {
            this.texture = texture;
            this.textureWidth = texWidth;
            this.textureHeight = texHeight;
            this.height = height;
            this.width = width;
            this.mspf = 1000 / fps;
            this.framesU = texHeight / height;
            this.framesV = texWidth / width;
        }

        public void blit(GuiGraphicsExtractor gg, int x, int y, double progress) {
            if (!started) {
                started = true;
                last_frame = System.currentTimeMillis();
            }

            int u = anim / framesU;
            int v = anim % framesU;

            // 26.3's extractor overload takes source pixel coordinates and
            // dimensions. The legacy normalized UV overload stretches the
            // animated fuel atlas into a screen-sized purple panel.
            gg.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, 0, 0, width, height,
                    width, height, textureWidth, textureHeight, net.minecraft.util.ARGB.white(1.0f));
            gg.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, width * u, height * v,
                    (int) (width * progress), height, (int) (width * progress), height,
                    textureWidth, textureHeight, net.minecraft.util.ARGB.white(1.0f));

            if (System.currentTimeMillis() - last_frame > mspf) {
                anim++;
                last_frame = System.currentTimeMillis();
            }
            if (anim >= framesU * framesV || !animate) anim = 1;
        }
    }


    public static class DynamicTimeKeep {
        public double speed = 1f;
        private long time = 0L;
        private long last_time = System.currentTimeMillis();

        public DynamicTimeKeep() {
            this.last_time = System.currentTimeMillis();
        }

        public DynamicTimeKeep(double speed) {
            this.speed = speed;
            this.last_time = System.currentTimeMillis();
        }

        public void update() {
            long diff = System.currentTimeMillis() - this.last_time;
            diff *= speed;
            this.time += diff;
            this.last_time = System.currentTimeMillis();
        }

        public float getFloat() {
            return (this.time % 1000L) / 1000.0f;
        }

        public double getDouble() {
            return (this.time % 1000L) / 1000.0;
        }

        public float getFloat(float offset) {
            long offsetTime = this.time + (long) (offset * 1000);
            return (offsetTime % 1000L) / 1000.0f;
        }

        public double getDouble(double offset) {
            long offsetTime = this.time + (long) (offset * 1000);
            return (offsetTime % 1000L) / 1000.0f;
        }

        public void speedUp(double amount) {
            this.speed += amount;
        }

        public void slowDown(double amount) {
            this.speed -= amount;
        }
    }
}
