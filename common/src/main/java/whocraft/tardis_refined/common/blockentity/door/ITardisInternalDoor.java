package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerEntity;

public interface ITardisInternalDoor {

    boolean isMainDoor();
    void onSetMainDoor(boolean isMainDoor);

    String getID();
    void setID(String id);

    boolean isOpen();
    void setClosed(boolean state);

    BlockPos getDoorPosition();

    BlockPos getEntryPosition();
    Direction getEntryRotation();

    void onEntityExit(ServerEntity entity);
}
