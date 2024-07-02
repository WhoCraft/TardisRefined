package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public abstract class ConsoleThemeDetails extends ConsoleTheme {

    protected ConsoleThemeDetails(ControlSpecification[] controlSpecifications) {
        super(new ResourceLocation("f"), null, controlSpecifications);
    }


}
