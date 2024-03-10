package whocraft.tardis_refined.fabric.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.client.GravityOverlay;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.command.TardisRefinedCommand;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.TardisTeleportData;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerImpl;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.registry.DimensionTypes;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_WORLD_TICK;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.START_WORLD_TICK;

public class ModEvents {

    public static void addCommonEvents() {

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> !MiscHelper.shouldCancelBreaking(world, player, pos, state));

        END_WORLD_TICK.register(TardisTeleportData::tick);
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
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> TardisRefinedCommand.register(dispatcher));
    }

    public static void addClientEvents() {
        ClientTickEvents.START_CLIENT_TICK.register(TardisClientData::tickClientData);
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> GravityOverlay.renderOverlay(matrixStack.pose()));

    }


}
