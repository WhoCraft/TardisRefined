package whocraft.tardis_refined.client.fabric;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class TRParticlesImpl {
    public static SimpleParticleType getParticleType() {
        return FabricParticleTypes.simple();
    }
}
