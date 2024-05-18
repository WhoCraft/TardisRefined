package whocraft.tardis_refined.compat.portals;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import qouteall.imm_ptl.core.portal.Portal;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.NbtConstants;

import java.util.Optional;
import java.util.UUID;

public class BOTIPortalEntity extends Portal {

    private static final EntityDataAccessor<Optional<UUID>> TARDIS_ID = SynchedEntityData.defineId(BOTIPortalEntity.class, EntityDataSerializers.OPTIONAL_UUID);


    // We don't save this as the portals die on server stop, we just need it in RAM
    ShellTheme shellTheme = ShellTheme.FACTORY.get();

    public ShellTheme getShellTheme() {
        return shellTheme;
    }

    public void setShellTheme(ShellTheme shellTheme) {
        this.shellTheme = shellTheme;
    }

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

    @Override
    public void tick() {
        UUID tardisId = getTardisId();
        contemplateExistence(tardisId);
        super.tick();
    }

    private void contemplateExistence(UUID tardisUuid) {
        PortalEntry portalEntry = ImmersivePortals.getPortalsForTardis(tardisUuid);
        if(portalEntry == null) return;
        if(!portalEntry.isPortalValidForEntry(this)){
            kill();
        }
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
