package whocraft.tardis_refined.common.tardis.control.ship;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.console.sound.PitchedSound;

public class ToggleDoorControl extends Control {
    public ToggleDoorControl(ResourceLocation id) {
        super(id);
    }

    public ToggleDoorControl(ResourceLocation id, String langId) {
        super(id, langId);
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!operator.getLevel().isClientSide()) {
            if (operator.getInternalDoor() != null) {
                if (operator.getExteriorManager().locked() || operator.getPilotingManager().isInFlight()) {
                    return false;
                }
                if (!operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition()).isAir()) {
                    var isDoorOpen = operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition()).getValue(GlobalDoorBlock.OPEN);
                    operator.setDoorClosed(isDoorOpen);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!operator.getLevel().isClientSide()) {
            if (operator.getInternalDoor() != null)
                operator.getInternalDoor().setLocked(!operator.getExteriorManager().locked());
            if (operator.getExteriorManager() != null)
                operator.getExteriorManager().setLocked(!operator.getExteriorManager().locked());

            operator.setDoorClosed(true);
            return true;
        }
        return false;
    }

    @Override
    public PitchedSound getSuccessSound(TardisLevelOperator operator, ConsoleTheme theme, boolean leftClick) {
        if (!operator.getLevel().isClientSide()) {
            if (!operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition()).isAir()) {
                var isDoorOpen = operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition()).getValue(GlobalDoorBlock.OPEN);
                var pitchedSound = (isDoorOpen) ? theme.getSoundProfile().getDoorClose().getRightClick() : theme.getSoundProfile().getDoorOpen().getRightClick();
                if (pitchedSound != null) {
                    return pitchedSound;
                }
            }
        }
        return super.getSuccessSound(operator, theme, leftClick);
    }

    @Override
    public PitchedSound getFailSound(TardisLevelOperator operator, ConsoleTheme theme, boolean leftClick) {
        return new PitchedSound(SoundEvents.NOTE_BLOCK_BIT.value());
    }
}
