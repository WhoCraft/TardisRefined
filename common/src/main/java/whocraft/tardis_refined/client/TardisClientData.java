package whocraft.tardis_refined.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.sounds.HumSoundManager;
import whocraft.tardis_refined.client.sounds.LoopingSound;
import whocraft.tardis_refined.client.sounds.QuickSimpleSound;
import whocraft.tardis_refined.common.GravityUtil;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.hum.HumEntry;
import whocraft.tardis_refined.common.hum.TardisHums;
import whocraft.tardis_refined.common.network.messages.sync.SyncTardisClientDataMessage;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.TRDimensionTypes;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.List;
import java.util.Map;

import static whocraft.tardis_refined.common.util.TardisHelper.isInArsArea;
import static whocraft.tardis_refined.constants.TardisDimensionConstants.ARS_TREE_CENTER;

public class TardisClientData {
    public static int FOG_TICK_DELTA = 0; // This is for the fading in and out of the fog.
    static int MAX_FOG_TICK_DELTA = 2 * 20; // This is for adjusting how fast the fog will fade in and out.

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
    private boolean isInDangerZone = false;
    private boolean isCrashing = false;
    private boolean isOnCooldown = false;
    private float flightShakeScale = 0;
    private double fuel = 0;
    private double maximumFuel = 0;

    private int tardisState = 0;


    //Not saved to disk, no real reason to be
    int nextAmbientNoiseCall = 40;

    // Independent of the hums logic
    int nextVoiceAmbientCall = 12000;

    private ResourceLocation shellTheme = ShellTheme.HALF_BAKED.getId();
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

    public double getFuel() {
        return fuel;
    }
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
    public double getMaximumFuel() {
        return maximumFuel;
    }
    public void setMaximumFuel(double fuel) {
        this.maximumFuel = fuel;
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
        compoundTag.putBoolean("isInDangerZone", this.isInDangerZone);
        compoundTag.putFloat("flightShakeScale", this.flightShakeScale);
        compoundTag.putBoolean("isOnCooldown", this.isOnCooldown);
        // Save shellTheme and shellPattern
        compoundTag.putString("shellTheme", shellTheme.toString());
        compoundTag.putString("shellPattern", shellPattern.toString());

        compoundTag.putString(NbtConstants.TARDIS_CURRENT_HUM, humEntry.getIdentifier().toString());

        compoundTag.putDouble(NbtConstants.FUEL, fuel);
        compoundTag.putDouble(NbtConstants.MAXIMUM_FUEL, maximumFuel);

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
        isInDangerZone = compoundTag.getBoolean("isInDangerZone");
        flightShakeScale = compoundTag.getFloat("flightShakeScale");
        isOnCooldown = compoundTag.getBoolean("isOnCooldown");

        // Load shellTheme and shellPattern
        shellTheme = new ResourceLocation(compoundTag.getString("shellTheme"));
        shellPattern = new ResourceLocation(compoundTag.getString("shellPattern"));

        setHumEntry(TardisHums.getHumById(new ResourceLocation(compoundTag.getString(NbtConstants.TARDIS_CURRENT_HUM))));

        fuel = compoundTag.getDouble(NbtConstants.FUEL);
        maximumFuel = compoundTag.getDouble(NbtConstants.MAXIMUM_FUEL);
    }

    /**
     * Syncs the Tardis instance with the specified server level. This method should only be called
     * server-side, as calling it client-side may cause the game to crash.
     */
    public void sync() {
        new SyncTardisClientDataMessage(getLevelKey(), serializeNBT()).sendToAll();
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

    public int getTardisState() {
        return tardisState;
    }

    public void setTardisState(int tardisState) {
        this.tardisState = tardisState;
    }
}
