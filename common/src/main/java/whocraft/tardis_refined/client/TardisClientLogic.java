package whocraft.tardis_refined.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.sounds.HumSoundManager;
import whocraft.tardis_refined.client.sounds.QuickSimpleSound;
import whocraft.tardis_refined.client.sounds.TRSoundInstances;
import whocraft.tardis_refined.common.GravityUtil;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.soundscape.hum.HumEntry;
import whocraft.tardis_refined.common.util.ClientHelper;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.registry.TRDimensionTypes;

import java.util.List;

import static whocraft.tardis_refined.client.TardisClientData.FOG_TICK_DELTA;
import static whocraft.tardis_refined.client.TardisClientData.MAX_FOG_TICK_DELTA;
import static whocraft.tardis_refined.client.sounds.TRSoundInstances.VORTEX_WINDS;
import static whocraft.tardis_refined.common.util.TardisHelper.isInArsArea;

public class TardisClientLogic {

    @Environment(EnvType.CLIENT)
    public static void tickClientside(TardisClientData clientData) {

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        // Start exterior-view audio as soon as the vortex view begins, including
        // during takeoff. The animation bookkeeping below returns early while
        // taking off, so this must run before those early returns.
        handleVortexSounds(clientData, player);

        if (clientData.isTakingOff() || clientData.isFlying() || clientData.isLanding()) {
            TardisPlayerInfo.get(player).ifPresent(info -> ShellPreviewDiagnostics.reportEvery(
                    "client-flight-state-" + clientData.getLevelKey().identifier(),
                    "tardis=" + clientData.getLevelKey().identifier()
                            + ", takeoff=" + clientData.isTakingOff()
                            + ", flying=" + clientData.isFlying()
                            + ", landing=" + clientData.isLanding()
                            + ", takeOffTime=" + clientData.takeOffTime
                            + ", landingTime=" + clientData.landingTime
                            + ", takeoffAnimation=" + clientData.TAKEOFF_ANIMATION.isStarted()
                            + ", rotorAnimation=" + clientData.ROTOR_ANIMATION.isStarted()
                            + ", viewedTardis=" + info.getViewedTardis()
                            + ", viewing=" + info.isViewingTardis()
                            + ", vortex=" + info.isRenderVortex()
                            + ", playerDimension=" + player.level().dimension().identifier(),
                    1000));
        }

        if (clientData.isTakingOff()) {
            clientData.takeOffTime++;
            clientData.landingTime = 0;
            return;
        }

        if (clientData.isLanding()) {
            clientData.landingTime++;
            clientData.takeOffTime = 0;
        }

        TardisPlayerInfo.get(player).ifPresent(tardisPlayerInfo -> {
            if (tardisPlayerInfo.isViewingTardis()) {
                Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_FRONT);
            }
        });

