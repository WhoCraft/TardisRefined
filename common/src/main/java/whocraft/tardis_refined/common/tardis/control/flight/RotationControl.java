package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.IControl;
import whocraft.tardis_refined.common.util.PlayerUtil;

public class RotationControl implements IControl {
    @Override
    public void onRightClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        Direction dir = operator.getControlManager().getTargetLocation().rotation;
        operator.getControlManager().getTargetLocation().rotation = dir.getClockWise();
        PlayerUtil.sendMessage(player, Component.translatable(operator.getControlManager().getTargetLocation().rotation.toString()), true);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        Direction dir = operator.getControlManager().getTargetLocation().rotation;
        operator.getControlManager().getTargetLocation().rotation = dir.getCounterClockWise();
        PlayerUtil.sendMessage(player, Component.translatable(operator.getControlManager().getTargetLocation().rotation.toString()), true);
    }
}
