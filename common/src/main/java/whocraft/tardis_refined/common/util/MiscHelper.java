package whocraft.tardis_refined.common.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.TardisRefined;

public class MiscHelper {

    @ExpectPlatform
    public static Packet<?> spawnPacket(Entity entity) {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }

}
