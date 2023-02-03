package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;

public class IncrementControl extends Control {

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        operator.getControlManager().cycleCordIncrement(1);
        int incrm = operator.getControlManager().getCordIncrement();
        PlayerUtil.sendMessage(player, Component.translatable("x" + incrm), true);
        super.onRightClick(operator, theme, controlEntity, player);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        operator.getControlManager().cycleCordIncrement(-1);
        int incrm = operator.getControlManager().getCordIncrement();
        PlayerUtil.sendMessage(player, Component.translatable("x" + incrm), true);
        super.onLeftClick(operator, theme, controlEntity, player);
    }
}
