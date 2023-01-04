package whocraft.tardis_refined.common.tardis.control.ship;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.IControl;

public class ToggleDoorControl implements IControl {
    @Override
    public void onRightClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        if(operator.getExteriorManager().locked()) {
            controlEntity.level.playSound(null, operator.getInternalDoor().getDoorPosition(), SoundEvents.ANVIL_PLACE, SoundSource.AMBIENT, 1f, 1f);
            return;
        }
        operator.setDoorClosed(operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition()).getValue(GlobalDoorBlock.OPEN));
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        operator.getInternalDoor().setLocked(!operator.getExteriorManager().locked());
        operator.getExteriorManager().setLocked(!operator.getExteriorManager().locked());
        operator.setDoorClosed(true);
    }
}
