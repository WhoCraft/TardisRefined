package whocraft.tardis_refined.client.renderer.blockentity.device;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.common.blockentity.device.EngineInterfaceBlockEntity;

public class EngineInterfaceRenderer implements BlockEntityRenderer<EngineInterfaceBlockEntity>, BlockEntityRendererProvider<EngineInterfaceBlockEntity> {

    public EngineInterfaceRenderer(Context context) {
    }

    @Override
    public void render(EngineInterfaceBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        ItemStack component = blockEntity.getComponent();
        poseStack.pushPose();
        poseStack.translate(0.5, 0.4, 0.5);
        poseStack.scale(0.5F, 0.5F, 0.5F);
        Minecraft.getInstance().getItemRenderer().renderStatic(component, ItemDisplayContext.FIXED, i, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, Minecraft.getInstance().level, 0);
        poseStack.popPose();
    }

    @Override
    public BlockEntityRenderer<EngineInterfaceBlockEntity> create(Context context) {
        return new EngineInterfaceRenderer(context);
    }
}
