package whocraft.tardis_refined.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRDimensionTypes;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @WrapOperation(
            method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    public boolean dropItemShellView(Level level, Entity entity, Operation<Boolean> original) {
        if (entity.level() != level) {
            return original.call(entity.level(), entity);
        }
        return original.call(level, entity);
    }

    @Unique
    private boolean tardis_refined$canBedSetSpawn() {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if(serverPlayer.level().dimensionTypeId() == TRDimensionTypes.TARDIS){
            return TardisLevelOperator.get(serverPlayer.serverLevel()).map(
                    operator -> operator.getInteriorManager().canBedSetSpawn()
            ).orElse(true);
        }
        return true;
    }

    @Inject(method = "stopSleepInBed(ZZ)V", at = @At("HEAD"))
    private void stopSleepInBed(boolean bl, boolean bl2, CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if (!tardis_refined$canBedSetSpawn()) {
            PlayerUtil.sendMessage(serverPlayer, ModMessages.TARDIS_SLEEP_END, true);
        }
    }

    @WrapWithCondition(
            method = "startSleepInBed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;setRespawnPosition(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/BlockPos;FZZ)V"
            )
    )
    public boolean setBedRespawnPosition(
            ServerPlayer instance, ResourceKey<Level> resourceKey,
            @Nullable BlockPos blockPos, float f, boolean bl, boolean bl2
    ) {
        return tardis_refined$canBedSetSpawn();
    }

}
