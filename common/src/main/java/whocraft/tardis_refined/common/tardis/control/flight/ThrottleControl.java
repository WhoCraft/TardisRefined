package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class ThrottleControl extends Control {

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        // We're going to need to change how the starting flight logic works, but we'll get there.

        // Find console
        GlobalConsoleBlockEntity console = controlEntity.getConsoleBlockEntity();
        if (console != null) {
            operator.getFlightDanceManager().startFlightDance(console);
            return true;
        }



        return false;
        //return this.engageThrottle(operator, theme, controlEntity, player);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return false;
    }

    private boolean engageThrottle(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!operator.getLevel().isClientSide()){
            TardisPilotingManager pilotManager = operator.getPilotingManager();
            if (pilotManager.isInFlight()) {
                if (pilotManager.canEndFlight()) {
                    var pitchedSound = theme.getSoundProfile().getThrottleDisable().getRightClick();
                    if (pitchedSound != null) {
                        this.setSuccessSound(pitchedSound);
                    }
                    return pilotManager.endFlight();
                }
            }

            if (pilotManager.canBeginFlight()) {
                var pitchedSound = theme.getSoundProfile().getThrottleEnable().getRightClick();
                if (pitchedSound != null) {
                    this.setSuccessSound(pitchedSound);
                }
                return pilotManager.beginFlight(false);
            }
            return false;
        }
        return false;
    }

}
