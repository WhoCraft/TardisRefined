package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.client.TRSoundInstances;
import whocraft.tardis_refined.client.TardisClientData;

public class LoopingFlightSound extends LoopingSound{

    public LoopingFlightSound(SoundEvent soundEvent, SoundSource soundSource) {
        super(soundEvent, soundSource);
    }

    @Override
    public void tick() {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) {
            this.stop();
            return;
        }

        Level level = player.level();

        TardisClientData tardisClientData = TardisClientData.getInstance(level.dimension());
        if (tardisClientData != null){
            //Handle single fly loop sound logic
            if(this == TRSoundInstances.TARDIS_SINGLE_FLY){
                if (tardisClientData.isFlying() && !tardisClientData.isTakingOff() && !tardisClientData.isLanding() && !tardisClientData.isCrashing()) {
                    this.setLocation(player.position());
                    volume = 0.5F;
                }
            }
            //Stop playing this looping sound if the Tardis is out of fuel
            if (tardisClientData.getFuel() == 0f) {
                volume = 0F;
                this.stop();
            }
        }
        else {
            volume = 0F;
            this.stop();
        }



    }
}