        if (player.level().dimensionTypeRegistration().is(TRDimensionTypes.TARDIS)) {
            ClientLevel tardisLevel = Minecraft.getInstance().level;
            createWorldAmbience(player);
            handleTardisLoopingSounds(clientData, player, tardisLevel);
            handleScreenShake(clientData, player);
            handleAestheticEffects(clientData, tardisLevel);
        }
    }

    private static void handleVortexSounds(TardisClientData clientData, Player player) {
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();

        TardisPlayerInfo.get(player).ifPresent(tardisPlayerInfo -> {
            // The client tick visits cached data for every TARDIS. During
            // exterior shell view, always use the TARDIS the player is
            // actually viewing instead of whichever cache entry is ticking.
            TardisClientData viewedTardis = TardisClientData.getForTardis(tardisPlayerInfo.getViewedTardis());
            boolean vortexFlight = tardisPlayerInfo.isViewingTardis()
                    && tardisPlayerInfo.isRenderVortex()
                    && viewedTardis != null
                    && (viewedTardis.isFlying() || viewedTardis.isTakingOff())
                    && !viewedTardis.isLanding();
            if (vortexFlight && !soundManager.isActive(TRSoundInstances.TARDIS_SINGLE_FLY_VORTEX)) {
                TRSoundInstances.TARDIS_SINGLE_FLY_VORTEX.restartSoundPlaying(); //Explicity tell the LoopingSound to set its volume to a non-zero value so that the SoundEngine will play it again.
                soundManager.play(TRSoundInstances.TARDIS_SINGLE_FLY_VORTEX.setPlayer(player).setLevel(Minecraft.getInstance().level));
            }
            if (vortexFlight && !soundManager.isActive(VORTEX_WINDS)) {
                TRSoundInstances.VORTEX_WINDS.restartSoundPlaying();
                soundManager.play(TRSoundInstances.VORTEX_WINDS.setPlayer(player));
            }
        });
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
            // Keep the same global-age phase used by 1.20.2. The legacy
            // AnimationState accumulated from start(0); the 26.3 state derives
            // elapsed time from this absolute start tick.
            tardisClientData.ROTOR_ANIMATION.start(0);
        }

        // Handle landing animation
        if (tardisClientData.isLanding()) {
            if (!tardisClientData.LANDING_ANIMATION.isStarted()) {
                tardisClientData.TAKEOFF_ANIMATION.stop();
                tardisClientData.LANDING_ANIMATION.start(0);
            }
        } else if (tardisClientData.LANDING_ANIMATION.isStarted()) {
            tardisClientData.LANDING_ANIMATION.stop();
        }

        // Handle takeoff animation
        if (tardisClientData.isTakingOff()) {
            if (!tardisClientData.TAKEOFF_ANIMATION.isStarted()) {
                tardisClientData.LANDING_ANIMATION.stop();
                tardisClientData.TAKEOFF_ANIMATION.start(0);
            }
        } else if (tardisClientData.TAKEOFF_ANIMATION.isStarted()) {
            tardisClientData.TAKEOFF_ANIMATION.stop();
        }

        // Handle crashing animation
        if (tardisClientData.isCrashing()) {
            if (!tardisClientData.CRASHING_ANIMATION.isStarted()) {
                tardisClientData.ROTOR_ANIMATION.stop();
                tardisClientData.LANDING_ANIMATION.stop();
                tardisClientData.TAKEOFF_ANIMATION.stop();
                tardisClientData.CRASHING_ANIMATION.start(0);
            }
        } else if (tardisClientData.CRASHING_ANIMATION.isStarted()) {
            tardisClientData.CRASHING_ANIMATION.stop();
        }
    }


    /**
     * Called by platform-specific methods
     *
     * @param client Minecraft client
     */
    public static void tickClientData(Minecraft client) {
        // Minecraft briefly clears the client level while changing dimensions.
        // Keep synchronized TARDIS state through that gap: shell previews use
        // it immediately after entering the exterior view, before the next
        // periodic server sync. Only discard it when the player has actually
        // left the world.
        if (client.isPaused()) {
            return;
        }
        if (client.level == null) {
            if (client.player == null && !TardisClientData.getAllEntries().isEmpty()) {
                TardisClientData.clearAll();
            }
            return;
        }

        handleNonTardisLoopingSounds(client.player, client.level);

        for (TardisClientData tardisClientData : TardisClientData.getAllEntries()) {
            TardisClientLogic.tickClientside(tardisClientData);
        }
    }

    private static void createWorldAmbience(Player player) {
        if (player.tickCount % 120 == 0 && !isInArsArea(player.blockPosition())) return;
        RandomSource random = player.level().getRandom();
        Level level = player.level();
        ClientLevel clientLevel = (ClientLevel) level;
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
            BlockPos position = new BlockPos((int) particleX, (int) particleY, (int) particleZ);
            if (isInArsArea(position)) {
                ClientHelper.playParticle(clientLevel, TRParticles.ARS_LEAVES.get(), position, velocityX, velocityY, velocityZ);
                ClientHelper.playParticle(clientLevel, ParticleTypes.END_ROD, position, velocityX, velocityY, velocityZ);
            }
        }
    }

    /**
     * Handle when to start playing looping sounds for anything that doesn't need to be played inside a Tardis dimension
     */
    private static void handleNonTardisLoopingSounds(Player player, Level targetLevel) {
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        if (GravityUtil.isInGravityShaft(Minecraft.getInstance().player)) {
            if (!soundManager.isActive(TRSoundInstances.GRAVITY_LOOP)) {
                soundManager.play(TRSoundInstances.GRAVITY_LOOP.setPlayer(player).setLevel(targetLevel));
            }
        }
    }

    /**
     * Handle when to trigger the looping sounds to start playing. This is not a duplicate of the logic in the LoopingSound implementation. Here we are only defining when we should start or continue playing the sound
     */
    private static void handleTardisLoopingSounds(TardisClientData clientData, Player player, Level targetLevel) {

        boolean isThisTardis = clientData.getLevelKey() == targetLevel.dimension();

        SoundManager soundManager = Minecraft.getInstance().getSoundManager();


        if (isInArsArea(player.blockPosition())) {
            if (!soundManager.isActive(TRSoundInstances.ARS_HUMMING)) {
                soundManager.play(TRSoundInstances.ARS_HUMMING.setPlayer(player).setLevel(targetLevel));
            }
        }

        TardisPlayerInfo.get(Minecraft.getInstance().player).ifPresent(tardisPlayerInfo -> {

            if (tardisPlayerInfo.isRenderVortex()) return;

            if (isThisTardis && !clientData.isTakingOff() && !clientData.isLanding() && !clientData.isCrashing() && clientData.isFlying()) {
                if (!soundManager.isActive(TRSoundInstances.TARDIS_SINGLE_FLY)) {
                    TRSoundInstances.TARDIS_SINGLE_FLY.restartSoundPlaying(); //Explicity tell the LoopingSound to set its volume to a non-zero value so that the SoundEngine will play it again.
                    soundManager.play(TRSoundInstances.TARDIS_SINGLE_FLY.setPlayer(player).setLevel(targetLevel));
                }
            }

            //Play hums, and use the dedicated HumSoundManager to stop and start sounds
            HumEntry humEntry = clientData.getHumEntry();
            if (isThisTardis && humEntry != null && !humEntry.getSoundEventId().toString().equals(HumSoundManager.getCurrentRawSound().location().toString()) || !soundManager.isActive(HumSoundManager.getCurrentHumSound())) {
                HumSoundManager.playHum(SoundEvent.createFixedRangeEvent(humEntry.getSoundEventId(), 1F), player, targetLevel);
            }

            //Hum ambient sounds
            if (isThisTardis && targetLevel.getGameTime() % clientData.nextAmbientNoiseCall == 0) {
                clientData.nextAmbientNoiseCall = targetLevel.getRandom().nextInt(400, 2400);
                List<Identifier> ambientSounds = humEntry.getAmbientSounds();
                if (ambientSounds != null && !ambientSounds.isEmpty()) {
                    RandomSource randomSource = targetLevel.getRandom();

                    Identifier randomSoundLocation = ambientSounds.get(randomSource.nextInt(ambientSounds.size()));
                    SoundEvent randomSoundEvent = SoundEvent.createVariableRangeEvent(randomSoundLocation);

                    QuickSimpleSound simpleSoundInstance = new QuickSimpleSound(randomSoundEvent, SoundSource.AMBIENT);
                    simpleSoundInstance.setVolume(0.3F);

                    ClientHelper.playAmbientSound(simpleSoundInstance, randomSource, 0.3f);

                }
            }

            //Interior Voice
            if (isThisTardis && targetLevel.getGameTime() % clientData.nextVoiceAmbientCall == 0) {
                clientData.nextVoiceAmbientCall = targetLevel.getRandom().nextInt(6000, 36000);

                RandomSource randomSource = targetLevel.getRandom();
                ClientHelper.playAmbientSound(TRSoundInstances.INTERIOR_VOICE, randomSource, 0.3f);
            }

        });
        //Play the Tardis flight loop sound when the Tardis is in flight but not taking off or landing or crashing, to minimise sound overlap

        if (TRSoundInstances.shouldMinecraftMusicStop(soundManager)) {
            Minecraft.getInstance().getMusicManager().stopPlaying();
        }
    }

    @Environment(EnvType.CLIENT)
    public static void handleClient() {
        Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
        TardisPlayerInfo.get(Minecraft.getInstance().player).ifPresent(tardisPlayerInfo -> {
            LocalPlayer player = Minecraft.getInstance().player;
            player.setXRot(tardisPlayerInfo.getPlayerPreviousYaw());
            player.setYHeadRot(tardisPlayerInfo.getPlayerPreviousRot());
        });
    }


    private static void handleScreenShake(TardisClientData clientData, Player player) {
        // Responsible for screen-shake. Not sure of a better solution at this point in time.

        if (player.level().dimension() == clientData.getLevelKey()) {
            if (clientData.isCrashing()) {
                player.setXRot(player.getXRot() + (player.getRandom().nextFloat() - 0.5f) * 0.5f);
                player.setYHeadRot(player.getYHeadRot() + (player.getRandom().nextFloat() - 0.5f) * 0.5f);
            } else {
                if (clientData.isFlying()) {
                    player.setXRot(player.getXRot() + (player.getRandom().nextFloat() - 0.5f) * (clientData.getThrottleStage() * 0.1f));
                    player.setYHeadRot(player.getYHeadRot() + (player.getRandom().nextFloat() - 0.5f) * (clientData.getThrottleStage() * 0.1f));
                }
            }
        }
    }

    /**
     * Higher means more fog, lower means less fog
     *
     * @return 0 -> 1 float based off fog tick delta
     */
    public static float getFogTickDelta(BlockPos playerPosition) {
        return TardisHelper.isInArsArea(playerPosition) ? 1f : 1f - (float) FOG_TICK_DELTA / (float) MAX_FOG_TICK_DELTA;
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

    private static void handleAestheticEffects(TardisClientData clientData, Level targetLevel) {
        boolean isThisTardis = clientData.getLevelKey() == targetLevel.dimension();

        if (isThisTardis) {
            tickFog(clientData.getTardisState() < TardisLevelOperator.STATE_EYE_OF_HARMONY || clientData.getFuel() != 0);
        }

        if (isThisTardis && clientData.getTardisState() == TardisLevelOperator.STATE_EYE_OF_HARMONY) {
            double motionX = 0;
            double motionY = 0.1 + targetLevel.getRandom().nextFloat() / 2;
            double motionZ = 0;
            Vec3 position = new Vec3((double) 1013 + 0.5 - 2 + targetLevel.getRandom().nextInt(4), 71, (double) 55 + 0.5 - 2 + targetLevel.getRandom().nextInt(4));
            ClientHelper.playParticle((ClientLevel) targetLevel, ParticleTypes.CLOUD, position, motionX, motionY, motionZ);
        }
    }

}
