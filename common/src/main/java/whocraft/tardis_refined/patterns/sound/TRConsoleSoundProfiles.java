package whocraft.tardis_refined.patterns.sound;

import net.minecraft.sounds.SoundEvents;

public class TRConsoleSoundProfiles {

    public static ConfiguredSound EMPTY_SOUND = new ConfiguredSound(SoundEvents.EMPTY, 1f, 0F);
    public static ConfiguredSound DEFAULT_LEFT_CLICK = new ConfiguredSound(SoundEvents.STONE_BUTTON_CLICK_ON, 0.95f, 1F);
    public static ConfiguredSound DEFAULT_RIGHT_CLICK = new ConfiguredSound(SoundEvents.STONE_BUTTON_CLICK_ON, 1.1f, 1F);
    public static ConfiguredSound DEFAULT_FLIGHT_CONTROL_ENABLE = new ConfiguredSound(SoundEvents.LEVER_CLICK, 0.6f, 1F);
    public static ConfiguredSound DEFAULT_FLIGHT_CONTROL_DISABLE = new ConfiguredSound(SoundEvents.LEVER_CLICK, 0.5f, 1F);


    public static ConsoleSoundProfile DEFAULT_SOUND_PROFILE = new ConsoleSoundProfile()
            .setGeneric(new ConsoleSound(DEFAULT_LEFT_CLICK, DEFAULT_RIGHT_CLICK))
            .setHandbrakeEnable(new ConsoleSound(EMPTY_SOUND, DEFAULT_FLIGHT_CONTROL_ENABLE))
            .setHandbrakeDisable(new ConsoleSound(EMPTY_SOUND, DEFAULT_FLIGHT_CONTROL_DISABLE))
            .setThrottleEnable(new ConsoleSound(EMPTY_SOUND, DEFAULT_FLIGHT_CONTROL_ENABLE))
            .setThrottleDisable(new ConsoleSound(EMPTY_SOUND, DEFAULT_FLIGHT_CONTROL_DISABLE));

}
