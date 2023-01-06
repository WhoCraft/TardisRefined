package whocraft.tardis_refined.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.model.blockentity.console.*;
import whocraft.tardis_refined.client.model.blockentity.door.*;
import whocraft.tardis_refined.client.model.blockentity.life.ArsEggModel;
import whocraft.tardis_refined.client.model.blockentity.shell.*;
import whocraft.tardis_refined.client.model.blockentity.shell.internal.door.RootShellDoorModel;
import whocraft.tardis_refined.client.model.blockentity.shell.rootplant.*;
import whocraft.tardis_refined.common.util.PlatformWarning;

import java.util.function.Supplier;

public class ModelRegistry {

    // Root Plants - Sorry in advance.
    public static ModelLayerLocation ROOT_PLANT_STATE_ONE;
    public static ModelLayerLocation ROOT_PLANT_STATE_TWO;
    public static ModelLayerLocation ROOT_PLANT_STATE_THREE;
    public static ModelLayerLocation ROOT_PLANT_STATE_FOUR;
    public static ModelLayerLocation ROOT_PLANT_STATE_FIVE;

    public static ModelLayerLocation FACTORY_CONSOLE;
    public static ModelLayerLocation NUKA_CONSOLE;
    public static ModelLayerLocation COPPER_CONSOLE;
    public static ModelLayerLocation CORAL_CONSOLE;
    public static ModelLayerLocation TOYOTA_CONSOLE;
    public static ModelLayerLocation CRYSTAL_CONSOLE;

    public static ModelLayerLocation ROOT_SHELL;
    public static ModelLayerLocation FACTORY_SHELL;
    public static ModelLayerLocation POLICE_BOX_SHELL;
    public static ModelLayerLocation PHONE_BOOTH_SHELL;
    public static ModelLayerLocation MYSTIC_SHELL;
    public static ModelLayerLocation DRIFTER_SHELL;
    public static ModelLayerLocation PRESENT_SHELL;
    public static ModelLayerLocation VENDING_SHELL;
    public static ModelLayerLocation BRIEFCASE_SHELL;
    public static ModelLayerLocation GROENING_SHELL;
    public static ModelLayerLocation BIG_BEN_SHELL;

    public static ModelLayerLocation ROOT_SHELL_DOOR;
    public static ModelLayerLocation FACTORY_DOOR;
    public static ModelLayerLocation POLICE_BOX_DOOR;
    public static ModelLayerLocation PHONE_BOOTH_DOOR;
    public static ModelLayerLocation MYSTIC_DOOR;
    public static ModelLayerLocation DRIFTER_DOOR;
    public static ModelLayerLocation PRESENT_DOOR;
    public static ModelLayerLocation VENDING_DOOR;
    public static ModelLayerLocation BRIEFCASE_DOOR;
    public static ModelLayerLocation GROENING_DOOR;
    public static ModelLayerLocation BIG_BEN_DOOR;

    public static ModelLayerLocation ARS_EGG;
    public static ModelLayerLocation BULK_HEAD_DOOR;


    public static void init() {
        ROOT_PLANT_STATE_ONE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_one"), "root_plant_one"), RootPlantStateOneModel::createBodyLayer);
        ROOT_PLANT_STATE_TWO = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_two"), "root_plant_two"), RootPlantStateTwoModel::createBodyLayer);
        ROOT_PLANT_STATE_THREE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_three"), "root_plant_three"), RootPlantStateThreeModel::createBodyLayer);
        ROOT_PLANT_STATE_FOUR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_four"), "root_plant_four"), RootPlantStateFourModel::createBodyLayer);
        ROOT_PLANT_STATE_FIVE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_plant_five"), "root_plant_five"), RootPlantStateFiveModel::createBodyLayer);

        FACTORY_CONSOLE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "factory_console"), "factory_console"), FactoryConsoleModel::createBodyLayer);
        NUKA_CONSOLE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "nuka_console"), "nuka_console"), NukaConsoleModel::createBodyLayer);
        COPPER_CONSOLE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "copper_console"), "copper_console"), CopperConsoleModel::createBodyLayer);
        CORAL_CONSOLE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "coral_console"), "coral_console"), CoralConsoleModel::createBodyLayer);
        TOYOTA_CONSOLE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "toyota_console"), "toyota_console"), ToyotaConsoleModel::createBodyLayer);
        CRYSTAL_CONSOLE = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "crystal_console"), "crystal_console"), CrystalConsoleModel::createBodyLayer);

        ROOT_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_shell"), "root_shell"), RootShellModel::createBodyLayer);
        FACTORY_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "factory_shell"), "factory_shell"), FactoryShellModel::createBodyLayer);
        POLICE_BOX_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "police_box_shell"), "police_box_shell"), PoliceBoxModel::createBodyLayer);
        PHONE_BOOTH_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "phone_booth_shell"), "phone_booth_shell"), PhoneBoothModel::createBodyLayer);
        MYSTIC_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "mystic_shell"), "mystic_shell"), MysticShellModel::createBodyLayer);
        DRIFTER_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "drifter_shell"), "drifter_shell"), DrifterShellModel::createBodyLayer);
        PRESENT_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "present_shell"), "present_shell"), PresentShellModel::createBodyLayer);
        VENDING_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "vending_shell"), "vending_shell"), VendingMachineShellModel::createBodyLayer);
        BRIEFCASE_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "briefcase_shell"), "briefcase_shell"), BriefcaseShellModel::createBodyLayer);
        GROENING_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "groening_shell"), "groening_shell"), GroeningShellModel::createBodyLayer);
        BIG_BEN_SHELL = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "big_ben_shell"), "big_ben_shell"), BigBenShellModel::createBodyLayer);

        ROOT_SHELL_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "root_shell_door"), "root_shell_door"), RootShellDoorModel::createBodyLayer);
        FACTORY_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "factory_door"), "factory_door"), FactoryDoorModel::createBodyLayer);
        POLICE_BOX_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "police_box_door"), "police_box_door"), PoliceBoxDoorModel::createBodyLayer);
        PHONE_BOOTH_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "phone_booth_door"), "phone_booth_door"), PhoneBoothDoorModel::createBodyLayer);
        MYSTIC_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "mystic_door"), "mystic_door"), MysticDoorModel::createBodyLayer);
        DRIFTER_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "drifter_door"), "drifter_door"), DrifterDoorModel::createBodyLayer);
        PRESENT_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "present_door"), "present_door"), PresentDoorModel::createBodyLayer);
        VENDING_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "vending_door"), "vending_door"), VendingMachineDoorModel::createBodyLayer);
        BRIEFCASE_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "briefcase_door"), "briefcase_door"), BriefcaseDoorModel::createBodyLayer);
        GROENING_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "groening_door"), "groening_door"), GroeningDoorModel::createBodyLayer);
        BIG_BEN_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "big_ben_door"), "big_ben_door"), BigBenDoorModel::createBodyLayer);


        ARS_EGG = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "ars_egg"), "ars_egg"), ArsEggModel::createBodyLayer);
        BULK_HEAD_DOOR = register(new ModelLayerLocation(new ResourceLocation(TardisRefined.MODID, "bulk_head_door"), "bulk_head_door"), BulkHeadDoorModel::createBodyLayer);
    }

    @ExpectPlatform
    public static ModelLayerLocation register(ModelLayerLocation location, Supplier<LayerDefinition> definitionSupplier) {
        throw new RuntimeException(PlatformWarning.addWarning(ModelRegistry.class));
    }

}
