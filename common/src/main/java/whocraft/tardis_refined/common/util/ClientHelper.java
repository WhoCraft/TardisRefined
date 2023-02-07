package whocraft.tardis_refined.common.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;

public class ClientHelper {

    public static void playParticle(ClientLevel level, ParticleOptions type, Vec3 pos, double motionX, double motionY, double motionZ) {
        level.addParticle(type, pos.x(), pos.y(), pos.z(), motionX, motionY, motionZ);
    }

}
