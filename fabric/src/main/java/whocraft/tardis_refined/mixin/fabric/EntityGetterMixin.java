package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.EntityGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

import java.util.function.Predicate;

// TODO Try to move this back to common in 1.21.1+ if the Forge bug preventing this mixin from working is fixed.
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
        return TardisPlayerInfo.wrapNullablePredicateWithExcludeShellView(predicate);
    }
    
}
