package whocraft.tardis_refined.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.network.messages.SyncIntReactionsMessage;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.Map;

public class TardisClientData {


    private final ResourceKey<Level> levelKey;
    public AnimationState ROTOR_ANIMATION = new AnimationState();
    public AnimationState LANDING_ANIMATION = new AnimationState();
    public AnimationState TAKEOFF_ANIMATION = new AnimationState();
    public TardisClientData(ResourceKey<Level> resourceKey){
        this.levelKey = resourceKey;
    }

    /**
     * @return The resource key for the level in which this Tardis instance is located.
     */
    public ResourceKey<Level> getLevelKey() {
        return levelKey;
    }

    private boolean flying = false;
    private boolean throttleDown = false;
    private boolean isLanding = false;
    private boolean isTakingOff = false;
    private ShellTheme shellTheme = ShellTheme.FACTORY;

    public int landingTime = 0, takeOffTime = 0;

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setThrottleDown(boolean throttleDown) {
        this.throttleDown = throttleDown;
    }

    public boolean isThrottleDown() {
        return throttleDown;
    }

    public void setIsLanding(boolean landing) {this.isLanding = landing;}
    public boolean isLanding() {return isLanding;}

    public void setIsTakingOff(boolean takingOff) {this.isTakingOff = takingOff;}
    public boolean isTakingOff() {return isTakingOff;}

    public void setShellTheme(ShellTheme theme) {this.shellTheme = theme;}
    public ShellTheme getShellTheme() {return shellTheme;}

    /**
     * Serializes the Tardis instance to a CompoundTag.
     *
     * @return A CompoundTag containing the serialized Tardis data.
     */
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();

        // Set the "flying" tag in the compound tag to the current flying state of the Tardis
        compoundTag.putBoolean("flying", flying);
        compoundTag.putBoolean("throttleDown", throttleDown);
        compoundTag.putBoolean("isLanding", isLanding);
        compoundTag.putBoolean("isTakingOff", isTakingOff);
        compoundTag.putString("shellTheme", String.valueOf(shellTheme));
        return compoundTag;
    }

    /**
     * Deserializes the Tardis instance from a CompoundTag.
     *
     * @param arg A CompoundTag containing the serialized Tardis data.
     */
    public void deserializeNBT(CompoundTag arg) {
        // Set the flying state of the Tardis to the value of the "flying" tag in the compound tag
        flying = arg.getBoolean("flying");
        throttleDown = arg.getBoolean("throttleDown");
        isLanding = arg.getBoolean("isLanding");
        isTakingOff = arg.getBoolean("isTakingOff");
        shellTheme = ShellTheme.findOr(arg.getString("shellTheme"), ShellTheme.FACTORY);
    }

    /**
     * Syncs the Tardis instance with the specified server level. This method should only be called
     * server-side, as calling it client-side may cause the game to crash.
     *
     * @param serverLevel The server level to sync the Tardis with.
     */
    public void sync(ServerLevel serverLevel) {
        // Send a SyncIntReactionsMessage to the specified server level, using the Tardis's current level key
        // and serialized NBT data
        TardisNetwork.NETWORK.sendToDimension(serverLevel, new SyncIntReactionsMessage(getLevelKey(), serializeNBT()));
    }

    public void tickAnimations() {
        if (isTakingOff()) {
            takeOffTime++;
            landingTime = 0;
            return;
        }

        if (isLanding()) {
            landingTime++;
            takeOffTime = 0;
        }
    }

    /**
     * Updates the Tardis instance. This method is called manually from the SyncIntReactionsMessage message.
     */
    public void update() {
        // Check if the Tardis is not currently flying and the rotor animation is started
        if (!flying && ROTOR_ANIMATION.isStarted()) {
            // If the Tardis is not flying but the rotor animation is started, stop the animation
            ROTOR_ANIMATION.stop();
        }

        // Check if the Tardis is flying and the rotor animation is not started
        if (flying && !ROTOR_ANIMATION.isStarted()) {
            // If the Tardis is flying but the rotor animation is not started, start the animation
            ROTOR_ANIMATION.start(0);
        }

        if (isLanding && !LANDING_ANIMATION.isStarted()) {
            TAKEOFF_ANIMATION.stop();
            LANDING_ANIMATION.start(0);
        }
        if (!isLanding && LANDING_ANIMATION.isStarted()) {
            LANDING_ANIMATION.stop();
        }

        if (isTakingOff && !TAKEOFF_ANIMATION.isStarted()) {
            LANDING_ANIMATION.stop();
            TAKEOFF_ANIMATION.start(0);
        }
        if (!isTakingOff && TAKEOFF_ANIMATION.isStarted()) {
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
    public static TardisClientData getInstance(ResourceKey<Level> levelResourceKey){
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

}
