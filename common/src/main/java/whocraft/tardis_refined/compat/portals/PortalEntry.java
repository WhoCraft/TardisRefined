package whocraft.tardis_refined.compat.portals;

import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.UUID;

public class PortalEntry {

    private BOTIPortalEntity internalPortal, shellPortal;
    ShellTheme shellTheme;
    UUID tardisId;

    public PortalEntry(BOTIPortalEntity internalPortal, BOTIPortalEntity shellPortal, ShellTheme shellTheme, UUID tardisId) {
        this.internalPortal = internalPortal;
        this.shellPortal = shellPortal;
        this.shellTheme = shellTheme;
        this.tardisId = tardisId;
    }

    public UUID getTardisId() {
        return tardisId;
    }

    public ShellTheme getShellTheme() {
        return shellTheme;
    }

    public BOTIPortalEntity getShellPortal() {
        return shellPortal;
    }

    public BOTIPortalEntity getInternalPortal() {
        return internalPortal;
    }

    public boolean isPortalValidForEntry(BOTIPortalEntity portalEntity){
        return portalEntity.getUUID() != internalPortal.getUUID() && portalEntity.getUUID() != shellPortal.getUUID();
    }

}
