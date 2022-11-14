package whocraft.tardis_refined.common.tardis.interior.exit;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;

import java.util.Optional;
import java.util.UUID;

public class AbstractEntityBlockDoor extends BlockEntity implements ITardisInternalDoor {

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
        return isOpen;
    }

    @Override
    public void setOpen(boolean state) {
        isOpen = state;
    }

    @Override
    public BlockPos getDoorPosition() {
        return this.getBlockPos();
    }

    @Override
    public BlockPos getEntryPosition() {
        return this.getBlockPos().above();
    }

    @Override
    public Rotation getEntryRotation() {
        return Rotation.NONE;
    }

    @Override
    public void onEntityExit(ServerEntity entity) {

    }

    public TardisLevelOperator getOperator() {
        return operator;
    }

    public void onBlockPlaced() {
        Optional<TardisLevelOperator> lvlOper = TardisLevelOperator.get((ServerLevel) this.getLevel());
        if (lvlOper.isPresent()) {
            this.operator = lvlOper.get();
        }
    }

    // Generic Tile stuff.

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        compoundTag.putBoolean(NBT_IsMainDoor, this.isMainDoor);
        compoundTag.putBoolean(NBT_IsOpen, this.isOpen);
        compoundTag.putString(NBT_ID, this.uuid_id);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);

        this.isMainDoor = compoundTag.getBoolean(NBT_IsMainDoor);
        this.uuid_id = compoundTag.getString(NBT_ID);
        this.isOpen = compoundTag.getBoolean(NBT_IsOpen);
    }

    private static String NBT_IsMainDoor = "is_main_door";
    private static String NBT_ID = "uuid_id";
    private static String NBT_IsOpen = "is_open";


}
