package whocraft.tardis_refined.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.network.messages.SyncIntReactionsMessage;
import whocraft.tardis_refined.common.util.Platform;

import java.util.Map;

public class TardisIntReactions {


    private final ResourceKey<Level> levelKey;
    public AnimationState ROTOR_ANIMATION = new AnimationState();

    public TardisIntReactions(ResourceKey<Level> resourceKey){
        this.levelKey = resourceKey;
    }

    /**
     * @return The resource key for the level in which this Tardis instance is located.
     */
    public ResourceKey<Level> getLevelKey() {
        return levelKey;
    }

    private boolean flying = false;

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean isFlying() {
        return flying;
    }

    /**
     * Serializes the Tardis instance to a CompoundTag.
     *
     * @return A CompoundTag containing the serialized Tardis data.
     */
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();

        // Set the "flying" tag in the compound tag to the current flying state of the Tardis
        compoundTag.putBoolean("flying", flying);
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
    }

    /**
     * Syncs the Tardis instance with the specified server level. This method should only be called
     * server-side, as calling it client-side may cause the game to crash.
     *
     * @param serverLevel The server level to sync the Tardis with.
     */
    public void sync(ServerLevel serverLevel) {
        // Check if the current code is running on the server-side
        if (Platform.isClient()) {
            // If the code is running on the client-side, log a warning message and return
            TardisRefined.LOGGER.warn("sync() was called on the client-side. This may cause the game to crash.");
            return;
        }

        // Send a SyncIntReactionsMessage to the specified server level, using the Tardis's current level key
        // and serialized NBT data
        TardisNetwork.NETWORK.sendToDimension(serverLevel, new SyncIntReactionsMessage(getLevelKey(), serializeNBT()));
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
    }

    // A map that stores information about Tardis instances, keyed by level resource key
    protected static Map<ResourceKey<Level>, TardisIntReactions> DATA = Util.make(new Object2ObjectOpenHashMap<>(), (objectOpenHashMap) -> {
        // Set the default return value for the map to be a new TardisIntReactions instance with a null level resource key
        objectOpenHashMap.defaultReturnValue(new TardisIntReactions(null));
    });


    /**
     * Registers information about a Tardis instance.
     *
     * @param tardisIntReactions The TardisIntReactions instance containing information about the Tardis.
     * @param levelResourceKey The resource key of the level the Tardis is in.
     */
    public static void add(TardisIntReactions tardisIntReactions, ResourceKey<Level> levelResourceKey) {
        // Add the information about the Tardis to the map
        DATA.put(levelResourceKey, tardisIntReactions);
    }

    /**
     * Retrieves information about a Tardis instance.
     *
     * @param levelResourceKey The resource key of the level the Tardis is in.
     * @return The TardisIntReactions instance containing information about the Tardis.
     */
    public static TardisIntReactions getInstance(ResourceKey<Level> levelResourceKey){
        // Check if the map contains information about the Tardis
        if (DATA.containsKey(levelResourceKey)) {
            // If the map contains information about the Tardis, return it
            return DATA.get(levelResourceKey);
        }

        // If the map does not contain information about the Tardis, create a new TardisIntReactions instance and add it to the map
        DATA.put(levelResourceKey, new TardisIntReactions(levelResourceKey));
        return DATA.get(levelResourceKey);
    }

    /**
     * Clears all Tardis information from the map.
     */
    public static void clearAll() {
        // Clear the map
        DATA.clear();
    }

}
