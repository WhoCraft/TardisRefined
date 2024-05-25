package whocraft.tardis_refined.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.ParticleGallifrey;
import whocraft.tardis_refined.client.TRItemColouring;
import whocraft.tardis_refined.client.TRParticles;
import whocraft.tardis_refined.client.forge.ModelRegistryImpl;
import whocraft.tardis_refined.client.renderer.blockentity.RootPlantRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.console.GlobalConsoleRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.device.ArtronPillarRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.device.AstralManipulatorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.device.ConsoleConfigurationRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.door.BulkHeadDoorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.door.GlobalDoorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.door.RootShellDoorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.life.ArsEggRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.life.EyeRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.shell.GlobalShellRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.shell.RootShellRenderer;
import whocraft.tardis_refined.client.renderer.entity.ControlEntityRenderer;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;
import whocraft.tardis_refined.registry.TREntityRegistry;
import whocraft.tardis_refined.registry.TRItemRegistry;

@Mod.EventBusSubscriber(modid = TardisRefined.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModBus {

    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item item) {
        item.register(TRItemColouring.SCREWDRIVER_COLORS, TRItemRegistry.SCREWDRIVER.get());
    }


    @SubscribeEvent
    public static void onBuildTabsContent(BuildCreativeModeTabContentsEvent event) {

        if (event.getTab() == TRItemRegistry.MAIN_TAB.get()) {
            for (RegistrySupplier<Item> item : TRItemRegistry.TAB_ITEMS.stream().toList()) {
                event.accept(item.get());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModelRegistry.init();
        ModelRegistryImpl.register(event);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ParticleEngine particleEngine = mc.particleEngine;
        particleEngine.register(TRParticles.GALLIFREY.get(), (ParticleEngine.SpriteParticleRegistration) (ParticleGallifrey.Provider::new));
        particleEngine.register(TRParticles.ARS_LEAVES.get(), (ParticleEngine.SpriteParticleRegistration) (ParticleGallifrey.ARSVinesParticle::new));
    }


    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(TRBlockEntityRegistry.ROOT_PLANT.get(), RootPlantRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.ROOT_SHELL.get(), RootShellRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.ROOT_SHELL_DOOR.get(), RootShellDoorRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.GLOBAL_SHELL_BLOCK.get(), GlobalShellRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.GLOBAL_DOOR_BLOCK.get(), GlobalDoorRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), GlobalConsoleRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.ARS_EGG.get(), ArsEggRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.THE_EYE.get(), EyeRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.BULK_HEAD_DOOR.get(), BulkHeadDoorRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.CONSOLE_CONFIGURATION.get(), ConsoleConfigurationRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.ASTRAL_MANIPULATOR.get(), AstralManipulatorRenderer::new);
        BlockEntityRenderers.register(TRBlockEntityRegistry.ARTRON_PILLAR.get(), ArtronPillarRenderer::new);

        EntityRenderers.register(TREntityRegistry.CONTROL_ENTITY.get(), ControlEntityRenderer::new);
    }


}
