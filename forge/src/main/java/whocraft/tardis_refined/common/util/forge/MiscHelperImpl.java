package whocraft.tardis_refined.common.util.forge;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;

public class MiscHelperImpl {
    public static Packet<ClientGamePacketListener> spawnPacket(Entity entity) {
        return new ClientboundAddEntityPacket(entity);
    }
}
