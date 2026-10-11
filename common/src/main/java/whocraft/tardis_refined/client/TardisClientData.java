package whocraft.tardis_refined.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.VortexRegistry;
import whocraft.tardis_refined.common.soundscape.hum.HumEntry;
import whocraft.tardis_refined.common.soundscape.hum.TardisHums;
import whocraft.tardis_refined.common.network.messages.sync.S2CSyncTardisClientData;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TardisClientData {
    private static final List<TardisClientData> DATA = new ArrayList<>();
    public static int FOG_TICK_DELTA = 0; // This is for the fading in and out of the fog.
    static int MAX_FOG_TICK_DELTA = 2 * 20; // This is for adjusting how fast the fog will fade in and out.
    private final ResourceKey<Level> levelKey;
    public AnimationState ROTOR_ANIMATION = new AnimationState();
    public AnimationState CRASHING_ANIMATION = new AnimationState();
    public AnimationState LANDING_ANIMATION = new AnimationState();
    public AnimationState TAKEOFF_ANIMATION = new AnimationState();
    public int landingTime = 0, takeOffTime = 0;
    //Not saved to disk, no real reason to be
    int nextAmbientNoiseCall = 40;
    // Independent of the hums logic
    int nextVoiceAmbientCall = 12000;
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
    private int recoveryTicks = 0;
    private float journeyProgress = 0;
    private Identifier shellTheme = ShellTheme.HALF_BAKED.getId();
    private Identifier vortex = VortexRegistry.FLOW.getId();
    private Identifier shellPattern = ShellPatterns.DEFAULT.id();
    private Identifier consoleTheme;
    private Identifier consolePattern;
    private HumEntry humEntry = TardisHums.getDefaultHum();
    public TardisClientData(ResourceKey<Level> resourceKey) {
        this.levelKey = resourceKey;
    }

    public static void add(TardisClientData tardisClientData) {
        DATA.add(tardisClientData);
    }

    public Identifier getVortex() {
        return vortex;
    }

    public void setVortex(Identifier vortex) {
        this.vortex = vortex;
    }

    /**
     * Retrieves information about a Tardis instance.
     *
     * @param levelResourceKey The resource key of the level the Tardis is in.
     * @return The TardisIntReactions instance containing information about the Tardis.
     */
    public static TardisClientData getInstance(ResourceKey<Level> levelResourceKey) {
        for (TardisClientData data : DATA) {
            if (data.getLevelKey().equals(levelResourceKey)) {
                return data;
            }
        }
        TardisClientData newData = new TardisClientData(levelResourceKey);
        DATA.add(newData);
        return newData;
    }

    /** Finds client state for the specific TARDIS being viewed from its UUID. */
    public static TardisClientData getForTardis(UUID tardisId) {
        if (tardisId == null) return null;
        for (TardisClientData data : DATA) {
            if (data.levelKey.identifier().getPath().equals(tardisId.toString())) {
                return data;
            }
        }
        return null;
    }

    public static List<TardisClientData> getAllEntries() {
        return new ArrayList<>(DATA);
    }

    public static void clearAll() {
        DATA.clear();
    }

    /**
     * @return The resource key for the level in which this Tardis instance is located.
     */
    public ResourceKey<Level> getLevelKey() {
        return levelKey;
    }

    public float getJourneyProgress() {
        return journeyProgress;
    }

    public void setJourneyProgress(float journeyProgress) {
        this.journeyProgress = journeyProgress;
    }

    public Identifier getShellTheme() {
        return shellTheme;
    }

    public void setShellTheme(Identifier shellTheme) {
        this.shellTheme = shellTheme;
    }

    public Identifier getConsoleTheme() {
        return consoleTheme;
    }

    public void setConsoleTheme(Identifier consoleTheme) {
        this.consoleTheme = consoleTheme;
    }

    public Identifier getConsolePattern() {
        return consolePattern;
    }

    public void setConsolePattern(Identifier consolePattern) {
        this.consolePattern = consolePattern;
    }

    public HumEntry getHumEntry() {
        return humEntry;
    }

    public void setHumEntry(HumEntry humEntry) {
        this.humEntry = humEntry;
    }

    public int getThrottleStage() {
        return this.throttleStage;
    }

    public void setThrottleStage(int stage) {
        this.throttleStage = stage;
    }

    public Identifier getShellPattern() {
        return shellPattern;
    }

    public void setShellPattern(Identifier shellPattern) {
        this.shellPattern = shellPattern;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
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

    public boolean isInRecovery() {
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
        compoundTag.putInt("recoveryTicks", recoveryTicks);
        compoundTag.putFloat("journeyProgress", journeyProgress);
        compoundTag.putBoolean(NbtConstants.HANDBRAKE_ENGAGED, isHandbrakeEngaged);
        compoundTag.putBoolean("isLanding", isLanding);
        compoundTag.putBoolean("isTakingOff", isTakingOff);
        compoundTag.putBoolean("isInDangerZone", this.isInDangerZone);
        compoundTag.putFloat("flightShakeScale", this.flightShakeScale);
        compoundTag.putBoolean("isOnCooldown", this.isOnCooldown);
        // Save shellTheme and shellPattern
        compoundTag.putString("shellTheme", shellTheme.toString());
        compoundTag.putString("vortex", vortex.toString());
        compoundTag.putString("shellPattern", shellPattern.toString());
        if (consoleTheme != null) compoundTag.putString("consoleTheme", consoleTheme.toString());
        if (consolePattern != null) compoundTag.putString("consolePattern", consolePattern.toString());

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
        flying = compoundTag.getBooleanOr("flying", false);
        throttleStage = compoundTag.getIntOr(NbtConstants.THROTTLE_STAGE, 0);
        isHandbrakeEngaged = compoundTag.getBooleanOr(NbtConstants.HANDBRAKE_ENGAGED, false);
        isLanding = compoundTag.getBooleanOr("isLanding", false);
        isTakingOff = compoundTag.getBooleanOr("isTakingOff", false);
        isInDangerZone = compoundTag.getBooleanOr("isInDangerZone", false);
        flightShakeScale = compoundTag.getFloatOr("flightShakeScale", 0.0F);
        isOnCooldown = compoundTag.getBooleanOr("isOnCooldown", false);
        recoveryTicks = compoundTag.getIntOr("recoveryTicks", 0);
        journeyProgress = compoundTag.getFloatOr("journeyProgress", 0.0F);
        // Load shellTheme and shellPattern
        shellTheme = Identifier.parse(compoundTag.getStringOr("shellTheme", ""));
        shellPattern = Identifier.parse(compoundTag.getStringOr("shellPattern", ""));
        consoleTheme = compoundTag.getString("consoleTheme").map(Identifier::parse).orElse(null);
        consolePattern = compoundTag.getString("consolePattern").map(Identifier::parse).orElse(null);
        vortex = Identifier.parse(compoundTag.getStringOr("vortex", ""));

        setHumEntry(TardisHums.getHumById(Identifier.parse(compoundTag.getStringOr(NbtConstants.TARDIS_CURRENT_HUM, ""))));

        fuel = compoundTag.getDoubleOr(NbtConstants.FUEL, 0.0D);
        maximumFuel = compoundTag.getDoubleOr(NbtConstants.MAXIMUM_FUEL, 0.0D);
    }

    /**
     * Syncs the Tardis instance with the specified server level. This method should only be called
     * server-side, as calling it client-side may cause the game to crash.
     */
    public void sync() {
        new S2CSyncTardisClientData(getLevelKey(), serializeNBT()).sendToAll();
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

    public void setRecoveryProgress(int crashRecoveryTicks) {
        this.recoveryTicks = crashRecoveryTicks;
    }

    public int getRecoveryTicks() {
        return recoveryTicks;
    }
}
