package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.door.InternalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.constants.NbtConstants;

import java.util.Optional;
import java.util.UUID;

public class AbstractDoorBlockEntity extends BlockEntity implements TardisInternalDoor {

    private boolean isLocked = false;
    private String uuid_id;
    private boolean isMainDoor = false;

    private TardisLevelOperator operator;

    public AbstractDoorBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
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
        this.setChanged();
    }

    @Override
    public boolean isOpen() {
        return this.getBlockState().getValue(InternalDoorBlock.OPEN);
    }

    @Override
    public void setClosed(boolean state) {
        BlockState blockState = this.getLevel().getBlockState(getDoorPosition());
        if (blockState.getBlock() instanceof InternalDoorBlock) {
            Level currentLevel = getLevel();
            currentLevel.setBlock(getDoorPosition(), blockState.setValue(GlobalDoorBlock.OPEN, !state), Block.UPDATE_CLIENTS);
            currentLevel.playSound(null, getDoorPosition(), isLocked ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1, isLocked ? 1.4F : 1F);
            this.setChanged();
        }
    }

    @Override
    public BlockPos getDoorPosition() {
        return this.getBlockPos();
    }

    @Override
    public BlockPos getEntryPosition() {
        Direction direction = this.getBlockState().getValue(InternalDoorBlock.FACING);
        return this.getBlockPos().offset(direction.getOpposite().getNormal());

    }

    @Override
    public Direction getEntryRotation() {
        return this.getBlockState().getValue(InternalDoorBlock.FACING).getOpposite();
    }

    @Override
    public Direction getDoorRotation() {
        return this.getBlockState().getValue(InternalDoorBlock.FACING);
    }

    @Override
    public void onEntityExit(ServerEntity entity) {

    }

    @Override
    public void setLocked(boolean locked) {
        this.isLocked = locked;
        this.setChanged();
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
            lvlOper.ifPresent(tardisLevelOperator -> this.operator = tardisLevelOperator);
        }
    }


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

    @Override
    public void onAttemptEnter(BlockState blockState, Level level, BlockPos doorPos, Entity entity) {
        if (!entity.level().isClientSide() && level instanceof ServerLevel serverLevel) {
            Optional<TardisLevelOperator> data = TardisLevelOperator.get(serverLevel);
            data.ifPresent(tardisLevelOperator -> {
                tardisLevelOperator.setInternalDoor(this);
                tardisLevelOperator.exitTardis(entity, serverLevel, doorPos, blockState.getValue(InternalDoorBlock.FACING), false);
            });
        }
    }

}
