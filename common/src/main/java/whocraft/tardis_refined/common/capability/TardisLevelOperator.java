package whocraft.tardis_refined.common.capability;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.common.blockentity.desktop.door.RootShellDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.tardis.manager.TardisFlightEventManager;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.dimension.DelayedTeleportData;
import whocraft.tardis_refined.common.tardis.ExteriorShell;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisControlManager;
import whocraft.tardis_refined.common.tardis.manager.TardisExteriorManager;
import whocraft.tardis_refined.common.tardis.manager.TardisInteriorManager;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.Optional;

public class TardisLevelOperator {

    private Level level;
    private boolean setUp = false;
    private TardisInternalDoor internalDoor = null;

    // Managers
    private TardisExteriorManager exteriorManager;
    private TardisInteriorManager interiorManager;
    private TardisControlManager controlManager;
    private TardisFlightEventManager tardisFlightEventManager;


    private TardisClientData tardisClientData;

    public TardisLevelOperator(Level level) {
        this.level = level;
        this.exteriorManager = new TardisExteriorManager(this);
        this.interiorManager = new TardisInteriorManager(this);
        this.controlManager = new TardisControlManager(this);
        this.tardisFlightEventManager = new TardisFlightEventManager(this);
        this.tardisClientData = new TardisClientData(level.dimension());
    }

    @ExpectPlatform
    public static Optional<TardisLevelOperator> get(ServerLevel level) {
        throw new AssertionError();
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean(NbtConstants.TARDIS_IS_SETUP, this.setUp);

        if (this.internalDoor != null) {
            compoundTag.putString(NbtConstants.TARDIS_INTERNAL_DOOR_ID, this.internalDoor.getID());
            compoundTag.put(NbtConstants.TARDIS_INTERNAL_DOOR_POSITION, NbtUtils.writeBlockPos(this.internalDoor.getDoorPosition()));
        }

        compoundTag = this.exteriorManager.saveData(compoundTag);
        compoundTag = this.interiorManager.saveData(compoundTag);
        compoundTag = this.controlManager.saveData(compoundTag);
        compoundTag = this.tardisFlightEventManager.saveData(compoundTag);

        return compoundTag;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.setUp = tag.getBoolean(NbtConstants.TARDIS_IS_SETUP);

        CompoundTag doorPos = tag.getCompound(NbtConstants.TARDIS_INTERNAL_DOOR_POSITION);
        if (doorPos != null) {
            if (level.getBlockEntity(NbtUtils.readBlockPos(doorPos)) instanceof TardisInternalDoor door) {
                this.internalDoor = door;
            }
        }

        // Managers
        this.exteriorManager.loadData(tag);
        this.interiorManager.loadData(tag);
        this.controlManager.loadData(tag);
        this.tardisFlightEventManager.loadData(tag);


        tardisClientData.sync((ServerLevel) this.getLevel());
    }

    public Level getLevel() {
        return level;
    }

    public void tick(ServerLevel level) {
        interiorManager.tick(level);
        controlManager.tick(level);
        tardisFlightEventManager.tick();

        var shouldSync = false;

        // If the Tardis's flying status does not match the control manager's in-flight status
        if (controlManager.isInFlight() != tardisClientData.isFlying()) {
            // If the current level is a ServerLevel instance
            // Set the Tardis's flying status to match the control manager's in-flight status
            tardisClientData.setFlying(controlManager.isInFlight());
            shouldSync = true;
        }

        if (controlManager.shouldThrottleBeDown() != tardisClientData.isThrottleDown()) {
            tardisClientData.setThrottleDown(controlManager.shouldThrottleBeDown());
            shouldSync = true;
        }

        if (exteriorManager.isLanding() != tardisClientData.isLanding()) {
            tardisClientData.setIsLanding(exteriorManager.isLanding());
            shouldSync = true;
        }

        if (exteriorManager.isTakingOff() != tardisClientData.isTakingOff()) {
            tardisClientData.setIsTakingOff(exteriorManager.isTakingOff());
            shouldSync = true;
        }

        if (controlManager.getCurrentExteriorTheme() != tardisClientData.getShellTheme()) {
            tardisClientData.setShellTheme(controlManager.getCurrentExteriorTheme());
            shouldSync = true;
        }

        if (tardisFlightEventManager.isInDangerZone() != tardisClientData.isInDangerZone()) {
            tardisClientData.setInDangerZone(tardisFlightEventManager.isInDangerZone());
            shouldSync = true;
        }

        if (tardisFlightEventManager.dangerZoneShakeScale() != tardisClientData.flightShakeScale()) {
            tardisClientData.setFlightShakeScale(tardisFlightEventManager.dangerZoneShakeScale());
            shouldSync = true;
        }

        if (controlManager.isOnCooldown() != tardisClientData.isOnCooldown()) {
            tardisClientData.setIsOnCooldown(controlManager.isOnCooldown());
            shouldSync = true;
        }



        // Synchronize the Tardis's data across the server
        if (shouldSync) {
            tardisClientData.sync(level);
            if (getExteriorManager().getLastKnownLocation() != null) {
                tardisClientData.sync(getExteriorManager().getLastKnownLocation().level);
            }
        }
    }

