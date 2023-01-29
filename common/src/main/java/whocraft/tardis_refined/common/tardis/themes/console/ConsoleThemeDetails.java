package whocraft.tardis_refined.common.tardis.themes.console;

import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.console.sound.ConsoleSoundProfile;
import whocraft.tardis_refined.common.tardis.themes.console.sound.GenericConsoleSoundProfile;

public abstract class ConsoleThemeDetails {

    ConsoleSoundProfile soundProfile = new GenericConsoleSoundProfile();

    public ControlSpecification[] getControlSpecification() {
        return null;
    };

    public ConsoleSoundProfile getSoundProfile() {
        return soundProfile;
    }

}
