package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;

public class RotationControl extends Control {

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return this.rotateDir(operator, theme, controlEntity, player, true);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return this.rotateDir(operator, theme, controlEntity, player, false);
    }

    private boolean rotateDir(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player, boolean clockwise){
        if (!operator.getLevel().isClientSide()) {
            Direction dir = operator.getControlManager().getTargetLocation().getDirection();
            operator.getControlManager().getTargetLocation().setDirection(clockwise ? dir.getClockWise() : dir.getCounterClockWise());
            var direction = operator.getControlManager().getTargetLocation().getDirection().getSerializedName();
            PlayerUtil.sendMessage(player, Component.translatable(direction), true);
            return true;
        }
        return false;
    }
}
