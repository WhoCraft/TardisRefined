package whocraft.tardis_refined.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static whocraft.tardis_refined.common.GravityUtil.easeMovement;

public class GravityClient {

    private static final float SHAFT_ASCEND_SPEED  = 0.12f;
    private static final float SHAFT_DESCEND_SPEED = 0.12f;
    private static final float SHAFT_DRAG          = 0.75f;

    public static void moveGravity(Player player, CallbackInfo info) {
        if (!GravityUtil.isInGravityShaft(player)) {
            player.setNoGravity(false);
            return;
        }

        Vec3 vel = player.getDeltaMovement();
        Options options = Minecraft.getInstance().options;

        player.stopFallFlying();
        player.resetFallDistance();
        player.setNoGravity(true);
        player.setPose(Pose.STANDING);

        double newX = vel.x * SHAFT_DRAG;
        double newZ = vel.z * SHAFT_DRAG;

        double newY;
        if (options.keyJump.isDown()) {
            newY = Math.min(vel.y + easeMovement(), SHAFT_ASCEND_SPEED);
            info.cancel();
        } else if (options.keyShift.isDown()) {
            newY = Math.max(vel.y - easeMovement(), -SHAFT_DESCEND_SPEED);
            info.cancel();
        } else {
            // Bring vertical momentum to zero smoothly rather than snapping
            newY = vel.y * SHAFT_DRAG;
        }

        player.setDeltaMovement(newX, newY, newZ);

        // Only fake ground contact on the tick the player lands in the shaft,
        // not every tick — avoids continuous hunger/exhaustion drain
        if (!player.onGround()) {
            player.setOnGround(true);
        }
    }


}
