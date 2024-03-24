package whocraft.tardis_refined.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.sounds.HumSoundManager;
import whocraft.tardis_refined.client.sounds.LoopingSound;
import whocraft.tardis_refined.client.sounds.QuickSimpleSound;
import whocraft.tardis_refined.common.hum.HumEntry;
import whocraft.tardis_refined.common.hum.TardisHums;
import whocraft.tardis_refined.common.network.messages.sync.SyncTardisClientDataMessage;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.DimensionTypes;

import java.util.List;
import java.util.Map;

import static whocraft.tardis_refined.common.util.TardisHelper.isInArsArea;

public class TardisClientData {


    private final ResourceKey<Level> levelKey;
    public AnimationState ROTOR_ANIMATION = new AnimationState();
    public AnimationState LANDING_ANIMATION = new AnimationState();
    public AnimationState TAKEOFF_ANIMATION = new AnimationState();

    public TardisClientData(ResourceKey<Level> resourceKey) {
        this.levelKey = resourceKey;
    }

    /**
     * @return The resource key for the level in which this Tardis instance is located.
     */
    public ResourceKey<Level> getLevelKey() {
        return levelKey;
    }

    private boolean flying = false;

    // Control specifics
    private int throttleStage = 0;
    private boolean isLanding = false;
    private boolean isHandbrakeEngaged = false;

    private boolean isTakingOff = false;
    private boolean isCrashing = false;
    private boolean isOnCooldown = false;

    //Not saved to disk, no real reason to be
    private int nextAmbientNoiseCall = 40;


    private ResourceLocation shellTheme = ShellTheme.FACTORY.getId();
    private ResourceLocation shellPattern = ShellPatterns.DEFAULT.id();

    private HumEntry humEntry = TardisHums.getDefaultHum();

    public ResourceLocation getShellTheme() {
        return shellTheme;
    }

    public HumEntry getHumEntry() {
        return humEntry;
    }

    public void setThrottleStage(int stage) {
        this.throttleStage = stage;
    }

    public int getThrottleStage() {
        return this.throttleStage;
    }

    public void setHumEntry(HumEntry humEntry) {
        this.humEntry = humEntry;
    }

    public void setShellTheme(ResourceLocation shellTheme) {
        this.shellTheme = shellTheme;
    }

    public ResourceLocation getShellPattern() {
        return shellPattern;
    }

    public void setShellPattern(ResourceLocation shellPattern) {
        this.shellPattern = shellPattern;
    }

    public int landingTime = 0, takeOffTime = 0;

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean isFlying() {
        return flying;
    }


    public void setIsLanding(boolean landing) {
        this.isLanding = landing;
    }

    public boolean isLanding() {
        return isLanding;
    }

    public void setIsTakingOff(boolean takingOff) {
        this.isTakingOff = takingOff;
    }

    public boolean isTakingOff() {
        return isTakingOff;
    }


    public void setIsCrashing(boolean isCrashing) {
        this.isCrashing = isCrashing;
    }

    public boolean isCrashing() {
        return isCrashing;
    }

    public void setIsOnCooldown(boolean isCooldown) {
        this.isOnCooldown = isCooldown;
    }

    public boolean isOnCooldown() {
        return isOnCooldown;
    }

    /**
     * Serializes the Tardis instance to a CompoundTag.
     *
     * @return A CompoundTag containing the serialized Tardis data.
     */
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();

        compoundTag.putBoolean("flying", flying);
        compoundTag.putInt(NbtConstants.THROTTLE_STAGE, throttleStage);
        compoundTag.putBoolean(NbtConstants.HANDBRAKE_ENGAGED, isHandbrakeEngaged);
        compoundTag.putBoolean("isLanding", isLanding);
        compoundTag.putBoolean("isTakingOff", isTakingOff);
        compoundTag.putBoolean("isOnCooldown", this.isOnCooldown);
        // Save shellTheme and shellPattern
        compoundTag.putString("shellTheme", shellTheme.toString());
        compoundTag.putString("shellPattern", shellPattern.toString());

        compoundTag.putString(NbtConstants.TARDIS_CURRENT_HUM, humEntry.getIdentifier().toString());

