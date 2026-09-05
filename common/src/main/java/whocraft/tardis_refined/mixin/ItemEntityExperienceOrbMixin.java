package whocraft.tardis_refined.mixin;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

@Mixin({ItemEntity.class, ExperienceOrb.class})
public class ItemEntityExperienceOrbMixin {

	@Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
	public void playerTouch(Player player, CallbackInfo ci) {
		TardisPlayerInfo.get(player).ifPresent(data -> {
			if (data.isViewingTardis()) {
				ci.cancel();
			}
		});
	}

}
