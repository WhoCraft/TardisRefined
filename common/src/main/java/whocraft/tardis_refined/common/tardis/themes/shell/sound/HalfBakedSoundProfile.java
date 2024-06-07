package whocraft.tardis_refined.common.tardis.themes.shell.sound;

import net.minecraft.sounds.SoundEvents;
import whocraft.tardis_refined.common.tardis.themes.PitchedSound;

public class HalfBakedSoundProfile extends ShellSoundProfile{

    public HalfBakedSoundProfile() {
        this.setDoorOpen(new PitchedSound(SoundEvents.IRON_DOOR_OPEN, 1F));
        this.setDoorClose(new PitchedSound(SoundEvents.IRON_DOOR_CLOSE, 1F));
        this.setDoorLocked(new PitchedSound(SoundEvents.WEEPING_VINES_PLACE, 1F));
        this.setDoorUnlocked(new PitchedSound(SoundEvents.WEEPING_VINES_BREAK, 1F));
    }

}