        return compoundTag;
    }

    /**
     * Deserializes the Tardis instance from a CompoundTag.
     *
     * @param compoundTag A CompoundTag containing the serialized Tardis data.
     */
    public void deserializeNBT(CompoundTag compoundTag) {
        flying = compoundTag.getBoolean("flying");
        throttleStage = compoundTag.getInt(NbtConstants.THROTTLE_STAGE);
        isHandbrakeEngaged = compoundTag.getBoolean(NbtConstants.HANDBRAKE_ENGAGED);
        isLanding = compoundTag.getBoolean("isLanding");
        isTakingOff = compoundTag.getBoolean("isTakingOff");
        isOnCooldown = compoundTag.getBoolean("isOnCooldown");

        // Load shellTheme and shellPattern
        shellTheme = new ResourceLocation(compoundTag.getString("shellTheme"));
        shellPattern = new ResourceLocation(compoundTag.getString("shellPattern"));

        setHumEntry(TardisHums.getHumById(new ResourceLocation(compoundTag.getString(NbtConstants.TARDIS_CURRENT_HUM))));
    }

    /**
     * Syncs the Tardis instance with the specified server level. This method should only be called
     * server-side, as calling it client-side may cause the game to crash.
     */
    public void sync() {
        new SyncTardisClientDataMessage(getLevelKey(), serializeNBT()).sendToAll();
    }

    public void tickClientside() {

        SoundManager soundManager = Minecraft.getInstance().getSoundManager();


        if (isTakingOff()) {
            takeOffTime++;
            landingTime = 0;
            return;
        }

        if (isLanding()) {
            landingTime++;
            takeOffTime = 0;
        }


        if (Minecraft.getInstance().player.level().dimensionTypeId() == DimensionTypes.TARDIS) {

            ClientLevel tardisLevel = Minecraft.getInstance().level;
            boolean isThisTardis = levelKey == tardisLevel.dimension();

            createWorldAmbience(Minecraft.getInstance().player);


            if (LoopingSound.ARS_HUMMING == null) {
                LoopingSound.setupSounds();
            }

            if (isInArsArea(Minecraft.getInstance().player.blockPosition())) {
                if (!soundManager.isActive(LoopingSound.ARS_HUMMING)) {
                    LoopingSound.ARS_HUMMING.setLocation(new Vec3(1037, 102, 21));
                    soundManager.play(LoopingSound.ARS_HUMMING);
                }
            }

            if (isThisTardis && isFlying()) {
                if (!soundManager.isActive(LoopingSound.FLIGHT_LOOP)) {
                    soundManager.play(LoopingSound.FLIGHT_LOOP);
                }
            }


            if (isThisTardis && humEntry != null && !humEntry.getSound().toString().equals(HumSoundManager.getCurrentRawSound().getLocation().toString()) || !soundManager.isActive(HumSoundManager.getCurrentSound())) {
                HumSoundManager.playHum(SoundEvent.createVariableRangeEvent(humEntry.getSound()));
            }

            if (isThisTardis && tardisLevel.getGameTime() % nextAmbientNoiseCall == 0) {
                nextAmbientNoiseCall = tardisLevel.random.nextInt(400, 2400);
                List<ResourceLocation> ambientSounds = humEntry.getAmbientSounds();
                if (!ambientSounds.isEmpty()) {
                    LocalPlayer player = Minecraft.getInstance().player;
                    RandomSource randomSource = tardisLevel.random;

                    ResourceLocation randomSoundLocation = ambientSounds.get(randomSource.nextInt(ambientSounds.size()));
                    SoundEvent randomSoundEvent = SoundEvent.createVariableRangeEvent(randomSoundLocation);

                    QuickSimpleSound simpleSoundInstance = new QuickSimpleSound(randomSoundEvent, SoundSource.AMBIENT);
                    simpleSoundInstance.setVolume(0.3F);

                    double randomX = player.getX() + (randomSource.nextDouble() - 0.5) * 100;
                    double randomY = player.getY() + (randomSource.nextDouble() - 0.5) * 100;
                    double randomZ = player.getZ() + (randomSource.nextDouble() - 0.5) * 100;

                    simpleSoundInstance.setLocation(new Vec3(randomX, randomY, randomZ));
                    Minecraft.getInstance().getSoundManager().play(simpleSoundInstance);
                }
            }

            if (LoopingSound.shouldMinecraftMusicStop(soundManager)) {
                Minecraft.getInstance().getMusicManager().stopPlaying();
            }


            // Responsible for screen-shake. Not sure of a better solution at this point in time.

            if (Minecraft.getInstance().player.level().dimension() == levelKey) {
                var player = Minecraft.getInstance().player;
                if (isCrashing) {
                    player.setXRot(player.getXRot() + (player.getRandom().nextFloat() - 0.5f) * 0.5f);
                    player.setYHeadRot(player.getYHeadRot() + (player.getRandom().nextFloat() - 0.5f) *  0.5f);
                } else {
                    if (isFlying()) {
                        player.setXRot(player.getXRot() + (player.getRandom().nextFloat() - 0.5f) * (throttleStage * 0.1f));
                        player.setYHeadRot(player.getYHeadRot() + (player.getRandom().nextFloat() - 0.5f) * (throttleStage * 0.1f));
                    }
                }
            }

        }

    }


    /**
     * Updates the Tardis instance. This method is called manually from the SyncIntReactionsMessage message.
     */
    public void update() {
        // Check if the Tardis is not currently flying and the rotor animation is started
        if (!flying && ROTOR_ANIMATION.isStarted()) {
            ROTOR_ANIMATION.stop();
        }
        // Check if the Tardis is flying and the rotor animation is not started
        else if (flying && !ROTOR_ANIMATION.isStarted()) {
            ROTOR_ANIMATION.start(0);
        }

        if (isLanding) {
            if (!LANDING_ANIMATION.isStarted()) {
                TAKEOFF_ANIMATION.stop();
                LANDING_ANIMATION.start(0);
            }
        } else if (LANDING_ANIMATION.isStarted()) {
            LANDING_ANIMATION.stop();
        }

        if (isTakingOff) {
            if (!TAKEOFF_ANIMATION.isStarted()) {
                LANDING_ANIMATION.stop();
                TAKEOFF_ANIMATION.start(0);
            }
        } else if (TAKEOFF_ANIMATION.isStarted()) {
            TAKEOFF_ANIMATION.stop();
        }
    }

    // A map that stores information about Tardis instances, keyed by level resource key
    protected static Map<ResourceKey<Level>, TardisClientData> DATA = Util.make(new Object2ObjectOpenHashMap<>(), (objectOpenHashMap) -> {
        // Set the default return value for the map to be a new TardisIntReactions instance with a null level resource key
        objectOpenHashMap.defaultReturnValue(new TardisClientData(null));
    });


    /**
     * Registers information about a Tardis instance.
     *
     * @param tardisClientData The TardisIntReactions instance containing information about the Tardis.
     * @param levelResourceKey The resource key of the level the Tardis is in.
     */
    public static void add(TardisClientData tardisClientData, ResourceKey<Level> levelResourceKey) {
        // Add the information about the Tardis to the map
        DATA.put(levelResourceKey, tardisClientData);
    }

    /**
     * Retrieves information about a Tardis instance.
     *
     * @param levelResourceKey The resource key of the level the Tardis is in.
     * @return The TardisIntReactions instance containing information about the Tardis.
     */
    public static TardisClientData getInstance(ResourceKey<Level> levelResourceKey) {
        // Check if the map contains information about the Tardis
        if (DATA.containsKey(levelResourceKey)) {
            // If the map contains information about the Tardis, return it
            return DATA.get(levelResourceKey);
        }

        // If the map does not contain information about the Tardis, create a new TardisIntReactions instance and add it to the map
        DATA.put(levelResourceKey, new TardisClientData(levelResourceKey));
        return DATA.get(levelResourceKey);
    }

    public static Map<ResourceKey<Level>, TardisClientData> getAllEntries() {
        return DATA;
    }

    /**
     * Clears all Tardis information from the map.
     */
    public static void clearAll() {
        // Clear the map
        DATA.clear();
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

        TardisClientData.getAllEntries().forEach((levelResourceKey, tardisClientData) -> tardisClientData.tickClientside());
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

    public Vec3 fogColor(boolean isCrashing) {
        if (isCrashing) {
            return new Vec3(1, 0, 0);
        }
        return new Vec3(0.14F, 0.15F, 0.22F);
    }

    public boolean isHandbrakeEngaged() {
        return isHandbrakeEngaged;
    }

    public void setHandbrakeEngaged(boolean handbrakeEngaged) {
        isHandbrakeEngaged = handbrakeEngaged;
    }
}
