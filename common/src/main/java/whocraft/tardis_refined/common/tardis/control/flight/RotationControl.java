package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.IControl;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;

public class RotationControl implements IControl {

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        Direction dir = operator.getControlManager().getTargetLocation().rotation;
        operator.getControlManager().getTargetLocation().rotation = dir.getClockWise();
        var direction = operator.getControlManager().getTargetLocation().rotation.toString();
        var caps = direction.substring(0, 1).toUpperCase() + direction.substring(1);
        PlayerUtil.sendMessage(player, Component.translatable(caps), true);
        playGenericClick(operator, theme, controlEntity, false, true);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        Direction dir = operator.getControlManager().getTargetLocation().rotation;
        operator.getControlManager().getTargetLocation().rotation = dir.getCounterClockWise();
        var direction = operator.getControlManager().getTargetLocation().rotation.toString();
        var caps = direction.substring(0, 1).toUpperCase() + direction.substring(1);
        PlayerUtil.sendMessage(player, Component.translatable(caps), true);
        PlayerUtil.sendMessage(player, Component.translatable(operator.getControlManager().getTargetLocation().rotation.toString()), true);
        playGenericClick(operator, theme, controlEntity, true, true);
    }
}
