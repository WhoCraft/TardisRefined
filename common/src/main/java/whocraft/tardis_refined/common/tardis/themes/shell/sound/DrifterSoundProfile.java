package whocraft.tardis_refined.common.tardis.themes.shell.sound;

import net.minecraft.sounds.SoundEvents;
import whocraft.tardis_refined.common.tardis.themes.PitchedSound;

public class DrifterSoundProfile extends ShellSoundProfile{

    public DrifterSoundProfile() {
        this.setDoorOpen(new PitchedSound(SoundEvents.WOOL_PLACE, 1F));
        this.setDoorClose(new PitchedSound(SoundEvents.WOOL_BREAK, 1F));
        this.setDoorLocked(new PitchedSound(SoundEvents.WOODEN_TRAPDOOR_CLOSE, 1F));
        this.setDoorUnlocked(new PitchedSound(SoundEvents.WOODEN_TRAPDOOR_OPEN, 1F));
    }

}
