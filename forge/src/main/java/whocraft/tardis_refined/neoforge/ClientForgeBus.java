package whocraft.tardis_refined.neoforge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
