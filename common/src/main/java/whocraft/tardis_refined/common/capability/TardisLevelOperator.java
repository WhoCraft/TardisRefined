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
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.blockentity.door.ITardisInternalDoor;
import whocraft.tardis_refined.common.dimension.DelayedTeleportData;
import whocraft.tardis_refined.common.tardis.IExteriorShell;
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
    private ITardisInternalDoor internalDoor = null;
    private TardisExteriorManager exteriorManager;
    private TardisInteriorManager interiorManager;
    private TardisControlManager controlManager;

    private TardisClientData tardisClientData;

    public TardisLevelOperator(Level level) {
        this.level = level;
        this.exteriorManager = new TardisExteriorManager(this);
        this.interiorManager = new TardisInteriorManager(this);
        this.controlManager = new TardisControlManager(this);
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

        return compoundTag;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.setUp = tag.getBoolean(NbtConstants.TARDIS_IS_SETUP);

        CompoundTag doorPos = tag.getCompound(NbtConstants.TARDIS_INTERNAL_DOOR_POSITION);
        if (doorPos != null) {
            if (level.getBlockEntity(NbtUtils.readBlockPos(doorPos)) instanceof ITardisInternalDoor door) {
                this.internalDoor = door;
            }
        }

        // Managers
        this.exteriorManager.loadData(tag);
        this.interiorManager.loadData(tag);
        this.controlManager.loadData(tag);

    }

    public Level getLevel() {
        return level;
    }

    public void tick(ServerLevel level) {
        interiorManager.tick(level);
        controlManager.tick(level);

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
    public void enterTardis(IExteriorShell shell, Player player, BlockPos externalPos, Level level, Direction direction) {

        if (!setUp) {

            this.interiorManager.generateDesktop(shell.getAssociatedTheme());
            this.getExteriorManager().setLastKnownLocation(new TardisNavLocation(externalPos, direction.getOpposite(), (ServerLevel) level));
            this.getControlManager().setTargetLocation(new TardisNavLocation(externalPos, direction.getOpposite(), (ServerLevel) level));
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
        TardisEvents.TARDIS_ENTRY_EVENT.invoker().onEnterTardis(shell, player, externalPos, level, direction);
    }

    public boolean isTardisReady() {
        return !this.getInteriorManager().isGeneratingDesktop();
    }

    public void exitTardis(Player player) {

        if (!this.internalDoor.isOpen()) {
            return;
        }

        if (this.exteriorManager != null) {
            if (this.exteriorManager.getLastKnownLocation() != null) {
                BlockPos targetPosition = this.exteriorManager.getLastKnownLocation().position;
                ServerLevel targetLevel = this.exteriorManager.getLastKnownLocation().level;

                ChunkAccess preloadedArea = this.exteriorManager.getLastKnownLocation().level.getChunk(targetPosition);

                if (player instanceof ServerPlayer serverPlayer) {
                    if (targetLevel.getBlockEntity(targetPosition) instanceof IExteriorShell shellBaseBlockEntity) {
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
    }

    public void setShellTheme(ShellTheme theme) {
        getExteriorManager().setShellTheme(theme);
        getInteriorManager().setShellTheme(theme);
        this.getControlManager().setCurrentExteriorTheme(theme);
    }

    /**
     * Sets the main operating door of an interior.
     *
     * @param door Internal door object.
     **/
    public void setInternalDoor(ITardisInternalDoor door) {
        if (this.internalDoor != null) {
            this.internalDoor.onSetMainDoor(false);
        }
        this.internalDoor = door;
        this.internalDoor.onSetMainDoor(true);
    }

    public TardisExteriorManager getExteriorManager() {
        return this.exteriorManager;
    }

    public ITardisInternalDoor getInternalDoor() {
        return this.internalDoor;
    }

    public TardisInteriorManager getInteriorManager() {
        return this.interiorManager;
    }

    public TardisControlManager getControlManager() {
        return this.controlManager;
    }

}
