package whocraft.tardis_refined.common.tardis.interior.shell;

import net.minecraft.core.BlockPos;
import whocraft.tardis_refined.common.tardis.interior.arctypes.DesktopTheme;

public interface IExteriorShell {

    BlockPos getExitPosition();
    DesktopTheme getAssociatedTheme();

}
