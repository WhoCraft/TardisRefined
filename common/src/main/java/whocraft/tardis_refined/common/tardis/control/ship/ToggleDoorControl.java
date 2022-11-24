package whocraft.tardis_refined.common.tardis.control.ship;

import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.control.IControl;

public class ToggleDoorControl implements IControl {
    @Override
    public void onRightClick(TardisLevelOperator operator) {
        operator.setDoorClosed(operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition()).getValue(GlobalDoorBlock.OPEN));
    }

    @Override
    public void onLeftClick() {

    }
}
