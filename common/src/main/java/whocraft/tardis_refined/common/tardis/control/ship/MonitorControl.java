package whocraft.tardis_refined.common.tardis.control.ship;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.network.messages.screens.OpenMonitorMessage;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.registry.ItemRegistry;

public class MonitorControl extends Control {
    public MonitorControl(ResourceLocation id) {
        super(id);
    }
    public MonitorControl(ResourceLocation id, String langId){
        super(id, langId);
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        if (!player.level().isClientSide()){
            ItemStack hand = player.getMainHandItem();

            // Temporary for testing purposes
            if (hand.is(Items.EXPERIENCE_BOTTLE)) {
                operator.getPilotingManager().setFuel(operator.getPilotingManager().getMaximumFuel());
                player.sendSystemMessage(Component.literal("Fueled up!"));
                return true;
            }

            boolean isSyncingKey = false;
            if (hand.getItem() instanceof KeyItem key){
                if (key.interactMonitor(hand,player, controlEntity, player.getUsedItemHand()))
                    isSyncingKey = true;
            }
            if (!isSyncingKey)
                new OpenMonitorMessage(operator.getInteriorManager().isWaitingToGenerate(), operator.getExteriorManager().getLastKnownLocation(), operator.getPilotingManager().getTargetLocation(), operator.getUpgradeHandler()).send((ServerPlayer) player);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {
        return false;
    }
}
