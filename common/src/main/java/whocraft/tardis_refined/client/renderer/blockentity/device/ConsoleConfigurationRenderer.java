package whocraft.tardis_refined.client.renderer.blockentity.device;

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
import whocraft.tardis_refined.client.model.blockentity.device.ConsoleConfigurationModel;
import whocraft.tardis_refined.client.model.blockentity.life.ArsEggModel;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.device.ConsoleConfigurationBlock;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.blockentity.device.ConsoleConfigurationBlockEntity;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class ConsoleConfigurationRenderer implements BlockEntityRenderer<ConsoleConfigurationBlockEntity>, BlockEntityRendererProvider<ConsoleConfigurationBlockEntity> {

    IConsoleUnit currentConsoleUnit, toyotaConsoleModel, coralConsoleModel, copperConsoleModel, nukaConsoleModel, factoryConsoleModel;

    private ConsoleConfigurationModel consoleConfigurationModel;
    private ResourceLocation consoleConfigurationTexture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/device/console_configuration.png");

    public ConsoleConfigurationRenderer(Context context) {
        this.consoleConfigurationModel = new ConsoleConfigurationModel(context.bakeLayer(ModelRegistry.CONSOLE_CONFIGURATION));
        this.factoryConsoleModel = new FactoryConsoleModel(context.bakeLayer(ModelRegistry.FACTORY_CONSOLE));

        factoryConsoleModel = new FactoryConsoleModel(context.bakeLayer((ModelRegistry.FACTORY_CONSOLE)));
        nukaConsoleModel = new NukaConsoleModel(context.bakeLayer((ModelRegistry.NUKA_CONSOLE)));
        copperConsoleModel = new CopperConsoleModel(context.bakeLayer((ModelRegistry.COPPER_CONSOLE)));
        coralConsoleModel = new CoralConsoleModel(context.bakeLayer((ModelRegistry.CORAL_CONSOLE)));
        toyotaConsoleModel = new ToyotaConsoleModel(context.bakeLayer((ModelRegistry.TOYOTA_CONSOLE)));
    }

    @Override
    public BlockEntityRenderer<ConsoleConfigurationBlockEntity> create(Context context) {
        return new ConsoleConfigurationRenderer(context);
    }

    @Override
    public void render(ConsoleConfigurationBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.475F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));

        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(GlobalDoorBlock.FACING).getOpposite().toYRot();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation));

        consoleConfigurationModel.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(consoleConfigurationTexture)),
                packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        if (blockEntity.getLevel().random.nextInt(20) != 0) {
            poseStack.scale(0.25f,0.25f,0.25f);
            poseStack.translate(0, 1.5f + blockEntity.getLevel().random.nextFloat() * 0.01, 0);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(blockEntity.getLevel().getGameTime() % 360));

            ConsoleTheme theme = blockstate.getValue(ConsoleConfigurationBlock.CONSOLE);

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

            currentConsoleUnit.renderConsole(blockEntity.getLevel(), poseStack, bufferSource.getBuffer(RenderType.entityTranslucentEmissive(currentConsoleUnit.getDefaultTexture())),
                    packedLight, OverlayTexture.NO_OVERLAY, 1f, 0.64f, 0f, 0.5f);
        }



        poseStack.popPose();
    }
}
