package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class LoopingSound extends AbstractTickableSoundInstance {

    protected SoundEvent soundEvent;
    protected Player player;
    protected Level level;

    public LoopingSound(SoundEvent soundEvent, SoundSource soundSource, Attenuation attenuation) {
        super(soundEvent, soundSource, SoundInstance.createUnseededRandom());
        this.soundEvent = soundEvent;
        this.attenuation = attenuation;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.5f;
    }

    public LoopingSound(SoundEvent soundEvent, SoundSource soundSource) {
        this(soundEvent, soundSource, Attenuation.NONE);
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

    /** Gets the player which will be hearing the sound. Doign this saves us having to create multiple instances of the target player */
    public Player getPlayer() {
        return this.player;
    }
    /** Sets the player which will be hearing the sound. Doign this saves us having to create multiple instances of the target player */
    public LoopingSound setPlayer(Player player){
        this.player = player;
        return this;
    }

    /** Gets the desired level to play this sound in. By default, it will pick the player's level.
     * <br> Can be useful for sounds that need to be played in another dimension which the player is not currently located in*/
    public Level getLevel() {
        if (this.level == null && this.player != null){
            return this.player.level();
        }
        return this.level;
    }

    /** Sets the desired level to play this sound in. By default, it will pick the player's level.
     * <br> Can be useful for sounds that need to be played in another dimension which the player is not currently located in*/
    public LoopingSound setLevel(Level targetLevel){
        this.level = targetLevel;
        return this;
    }

    public void stopSound() {
        Minecraft.getInstance().getSoundManager().stop(this);
    }

    @Override
    public Sound getSound() {
        return super.getSound();
    }

    public SoundEvent getSoundEvent(){
        return this.soundEvent;
    }

    /** Add logic here to set volume etc.*/
    public void playSoundInstance(Player player){

    }

}
