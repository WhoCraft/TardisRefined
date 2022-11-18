package whocraft.tardis_refined.forge;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.forge.ModelRegistryImpl;
import whocraft.tardis_refined.client.renderer.blockentity.interior.door.RootShellDoorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.shell.RootPlantRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.shell.RootShellRenderer;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

@Mod.EventBusSubscriber(modid = TardisRefined.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModBus {

    @SubscribeEvent
    public static void event(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModelRegistry.init();
        ModelRegistryImpl.register(event);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
       BlockEntityRenderers.register(BlockEntityRegistry.ROOT_PLANT.get(), RootPlantRenderer::new);
       BlockEntityRenderers.register(BlockEntityRegistry.ROOT_SHELL.get(), RootShellRenderer::new);
       BlockEntityRenderers.register(BlockEntityRegistry.ROOT_SHELL_DOOR.get(), RootShellDoorRenderer::new);
    }

}
