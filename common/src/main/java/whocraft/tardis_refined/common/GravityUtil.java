package whocraft.tardis_refined.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import whocraft.tardis_refined.common.block.device.AntiGravityBlock;
import whocraft.tardis_refined.registry.TRDimensionTypes;

import static net.minecraft.core.BlockPos.betweenClosed;

public class GravityUtil {


    private static final int MAX_Y = 30; // The most a shaft can carry a player up
    private static final double ACCELERATION = 0.2;
    public static boolean isInAntiGrav(Player playerBox, AABB box, Level level) {
//        if(level.dimensionTypeId() != TRDimensionTypes.TARDIS) return false; //Allow sound to play no matter what dimension the block is placed in
        for (BlockPos pos : betweenClosed(new BlockPos((int) box.maxX, (int) box.maxY, (int) box.maxZ), new BlockPos((int) box.minX, (int) box.minY, (int) box.minZ))) {
            BlockState blockState = level.getBlockState(pos);
            if (blockState.getBlock() instanceof AntiGravityBlock) {
                int space = blockState.getValue(AntiGravityBlock.SPACE);
                if (space > 0 && level.getBlockState(pos.above()).isAir()) {
                    AABB myGravBox = GravityUtil.createGravityBoxFromLevel(level, pos, space);
                    spawnParticlesWithinAABB(level, myGravBox);
                    if (myGravBox.intersects(playerBox.getBoundingBox()) && playerBox.blockPosition().getY() >= pos.getY()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static void spawnParticlesWithinAABB(Level level, AABB box) {
        RandomSource randomSource = level.random;
        for (int i = 0; i < 2; i++) {
            double x = box.minX + (box.maxX - box.minX) * randomSource.nextDouble();
            double y = box.minY + (box.maxY - box.minY) * randomSource.nextDouble();
            double z = box.minZ + (box.maxZ - box.minZ) * randomSource.nextDouble();
            level.addParticle(ParticleTypes.ASH, x, y, z, 0, 0.2, 0);
        }
    }


    public static AABB createGravityBoxFromLevel(Level level, BlockPos blockPos, int range) {
        if (level != null) {
            return new AABB(blockPos).inflate(range, MAX_Y, range);
        }
        return null;

    }


    public static boolean isInGravityShaft(Player player) {
        return isInAntiGrav(player, player.getBoundingBox().inflate(8, MAX_Y, 8), player.level());
    }


    static double easeMovement() {
        double smoothedMovement = Math.abs(GravityUtil.ACCELERATION);
        return smoothedMovement * smoothedMovement * smoothedMovement;
    }



}
