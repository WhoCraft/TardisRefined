package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.TardisClientData;

public class LoopingHumSound extends LoopingSound{
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


        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null){
            setLocation(player.position());

            // Assume we're in the Tardis
            TardisClientData data = TardisClientData.getInstance(Minecraft.getInstance().level.dimension());
            volume = (data.getFuel() == 0) ? 0 : 0.10F;
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
