package whocraft.tardis_refined.client.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Transformation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.GravityUtil;
import whocraft.tardis_refined.constants.ModMessages;

public class GravityOverlay {

    private static final int DISPLAY_TIME = 100; // 5 seconds @ 20 TPS

    private static boolean wasInShaft = false;
    private static int timer = 0;

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null)
            return;

        boolean inShaft = GravityUtil.isInGravityShaft(player);

        // Just entered the shaft
        if (inShaft && !wasInShaft) {
            timer = DISPLAY_TIME;
        }

        wasInShaft = inShaft;

        if (timer > 0) {
            timer--;
        }
    }

    public static void renderOverlay(GuiGraphics graphics) {
        tick();

        if (timer <= 0)
            return;

        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        float alpha = 1.0f;

        if (timer > DISPLAY_TIME - 10)
            alpha = (DISPLAY_TIME - timer) / 10f;
        else if (timer < 10)
            alpha = timer / 10f;

        String title = "Gravity Shaft";
        String line1 = "[" + mc.options.keyJump.getTranslatedKeyMessage().getString() + "] Ascend";
        String line2 = "[" + mc.options.keyShift.getTranslatedKeyMessage().getString() + "] Descend";

        int padding = 6;
        int lineSpacing = 11;

        int width = Math.max(
                font.width(title),
                Math.max(font.width(line1), font.width(line2))
        ) + padding * 2;

        int height = padding * 2 + lineSpacing * 3;

        int x = 8;
        int y = 8;

        int bg = ((int) (alpha * 170) << 24);
        int border = ((int) (alpha * 255) << 24) | 0x5A8CFF;
        int titleColor = ((int) (alpha * 255) << 24) | 0x8FC8FF;
        int textColor = ((int) (alpha * 255) << 24) | 0xFFFFFF;

        // Background
        graphics.fill(x, y, x + width, y + height, bg);

        // Border
        graphics.fill(x, y, x + width, y + 1, border);
        graphics.fill(x, y + height - 1, x + width, y + height, border);
        graphics.fill(x, y, x + 1, y + height, border);
        graphics.fill(x + width - 1, y, x + width, y + height, border);

        int textX = x + padding;
        int textY = y + padding;

        graphics.drawString(font, title, textX, textY, titleColor, true);
        graphics.drawString(font, line1, textX, textY + lineSpacing, textColor, true);
        graphics.drawString(font, line2, textX, textY + lineSpacing * 2, textColor, true);
    }
}