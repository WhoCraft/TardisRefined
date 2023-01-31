package whocraft.tardis_refined.fabric.compat;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;

public class PortalsCompatFabric {

    public static void init(){
        PlayerBlockBreakEvents.BEFORE.register(ImmersivePortals::onDoorRemoved);
    }

}
