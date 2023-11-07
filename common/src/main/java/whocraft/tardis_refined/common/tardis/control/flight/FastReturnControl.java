package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.console.sound.PitchedSound;

public class FastReturnControl extends Control {

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return this.onRightClick(operator, theme, controlEntity, player);
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!operator.getLevel().isClientSide()){
            TardisPilotingManager pilotManager = operator.getPilotingManager();
            if (pilotManager.preloadFastReturn()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    @Override
    public PitchedSound getFailSound(TardisLevelOperator operator, ConsoleTheme theme, boolean leftClick) {
        return new PitchedSound(SoundEvents.NOTE_BLOCK_BIT, 1F);
    }
}
