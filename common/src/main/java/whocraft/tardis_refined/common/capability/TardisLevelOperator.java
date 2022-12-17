package whocraft.tardis_refined.common.capability;

import com.mojang.math.Vector3d;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.NbtConstants;
import whocraft.tardis_refined.client.TardisIntReactions;
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

    private TardisIntReactions tardisIntReactions;

    public TardisLevelOperator(Level level) {
        this.level = level;
        this.exteriorManager = new TardisExteriorManager(this);
        this.interiorManager = new TardisInteriorManager(this);
        this.controlManager = new TardisControlManager(this);
        this.tardisIntReactions = new TardisIntReactions(level.dimension());
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

        for (AABB aabb : get(level).get().interiorManager.unbreakableZones()) {
            BlockPos.betweenClosedStream(aabb).forEach(blockPos -> {
                Vec3 end = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                Vec3 start = aabb.getCenter();
                Vec3 path = start.subtract(end);
                for (int i = 0; i < 10; ++i) {
                    double percent = (double) i / 10.0D;
                    Vec3 spawnPoint = new Vec3(start.x() + 0.5D + path.x() * percent, start.y() + 1.3D + path.y() * percent, start.z() + 0.5D + path.z * percent);
                    level.sendParticles(ParticleTypes.ANGRY_VILLAGER, spawnPoint.x, spawnPoint.y, spawnPoint.z, 2, 0.0D, 0.0D, 0.0D, 0);
                }
            });

        }


        interiorManager.tick(level);
        controlManager.tick(level);

        // If the Tardis's flying status does not match the control manager's in-flight status
        if (controlManager.isInFlight() != tardisIntReactions.isFlying()) {
            // If the current level is a ServerLevel instance
            // Set the Tardis's flying status to match the control manager's in-flight status
            tardisIntReactions.setFlying(controlManager.isInFlight());

            // Synchronize the Tardis's data across the server
            tardisIntReactions.sync(level);
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
        getExteriorManager().setDoorClosed(closeDoor);
        getInternalDoor().setClosed(closeDoor);
    }

    public void setShellTheme(ShellTheme theme) {
        getExteriorManager().setShellTheme(theme);
        getInteriorManager().setShellTheme(theme);
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
