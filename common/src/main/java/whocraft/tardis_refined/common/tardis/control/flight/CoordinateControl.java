package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.IControl;
import whocraft.tardis_refined.registry.SoundRegistry;

public class CoordinateControl implements IControl {

    CoordinateButton button;

    public CoordinateControl(CoordinateButton button) {
        this.button = button;
    }

    @Override
    public void onRightClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {

        int increment = operator.getControlManager().getCordIncrement();

        switch (button){
            case X -> operator.getControlManager().offsetTargetPositionX(increment);
            case Y -> {
                int potentialY = operator.getControlManager().getTargetLocation().position.getY() + increment;
                if (potentialY < operator.getControlManager().getTargetLocation().level.getMaxBuildHeight()) {
                    operator.getControlManager().offsetTargetPositionY(increment);
                }else {
                    operator.getLevel().playSound(null, controlEntity.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1, 1);
                }
            }
            case Z -> operator.getControlManager().offsetTargetPositionZ(increment);
        }

        player.displayClientMessage(Component.translatable(operator.getControlManager().getTargetLocation().position.toShortString()), true);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        int increment = operator.getControlManager().getCordIncrement();
        switch (button){
            case X -> operator.getControlManager().offsetTargetPositionX(-increment);
            case Y -> {
                int potentialY = operator.getControlManager().getTargetLocation().position.getY() - increment;
                if (potentialY > operator.getControlManager().getTargetLocation().level.getMinBuildHeight()) {
                    operator.getControlManager().offsetTargetPositionY(-increment);
                }else {
                    operator.getLevel().playSound(null, controlEntity.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1, 1);
                }
            }
            case Z -> operator.getControlManager().offsetTargetPositionZ(-increment);
        }

        player.displayClientMessage(Component.translatable(operator.getControlManager().getTargetLocation().position.toShortString()), true);
    }
}

