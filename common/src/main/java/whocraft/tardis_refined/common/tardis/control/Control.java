package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.console.sound.PitchedSound;

public abstract class Control {

    private PitchedSound successSound = new PitchedSound(SoundEvents.ARROW_HIT_PLAYER);
    private PitchedSound failSound = new PitchedSound(SoundEvents.ITEM_BREAK);

    public abstract boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player);

    public abstract boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player);

    /** The sound event to be played when the control fails to activate*/
    public PitchedSound getFailSound(TardisLevelOperator operator, ConsoleTheme theme, boolean leftClick){
        return this.failSound;
    }

    /**
     * Directly set the fail sound event to be used in special scenarios
     * @param failSound
     */
    public void setFailSound(PitchedSound failSound){
        this.failSound = failSound;
    }

    public PitchedSound getSuccessSound(TardisLevelOperator operator, ConsoleTheme theme, boolean leftClick){
        var pitchedSound = (leftClick) ? theme.getSoundProfile().getGeneric().getLeftClick(): theme.getSoundProfile().getGeneric().getRightClick();
        if (pitchedSound != null){
            this.successSound = pitchedSound;
        }
        return this.successSound;
    }

    /**Directly set the success sound event to be used in special scenario
     *
     * @param successSound
     */
    public void setSuccessSound(PitchedSound successSound){
        this.successSound = successSound;
    }

    public void playControlPitchedSound(TardisLevelOperator operator, ControlEntity controlEntity, PitchedSound pitchedSound, SoundSource source, float volume, float pitch, boolean ignorePitch){
        controlEntity.level().playSound(null, controlEntity.blockPosition(), pitchedSound.getSoundEvent(), source, volume, ignorePitch ? pitch : pitchedSound.getPitch());
    }

    public void playControlPitchedSound(TardisLevelOperator operator, ControlEntity controlEntity, PitchedSound pitchedSound, float pitch){
        this.playControlPitchedSound(operator, controlEntity, pitchedSound, SoundSource.BLOCKS, 1F, pitch, true);
    }

    public void playControlPitchedSound(TardisLevelOperator operator, ControlEntity controlEntity, PitchedSound pitchedSound){
        this.playControlPitchedSound(operator, controlEntity, pitchedSound, SoundSource.BLOCKS, 1F, 1F, false);
    }

}
