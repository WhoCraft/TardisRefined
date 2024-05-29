package whocraft.tardis_refined.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

/**
 * Helpers related to levels
 **/
public class LevelHelper {

    public static Vec3 centerPos(BlockPos pos, boolean centerY) {
        return new Vec3(pos.getX() + 0.5, pos.getY() + (centerY ? 0.5 : 0), pos.getZ() + 0.5);
    }

    public static float getAdjustedRotation(float rot) {
        float newR = rot % 360.0F;
        if (newR < 0)
            return 360.0F + newR;

        return newR;
    }

    public static float getAngleFromDirection(Direction dir) {
        switch (dir) {
            case EAST:
                return 90.0F;
            case SOUTH:
                return 180.0F;
            case WEST:
                return 270.0F;
            default:
                return 0.0F;
        }
    }


}
