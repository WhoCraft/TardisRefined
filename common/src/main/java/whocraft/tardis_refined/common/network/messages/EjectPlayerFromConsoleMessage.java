package whocraft.tardis_refined.common.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRDimensionTypes;

public class EjectPlayerFromConsoleMessage extends MessageC2S {

    public EjectPlayerFromConsoleMessage() {

    }

    public EjectPlayerFromConsoleMessage(FriendlyByteBuf buffer) {

    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.EJECT_PLAYER;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(MessageContext context) {

        Level playerLevel = context.getPlayer().level();
        ServerPlayer player = context.getPlayer();

        if (playerLevel instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(operator -> {

                if (!operator.getPilotingManager().isInFlight()) {
                    operator.forceEjectPlayer(player);
                } else {
                    PlayerUtil.sendMessage(player, Component.translatable(ModMessages.UI_EJECT_CANNOT_IN_FLIGHT), true);
                    player.playSound(SoundEvents.ITEM_BREAK);
                }


            });
        }


    }
}
