package whocraft.tardis_refined.client.renderer.blockentity.door;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.door.FactoryDoorModel;
import whocraft.tardis_refined.client.model.blockentity.shell.PoliceBoxModel;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class GlobalDoorRenderer implements BlockEntityRenderer<GlobalDoorBlockEntity>, BlockEntityRendererProvider<GlobalDoorBlockEntity> {

    private static FactoryDoorModel factoryDoorModel;
    private static PoliceBoxModel policeBoxModel;

    public GlobalDoorRenderer(BlockEntityRendererProvider.Context context) {
        factoryDoorModel = new FactoryDoorModel(context.bakeLayer((ModelRegistry.FACTORY_DOOR)));
        policeBoxModel = new PoliceBoxModel(context.bakeLayer((ModelRegistry.POLICE_BOX_DOOR)));
    }

    @Override
    public void render(GlobalDoorBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        BlockState blockstate = blockEntity.getBlockState();
        float rotation = ((Direction)blockstate.getValue(GlobalDoorBlock.FACING)).toYRot();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
        ShellTheme theme = blockstate.getValue(GlobalDoorBlock.SHELL);
        boolean isOpen = blockstate.getValue(GlobalDoorBlock.OPEN);

        if (theme == ShellTheme.FACTORY) {
            factoryDoorModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(theme.getInternalDoorTexture())),
                    i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            factoryDoorModel.setDoorPosition(isOpen);
        }

        if (theme == ShellTheme.POLICE_BOX) {

            policeBoxModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(theme.getInternalDoorTexture())),
                    i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            policeBoxModel.setDoorPosition(isOpen);
        }

        poseStack.popPose();
    }

    @Override
    public BlockEntityRenderer<GlobalDoorBlockEntity> create(Context context) {
        return new GlobalDoorRenderer(context);
    }
}
