package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRSoundRegistry;

public class TargetLockControl extends Control {

    public TargetLockControl(ResourceLocation id) {
        super(id, true);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return onRightClick(operator, theme, controlEntity, player);
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        operator.getPilotingManager().setTargetLocked(!operator.getPilotingManager().isTargetLocked());
        player.displayClientMessage(Component.translatable(operator.getPilotingManager().isTargetLocked() ? ModMessages.TARGET_LOCKED : ModMessages.TARGET_UNLOCKED), true);
        player.level().playSound(null, controlEntity.blockPosition(), TRSoundRegistry.DESTINATION_DING.get(), SoundSource.BLOCKS, 1f, !operator.getPilotingManager().isTargetLocked() ? 1.95f : 1.8f);

        return true;
    }


}
