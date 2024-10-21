package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;

public class RandomControl extends Control {
    public RandomControl(ResourceLocation id) {
        super(id);
    }

    public RandomControl(ResourceLocation id, String langId) {
        super(id, langId);
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!operator.getLevel().isClientSide()) {
            TardisPilotingManager pilotManager = operator.getPilotingManager();

            int increment = pilotManager.getCordIncrement();
            BlockPos currentExLoc = pilotManager.getCurrentLocation().getPosition();
            if (ModCompatChecker.valkyrienSkies()) {
                currentExLoc = VSHelper.toWorldPosition(pilotManager.getCurrentLocation().getLevel(), currentExLoc);
            }
            pilotManager.getTargetLocation().setPosition(
                    new BlockPos((currentExLoc.getX() - (increment / 2)) + operator.getLevel().random.nextInt(increment * 2),
                            150,
                            (currentExLoc.getZ() - (increment / 2)) + operator.getLevel().random.nextInt(increment * 2)
                    )
            );

            if (pilotManager.isInFlight()) {
                pilotManager.recalculateFlightDistance();
            }

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
