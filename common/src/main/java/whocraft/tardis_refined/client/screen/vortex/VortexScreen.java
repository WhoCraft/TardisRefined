package whocraft.tardis_refined.client.screen.vortex;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.VortexModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.TRBlockRegistry;

import java.util.UUID;

public class VortexScreen extends Screen {

    public static GlobalShellBlockEntity globalShellBlockEntity;
    private double tardisOffsetX = 0.0D, tardisOffsetY = 0.0D;
    private float tardisOffsetZ = 15f;
    public static ResourceLocation VORTEX = new ResourceLocation(TardisRefined.MODID, "textures/gui/vortex.png");
    VortexModel vortexModel;

    private float red;
    private float green;
    private float blue;
    private long startTime;
    private long interval = 20000; //20 seconds

    private double interpolatedX = 0.0D, interpolatedY = 0.0D;
    private static final double SMOOTHING_FACTOR = 0.1D;

    public VortexScreen() {
        super(Component.empty());
        generateDummyGlobalShell();
        vortexModel = new VortexModel(Minecraft.getInstance().getEntityModels().bakeLayer((ModelRegistry.VORTEX)));
        this.startTime = System.currentTimeMillis();
        this.red = 1.0f;
        this.green = 0.0f;
        this.blue = 0.0f;
    }

    @Override
    public void tick() {
        super.tick();
        updateColor();
    }

    public void updateColor() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        float progress = (float) (elapsedTime % interval) / interval;
        float sinValue = (float) Math.sin(progress * Math.PI * 2);
        float mappedValue = (sinValue + 1) / 2;
        this.red = 1.0f * (1 - mappedValue) + 0.0f * mappedValue;
        this.green = 0.0f;
        this.blue = 0.0f * (1 - mappedValue) + 1.0f * mappedValue;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 83 && this.tardisOffsetY > -35.0D)
            this.tardisOffsetY -= 4.0D;
        if (keyCode == 87 && this.tardisOffsetY < 35.0D)
            this.tardisOffsetY += 4.0D;
        if (keyCode == 68 && this.tardisOffsetX > -35.0D)
            this.tardisOffsetX -= 4.0D;
        if (keyCode == 65 && this.tardisOffsetX < 35.0D)
            this.tardisOffsetX += 4.0D;

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public static void generateDummyGlobalShell() {
        globalShellBlockEntity = new GlobalShellBlockEntity(BlockPos.ZERO, TRBlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState());
        assert Minecraft.getInstance().level != null;
        globalShellBlockEntity.setLevel(Minecraft.getInstance().level);
        ResourceKey<Level> generatedLevelKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(TardisRefined.MODID, UUID.randomUUID().toString()));
        globalShellBlockEntity.setTardisId(generatedLevelKey);
        globalShellBlockEntity.setShellTheme(ShellTheme.POLICE_BOX.getId());
        globalShellBlockEntity.setPattern(ShellPatterns.getPatternOrDefault(ShellTheme.POLICE_BOX.getId(), ShellPatterns.DEFAULT.id()));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY == -1) {
            tardisOffsetZ -= 1;
        }

        if (scrollY == 1) {
            tardisOffsetZ += 1;
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    private void renderShell(GuiGraphics guiGraphics, int x, int y, float scale) {
        ShellModel model = ShellModelCollection.getInstance().getShellEntry(globalShellBlockEntity.getShellTheme()).getShellModel(globalShellBlockEntity.pattern());
        model.setDoorPosition(false);
        Lighting.setupForEntityInInventory();
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate((float) x, y, 100);
        pose.scale(-scale, scale, scale);

        long currentTime = System.currentTimeMillis();
        float rotationX = (float) (currentTime % 3600L) / 10.0f; // Adjust the divisor for speed
        float rotationY = (float) (currentTime % 2700L) / 10.0f;
        float rotationZ = (float) (currentTime % 5400L) / 15.0f;

        pose.mulPose(Axis.XN.rotationDegrees(rotationX));
        pose.mulPose(Axis.YN.rotationDegrees(rotationY));
        pose.mulPose(Axis.ZP.rotationDegrees(rotationZ));
        pose.mulPose(Axis.YP.rotationDegrees((float) (currentTime % 5400L) / 10L));
        pose.mulPose(Axis.YP.rotationDegrees(180));

        VertexConsumer vertexConsumer = guiGraphics.bufferSource().getBuffer(model.renderType(model.getShellTexture(globalShellBlockEntity.pattern(), false)));
        model.renderShell(globalShellBlockEntity, false, true, pose, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.flush();
        pose.popPose();
        Lighting.setupFor3DItems();

        Lighting.setupForEntityInInventory();
        pose.pushPose();
        pose.translate((float) width / 2, height / 2 - 400, -100);
        pose.scale(-255, 255, 255);

        VertexConsumer vertexConsumerVVot = guiGraphics.bufferSource().getBuffer(RenderType.entityTranslucent(VORTEX));
        vortexModel.renderToBuffer(pose, vertexConsumerVVot, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.flush();
        pose.popPose();
        Lighting.setupFor3DItems();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        interpolatedX += (mouseX - interpolatedX) * SMOOTHING_FACTOR;
        interpolatedY += (mouseY - interpolatedY) * SMOOTHING_FACTOR;

        renderShell(guiGraphics, (int) interpolatedX, (int) interpolatedY, tardisOffsetZ);
    }
}
