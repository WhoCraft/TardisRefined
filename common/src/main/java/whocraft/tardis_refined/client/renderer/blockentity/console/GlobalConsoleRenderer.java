package whocraft.tardis_refined.client.renderer.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.console.*;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

import java.util.Objects;

public class GlobalConsoleRenderer implements BlockEntityRenderer<GlobalConsoleBlockEntity>, BlockEntityRendererProvider<GlobalConsoleBlockEntity> {
    IConsoleUnit currentConsoleUnit, toyotaConsoleModel, coralConsoleModel, copperConsoleModel, nukaConsoleModel, factoryConsoleModel;

    public GlobalConsoleRenderer(BlockEntityRendererProvider.Context context) {
        factoryConsoleModel = new FactoryConsoleModel(context.bakeLayer((ModelRegistry.FACTORY_CONSOLE)));
        nukaConsoleModel = new NukaConsoleModel(context.bakeLayer((ModelRegistry.NUKA_CONSOLE)));
        copperConsoleModel = new CopperConsoleModel(context.bakeLayer((ModelRegistry.COPPER_CONSOLE)));
        coralConsoleModel = new CoralConsoleModel(context.bakeLayer((ModelRegistry.CORAL_CONSOLE)));
        toyotaConsoleModel = new ToyotaConsoleModel(context.bakeLayer((ModelRegistry.TOYOTA_CONSOLE)));
    }

    @Override
    public void render(GlobalConsoleBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        BlockState blockstate = blockEntity.getBlockState();
        ConsoleTheme theme = blockstate.getValue(GlobalConsoleBlock.CONSOLE);

        switch (theme) {
            case FACTORY:
                currentConsoleUnit = factoryConsoleModel;
                break;
            case COPPER:
                currentConsoleUnit = copperConsoleModel;
                break;
            case CORAL:
                currentConsoleUnit = coralConsoleModel;
                break;
            case TOYOTA:
                currentConsoleUnit = toyotaConsoleModel;
                break;
            case NUKA:
                currentConsoleUnit = nukaConsoleModel;
                break;
        }

        currentConsoleUnit.renderConsole(Objects.requireNonNull(blockEntity.getLevel()), poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(currentConsoleUnit.getTexture(blockEntity))), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        poseStack.popPose();
    }

    @Override
    public BlockEntityRenderer<GlobalConsoleBlockEntity> create(BlockEntityRendererProvider.Context context) {
        return new GlobalConsoleRenderer(context);
    }
}
