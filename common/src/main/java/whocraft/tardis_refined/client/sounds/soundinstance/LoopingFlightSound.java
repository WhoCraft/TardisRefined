package whocraft.tardis_refined.client.sounds.soundinstance;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.client.sounds.LoopingTardisInteriorSound;

/** LoopingSound that is played during flight. At the moment, this is only the TARDIS_SINGLE_FLY sound*/
public class LoopingFlightSound extends LoopingTardisInteriorSound {

    public LoopingFlightSound(SoundEvent soundEvent, SoundSource soundSource) {
        super(soundEvent, soundSource);
    }

    @Override
    public void playSoundInstance(Player player) {

        if (this.getTardisClientData() != null){

            //Handle single fly loop sound logic
            //Can be moved to its own handler if more looping sounds are played during flight
            if (this.getTardisClientData().isFlying() && !this.getTardisClientData().isTakingOff() && !this.getTardisClientData().isLanding() && !getTardisClientData().isCrashing()) {
                this.setLocation(player.position());
                this.setVolume(0.5F);
            }

            //Stop playing this looping sound if the Tardis is out of fuel
            //Applies to any looping sound that applies during
            if (this.getTardisClientData().getFuel() == 0f) {
                this.setVolume(0F);
                this.stop();
            }
        }
    }
}
