package whocraft.tardis_refined.common.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

public class C2SEjectPlayer extends MessageC2S {

    public C2SEjectPlayer() {

    }

    public C2SEjectPlayer(FriendlyByteBuf buffer) {

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
        TardisRefined.LOGGER.info("Emergency eject requested by {} in {}", player.getGameProfile().name(), player.level().dimension().identifier());

        if (playerLevel instanceof ServerLevel serverLevel) {
            var operatorOptional = TardisLevelOperator.get(serverLevel);
            if (operatorOptional.isEmpty()) {
                TardisRefined.LOGGER.warn("Emergency eject unavailable for {}: no TARDIS operator in {}",
                        player.getGameProfile().name(), serverLevel.dimension().identifier());
                return;
            }
            operatorOptional.ifPresent(operator -> {

                if (!operator.getPilotingManager().isInFlight()) {
                    boolean ejected = operator.forceEjectPlayer(player);
                    TardisRefined.LOGGER.info("Emergency eject {} for {}", ejected ? "succeeded" : "failed", player.getGameProfile().name());
                    if (!ejected) {
                        PlayerUtil.sendMessage(player, Component.literal("Unable to eject: the TARDIS has no safe exit location."), true);
                    }
                } else {
                    PlayerUtil.sendMessage(player, Component.translatable(ModMessages.UI_EJECT_CANNOT_IN_FLIGHT), true);
                    player.playSound(SoundEvents.ITEM_BREAK.value());
                }


            });
        }


    }
}
