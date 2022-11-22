package whocraft.tardis_refined.common.tardis.control.flight;

import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.control.IControl;

public class ThrottleControl implements IControl {
    @Override
    public void onRightClick(TardisLevelOperator operator) {
        System.out.println("RIGHT CLICK, WE HAVE A RIGHT CLICK!!!!!");
        operator.setDoorClosed(!operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition()).getValue(GlobalDoorBlock.OPEN));
    }

    @Override
    public void onLeftClick() {

    }
}
