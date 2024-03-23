package whocraft.tardis_refined.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.block.device.AntiGravityBlock;

import java.util.Iterator;

import static net.minecraft.core.BlockPos.betweenClosed;

public class GravityUtil {


    public static boolean isInAntiGrav(AABB playerBox, AABB box, Level level) {
        for (BlockPos pos : betweenClosed(new BlockPos((int) box.maxX, (int) box.maxY, (int) box.maxZ), new BlockPos((int) box.minX, (int) box.minY, (int) box.minZ))) {
            BlockState blockState = level.getBlockState(pos);
            if (blockState.getBlock() instanceof AntiGravityBlock) {
                int space = blockState.getValue(AntiGravityBlock.SPACE);
                if (space > 0 && level.getBlockState(pos.above()).isAir()) {
                    if (GravityUtil.createGravityBoxFromLevel(level, pos, space).intersects(playerBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static AABB createGravityBoxFromLevel(Level level, BlockPos blockPos, int range) {
        if (level != null) {
            return new AABB(blockPos).inflate(range, level.getHeight(), range);
        }
        return null;
    }


    public static boolean isInGravityShaft(Player player) {
        AABB aabb = new AABB(new BlockPos( 999, 101, 19), new BlockPos(1002, 70, 17));
        return player.getBoundingBox().intersects(aabb) || isInAntiGrav(player.getBoundingBox(), player.getBoundingBox().inflate(20, 20, 20), player.level());
    }

    public static void moveGravity(Player player, CallbackInfo info) {
        Vec3 deltaMovement = player.getDeltaMovement();
        Minecraft minecraft = Minecraft.getInstance();
        Options options = minecraft.options;

        if (isInGravityShaft(player)) {
            player.resetFallDistance();
            player.setNoGravity(true);
            player.setPose(Pose.STANDING);

            double acceleration = 0.2;
            double maxSpeed = 0.5;

            if (options.keyJump.isDown()) {
                player.setDeltaMovement(deltaMovement.add(0, easeMovement(acceleration, maxSpeed), 0));
                info.cancel();
            } else if (options.keyShift.isDown()) {
                player.setDeltaMovement(deltaMovement.add(0, -easeMovement(acceleration, maxSpeed), 0));
                info.cancel();
            } else {
                player.setDeltaMovement(deltaMovement.x, 0, deltaMovement.z);
            }
        } else {
            player.setNoGravity(false);
        }
    }

    private static double easeMovement(double acceleration, double maxSpeed) {
        double smoothedMovement = Math.min(Math.abs(acceleration), maxSpeed);
        return smoothedMovement * smoothedMovement * smoothedMovement;
    }



}