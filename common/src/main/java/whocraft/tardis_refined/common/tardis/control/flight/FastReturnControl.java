package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class FastReturnControl extends Control {

    @Override
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
    }

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        if (operator.getControlManager().preloadFastReturn()) {
            super.onRightClick(operator, theme, controlEntity, player);
        } else {
            controlEntity.level.playSound(null, controlEntity.blockPosition(), SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.BLOCKS, 100, (float)(0.1 + (controlEntity.level.getRandom().nextFloat() * 0.5)));
        }

    }
}
