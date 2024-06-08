package whocraft.tardis_refined.common.tardis.control.ship;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import whocraft.tardis_refined.common.blockentity.door.AbstractDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.ConfiguredSound;

public class ToggleDoorControl extends Control {
    public ToggleDoorControl(ResourceLocation id) {
        super(id);
    }
    public ToggleDoorControl(ResourceLocation id, String langId){
        super(id, langId);
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!operator.getLevel().isClientSide()){
            if (operator.getInternalDoor() != null) {
                if(operator.getExteriorManager().locked() || operator.getPilotingManager().isInFlight()) {
                    return false;
                }
                BlockEntity blockEntity = operator.getLevel().getBlockEntity(operator.getInternalDoor().getDoorPosition());
                if (blockEntity != null){
                    if (blockEntity instanceof TardisInternalDoor internalDoor){
                        var isDoorOpen = internalDoor.isOpen();
                        operator.setDoorClosed(isDoorOpen);
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!operator.getLevel().isClientSide()) {
            //Update both internal and exterior shell doors with the value from the exterior manager, which is the Tardis' current data
            if (operator.getExteriorManager() != null)
                operator.setDoorLocked(!operator.getExteriorManager().locked());
            operator.setDoorClosed(true);
            return true;
        }
        return false;
    }

    @Override
    public ConfiguredSound getSuccessSound(TardisLevelOperator operator, ConsoleTheme theme, boolean leftClick) {
        if (!operator.getLevel().isClientSide()) {
            BlockEntity blockEntity = operator.getLevel().getBlockEntity(operator.getInternalDoor().getDoorPosition());
            if (blockEntity != null){
                if (blockEntity instanceof GlobalDoorBlockEntity internalDoor){
                    var isDoorOpen = internalDoor.isOpen();
                    var pitchedSound = (isDoorOpen) ? internalDoor.pattern().soundProfile().getDoorClose() : internalDoor.pattern().soundProfile().getDoorOpen();
                    if (pitchedSound != null) {
                        return pitchedSound;
                    }
                }

            }
        }
        return super.getSuccessSound(operator, theme, leftClick);
    }

    @Override
    public ConfiguredSound getFailSound(TardisLevelOperator operator, ConsoleTheme theme, boolean leftClick) {
        return new ConfiguredSound(SoundEvents.NOTE_BLOCK_BIT.value());
    }
}
