package whocraft.tardis_refined.common.tardis.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;

/**
 * TardisNavLocation
 * Co-ordinates that represent position, rotation and level.
 * **/
public class TardisNavLocation {
    public BlockPos position;
    public Direction rotation;
    public ServerLevel level;

    /**
     * @param position World co-ordinate
     * @param rotation Rotation/Facing direction.
     * @param level ResourceKey of the desired level.
     * **/
    public TardisNavLocation(BlockPos position, Direction rotation, ServerLevel level) {
        this.position = position;
        this.rotation = rotation;
        this.level = level;
    }
}
