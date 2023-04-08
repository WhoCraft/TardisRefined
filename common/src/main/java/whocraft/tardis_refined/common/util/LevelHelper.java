package whocraft.tardis_refined.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

/** Helpers related to levels **/
public class LevelHelper {

    public static Vec3 centerPos(BlockPos pos, boolean centerY){
        return new Vec3(pos.getX() + 0.5, pos.getY() + (centerY ? 0.5 : 0), pos.getZ() + 0.5);
    }

}
