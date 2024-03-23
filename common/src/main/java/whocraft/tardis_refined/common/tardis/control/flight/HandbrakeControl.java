package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;

public class HandbrakeControl extends Control {
    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        if (operator.getPilotingManager().isInFlight()) {
            operator.getPilotingManager().setHandbrakeOn(true);
            System.out.println("Handbrake: " + operator.getPilotingManager().isHandbrakeOn() );
            PlayerUtil.sendMessage(player, Component.translatable("Handbrake: ").append(operator.getPilotingManager().isHandbrakeOn() ? "Engaged" : "Disengaged"), true);
            return true;
        }

        return false;
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        var pitchedSound = theme.getSoundProfile().getThrottleEnable().getRightClick();
        this.setSuccessSound(pitchedSound);

        if (operator.getPilotingManager().isInFlight()) {
            PlayerUtil.sendMessage(player, Component.translatable("Ship is in flight. Left click the handbrake to engage."), true); // MAKE THIS IS A TRANSLATION
            return false;
        } else {
            operator.getPilotingManager().setHandbrakeOn(!operator.getPilotingManager().isHandbrakeOn());
            System.out.println("Handbrake: " + operator.getPilotingManager().isHandbrakeOn() );
            PlayerUtil.sendMessage(player, Component.translatable("Handbrake: ").append(operator.getPilotingManager().isHandbrakeOn() ? "Engaged" : "Disengaged"), true); // MAKE THIS IS A TRANSLATION
            return true;
        }




    }
}
