package whocraft.tardis_refined.client.renderer.blockentity.door;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.model.blockentity.door.interior.*;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.HashMap;
import java.util.Map;

public class GlobalDoorRenderer implements BlockEntityRenderer<GlobalDoorBlockEntity>, BlockEntityRendererProvider<GlobalDoorBlockEntity> {


    protected static ShellDoorModel currentModel;
    private static ShellDoorModel factoryDoorModel, policeBoxModel, phoneBoothDoorModel, mysticDoorModel, drifterModel, presentModel, vendingModel, briefcaseModel,
            groeningModel, bigBenModel, nukaModel, growthModel, portalooModel, pagodaModel, liftDoorModel;

    public static Map<ResourceLocation, ShellDoorModel> DOOR_MODELS = new HashMap<>();


    public GlobalDoorRenderer(BlockEntityRendererProvider.Context context) {
        this.registerModels(context);
    }

    public void registerModels(BlockEntityRendererProvider.Context context){
        factoryDoorModel = new FactoryDoorModel(context.bakeLayer((ModelRegistry.FACTORY_DOOR)));
        policeBoxModel = new PoliceBoxDoorModel(context.bakeLayer((ModelRegistry.POLICE_BOX_DOOR)));
        phoneBoothDoorModel = new PhoneBoothDoorModel(context.bakeLayer((ModelRegistry.PHONE_BOOTH_DOOR)));
        mysticDoorModel = new MysticDoorModel(context.bakeLayer((ModelRegistry.MYSTIC_DOOR)));
        drifterModel = new DrifterDoorModel(context.bakeLayer((ModelRegistry.DRIFTER_DOOR)));
        presentModel = new PresentDoorModel(context.bakeLayer((ModelRegistry.PRESENT_DOOR)));
        vendingModel = new VendingMachineDoorModel(context.bakeLayer((ModelRegistry.VENDING_DOOR)));
        briefcaseModel = new BriefcaseDoorModel(context.bakeLayer((ModelRegistry.BRIEFCASE_DOOR)));
        groeningModel = new GroeningDoorModel(context.bakeLayer((ModelRegistry.GROENING_DOOR)));
        bigBenModel = new BigBenDoorModel(context.bakeLayer((ModelRegistry.BIG_BEN_DOOR)));
        nukaModel = new NukaDoorModel(context.bakeLayer((ModelRegistry.NUKA_DOOR)));
        growthModel = new GrowthDoorModel(context.bakeLayer((ModelRegistry.GROWTH_DOOR)));
        portalooModel = new PortalooDoorModel(context.bakeLayer((ModelRegistry.PORTALOO_DOOR)));
        pagodaModel = new PagodaDoorModel(context.bakeLayer((ModelRegistry.PAGODA_DOOR)));
        liftDoorModel = new LiftShellDoorModel(context.bakeLayer((ModelRegistry.LIFT_DOOR)));

        DOOR_MODELS.put(ShellTheme.FACTORY.getId(), factoryDoorModel);
        DOOR_MODELS.put(ShellTheme.POLICE_BOX.getId(), policeBoxModel);
        DOOR_MODELS.put(ShellTheme.PHONE_BOOTH.getId(), phoneBoothDoorModel);
        DOOR_MODELS.put(ShellTheme.MYSTIC.getId(), mysticDoorModel);
        DOOR_MODELS.put(ShellTheme.DRIFTER.getId(), drifterModel);
        DOOR_MODELS.put(ShellTheme.PRESENT.getId(), presentModel);
        DOOR_MODELS.put(ShellTheme.VENDING.getId(), vendingModel);
        DOOR_MODELS.put(ShellTheme.BRIEFCASE.getId(), briefcaseModel);
        DOOR_MODELS.put(ShellTheme.GROENING.getId(), groeningModel);
        DOOR_MODELS.put(ShellTheme.BIG_BEN.getId(), bigBenModel);
        DOOR_MODELS.put(ShellTheme.NUKA.getId(), nukaModel);
        DOOR_MODELS.put(ShellTheme.GROWTH.getId(), growthModel);
        DOOR_MODELS.put(ShellTheme.PORTALOO.getId(), portalooModel);
        DOOR_MODELS.put(ShellTheme.PAGODA.getId(), pagodaModel);
        DOOR_MODELS.put(ShellTheme.LIFT.getId(), liftDoorModel);

    }

    @Override
    public void render(GlobalDoorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180F));
        BlockState blockstate = blockEntity.getBlockState();
        float rotation = blockstate.getValue(GlobalDoorBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        ResourceLocation theme = blockEntity.theme();
        boolean isOpen = blockstate.getValue(GlobalDoorBlock.OPEN);

        // Render slightly off the wall to prevent z-fighting.
        poseStack.translate(0, 0, -0.01);
        currentModel = DOOR_MODELS.get(theme);

        if(theme == ShellTheme.POLICE_BOX.getId()){
            poseStack.scale(1.05f, 1.05f, 1.05f);
            poseStack.translate(0, -0.07, 0);
        }

        currentModel.setDoorPosition(isOpen);
        currentModel.renderInteriorDoor(blockEntity, isOpen, true, poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(currentModel.getInteriorDoorTexture(blockEntity))), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(GlobalDoorBlockEntity blockEntity) {
        return true;
    }

    @Override
    public BlockEntityRenderer<GlobalDoorBlockEntity> create(Context context) {
        return new GlobalDoorRenderer(context);
    }
}
