package whocraft.tardis_refined.client.renderer.blockentity.console;

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
import whocraft.tardis_refined.client.model.blockentity.console.CopperConsoleModel;
import whocraft.tardis_refined.client.model.blockentity.console.FactoryConsoleModel;
import whocraft.tardis_refined.client.model.blockentity.console.NukaConsoleModel;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class GlobalConsoleRenderer implements BlockEntityRenderer<GlobalConsoleBlockEntity>, BlockEntityRendererProvider<GlobalConsoleBlockEntity> {

    private static FactoryConsoleModel factoryConsoleModel;
    private static ResourceLocation factoryConsoleBaseTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/factory_console.png");

    private static NukaConsoleModel nukaConsoleModel;
    private static ResourceLocation nukaConsoleBaseTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/nuka_console.png");

    private static CopperConsoleModel copperConsoleModel;
    private static ResourceLocation copperConsoleBaseTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/copper_console.png");

    public GlobalConsoleRenderer(BlockEntityRendererProvider.Context context) {
        factoryConsoleModel = new FactoryConsoleModel(context.bakeLayer((ModelRegistry.FACTORY_CONSOLE)));
        nukaConsoleModel = new NukaConsoleModel(context.bakeLayer((ModelRegistry.NUKA_CONSOLE)));
        copperConsoleModel = new CopperConsoleModel(context.bakeLayer((ModelRegistry.COPPER_CONSOLE)));
    }

    @Override
    public void render(GlobalConsoleBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        BlockState blockstate = blockEntity.getBlockState();
        ConsoleTheme theme = blockstate.getValue(GlobalConsoleBlock.CONSOLE);

        if (theme == ConsoleTheme.FACTORY) {
            factoryConsoleModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(factoryConsoleBaseTexture)),
                    i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }

        if (theme == ConsoleTheme.NUKA) {
            nukaConsoleModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(nukaConsoleBaseTexture)),
                    i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }

        if (theme == ConsoleTheme.COPPER) {
            copperConsoleModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityTranslucent(copperConsoleBaseTexture)),
                    i, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }

        poseStack.popPose();
    }

    @Override
    public BlockEntityRenderer<GlobalConsoleBlockEntity> create(BlockEntityRendererProvider.Context context) {
        return new GlobalConsoleRenderer(context);
    }
}
