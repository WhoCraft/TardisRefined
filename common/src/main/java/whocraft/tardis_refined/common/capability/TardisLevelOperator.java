package whocraft.tardis_refined.common.capability;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkAccess;
import whocraft.tardis_refined.common.tardis.data.TardisExternalReadingsData;
import whocraft.tardis_refined.common.tardis.data.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.interior.TardisArchitecture;
import whocraft.tardis_refined.common.tardis.interior.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.interior.exit.ITardisInternalDoor;
import whocraft.tardis_refined.common.util.NbtUtil;

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
        compoundTag.putBoolean(NBT_IS_SETUP, this.setUp);
        if (this.internalDoor != null) {
            compoundTag.putString(NBT_INTERNAL_DOOR_ID, this.internalDoor.getID());
            compoundTag.putIntArray(NBT_INTERNAL_DOOR_POSITION, NbtUtil.getIntArrayFromBlockPos(this.internalDoor.getDoorPosition()));
        }

        compoundTag = this.externalReadingsData.saveData(compoundTag);

        return compoundTag;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.setUp = tag.getBoolean(NBT_IS_SETUP);
        int[] doorPos = tag.getIntArray(NBT_INTERNAL_DOOR_POSITION);
        if (doorPos != null) {
            this.internalDoor = (ITardisInternalDoor) level.getBlockEntity(NbtUtil.getBlockPosFromIntArray(doorPos));
        }

        // Datareadings
        this.externalReadingsData.loadData(tag);
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
    public void enterTardis(Player player, BlockPos externalPos, Level level) {
        if (!setUp) {
            TardisArchitectureHandler.generateDesktop(getLevel(), TardisArchitecture.FACTORY_THEME);
            TardisArchitectureHandler.createDesktopDoor(this, new BlockPos(2,99,0));
            this.externalReadingsData.setLastKnownLocation(new TardisNavLocation(externalPos, 0, (ServerLevel) level));
            this.setUp = true;
        }

        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (internalDoor != null) {
            BlockPos targetPosition = internalDoor.getEntryPosition();
            ChunkAccess preloadedArea = getLevel().getChunk(targetPosition);
            serverPlayer.teleportTo(getLevel(), targetPosition.getX(), targetPosition.getY(), targetPosition.getZ(), 0,0);
        } else {
            ChunkAccess preloadedArea = getLevel().getChunk(new BlockPos(0,0,0));
            serverPlayer.teleportTo(getLevel(), 0, 101, 0, 0, 0);
        }
    }

    public void exitTardis(Player player) {

        System.out.println("Exiting for the player.");

        if (this.externalReadingsData != null) {
            if (this.externalReadingsData.getLastKnownLocation() != null) {
                BlockPos targetPosition =  this.externalReadingsData.getLastKnownLocation().position;
                ServerLevel targetLevel = this.externalReadingsData.getLastKnownLocation().level;

                ChunkAccess preloadedArea = this.externalReadingsData.getLastKnownLocation().level.getChunk(targetPosition);
                ServerPlayer serverPlayer = (ServerPlayer) player;
                serverPlayer.teleportTo(targetLevel, targetPosition.getX(), targetPosition.getY() + 1, targetPosition.getZ(), 0,0);
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

    private static final String NBT_IS_SETUP = "has_setup";
    private static final String NBT_INTERNAL_DOOR_ID = "internal_door_id";
    private static final String NBT_INTERNAL_DOOR_POSITION = "internal_door_pos";

}
