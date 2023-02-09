package whocraft.tardis_refined.common.tardis.control.ship;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.network.messages.OpenMonitorMessage;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class MonitorControl extends Control {

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        new OpenMonitorMessage(operator.getInteriorManager().isWaitingToGenerate(),operator.getExteriorManager().getLastKnownLocation(), operator.getControlManager().getTargetLocation()).send((ServerPlayer) player);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

    }
}
