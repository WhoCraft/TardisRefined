package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

public interface ExteriorShell {

    BlockPos getExitPosition();
    DesktopTheme getAssociatedTheme();

    ResourceKey<Level> getTardisId();

    void setTardisId(ResourceKey<Level> levelKey);

    void onAttemptEnter(BlockState blockState, Level level, BlockPos externalShellPos, Entity entity);

}
