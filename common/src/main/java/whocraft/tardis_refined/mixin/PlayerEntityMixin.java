package whocraft.tardis_refined.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.GravityClient;
import whocraft.tardis_refined.common.GravityUtil;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

@Mixin(Player.class)
public class PlayerEntityMixin {

    @Inject(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At("TAIL"), cancellable = true)
    private void move(Vec3 vec3, CallbackInfo info) {
        Player player = (Player) (Object) this;

        TardisPlayerInfo.get(player).ifPresent(tardisInfo -> {
            if (tardisInfo.isViewingTardis()) {
                player.setDeltaMovement(0, 0, 0);
                info.cancel();
            }
        });

        if (!player.level().isClientSide) return;
        GravityClient.moveGravity(player, info);
    }


    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (GravityUtil.isInGravityShaft(player)) {
            player.resetFallDistance();
        }

        if (player.tickCount % 20 == 0 && !player.level().isClientSide) {
            TardisPlayerInfo.get(player).ifPresent(tardisPlayerInfo -> {
                tardisPlayerInfo.syncToClients(null);
            });
        }

    }

    @ModifyReturnValue(method = "isInvulnerableTo", at = @At("RETURN"))
    public boolean isInvulnerable(boolean original, @Local(argsOnly = true) DamageSource damageSource) {
        if (!original) {
            var playerInfo = TardisPlayerInfo.get((Player) (Object) this);
            if (playerInfo.isPresent()) {
                return playerInfo.get().isViewingTardis();
            }
        }
        return original;
    }


}
