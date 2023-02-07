package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.door.InternalDoorBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;

import java.util.Optional;
import java.util.UUID;

public class AbstractEntityBlockDoor extends BlockEntity implements TardisInternalDoor {
    private boolean isLocked = false;
    private String uuid_id;
    private boolean isOpen = false;
    private boolean isMainDoor = false;

    private TardisLevelOperator operator;

    public AbstractEntityBlockDoor(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.uuid_id = UUID.randomUUID().toString();
    }

    @Override
    public boolean isMainDoor() {
        return isMainDoor;
    }

    @Override
    public void onSetMainDoor(boolean isMainDoor) {
        this.isMainDoor = isMainDoor;
    }

    @Override
    public String getID() {
        return this.uuid_id;
    }

    @Override
    public void setID(String id) {
        this.uuid_id = id;
    }

    @Override
    public boolean isOpen() {
        return getBlockState().getValue(GlobalDoorBlock.OPEN);
    }

    @Override
    public void setClosed(boolean state) {
        BlockState blockState = getLevel().getBlockState(getDoorPosition());
        getLevel().setBlock(getDoorPosition(), blockState.setValue(GlobalDoorBlock.OPEN, !state), 2);
        getLevel().playSound(null, getDoorPosition(), isLocked ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1, isLocked ? 1.4F : 1F);
    }

    @Override
    public BlockPos getDoorPosition() {
        return this.getBlockPos();
    }

    @Override
    public BlockPos getEntryPosition() {

        int direction = getBlockState().getValue(ShellBaseBlock.FACING).get2DDataValue();
        switch (direction) {
            case 3:
                return new BlockPos(getBlockPos().getX() - 1, getBlockPos().getY(), getBlockPos().getZ());
            case 2:
                return new BlockPos(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ() + 1);
            case 1:
                return new BlockPos(getBlockPos().getX() + 1, getBlockPos().getY(), getBlockPos().getZ());
            case 0:
                return new BlockPos(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ() - 1);
        }

        return getBlockPos().above();
    }

    @Override
    public Direction getEntryRotation() {
        return getBlockState().getValue(InternalDoorBlock.FACING).getOpposite();
    }

    @Override
    public void onEntityExit(ServerEntity entity) {

    }

    @Override
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public boolean locked() {
        return isLocked;
    }

    public TardisLevelOperator getOperator() {
        return operator;
    }

    public void onBlockPlaced() {
        if (!getLevel().isClientSide()) {
            Optional<TardisLevelOperator> lvlOper = TardisLevelOperator.get((ServerLevel) this.getLevel());
            if (lvlOper.isPresent()) {
                this.operator = lvlOper.get();
            }
        }
    }

    // Generic Tile stuff.

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        compoundTag.putBoolean(NbtConstants.DOOR_IS_MAIN_DOOR, this.isMainDoor);
        compoundTag.putString(NbtConstants.DOOR_ID, this.uuid_id);
        compoundTag.putBoolean(NbtConstants.DOOR_IS_LOCKED, this.isLocked);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.isMainDoor = compoundTag.getBoolean(NbtConstants.DOOR_IS_MAIN_DOOR);
        this.uuid_id = compoundTag.getString(NbtConstants.DOOR_ID);
        this.isLocked = compoundTag.getBoolean(NbtConstants.DOOR_IS_LOCKED);
    }

}
