package whocraft.tardis_refined.compat.portals.neoforge;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;

import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class PortalsCompatForge {

    public static void init() {
        EVENT_BUS.register(new PortalsCompatForge());
    }

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent breakEvent) {
        BlockEntity blockEntity = breakEvent.getLevel().getBlockEntity(breakEvent.getPos());
        ImmersivePortals.onDoorRemoved(breakEvent.getPlayer().level(), breakEvent.getPlayer(), breakEvent.getPos(), breakEvent.getState(), blockEntity);
    }


    @SubscribeEvent
    public void onServerShutdown(ServerStoppedEvent serverStoppedEvent) {
        ImmersivePortals.tardisToPortalsMap.clear();
    }

}
