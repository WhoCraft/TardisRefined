package whocraft.tardis_refined.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRDimensionTypes;
import whocraft.tardis_refined.registry.TRUpgrades;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {



    @Inject(method = "stopSleepInBed(ZZ)V", at = @At("HEAD"))
    private void stopSleepInBed(boolean bl, boolean bl2, CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if (serverPlayer.serverLevel() != null) {
            if (serverPlayer.level().dimensionTypeId() == TRDimensionTypes.TARDIS) {
                TardisLevelOperator.get(serverPlayer.serverLevel()).ifPresent(x -> {
                    var upgradeHandler = x.getUpgradeHandler();
                    if (!upgradeHandler.isUpgradeUnlocked(TRUpgrades.RESPAWN_ALLOWED.get())) {
                        PlayerUtil.sendMessage(serverPlayer, ModMessages.TARDIS_SLEEP_END, true);
                    }
                });
            }
        }
    }

    @Inject(method = "setRespawnPosition(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/BlockPos;FZZ)V", at = @At("HEAD"), cancellable = true)
    public void setRespawnPosition(ResourceKey<Level> resourceKey, BlockPos blockPos, float f, boolean bl, boolean bl2, CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if (serverPlayer.serverLevel() != null) {
            if (serverPlayer.level().dimensionTypeId() == TRDimensionTypes.TARDIS) {
                TardisLevelOperator.get(serverPlayer.serverLevel()).ifPresent(x -> {
                    var upgradeHandler = x.getUpgradeHandler();
                    if (!upgradeHandler.isUpgradeUnlocked(TRUpgrades.RESPAWN_ALLOWED.get())) {
                        ci.cancel();
                    }
                });
            }
        }
    }

}
