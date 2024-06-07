package whocraft.tardis_refined.common.tardis.themes.shell.sound;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import whocraft.tardis_refined.common.tardis.themes.PitchedSound;

public class GenericShellSoundProfile extends ShellSoundProfile {

    public GenericShellSoundProfile() {
        this.setDoorOpen(new PitchedSound(SoundEvents.IRON_DOOR_OPEN, 1F));
        this.setDoorClose(new PitchedSound(SoundEvents.IRON_DOOR_CLOSE, 1F));
        this.setDoorLocked(new PitchedSound(BlockSetType.IRON.doorClose(), 1F));
        this.setDoorUnlocked(new PitchedSound(BlockSetType.IRON.doorOpen(), 1F));
    }

}
