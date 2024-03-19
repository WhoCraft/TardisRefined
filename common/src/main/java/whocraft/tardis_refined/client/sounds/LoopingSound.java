package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.registry.SoundRegistry;

public class LoopingSound extends AbstractTickableSoundInstance {

    public static LoopingSound ARS_HUMMING = null;
    public static LoopingSound FLIGHT_LOOP = null;
    public LoopingSound(@NotNull SoundEvent soundEvent, SoundSource soundSource) {
        super(soundEvent, soundSource, SoundInstance.createUnseededRandom());
        attenuation = Attenuation.NONE;
        looping = true;
        delay = 0;
        volume = 0.5f;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    public void setLocation(Vec3 location) {
        x = location.x;
        y = location.y;
        z = location.z;
    }

    public void stopSound() {
        Minecraft.getInstance()
                .getSoundManager()
                .stop(this);
    }

    @Override
    public void tick() {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) return;

        TardisClientData tardisClientData = TardisClientData.getInstance(Minecraft.getInstance().level.dimension());

        if(this == LoopingSound.ARS_HUMMING) {
            Vec3 playerVec = player.position();
            double distance = playerVec.distanceTo(new Vec3(x, y, z));
            double maxDistance = 11.0;
            double fadeFactor = Math.max(1.0 - distance / maxDistance, 0.0);
            float defaultVolume = 1.0f;
            volume = (float) (fadeFactor * defaultVolume);
        }

        if(this == LoopingSound.FLIGHT_LOOP){
            if (tardisClientData.isFlying() && !tardisClientData.isCrashing()) {
                LoopingSound.FLIGHT_LOOP.setLocation(player.position());
                volume = 0.5F;
            } else {
                volume = 0F;
            }
        }

        if (tardisClientData.getFuel() == 0f) {
            volume = 0F;
        }
    }

    @Override
    public Sound getSound() {
        return super.getSound();
    }

    public static boolean shouldMinecraftMusicStop(SoundManager soundManager){
        return soundManager.isActive(FLIGHT_LOOP) || soundManager.isActive(ARS_HUMMING) || soundManager.isActive(HumSoundManager.getCurrentSound());
    }

    public static void setupSounds(){
        LoopingSound.ARS_HUMMING = new LoopingSound(SoundRegistry.ARS_HUM.get(), SoundSource.AMBIENT);
        LoopingSound.FLIGHT_LOOP = new LoopingSound(SoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.AMBIENT);
    }

}
