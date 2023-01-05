package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.IControl;

public class ThrottleControl implements IControl {


    @Override
    public void onRightClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        if (operator.getControlManager().isInFlight()) {
            operator.getControlManager().endFlight();
        } else {
            operator.getControlManager().beginFlight(true);
        }
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {

    }
}
