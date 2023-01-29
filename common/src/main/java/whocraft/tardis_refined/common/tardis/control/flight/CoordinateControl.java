package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;


public class CoordinateControl extends Control {

    CoordinateButton button;

    public CoordinateControl(CoordinateButton button) {
        this.button = button;
    }

    @Override
    public void onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

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

        if (operator.getControlManager().isInFlight()) {
            operator.getTardisFlightEventManager().calculateTravelLogic();
        }

        PlayerUtil.sendMessage(player, Component.translatable(operator.getControlManager().getTargetLocation().position.toShortString()), true);
        super.onRightClick(operator, theme, controlEntity, player);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
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

        if (operator.getControlManager().isInFlight()) {
            operator.getTardisFlightEventManager().calculateTravelLogic();
        }

        PlayerUtil.sendMessage(player, Component.translatable(operator.getControlManager().getTargetLocation().position.toShortString()), true);
        super.onLeftClick(operator, theme, controlEntity, player);
    }
}

