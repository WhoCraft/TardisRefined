package whocraft.tardis_refined.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.model.blockentity.door.FactoryDoorModel;
import whocraft.tardis_refined.client.model.blockentity.shell.FactoryShellModel;
import whocraft.tardis_refined.client.model.blockentity.shell.internal.door.RootShellDoorModel;
import whocraft.tardis_refined.client.model.blockentity.shell.rootplant.*;
import whocraft.tardis_refined.client.renderer.blockentity.shell.RootShellRenderer;
import whocraft.tardis_refined.common.util.PlatformWarning;

import java.util.function.Supplier;

public class ModelRegistry {

    // Root Plants - Sorry in advance.
    public static ModelLayerLocation ROOT_PLANT_STATE_ONE;
    public static ModelLayerLocation ROOT_PLANT_STATE_TWO;
    public static ModelLayerLocation ROOT_PLANT_STATE_THREE;
    public static ModelLayerLocation ROOT_PLANT_STATE_FOUR;
    public static ModelLayerLocation ROOT_PLANT_STATE_FIVE;

    public static ModelLayerLocation ROOT_SHELL;
    public static ModelLayerLocation ROOT_SHELL_DOOR;

    public static ModelLayerLocation FACTORY_SHELL;
    public static ModelLayerLocation FACTORY_DOOR;


    public static void init() {
        ROOT_PLANT_STATE_ONE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_one"), "root_plant_one"), RootPlantStateOneModel::createBodyLayer);
        ROOT_PLANT_STATE_TWO = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_two"), "root_plant_two"), RootPlantStateTwoModel::createBodyLayer);
        ROOT_PLANT_STATE_THREE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_three"), "root_plant_three"), RootPlantStateThreeModel::createBodyLayer);
        ROOT_PLANT_STATE_FOUR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_four"), "root_plant_four"), RootPlantStateFourModel::createBodyLayer);
        ROOT_PLANT_STATE_FIVE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_five"), "root_plant_five"), RootPlantStateFiveModel::createBodyLayer);

        ROOT_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_shell"), "root_shell"), RootShellModel::createBodyLayer);
        ROOT_SHELL_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_shell_door"), "root_shell_door"), RootShellDoorModel::createBodyLayer);

        FACTORY_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "factory_shell"), "factory_shell"), FactoryShellModel::createBodyLayer);
        FACTORY_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "factory_door"), "factory_door"), FactoryDoorModel::createBodyLayer);
    }

    @ExpectPlatform
    public static ModelLayerLocation register(ModelLayerLocation location, Supplier<LayerDefinition> definitionSupplier) {
        throw new RuntimeException(PlatformWarning.addWarning(ModelRegistry.class));
    }

}
