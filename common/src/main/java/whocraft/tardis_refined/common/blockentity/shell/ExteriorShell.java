package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

public interface ExteriorShell {

    BlockPos getExitPosition();

    /** The true facing of the Exterior Shell based off its blockstate*/
    Direction getShellRotation();

    /** Get the direction which we want the player to face when it is arrived at the exterior shell door block, for teleportation purposes.
     * @implNote This should be the opposite facing of the exterior shell block*/
    Direction getExitRotation();

    DesktopTheme getAssociatedTheme();

    ResourceKey<Level> getTardisId();

    void setTardisId(ResourceKey<Level> levelKey);

    boolean isOpen();
    /** Source of truth logic for setting this Exterior Shell to be opened/closed. */
    void setClosed(boolean closeDoor);

    void onAttemptEnter(BlockState blockState, Level level, BlockPos externalShellPos, Entity entity);

    void onEntityExit(ServerEntity entity);

    /** Sets the Shell to be locked*/
    void setLocked(boolean locked);
    /** Determines if this particular Shell thinks it is locked or not.
     * <br> If the Tardis (via TardisExteriorManager) is out of sync with the value on this actual shell (such as if the exterior is duplicated), this will help determine what is the source of truth
     * <br> Hence the ExteriorManager will need to check this shell's local value.*/
    boolean locked();



}
