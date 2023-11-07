package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;


public class CoordinateControl extends Control {

    private CoordinateButton button;

    public CoordinateControl(CoordinateButton button) {
        this.button = button;
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return this.changeCoord(operator, theme, controlEntity, player, true);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return this.changeCoord(operator, theme, controlEntity, player, false);
    }

    private boolean changeCoord(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player, boolean addValue){
        if (!operator.getLevel().isClientSide()){
            TardisPilotingManager pilotManager = operator.getPilotingManager();

            int increment = pilotManager.getCordIncrement();
            int incrementAmount = addValue ? increment : -increment;
            BlockPos potentialPos = pilotManager.getTargetLocation().getPosition();

            switch (button){
                case X -> potentialPos = potentialPos.offset(incrementAmount, 0, 0);
                case Y -> potentialPos = potentialPos.offset(0, incrementAmount, 0);
                case Z -> potentialPos = potentialPos.offset(0, 0, incrementAmount);
            }

            if (pilotManager.getTargetLocation().getLevel().isInWorldBounds(potentialPos)){ //Use vanilla check which accounts for both world height and horizontal bounds

                pilotManager.setTargetPosition(potentialPos); //Only update target position if it is within both vertical and horizontal bounds.

                if (pilotManager.isInFlight()) {
                    operator.getTardisFlightEventManager().calculateTravelLogic();
                }

                PlayerUtil.sendMessage(player, Component.translatable(pilotManager.getTargetLocation().getPosition().toShortString()), true);
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
}

