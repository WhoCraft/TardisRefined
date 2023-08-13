package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class ThrottleControl extends Control {

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return this.engageThrottle(operator, theme, controlEntity, player);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return false;
    }

    private boolean engageThrottle(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!operator.getLevel().isClientSide()){
            if (operator.getControlManager().isInFlight()) {
                if (operator.getControlManager().canEndFlight()) {
                    var pitchedSound = theme.getSoundProfile().getThrottleDisable().getRightClick();
                    if (pitchedSound != null) {
                        this.setSuccessSound(pitchedSound);
                    }
                    return operator.getControlManager().endFlight();
                }
            }

            if (operator.getControlManager().canBeginFlight()) {
                var pitchedSound = theme.getSoundProfile().getThrottleEnable().getRightClick();
                if (pitchedSound != null) {
                    this.setSuccessSound(pitchedSound);
                }
                return operator.getControlManager().beginFlight(false);
            }
            return false;
        }
        return false;
    }

}
