package whocraft.tardis_refined.common.tardis.themes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

/**
 * An abstraction over the SoundEvent object which allows datapacks to define and intended pitch and volume to play.
 **/
public class ConfiguredSound {

    public static final Codec<ConfiguredSound> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(ConfiguredSound::getSoundEventKey),
                Codec.FLOAT.fieldOf("pitch").forGetter(ConfiguredSound::getPitch),
                Codec.FLOAT.fieldOf("volume").forGetter(ConfiguredSound::getVolume)
        ).apply(instance, ConfiguredSound::new);
    });


    private final ResourceLocation soundEventKey;
    private final float pitch;

    private final float volume;


    /** Constructor for data driven entries*/
    public ConfiguredSound(ResourceLocation soundEventKey, float pitch, float volume) {
        this.soundEventKey = soundEventKey;
        this.pitch = pitch;
        this.volume = volume;
    }

    public ConfiguredSound(ResourceLocation event, float pitch){
        this(event, pitch, 1F);
    }

    public ConfiguredSound(ResourceLocation event){
        this(event, 1F, 1F);
    }

    public ConfiguredSound(SoundEvent soundEvent, float pitch, float volume) {
        this.soundEventKey = soundEvent.getLocation();
        this.pitch = pitch;
        this.volume = volume;
    }

    public ConfiguredSound(SoundEvent soundEvent, float pitch){
        this(soundEvent, pitch, 1F);
    }

    public ConfiguredSound(SoundEvent event){
        this(event, 1F, 1F);
    }

    public SoundEvent getSoundEvent(Level level) {
        return level.registryAccess().registry(Registries.SOUND_EVENT).get().get(this.soundEventKey);
    }

    public ResourceLocation getSoundEventKey() {
        return this.soundEventKey;
    }

    public float getPitch() {
        return pitch;
    }

    public float getVolume() {
        return this.volume;
    }
}
