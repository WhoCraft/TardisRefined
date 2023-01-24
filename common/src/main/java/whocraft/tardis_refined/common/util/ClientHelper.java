package whocraft.tardis_refined.common.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;

public class ClientHelper {

    public static void playParticle(ClientLevel level, ParticleOptions type, BlockPos pos, double motionX, double motionY, double motionZ) {
        level.addParticle(type, pos.getX(), pos.getY(), pos.getZ(), motionX, motionY, motionZ);
    }

}
