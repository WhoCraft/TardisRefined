package whocraft.tardis_refined.common.util;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.TRParticles;
import whocraft.tardis_refined.client.sounds.QuickSimpleSound;

public class ClientHelper {

    public static void playParticle(ClientLevel level, ParticleOptions type, Vec3 pos, double motionX, double motionY, double motionZ) {
        level.addParticle(type, pos.x(), pos.y(), pos.z(), motionX, motionY, motionZ);
    }

    public static void playParticle(ClientLevel level, ParticleOptions type, Vec3i pos, double motionX, double motionY, double motionZ) {
        playParticle(level, type, new Vec3(pos.getX(), pos.getY(), pos.getZ()), motionX, motionY, motionZ);
    }

    public static void playParticle(ClientLevel level, ParticleOptions type, BlockPos pos, double motionX, double motionY, double motionZ) {
        playParticle(level, type, new Vec3(pos.getX(), pos.getY(), pos.getZ()), motionX, motionY, motionZ);
    }

    public static void playAmbientSound(QuickSimpleSound sound, RandomSource randomSource, float volume) {
        sound.setVolume(volume);
        LocalPlayer player = Minecraft.getInstance().player;
        double randomX = player.getX() + (randomSource.nextDouble() - 0.5) * 100;
        double randomY = player.getY() + (randomSource.nextDouble() - 0.5) * 100;
        double randomZ = player.getZ() + (randomSource.nextDouble() - 0.5) * 100;
        sound.setLocation(new Vec3(randomX, randomY, randomZ));
        Minecraft.getInstance().getSoundManager().play(sound);
    }

}
