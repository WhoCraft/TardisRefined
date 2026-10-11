package whocraft.tardis_refined.client.renderer.blockentity.console;

import whocraft.tardis_refined.client.renderer.LegacyBlockEntityRenderer;
import whocraft.tardis_refined.client.renderer.DeferredGeometryBuffer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.TRShaders;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.ShellPreviewDiagnostics;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleModelCollection;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleUnit;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.client.renderer.RenderHelper;
import whocraft.tardis_refined.client.screen.screens.ShellSelectionScreen;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

public class GlobalConsoleRenderer extends LegacyBlockEntityRenderer<GlobalConsoleBlockEntity> {

    private static final Logger LOGGER = LogManager.getLogger("TardisRefined/ConsoleRenderer");
    private static final AtomicBoolean REFURBISHED_GEOMETRY_REPORTED = new AtomicBoolean();

    private static final Vec3 crystalHolo = new Vec3(0.3f, -1.725, 0.655);
    private static final Vec3 crystalHoloColor = new Vec3(1f, 0.64f, 0f);

    private static final Vec3 initiativeHolo = new Vec3(-1.23, -1.225, 1.775F);
    private static final Vec3 initiativeHoloColor = new Vec3(0, 0.8f, 1f);



    public GlobalConsoleRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderLegacy(GlobalConsoleBlockEntity blockEntity, float partialTick, PoseStack poseStack, DeferredGeometryBuffer bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.rotateDegrees(Axis.ZP, 180F);

        BlockState blockState = blockEntity.getBlockState();
        Level level = blockEntity.getLevel();
        Identifier blockEntityTheme = blockEntity.theme();
        TardisClientData appearance = level == null ? null : TardisClientData.getInstance(level.dimension());
        Identifier cachedTheme = appearance == null ? null : appearance.getConsoleTheme();
        // Treat the Factory constructor default as missing selection data.
        // An explicit non-Factory block-entity theme remains authoritative,
        // but the saved TARDIS selection restores consoles whose BE still has
        // only that default value.
        Identifier factoryTheme = ConsoleTheme.FACTORY.getId();
        Identifier theme = blockEntityTheme != null
                && !factoryTheme.equals(blockEntityTheme)
                && ConsoleTheme.CONSOLE_THEME_DEFERRED_REGISTRY.get(blockEntityTheme) != null
                ? blockEntityTheme
                : cachedTheme != null && !factoryTheme.equals(cachedTheme)
                    ? cachedTheme : blockEntityTheme;

        Identifier cachedPatternId = appearance == null ? null : appearance.getConsolePattern();
        ConsolePattern pattern = resolvePattern(theme, blockEntity.pattern());
        // The block entity's pattern can be stale on the client after a
        // configurator change. The synchronized TARDIS selection is updated
        // directly by setPattern(), so prefer it whenever it belongs to this
        // console theme, even if the stale block-entity pattern has the same
        // theme (as happens when switching Coral's War/Blue variants).
        if (cachedPatternId != null && ConsolePatterns.doesPatternExist(theme, cachedPatternId)) {
            pattern = ConsolePatterns.getPatternOrDefault(theme, cachedPatternId);
        }
        ConsoleUnit consoleModel = ConsoleModelCollection.getInstance().getConsoleEntry(theme).getConsoleModel(pattern);
        Identifier texture = pattern == null ? consoleModel.getDefaultTexture() : pattern.texture();
        consoleModel.renderConsole(blockEntity, level, poseStack, bufferSource.getBuffer(RenderTypes.entityTranslucent(texture)), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        if (level != null) {
            ShellPreviewDiagnostics.report("interior-console-state",
                    "dimension=" + level.dimension().identifier()
                            + ", console=" + theme
                            + ", blockEntityConsole=" + blockEntityTheme
                            + ", cachedConsole=" + cachedTheme
                            + ", pattern=" + (blockEntity.pattern() == null ? "null" : blockEntity.pattern().id())
                            + ", powered=" + blockState.getValue(GlobalConsoleBlock.POWERED)
                            + ", shellTheme=" + appearance.getShellTheme()
                            + ", shellPattern=" + appearance.getShellPattern());
        }

        if (blockEntity != null && blockState.getValue(GlobalConsoleBlock.POWERED)) {
            if (pattern != null && pattern.patternTexture().emissive()) {
                consoleModel.renderConsole(blockEntity, level, poseStack, bufferSource.getBuffer(TRShaders.glow(pattern.emissiveTexture(), 0.5F)), 15728640, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            }
        }

        if (ConsoleTheme.REFURBISHED.getId().equals(theme)
                && REFURBISHED_GEOMETRY_REPORTED.compareAndSet(false, true)) {
            LOGGER.info("Refurbished console captured render geometry: theme={}, blockEntityTheme={}, pattern={}, texture={}, emissive={}, {}",
                    theme, blockEntityTheme, pattern == null ? "default" : pattern.id() + "/" + pattern.getThemeId(),
                    texture, pattern != null && pattern.patternTexture().emissive(), bufferSource.geometrySummary());
        }

        poseStack.popPose();

        if (blockEntity != null && blockState.getValue(GlobalConsoleBlock.POWERED)) {
            if (theme.toString().equals(ConsoleTheme.CRYSTAL.getId().toString())) {
                renderHoloShell(crystalHolo, 270, blockEntity, poseStack, bufferSource, packedLight, crystalHoloColor);
            }

            if (theme.toString().equals(ConsoleTheme.INITIATIVE.getId().toString())) {
                renderHoloShell(initiativeHolo, -30 + 180, blockEntity, poseStack, bufferSource, packedLight, initiativeHoloColor);
            }
        }
    }

    private static ConsolePattern resolvePattern(Identifier theme, ConsolePattern blockEntityPattern) {
        if (blockEntityPattern != null && theme.equals(blockEntityPattern.getThemeId())) {
            return blockEntityPattern;
        }

        var patterns = ConsolePatterns.getPatternsForTheme(theme);
        if (patterns == null || patterns.isEmpty()) {
            return null;
        }

        Identifier preferredPattern = blockEntityPattern == null ? null : blockEntityPattern.id();
        for (ConsolePattern pattern : patterns) {
            if (pattern.id().equals(preferredPattern)) {
                return pattern;
            }
        }
        return patterns.getFirst();
    }

    private void renderHoloShell(Vec3 offset, int rotation, GlobalConsoleBlockEntity blockEntity, PoseStack poseStack, DeferredGeometryBuffer bufferSource, int packedLight, Vec3 color) {
        Level level = blockEntity.getLevel();

        if (level.getRandom().nextInt(20) != 0) {
            poseStack.pushPose();

            // Fetch shell data
            TardisClientData reactions = TardisClientData.getInstance(level.dimension());
            Identifier shellTheme = reactions.getShellTheme();
            Identifier shellPattern = reactions.getShellPattern();
            ShellPattern pattern = ShellPatterns.getPatternOrDefault(shellTheme, shellPattern);

            var model = ShellModelCollection.getInstance().getShellEntry(shellTheme).getShellModel(pattern);
            ShellPreviewDiagnostics.report("interior-console",
                    "dimension=" + level.dimension().identifier()
                            + ", tardis=" + reactions.getLevelKey().identifier()
                            + ", dataTheme=" + shellTheme + ", dataPattern=" + shellPattern
                            + ", resolvedPattern=" + pattern.id()
                            + ", model=" + (model == null ? "null" : model.getClass().getSimpleName()));
            model.setDoorPosition(false);

            // Base rotation and positioning
            poseStack.rotateDegrees(Axis.ZP, 180F);
            poseStack.translate(offset.x, offset.y, offset.z);

            // Add subtle floating animation
            if (reactions.isFlying()) {
                float floatingOffset = (float) Math.sin(level.getGameTime() / 15.0) * 0.05f;
                poseStack.translate(0, floatingOffset, 0);
            }

            // Random subtle jitter
            poseStack.translate(
                    level.getRandom().nextFloat() * 0.005f - 0.0025f,
                    level.getRandom().nextFloat() * 0.005f - 0.0025f,
                    level.getRandom().nextFloat() * 0.005f - 0.0025f
            );

            float scaleModifier = 0.1f + (float) Math.sin(level.getGameTime() / 20.0) * 0.005f;
            poseStack.scale(scaleModifier, scaleModifier, scaleModifier);

            // Add rotation effect
            if (reactions.isFlying()) {
                // Time-based calculations for loop able motion and rotation
                long time = System.currentTimeMillis();
                float timeFactor = (time % 4000L) / 4000.0f * (float) (2 * Math.PI);

                // Chaotic but loop able rotations
                float xR = (float) Math.sin(timeFactor * 2) * 15.0f; // Wobble on X-axis
                float yR = ((timeFactor * 360 / (float) (2 * Math.PI)) % 360) * reactions.getThrottleStage(); // Continuous spin on Y-axis
                float zR = (float) Math.cos(timeFactor * 3) * 10.0f; // Wobble on Z-axis
                int control = 1;
                RenderHelper.rotateZYX(poseStack, xR * control, yR * control, zR * control);

            } else {
                poseStack.rotateDegrees(Axis.YP, rotation % 360);
            }

            if (ShellSelectionScreen.GLOBALSHELL_BLOCKENTITY == null) {
                ShellSelectionScreen.generateDummyGlobalShell();
            }

            ShellSelectionScreen.GLOBALSHELL_BLOCKENTITY.setTardisId(reactions.getLevelKey());
            // The shared preview block entity can retain the factory theme
            // from its initial setup. Keep its appearance in lockstep with
            // the shell selected above before the model reads it.
            ShellSelectionScreen.GLOBALSHELL_BLOCKENTITY.setPreviewAppearance(shellTheme, pattern);

            // Dynamic flickering alpha for a hologram effect
            float flickerAlpha = 0.2f + level.getRandom().nextFloat() * 0.1f;

            boolean recoveryOrCrashing = reactions.isCrashing() || reactions.isInRecovery();

            float time = level.getGameTime() / 100.0f;
            float red = recoveryOrCrashing ? 0.5f + (float) Math.sin(time) * 0.5f : (float) color.x;
            float green = recoveryOrCrashing ? 0.5f + (float) Math.sin(time + Math.PI / 2) * 0.5f : (float) color.y;
            float blue = recoveryOrCrashing ? 0.5f + (float) Math.sin(time + Math.PI) * 0.5f : (float) color.z;

            model.setIgnoreAnmationAlpha(!reactions.isTakingOff() && !reactions.isLanding());
            model.renderShell(
                    ShellSelectionScreen.GLOBALSHELL_BLOCKENTITY,
                    false,
                    true,
                    poseStack,
                    bufferSource.getBuffer(RenderTypes.entityTranslucent(pattern.shellTexture().texture())),
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    red,
                    green,
                    blue,
                    flickerAlpha
            );
            model.setIgnoreAnmationAlpha(false);

            poseStack.popPose();
        }

    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }



}
