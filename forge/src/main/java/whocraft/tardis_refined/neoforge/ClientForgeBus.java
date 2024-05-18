package whocraft.tardis_refined.neoforge;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.event.TickEvent;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.GravityOverlay;
import whocraft.tardis_refined.client.TardisClientLogic;

@Mod.EventBusSubscriber(modid = TardisRefined.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeBus {

    @SubscribeEvent
    public static void tickTARDIS(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        TardisClientLogic.tickClientData(Minecraft.getInstance());
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post guiOverlayEvent) {
        GravityOverlay.renderOverlay(guiOverlayEvent.getGuiGraphics().pose());
    }
}
