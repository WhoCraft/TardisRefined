package whocraft.tardis_refined.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class GravityUtil {

    public static boolean isInGravityShaft(Player player) {
        AABB aabb = new AABB(new BlockPos( 999, 101, 19), new BlockPos(1002, 70, 17));
        return player.getBoundingBox().intersects(aabb);
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
