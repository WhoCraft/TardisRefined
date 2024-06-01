package whocraft.tardis_refined.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.sounds.HumSoundManager;
import whocraft.tardis_refined.client.sounds.LoopingSound;
import whocraft.tardis_refined.client.sounds.QuickSimpleSound;
import whocraft.tardis_refined.common.GravityUtil;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.hum.HumEntry;
import whocraft.tardis_refined.common.hum.TardisHums;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.registry.TRDimensionTypes;

import java.util.List;

import static whocraft.tardis_refined.client.TardisClientData.FOG_TICK_DELTA;
import static whocraft.tardis_refined.client.TardisClientData.MAX_FOG_TICK_DELTA;
import static whocraft.tardis_refined.common.util.TardisHelper.isInArsArea;
import static whocraft.tardis_refined.constants.TardisDimensionConstants.ARS_TREE_CENTER;

public class TardisClientLogic {


    /**
     * Higher means more fog, lower means less fog
     * @return 0 -> 1 float based off fog tick delta
     */
    public static float getFogTickDelta(BlockPos playerPosition) {
        return TardisHelper.isInArsArea(playerPosition) ? 1f :  1f - (float) FOG_TICK_DELTA / (float) MAX_FOG_TICK_DELTA;
    }

    public static void tickFog(boolean hasFuel) {
        if (!hasFuel && (FOG_TICK_DELTA <= MAX_FOG_TICK_DELTA) && (FOG_TICK_DELTA > 0)) {
            FOG_TICK_DELTA--; // Fading in the fog
            return;
        }

        if (hasFuel && (FOG_TICK_DELTA != MAX_FOG_TICK_DELTA)) {
            FOG_TICK_DELTA++; // Fading out the fog
            return;
        }
    }


