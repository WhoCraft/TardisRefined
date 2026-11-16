package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class BulkHeadDoorExtensionBlockEntity extends BlockEntity {

    private BlockPos masterDoorBlockPos;
    private BulkHeadDoorBlockEntity masterDoorBlockEntity;

    public BulkHeadDoorExtensionBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.BULK_HEAD_DOOR_EXT.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);

        if (masterDoorBlockPos != null) {
            compoundTag.put(NbtConstants.MASTER_DOOR, NbtUtils.writeBlockPos(this.masterDoorBlockPos));
        }

    }

    @Override
    public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        this.masterDoorBlockPos = NbtUtils.readBlockPos(compoundTag, NbtConstants.MASTER_DOOR).orElse(masterDoorBlockPos);

        super.loadAdditional(compoundTag, provider);

    }

    public BulkHeadDoorBlockEntity getMasterDoorBlockEntity() {
        if (this.masterDoorBlockEntity == null && this.masterDoorBlockPos != null) {
            // FETCH
            if (this.getLevel().getBlockEntity(this.masterDoorBlockPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                this.masterDoorBlockEntity = bulkHeadDoorBlockEntity;
                return this.masterDoorBlockEntity;
            }

        }

        if (this.masterDoorBlockEntity != null && this.masterDoorBlockPos != null) {
            return this.masterDoorBlockEntity;
        }

        return null;

    }

    public void setMasterDoorBlock(BulkHeadDoorBlockEntity entity) {
        this.masterDoorBlockEntity = entity;
        this.masterDoorBlockPos = entity.getBlockPos();
    }

    public InteractionResult onRightClick(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {

        BulkHeadDoorBlockEntity masterBlockEntity = getMasterDoorBlockEntity();

        if (masterBlockEntity != null) {
            masterBlockEntity.onRightClick(masterBlockEntity.getBlockState(), level, masterBlockEntity.getBlockPos(), player, blockHitResult);
        }

        return InteractionResult.SUCCESS;
    }

    public void onDestroy(Level level, BlockPos blockPos, BlockState blockState) {
        if (getMasterDoorBlockEntity() != null) {
            BulkHeadDoorBlock.clearDoor(level, this.masterDoorBlockPos, this.masterDoorBlockEntity.getBlockState());
            this.getLevel().destroyBlock(this.masterDoorBlockPos, true);
        }
    }

}
