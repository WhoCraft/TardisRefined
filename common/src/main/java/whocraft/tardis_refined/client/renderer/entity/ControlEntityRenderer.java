package whocraft.tardis_refined.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.common.entity.ControlEntity;

public class ControlEntityRenderer extends NoopRenderer<ControlEntity> {

    public ControlEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected boolean shouldShowName(ControlEntity entity) {
        return (TRConfig.CLIENT.CONTROL_NAMES.get() && Minecraft.renderNames() && this.entityRenderDispatcher.crosshairPickEntity == entity);
    }


    @Override
    protected void renderNameTag(ControlEntity entity, Component component, PoseStack poseStack, MultiBufferSource multiBufferSource, int textRenderingLayer) {
        MutableComponent textComponent = Component.literal(component.getString());
        textComponent.withStyle(style -> style
                .applyFormats(ChatFormatting.BOLD, ChatFormatting.YELLOW)
        );

        double distanceSquared = this.entityRenderDispatcher.distanceToSqr(entity);
        if (!(distanceSquared > 4096.0)) {
            boolean isSolid = !entity.isDiscrete();
            float boundingBoxHeight = entity.getBbHeight() + 0.5F;
            int verticalTextOffset = 35;
            poseStack.pushPose();
            poseStack.translate(0.0, boundingBoxHeight, 0.0);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());


            float scale = 0.010F;
            poseStack.scale(-scale, -scale, scale);

            Matrix4f textMatrix = poseStack.last().pose();
            float textBackgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.5F);
            ;
            int textColor = (int) (textBackgroundOpacity * 255.0F) << 24;
            Font font = this.getFont();
            float textHorizontalPosition = (float) (-font.width(component) / 2);


            font.drawInBatch(textComponent, textHorizontalPosition, (float) verticalTextOffset, 553648127, false, textMatrix, multiBufferSource, isSolid, textColor, textRenderingLayer);
            if (isSolid) {
                font.drawInBatch(textComponent, textHorizontalPosition, (float) verticalTextOffset, -1, false, textMatrix, multiBufferSource, false, 0, textRenderingLayer);
            }

            poseStack.popPose();
        }


    }
}
