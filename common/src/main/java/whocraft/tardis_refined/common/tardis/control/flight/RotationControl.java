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
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        Direction dir = operator.getControlManager().getTargetLocation().rotation;
        operator.getControlManager().getTargetLocation().rotation = dir.getClockWise();
        var direction = operator.getControlManager().getTargetLocation().rotation.getSerializedName();
        PlayerUtil.sendMessage(player, Component.translatable(direction), true);
        super.onRightClick(operator, theme, controlEntity, player);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        Direction dir = operator.getControlManager().getTargetLocation().rotation;
        operator.getControlManager().getTargetLocation().rotation = dir.getCounterClockWise();

        var direction = operator.getControlManager().getTargetLocation().rotation.getSerializedName();
        PlayerUtil.sendMessage(player, Component.translatable(direction), true);
        super.onLeftClick(operator, theme, controlEntity, player);
    }
}
