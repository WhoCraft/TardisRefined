package whocraft.tardis_refined.common.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.imm_ptl.core.portal.Portal;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.compat.portals.TardisPortalData;

import java.util.UUID;

@Mixin(Portal.class)
public interface PortalAccessorMixin {
    @Accessor
    public void readPortalDataFromNbt(CompoundTag compound);
}
