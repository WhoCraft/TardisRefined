package whocraft.tardis_refined.patterns.sound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import javax.annotation.Nullable;


/**
 * Sound for a specific action, featuring left, right and failure presses.
 **/
public record ConsoleSound(ConfiguredSound leftClick, ConfiguredSound rightClick) {

    public static final Codec<ConsoleSound> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                ConfiguredSound.CODEC.fieldOf("left_click").forGetter(ConsoleSound::leftClick),
                ConfiguredSound.CODEC.fieldOf("right_click").forGetter(ConsoleSound::rightClick)
        ).apply(instance, ConsoleSound::new);
    });


    /**
     * Datatype for referencing a sound event for how a player would interact with a control.
     *
     * @param leftClick  the sound event fired when a control is left-clicked.;
     * @param rightClick the sound event fired when a control is right-clicked;
     */
    public ConsoleSound(@Nullable ConfiguredSound leftClick, @Nullable ConfiguredSound rightClick) {
        this.leftClick = leftClick;
        this.rightClick = rightClick;
    }
}
