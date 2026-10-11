package whocraft.tardis_refined.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class ParticleGallifrey extends BaseAshSmokeParticle {


    private final SpriteSet sprites;

    protected ParticleGallifrey(ClientLevel arg, double d, double e, double f, double g, double h, double i, float j, SpriteSet arg2) {
        super(arg, d, e, f, 0.0F, 0.1F, 0.0F, g, h, i, j, arg2, 0.3F, 8, -0.1F, true);
        this.sprites = arg2;
        setColor(1, 1, 1);
        setLifetime(lifetime * 2);
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


        public Particle createParticle(SimpleParticleType arg, ClientLevel arg2, double d, double e, double f, double g, double h, double i, RandomSource random) {
            ParticleGallifrey particleGallifrey = new ParticleGallifrey(arg2, d, e, f, g, h, i, 1.0F, this.sprite);
            return particleGallifrey;
        }
    }

    private static class ARSVinesSmokeParticle extends BaseAshSmokeParticle {
        ARSVinesSmokeParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites, RandomSource random) {
            super(level, x, y, z, 0.0F, 0.1F, 0.0F, 0.2F, 0.2F, 0.35F, 1.0F, sprites, 0.3F, 8, -0.1F, true);
            setLifetime(Mth.randomBetweenInclusive(random, 500, 1000));
            gravity = 0.01F;
            setColor(0.4F, 0.4F, 0.7F);
        }
    }

    public static class ARSVinesParticle implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ARSVinesParticle(SpriteSet arg) {
            this.sprite = arg;
        }

        @Override
        public Particle createParticle(SimpleParticleType arg, ClientLevel arg2, double d, double e, double f, double g, double h, double i, RandomSource random) {
            ARSVinesSmokeParticle particle = new ARSVinesSmokeParticle(arg2, d, e, f, this.sprite, random);
            return particle;
        }

    }
}
