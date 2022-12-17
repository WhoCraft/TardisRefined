package whocraft.tardis_refined.client.model.blockentity.console;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

/***
 * A collection of models for rendering the console unit.
 */
public class ConsoleModelCollection {

    IConsoleUnit toyotaConsoleModel, coralConsoleModel, copperConsoleModel, nukaConsoleModel, factoryConsoleModel;

    public ConsoleModelCollection(BlockEntityRendererProvider.Context context) {
        factoryConsoleModel = new FactoryConsoleModel(context.bakeLayer((ModelRegistry.FACTORY_CONSOLE)));
        nukaConsoleModel = new NukaConsoleModel(context.bakeLayer((ModelRegistry.NUKA_CONSOLE)));
        copperConsoleModel = new CopperConsoleModel(context.bakeLayer((ModelRegistry.COPPER_CONSOLE)));
        coralConsoleModel = new CoralConsoleModel(context.bakeLayer((ModelRegistry.CORAL_CONSOLE)));
        toyotaConsoleModel = new ToyotaConsoleModel(context.bakeLayer((ModelRegistry.TOYOTA_CONSOLE)));
    }

    /**
     * Get the associated console model from a console theme.
     * @param theme The Console theme.
     * @return Console unit model tied with the console theme.
     * **/
    public IConsoleUnit getConsoleModel(ConsoleTheme theme) {
        switch (theme) {
            case COPPER:
                return copperConsoleModel;
            case CORAL:
                return coralConsoleModel;
            case TOYOTA:
                return toyotaConsoleModel;
            case NUKA:
                return nukaConsoleModel;
            default:
                return factoryConsoleModel;
        }
    }

}
