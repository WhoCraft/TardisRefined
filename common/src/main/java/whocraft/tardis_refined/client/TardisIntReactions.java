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

import java.util.Map;

public class TardisIntReactions {


    private final ResourceKey<Level> levelKey;
    public AnimationState ROTOR_ANIMATION = new AnimationState();

    public TardisIntReactions(ResourceKey<Level> resourceKey){
        this.levelKey = resourceKey;
    }

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

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean("flying", flying);
        return compoundTag;
    }

    public void deserializeNBT(CompoundTag arg) {
        flying = arg.getBoolean("flying");
    }

    public void sync(ServerLevel serverLevel) {
        TardisNetwork.NETWORK.sendToDimension(serverLevel, new SyncIntReactionsMessage(getLevelKey(), serializeNBT()));
    }

    public void update() {
        if (!flying && ROTOR_ANIMATION.isStarted()) {
            ROTOR_ANIMATION.stop();
        }

        if (flying) {
            ROTOR_ANIMATION.start(0);
        }

        System.out.println("CAR!");
    }

    protected static Map<ResourceKey<Level>, TardisIntReactions> DATA = Util.make(new Object2ObjectOpenHashMap<>(), (objectOpenHashMap) -> {
        objectOpenHashMap.defaultReturnValue(new TardisIntReactions(null));
    });


    /*Register information about a Tardis*/
    public static void add(TardisIntReactions tardisIntReactions, ResourceKey<Level> levelResourceKey) {
        DATA.put(levelResourceKey, tardisIntReactions);
    }

    /*Retrieve information about a Tardis*/
    public static TardisIntReactions getInstance(ResourceKey<Level> levelResourceKey){

        if(DATA.containsKey(levelResourceKey)){
            return DATA.get(levelResourceKey);
        }

        DATA.put(levelResourceKey, new TardisIntReactions(levelResourceKey));

        return DATA.get(levelResourceKey);
    }

    public static void clearAll() {
        DATA.clear();
    }


}
