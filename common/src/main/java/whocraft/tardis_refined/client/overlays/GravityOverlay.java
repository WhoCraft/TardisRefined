package whocraft.tardis_refined.client.overlays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.GravityUtil;
import whocraft.tardis_refined.constants.ModMessages;

public final class GravityOverlay {
    private static boolean isInShaft;

    private GravityOverlay() {}

    private static void checkOverlay(Player player) {
        isInShaft = GravityUtil.isInGravityShaft(player);
    }

    public static void renderOverlay(GuiGraphicsExtractor graphics) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        if (player.tickCount % 100 == 0) checkOverlay(player);
        if (!isInShaft) return;

        String ascend = Component.translatable(ModMessages.ASCEND_KEY,
                minecraft.options.keyJump.key.getDisplayName()).getString();
        String descend = Component.translatable(ModMessages.DESCEND_KEY,
                minecraft.options.keyShift.key.getDisplayName()).getString();
        int x = 15;
        int y = 15;
        int width = Math.max(minecraft.font.width(ascend), minecraft.font.width(descend));
        int lineHeight = minecraft.font.lineHeight;
        graphics.fill(x - 5, y - 5, x + width + 5, y + lineHeight * 2 + 5, 0x88000000);
        graphics.text(minecraft.font, ascend, x, y, 0xFFFFFFFF);
        graphics.text(minecraft.font, descend, x, y + lineHeight, 0xFFFFFFFF);
    }
}
