package whocraft.tardis_refined.common.tardis.themes.shell.sound;

import whocraft.tardis_refined.common.tardis.themes.PitchedSound;

public abstract class ShellSoundProfile {

    private PitchedSound doorOpen;
    private PitchedSound doorClose;
    private PitchedSound doorLocked;

    private PitchedSound doorUnlocked;

    public PitchedSound getDoorOpen() {
        return doorOpen;
    }

    public void setDoorOpen(PitchedSound doorOpen) {
        this.doorOpen = doorOpen;
    }

    public PitchedSound getDoorClose() {
        return doorClose;
    }

    public void setDoorClose(PitchedSound doorClose) {
        this.doorClose = doorClose;
    }

    public PitchedSound getDoorLocked() {
        return doorLocked;
    }

    public void setDoorLocked(PitchedSound doorLocked) { this.doorLocked = doorLocked;}

    public PitchedSound getDoorUnlocked() {
        return this.doorUnlocked;
    }

    public void setDoorUnlocked(PitchedSound doorUnlocked) {
        this.doorUnlocked = doorUnlocked;
    }
}


