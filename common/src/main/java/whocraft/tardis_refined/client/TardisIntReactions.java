package whocraft.tardis_refined.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;

import java.util.Map;

public class TardisIntReactions {


    public AnimationState ROTOR_ANIMATION = new AnimationState();
    private boolean flying = false;


    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean("flying", flying);
        return compoundTag;
    }

    public void deserializeNBT(CompoundTag arg) {
        flying = arg.getBoolean("flying");
    }

    public void tick(){
        if(!flying && ROTOR_ANIMATION.isStarted()){
            ROTOR_ANIMATION.stop();
        }

        if(flying){
            ROTOR_ANIMATION.start(0);
        }
    }

    public static void tickAll(){
        DATA.forEach((levelResourceKey, tardisIntReactions) -> tardisIntReactions.tick());
    }

    protected static Map<ResourceKey<Level>, TardisIntReactions> DATA = Util.make(new Object2ObjectOpenHashMap<>(), (objectOpenHashMap) -> {
        objectOpenHashMap.defaultReturnValue(new TardisIntReactions());
    });


    /*Register information about a Tardis*/
    public static void add(TardisIntReactions tardisIntReactions, ResourceKey<Level> levelResourceKey) {
        DATA.put(levelResourceKey, tardisIntReactions);
    }

    /*Retrieve information about a Tardis*/
    public static TardisIntReactions getInstance(ResourceKey<Level> levelResourceKey){
        return DATA.get(levelResourceKey);
    }

    public static void clearAll() {
        DATA.clear();
    }


}
