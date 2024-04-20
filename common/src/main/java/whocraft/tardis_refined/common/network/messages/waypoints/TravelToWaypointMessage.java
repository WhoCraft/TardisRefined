package whocraft.tardis_refined.common.network.messages.waypoints;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.manager.TardisWaypointManager;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

public class TravelToWaypointMessage extends MessageC2S {

    String tardisNavName;

    public TravelToWaypointMessage(String tardisNavName) {
        this.tardisNavName = tardisNavName;
    }


    public TravelToWaypointMessage(FriendlyByteBuf buf) {
        tardisNavName = buf.readUtf();
    }


    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.SET_WAYPOINT;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(tardisNavName);
    }

    @Override
    public void handle(MessageContext context) {
        ServerPlayer player = context.getPlayer();
        ServerLevel serverLevel = player.serverLevel();

        TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
            TardisWaypointManager tardisWaypointManager = tardisLevelOperator.getTardisWaypointManager();
            TardisNavLocation waypoint = tardisWaypointManager.getWaypointByName(tardisNavName);
            TardisPilotingManager pilotManager = tardisLevelOperator.getPilotingManager();
            pilotManager.setTargetLocation(waypoint);

            serverLevel.playSound(player, BlockPos.containing(player.position()), SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.BLOCKS, 1, 1);
            PlayerUtil.sendMessage(player, Component.translatable(ModMessages.WAYPOINT_LOADED, tardisNavName), true);

        });
    }
}
