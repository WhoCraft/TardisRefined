package whocraft.tardis_refined.patterns.sound;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import whocraft.tardis_refined.common.tardis.themes.ConfiguredSound;

public class TRShellSoundProfiles {

    public static ConfiguredSound DEFAULT_DOOR_OPEN = new ConfiguredSound(SoundEvents.IRON_DOOR_OPEN, 1F, 1F);
    public static ConfiguredSound DEFAULT_DOOR_CLOSE = new ConfiguredSound(SoundEvents.IRON_DOOR_CLOSE, 1F, 1.4F);
    public static ConfiguredSound DEFAULT_DOOR_LOCK = new ConfiguredSound(BlockSetType.IRON.doorClose(), 1F, 1.4F);
    public static ConfiguredSound DEFAULT_DOOR_UNLOCK = new ConfiguredSound(BlockSetType.IRON.doorOpen(), 1F, 1F);

    public static ShellSoundProfile DEFAULT_SOUND_PROFILE = new ShellSoundProfile()
            .setDoorOpen(DEFAULT_DOOR_OPEN)
            .setDoorClose(DEFAULT_DOOR_CLOSE)
            .setDoorLocked(DEFAULT_DOOR_LOCK)
            .setDoorUnlocked(DEFAULT_DOOR_UNLOCK);

    public static ShellSoundProfile HALF_BAKED_SOUND_PROFILE = new ShellSoundProfile()
            .setDoorOpen(DEFAULT_DOOR_OPEN)
            .setDoorClose(DEFAULT_DOOR_CLOSE)
            .setDoorLocked(new ConfiguredSound(SoundEvents.WEEPING_VINES_PLACE, 1F, 1.4F))
            .setDoorUnlocked(new ConfiguredSound(SoundEvents.WEEPING_VINES_BREAK, 1F, 1.4F));

    public static ShellSoundProfile WOOL_SOUND_PROFILE = new ShellSoundProfile()
            .setDoorOpen(new ConfiguredSound(SoundEvents.WOOL_PLACE, 1F, 1F))
            .setDoorClose(new ConfiguredSound(SoundEvents.WOOL_BREAK, 1F, 1.4F))
            .setDoorLocked(new ConfiguredSound(SoundEvents.WEEPING_VINES_PLACE, 1F, 1.4F))
            .setDoorUnlocked(new ConfiguredSound(SoundEvents.WEEPING_VINES_BREAK, 1F, 1F));

    public static ShellSoundProfile WOODEN_SOUND_PROFILE = new ShellSoundProfile()
            .setDoorOpen(new ConfiguredSound(BlockSetType.SPRUCE.doorOpen(), 1F, 1F))
            .setDoorClose(new ConfiguredSound(BlockSetType.SPRUCE.doorClose(), 1F, 1.4F))
            .setDoorLocked(new ConfiguredSound(BlockSetType.SPRUCE.trapdoorClose(), 1F, 1.4F))
            .setDoorUnlocked(new ConfiguredSound(BlockSetType.SPRUCE.trapdoorOpen(), 1F, 1F));
}
