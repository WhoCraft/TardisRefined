package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

import java.util.UUID;

public interface IExteriorShell {

    BlockPos getExitPosition();
    DesktopTheme getAssociatedTheme();

}
