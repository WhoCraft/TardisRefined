package whocraft.tardis_refined.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;

public class TardisHelper {

    public static void playCloisterBell(TardisLevelOperator tardisLevelOperator) {
        for (int i = 0; i < 3; i++) {
            tardisLevelOperator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 1000f, 0.1f);
        }
    }

    public static boolean isInArsArea(BlockPos blockPos) {

        BlockPos corner1 = new BlockPos(1009, 97, -2);
        BlockPos corner2 = new BlockPos(1041, 118, 30);

        int minX = Math.min(corner1.getX(), corner2.getX());
        int maxX = Math.max(corner1.getX(), corner2.getX());
        int minY = Math.min(corner1.getY(), corner2.getY());
        int maxY = Math.max(corner1.getY(), corner2.getY());
        int minZ = Math.min(corner1.getZ(), corner2.getZ());
        int maxZ = Math.max(corner1.getZ(), corner2.getZ());

        return blockPos.getX() >= minX && blockPos.getX() <= maxX &&
                blockPos.getY() >= minY && blockPos.getY() <= maxY &&
                blockPos.getZ() >= minZ && blockPos.getZ() <= maxZ;
    }
}
