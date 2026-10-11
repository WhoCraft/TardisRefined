package whocraft.tardis_refined.client.overlays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import whocraft.tardis_refined.client.ShellPreviewDiagnostics;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.renderer.vortex.ShellRenderer;
import whocraft.tardis_refined.client.renderer.vortex.VortexRenderer;
import whocraft.tardis_refined.client.screen.screens.ShellSelectionScreen;
import whocraft.tardis_refined.common.VortexRegistry;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

public final class VortexOverlay {
    public static final VortexRenderer VORTEX = new VortexRenderer(VortexRegistry.CLOUDS.get());
    private static float dematerialization;
    private static float immersion;
    private static double tardisX;
    private static double tardisY;
    private static double velocityX;
    private static double velocityY;
    private static boolean wasTakingOff;
    private static long lastTime = System.currentTimeMillis();
    private static boolean flightViewCaptured;

    private VortexOverlay() {}

    public static void update(GuiGraphicsExtractor graphics) {
        if (ShellSelectionScreen.GLOBALSHELL_BLOCKENTITY == null) {
            ShellSelectionScreen.generateDummyGlobalShell();
        }
    }

    public static void renderOverlay(GuiGraphicsExtractor graphics) {
        TardisPlayerInfo.get(Minecraft.getInstance().player).ifPresent(playerInfo -> {
            TardisClientData data = TardisClientData.getInstance(playerInfo.getPlayerPreviousPos().getDimensionKey());
            if (data == null) return;
            // The PIP scene has its own camera. Do not advance its animation
            // clock or alter the player's view after shell view has closed.
            if (!playerInfo.isViewingTardis()) {
                flightViewCaptured = false;
                return;
            }
            boolean firstFlightView = !flightViewCaptured;
            if (firstFlightView) flightViewCaptured = true;
            boolean moving = data.isTakingOff() || (data.isFlying() && !data.isLanding());
            long now = System.currentTimeMillis();
            float elapsed = Math.max(0, Math.min(100, now - lastTime));
            float delta = elapsed / 12000.0f;
            if (data.isTakingOff() && !wasTakingOff) {
                // The player can open shell view after takeoff has begun. Pick
                // up its synchronized animation clock instead of replaying the
                // still, pre-flight portion from zero.
                dematerialization = Mth.clamp(data.takeOffTime / 240.0f, 0.0f, 1.0f);
                immersion = 0.0f;
            } else if (firstFlightView && data.isFlying() && !data.isLanding()) {
                // Opening the view during an established flight must not replay
                // the takeoff fade and leave the shell motionless for seconds.
                dematerialization = 1.0f;
                immersion = 1.0f;
            }
            dematerialization = Mth.clamp(dematerialization + (moving ? delta : -delta), 0, 1);
            lastTime = now;
            wasTakingOff = data.isTakingOff();
            // The replicated renderVortex flag can outlive a flight transition
            // briefly. Never cover an ordinary exterior shell view with the
            // full-screen cloud texture unless a flight transition is active.
            update(graphics);
            VORTEX.vortexType = VortexRegistry.VORTEX_DEFERRED_REGISTRY.get(data.getVortex());
            VORTEX.time.speed = 0.3 + data.getThrottleStage() * 0.1;
            boolean inFlightSequence = data.isTakingOff() || data.isFlying() || data.isLanding();
            ShellPreviewDiagnostics.reportEvery("takeoff-overlay",
                    "tardis=" + data.getLevelKey().identifier() + ", viewing=" + playerInfo.isViewingTardis()
                            + ", renderVortex=" + playerInfo.isRenderVortex()
                            + ", takingOff=" + data.isTakingOff() + ", flying=" + data.isFlying()
                            + ", landing=" + data.isLanding() + ", takeOffTime=" + data.takeOffTime
                            + ", demat=" + dematerialization + ", immersion=" + immersion
                            + ", vortexRotation=" + flightVortexRotationDegrees()
                            + ", shellMotionControl=" + flightMotionControl()
                            + ", shellSubmitted=" + inFlightSequence + ", playerDimension="
                            + Minecraft.getInstance().player.level().dimension().identifier(), 1000);
            if (inFlightSequence) {
                // Leave the GUI/world backdrop visible behind translucent
                // vortex geometry while diagnosing the black flight view.
                VORTEX.time.update();
                VORTEX.time.speed = 0.3 + data.getThrottleStage() * 0.1;
                // Match the legacy renderer's shell immersion and lightning
                // recoil. The old renderer accumulated a small velocity,
                // displaced the shell inside the tube, then damped it each frame.
                immersion = dematerialization >= 1.0f
                        ? Math.min(1.0f, immersion + elapsed / (1000.0f + 5000.0f * immersion))
                        : immersion * 0.9f;
                // Preserve the legacy shell-view steering controls. They are
                // sampled only while the player is looking through the shell
                // and no other screen is open.
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.gui.screen() == null) {
                    var options = minecraft.options;
                    if (options.keyUp.isDown()) velocityY += 0.01;
                    if (options.keyDown.isDown()) velocityY -= 0.01;
                    if (options.keyLeft.isDown()) velocityX -= 0.01;
                    if (options.keyRight.isDown()) velocityX += 0.01;
                }
                float strike = VORTEX.lightning_strike;
                if (strike > 0.4f) {
                    velocityX -= (Math.random() > 0.5 ? 0.001 : -0.001) * strike * 90.0 * Mth.sin(strike);
                    velocityY -= (Math.random() > 0.5 ? 0.001 : -0.001) * strike * 90.0 * Mth.sin(strike);
                }
                tardisX += velocityX;
                tardisY += velocityY;
                double radius = Math.sqrt(tardisX * tardisX + tardisY * tardisY);
                if (radius > 1.0) {
                    tardisX /= radius;
                    tardisY /= radius;
                }
                velocityX = Mth.clamp(velocityX, -1.0, 1.0);
                velocityY = Mth.clamp(velocityY, -1.0, 1.0);
                velocityX *= 0.9;
                velocityY *= 0.9;
                tardisX *= 0.99;
                tardisY *= 0.99;
            } else {
                immersion *= 0.9f;
                tardisX *= 0.9;
                tardisY *= 0.9;
                velocityX *= 0.8;
                velocityY *= 0.8;
            }

            // 1.20.2 draws the shell copy in the tunnel only after flight has
            // started. During takeoff the real exterior shell is still at its
            // parked location and plays MODEL_TAKEOFF there. Showing the PIP
            // copy during takeoff created a frozen duplicate inside the tube.
            if (inFlightSequence) {
                ShellRenderer.renderShell(graphics, flightMotionControl(), vortexOpacity(), data.getThrottleStage());
            }
        });
    }

    public static boolean isFlightScene(TardisClientData data) {
        return data.isTakingOff() || data.isFlying() || data.isLanding();
    }

    public static Vector3f shellFlightOffset() {
        float motion = flightMotionControl();
        return new Vector3f((float) (4.0 * tardisX * motion), (float) (4.0 * tardisY * motion), 0.0f);
    }

    /** Use the same immersion factor as the legacy vortex shell renderer. */
    private static float flightMotionControl() {
        // Let the in-vortex shell begin moving as soon as takeoff starts.
        // Immersion continues to control established-flight motion, while
        // dematerialization bridges the takeoff interval before immersion.
        return Math.max(dematerialization, immersion);
    }

    public static Quaternionf shellFlightRotation(int throttle) {
        float phase = (System.currentTimeMillis() % 4000L) / 4000.0f * (float) (2.0 * Math.PI);
        float control = flightMotionControl();
        // Keep the legacy renderer's four-second cycle and degree-based rates.
        float xWobble = (float) Math.sin(phase * 2.0f) * 15.0f * control;
        float ySpin = ((phase * 360.0f / (float) (2.0 * Math.PI)) % 360.0f)
                * (1.0f + throttle * 0.5f) * control;
        float zWobble = (float) Math.cos(phase * 3.0f) * 10.0f * control;
        float bank = (float) (-450.0 * control * velocityX);
        float recoil = control * VORTEX.lightning_strike * 90.0f * Mth.sin(VORTEX.lightning_strike);

        // Match the sequence of legacy PoseStack rotations: scene banking and
        // lightning recoil, then the shell's X/Z wobble, then its Y spin.
        Quaternionf rotation = new Quaternionf()
                .rotationZYX((float) Math.toRadians(bank), 0.0f, 0.0f)
                .rotateZ((float) Math.toRadians(recoil));
        rotation.rotateZYX((float) Math.toRadians(zWobble), 0.0f,
                (float) Math.toRadians(xWobble));
        rotation.rotateY((float) Math.toRadians(ySpin));
        return rotation;
    }

    /** Keep the tunnel fully visible from the first takeoff frame. */
    public static float vortexOpacity() {
        return 1.0f;
    }

    /** Axial tunnel rotation is disabled; only its normal texture animation remains. */
    public static float flightVortexRotationDegrees() {
        return 0.0f;
    }

    /** The mesh is already aligned to the PIP's forward axis. */
    public static Quaternionf flightCameraAngle() {
        // This preview camera is already placed inside the vortex tunnel. It
        // must stay fixed; rotating it from the outside player's view made
        // the complete tunnel appear to slide into frame during takeoff.
        return new Quaternionf();
    }
}
