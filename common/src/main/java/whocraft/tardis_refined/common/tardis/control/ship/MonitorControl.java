package whocraft.tardis_refined.common.tardis.control.ship;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.network.messages.screens.OpenMonitorMessage;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;
import whocraft.tardis_refined.constants.ModMessages;

public class MonitorControl extends Control {
    public MonitorControl(ResourceLocation id) {
        super(id, true);
    }
    public MonitorControl(ResourceLocation id, String langId){
        super(id, langId, true);
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!player.level().isClientSide()){

            if (operator.getTardisState() != TardisLevelOperator.STATE_EYE_OF_HARMONY || operator.getPilotingManager().isOutOfFuel()) {
                PlayerUtil.sendMessage(player, ModMessages.HARDWARE_OFFLINE, true);
                return false;
            }

            ItemStack hand = player.getMainHandItem();

            boolean isSyncingKey = false;
            if (hand.getItem() instanceof KeyItem key){
                if (key.interactMonitor(hand,player, controlEntity, player.getUsedItemHand()))
                    isSyncingKey = true;
            }
            if (!isSyncingKey) {
                TardisNavLocation currentLocation = operator.getPilotingManager().getCurrentLocation();
                if (ModCompatChecker.valkyrienSkies()) {
                    currentLocation = VSHelper.toWorldLocation(currentLocation);
                }

                new OpenMonitorMessage(
                    operator.getInteriorManager().isWaitingToGenerate(),
                    currentLocation,
                    operator.getPilotingManager().getTargetLocation(),
                    operator.getUpgradeHandler(),
                    operator.getLevelKey()
                ).send((ServerPlayer) player);
                operator.updatingMonitors.add((ServerPlayer) player);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return false;
    }


    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public Component getCustomControlName(TardisLevelOperator operator, ControlEntity entity, ControlSpecification controlSpecification) {
        if (operator.getPilotingManager().isInFlight() && !operator.getPilotingManager().isLanding()) {
            float percentageCompleted = (operator.getPilotingManager().getFlightPercentageCovered() * 100f);
            if (percentageCompleted > 100) {
                percentageCompleted = 100;
            }
            return Component.translatable(controlSpecification.control().getTranslationKey()).append(" (" + (int) percentageCompleted + "%)");
        }

        return super.getCustomControlName(operator, entity, controlSpecification);
    }
}
