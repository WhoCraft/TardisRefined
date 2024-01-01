package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.HumSoundManager;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.registry.DimensionTypes;
import whocraft.tardis_refined.registry.SoundRegistry;

public class QuickSimpleSound extends AbstractTickableSoundInstance {

    public QuickSimpleSound(@NotNull SoundEvent soundEvent, SoundSource soundSource) {
        super(soundEvent, soundSource, SoundInstance.createUnseededRandom());
        attenuation = Attenuation.NONE;
        looping = false;
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
    public Sound getSound() {
        return super.getSound();
    }


    @Override
    public void tick() {

    }
}