    @Environment(EnvType.CLIENT)
    public static void tickClientside(TardisClientData clientData) {

        SoundManager soundManager = Minecraft.getInstance().getSoundManager();


        if (clientData.isTakingOff()) {
            clientData.takeOffTime++;
            clientData.landingTime = 0;
            return;
        }

        if (clientData.isLanding()) {
            clientData.landingTime++;
            clientData.takeOffTime = 0;
        }


        if (Minecraft.getInstance().player.level().dimensionTypeId() == TRDimensionTypes.TARDIS) {

            ClientLevel tardisLevel = Minecraft.getInstance().level;
            boolean isThisTardis = clientData.getLevelKey() == tardisLevel.dimension();

            createWorldAmbience(Minecraft.getInstance().player);

            if (LoopingSound.ARS_HUMMING == null) {
                LoopingSound.setupSounds();
            }

            if (isInArsArea(Minecraft.getInstance().player.blockPosition())) {
                if (!soundManager.isActive(LoopingSound.ARS_HUMMING)) {
                    LoopingSound.ARS_HUMMING.setLocation(ARS_TREE_CENTER);
                    soundManager.play(LoopingSound.ARS_HUMMING);
                }
            }

            if (isThisTardis && clientData.isFlying()) {
                if (!soundManager.isActive(LoopingSound.FLIGHT_LOOP)) {
                    soundManager.play(LoopingSound.FLIGHT_LOOP);
                }
                else {
                    soundManager.stop(LoopingSound.FLIGHT_LOOP);
                }
            }

            HumEntry humEntry = clientData.getHumEntry();
            if (isThisTardis && humEntry != null && !humEntry.getSound().toString().equals(HumSoundManager.getCurrentRawSound().getLocation().toString()) || !soundManager.isActive(HumSoundManager.getCurrentSound())) {
                HumSoundManager.playHum(SoundEvent.createVariableRangeEvent(humEntry.getSound()));
            }

            if (isThisTardis && tardisLevel.getGameTime() % clientData.nextAmbientNoiseCall == 0) {


                clientData.nextAmbientNoiseCall = tardisLevel.random.nextInt(400, 2400);
                List<ResourceLocation> ambientSounds = humEntry.getAmbientSounds();
                if (ambientSounds != null && !ambientSounds.isEmpty()) {
                    RandomSource randomSource = tardisLevel.random;

                    ResourceLocation randomSoundLocation = ambientSounds.get(randomSource.nextInt(ambientSounds.size()));
                    SoundEvent randomSoundEvent = SoundEvent.createVariableRangeEvent(randomSoundLocation);

                    QuickSimpleSound simpleSoundInstance = new QuickSimpleSound(randomSoundEvent, SoundSource.AMBIENT);
                    simpleSoundInstance.setVolume(0.3F);

                    playAmbientSound(simpleSoundInstance, randomSource, 0.3f);

                }
            }

            if (LoopingSound.shouldMinecraftMusicStop(soundManager)) {
                Minecraft.getInstance().getMusicManager().stopPlaying();
            }



            if (isThisTardis && tardisLevel.getGameTime() % clientData.nextVoiceAmbientCall == 0) {
                clientData.nextVoiceAmbientCall = tardisLevel.random.nextInt(6000, 36000);

                RandomSource randomSource = tardisLevel.random;
                playAmbientSound(QuickSimpleSound.VOICE_QUICK_SOUND, randomSource, 0.3f);
            }


            // Responsible for screen-shake. Not sure of a better solution at this point in time.

            if (Minecraft.getInstance().player.level().dimension() == clientData.getLevelKey()) {
                var player = Minecraft.getInstance().player;
                if (clientData.isCrashing()) {
                    player.setXRot(player.getXRot() + (player.getRandom().nextFloat() - 0.5f) * 0.5f);
                    player.setYHeadRot(player.getYHeadRot() + (player.getRandom().nextFloat() - 0.5f) *  0.5f);
                } else {
                    if (clientData.isFlying()) {
                        player.setXRot(player.getXRot() + (player.getRandom().nextFloat() - 0.5f) * (clientData.getThrottleStage() * 0.1f));
                        player.setYHeadRot(player.getYHeadRot() + (player.getRandom().nextFloat() - 0.5f) * (clientData.getThrottleStage() * 0.1f));
                    }
                }
            }

            if (isThisTardis) {
                tickFog(  clientData.getTardisState() < TardisLevelOperator.STATE_EYE_OF_HARMONY || clientData.getFuel() != 0);
            }

            if (isThisTardis && clientData.getTardisState() == TardisLevelOperator.STATE_EYE_OF_HARMONY) {
                tardisLevel.addParticle(ParticleTypes.CLOUD, (double)1013 + 0.5 - 2 + tardisLevel.random.nextInt(4), 71, (double)55 + 0.5- 2 + tardisLevel.random.nextInt(4),0, 0.1 + tardisLevel.random.nextFloat() / 2 ,0);
            }
        }

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

    /**
     * Updates the Tardis instance. This method is called manually from the SyncIntReactionsMessage message.
     */
    public static void update(TardisClientData tardisClientData) {
        // Check if the Tardis is not currently flying and the rotor animation is started
        if (!tardisClientData.isFlying() && tardisClientData.ROTOR_ANIMATION.isStarted()) {
            tardisClientData.ROTOR_ANIMATION.stop();
        }
        // Check if the Tardis is flying and the rotor animation is not started
        else if (tardisClientData.isFlying() && !tardisClientData.ROTOR_ANIMATION.isStarted()) {
            tardisClientData.ROTOR_ANIMATION.start(0);
        }

        if (tardisClientData.isLanding()) {
            if (!tardisClientData.LANDING_ANIMATION.isStarted()) {
                tardisClientData.TAKEOFF_ANIMATION.stop();
                tardisClientData.LANDING_ANIMATION.start(0);
            }
        } else if (tardisClientData.LANDING_ANIMATION.isStarted()) {
            tardisClientData.LANDING_ANIMATION.stop();
        }

        if (tardisClientData.isTakingOff()) {
            if (!tardisClientData.TAKEOFF_ANIMATION.isStarted()) {
                tardisClientData.LANDING_ANIMATION.stop();
                tardisClientData.TAKEOFF_ANIMATION.start(0);
            }
        } else if (tardisClientData.TAKEOFF_ANIMATION.isStarted()) {
            tardisClientData.TAKEOFF_ANIMATION.stop();
        }
    }

    /**
     * Called by platform-specific methods
     *
     * @param client Minecraft client
     */
    public static void tickClientData(Minecraft client) {
        // Inelegant solution, please revise
        if (client.level == null || client.isPaused()) {
            if (!TardisClientData.getAllEntries().isEmpty() && !client.isPaused()) {
                TardisClientData.clearAll();
            }
            return;
        }

        SoundManager soundManager = Minecraft.getInstance().getSoundManager();

        if (LoopingSound.ARS_HUMMING == null) {
            LoopingSound.setupSounds();
        }

        if (GravityUtil.isInGravityShaft(Minecraft.getInstance().player)) {
            if (!soundManager.isActive(LoopingSound.GRAVITY_LOOP)) {
                soundManager.play(LoopingSound.GRAVITY_LOOP);
            }
        }

        for (TardisClientData tardisClientData : TardisClientData.getAllEntries()) {
            TardisClientLogic.tickClientside(tardisClientData);
        }
    }

    private static void createWorldAmbience(Player player) {
        if (player.tickCount % 120 == 0 && !isInArsArea(player.blockPosition())) return;
        RandomSource random = player.level().random;
        Level level = player.level();
        double originX = player.getX();
        double originY = player.getY();
        double originZ = player.getZ();
        for (int i = 0; i < 5; i++) {
            double particleX = originX + (random.nextInt(24) - random.nextInt(24));
            double particleY = originY + (random.nextInt(24) - random.nextInt(24));
            double particleZ = originZ + (random.nextInt(24) - random.nextInt(24));
            double velocityX = (random.nextDouble() - 0.5) * 0.02;
            double velocityY = (random.nextDouble() - 0.5) * 0.02;
            double velocityZ = (random.nextDouble() - 0.5) * 0.02;
            if (isInArsArea(new BlockPos((int) particleX, (int) particleY, (int) particleZ))) {
                level.addParticle(TRParticles.ARS_LEAVES.get(), particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
                level.addParticle(ParticleTypes.END_ROD, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
            }
        }
    }



}
