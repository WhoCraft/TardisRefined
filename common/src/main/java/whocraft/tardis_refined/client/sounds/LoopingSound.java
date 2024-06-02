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
import whocraft.tardis_refined.common.GravityUtil;
import whocraft.tardis_refined.registry.TRSoundRegistry;

public abstract class LoopingSound extends AbstractTickableSoundInstance {

    protected SoundEvent soundEvent;

    public LoopingSound(@NotNull SoundEvent soundEvent, SoundSource soundSource) {
        super(soundEvent, soundSource, SoundInstance.createUnseededRandom());
        this.soundEvent = soundEvent;
        this.attenuation = Attenuation.NONE;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.5f;
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
        this.x = location.x;
        this.y = location.y;
        this.z = location.z;
    }

    public void stopSound() {
        Minecraft.getInstance()
                .getSoundManager()
                .stop(this);
    }

    @Override
    public Sound getSound() {
        return super.getSound();
    }

    public SoundEvent getSoundEvent(){
        return this.soundEvent;
    }

}
