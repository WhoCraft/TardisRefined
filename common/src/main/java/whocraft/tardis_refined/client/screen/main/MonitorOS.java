package whocraft.tardis_refined.client.screen.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.ShellPreviewDiagnostics;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.client.renderer.vortex.ShellRenderer;
import whocraft.tardis_refined.client.screen.ScreenHelper;
import whocraft.tardis_refined.client.screen.components.CommonTRWidgets;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.screens.VortexSelectionScreen;
import whocraft.tardis_refined.common.VortexRegistry;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.TRBlockRegistry;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public class MonitorOS extends Screen {

    public static Identifier FRAME = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/monitor/frame_brass.png");
    protected static final int frameWidth = 256, frameHeight = 180;
    protected static final int monitorWidth = 230, monitorHeight = 130;
    public final Identifier backdrop;
    public static Identifier currentVortex = VortexRegistry.VORTEX_DEFERRED_REGISTRY.getKey(VortexRegistry.CLOUDS.get());
    public static Identifier NOISE = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/monitor/noise.png");
    public static Identifier SYMBLS = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/monitor/gallifreyan_symbols.png");

    public MonitorOS LEFT;
    public MonitorOS RIGHT;
    public MonitorOS PREVIOUS;

    public static final Identifier BUTTON_LOCATION = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "save");
    public static final Identifier BCK_LOCATION = Identifier.fromNamespaceAndPath(TardisRefined.MODID, "back");
    public int shakeX, shakeY, age, transitionStartTime = -1;
    public float shakeAlpha;
    private MonitorOSRun onSubmit;
    private MonitorOSRun onCancel;

    public MonitorOS(Component title, Identifier backdrop) {
        super(title);
        this.backdrop = backdrop;
    }

    @Override
    protected void init() {
        super.init();
        ObjectSelectionList<SelectionListEntry> list = createSelectionList();
        if (list != null) this.addRenderableWidget(list);
    }

    public void renderBackground(GuiGraphicsExtractor guiGraphics) {

    }

    public Identifier getPatternForRender(){
        return null;
    }

    public void render2Background(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int hPos = (width - monitorWidth) / 2;
        int vPos = (height - monitorHeight) / 2;

        guiGraphics.enableScissor(0, 0, width, vPos + shakeY);
        guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        guiGraphics.disableScissor();
        guiGraphics.enableScissor(0, vPos + shakeY, hPos + shakeX, height - vPos + shakeY);
        guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        guiGraphics.disableScissor();
        guiGraphics.enableScissor(width - hPos + shakeX, vPos + shakeY, width, height - vPos + shakeY);
        guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        guiGraphics.disableScissor();
        guiGraphics.enableScissor(0, height - vPos + shakeY, width, height);
        guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        guiGraphics.disableScissor();
    }


    public void renderVortex(@NotNull GuiGraphicsExtractor guiGraphics) {
        renderVortex(guiGraphics, 1.0F);
    }

    private void renderVortex(@NotNull GuiGraphicsExtractor guiGraphics, float alpha) {
        if (Minecraft.getInstance().level == null) return;
        int hPos = (width - monitorWidth) / 2;
        int vPos = (height - monitorHeight) / 2;
        guiGraphics.enableScissor(hPos + shakeX, vPos + shakeY, width - hPos + shakeX, height - vPos + shakeY);
        TardisClientData data = TardisClientData.getInstance(Minecraft.getInstance().level.dimension());
        Identifier selectedVortex = this instanceof VortexSelectionScreen
                ? VortexSelectionScreen.currentVortex : data.getVortex();
        // Use the same perspective tunnel mesh as shell view, rendered into the Computer Base
        // monitor's picture-in-picture viewport instead of flattening the surface texture.
        ShellRenderer.renderVortexPreview(guiGraphics, selectedVortex,
                hPos + shakeX, vPos + shakeY, monitorWidth, monitorHeight);
        guiGraphics.disableScissor();
    }

    public void renderBackdrop(@NotNull GuiGraphicsExtractor guiGraphics) {
        renderBackdrop(guiGraphics, 1.0F);
    }

    private void renderBackdrop(@NotNull GuiGraphicsExtractor guiGraphics, float alpha) {
        // The vortex is the screen texture behind the Computer Base UI. VortexSelectionScreen
        // uses its current choice here; all other pages use the user's saved vortex.
        renderVortex(guiGraphics, alpha);
        if (backdrop == null) return;
        int hPos = (width - monitorWidth) / 2;
        int vPos = (height - monitorHeight) / 2;
        // The brown patterned panel occupies the top-left 230x128 pixels of
        // backdrop.png. The remaining 26 columns are transparent atlas space;
        // including them in the UV range shrinks the artwork and lets the
        // vortex show through at the right edge. Crop to the panel itself and
        // map it across the full monitor viewport.
        blitTexture(guiGraphics, backdrop, hPos, vPos, monitorWidth, monitorHeight, 0, 0,
                monitorWidth, 128, 256, 256,
                net.minecraft.util.ARGB.colorFromFloat(alpha, 1, 1, 1));
        int b = height - vPos, r = width - hPos;
        guiGraphics.fill(hPos, vPos, r, b, 0x40000000);
    }

    protected static void blitTexture(GuiGraphicsExtractor graphics, Identifier texture, int x, int y,
                                    int width, int height, float u, float v, int sourceWidth,
                                    int sourceHeight, int textureWidth, int textureHeight, int color) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, u, v, width, height,
                sourceWidth, sourceHeight, textureWidth, textureHeight, color);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        render2Background(guiGraphics, mouseX, mouseY, partialTick);

        int hPos = (width - monitorWidth) / 2;
        int vPos = (height - monitorHeight) / 2;

        guiGraphics.enableScissor(hPos, vPos, width - hPos, height - vPos);

        var poseStack = guiGraphics.pose();
        poseStack.pushMatrix();
        poseStack.translate(shakeX, shakeY);

        int symb = 0 % 64;
        poseStack.pushMatrix();
        poseStack.translate(hPos + 10, vPos + 10);
        poseStack.scale(2, 2);
        poseStack.rotate((float) Math.toRadians((float) (System.currentTimeMillis() % 5400L) / 15L));
        poseStack.translate(-31 / 2f, -31 / 2f);
        blitTexture(guiGraphics, SYMBLS, 0, 0, 32, 32, 32 * (symb % 8), 32 * (symb / 8), 32, 32, 128, 128, -1);
        poseStack.popMatrix();

        symb = 3 % 64;
        poseStack.pushMatrix();
        poseStack.translate(hPos + monitorWidth - 10, vPos + monitorHeight - 10);
        poseStack.scale(2, 2);
        poseStack.rotate((float) Math.toRadians((float) (System.currentTimeMillis() % 5400L) / 15L));
        poseStack.translate(-31 / 2f, -31 / 2f);
        blitTexture(guiGraphics, SYMBLS, 0, 0, 32, 32, 32 * (symb % 8), 32 * (symb / 8), 32, 32, 128, 128, -1);
        poseStack.popMatrix();

        symb = 9 % 64;
        poseStack.pushMatrix();
        poseStack.translate(hPos + 10, vPos + monitorHeight - 10);
        poseStack.scale(2, 2);
        poseStack.rotate((float) Math.toRadians(-(float) (System.currentTimeMillis() % 5400L) / 15L));
        poseStack.translate(-31 / 2f, -31 / 2f);
        blitTexture(guiGraphics, SYMBLS, 0, 0, 32, 32, 32 * (symb % 8), 32 * (symb / 8), 32, 32, 128, 128, -1);
        poseStack.popMatrix();

        symb = 8 % 64;
        poseStack.pushMatrix();
        poseStack.translate(hPos + monitorWidth - 10, vPos + 10);
        poseStack.scale(2, 2);
        poseStack.rotate((float) Math.toRadians(-(float) (System.currentTimeMillis() % 5400L) / 15L));
        poseStack.translate(-31 / 2f, -31 / 2f);
        blitTexture(guiGraphics, SYMBLS, 0, 0, 32, 32, 32 * (symb % 8), 32 * (symb / 8), 32, 32, 128, 128, -1);
        poseStack.popMatrix();

        boolean right = RIGHT != null && PREVIOUS != null && RIGHT == PREVIOUS && transitionStartTime >= 0;
        boolean left = LEFT != null && PREVIOUS != null && LEFT == PREVIOUS && transitionStartTime >= 0;
        float t = (age - transitionStartTime + partialTick) / 10f;
        float o = -0.5f * Mth.cos(Mth.PI * t) + 0.5f;

        renderBackdrop(guiGraphics, right || left ? o : 1.0F);

        if (right) {
            RIGHT.renderBackdrop(guiGraphics, 1.0F - o);
            poseStack.translate(monitorWidth * o, 0);
            RIGHT.doRender(guiGraphics, mouseX, mouseY, partialTick);
        }

        if (left) {
            LEFT.renderBackdrop(guiGraphics, 1.0F - o);
            poseStack.translate(-monitorWidth * o, 0);
            LEFT.doRender(guiGraphics, mouseX, mouseY, partialTick);
        }

        if (right || left) poseStack.translate(right ? -monitorWidth : monitorWidth, 0);

        doRender(guiGraphics, mouseX, mouseY, partialTick);

        poseStack.popMatrix();

        guiGraphics.disableScissor();

        // Static is a crash effect. A 1% noise layer was being composited onto
        // every Computer Base page even when the TARDIS was healthy, causing a
        // brief whole-screen flicker during page changes. Keep the effect only
        // while crash shake is active.
        if (shakeAlpha > 0.0F) {
            blitTexture(guiGraphics, NOISE, hPos + shakeX, vPos + shakeY, monitorWidth, monitorHeight,
                    (int) (Math.random() * 736), (int) (414 * (System.currentTimeMillis() % 1000) / 1000.0),
                    monitorWidth, monitorHeight, 736, 414,
                    net.minecraft.util.ARGB.colorFromFloat((shakeAlpha - partialTick) / 100.0f, 1, 1, 1));
        }

        renderFrame(guiGraphics, mouseX, mouseY, partialTick);

    }

    public void doRender(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        inMonitorRender(guiGraphics, mouseX, mouseY, partialTick);

        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        ScreenHelper.renderWidthScaledText(title.getString(), guiGraphics, Minecraft.getInstance().font, width / 2f, 5 + (height - monitorHeight) / 2f, Color.LIGHT_GRAY.getRGB(), 300, true);
    }

    public void inMonitorRender(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
    }

    public void renderFrame(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int hPos = (width - frameWidth) / 2;
        int vPos = -13 + (height - monitorHeight) / 2;

        blitTexture(guiGraphics, FRAME, hPos + shakeX, vPos + shakeY, frameWidth, frameHeight,
                0, 0, frameWidth, frameHeight, 256, 256, -1);
    }

    @Override
    public void tick() {
        super.tick();
        this.age++;
        if (transitionStartTime >= 0 && age - transitionStartTime >= 10) transitionStartTime = -1;

        if (minecraft == null || minecraft.level == null) return;
        boolean isCrashed = TardisClientData.getInstance(minecraft.level.dimension()).isCrashing();

        this.shakeAlpha--;

        if (isCrashed) this.shakeAlpha = 50;
        if (shakeAlpha < 0) shakeAlpha = 0;

        if (shakeAlpha > 0) {
            this.shakeX = (int) (this.shakeAlpha * (Math.random() - 0.5) * 0.5);
            this.shakeY = (int) (this.shakeAlpha * (Math.random() - 0.5) * 0.5);
        }
    }

    public void switchScreenToLeft(MonitorOS next) {
        this.LEFT = next;
        next.PREVIOUS = this;
        next.RIGHT = this;
        next.transition();
        Minecraft.getInstance().setScreenAndShow(next);
    }

    public void switchScreenToRight(MonitorOS next) {
        this.RIGHT = next;
        next.PREVIOUS = this;
        next.LEFT = this;
        next.transition();
        Minecraft.getInstance().setScreenAndShow(next);
    }

    public void transition() {
        transitionStartTime = age;
    }

    public void setEvents(MonitorOSRun onSubmit, MonitorOSRun onCancel) {
        this.onSubmit = onSubmit;
        this.onCancel = onCancel;
    }

    public Button addSubmitButton(int x, int y) {
        if (onSubmit != null) {
            Button spriteiconbutton = this.addRenderableWidget(CommonTRWidgets.imageButton(20, Component.translatable(ModMessages.SUBMIT), (arg) -> this.onSubmit.onPress(), true, BUTTON_LOCATION));
            spriteiconbutton.setPosition(x, y);
            return spriteiconbutton;
        }
        return null;
    }

    public void addCancelButton(int x, int y) {
        if (onCancel != null) {
            Button spriteiconbutton = this.addRenderableWidget(CommonTRWidgets.imageButton(20, Component.translatable("Cancel"), (arg) -> this.onCancel.onPress(), true, BCK_LOCATION));
            spriteiconbutton.setPosition(x, y);
        }
    }

    public ObjectSelectionList<SelectionListEntry> createSelectionList() {
        return null;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public interface MonitorOSRun {
        void onPress();
    }

    public static class MonitorOSExtension extends MonitorOS {
        protected final Identifier currentShellPattern;

        public MonitorOSExtension(Component title, Identifier currentShellThem) {
            this(title, currentShellThem, null);
        }

        public MonitorOSExtension(Component title, Identifier currentShellThem, Identifier currentShellPattern) {
            super(title, null);
            CURRENTSHELLTHEME = currentShellThem;
            this.currentShellPattern = currentShellPattern;
            PATTERNCOLLECTION = ShellPatterns.getPatternCollectionForTheme(CURRENTSHELLTHEME);
            THEMELIST = ShellTheme.SHELL_THEME_DEFERRED_REGISTRY.keySet().stream().toList();
            generateDummyGlobalShell();
        }

        @Override
        protected void init() {
            super.init();
            if (CURRENTSHELLTHEME == null) CURRENTSHELLTHEME = THEMELIST.get(0);
        }

        public static GlobalShellBlockEntity GLOBALSHELL_BLOCKENTITY;
        public static Identifier CURRENTSHELLTHEME;
        public static List<Identifier> THEMELIST;
        public static List<ShellPattern> PATTERNCOLLECTION;

        public void renderShell(GuiGraphicsExtractor guiGraphics, int x, int y, float scale) {
            Identifier patternId = getPatternForRender();
            if (patternId == null) patternId = currentShellPattern;
            ShellPattern pattern = ShellPatterns.getPatternOrDefault(CURRENTSHELLTHEME, patternId);
            ShellPreviewDiagnostics.report("monitor-shell",
                    "theme=" + CURRENTSHELLTHEME + ", requestedPattern=" + patternId
                            + ", resolvedPattern=" + pattern.id());
            ShellRenderer.renderSelectedShell(guiGraphics, CURRENTSHELLTHEME, pattern.id(), x, y, scale);
        }

        public static void generateDummyGlobalShell() {
            GLOBALSHELL_BLOCKENTITY = new GlobalShellBlockEntity(BlockPos.ZERO, TRBlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState());
            assert Minecraft.getInstance().level != null;
            GLOBALSHELL_BLOCKENTITY.setLevel(Minecraft.getInstance().level);
            ResourceKey<Level> generatedLevelKey = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(TardisRefined.MODID, UUID.randomUUID().toString()));
            GLOBALSHELL_BLOCKENTITY.setTardisId(generatedLevelKey);
            GLOBALSHELL_BLOCKENTITY.setShellTheme(ShellTheme.POLICE_BOX.getId());
            GLOBALSHELL_BLOCKENTITY.setPattern(ShellPatterns.DEFAULT);
        }
    }
}
