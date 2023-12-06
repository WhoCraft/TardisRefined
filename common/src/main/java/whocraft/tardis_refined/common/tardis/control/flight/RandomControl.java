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

public class RandomControl extends Control {

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!operator.getLevel().isClientSide()){
            TardisPilotingManager pilotManager = operator.getPilotingManager();

            int increment = pilotManager.getCordIncrement();
            BlockPos currentExLoc = operator.getExteriorManager().getLastKnownLocation().getPosition();
            pilotManager.getTargetLocation().setPosition(
                    new BlockPos((currentExLoc.getX() - (increment / 2)) +  operator.getLevel().random.nextInt(increment * 2),
                            150,
                            (currentExLoc.getZ() - (increment / 2)) +  operator.getLevel().random.nextInt(increment * 2)
                    )
            );
            PlayerUtil.sendMessage(player, Component.translatable(pilotManager.getTargetLocation().getPosition().toShortString()), true);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return false;
    }
}
