package whocraft.tardis_refined.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import whocraft.tardis_refined.common.GravityClient;
import whocraft.tardis_refined.common.GravityUtil;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

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

    @ModifyArgs(
            method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/ItemEntity;<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"
            )
    )
    public void dropCheckShellView(Args args, @Share("rotation") LocalRef<Vec2> rotation) {
        TardisPlayerInfo.get((Player) (Object) this).ifPresent(data -> {
            if (data.isViewingTardis() && getServer() != null) {
                var prevDim = getServer().getLevel(data.getPlayerPreviousDim());
                if (prevDim != null) {
                    args.set(0, prevDim);
                    var prevPos = data.getPlayerPreviousPosAccurate();
                    args.set(1, prevPos.x);
                    args.set(2, prevPos.y + ((double) args.get(2)) - getY());
                    args.set(3, prevPos.z);
                    rotation.set(new Vec2(data.getPlayerPreviousRot(), data.getPlayerPreviousYaw()));
                }
            }
        });
    }

    @ModifyExpressionValue(
            method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getXRot()F"
            )
    )
    public float dropFixXRot(float original, @Share("rotation") LocalRef<Vec2> rotation) {
        if (rotation.get() != null) {
            return rotation.get().x;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getYRot()F"
            )
    )
    public float dropFixYRot(float original, @Share("rotation") LocalRef<Vec2> rotation) {
        if (rotation.get() != null) {
            return rotation.get().y;
        }
        return original;
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
