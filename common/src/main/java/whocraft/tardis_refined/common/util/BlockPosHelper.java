package whocraft.tardis_refined.common.util;

import net.minecraft.core.BlockPos;

public class BlockPosHelper {

    public static double distanceBetween(BlockPos pos1, BlockPos pos2) {
        double sum = (pos1.getX() - pos2.getX())^2 + (pos1.getY() - pos2.getY())^2 + (pos1.getZ() - pos2.getZ())^2;
        return Math.sqrt(sum);
    }

}
