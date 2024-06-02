package whocraft.tardis_refined.client;

import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundSource;
import whocraft.tardis_refined.client.sounds.HumSoundManager;
import whocraft.tardis_refined.client.sounds.LoopingFlightSound;
import whocraft.tardis_refined.client.sounds.LoopingTardisInteriorSound;
import whocraft.tardis_refined.client.sounds.QuickSimpleSound;
import whocraft.tardis_refined.registry.TRSoundRegistry;

public class TRSoundInstances {
    public static LoopingTardisInteriorSound ARS_HUMMING = new LoopingTardisInteriorSound(TRSoundRegistry.ARS_HUM.get(), SoundSource.AMBIENT);
    public static LoopingTardisInteriorSound GRAVITY_LOOP = new LoopingTardisInteriorSound(TRSoundRegistry.GRAVITY_TUNNEL.get(), SoundSource.AMBIENT);

    public static LoopingFlightSound TARDIS_SINGLE_FLY = new LoopingFlightSound(TRSoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.AMBIENT);

    public static QuickSimpleSound INTERIOR_VOICE =  new QuickSimpleSound(TRSoundRegistry.INTERIOR_VOICE.get(), SoundSource.AMBIENT);

    public static boolean shouldMinecraftMusicStop(SoundManager soundManager){
        return soundManager.isActive(TARDIS_SINGLE_FLY) || soundManager.isActive(ARS_HUMMING) || soundManager.isActive(HumSoundManager.getCurrentHumSound());
    }

}
