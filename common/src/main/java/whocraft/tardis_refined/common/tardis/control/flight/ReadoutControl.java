package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;

public class ReadoutControl extends Control {
    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        TardisNavLocation currentPosition = operator.getExteriorManager().getLastKnownLocation();
        PlayerUtil.sendMessage(player, Component.translatable("Current - X: " + currentPosition.getPosition().getX() + " Y: " + currentPosition.getPosition().getY()+ " Z: " + currentPosition.getPosition().getZ() +  "F: " + currentPosition.getDirection().getName() + " D: " + currentPosition.getDimensionKey().location().getPath()), true);


        return true;
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        TardisNavLocation targetLocation = operator.getPilotingManager().getTargetLocation();
        PlayerUtil.sendMessage(player, Component.translatable("Destination - X: " + targetLocation.getPosition().getX() + " Y: " + targetLocation.getPosition().getY()+ " Z: " + targetLocation.getPosition().getZ() +  " F: " + targetLocation.getDirection().getName() + " D: " + targetLocation.getDimensionKey().location().getPath()), true);

        return true;
    }
}
