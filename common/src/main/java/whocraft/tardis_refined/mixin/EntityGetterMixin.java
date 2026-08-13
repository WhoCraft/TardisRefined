package whocraft.tardis_refined.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.EntityGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

import java.util.function.Predicate;

@Mixin(EntityGetter.class)
public interface EntityGetterMixin {
    
    @ModifyVariable(
            method = "getNearestPlayer(DDDDLjava/util/function/Predicate;)Lnet/minecraft/world/entity/player/Player;",
            at = @At("HEAD"),
            argsOnly = true
    )
    default Predicate<Entity> getNearestPlayer(
            Predicate<Entity> predicate
    ) {
        Predicate<Entity> additionalPredicate = entity -> {
            if (entity instanceof LivingEntity living) {
                var info = TardisPlayerInfo.get(living);
                if (info.isPresent()) {
                    return !info.get().isViewingTardis();
                }
            }
            return true;
        };
        if (predicate == null) {
            return additionalPredicate;
        } else {
            return predicate.and(additionalPredicate);
        }
    }
    
}
