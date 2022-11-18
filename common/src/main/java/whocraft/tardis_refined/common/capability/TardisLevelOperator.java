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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.NbtConstants;
import whocraft.tardis_refined.common.dimension.DelayedTeleportData;
import whocraft.tardis_refined.common.tardis.data.TardisExternalReadingsData;
import whocraft.tardis_refined.common.tardis.data.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.interior.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.interior.exit.ITardisInternalDoor;
import whocraft.tardis_refined.common.tardis.interior.shell.IExteriorShell;

import java.util.Optional;

public class TardisLevelOperator {

    private ServerLevel level;
    private boolean setUp = false;
    private ITardisInternalDoor internalDoor = null;
    private TardisExternalReadingsData externalReadingsData;

    public TardisLevelOperator(ServerLevel level) {

        this.level = level;
        this.externalReadingsData = new TardisExternalReadingsData(this);
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

        compoundTag = this.externalReadingsData.saveData(compoundTag);

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

        // Datareadings
        if (!level.isClientSide()) {
            this.externalReadingsData.loadData(tag);
        }

    }

    public ServerLevel getLevel() {
        return level;
    }

    public void tick(Level level) {

    }

    /**
     * Moves the entity into the TARDIS. If the TARDIS has no door established, the player is sent to 0,0,0.
     * @param player Player Entity.
     * **/
    public void enterTardis(IExteriorShell shell, Player player, BlockPos externalPos, Level level, Direction direction) {

        if (!setUp) {
            TardisArchitectureHandler.generateDesktop(getLevel(), shell.getAssociatedTheme());
            this.externalReadingsData.setLastKnownLocation(new TardisNavLocation(externalPos, direction.getOpposite(), (ServerLevel) level));
            this.setUp = true;
            return;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            if (internalDoor != null) {
                BlockPos targetPosition = internalDoor.getEntryPosition();
                Direction dir = internalDoor.getEntryRotation();

                ChunkAccess chunk = getLevel().getChunk(internalDoor.getDoorPosition());
                getLevel().setChunkForced(chunk.getPos().x, chunk.getPos().z, true);
                getLevel().getChunkSource().updateChunkForced(chunk.getPos(), true);

                DelayedTeleportData.getOrCreate(serverPlayer.getLevel()).schedulePlayerTeleport(serverPlayer, getLevel().dimension(), Vec3.atCenterOf(targetPosition), dir.get2DDataValue() * (360/4));
            } else {

                // TODO: Scan for console units near the center to warp to.

                ChunkAccess chunk = getLevel().getChunk(TardisArchitectureHandler.DESKTOP_CENTER_POS);
                getLevel().setChunkForced(chunk.getPos().x, chunk.getPos().z, true);
                getLevel().getChunkSource().updateChunkForced(chunk.getPos(), true);
                DelayedTeleportData.getOrCreate(serverPlayer.getLevel()).schedulePlayerTeleport(serverPlayer, getLevel().dimension(), Vec3.atCenterOf(TardisArchitectureHandler.DESKTOP_CENTER_POS.above()), 0);
            }
        }




    }

    public void exitTardis(Player player) {
        if (this.externalReadingsData != null) {
            if (this.externalReadingsData.getLastKnownLocation() != null) {
                BlockPos targetPosition =  this.externalReadingsData.getLastKnownLocation().position;
                ServerLevel targetLevel = this.externalReadingsData.getLastKnownLocation().level;

                ChunkAccess preloadedArea = this.externalReadingsData.getLastKnownLocation().level.getChunk(targetPosition);

                if (player instanceof ServerPlayer serverPlayer) {
                    if (targetLevel.getBlockEntity(targetPosition) instanceof IExteriorShell shellBaseBlockEntity) {
                        BlockPos landingArea = shellBaseBlockEntity.getExitPosition();
                        DelayedTeleportData.getOrCreate(serverPlayer.getLevel()).schedulePlayerTeleport(serverPlayer, targetLevel.dimension(), Vec3.atCenterOf(landingArea), this.externalReadingsData.getLastKnownLocation().rotation.get2DDataValue() * (360/4));
                    }
                }
            }
        }
    }

    /**
     * Sets the main operating door of an interior.
     * @param door Internal door object.
     * **/
    public void setInternalDoor(ITardisInternalDoor door) {
        if (this.internalDoor != null) {
            this.internalDoor.onSetMainDoor(false);
        }
        this.internalDoor = door;
        this.internalDoor.onSetMainDoor(true);
    }


    public TardisExternalReadingsData getExternalReadingsData() {
        return this.externalReadingsData;
    }

    public ITardisInternalDoor getInternalDoor() {
        return this.internalDoor;
    }

}
