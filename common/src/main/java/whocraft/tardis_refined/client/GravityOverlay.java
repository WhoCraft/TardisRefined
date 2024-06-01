package whocraft.tardis_refined.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Transformation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import whocraft.tardis_refined.common.GravityUtil;
import whocraft.tardis_refined.constants.ModMessages;

public class GravityOverlay {

    public static void renderOverlay(PoseStack poseStack) {
        Minecraft mc = Minecraft.getInstance();
        Font fontRenderer = mc.font;

        if (GravityUtil.isInGravityShaft(mc.player) && !mc.options.hideGui) {
            poseStack.pushPose();
            poseStack.scale(1.2f, 1.2f, 1.2f);

            int x = 5;
            int y = 5;

            MutableComponent ascendKey = Component.translatable(mc.options.keyJump.getDefaultKey().getName());
            MutableComponent descendKey = Component.translatable(mc.options.keyShift.getDefaultKey().getName());

            MultiBufferSource.BufferSource renderImpl = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            fontRenderer.drawInBatch(Component.translatable(ModMessages.ASCEND_KEY, ascendKey).getString(), x, y, ChatFormatting.WHITE.getColor(), true, Transformation.identity().getMatrix(), renderImpl, Font.DisplayMode.NORMAL, 0, 15728880);
            fontRenderer.drawInBatch(Component.translatable(Component.translatable(ModMessages.DESCEND_KEY, descendKey).getString()), x, y + fontRenderer.lineHeight, ChatFormatting.WHITE.getColor(), true, Transformation.identity().getMatrix(), renderImpl, Font.DisplayMode.NORMAL, 0, 15728880);
            renderImpl.endBatch();

            poseStack.popPose();
        }
    }

}
