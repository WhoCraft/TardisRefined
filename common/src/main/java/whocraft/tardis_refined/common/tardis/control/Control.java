package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public abstract class Control {

    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        playGenericClick(operator,theme,controlEntity, true, true);
    }

    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        playGenericClick(operator,theme,controlEntity, false, true);
    }

    public void playGenericClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, boolean left, boolean ignorePitch) {
        var pitchedSound = (left) ? theme.getSoundProfile().getGeneric().getLeftClick(): theme.getSoundProfile().getGeneric().getRightClick();
        if (pitchedSound == null) {return;}
        operator.getLevel().playSound(null, controlEntity.blockPosition(), pitchedSound.getSoundEvent(), SoundSource.BLOCKS, 1f, (ignorePitch) ? 1f : pitchedSound.getPitch());
    }
}
