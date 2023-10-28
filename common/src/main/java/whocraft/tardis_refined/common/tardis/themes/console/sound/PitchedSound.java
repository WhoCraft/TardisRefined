package whocraft.tardis_refined.common.tardis.themes.console.sound;

import net.minecraft.sounds.SoundEvent;


/**
 * A small link between a SoundEvent and intended pitch to play.
 **/
public class PitchedSound {

    private final SoundEvent soundEvent;
    private final float pitch;

    public PitchedSound(SoundEvent event, float pitch) {
        this.soundEvent = event;
        this.pitch = pitch;
    }

    public PitchedSound(SoundEvent event){
        this(event, 1F);
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    public float getPitch() {
        return pitch;
    }
}
