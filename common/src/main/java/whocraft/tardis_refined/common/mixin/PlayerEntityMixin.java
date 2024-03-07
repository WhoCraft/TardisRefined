package whocraft.tardis_refined.common.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.GravityUtil;

@Mixin(Player.class)
public class PlayerEntityMixin {

    @Inject(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At("TAIL"), cancellable = true)
    private void move(Vec3 vec3, CallbackInfo info) {
        Player player = (Player) (Object) this;
        GravityUtil.moveGravity(player, info);
    }


}
