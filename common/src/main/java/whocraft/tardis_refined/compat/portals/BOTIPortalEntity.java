package whocraft.tardis_refined.compat.portals;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import qouteall.imm_ptl.core.portal.Portal;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.constants.NbtConstants;

import java.util.Optional;
import java.util.UUID;

public class BOTIPortalEntity extends Portal {

    private static final EntityDataAccessor<Optional<UUID>> TARDIS_ID = SynchedEntityData.defineId(BOTIPortalEntity.class, EntityDataSerializers.OPTIONAL_UUID);


    public BOTIPortalEntity(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        UUID tardisId = getTardisId();
        if (tardisId != null) {
            compoundTag.putUUID(NbtConstants.TARDIS_ID, tardisId);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains(NbtConstants.TARDIS_ID)) {
            setTardisId(compoundTag.getUUID(NbtConstants.TARDIS_ID));
        }
    }

    public boolean isValidToStayAlive(){


        return false;
    }

    @Override
    public void tick() {
        UUID tardisId = getTardisId();
        if (tardisId == null || !(level() instanceof ServerLevel serverLevel)) {
            return;
        }



        if (!ImmersivePortals.tardisToPortalsMap.containsKey(tardisId)) {
            //kill();
            if (serverLevel.dimension().location().getPath().equals(tardisId.toString())) {
                TardisLevelOperator.get(serverLevel).ifPresent(ImmersivePortals::createPortals);
            }
        } else {
            for (Portal portal : ImmersivePortals.tardisToPortalsMap.get(tardisId)) {
                if (portal == null) {
                    //kill();
                    break;
                }
            }
        }

        super.tick();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(TARDIS_ID, Optional.of(UUID.randomUUID()));
    }

    public UUID getTardisId() {
        return this.getEntityData().get(TARDIS_ID).get();
    }

    public void setTardisId(UUID tardisId) {
        this.getEntityData().set(TARDIS_ID, Optional.of(tardisId));
    }

}
