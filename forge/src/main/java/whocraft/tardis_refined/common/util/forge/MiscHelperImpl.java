package whocraft.tardis_refined.common.util.forge;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkHooks;

public class MiscHelperImpl {
    public static Packet<?> spawnPacket(Entity entity) {
        return NetworkHooks.getEntitySpawningPacket(entity);
    }
}
