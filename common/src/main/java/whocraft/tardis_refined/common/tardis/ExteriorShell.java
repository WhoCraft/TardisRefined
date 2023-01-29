package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

public interface ExteriorShell {

    BlockPos getExitPosition();
    DesktopTheme getAssociatedTheme();

}
