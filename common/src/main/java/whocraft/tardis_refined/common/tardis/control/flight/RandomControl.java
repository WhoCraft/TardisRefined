package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.IControl;

public class RandomControl implements IControl {
    @Override
    public void onRightClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        int increment = operator.getControlManager().getCordIncrement();
        BlockPos currentExLoc = operator.getExteriorManager().getLastKnownLocation().position;
        operator.getControlManager().getTargetLocation().position =
                new BlockPos((currentExLoc.getX() - (increment / 2)) +  operator.getLevel().random.nextInt(increment * 2),
                        150,
                        (currentExLoc.getZ() - (increment / 2)) +  operator.getLevel().random.nextInt(increment * 2)
                        );

        player.displayClientMessage(Component.translatable(operator.getControlManager().getTargetLocation().position.toShortString()), true);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {

    }
}
