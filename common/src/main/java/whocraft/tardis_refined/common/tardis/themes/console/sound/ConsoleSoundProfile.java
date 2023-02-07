package whocraft.tardis_refined.common.tardis.themes.console.sound;

public abstract class ConsoleSoundProfile {

    private ConsoleSound throttleEnable;
    private ConsoleSound throttleDisable;
    private ConsoleSound doorOpen;
    private ConsoleSound doorClose;
    private ConsoleSound doorLocked;
    private ConsoleSound generic;


    public ConsoleSound getThrottleEnable() {
        return throttleEnable;
    }

    public void setThrottleEnable(ConsoleSound throttleEnable) {
        this.throttleEnable = throttleEnable;
    }

    public ConsoleSound getThrottleDisable() {
        return throttleDisable;
    }

    public void setThrottleDisable(ConsoleSound throttleDisable) {
        this.throttleDisable = throttleDisable;
    }

    public ConsoleSound getDoorOpen() {
        return doorOpen;
    }

    public void setDoorOpen(ConsoleSound doorOpen) {
        this.doorOpen = doorOpen;
    }

    public ConsoleSound getDoorClose() {
        return doorClose;
    }

    public void setDoorClose(ConsoleSound doorClose) {
        this.doorClose = doorClose;
    }

    public ConsoleSound getDoorLocked() {
        return doorLocked;
    }

    public ConsoleSound getGeneric() {
        return generic;
    }

    public void setGeneric(ConsoleSound generic) {
        this.generic = generic;
    }
}


