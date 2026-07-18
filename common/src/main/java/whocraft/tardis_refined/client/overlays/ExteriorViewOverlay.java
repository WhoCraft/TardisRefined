package whocraft.tardis_refined.client.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TRKeybinds;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.renderer.RenderHelper;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.constants.ModMessages;

public class ExteriorViewOverlay {

    public static final ResourceLocation BAR_TEXTURE      = new ResourceLocation(TardisRefined.MODID, "textures/gui/bar/journey_bar.png");
    public static final ResourceLocation FUEL_BAR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/gui/bar/fuel_bar.png");
    public static boolean shouldRender = true;

    private static final RenderHelper.CustomProgressBar PROGRESS_BAR = new RenderHelper.CustomProgressBar(BAR_TEXTURE, 256, 256, 5, 182, 60);
    public static  final RenderHelper.CustomProgressBar FUEL_BAR     = new RenderHelper.CustomProgressBar(FUEL_BAR_TEXTURE, 256, 256, 11, 127, 60);

    private static float danger = 0.0f;

    public static void renderOverlay(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();

        if (!shouldRender) return;

        TardisPlayerInfo.get(mc.player).ifPresent(info -> {
            if (!info.isViewingTardis()) return;

            TardisClientData data = TardisClientData.getInstance(info.getPlayerPreviousPos().getDimensionKey());
            Font font             = mc.font;
            int sw                = mc.getWindow().getGuiScaledWidth();
            int sh                = mc.getWindow().getGuiScaledHeight();

            updateDanger(mc.player);

            int remainingFuel  = (int) data.getFuel();
            int maxFuel        = (int) data.getMaximumFuel();
            float fuelFraction = maxFuel > 0 ? (float) remainingFuel / maxFuel : 0f;
            int fuelPct        = (int) (fuelFraction * 100);

            int throttleStage = data.getThrottleStage();
            int maxThrottle   = TardisPilotingManager.MAX_THROTTLE_STAGE;

            renderScanlines(guiGraphics, sw, sh);
            renderCorners(guiGraphics, sw, sh);

            renderStatusPanel(guiGraphics, font, fuelFraction, fuelPct, throttleStage, maxThrottle, data.isFlying());

            if (!data.isFlying()) {
                renderCoordsPanel(guiGraphics, font, sw, mc.player.blockPosition());
            }

            if (data.isFlying()) {
                float progress = Mth.clamp(data.getJourneyProgress() / 100f, 0f, 1f);
                renderJourneyProgressBar(guiGraphics, progress, sw, sh);
            }

            if (mc.screen == null) {
                renderExitHint(guiGraphics, font, sw, sh);
            }

            renderVortex(guiGraphics, sw / 2, sh);

    });
    }

    private static void updateDanger(net.minecraft.world.entity.player.Player player) {
        float target = 0.0f;

        float hp = player.getHealth() / player.getMaxHealth();
        if (hp < 0.5f) target += (1.0f - hp);

        if (player.isOnFire()) target += 0.7f;

        if (player.level().getFluidState(player.blockPosition()).is(FluidTags.LAVA)) {
            target += 1.2f;
        }

        danger += (target - danger) * 0.08f;
    }

    static void renderVortex(GuiGraphics gui, int cx, int h) {
        int t = Minecraft.getInstance().player.tickCount;

        for (int i = 0; i < 10; i++) {
            int r = (int) ((t * (2 + danger * 6) + i * 18) % 220);
            drawRing(gui, cx, h - 10, r, 0x2200FFAA);
        }
    }

    private static void renderScanlines(GuiGraphics g, int w, int h) {
        for (int y = 0; y < h; y += 3) {
            int alpha = (int) (0x14 + danger * 60);
            alpha = Math.min(alpha, 180);
            int col = (alpha << 24) | 0x00FFAA;
            g.fill(0, y, w, y + 2, col);
        }
    }

