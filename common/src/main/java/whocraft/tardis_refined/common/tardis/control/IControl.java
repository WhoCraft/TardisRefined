package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public interface IControl {

    void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player);
    void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player);

    default void playGenericClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, boolean left, boolean ignorePitch) {
        var pitchedSound = (left) ? theme.getSoundProfile().generic.getLeftClick(): theme.getSoundProfile().generic.getRightClick();
        operator.getLevel().playSound(null, new BlockPos(controlEntity.position().x, controlEntity.position().y, controlEntity.position().z), pitchedSound.getSoundEvent(), SoundSource.BLOCKS, 1f, (ignorePitch) ? 1f : pitchedSound.getPitch());
    }
}
