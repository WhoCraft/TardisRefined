package whocraft.tardis_refined.client.model.blockentity.shell;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.HashMap;
import java.util.Map;

public class ShellModelCollection {

    private static ShellModel factoryShellModel, policeBoxModel, phoneBoothModel, mysticModel, drifterModel,
            presentModel, vendingModel, briefcaseModel, groeningModel, bigBenModel, nukaModel, growthModel,
            portalooModel, pagodaModel;

    public static Map<ResourceLocation, ShellModel> SHELL_MODELS = new HashMap<>();


    public ShellModelCollection() {
        var context = Minecraft.getInstance().getEntityModels();
        this.registerModels(context);
    }

    public void registerModels(EntityModelSet context){
        factoryShellModel = new FactoryShellModel(context.bakeLayer((ModelRegistry.FACTORY_SHELL)));
        policeBoxModel = new PoliceBoxModel(context.bakeLayer((ModelRegistry.POLICE_BOX_SHELL)));
        phoneBoothModel = new PhoneBoothModel(context.bakeLayer((ModelRegistry.PHONE_BOOTH_SHELL)));
        mysticModel = new MysticShellModel(context.bakeLayer((ModelRegistry.MYSTIC_SHELL)));
        drifterModel = new DrifterShellModel(context.bakeLayer((ModelRegistry.DRIFTER_SHELL)));
        presentModel = new PresentShellModel(context.bakeLayer((ModelRegistry.PRESENT_SHELL)));
        vendingModel = new VendingMachineShellModel(context.bakeLayer((ModelRegistry.VENDING_SHELL)));
        briefcaseModel = new BriefcaseShellModel(context.bakeLayer((ModelRegistry.BRIEFCASE_SHELL)));
        groeningModel = new GroeningShellModel(context.bakeLayer((ModelRegistry.GROENING_SHELL)));
        bigBenModel = new BigBenShellModel(context.bakeLayer((ModelRegistry.BIG_BEN_SHELL)));
        nukaModel = new NukaShellModel(context.bakeLayer((ModelRegistry.NUKA_SHELL)));
        growthModel = new GrowthShellModel(context.bakeLayer((ModelRegistry.GROWTH_SHELL)));
        portalooModel = new PortalooShellModel(context.bakeLayer((ModelRegistry.PORTALOO_SHELL)));
        pagodaModel = new PagodaShellModel(context.bakeLayer((ModelRegistry.PAGODA_SHELL)));

        SHELL_MODELS.put(ShellTheme.FACTORY.getId(), factoryShellModel);
        SHELL_MODELS.put(ShellTheme.POLICE_BOX.getId(), policeBoxModel);
        SHELL_MODELS.put(ShellTheme.PHONE_BOOTH.getId(), phoneBoothModel);
        SHELL_MODELS.put(ShellTheme.MYSTIC.getId(), mysticModel);
        SHELL_MODELS.put(ShellTheme.DRIFTER.getId(), drifterModel);
        SHELL_MODELS.put(ShellTheme.PRESENT.getId(), presentModel);
        SHELL_MODELS.put(ShellTheme.VENDING.getId(), vendingModel);
        SHELL_MODELS.put(ShellTheme.BRIEFCASE.getId(), briefcaseModel);
        SHELL_MODELS.put(ShellTheme.GROENING.getId(), groeningModel);
        SHELL_MODELS.put(ShellTheme.BIG_BEN.getId(), bigBenModel);
        SHELL_MODELS.put(ShellTheme.NUKA.getId(), nukaModel);
        SHELL_MODELS.put(ShellTheme.GROWTH.getId(), growthModel);
        SHELL_MODELS.put(ShellTheme.PORTALOO.getId(), portalooModel);
        SHELL_MODELS.put(ShellTheme.PAGODA.getId(), pagodaModel);
    }

    /**
     * Get the associated shell model from a shell theme.
     * @param themeId The Shell theme Id.
     * @return Shell model tied with the shell theme.
     * **/
    public ShellModel getShellModel(ResourceLocation themeId) {
        return SHELL_MODELS.get(themeId);
    }

    private static ShellModelCollection instance = null;

    public static ShellModelCollection getInstance() {
        if (ShellModelCollection.instance == null) {
            ShellModelCollection.instance = new ShellModelCollection();
        }

        return instance;
    }

}
