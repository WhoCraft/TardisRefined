package whocraft.tardis_refined.fabric.events;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientBlockEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.client.*;
import whocraft.tardis_refined.client.overlays.ExteriorViewOverlay;
import whocraft.tardis_refined.client.overlays.GravityOverlay;
import whocraft.tardis_refined.client.overlays.VortexOverlay;
import whocraft.tardis_refined.client.renderer.vortex.RenderTargetHelper;
import whocraft.tardis_refined.command.TardisRefinedCommand;
import whocraft.tardis_refined.common.blockentity.life.ZeitonGlassBlockEntity;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.dimension.TardisTeleportData;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerImpl;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.registry.TRDimensionTypes;
import whocraft.tardis_refined.registry.TRItemRegistry;
import whocraft.tardis_refined.registry.TRPointOfInterestTypes;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.START_WORLD_TICK;

public class ModEvents {

    public static void addCommonEvents() {

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> !MiscHelper.shouldCancelBreaking(world, player, pos, state));

        START_WORLD_TICK.register(world -> {
            if (TRDimensionTypes.isTARDISDimension(world)) {
                TardisLevelOperator.get(world).get().tick(world);
            }
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {

            // Load Levels
            ServerLevel world = server.getLevel(Level.OVERWORLD);
            DimensionHandler.loadLevels(world);

            // We call this here to make sure blocks are registered
            TRPointOfInterestTypes.registerBlockStates();

        });

        // Force End a Vortex Session
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.getPlayer();
            TardisPlayerInfo.get(player).ifPresent(tardisPlayerInfo -> {
                tardisPlayerInfo.endShellView(player);
            });
        });


        ServerTickEvents.END_SERVER_TICK.register(server -> TardisTeleportData.tick());

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            DimensionHandlerImpl.clear();

            if (ModCompatChecker.immersivePortals()) {
                ImmersivePortals.onServerStopping(server);
            }
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> TardisRefinedCommand.register(dispatcher, registryAccess));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer serverPlayer = handler.getPlayer();
            TardisHelper.handlePlayerJoinWorldEvents(serverPlayer);
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (newPlayer != null) {
                TardisHelper.handlePlayerJoinWorldEvents(newPlayer);
            }
        });

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            if (player != null) {
                TardisHelper.handlePlayerJoinWorldEvents(player);
            }
        });
    }

    public static void addClientEvents() {
        ClientTickEvents.START_CLIENT_TICK.register(TardisClientLogic::tickClientData);
        ColorProviderRegistry.ITEM.register(TRItemColouring.SCREWDRIVER_COLORS, TRItemRegistry.SCREWDRIVER.get());
        ColorProviderRegistry.ITEM.register(TRItemColouring.SAMPLE_COLORS, TRItemRegistry.TEST_TUBE.get());

        ClientBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, world) -> {
            if (blockEntity instanceof ZeitonGlassBlockEntity zeitonGlassBlockEntity) {
                ZeitonGlassTracker.onLoad(zeitonGlassBlockEntity);
            }
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (world.isClientSide) return InteractionResult.PASS;
            AtomicBoolean stopBreak = new AtomicBoolean(false);
            TardisPlayerInfo.get(player).ifPresent(tardisPlayerInfo -> stopBreak.set(tardisPlayerInfo.isViewingTardis()));
            return stopBreak.get() ? InteractionResult.FAIL : InteractionResult.PASS;
        });

        CoreShaderRegistrationCallback.EVENT.register(context -> {
            context.register(RegistryHelper.makeKey("glow_shader"), DefaultVertexFormat.NEW_ENTITY, shaderInstance -> TRShaders.GLOW_SHADER = shaderInstance);
            context.register(RegistryHelper.makeKey("nivis"), DefaultVertexFormat.NEW_ENTITY, shaderInstance -> TRShaders.SNOW_SHADER = shaderInstance);
        });

        WorldRenderEvents.LAST.register(context -> {
            Camera camera = context.camera();
            PoseStack matrices = context.matrixStack();
            ClientLevel world = Minecraft.getInstance().level;
            if (world == null) return;
            TardisClientData tardisClientData = TardisClientData.getInstance(world.dimension());
            matrices.pushPose();
            RenderTargetHelper.renderZeitonGlass(camera, ModelRegistry.getZeitonGlassModel(), matrices, context.consumers(), LightTexture.FULL_BLOCK, tardisClientData, true);
            matrices.popPose();
        });


        Supplier<GuiGraphics> guiGraphics = () -> new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> VortexOverlay.renderOverlay(guiGraphics.get()));
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> ExteriorViewOverlay.renderOverlay(guiGraphics.get()));
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> GravityOverlay.renderOverlay(guiGraphics.get()));
    }


}
