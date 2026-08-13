package whocraft.tardis_refined.common.capability.player;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.network.messages.player.C2SExitTardisView;
import whocraft.tardis_refined.common.network.messages.player.S2CResetPostShellView;
import whocraft.tardis_refined.common.network.messages.sync.S2CSyncTardisPlayerView;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.TRTeleporter;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class TardisPlayerInfo implements TardisPilot {

    private Player player;
    private UUID viewedTardis;
    private ResourceKey<Level> playerPreviousDim = Level.OVERWORLD;
    private Vec3 playerPreviousPos = Vec3.ZERO;
    private float playerPreviousRot = 0;
    private float playerPreviousYaw = 0;
    private boolean renderVortex = false;

    public TardisPlayerInfo(Player player) {
        this.player = player;
    }

    @ExpectPlatform
    public static Optional<TardisPlayerInfo> get(LivingEntity player) {
        throw new AssertionError();
    }

    public static void updateTardisForAllPlayers(TardisLevelOperator tardisLevelOperator, TardisNavLocation tardisNavLocation, boolean timeVortex) {
        if (Platform.getServer() == null) return;
        Platform.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
            TardisPlayerInfo.get(serverPlayer).ifPresent(tardisPlayerInfo -> {
                if (tardisPlayerInfo.isViewingTardis()) {
                    if (Objects.equals(tardisPlayerInfo.getViewedTardis().toString(), UUID.fromString(tardisLevelOperator.getLevelKey().location().getPath()).toString())) {
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
        UUID uuid = UUID.fromString(tardisLevelOperator.getLevelKey().location().getPath());

        if (!isViewingTardis()) {
            setPlayerPreviousPos(player.position());
            setPlayerPreviousDim(player.level().dimension());
            setPlayerPreviousRot(player.getXRot());
            setPlayerPreviousYaw(player.getYRot());
        }

        setViewedTardis(uuid);

        if (spectateTarget != null) {

            BlockPos spectatePos = spectateTarget.getPosition();
            Vec3 accurateSpectatePos = Vec3.atBottomCenterOf(spectatePos);

            if (ModCompatChecker.valkyrienSkies()) {
                accurateSpectatePos = VSHelper.toWorldPosition(spectateTarget.getLevel(), spectatePos, accurateSpectatePos);
                spectatePos = VSHelper.toWorldPosition(spectateTarget.getLevel(), spectatePos);
            }

            if (spectatePos.distManhattan(new Vec3i((int) player.position().x, (int) player.position().y, (int) player.position().z)) > 3 || !player.level().dimension().location().toString().equals(spectateTarget.getDimensionKey().location().toString())) {
                TRTeleporter.simpleTeleport(player, spectateTarget.getLevel(), accurateSpectatePos.x, accurateSpectatePos.y, accurateSpectatePos.z, playerPreviousRot, playerPreviousYaw);
            }
            updatePlayerAbilities(serverPlayer, serverPlayer.getAbilities(), true);
            setRenderVortex(timeVortex);
            serverPlayer.onUpdateAbilities();
            syncToClients(null);
        }


    }

    public ResourceKey<Level> getPlayerPreviousDim() {
        return playerPreviousDim;
    }

    public void setPlayerPreviousDim(ResourceKey<Level> playerPreviousDim) {
        this.playerPreviousDim = playerPreviousDim;
    }

    public Vec3 getPlayerPreviousPosAccurate() {
        return playerPreviousPos;
    }

    @Deprecated
    public TardisNavLocation getPlayerPreviousPos() {
        return new TardisNavLocation(BlockPos.containing(playerPreviousPos), Direction.NORTH, playerPreviousDim);
    }

    @Deprecated
    public void setPlayerPreviousPos(TardisNavLocation playerPreviousPos) {
        this.playerPreviousPos = Vec3.atBottomCenterOf(playerPreviousPos.getPosition());
        this.playerPreviousDim = playerPreviousPos.getDimensionKey();
    }

    public void setPlayerPreviousPos(Vec3 playerPreviousPos) {
        this.playerPreviousPos = playerPreviousPos;
    }

    public void resetPlayerPreviousPos() {
        this.playerPreviousDim = Level.OVERWORLD;
        this.playerPreviousPos = Vec3.ZERO;
        this.playerPreviousRot = 0;
        this.playerPreviousYaw = 0;
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
            tag.putUUID("ViewedTardis", viewedTardis);
        }

        tag.putBoolean("RenderVortex", renderVortex);
        tag.putFloat("PlayerPreviousRot", playerPreviousRot);
        tag.putFloat("PlayerPreviousYaw", playerPreviousYaw);
        tag.put(
                "PlayerPreviousDim",
                Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, playerPreviousDim).getOrThrow(false, err -> {})
        );
        tag.put(
                "PlayerPreviousPos",
                Vec3.CODEC.encodeStart(NbtOps.INSTANCE, playerPreviousPos).getOrThrow(false, err -> {})
        );

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

        playerPreviousRot = tag.getFloat("PlayerPreviousRot");
        playerPreviousYaw = tag.getFloat("PlayerPreviousYaw");

        // For backwards-compatibility.
        if (tag.contains("TardisPlayerPos")) {
            var oldPos = TardisNavLocation.deserialize(tag.getCompound("TardisPlayerPos"));
            playerPreviousDim = oldPos.getDimensionKey();
            playerPreviousPos = Vec3.atBottomCenterOf(oldPos.getPosition());
        }

        if (tag.contains("PlayerPreviousDim")) {
            playerPreviousDim = Level.RESOURCE_KEY_CODEC.parse(
                    NbtOps.INSTANCE, tag.get("PlayerPreviousDim")
            ).resultOrPartial(err -> {}).orElse(playerPreviousDim);
        }
        if (tag.contains("PlayerPreviousPos")) {
            playerPreviousPos = Vec3.CODEC.parse(
                    NbtOps.INSTANCE, tag.get("PlayerPreviousPos")
            ).resultOrPartial(err -> {}).orElse(playerPreviousPos);
        }

        if (tag.hasUUID("ViewedTardis")) {
            this.viewedTardis = tag.getUUID("ViewedTardis");
        } else {
            this.viewedTardis = null;
        }

        renderVortex = tag.getBoolean("RenderVortex");

    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void endShellView(ServerPlayer serverPlayer) {
        if (!isViewingTardis()) return;

        TRTeleporter.simpleTeleport(serverPlayer, serverPlayer.getServer().getLevel(getPlayerPreviousDim()), playerPreviousPos.x, playerPreviousPos.y, playerPreviousPos.z, playerPreviousYaw, playerPreviousRot);
        updatePlayerAbilities(serverPlayer, serverPlayer.getAbilities(), false);
        serverPlayer.onUpdateAbilities();
        new S2CResetPostShellView().send(serverPlayer);

        resetPlayerPreviousPos();
        setRenderVortex(false);
        // Clear the viewed TARDIS UUID
        setViewedTardis(null);

        syncToClients(null);

    }

    @Override
    public void syncToClients(@Nullable ServerPlayer serverPlayerEntity) {
        if (player != null && player.level().isClientSide)
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
        if (tardisLevelOperator.getLevelKey() == getPlayerPreviousDim()) {
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
