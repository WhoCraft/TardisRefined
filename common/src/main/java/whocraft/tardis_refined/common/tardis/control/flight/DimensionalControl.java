package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.ArrayList;
import java.util.List;

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
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        var server = operator.getLevel().getServer();
        if (server == null) {return;}
        var dimensions = getAllowedDimensions(server);
        var currentIndex = dimensions.indexOf(operator.getControlManager().getTargetLocation().getLevel());
        var nextIndex = (currentIndex <= 0) ? dimensions.size() - 1 : currentIndex - 1;

        operator.getControlManager().getTargetLocation().setLevel(dimensions.get(nextIndex));

        PlayerUtil.sendMessage(player,
                Component.translatable(ModMessages.CONTROL_DIMENSION_SELECTED, MiscHelper.getCleanDimensionName(operator.getControlManager().getTargetLocation().getDimensionKey())), true);

        operator.getTardisFlightEventManager().calculateTravelLogic();

        super.onLeftClick(operator, theme, controlEntity, player);
    }

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        var server = operator.getLevel().getServer();
        if (server == null) {return;}
        var dimensions = getAllowedDimensions(server);
        var currentIndex = dimensions.indexOf(operator.getControlManager().getTargetLocation().getLevel());
        var nextIndex = (currentIndex >= dimensions.size()-1) ? 0 : currentIndex + 1;

        operator.getControlManager().getTargetLocation().setLevel(dimensions.get(nextIndex));

        PlayerUtil.sendMessage(player,
                Component.translatable(ModMessages.CONTROL_DIMENSION_SELECTED, MiscHelper.getCleanDimensionName(operator.getControlManager().getTargetLocation().getDimensionKey())), true);

        operator.getTardisFlightEventManager().calculateTravelLogic();

        super.onRightClick(operator, theme, controlEntity, player);
    }
}