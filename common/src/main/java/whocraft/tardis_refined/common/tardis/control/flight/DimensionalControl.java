package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.registry.DimensionTypes;

import java.util.ArrayList;
import java.util.List;

public class DimensionalControl extends Control {

    private boolean isBlockedDimension(ServerLevel level) {
        return level.dimensionTypeId().equals(DimensionTypes.TARDIS) || level.dimensionTypeId().location().getPath().contains("the_end");
    }

    private List<ServerLevel> getAllowedDimensions(MinecraftServer server) {
        var filteredDimensions = new ArrayList<ServerLevel>();
        var filteredLevels = server.getAllLevels();

        filteredLevels.forEach(x -> {
            if (!isBlockedDimension(x)) {
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
        var currentIndex = dimensions.indexOf(operator.getControlManager().getTargetLocation().level);
        var nextIndex = (currentIndex <= 0) ? dimensions.size() - 1 : currentIndex - 1;

        operator.getControlManager().getTargetLocation().level = dimensions.get(nextIndex);

        PlayerUtil.sendMessage(player,
                Component.translatable("Selected: ").append(operator.getControlManager().getTargetLocation().level.dimension().location().getPath().toUpperCase()), true);

        operator.getTardisFlightEventManager().calculateTravelLogic();

        super.onLeftClick(operator, theme, controlEntity, player);
    }

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        var server = operator.getLevel().getServer();
        if (server == null) {return;}
        var dimensions = getAllowedDimensions(server);
        var currentIndex = dimensions.indexOf(operator.getControlManager().getTargetLocation().level);
        var nextIndex = (currentIndex >= dimensions.size()-1) ? 0 : currentIndex + 1;

        operator.getControlManager().getTargetLocation().level = dimensions.get(nextIndex);

        PlayerUtil.sendMessage(player,
                Component.translatable("Selected: ").append(operator.getControlManager().getTargetLocation().level.dimension().location().getPath().toUpperCase()), true);

        operator.getTardisFlightEventManager().calculateTravelLogic();

        super.onRightClick(operator, theme, controlEntity, player);
    }
}