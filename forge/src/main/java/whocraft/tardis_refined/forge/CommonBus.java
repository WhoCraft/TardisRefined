package whocraft.tardis_refined.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.dimension.DelayedTeleportData;

@Mod.EventBusSubscriber(modid = TardisRefined.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonBus {

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        if (level instanceof ServerLevel serverLevel) {
            if (event.phase == TickEvent.Phase.END) {
                DelayedTeleportData.tick(serverLevel);
            }
        }
    }

}
