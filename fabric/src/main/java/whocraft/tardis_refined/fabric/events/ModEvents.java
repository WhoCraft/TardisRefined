package whocraft.tardis_refined.fabric.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.ControlGroupCheckers;
import whocraft.tardis_refined.client.GravityOverlay;
import whocraft.tardis_refined.client.TRItemColouring;
import whocraft.tardis_refined.client.TardisClientLogic;
import whocraft.tardis_refined.command.TardisRefinedCommand;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.dimension.TardisTeleportData;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerImpl;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.registry.TRDimensionTypes;
import whocraft.tardis_refined.registry.TRItemRegistry;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.START_WORLD_TICK;

public class ModEvents {

    public static void addCommonEvents() {

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> !MiscHelper.shouldCancelBreaking(world, player, pos, state));

        START_WORLD_TICK.register(world -> {
            if (world.dimensionTypeId().location() == TRDimensionTypes.TARDIS.location()) {
                TardisLevelOperator.get(world).get().tick(world);
            }
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {

            // Load Levels
            ServerLevel world = server.getLevel(Level.OVERWORLD);
            DimensionHandler.loadLevels(world);

        });

        ServerTickEvents.START_SERVER_TICK.register(ControlGroupCheckers::tickServer);

        ServerTickEvents.END_SERVER_TICK.register(server -> TardisTeleportData.tick());

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            DimensionHandlerImpl.clear();

            if(ModCompatChecker.immersivePortals()){
                ImmersivePortals.onServerStopping(server);
            }
        });


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> TardisRefinedCommand.register(dispatcher));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer serverPlayer = handler.getPlayer();
            TardisHelper.handlePlayerJoinWorldEvents(serverPlayer);
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (newPlayer != null){
                TardisHelper.handlePlayerJoinWorldEvents(newPlayer);
            }
        });

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            if (player != null){
                TardisHelper.handlePlayerJoinWorldEvents(player);
            }
        });
    }

    public static void addClientEvents() {
        ClientTickEvents.START_CLIENT_TICK.register(TardisClientLogic::tickClientData);
        ColorProviderRegistry.ITEM.register(TRItemColouring.SCREWDRIVER_COLORS, TRItemRegistry.SCREWDRIVER.get());
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> GravityOverlay.renderOverlay(matrixStack.pose()));
    }


}