    private static void renderCorners(GuiGraphics g, int w, int h) {
        int pulse = (int) (85 + danger * 140);
        int c = (pulse << 24) | 0xAAFFAA;
        int l = 18;

        g.fill(16, 16, 16 + l, 17, c);
        g.fill(16, 16, 17, 16 + l, c);
        g.fill(w - 16 - l, 16, w - 16, 17, c);
        g.fill(w - 17, 16, w - 16, 16 + l, c);
        g.fill(16, h - 17, 16 + l, h - 16, c);
        g.fill(16, h - 16 - l, 17, h - 16, c);
        g.fill(w - 16 - l, h - 17, w - 16, h - 16, c);
        g.fill(w - 17, h - 16 - l, w - 16, h - 16, c);
    }

    private static void renderStatusPanel(GuiGraphics g, Font font,
                                          float fuelFraction, int fuelPct,
                                          int throttleStage, int maxThrottle,
                                          boolean flying) {
        int x = 20;
        int y = 20;

        int throttlePct = maxThrottle > 0 ? (int) ((double) throttleStage / maxThrottle * 100) : 0;
        int fuelColor   = danger > 0.5f ? 0xFF5555 : 0x66FFCC;
        int textColor   = danger > 0.5f ? 0xFF6666 : 0x88FFCC;

        g.drawString(font, "FUEL: " + fuelPct + "%",         x, y,      fuelColor, false);
        g.drawString(font, "THROTTLE: " + throttlePct + "%", x, y + 10, textColor, false);
    }

    private static void renderCoordsPanel(GuiGraphics g, Font font, int sw, BlockPos pos) {
        int x = 20;
        int y = 20 + font.lineHeight * 2 + 6;

        int c = danger > 0.5f ? 0xFF6666 : 0x88FFCC;

        g.drawString(font, "X " + pos.getX(), x, y,      c, false);
        g.drawString(font, "Y " + pos.getY(), x, y + 10, c, false);
        g.drawString(font, "Z " + pos.getZ(), x, y + 20, c, false);
    }

    public static void renderJourneyProgressBar(GuiGraphics g, float progress, int sw, int sh) {
        Font font = Minecraft.getInstance().font;
        int cx    = sw / 2;

        int t = Minecraft.getInstance().player.tickCount;
        for (int i = 0; i < 10; i++) {
            int r = (int) ((t * (2 + danger * 6) + i * 18) % 220);
            drawRing(g, cx, sh - 10, r, 0x2200FFAA);
        }

        int barW = 182;
        int barX = (sw - barW) / 2;
        int barY = sh - 48;

        PROGRESS_BAR.blit(g, barX, barY, progress);

        String text = String.format("Journey  %.0f%%", progress * 100);
        int textX   = (sw - font.width(text)) / 2;
        int textColor = danger > 0.5f ? 0xFF6666 : 0x88FFCC;
        g.drawString(font, text, textX, barY - font.lineHeight - 2, textColor, false);
    }

    private static void drawRing(GuiGraphics g, int cx, int cy, int r, int color) {
        for (int a = 0; a < 360; a += 8) {
            double rad = Math.toRadians(a);
            int x = (int) (cx + Math.cos(rad) * r);
            int y = (int) (cy + Math.sin(rad) * r * 0.35);
            g.fill(x, y, x + 1, y + 1, color);
        }
    }

    private static void renderExitHint(GuiGraphics g, Font font, int sw, int sh) {
        Component exitKey = TRKeybinds.EXIT_EXTERIOR_VIEW.key.getDisplayName();
        Component msg     = Component.translatable(ModMessages.EXIT_EXTERNAL_VIEW).append(exitKey);
        String    text    = msg.getString();

        int textColor = danger > 0.5f ? 0xFF6666 : 0x88FFCC;
        int textX     = (sw - font.width(text)) / 2;
        g.drawString(font, text, textX, sh - font.lineHeight - 10, textColor, false);
    }


}