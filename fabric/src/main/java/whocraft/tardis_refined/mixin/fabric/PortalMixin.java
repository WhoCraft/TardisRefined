package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.imm_ptl.core.portal.Portal;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerIP;
import whocraft.tardis_refined.common.dimension.fabric.TARDISPortalData;

import java.util.UUID;

@Mixin(value = Portal.class, remap = false)
public abstract class PortalMixin implements TARDISPortalData {
    @Unique
    private UUID tardisID;

    @Override
    public void setTardisID(UUID tardisID) {
        this.tardisID = tardisID;
    }

    @Inject(method = "tick", at = @At(value = "HEAD"), remap = true)
    private void onTick(CallbackInfo ci) {
        Portal thisPortal = (Portal) (Object) this;
        // Just in case the portal persists
        if(this.tardisID != null && !thisPortal.level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) thisPortal.level;
            if (DimensionHandlerIP.tardisToPortalsMap.get(tardisID) == null) {
                thisPortal.kill();
                if(serverLevel.dimension().location().getPath().equals(tardisID.toString())) {
                    TardisLevelOperator.get(serverLevel).ifPresent(DimensionHandlerIP::createPortals);
                }
            } else {
                for (Portal portal : DimensionHandlerIP.tardisToPortalsMap.get(tardisID)) {
                    if (portal == null) {
                        thisPortal.kill();
                        break;
                    }
                }
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "INVOKE", target = "Lqouteall/q_misc_util/my_util/SignalBiArged;emit(Ljava/lang/Object;Ljava/lang/Object;)V", shift = At.Shift.AFTER), remap = true)
    private void addTARDISData(CompoundTag compoundTag, CallbackInfo ci) {
        if (tardisID != null) {
            compoundTag.putUUID("tardis_id", tardisID);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "INVOKE", target = "Lqouteall/q_misc_util/my_util/SignalBiArged;emit(Ljava/lang/Object;Ljava/lang/Object;)V", shift = At.Shift.AFTER), remap = true)
    private void readTARDISData(CompoundTag compoundTag, CallbackInfo ci) {
        if (compoundTag.contains("tardis_id")) {
            tardisID = compoundTag.getUUID("tardis_id");
        }
    }
}
