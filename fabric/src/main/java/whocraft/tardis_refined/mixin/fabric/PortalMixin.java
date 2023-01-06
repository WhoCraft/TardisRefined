package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.imm_ptl.core.portal.Portal;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerIP;
import whocraft.tardis_refined.common.dimension.fabric.TARDISPortalData;

import java.util.UUID;

@Mixin(value = Portal.class, remap = false)
public abstract class PortalMixin implements TARDISPortalData {
    @Shadow
    public abstract void reloadAndSyncToClient();

    @Unique
    private UUID tardisID;

    @Override
    public void setTardisID(UUID tardisID) {
        this.tardisID = tardisID;
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "INVOKE", target = "Lqouteall/q_misc_util/my_util/SignalBiArged;emit(Ljava/lang/Object;Ljava/lang/Object;)V", shift = At.Shift.AFTER), remap = true)
    private void addTARDISData(CompoundTag compoundTag, CallbackInfo ci) {
        if(!((Portal) (Object) this).level.isClientSide) {
            if (tardisID != null) {
                compoundTag.putUUID("tardis_id", tardisID);
            }
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "INVOKE", target = "Lqouteall/q_misc_util/my_util/SignalBiArged;emit(Ljava/lang/Object;Ljava/lang/Object;)V", shift = At.Shift.AFTER), remap = true)
    private void readTARDISData(CompoundTag compoundTag, CallbackInfo ci) {
        Portal thisPortal = (Portal) (Object) this;

        if(!thisPortal.level.isClientSide) {
            if (compoundTag.contains("tardis_id")) {
                tardisID = compoundTag.getUUID("tardis_id");
                if (DimensionHandlerIP.tardisToPortalsMap.get(tardisID) == null) {
                    thisPortal.kill();
                    reloadAndSyncToClient();
                } else {
                    for (Portal portal : DimensionHandlerIP.tardisToPortalsMap.get(tardisID)) {
                        if (portal == null) {
                            thisPortal.kill();
                            reloadAndSyncToClient();
                        }
                    }
                }
            }
        }
    }
}
