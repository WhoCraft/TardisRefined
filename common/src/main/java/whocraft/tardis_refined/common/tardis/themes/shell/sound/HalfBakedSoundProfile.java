package whocraft.tardis_refined.common.tardis.themes.shell.sound;

import net.minecraft.sounds.SoundEvents;
import whocraft.tardis_refined.common.tardis.themes.PitchedSound;

public class HalfBakedSoundProfile extends ShellSoundProfile{

    public HalfBakedSoundProfile() {
        this.setDoorOpen(new PitchedSound(SoundEvents.IRON_DOOR_OPEN, 1F));
        this.setDoorClose(new PitchedSound(SoundEvents.IRON_DOOR_CLOSE, 1.4F));
        this.setDoorLocked(new PitchedSound(SoundEvents.CROP_PLANTED, 1.4F));
        this.setDoorUnlocked(new PitchedSound(SoundEvents.CROP_BREAK, 1F));
    }

}
