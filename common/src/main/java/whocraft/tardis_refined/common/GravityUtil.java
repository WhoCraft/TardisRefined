package whocraft.tardis_refined.common;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.block.device.AntiGravityBlock;
import whocraft.tardis_refined.registry.DimensionTypes;

import static net.minecraft.core.BlockPos.betweenClosed;

public class GravityUtil {


    private static final int MAX_Y = 30; // The most a shaft can carry a player up
    private static final double ACCELERATION = 0.2;
    public static boolean isInAntiGrav(Player playerBox, AABB box, Level level) {
        if(level.dimensionTypeId() != DimensionTypes.TARDIS) return false;
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
        return isInAntiGrav(player, player.getBoundingBox().inflate(20, MAX_Y, 20), player.level());
    }

    @Environment(EnvType.CLIENT)
    public static void moveGravity(Player player, CallbackInfo info) {
        Vec3 deltaMovement = player.getDeltaMovement();
        Minecraft minecraft = Minecraft.getInstance();
        Options options = minecraft.options;

        if (isInGravityShaft(player)) {
            player.resetFallDistance();
            player.setNoGravity(true);
            player.setPose(Pose.STANDING);

            if (options.keyJump.isDown()) {
                player.setDeltaMovement(deltaMovement.add(0, easeMovement(), 0));
                info.cancel();
            } else if (options.keyShift.isDown()) {
                player.setDeltaMovement(deltaMovement.add(0, -easeMovement(), 0));
                info.cancel();
            } else {
                player.setDeltaMovement(deltaMovement.x, 0, deltaMovement.z);
            }
        } else {
            player.setNoGravity(false);
        }
    }


    private static double easeMovement() {
        double smoothedMovement = Math.abs(GravityUtil.ACCELERATION);
        return smoothedMovement * smoothedMovement * smoothedMovement;
    }



}
