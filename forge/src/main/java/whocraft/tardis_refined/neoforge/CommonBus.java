package whocraft.tardis_refined.neoforge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.command.TardisRefinedCommand;
import whocraft.tardis_refined.common.dimension.DelayedTeleportData;
import whocraft.tardis_refined.common.hum.TardisHums;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.patterns.ShellPatterns;

@Mod.EventBusSubscriber(modid = TardisRefined.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonBus {

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        if (level instanceof ServerLevel serverLevel) {
            if (event.phase == TickEvent.Phase.END) {
                DelayedTeleportData.tick(serverLevel);
            }
        }
    }

    @SubscribeEvent
    public static void onDatapack(AddReloadListenerEvent addReloadListenerEvent) {
        addReloadListenerEvent.addListener(ConsolePatterns.getReloadListener());

        addReloadListenerEvent.addListener(TardisDesktops.getReloadListener());
        addReloadListenerEvent.addListener(ShellPatterns.getReloadListener());
        addReloadListenerEvent.addListener(TardisHums.getReloadListener());

    }

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        TardisRefinedCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (MiscHelper.shouldStopItem(event.getEntity().level(), player, event.getPos(), player.getMainHandItem())) {
                event.getLevel().destroyBlock(event.getPos(), true);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        event.setCanceled(MiscHelper.shouldCancelBreaking(event.getPlayer().level(), event.getPlayer(), event.getPos(), event.getState()));
    }

}