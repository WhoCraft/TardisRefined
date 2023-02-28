package whocraft.tardis_refined.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticleGallifrey extends BaseAshSmokeParticle {


    private final SpriteSet sprites;

    protected ParticleGallifrey(ClientLevel arg, double d, double e, double f, double g, double h, double i, float j, SpriteSet arg2) {
        super(arg, d, e, f, 0.0F, 0.1F, 0.0F, g, h, i, j, arg2, 0.3F, 8, -0.1F, true);
        this.sprites = arg2;
        setColor(1,1,1);
        setLifetime(lifetime * 2);
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float f) {
        return 0.05F;
    }

    @Override
    public void tick() {
        super.tick();
        setColor(1, 1, 1);
        if (age % 40 == 0) {
            this.setSpriteFromAge(this.sprites);
        }
    }


    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet arg) {
            this.sprite = arg;
        }


        public Particle createParticle(SimpleParticleType arg, ClientLevel arg2, double d, double e, double f, double g, double h, double i) {
            ParticleGallifrey particleGallifrey = new ParticleGallifrey(arg2, d, e, f, g, h, i, 1.0F, this.sprite);
            particleGallifrey.pickSprite(this.sprite);
            return particleGallifrey;
        }
    }
}
