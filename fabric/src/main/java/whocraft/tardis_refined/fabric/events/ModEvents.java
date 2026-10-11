package whocraft.tardis_refined.fabric.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityLevelChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.client.TardisClientLogic;
import whocraft.tardis_refined.client.overlays.ExteriorViewOverlay;
import whocraft.tardis_refined.client.overlays.GravityOverlay;
import whocraft.tardis_refined.client.overlays.VortexOverlay;
import whocraft.tardis_refined.command.TardisRefinedCommand;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.dimension.TardisTeleportData;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerImpl;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals26.ImmersivePortals;
import whocraft.tardis_refined.registry.TRDimensionTypes;
import whocraft.tardis_refined.registry.TRPointOfInterestTypes;

import java.util.concurrent.atomic.AtomicBoolean;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.START_LEVEL_TICK;

public class ModEvents {

    public static void addCommonEvents() {

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> !MiscHelper.shouldCancelBreaking(world, player, pos, state));

        START_LEVEL_TICK.register(world -> {
            if (world.dimensionTypeRegistration().is(TRDimensionTypes.TARDIS)) {
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

        ServerEntityLevelChangeEvents.AFTER_PLAYER_CHANGE_LEVEL.register((player, origin, destination) -> {
            if (player != null) {
                TardisHelper.handlePlayerJoinWorldEvents(player);
            }
        });
    }

    public static void addClientEvents() {
        ClientTickEvents.START_CLIENT_TICK.register(TardisClientLogic::tickClientData);

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (world.isClientSide()) return InteractionResult.PASS;
            AtomicBoolean stopBreak = new AtomicBoolean(false);
            TardisPlayerInfo.get(player).ifPresent(tardisPlayerInfo -> stopBreak.set(tardisPlayerInfo.isViewingTardis()));
            return stopBreak.get() ? InteractionResult.FAIL : InteractionResult.PASS;
        });

        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("tardis_refined", "vortex"), (graphics, delta) -> VortexOverlay.renderOverlay(graphics));
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("tardis_refined", "exterior_view"), (graphics, delta) -> ExteriorViewOverlay.renderOverlay(graphics));
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("tardis_refined", "gravity"), (graphics, delta) -> GravityOverlay.renderOverlay(graphics));
    }


}
