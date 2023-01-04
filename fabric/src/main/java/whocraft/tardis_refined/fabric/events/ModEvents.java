package whocraft.tardis_refined.fabric.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.q_misc_util.my_util.DQuaternion;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DelayedTeleportData;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerImpl;
import whocraft.tardis_refined.common.tardis.IExteriorShell;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.registry.DimensionTypes;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_WORLD_TICK;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.START_WORLD_TICK;

public class ModEvents {

    public static void addCommonEvents() {

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> !MiscHelper.shouldCancelBreaking(world, player, pos, state));

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
            TardisClientData.getAllEntries().forEach((levelResourceKey, tardisClientData) -> tardisClientData.tickClientside());

            if (Minecraft.getInstance().level == null) {
                TardisClientData.clearAll();
            }
        });
    }

}
