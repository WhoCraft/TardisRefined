package whocraft.tardis_refined.common.network.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.entity.ControlEntity;

import java.util.Set;

public class ClientPacketHandler {

    public static void handleDimSyncPacket(ResourceKey<Level> level, boolean add) {

        if (Minecraft.getInstance().player == null || Minecraft.getInstance().player.connection.levels() == null)
            return;

        Set<ResourceKey<Level>> levels = Minecraft.getInstance().player.connection.levels();
        //If this player knows about this dimension
        if (levels.contains(level)) {
            //If remove
            if (!add) {
                levels.remove(level);
            }
        }
        //If player does not know about this dim, and we're trying to add it
        else if (add) {
            levels.add(level);
        }
    }

    public static void handleControlSizePacket(int entityId, float width, float height) {
        Level level = Minecraft.getInstance().level;
        if(level != null){
            if(level.getEntity(entityId) instanceof ControlEntity control){
                control.setSizeAndUpdate(width, height);
            }
        }
    }

}
