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
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return changeDim(operator, theme, controlEntity, player, false);
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return changeDim(operator, theme, controlEntity, player, true);
    }

    private boolean changeDim(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player, boolean forward) {
        if (!operator.getLevel().isClientSide()) {
            var server = operator.getLevel().getServer();
            var dimensions = getAllowedDimensions(server);
            var currentIndex = dimensions.indexOf(operator.getControlManager().getTargetLocation().getLevel());
            var nextIndex = forward ? ( (currentIndex >= dimensions.size()-1) ? 0 : currentIndex + 1) : ((currentIndex <= 0) ? dimensions.size() - 1 : currentIndex - 1);

            operator.getControlManager().getTargetLocation().setLevel(dimensions.get(nextIndex));

            PlayerUtil.sendMessage(player, Component.translatable(ModMessages.CONTROL_DIMENSION_SELECTED, MiscHelper.getCleanDimensionName(operator.getControlManager().getTargetLocation().getDimensionKey())), true);

            operator.getTardisFlightEventManager().calculateTravelLogic();
            return true;
        }
        return false;
    }

}