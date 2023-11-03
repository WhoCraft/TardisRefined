package whocraft.tardis_refined.common.tardis.control.ship;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.network.messages.OpenMonitorMessage;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.manager.TardisExteriorManager;
import whocraft.tardis_refined.common.tardis.manager.TardisWaypointManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.registry.ItemRegistry;

public class MonitorControl extends Control {

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!player.level.isClientSide()){
            boolean isSyncingKey = false;
            if (PlayerUtil.isInMainHand(player, ItemRegistry.KEY.get())){
                KeyItem key = (KeyItem)player.getMainHandItem().getItem();
                if (key.interactMonitor(player.getMainHandItem(),player, controlEntity, player.getUsedItemHand()))
                    isSyncingKey = true;
            }
            if (!isSyncingKey)
                new OpenMonitorMessage(operator.getInteriorManager().isWaitingToGenerate(),operator.getExteriorManager().getLastKnownLocation(), operator.getControlManager().getTargetLocation()).send((ServerPlayer) player);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        TardisWaypointManager waypoints = operator.getTardisWaypointManager();
        TardisExteriorManager extManager = operator.getExteriorManager();

        if(player.getMainHandItem().getItem() instanceof MapItem mapItem){
            Integer mapID = MapItem.getMapId(player.getMainHandItem());
            MapItemSavedData mapData = MapItem.getSavedData(mapID, player.getServer().getLevel(Level.OVERWORLD));
            waypoints.addWaypoint(new TardisNavLocation(new BlockPos(mapData.x, 0, mapData.z), Direction.NORTH, Level.OVERWORLD), String.valueOf(player.getLevel().random.nextInt(Integer.MAX_VALUE)));
        }

       // waypoints.addWaypoint(extManager.getLastKnownLocation(), String.valueOf(player.level().random.nextInt(Integer.MAX_VALUE)));

        return false;
    }
}
