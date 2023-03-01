package whocraft.tardis_refined.fabric.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DelayedTeleportData;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerImpl;
import whocraft.tardis_refined.common.network.messages.SyncConsolePatternsMessage;
import whocraft.tardis_refined.common.network.messages.SyncShellPatternsMessage;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.DimensionTypes;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_WORLD_TICK;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.START_WORLD_TICK;

public class ModEvents {

    public static void addCommonEvents() {

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> !MiscHelper.shouldCancelBreaking(world, player, pos, state));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            new SyncConsolePatternsMessage(ConsolePatterns.getPatterns()).send(handler.getPlayer());
            new SyncShellPatternsMessage(ShellPatterns.getPatterns()).send(handler.getPlayer());
        });

        END_WORLD_TICK.register(DelayedTeleportData::tick);
        START_WORLD_TICK.register(world -> {
            if (world.dimensionTypeId().location() == DimensionTypes.TARDIS.location()) {
                TardisLevelOperator.get(world).get().tick(world);
            }
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerLevel world = server.getLevel(Level.OVERWORLD);
            DimensionHandlerImpl.loadLevels(world);
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> DimensionHandlerImpl.clear());
    }

    public static void addClientEvents() {
        ClientTickEvents.START_WORLD_TICK.register(world -> {
            TardisClientData.getAllEntries().forEach((levelResourceKey, tardisClientData) -> {
             /*   if (world.dimension() != levelResourceKey) {
                    return;
                }*/
                tardisClientData.tickClientside();
            });

            if (Minecraft.getInstance().level == null) {
                TardisClientData.clearAll();
            }
        });
    }


}
