package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.api.event.EventResult;
import whocraft.tardis_refined.api.event.TardisCommonEvents;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.console.sound.PitchedSound;

public abstract class Control {
    protected final ResourceLocation id;
    protected final String langId;
    private PitchedSound successSound = new PitchedSound(SoundEvents.ARROW_HIT_PLAYER);
    private PitchedSound failSound = new PitchedSound(SoundEvents.ITEM_BREAK);

    protected Control(ResourceLocation id, String langId) {
        this.id = id;
        this.langId = langId;
    }
    protected Control(ResourceLocation id) {
        this(id, "control." + id.getNamespace() + "." + id.getPath());
    }

    public abstract boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player);

    public abstract boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player);

    /**
     * The sound event to be played when the control fails to activate
     */
    public PitchedSound getFailSound(TardisLevelOperator operator, ConsoleTheme theme, boolean leftClick) {
        return this.failSound;
    }

    /**
     * Directly set the fail sound event to be used in special scenarios
     *
     * @param failSound
     */
    public void setFailSound(PitchedSound failSound) {
        this.failSound = failSound;
    }

    public PitchedSound getSuccessSound(TardisLevelOperator operator, ConsoleTheme theme, boolean leftClick) {
        var pitchedSound = (leftClick) ? theme.getSoundProfile().getGeneric().getLeftClick() : theme.getSoundProfile().getGeneric().getRightClick();
        if (pitchedSound != null) {
            this.successSound = pitchedSound;
        }
        return this.successSound;
    }

    /**
     * Directly set the success sound event to be used in special scenario
     *
     * @param successSound
     */
    public void setSuccessSound(PitchedSound successSound) {
        this.successSound = successSound;
    }

    public void playControlPitchedSound(TardisLevelOperator operator, ControlEntity controlEntity, PitchedSound pitchedSound, SoundSource source, float volume, float pitch, boolean ignorePitch) {
        controlEntity.level().playSound(null, controlEntity.blockPosition(), pitchedSound.getSoundEvent(), source, volume, ignorePitch ? pitch : pitchedSound.getPitch());
    }

    public void playControlPitchedSound(TardisLevelOperator operator, ControlEntity controlEntity, PitchedSound pitchedSound, float pitch) {
        this.playControlPitchedSound(operator, controlEntity, pitchedSound, SoundSource.BLOCKS, 1F, pitch, true);
    }

    public void playControlPitchedSound(TardisLevelOperator operator, ControlEntity controlEntity, PitchedSound pitchedSound) {
        this.playControlPitchedSound(operator, controlEntity, pitchedSound, SoundSource.BLOCKS, 1F, 1F, false);
    }

    public boolean canUseControl(TardisLevelOperator tardisLevelOperator, Control control, ControlEntity controlEntity) {
        boolean isDeskopWaiting = controlEntity.isDesktopWaitingToGenerate(tardisLevelOperator);
        return !isDeskopWaiting && TardisCommonEvents.PLAYER_CONTROL_INTERACT.invoker().canControlBeUsed(tardisLevelOperator, control, controlEntity) == EventResult.pass();
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getTranslationKey() {
        return this.langId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Control control = (Control) o;

        return getId().equals(control.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public boolean hasCustomName() {
        return false;
    }

    public Component getCustomControlName(TardisLevelOperator operator, ControlEntity entity, ControlSpecification controlSpecification) {
        return Component.translatable(controlSpecification.control().getTranslationKey());
    }

}
