package whocraft.tardis_refined.common.network.messages.player;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.TardisRefined;

public class C2SBeginShellView extends MessageC2S {

    public C2SBeginShellView() {
    }

    public C2SBeginShellView(FriendlyByteBuf buf) {
    }


    @Override
    public @NotNull MessageType getType() {
        return TardisNetwork.START_VORTEX_SESSION;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(MessageContext context) {
        ServerPlayer player = context.getPlayer();
        Level level = context.getPlayer().level();
        if (level instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
                // The shell scene is rendered from client data rather than the
                // exterior block entity. Refresh the selected appearance at
                // view start so a cached default can never draw the factory
                // shell while waiting for the periodic TARDIS sync.
                var clientData = tardisLevelOperator.tardisClientData();
                clientData.setShellTheme(tardisLevelOperator.getExteriorShellTheme());
                clientData.setShellPattern(tardisLevelOperator.getExteriorShellPattern().id());

                var piloting = tardisLevelOperator.getPilotingManager();
                // While parked, spectate the actual exterior location. The
                // destination can be stale (or in an unloaded dimension) from
                // a previous trip, which leaves the outside view in darkness.
                // During flight keep the established takeoff/arrival targets.
                var spectateTarget = piloting.isTakingOff() || !piloting.isInFlight()
                        ? piloting.getCurrentLocation()
                        : piloting.getTargetLocation();
                boolean vortexView = piloting.isInFlight() || piloting.isTakingOff() || piloting.isLanding();
                TardisRefined.LOGGER.info(
                        "Shell-view takeoff trace: begin player={} sourceDimension={} inFlight={} takingOff={} landing={} vortexView={} target={}",
                        player.getGameProfile().name(), player.level().dimension().identifier(),
                        piloting.isInFlight(), piloting.isTakingOff(), piloting.isLanding(), vortexView,
                        spectateTarget == null ? "null" : spectateTarget.getDimensionKey().identifier()
                                + "@" + spectateTarget.getPosition());
                TardisPlayerInfo.get(context.getPlayer()).ifPresent(tardisInfo -> {
                    tardisInfo.startShellView(player, tardisLevelOperator, spectateTarget,
                            vortexView);
                    // Send appearance after the dimension transition begins;
                    // a prior sync can be dropped while the client has no level.
                    clientData.sync();
                });
            });
        }
    }
}
