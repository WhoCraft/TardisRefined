package whocraft.tardis_refined.client.renderer.blockentity.life;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.life.ZeitonGlassModel;
import whocraft.tardis_refined.common.blockentity.life.ZeitonGlassBlockEntity;

public class ZeitonGlassRenderer implements BlockEntityRenderer<ZeitonGlassBlockEntity>, BlockEntityRendererProvider<ZeitonGlassBlockEntity> {

    private final ZeitonGlassModel zeitonGlassModel;

    public ZeitonGlassRenderer(Context context) {
        this.zeitonGlassModel = new ZeitonGlassModel(context.bakeLayer(ModelRegistry.ZEITON_GLASS));
    }

    @Override
    public void render(ZeitonGlassBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
    }

    @Override
    public BlockEntityRenderer<ZeitonGlassBlockEntity> create(Context context) {
        return new ZeitonGlassRenderer(context);
    }
}
