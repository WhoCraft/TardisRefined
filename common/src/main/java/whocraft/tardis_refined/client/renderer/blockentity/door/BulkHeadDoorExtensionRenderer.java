package whocraft.tardis_refined.client.renderer.blockentity.door;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.door.interior.BulkHeadDoorModel;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorExtensionBlockEntity;

public class BulkHeadDoorExtensionRenderer implements BlockEntityRenderer<BulkHeadDoorExtensionBlockEntity>, BlockEntityRendererProvider<BulkHeadDoorExtensionBlockEntity> {

    public BulkHeadDoorExtensionRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public boolean shouldRenderOffScreen(BulkHeadDoorExtensionBlockEntity blockEntity) {
        return false;
    }

    @Override
    public void render(BulkHeadDoorExtensionBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {

    }

    @Override
    public BlockEntityRenderer<BulkHeadDoorExtensionBlockEntity> create(BlockEntityRendererProvider.Context context) {
        return new BulkHeadDoorExtensionRenderer(context);
    }
}
