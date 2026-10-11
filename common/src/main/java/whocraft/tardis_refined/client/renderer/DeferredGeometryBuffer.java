package whocraft.tardis_refined.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Records legacy vertex calls and replays them as queued 26.3 custom geometry. */
public final class DeferredGeometryBuffer {
    private final Map<RenderType, RecordingConsumer> layers = new LinkedHashMap<>();

    public VertexConsumer getBuffer(RenderType type) {
        return getBuffer(type, 1.0f);
    }

    /** Records a layer with a vertex-color multiplier while preserving texture alpha. */
    public VertexConsumer getBuffer(RenderType type, float brightness) {
        return layers.computeIfAbsent(type, ignored -> new RecordingConsumer(brightness));
    }

    /** Returns a compact summary of the model-space geometry captured this frame. */
    public String geometrySummary() {
        int vertexCount = 0;
        float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY, minZ = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY, maxZ = Float.NEGATIVE_INFINITY;
        for (RecordingConsumer consumer : layers.values()) {
            vertexCount += consumer.vertices.size();
            for (MutableVertex vertex : consumer.vertices) {
                minX = Math.min(minX, vertex.x); minY = Math.min(minY, vertex.y); minZ = Math.min(minZ, vertex.z);
                maxX = Math.max(maxX, vertex.x); maxY = Math.max(maxY, vertex.y); maxZ = Math.max(maxZ, vertex.z);
            }
        }
        if (vertexCount == 0) return "vertices=0";
        return "vertices=" + vertexCount + ", bounds=[" + minX + "," + minY + "," + minZ
                + " -> " + maxX + "," + maxY + "," + maxZ + "]";
    }

    public void submit(SubmitNodeCollector collector, PoseStack poseStack) {
        layers.forEach((type, consumer) -> {
            if (consumer.vertices.isEmpty()) return;
            List<Vertex> vertices = consumer.vertices.stream().map(MutableVertex::freeze).toList();
            collector.submitCustomGeometry(poseStack, type, (pose, output) -> {
                for (Vertex vertex : vertices) {
                    Vector3f position = pose.pose().transformPosition(vertex.x, vertex.y, vertex.z, new Vector3f());
                    Vector3f normal = pose.normal().transform(vertex.normalX, vertex.normalY, vertex.normalZ, new Vector3f()).normalize();
                    output.addVertex(position.x, position.y, position.z)
                            .setColor(vertex.color)
                            .setUv(vertex.u, vertex.v)
                            .setUv1(vertex.overlayU, vertex.overlayV)
                            .setUv2(vertex.lightU, vertex.lightV)
                            .setUv3(vertex.extraUvU, vertex.extraUvV)
                            .setNormal(normal.x, normal.y, normal.z);
                }
            });
        });
    }

    private static final class RecordingConsumer implements VertexConsumer {
        private final List<MutableVertex> vertices = new ArrayList<>();
        private final float brightness;
        private int color = -1, overlayU, overlayV, lightU, lightV;
        private float u, v, extraUvU, extraUvV, normalX, normalY, normalZ;

        private RecordingConsumer(float brightness) {
            this.brightness = Math.max(1.0f, brightness);
        }

        private int brighten(int color) {
            int alpha = color >>> 24;
            int red = Math.min(255, Math.round(((color >>> 16) & 255) * brightness));
            int green = Math.min(255, Math.round(((color >>> 8) & 255) * brightness));
            int blue = Math.min(255, Math.round((color & 255) * brightness));
            return alpha << 24 | red << 16 | green << 8 | blue;
        }

        @Override public VertexConsumer addVertex(float x, float y, float z) {
            vertices.add(new MutableVertex(x, y, z, color, u, v, overlayU, overlayV, lightU, lightV,
                    extraUvU, extraUvV, normalX, normalY, normalZ));
            return this;
        }
        @Override public VertexConsumer setColor(int r, int g, int b, int a) {
            color = brighten((a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | (b & 255));
            if (!vertices.isEmpty()) vertices.getLast().color = color;
            return this;
        }
        @Override public VertexConsumer setColor(int color) {
            this.color = brighten(color);
            if (!vertices.isEmpty()) vertices.getLast().color = color;
            return this;
        }
        @Override public VertexConsumer setUv(float u, float v) {
            this.u = u; this.v = v;
            if (!vertices.isEmpty()) { vertices.getLast().u = u; vertices.getLast().v = v; }
            return this;
        }
        @Override public VertexConsumer setUv1(int u, int v) {
            overlayU = u; overlayV = v;
            if (!vertices.isEmpty()) { vertices.getLast().overlayU = u; vertices.getLast().overlayV = v; }
            return this;
        }
        @Override public VertexConsumer setUv2(int u, int v) {
            lightU = u; lightV = v;
            if (!vertices.isEmpty()) { vertices.getLast().lightU = u; vertices.getLast().lightV = v; }
            return this;
        }
        @Override public VertexConsumer setUv3(float u, float v) {
            extraUvU = u; extraUvV = v;
            if (!vertices.isEmpty()) { vertices.getLast().extraUvU = u; vertices.getLast().extraUvV = v; }
            return this;
        }
        @Override public VertexConsumer setNormal(float x, float y, float z) {
            normalX = x; normalY = y; normalZ = z;
            if (!vertices.isEmpty()) { vertices.getLast().normalX = x; vertices.getLast().normalY = y; vertices.getLast().normalZ = z; }
            return this;
        }
        @Override public VertexConsumer setLineWidth(float width) { return this; }
    }

    private static final class MutableVertex {
        private final float x, y, z;
        private int color, overlayU, overlayV, lightU, lightV;
        private float u, v, extraUvU, extraUvV, normalX, normalY, normalZ;

        private MutableVertex(float x, float y, float z, int color, float u, float v,
                              int overlayU, int overlayV, int lightU, int lightV,
                              float extraUvU, float extraUvV, float normalX, float normalY, float normalZ) {
            this.x = x; this.y = y; this.z = z; this.color = color; this.u = u; this.v = v;
            this.overlayU = overlayU; this.overlayV = overlayV; this.lightU = lightU; this.lightV = lightV;
            this.extraUvU = extraUvU; this.extraUvV = extraUvV; this.normalX = normalX; this.normalY = normalY; this.normalZ = normalZ;
        }

        private Vertex freeze() {
            return new Vertex(x, y, z, color, u, v, overlayU, overlayV, lightU, lightV,
                    extraUvU, extraUvV, normalX, normalY, normalZ);
        }
    }

    private record Vertex(float x, float y, float z, int color, float u, float v,
                          int overlayU, int overlayV, int lightU, int lightV,
                          float extraUvU, float extraUvV, float normalX, float normalY, float normalZ) {}
}
