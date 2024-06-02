package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.client.sounds.soundinstance.LoopingHumSound;

public class HumSoundManager {
    private static final Minecraft MC = Minecraft.getInstance();

    private static LoopingHumSound currentSound;
    private static SoundEvent currentRawSound = SoundEvents.CAT_DEATH;

    public static void playHum(SoundEvent soundEvent, Player player, Level targetLevel) {
        stopCurrentHum();
        LoopingHumSound newSound = createHumSound(soundEvent, player, targetLevel);
        currentSound = newSound;
        currentRawSound = soundEvent;
        MC.getSoundManager().play(newSound);
    }

    public static LoopingHumSound getCurrentHumSound() {
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

    private static LoopingHumSound createHumSound(SoundEvent soundEvent, Player player, Level targetLevel) {
        return (LoopingHumSound) new LoopingHumSound(soundEvent, SoundSource.AMBIENT).setPlayer(player).setLevel(targetLevel);
    }
}
