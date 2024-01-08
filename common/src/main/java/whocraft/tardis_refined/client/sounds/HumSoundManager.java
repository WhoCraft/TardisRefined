package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class HumSoundManager {
    private static final Minecraft MC = Minecraft.getInstance();

    private static LoopingHumSound currentSound;
    private static SoundEvent currentRawSound = SoundEvents.CAT_DEATH;

    public static void playHum(SoundEvent soundEvent) {
        stopCurrentHum();
        LoopingHumSound newSound = createSound(soundEvent);
        currentSound = newSound;
        currentRawSound = soundEvent;
        MC.getSoundManager().play(newSound);
    }

    public static LoopingHumSound getCurrentSound() {
        return currentSound;
    }

    public static void setCurrentSound(LoopingHumSound currentSound) {
        HumSoundManager.currentSound = currentSound;
    }

    public static SoundEvent getCurrentRawSound() {
        return currentRawSound;
    }

    public static void setCurrentRawSound(SoundEvent currentRawSound) {
        HumSoundManager.currentRawSound = currentRawSound;
    }

    private static void stopCurrentHum() {
        if (currentSound != null) {
            MC.getSoundManager().stop(currentSound);
            currentSound = null;
        }
    }

    private static LoopingHumSound createSound(SoundEvent soundEvent) {
        return new LoopingHumSound(soundEvent, SoundSource.AMBIENT);
    }
}
