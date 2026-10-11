package whocraft.tardis_refined.mixin.render;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.client.sounds.TRSoundInstances;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

/** Keeps positional sounds from the exterior world out of the TARDIS shell-view scene. */
@Mixin(SoundManager.class)
public abstract class ShellViewSoundMixin {

    @Inject(method = "play(Lnet/minecraft/client/resources/sounds/SoundInstance;)Lnet/minecraft/client/sounds/SoundEngine$PlayResult;", at = @At("HEAD"), cancellable = true)
    private void tardisRefined$silenceExteriorWorldWhileViewing(SoundInstance sound, CallbackInfoReturnable<SoundEngine.PlayResult> cir) {
        var player = net.minecraft.client.Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        // A parked shell view is just a view of the real exterior location;
        // leave its ambient sounds alone. Only isolate the exterior world
        // while the TARDIS is actually moving through the vortex.
        var viewing = TardisPlayerInfo.get(player).filter(TardisPlayerInfo::isViewingTardis);
        if (viewing.isEmpty() || !viewing.get().isRenderVortex()) return;

        var viewedTardisId = viewing.get().getViewedTardis();
        if (viewedTardisId == null || TardisClientData.getAllEntries().stream()
                .filter(data -> data.getLevelKey().identifier().getPath().equals(viewedTardisId.toString()))
                .noneMatch(TardisClientData::isFlying)) return;

        // Keep interface clicks and TARDIS effects (including the vanilla
        // elytra wind loop used by the vortex), but suppress positional sounds
        // from the exterior world around the projected shell.
        boolean shellViewSound = sound == TRSoundInstances.TARDIS_SINGLE_FLY_VORTEX
                || sound == TRSoundInstances.VORTEX_WINDS;
        if (sound.getSource() != SoundSource.MASTER && !shellViewSound) {
            cir.setReturnValue(SoundEngine.PlayResult.NOT_STARTED);
        }
    }
}
