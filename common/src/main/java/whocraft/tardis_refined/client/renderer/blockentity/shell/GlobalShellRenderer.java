package whocraft.tardis_refined.client.renderer.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.shell.*;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class GlobalShellRenderer implements BlockEntityRenderer<GlobalShellBlockEntity>, BlockEntityRendererProvider<GlobalShellBlockEntity> {

    private static IShellModel currentModel, factoryShellModel, policeBoxModel, phoneBoothModel, mysticModel;

    public GlobalShellRenderer(Context context) {
        factoryShellModel = new FactoryShellModel(context.bakeLayer((ModelRegistry.FACTORY_SHELL)));
        policeBoxModel = new PoliceBoxModel(context.bakeLayer((ModelRegistry.POLICE_BOX_SHELL)));
        phoneBoothModel = new PhoneBoothModel(context.bakeLayer((ModelRegistry.PHONE_BOOTH_SHELL)));
        mysticModel = new MysticShellModel(context.bakeLayer((ModelRegistry.MYSTIC_SHELL)));
    }

    @Override
    public void render(GlobalShellBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // Get the current factory shell.

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));

        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(RootedShellBlock.FACING).toYRot();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
        ShellTheme theme = blockstate.getValue(GlobalShellBlock.SHELL);
        boolean isOpen = blockstate.getValue(GlobalShellBlock.OPEN);

        float sine = 0;
        if (blockstate.getValue(ShellBaseBlock.REGEN)) {
            sine = (float) ((Math.sin(0.1 * (blockEntity.getLevel().dayTime())) * 1));
            if (sine < 0) {
                sine = 0;
            }
        }

        currentModel = getModelForTheme(theme);


        currentModel.renderShell(blockEntity, isOpen, true, poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(theme.getExternalShellTexture())), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        /*Emmissive*/
        Boolean isRegenerating = blockstate.getValue(ShellBaseBlock.REGEN);
        if (currentModel.lightTexture() != null) {
            currentModel.renderShell(blockEntity, isOpen, false, poseStack, bufferSource.getBuffer(RenderType.entityTranslucentEmissive(currentModel.lightTexture())), 15728640, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, (isRegenerating) ? sine : 1f);
        } else {
            if (isRegenerating) {
                currentModel.renderShell(blockEntity, isOpen, false, poseStack, bufferSource.getBuffer(RenderType.entityTranslucentEmissive(currentModel.texture())), 15728640, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, sine);
            }
        }

        poseStack.popPose();

    }

    @Override
    public boolean shouldRenderOffScreen(GlobalShellBlockEntity blockEntity) {
        return true;
    }

    @Override
    public BlockEntityRenderer<GlobalShellBlockEntity> create(Context context) {
        return new GlobalShellRenderer(context);
    }

    public static IShellModel getModelForTheme(ShellTheme theme) {
        switch (theme) {
            case PHONE_BOOTH:
                return phoneBoothModel;
            case POLICE_BOX:
                return policeBoxModel;
            case FACTORY:
                return factoryShellModel;
            case MYSTIC:
                return mysticModel;
        }
        return null;
    }
}
