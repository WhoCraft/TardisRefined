package whocraft.tardis_refined.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.joml.Matrix4f;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.common.entity.ControlEntity;

public class ControlEntityRenderer extends NoopRenderer<ControlEntity> {

    public ControlEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected boolean shouldShowName(ControlEntity entity) {
        return (TRConfig.CLIENT.CONTROL_NAMES.get() && Minecraft.renderNames() && isMouseOverEntity(entity));
    }

    private boolean isMouseOverEntity(ControlEntity entity) {
        HitResult hitResult = Minecraft.getInstance().hitResult;
        if (hitResult == null) return false;
        if (hitResult instanceof EntityHitResult entityHitResult) {
            return entityHitResult.getEntity() == entity;
        }
        return false;
    }

    @Override
    public void render(ControlEntity entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (this.shouldShowName(entity)) {
            this.renderNameTag(entity, entity.getDisplayName(), poseStack, multiBufferSource, i);
        }
    }

    @Override
    protected void renderNameTag(ControlEntity entity, Component component, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLightCoords) {
        MutableComponent textComponent = component.copy();
        textComponent.withStyle(style -> style
                .applyFormats(ChatFormatting.BOLD, (entity.level().getGameTime() % 60 == 0) ?  ChatFormatting.GOLD : ChatFormatting.YELLOW)
        );

        double distanceSquared = this.entityRenderDispatcher.distanceToSqr(entity);
        if (!(distanceSquared > 4096.0)) {
            boolean isSolid = !entity.isDiscrete();
            float boundingBoxHeight = entity.getNameTagOffsetY();
            int verticalTextOffset = 35;
            poseStack.pushPose();
            poseStack.translate(0.0, boundingBoxHeight, 0.0);

            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            float scale = 0.010F;
            poseStack.scale(-scale, -scale, scale);

            Matrix4f textMatrix = poseStack.last().pose();
            float textBackgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.5F);

            int textColor = (int) (textBackgroundOpacity * 255.0F) << 24;
            Font font = this.getFont();
            float textHorizontalPosition = (float) (-font.width(component) / 2);

            font.drawInBatch(textComponent, textHorizontalPosition, (float) verticalTextOffset, 553648127, false, textMatrix, multiBufferSource, Font.DisplayMode.SEE_THROUGH, textColor, packedLightCoords);
            if (isSolid) {
                font.drawInBatch(textComponent, textHorizontalPosition, (float) verticalTextOffset, -1, false, textMatrix, multiBufferSource, Font.DisplayMode.NORMAL, 0, packedLightCoords);
            }

            poseStack.popPose();
        }


    }
}
