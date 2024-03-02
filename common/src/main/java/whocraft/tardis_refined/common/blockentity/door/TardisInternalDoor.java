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

    BlockPos getEntryPosition();
    Direction getEntryRotation();

    /** The true facing of the internal door based off its blockstate*/
    Direction getDoorRotation();

    void onEntityExit(ServerEntity entity);

    void setLocked(boolean locked);

    boolean locked();
    void onAttemptEnter(BlockState blockState, Level level, BlockPos doorPos, Entity entity);

}
