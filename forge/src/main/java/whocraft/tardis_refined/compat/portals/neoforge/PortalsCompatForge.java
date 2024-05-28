package whocraft.tardis_refined.compat.portals.neoforge;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class PortalsCompatForge {

    public static void init() {
        EVENT_BUS.register(new PortalsCompatForge());
    }

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent breakEvent) {
        BlockEntity blockEntity = breakEvent.getLevel().getExistingBlockEntity(breakEvent.getPos());
        ImmersivePortals.onDoorRemoved(breakEvent.getPlayer().m_20193_(), breakEvent.getPlayer(), breakEvent.getPos(), breakEvent.getState(), blockEntity);
    }


    @SubscribeEvent
    public void onServerShutdown(ServerStoppedEvent serverStoppedEvent) {
        ImmersivePortals.clearPortalCache();
    }

}
