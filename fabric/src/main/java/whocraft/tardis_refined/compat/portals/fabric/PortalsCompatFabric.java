package whocraft.tardis_refined.compat.portals.fabric;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;

public class PortalsCompatFabric {

    public static void init() {
        PlayerBlockBreakEvents.BEFORE.register(ImmersivePortals::onDoorRemoved);
        ServerLifecycleEvents.SERVER_STOPPING.register((server) -> ImmersivePortals.tardisToPortalsMap.clear());
    }

}
