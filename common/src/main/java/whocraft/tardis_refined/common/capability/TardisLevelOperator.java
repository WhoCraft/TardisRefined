package whocraft.tardis_refined.common.capability;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.door.RootShellDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.hum.HumEntry;
import whocraft.tardis_refined.common.hum.TardisHums;
import whocraft.tardis_refined.common.tardis.ExteriorShell;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.*;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.constants.NbtConstants;

import java.util.Optional;

import static whocraft.tardis_refined.common.block.RootPlantBlock.FACING;
import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.OPEN;

public class TardisLevelOperator {
    private final Level level;
    private boolean hasInitiallyGenerated = false;
    private TardisInternalDoor internalDoor = null;

    // Managers
    private final TardisExteriorManager exteriorManager;
    private final TardisInteriorManager interiorManager;
    private final TardisPilotingManager pilotingManager;
    private final TardisWaypointManager tardisWaypointManager;
    private final FlightDanceManager flightDanceManager;
    private final TardisClientData tardisClientData;
    private final UpgradeHandler upgradeHandler;
    private final AestheticHandler aestheticHandler;

    // TARDIS state refers to different stages of TARDIS creation. This allows for different logic to operate in those moments.
    private int tardisState = 0;

    public static final int STATE_CAVE = 0;
    public static final int STATE_TERRAFORMED_NO_EYE = 1;
    public static final int STATE_EYE_OF_HARMONY = 2;


    public TardisLevelOperator(Level level) {
        this.level = level;
        this.exteriorManager = new TardisExteriorManager(this);
        this.interiorManager = new TardisInteriorManager(this);
        this.pilotingManager = new TardisPilotingManager(this);
        this.tardisWaypointManager = new TardisWaypointManager(this);
        this.tardisClientData = new TardisClientData(level.dimension());
        this.upgradeHandler = new UpgradeHandler(this);
        this.aestheticHandler = new AestheticHandler(this);
        this.flightDanceManager = new FlightDanceManager(this);
    }

    public UpgradeHandler getUpgradeHandler() {
        return upgradeHandler;
    }

    public TardisClientData tardisClientData() {
        return tardisClientData;
    }

    public AestheticHandler getAestheticHandler() {
        return aestheticHandler;
    }

    public FlightDanceManager getFlightDanceManager() {
        return this.flightDanceManager;
    }

    @ExpectPlatform
    public static Optional<TardisLevelOperator> get(ServerLevel level) {
        throw new AssertionError();
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean(NbtConstants.TARDIS_IS_SETUP, this.hasInitiallyGenerated);

        if (this.internalDoor != null) {
            compoundTag.putString(NbtConstants.TARDIS_INTERNAL_DOOR_ID, this.internalDoor.getID());
            compoundTag.put(NbtConstants.TARDIS_INTERNAL_DOOR_POSITION, NbtUtils.writeBlockPos(this.internalDoor.getDoorPosition()));
        }

        compoundTag = this.exteriorManager.saveData(compoundTag);
        compoundTag = this.interiorManager.saveData(compoundTag);
        compoundTag = this.pilotingManager.saveData(compoundTag);
        compoundTag = this.tardisWaypointManager.saveData(compoundTag);
        compoundTag = this.upgradeHandler.saveData(compoundTag);
        compoundTag = this.aestheticHandler.saveData(compoundTag);

        compoundTag.putInt("tardis_state", this.tardisState);

        return compoundTag;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.hasInitiallyGenerated = tag.getBoolean(NbtConstants.TARDIS_IS_SETUP);

        CompoundTag doorPos = tag.getCompound(NbtConstants.TARDIS_INTERNAL_DOOR_POSITION);
        if (doorPos != null) {
            if (level.getBlockEntity(NbtUtils.readBlockPos(doorPos)) instanceof TardisInternalDoor door) {
                this.internalDoor = door;
                this.internalDoor.setID(tag.getString(NbtConstants.TARDIS_INTERNAL_DOOR_ID));
            }
        }

        // Managers
        this.exteriorManager.loadData(tag);
        this.interiorManager.loadData(tag);
        this.pilotingManager.loadData(tag);
        this.tardisWaypointManager.loadData(tag);
        this.upgradeHandler.loadData(tag);
        this.aestheticHandler.loadData(tag);

        this.tardisState = tag.getInt("tardis_state");

        tardisClientData.sync();
    }

    public Level getLevel() {
        return level;
    }

    public void tick(ServerLevel level) {
        interiorManager.tick(level);
        pilotingManager.tick(level);
        flightDanceManager.tick();
        
        var shouldSync = level.getGameTime() % 40 == 0;
        if (shouldSync) {
            tardisClientData.setIsOnCooldown(pilotingManager.isOnCooldown());
            tardisClientData.setShellTheme(aestheticHandler.getShellTheme());
            tardisClientData.setShellPattern(aestheticHandler.shellPattern().id());
            tardisClientData.setHumEntry(interiorManager.getHumEntry());
            tardisClientData.setFuel(pilotingManager.getFuel());
            tardisClientData.setMaximumFuel(pilotingManager.getMaximumFuel());
            tardisClientData.setTardisState(tardisState);

            tardisClientData.sync();
        } else {
            tardisClientData.setFlying(pilotingManager.isInFlight());
            tardisClientData.setIsLanding(exteriorManager.isLanding());
            tardisClientData.setIsTakingOff(exteriorManager.isTakingOff());
            tardisClientData.setThrottleStage(pilotingManager.getThrottleStage());
            tardisClientData.setHandbrakeEngaged(pilotingManager.isHandbrakeOn());
            tardisClientData.sync();
        }
    }

