package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.TRDimensionTypes;

public class LoopingHumSound extends LoopingSound {
    private SoundEvent soundEvent;

    public LoopingHumSound(@NotNull SoundEvent soundEvent, SoundSource soundSource) {
        super(soundEvent, soundSource);
        this.soundEvent = soundEvent;
    }

    public void setSoundEvent(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    @Override
    public void tick() {
        super.tick();
        volume = 0.10F;
        LocalPlayer player = Minecraft.getInstance().player;

        if (soundEvent.getLocation().getNamespace().contains(NbtConstants.MINECRAFT)) {
            volume = 1f;
        } else {
            volume = 0.10F;
        }

        if (player != null) {
            setLocation(player.position());

            if (player.level().dimensionTypeId() != TRDimensionTypes.TARDIS) {
                stopSound();
            }

        }


    }

    // So, I don't like this. But it is necessary else we are making another Mixin, which isn't a bad thing but better to avoid
    // You need to call setSoundEvent and then call resolve, it's...yeah ok there's no excusing this
    @Override
    public WeighedSoundEvents resolve(SoundManager soundManager) {
        if (this.location.equals(SoundManager.INTENTIONALLY_EMPTY_SOUND_LOCATION)) {
            this.sound = SoundManager.INTENTIONALLY_EMPTY_SOUND;
            return SoundManager.INTENTIONALLY_EMPTY_SOUND_EVENT;
        } else {
            WeighedSoundEvents weighedSoundEvents = soundManager.getSoundEvent(soundEvent.getLocation());
            if (weighedSoundEvents == null) {
                this.sound = SoundManager.EMPTY_SOUND;
            } else {
                this.sound = weighedSoundEvents.getSound(this.random);
            }

            return weighedSoundEvents;
        }
    }
}
