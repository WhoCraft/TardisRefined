package whocraft.tardis_refined.common.util;

import net.minecraft.core.BlockPos;

public class NbtUtil {

    public static BlockPos getBlockPosFromIntArray(int[] array) {
        if (array.length != 3) {
            return null;
        }

        return new BlockPos(array[0], array[1], array[2]);
    }

    public static int[] getIntArrayFromBlockPos(BlockPos blockPos) {
        return new int[] {blockPos.getX(), blockPos.getY(), blockPos.getZ()};
    }

}
