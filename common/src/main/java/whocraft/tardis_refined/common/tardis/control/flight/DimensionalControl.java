package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.Level.OVERWORLD;

public class DimensionalControl extends Control {

    private List<ServerLevel> getAllowedDimensions(MinecraftServer server) {
        var filteredDimensions = new ArrayList<ServerLevel>();
        var filteredLevels = server.getAllLevels();

        filteredLevels.forEach(x -> {
            if (DimensionUtil.isAllowedDimension(x.dimension())) {
                filteredDimensions.add(x);
            }
        });

        return filteredDimensions;
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return changeDim(operator, theme, controlEntity, player, false);
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return changeDim(operator, theme, controlEntity, player, true);
    }

    private boolean changeDim(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player, boolean forward) {
        if (!operator.getLevel().isClientSide()) {



            TardisPilotingManager pilotManager = operator.getPilotingManager();
            UpgradeHandler upgradeHandler = operator.getUpgradeHandler();

            if(!Upgrades.DIMENSION_TRAVEL.get().isUnlockedAndCanBeUsed(operator, upgradeHandler)){
                PlayerUtil.sendMessage(player, Component.translatable(ModMessages.HARDWARE_OFFLINE), true);
                pilotManager.getTargetLocation().setDimensionKey(OVERWORLD);
                return false;
            }

            var server = operator.getLevel().getServer();
            var dimensions = getAllowedDimensions(server);
            var currentIndex = dimensions.indexOf(pilotManager.getTargetLocation().getLevel());
            var nextIndex = forward ? ( (currentIndex >= dimensions.size()-1) ? 0 : currentIndex + 1) : ((currentIndex <= 0) ? dimensions.size() - 1 : currentIndex - 1);

            pilotManager.getTargetLocation().setLevel(dimensions.get(nextIndex));

            PlayerUtil.sendMessage(player, Component.translatable(ModMessages.CONTROL_DIMENSION_SELECTED, MiscHelper.getCleanDimensionName(pilotManager.getTargetLocation().getDimensionKey())), true);

            operator.getTardisFlightEventManager().calculateTravelLogic();
            return true;
        }
        return false;
    }

}