    public boolean hasInitiallyGenerated() {
        return hasInitiallyGenerated;
    }

    public void setInitiallyGenerated(boolean hasInitiallyGenerated) {
        this.hasInitiallyGenerated = hasInitiallyGenerated;
    }

    /**
     * Moves the entity into the TARDIS. If the TARDIS has no door established, the entity is sent to 0,100,0.
     **/
    public boolean enterTardis(Entity entity, BlockPos externalShellPos, ServerLevel shellLevel, Direction shellDirection) {

        if (this.level instanceof ServerLevel targetServerLevel) {

            BlockPos targetPosition = internalDoor != null ? internalDoor.getEntryPosition() : TardisArchitectureHandler.DESKTOP_CENTER_POS.above();
            Direction doorDirection = internalDoor != null ? internalDoor.getDoorRotation() : entity.getDirection();

            TardisNavLocation sourceLocation = new TardisNavLocation(externalShellPos, shellDirection, shellLevel);
            TardisNavLocation targetLocation = new TardisNavLocation(targetPosition, doorDirection, targetServerLevel);

            TardisHelper.teleportEntityTardis(this, entity, sourceLocation, targetLocation, true);
            return true;
        }

        return false;

    }
    public boolean isTardisReady() {
        return !this.getInteriorManager().isGeneratingDesktop();
    }
    public boolean exitTardis(Entity entity, ServerLevel doorLevel, BlockPos doorPos, Direction doorDirection) {

        if (!this.internalDoor.isOpen()) {
            return false;
        }

        if (aestheticHandler.getShellTheme() != null) {
            ResourceLocation theme = aestheticHandler.getShellTheme();
            if (ModCompatChecker.immersivePortals() && !(this.internalDoor instanceof RootShellDoorBlockEntity)) {
                if (ImmersivePortals.exteriorHasPortalSupport(theme)) {
                    return false;
                }
            }
        }

        if (this.exteriorManager != null) {
            if (this.exteriorManager.getLastKnownLocation() != null) {

                TardisNavLocation targetLocation = this.exteriorManager.getLastKnownLocation();
                BlockPos exteriorPos = targetLocation.getPosition();
                ServerLevel targetLevel = targetLocation.getLevel();
                Direction exteriorDirection = targetLocation.getDirection().getOpposite();

                BlockPos teleportPos = exteriorPos;

                if (targetLevel.getBlockEntity(exteriorPos) instanceof ExteriorShell exteriorShell) {
                    teleportPos = exteriorShell.getExitPosition();
                }

                TardisNavLocation sourceLocation = new TardisNavLocation(doorPos, doorDirection, doorLevel);
                TardisNavLocation destinationLocation = new TardisNavLocation(teleportPos, exteriorDirection, targetLevel);

                TardisHelper.teleportEntityTardis(this, entity, sourceLocation, destinationLocation, false);
            }
        }

        return true;
    }

    public void setDoorClosed(boolean closeDoor) {
        TardisExteriorManager extManager = getExteriorManager();
        TardisInternalDoor intDoor = getInternalDoor();

        if (intDoor != null) {
            intDoor.setClosed(closeDoor);
        }
        if (closeDoor) {
            TardisEvents.DOOR_CLOSED_EVENT.invoker().onDoorClosed(this);
        } else {
            TardisEvents.DOOR_OPENED_EVENT.invoker().onDoorOpen(this);
        }

        if (extManager != null) {
            if (extManager.getLastKnownLocation() != null) {
                extManager.setDoorClosed(closeDoor);
            }
        }
    }

    public void setShellTheme(ResourceLocation theme, boolean setupTardis) {
        this.getAestheticHandler().setShellTheme(theme, setupTardis, getExteriorManager().getLastKnownLocation());
        tardisClientData.setShellTheme(theme);
        tardisClientData.setShellPattern(aestheticHandler.shellPattern().id());
        tardisClientData.sync();
        TardisEvents.SHELL_CHANGE_EVENT.invoker().onShellChange(this, theme, setupTardis);
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
        if (door != null) //If the new door value is not null
            this.internalDoor.onSetMainDoor(true);
    }
    public void setupInitialCave(ServerLevel shellServerLevel, BlockState shellBlockState, BlockPos shellBlockPos) {
        this.interiorManager.generateDesktop(TardisDesktops.DEFAULT_OVERGROWN_THEME);

        Direction direction = shellBlockState.getValue(ShellBaseBlock.FACING).getOpposite();
        TardisNavLocation navLocation = new TardisNavLocation(shellBlockPos, direction, shellServerLevel);
        this.exteriorManager.setLastKnownLocation(navLocation);
        this.pilotingManager.setTargetLocation(navLocation);

        shellServerLevel.setBlock(shellBlockPos, shellBlockState.setValue(OPEN, true), Block.UPDATE_ALL);

        this.setInitiallyGenerated(true);
        this.setTardisState(TardisLevelOperator.STATE_CAVE);
        this.interiorManager.setHumEntry(TardisHums.CAVE);
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
    public TardisPilotingManager getPilotingManager() {
        return this.pilotingManager;
    }
    public TardisWaypointManager getTardisWaypointManager() {
        return tardisWaypointManager;
    }

    public int getTardisState() {
        return tardisState;
    }

    public void setTardisState(int state) {
        this.tardisState = state;
    }
}
