package whocraft.tardis_refined.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.GravityUtil;

import static whocraft.tardis_refined.common.util.TardisHelper.isInArsArea;

public class LoopingTardisInteriorSound extends LoopingSound{
    public LoopingTardisInteriorSound(@NotNull SoundEvent soundEvent, SoundSource soundSource) {
        super(soundEvent, soundSource);
    }

    @Override
    public void tick() {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) {
            this.stop();
            return;
        }

        if (isInArsArea(Minecraft.getInstance().player.blockPosition())) {
            Vec3 playerVec = player.position();
            double distance = playerVec.distanceTo(new Vec3(x, y, z));
            double maxDistance = 11.0;
            double fadeFactor = Math.max(1.0 - distance / maxDistance, 0.0);
            float defaultVolume = 1.0f;
            volume = (float) (fadeFactor * defaultVolume);
        }
        if (GravityUtil.isInGravityShaft(Minecraft.getInstance().player)) {
            this.setLocation(player.position());
            volume = 0.5F;
        }
        else {
            volume = 0F;
            this.stop();
        }
    }
}
