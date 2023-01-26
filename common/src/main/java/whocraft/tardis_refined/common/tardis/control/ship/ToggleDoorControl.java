package whocraft.tardis_refined.common.tardis.control.ship;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.IControl;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class ToggleDoorControl implements IControl {

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if(operator.getExteriorManager().locked() || operator.getControlManager().isInFlight()) {
            controlEntity.level.playSound(null, operator.getInternalDoor().getDoorPosition(), SoundEvents.NOTE_BLOCK_BIT, SoundSource.BLOCKS, 100, (float)(0.1 + (controlEntity.level.getRandom().nextFloat() * 0.5)));
            return;
        }

        var isDoorOpen = operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition()).getValue(GlobalDoorBlock.OPEN);
        var pitchedSound = (isDoorOpen) ? theme.getSoundProfile().doorClose.getRightClick() : theme.getSoundProfile().doorOpen.getRightClick();
        operator.getLevel().playSound(null, new BlockPos(controlEntity.position().x, controlEntity.position().y, controlEntity.position().z), pitchedSound.getSoundEvent(), SoundSource.BLOCKS, 1f, pitchedSound.getPitch());
        operator.setDoorClosed(isDoorOpen);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        operator.getInternalDoor().setLocked(!operator.getExteriorManager().locked());
        operator.getExteriorManager().setLocked(!operator.getExteriorManager().locked());

        operator.setDoorClosed(true);
    }
}
