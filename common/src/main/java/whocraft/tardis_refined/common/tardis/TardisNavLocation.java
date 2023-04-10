package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

/**
 * TardisNavLocation
 * Co-ordinates that represent position, rotation and level.
 * **/
public class TardisNavLocation {
    public BlockPos position;
    public Direction rotation;
    private ServerLevel level;

    private ResourceKey<Level> dimensionKey;

    /**
     * @param position World co-ordinate
     * @param rotation Rotation/Facing direction.
     * @param level ResourceKey of the desired level.
     * **/
    public TardisNavLocation(BlockPos position, Direction rotation, ServerLevel level) {
        this.position = position;
        this.rotation = rotation;
        this.level = level;
        if (level != null) {
            this.dimensionKey = level.dimension();
        }

    }

    public TardisNavLocation(BlockPos position, Direction rotation, ResourceKey<Level> level) {
        this.position = position;
        this.rotation = rotation;
        this.dimensionKey = level;
    }

    public ServerLevel getLevel() {
        if (this.level != null) {
            this.dimensionKey = this.level.dimension();
        }

        return this.level;
    }

    public void setLevel(ServerLevel level) {
        this.dimensionKey = level.dimension();
        this.level = level;
    }

    public ResourceKey<Level> getDimensionKey() {return dimensionKey;}
}
