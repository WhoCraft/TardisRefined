package whocraft.tardis_refined.common.capability.player;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.network.messages.player.C2SExitTardisView;
import whocraft.tardis_refined.common.network.messages.player.S2CResetPostShellView;
import whocraft.tardis_refined.common.network.messages.sync.S2CSyncTardisPlayerView;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.TRTeleporter;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class TardisPlayerInfo implements TardisPilot {

    private Player player;
    private UUID viewedTardis;
    private TardisNavLocation playerPreviousPos = TardisNavLocation.ORIGIN;
    private float playerPreviousRot = 0;
    private float playerPreviousYaw = 0;
    private boolean renderVortex = false;

    public TardisPlayerInfo(Player player) {
        this.player = player;
    }

    public static Optional<TardisPlayerInfo> get(LivingEntity player){
        return whocraft.tardis_refined.common.capability.player.fabric.TardisPlayerInfoImpl.get(player);
    }

    public static void updateTardisForAllPlayers(TardisLevelOperator tardisLevelOperator, TardisNavLocation tardisNavLocation, boolean timeVortex) {
        if (Platform.getServer() == null) return;
        Platform.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
            TardisPlayerInfo.get(serverPlayer).ifPresent(tardisPlayerInfo -> {
                if (tardisPlayerInfo.isViewingTardis()) {
                    if (Objects.equals(tardisPlayerInfo.getViewedTardis().toString(), UUID.fromString(tardisLevelOperator.getLevelKey().identifier().getPath()).toString())) {
                        tardisPlayerInfo.startShellView(serverPlayer, tardisLevelOperator, tardisNavLocation, timeVortex);
                    }
                }
            });
        });
    }

    public float getPlayerPreviousRot() {
        return playerPreviousRot;
    }

    public void setPlayerPreviousRot(float playerPreviousRot) {
        this.playerPreviousRot = playerPreviousRot;
    }

    public float getPlayerPreviousYaw() {
        return playerPreviousYaw;
    }

    public void setPlayerPreviousYaw(float playerPreviousYaw) {
        this.playerPreviousYaw = playerPreviousYaw;
    }

    @Override
    public void updatePlayerAbilities(ServerPlayer player, Abilities abilities, boolean isWatcher) {

        if (isWatcher) {
            abilities.mayfly = false;
            abilities.instabuild = false;
            abilities.mayBuild = false;
            abilities.invulnerable = true;
            abilities.flying = true;
            player.setNoGravity(true);
        } else {
            player.gameMode.getGameModeForPlayer().updatePlayerAbilities(abilities);
            player.setNoGravity(false);
        }
    }

    @Override
    public void startShellView(ServerPlayer serverPlayer, TardisLevelOperator tardisLevelOperator, TardisNavLocation spectateTarget, boolean timeVortex) {

        // Set the player's viewed TARDIS UUID
        UUID uuid = UUID.fromString(tardisLevelOperator.getLevelKey().identifier().getPath());

        boolean alreadyViewing = isViewingTardis();
        if (!alreadyViewing) {
            setPlayerPreviousPos(new TardisNavLocation(serverPlayer.blockPosition(), Direction.NORTH, serverPlayer.level().dimension()));
            setPlayerPreviousRot(serverPlayer.getYHeadRot());
            setPlayerPreviousYaw(serverPlayer.getXRot());
        }

        // Landing can clear or replace the target location between the key
        // press and this request. Always initialize the viewer, using the
        // current shell location as a safe fallback instead of leaving the
        // client in a half-started (black PIP) state.
        if (spectateTarget == null) {
            spectateTarget = tardisLevelOperator.getPilotingManager().getCurrentLocation();
        }
        // Shell view is anchored at the exterior shell location, including
        // while the vortex overlay is active. Keeping the player in the
        // interior dimension gives the preview the wrong scene and backdrop.
        if (spectateTarget != null) {
            BlockPos spectatePos = spectateTarget.getPosition();
            // Third-person-front places the camera in front of the player.
            // Face the shell's exterior direction so that camera sits outside
            // the shell and looks back toward it, independent of where the
            // player was looking inside the TARDIS before opening shell view.
            float shellViewYaw = spectateTarget.getDirection().getOpposite().toYRot();
            boolean sameDimension = serverPlayer.level().dimension().equals(spectateTarget.getDimensionKey());
            boolean needsTeleport = spectateTarget.getPosition().distManhattan(serverPlayer.blockPosition()) > 3 || !sameDimension;
            if (needsTeleport) {
                // During flight this method is called every tick to move the shell-view
                // camera. A failed cross-dimension transfer must not be retried every tick:
                // that leaves the client on "Loading terrain" and repeatedly removes the
                // same player from chunk tracking. Cross dimensions only on initial entry;
                // subsequent updates are allowed only once the player is in the target level.
                if (alreadyViewing && !sameDimension) {
                    whocraft.tardis_refined.TardisRefined.LOGGER.warn(
                            "Shell-view update skipped cross-dimension retry player={} current={} target={}",
                            serverPlayer.getGameProfile().name(), serverPlayer.level().dimension().identifier(),
                            spectateTarget.getDimensionKey().identifier());
                    return;
                }
                boolean teleported;
                try {
                    teleported = TRTeleporter.simpleTeleport(serverPlayer, spectateTarget.getLevel(), spectatePos.getX() + 0.5, spectatePos.getY(), spectatePos.getZ() + 0.5, shellViewYaw, 0.0F);
                } catch (RuntimeException exception) {
                    whocraft.tardis_refined.TardisRefined.LOGGER.error(
                            "Shell-view teleport failed player={} destination={}@{}",
                            serverPlayer.getGameProfile().name(), spectateTarget.getDimensionKey().identifier(), spectatePos,
                            exception);
                    if (!alreadyViewing) setPlayerPreviousPos(TardisNavLocation.ORIGIN);
                    return;
                }
                whocraft.tardis_refined.TardisRefined.LOGGER.info(
                        "Shell-view takeoff trace: teleport player={} success={} destination={}@{} actual={}@{}",
                        serverPlayer.getGameProfile().name(), teleported,
                        spectateTarget.getDimensionKey().identifier(), spectatePos,
                        serverPlayer.level().dimension().identifier(), serverPlayer.blockPosition());
                if (!teleported || !serverPlayer.level().dimension().equals(spectateTarget.getDimensionKey())) {
                    whocraft.tardis_refined.TardisRefined.LOGGER.warn(
                            "Shell-view teleport did not reach target dimension player={} actual={} expected={}",
                            serverPlayer.getGameProfile().name(), serverPlayer.level().dimension().identifier(),
                            spectateTarget.getDimensionKey().identifier());
                    if (!alreadyViewing) setPlayerPreviousPos(TardisNavLocation.ORIGIN);
                    return;
                }
            } else {
                serverPlayer.setYRot(shellViewYaw);
                serverPlayer.setYHeadRot(shellViewYaw);
                serverPlayer.setYBodyRot(shellViewYaw);
                serverPlayer.setXRot(0.0F);
                whocraft.tardis_refined.TardisRefined.LOGGER.info(
                        "Shell-view takeoff trace: teleport skipped player={} current={}@{} target={}@{}",
                        serverPlayer.getGameProfile().name(), serverPlayer.level().dimension().identifier(), serverPlayer.blockPosition(),
                        spectateTarget.getDimensionKey().identifier(), spectatePos);
            }
        } else {
            whocraft.tardis_refined.TardisRefined.LOGGER.warn(
                    "Shell-view takeoff trace: no spectate target for player={}", serverPlayer.getGameProfile().name());
        }
        setViewedTardis(uuid);
        updatePlayerAbilities(serverPlayer, serverPlayer.getAbilities(), true);
        setRenderVortex(timeVortex);
        serverPlayer.onUpdateAbilities();
        syncToClients(null);


    }

    public TardisNavLocation getPlayerPreviousPos() {
        return playerPreviousPos;
    }

    public void setPlayerPreviousPos(TardisNavLocation playerPreviousPos) {
        this.playerPreviousPos = playerPreviousPos;
    }

    public static void onExitKeybindPressed() {
        new C2SExitTardisView().send();
    }

    @Override
    @Nullable
    public UUID getViewedTardis() {
        return viewedTardis;
    }

    @Override
    public void setViewedTardis(@Nullable UUID uuid) {
        this.viewedTardis = uuid;
    }

    @Override
    public boolean isViewingTardis() {
        return viewedTardis != null;
    }

    @Override
    public CompoundTag saveData() {
        CompoundTag tag = new CompoundTag();
        if (viewedTardis != null) {
            tag.putString("ViewedTardis", viewedTardis.toString());
        }

        CompoundTag playerPos = playerPreviousPos.serialise();
        tag.put("TardisPlayerPos", playerPos);

        tag.putBoolean("RenderVortex", renderVortex);
        tag.putFloat("PlayerPreviousRot", playerPreviousRot);
        tag.putFloat("PlayerPreviousYaw", playerPreviousYaw);

        return tag;
    }

    public boolean isRenderVortex() {
        return renderVortex;
    }

    public void setRenderVortex(boolean renderVortex) {
        this.renderVortex = renderVortex;
        syncToClients(null);
    }

    @Override
    public void loadData(CompoundTag tag) {

        playerPreviousRot = tag.getFloatOr("PlayerPreviousRot", 0.0F);
        playerPreviousYaw = tag.getFloatOr("PlayerPreviousYaw", 0.0F);

        if (tag.contains("TardisPlayerPos")) {
            playerPreviousPos = TardisNavLocation.deserialize(tag.getCompoundOrEmpty("TardisPlayerPos"));
        }

        this.viewedTardis = tag.getString("ViewedTardis").map(UUID::fromString)
                .orElseGet(() -> tag.getIntArray("ViewedTardis").map(UUIDUtil::uuidFromIntArray).orElse(null));

        renderVortex = tag.getBooleanOr("RenderVortex", false);

    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void endShellView(ServerPlayer serverPlayer) {
        if (!isViewingTardis()) return;
        TardisNavLocation returnLocation = getPlayerPreviousPos();
        BlockPos targetPosition = returnLocation.getPosition();
        net.minecraft.server.level.ServerLevel returnLevel = DimensionUtil.getLevel(returnLocation.getDimensionKey());
        if (returnLevel != null) {
            TRTeleporter.simpleTeleport(serverPlayer, returnLevel,
                    targetPosition.getX() + 0.5, targetPosition.getY(), targetPosition.getZ() + 0.5,
                    playerPreviousRot, playerPreviousYaw);
        } else {
            whocraft.tardis_refined.TardisRefined.LOGGER.warn(
                    "Shell view return level {} is unavailable for {}; ending view in current location",
                    returnLocation.getDimensionKey().identifier(), serverPlayer.getGameProfile().name());
        }
        updatePlayerAbilities(serverPlayer, serverPlayer.getAbilities(), false);
        serverPlayer.onUpdateAbilities();
        new S2CResetPostShellView().send(serverPlayer);

        setPlayerPreviousPos(TardisNavLocation.ORIGIN);
        setRenderVortex(false);
        // Clear the viewed TARDIS UUID
        setViewedTardis(null);

        syncToClients(null);

    }

    @Override
    public void syncToClients(@Nullable ServerPlayer serverPlayerEntity) {
        if (player != null && player.level().isClientSide())
            throw new IllegalStateException("Don't sync client -> server");

        CompoundTag nbt = saveData();

        S2CSyncTardisPlayerView message = new S2CSyncTardisPlayerView(this.player.getId(), nbt);
        if (serverPlayerEntity == null) {
            message.sendToAll();
        } else {
            message.send(serverPlayerEntity);
        }
    }

    @Override
    public void tick(TardisLevelOperator tardisLevelOperator, ServerPlayer serverPlayerEntity) {
        TardisPilotingManager pilotManger = tardisLevelOperator.getPilotingManager();
        if (tardisLevelOperator.getLevelKey() == getPlayerPreviousPos().getDimensionKey()) {
            boolean showVortex = pilotManger.isLanding() || pilotManger.isTakingOff() || pilotManger.isInFlight();

            TardisNavLocation movePlayerToLocation = pilotManger.getCurrentLocation();

            if (pilotManger.isInFlight()) {
                if (pilotManger.isLanding()) {
                    movePlayerToLocation = pilotManger.getTargetLocation();
                } else if (pilotManger.isTakingOff()) {
                    movePlayerToLocation = pilotManger.getCurrentLocation();
                }
            }
            updateTardisForAllPlayers(tardisLevelOperator, movePlayerToLocation, showVortex);
            setRenderVortex(showVortex);
        }
    }
}
