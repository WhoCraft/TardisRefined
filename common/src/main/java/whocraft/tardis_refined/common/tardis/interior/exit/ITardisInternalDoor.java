package whocraft.tardis_refined.common.tardis.interior.exit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;

public interface ITardisInternalDoor {

    boolean isMainDoor();
    void onSetMainDoor(boolean isMainDoor);

    String getID();
    void setID(String id);

    boolean isOpen();
    void setOpen(boolean state);

    BlockPos getDoorPosition();

    BlockPos getEntryPosition();
    Direction getEntryRotation();

    void onEntityExit(ServerEntity entity);
}
