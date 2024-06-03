package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public interface TardisInternalDoor {

    boolean isMainDoor();
    void onSetMainDoor(boolean isMainDoor);

    String getID();
    void setID(String id);

    boolean isOpen();
    void setClosed(boolean state);

    BlockPos getDoorPosition();
    /** Get the direction which we want the player to face for teleportation purposes.
     * @implNote This should be the opposite facing of the internal door block*/
    BlockPos getEntryPosition();
    Direction getEntryRotation();

    /** The true facing of the internal door based off its blockstate*/
    Direction getDoorRotation();

    void onEntityExit(ServerEntity entity);
    /** Sets the internal door to be locked*/
    void setLocked(boolean locked);
    /** Determines if this particular door block thinks it is locked or not.
     * <br> When multiple internal doors are placed inside a Tardis dimension, and the player interacts on a door that is not the main door, the TardisExteriorManager may still be tracking the previous door's data
     * <br> Hence the ExteriorManager will need to check this door's local value.*/
    boolean locked();
    void onAttemptEnter(BlockState blockState, Level level, BlockPos doorPos, Entity entity);

}
