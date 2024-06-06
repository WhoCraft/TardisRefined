package whocraft.tardis_refined.common.tardis.themes.console.sound;

public abstract class ConsoleSoundProfile {

    private ConsoleSound throttleEnable;
    private ConsoleSound throttleDisable;
    private ConsoleSound handbrakeEnable;
    private ConsoleSound handbrakeDisable;
    private ConsoleSound doorOpen;
    private ConsoleSound doorClose;
    private ConsoleSound doorLocked;
    private ConsoleSound doorUnlocked;
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

    public ConsoleSound getHandbrakeEnable() {
        return handbrakeEnable;
    }

    public void setHandbrakeEnable(ConsoleSound handbrakeEnable) {
        this.handbrakeEnable = handbrakeEnable;
    }

    public ConsoleSound getHandbrakeDisable() {
        return handbrakeDisable;
    }

    public void setHandbrakeDisable(ConsoleSound handbrakeDisable) {
        this.handbrakeDisable = handbrakeDisable;
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

    public void setDoorLocked(ConsoleSound doorLocked) { this.doorLocked = doorLocked;}

    public ConsoleSound getDoorUnlocked() {
        return doorUnlocked;
    }

    public void setDoorUnlocked(ConsoleSound doorUnlocked) {
        this.doorUnlocked = doorUnlocked;
    }

    public ConsoleSound getGeneric() {
        return generic;
    }

    public void setGeneric(ConsoleSound generic) {
        this.generic = generic;
    }
}


