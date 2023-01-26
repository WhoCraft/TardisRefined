package whocraft.tardis_refined.common.tardis.control.ship;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.client.screen.MonitorScreen;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.network.messages.OpenMonitorMessage;
import whocraft.tardis_refined.common.tardis.control.IControl;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.Platform;

public class MonitorControl implements IControl {

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        new OpenMonitorMessage(operator.getInteriorManager().isWaitingToGenerate(),operator.getExteriorManager().getLastKnownLocation(), operator.getControlManager().getTargetLocation()).send((ServerPlayer) player);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

    }
}
