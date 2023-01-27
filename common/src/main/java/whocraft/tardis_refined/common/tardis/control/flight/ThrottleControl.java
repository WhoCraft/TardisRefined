package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class ThrottleControl extends Control {

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (operator.getControlManager().isInFlight()) {
            if (operator.getControlManager().endFlight()) {
                var pitchedSound = theme.getSoundProfile().getThrottleDisable().getRightClick();
                if (pitchedSound == null) {return;}
                operator.getLevel().playSound(null, new BlockPos(controlEntity.position().x, controlEntity.position().y, controlEntity.position().z), pitchedSound.getSoundEvent(), SoundSource.BLOCKS, 1f, pitchedSound.getPitch());
            }
        }

        if (operator.getControlManager().beginFlight(false)) {
            var pitchedSound = theme.getSoundProfile().getThrottleEnable().getRightClick();
            if (pitchedSound == null) {return;}
            operator.getLevel().playSound(null, new BlockPos(controlEntity.position().x, controlEntity.position().y, controlEntity.position().z), pitchedSound.getSoundEvent(), SoundSource.BLOCKS, 1f, pitchedSound.getPitch());
        }
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

    }
}
