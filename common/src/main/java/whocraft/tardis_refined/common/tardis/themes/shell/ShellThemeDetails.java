package whocraft.tardis_refined.common.tardis.themes.shell;



import whocraft.tardis_refined.common.tardis.themes.shell.sound.GenericShellSoundProfile;
import whocraft.tardis_refined.common.tardis.themes.shell.sound.ShellSoundProfile;

public abstract class ShellThemeDetails {

    private ShellSoundProfile soundProfile = new GenericShellSoundProfile();

    public ShellSoundProfile getSoundProfile() {
        return soundProfile;
    }

    public ShellThemeDetails setSoundProfile(ShellSoundProfile soundProfile){
        this.soundProfile = soundProfile;
        return this;
    }

}