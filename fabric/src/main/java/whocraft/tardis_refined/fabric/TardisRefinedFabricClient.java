package whocraft.tardis_refined.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import whocraft.tardis_refined.client.renderer.blockentity.RootPlantRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.console.GlobalConsoleRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.door.GlobalDoorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.door.RootShellDoorRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.shell.GlobalShellRenderer;
import whocraft.tardis_refined.client.renderer.blockentity.shell.RootShellRenderer;
import whocraft.tardis_refined.client.renderer.entity.ControlEntityRenderer;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.EntityRegistry;

public class TardisRefinedFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        establishBlockEntityRenderers();
        registerEntityRenderers();
    }

    private void establishBlockEntityRenderers() {
        BlockEntityRendererRegistry.register(BlockEntityRegistry.ROOT_PLANT.get(), RootPlantRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.ROOT_SHELL.get(), RootShellRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.ROOT_SHELL_DOOR.get(), RootShellDoorRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.GLOBAL_SHELL_BLOCK.get(), GlobalShellRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.GLOBAL_DOOR_BLOCK.get(), GlobalDoorRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), GlobalConsoleRenderer::new);
    }

    private void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityRegistry.CONTROL_ENTITY.get(), ControlEntityRenderer::new);
    }
}
