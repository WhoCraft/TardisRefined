package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

public interface ExteriorShell {

    BlockPos getExitPosition();
    DesktopTheme getAssociatedTheme();

    ResourceKey<Level> getTardisId();

    void setTardisId(ResourceKey<Level> levelKey);

}
