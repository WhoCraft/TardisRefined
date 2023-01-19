package whocraft.tardis_refined.client.model.blockentity.shell;

import net.minecraft.client.Minecraft;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class ShellModelCollection {

    private static ShellModel factoryShellModel, policeBoxModel, phoneBoothModel, mysticModel, drifterModel, presentModel, vendingModel, briefcaseModel, greoningModel, bigBenModel, nukaModel, growthModel;


    public ShellModelCollection() {
        var context = Minecraft.getInstance().getEntityModels();
        factoryShellModel = new FactoryShellModel(context.bakeLayer((ModelRegistry.FACTORY_SHELL)));
        policeBoxModel = new PoliceBoxModel(context.bakeLayer((ModelRegistry.POLICE_BOX_SHELL)));
        phoneBoothModel = new PhoneBoothModel(context.bakeLayer((ModelRegistry.PHONE_BOOTH_SHELL)));
        mysticModel = new MysticShellModel(context.bakeLayer((ModelRegistry.MYSTIC_SHELL)));
        drifterModel = new DrifterShellModel(context.bakeLayer((ModelRegistry.DRIFTER_SHELL)));
        presentModel = new PresentShellModel(context.bakeLayer((ModelRegistry.PRESENT_SHELL)));
        vendingModel = new VendingMachineShellModel(context.bakeLayer((ModelRegistry.VENDING_SHELL)));
        briefcaseModel = new BriefcaseShellModel(context.bakeLayer((ModelRegistry.BRIEFCASE_SHELL)));
        greoningModel = new GroeningShellModel(context.bakeLayer((ModelRegistry.GROENING_SHELL)));
        bigBenModel = new BigBenShellModel(context.bakeLayer((ModelRegistry.BIG_BEN_SHELL)));
        nukaModel = new NukaShellModel(context.bakeLayer((ModelRegistry.NUKA_SHELL)));
        growthModel = new GrowthShellModel(context.bakeLayer((ModelRegistry.GROWTH_SHELL)));

    }

    /**
     * Get the associated shell model from a shell theme.
     * @param theme The Shell theme.
     * @return Shell model tied with the shell theme.
     * **/
    public ShellModel getShellModel(ShellTheme theme) {
        return switch (theme) {
            case PHONE_BOOTH -> phoneBoothModel;
            case POLICE_BOX -> policeBoxModel;
            case FACTORY -> factoryShellModel;
            case MYSTIC -> mysticModel;
            case PRESENT -> presentModel;
            case DRIFTER -> drifterModel;
            case VENDING -> vendingModel;
            case BRIEFCASE -> briefcaseModel;
            case GROENING -> greoningModel;
            case BIG_BEN -> bigBenModel;
            case NUKA -> nukaModel;
            case GROWTH -> growthModel;

        };
    }

    private static ShellModelCollection instance = null;

    public static ShellModelCollection getInstance() {
        if (ShellModelCollection.instance == null) {
            ShellModelCollection.instance = new ShellModelCollection();
        }

        return instance;
    }

}