    /**
     * Moves the entity into the TARDIS. If the TARDIS has no door established, the player is sent to 0,0,0.
     *
     * @param player Player Entity.
     **/
    public void enterTardis(ExteriorShell shell, Player player, BlockPos externalPos, Level level, Direction direction) {

        if (!setUp) {
            this.interiorManager.generateDesktop(shell.getAssociatedTheme());
            TardisNavLocation navLocation = new TardisNavLocation(externalPos, direction.getOpposite(), (ServerLevel) level);
            this.getExteriorManager().setLastKnownLocation(navLocation);
            this.getControlManager().setTargetLocation(navLocation);
            this.setUp = true;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            if (internalDoor != null) {
                BlockPos targetPosition = internalDoor.getEntryPosition();
                Direction dir = internalDoor.getEntryRotation();

                ChunkAccess chunk = getLevel().getChunk(internalDoor.getDoorPosition());
                if (getLevel() instanceof ServerLevel serverLevel) {
                    serverLevel.setChunkForced(chunk.getPos().x, chunk.getPos().z, true);
                }
                level.getChunkSource().updateChunkForced(chunk.getPos(), true);
                DelayedTeleportData.getOrCreate(serverPlayer.getLevel()).schedulePlayerTeleport(serverPlayer, getLevel().dimension(), Vec3.atCenterOf(targetPosition), dir.get2DDataValue() * (360 / 4));
            } else {

                // TODO: Scan for console units near the center to warp to.

                ChunkAccess chunk = getLevel().getChunk(TardisArchitectureHandler.DESKTOP_CENTER_POS);

                if (getLevel() instanceof ServerLevel serverLevel) {
                    serverLevel.setChunkForced(chunk.getPos().x, chunk.getPos().z, true);
                }
                level.getChunkSource().updateChunkForced(chunk.getPos(), true);
                DelayedTeleportData.getOrCreate(serverPlayer.getLevel()).schedulePlayerTeleport(serverPlayer, getLevel().dimension(), Vec3.atCenterOf(TardisArchitectureHandler.DESKTOP_CENTER_POS.above()), 0);
            }
        }

        tardisClientData.sync((ServerLevel) this.getLevel());
        TardisEvents.TARDIS_ENTRY_EVENT.invoker().onEnterTardis(this, shell, player, externalPos, level, direction);
    }

    public boolean isTardisReady() {
        return !this.getInteriorManager().isGeneratingDesktop();
    }

    public void exitTardis(Player player) {

        if (!this.internalDoor.isOpen()) {
            return;
        }

        if(getExteriorManager().getCurrentTheme() != null) {
            ShellTheme theme = getExteriorManager().getCurrentTheme();
            if(ModCompatChecker.immersivePortals() && !(this.internalDoor instanceof RootShellDoorBlockEntity)) {
               if(ImmersivePortals.exteriorHasPortalSupport(theme)) {
                   return;
               }
            }
        }

        if (this.exteriorManager != null) {
            if (this.exteriorManager.getLastKnownLocation() != null) {
                BlockPos targetPosition = this.exteriorManager.getLastKnownLocation().position;
                ServerLevel targetLevel = this.exteriorManager.getLastKnownLocation().level;

                ChunkAccess preloadedArea = this.exteriorManager.getLastKnownLocation().level.getChunk(targetPosition);

                if (player instanceof ServerPlayer serverPlayer) {
                    if (targetLevel.getBlockEntity(targetPosition) instanceof ExteriorShell shellBaseBlockEntity) {
                        BlockPos landingArea = shellBaseBlockEntity.getExitPosition();
                        DelayedTeleportData.getOrCreate(serverPlayer.getLevel()).schedulePlayerTeleport(serverPlayer, targetLevel.dimension(), Vec3.atCenterOf(landingArea), this.exteriorManager.getLastKnownLocation().rotation.get2DDataValue() * (360 / 4));
                    }
                }
            }
        }
    }

    public void setDoorClosed(boolean closeDoor) {
        if (getExteriorManager() != null) {
            if (getExteriorManager().getLastKnownLocation() != null) {
                getExteriorManager().setDoorClosed(closeDoor);
            }
        }
        if (getInternalDoor() != null) {
            getInternalDoor().setClosed(closeDoor);
        }

        if(closeDoor) {
            TardisEvents.DOOR_CLOSED_EVENT.invoker().onDoorClosed(this);
        } else {
            TardisEvents.DOOR_OPENED_EVENT.invoker().onDoorOpen(this);
        }
    }

    public void setShellTheme(ShellTheme theme) {
        getExteriorManager().setShellTheme(theme);
        getInteriorManager().setShellTheme(theme);
        this.getControlManager().setCurrentExteriorTheme(theme);
        TardisEvents.SHELL_CHANGE_EVENT.invoker().onShellChange(this, theme);
    }

    /**
     * Sets the main operating door of an interior.
     *
     * @param door Internal door object.
     **/
    public void setInternalDoor(TardisInternalDoor door) {
        if (this.internalDoor != null) {
            this.internalDoor.onSetMainDoor(false);
        }
        this.internalDoor = door;
        this.internalDoor.onSetMainDoor(true);
    }

    public TardisExteriorManager getExteriorManager() {
        return this.exteriorManager;
    }

    public TardisInternalDoor getInternalDoor() {
        return this.internalDoor;
    }

    public TardisInteriorManager getInteriorManager() {
        return this.interiorManager;
    }

    public TardisControlManager getControlManager() {
        return this.controlManager;
    }

    public TardisFlightEventManager getTardisFlightEventManager() {
        return this.tardisFlightEventManager;
    }

}
