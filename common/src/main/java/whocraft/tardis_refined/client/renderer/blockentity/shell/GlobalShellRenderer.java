package whocraft.tardis_refined.client.renderer.blockentity.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.shell.FactoryShellModel;
import whocraft.tardis_refined.client.model.blockentity.shell.PhoneBoothModel;
import whocraft.tardis_refined.client.model.blockentity.shell.PoliceBoxModel;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class GlobalShellRenderer implements BlockEntityRenderer<GlobalShellBlockEntity>, BlockEntityRendererProvider<GlobalShellBlockEntity> {

    private static FactoryShellModel factoryShellModel;
    private static PoliceBoxModel policeBoxModel;
    private static PhoneBoothModel phoneBoothModel;

    // Too specific for ShellTheme textures
    private static ResourceLocation policeBoxEmissive = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/tdis_shell_emissive.png");
    private static ResourceLocation phoneBoothEmissive = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/phone_booth_shell_emissive.png");

    public GlobalShellRenderer(Context context) {
        factoryShellModel = new FactoryShellModel(context.bakeLayer((ModelRegistry.FACTORY_SHELL)));
        policeBoxModel = new PoliceBoxModel(context.bakeLayer((ModelRegistry.POLICE_BOX_SHELL)));
        phoneBoothModel = new PhoneBoothModel(context.bakeLayer((ModelRegistry.PHONE_BOOTH_SHELL)));
    }

    @Override
    public void render(GlobalShellBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        // Get the current factory shell.


        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));

        BlockState blockstate = blockEntity.getBlockState();
        float rotation = ((Direction) blockstate.getValue(RootedShellBlock.FACING)).toYRot();
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

        switch (theme) {
            case POLICE_BOX -> {
                poseStack.scale(1.05f, 1.05f, 1.05f);
                poseStack.translate(0, -0.07, 0);
                policeBoxModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(theme.getExternalShellTexture())), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                policeBoxModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(policeBoxEmissive)), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

                if (blockstate.getValue(ShellBaseBlock.REGEN)) {
                    policeBoxModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(theme.getExternalShellTexture())), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, sine);
                }

                policeBoxModel.setDoorPosition(isOpen);
                break;
            }

            case PHONE_BOOTH -> {
                phoneBoothModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(theme.getExternalShellTexture())), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                phoneBoothModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(phoneBoothEmissive)), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

                if (blockstate.getValue(ShellBaseBlock.REGEN)) {
                    phoneBoothModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(phoneBoothEmissive)), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, sine);
                }

                phoneBoothModel.setDoorPosition(isOpen);
                break;
            }

            default -> {
                factoryShellModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(theme.getExternalShellTexture())), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

                if (blockstate.getValue(ShellBaseBlock.REGEN)) {
                    factoryShellModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(theme.getExternalShellTexture())), i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, sine);
                }

                factoryShellModel.setDoorPosition(isOpen);
                break;
            }
        }

        poseStack.popPose();

    }

    @Override
    public BlockEntityRenderer<GlobalShellBlockEntity> create(Context context) {
        return new GlobalShellRenderer(context);
    }
}
