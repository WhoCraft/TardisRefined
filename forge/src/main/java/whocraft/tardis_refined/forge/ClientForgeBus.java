package whocraft.tardis_refined.forge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;

@Mod.EventBusSubscriber(modid = TardisRefined.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeBus {

    @SubscribeEvent
    public static void tickTARDIS(TickEvent.ClientTickEvent event) {
        if(event.phase != TickEvent.Phase.START) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        // Inelegant solution, please revise
        if (client.level == null || client.isPaused()) {
            if(!TardisClientData.getAllEntries().isEmpty() && !client.isPaused()) {
                TardisClientData.clearAll();
            }
            return;
        }

        TardisClientData.getAllEntries().forEach((levelResourceKey, tardisClientData) -> tardisClientData.tickClientside());
    }
}
