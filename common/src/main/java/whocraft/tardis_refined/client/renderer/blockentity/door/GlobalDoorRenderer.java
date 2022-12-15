package whocraft.tardis_refined.client.renderer.blockentity.door;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.door.FactoryDoorModel;
import whocraft.tardis_refined.client.model.blockentity.door.PhoneBoothDoorModel;
import whocraft.tardis_refined.client.model.blockentity.shell.IShellModel;
import whocraft.tardis_refined.client.model.blockentity.shell.PoliceBoxModel;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class GlobalDoorRenderer implements BlockEntityRenderer<GlobalDoorBlockEntity>, BlockEntityRendererProvider<GlobalDoorBlockEntity> {

    private static IShellModel currentModel, factoryDoorModel, policeBoxModel, phoneBoothDoorModel;

    public GlobalDoorRenderer(BlockEntityRendererProvider.Context context) {
        factoryDoorModel = new FactoryDoorModel(context.bakeLayer((ModelRegistry.FACTORY_DOOR)));
        policeBoxModel = new PoliceBoxModel(context.bakeLayer((ModelRegistry.POLICE_BOX_DOOR)));
        phoneBoothDoorModel = new PhoneBoothDoorModel(context.bakeLayer((ModelRegistry.PHONE_BOOTH_DOOR)));
    }

    @Override
    public void render(GlobalDoorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(GlobalDoorBlock.FACING).toYRot();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
        ShellTheme theme = blockstate.getValue(GlobalDoorBlock.SHELL);
        boolean isOpen = blockstate.getValue(GlobalDoorBlock.OPEN);

        switch (theme) {
            case FACTORY:
                currentModel = factoryDoorModel;
                break;
            case POLICE_BOX:
                currentModel = policeBoxModel;
                break;
            case PHONE_BOOTH:
                currentModel = phoneBoothDoorModel;
                break;
        }
        currentModel.setDoorPosition(isOpen);

        if (currentModel instanceof HierarchicalModel<?> hierarchicalModel) {
            hierarchicalModel.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(theme.getInternalDoorTexture())), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }

        poseStack.popPose();
    }

    @Override
    public BlockEntityRenderer<GlobalDoorBlockEntity> create(Context context) {
        return new GlobalDoorRenderer(context);
    }
}
