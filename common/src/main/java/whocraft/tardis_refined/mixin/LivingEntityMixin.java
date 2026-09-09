package whocraft.tardis_refined.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Unique
    private boolean tardis_refined$isViewingTARDIS() {
        if ((Object) this instanceof Player player) {
            var playerInfo = TardisPlayerInfo.get(player);
            if (playerInfo.isPresent()) {
                return playerInfo.get().isViewingTardis();
            }
        }
        return false;
    }

    @ModifyReturnValue(method = "canBeAffected", at = @At("RETURN"))
    public boolean canBeAffected(boolean original) {
        if (original && tardis_refined$isViewingTARDIS()) {
            return false;
        }
        return original;
    }

    @WrapWithCondition(
            method = "baseTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;tickEffects()V"
            )
    )
    public boolean tickEffects(LivingEntity instance) {
        return !tardis_refined$isViewingTARDIS();
    }

}
