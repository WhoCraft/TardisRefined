package whocraft.tardis_refined.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.ParticleGallifrey;
import whocraft.tardis_refined.client.TRParticles;
import whocraft.tardis_refined.client.renderer.blockentity.RootPlantRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.console.GlobalConsoleRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.device.ConsoleConfigurationRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.door.BulkHeadDoorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.door.GlobalDoorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.door.RootShellDoorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.life.ArsEggRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.shell.GlobalShellRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.shell.RootShellRenderer;
import whocraft.tardis_refined.client.renderer.entity.ControlEntityRenderer;
import whocraft.tardis_refined.fabric.events.ModEvents;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.EntityRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class TardisRefinedFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        establishBlockEntityRenderers();
        registerEntityRenderers();
        ModelRegistry.init();
        ModEvents.addClientEvents();
        particles();

    }

    private void particles() {
        ParticleFactoryRegistry.getInstance().register(TRParticles.GALLIFREY.get(), (ParticleGallifrey.Provider::new));
    }

    private void establishBlockEntityRenderers() {
        BlockEntityRendererRegistry.register(BlockEntityRegistry.ROOT_PLANT.get(), RootPlantRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.ROOT_SHELL.get(), RootShellRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.ROOT_SHELL_DOOR.get(), RootShellDoorRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.GLOBAL_SHELL_BLOCK.get(), GlobalShellRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.GLOBAL_DOOR_BLOCK.get(), GlobalDoorRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), GlobalConsoleRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.ARS_EGG.get(), ArsEggRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.BULK_HEAD_DOOR.get(), BulkHeadDoorRenderer::new);

        BlockEntityRendererRegistry.register(BlockEntityRegistry.CONSOLE_CONFIGURATION.get(), ConsoleConfigurationRenderer::new);

        /*Required to Render Transparency*/
        for (RegistrySupplier<Block> entry : BlockRegistry.BLOCKS.getEntries()) {
            BlockRenderLayerMap.INSTANCE.putBlock(entry.get(), RenderType.cutout());
        }

    }

    private void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityRegistry.CONTROL_ENTITY.get(), ControlEntityRenderer::new);
    }
}
