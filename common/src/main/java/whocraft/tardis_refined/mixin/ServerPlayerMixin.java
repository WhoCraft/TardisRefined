package whocraft.tardis_refined.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRDimensionTypes;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "stopSleepInBed(ZZ)V", at = @At("HEAD"))
    private void stopSleepInBed(boolean bl, boolean bl2, CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if (serverPlayer.level().dimensionTypeRegistration().is(TRDimensionTypes.TARDIS)) {
            PlayerUtil.sendMessage(serverPlayer, ModMessages.TARDIS_SLEEP_END, true);
        }
    }

    @Inject(method = "setRespawnPosition(Lnet/minecraft/server/level/ServerPlayer$RespawnConfig;Z)V", at = @At("HEAD"), cancellable = true)
    public void setRespawnPosition(ServerPlayer.RespawnConfig respawnConfig, boolean bl, CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if(serverPlayer.level().dimensionTypeRegistration().is(TRDimensionTypes.TARDIS)){
            ci.cancel();
        }
    }

}
