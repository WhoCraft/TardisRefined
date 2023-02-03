package whocraft.tardis_refined.common.tardis.themes.console.sound;

import net.minecraft.sounds.SoundEvents;

public class GenericConsoleSoundProfile extends ConsoleSoundProfile {

    public GenericConsoleSoundProfile() {
        setThrottleEnable(new ConsoleSound(null, new PitchedSound(SoundEvents.LEVER_CLICK, 0.6f)));
        setThrottleDisable(new ConsoleSound(null, new PitchedSound(SoundEvents.LEVER_CLICK, 0.5f)));
        setDoorOpen(new ConsoleSound(null, new PitchedSound(SoundEvents.LEVER_CLICK, 0.6f)));
        setDoorClose(new ConsoleSound(null, new PitchedSound(SoundEvents.LEVER_CLICK, 0.5f)));
        setGeneric(new ConsoleSound(new PitchedSound(SoundEvents.STONE_BUTTON_CLICK_ON, 0.95f), new PitchedSound(SoundEvents.STONE_BUTTON_CLICK_ON, 1.1f)));
    }

}
