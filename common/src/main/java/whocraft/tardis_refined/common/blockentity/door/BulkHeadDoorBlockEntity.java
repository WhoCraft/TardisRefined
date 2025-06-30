package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorExtensionBlock;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;
import whocraft.tardis_refined.registry.TRItemRegistry;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import static whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock.*;

public class BulkHeadDoorBlockEntity extends BlockEntity implements BlockEntityTicker<BulkHeadDoorBlockEntity> {

    private String doorName;

    public BulkHeadDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.BULK_HEAD_DOOR.get(), blockPos, blockState);
    }


    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, BulkHeadDoorBlockEntity blockEntity) {

        if (blockState.getValue(LOCKED)) {
            return;
        }

        double detectionRadius = 1.5;
        double centerX = blockPos.getX() + 0.5;
        double centerY = blockPos.getY() + 0.5;
        double centerZ = blockPos.getZ() + 0.5;

        for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(centerX - detectionRadius, centerY - detectionRadius, centerZ - detectionRadius, centerX + detectionRadius, centerY + detectionRadius, centerZ + detectionRadius))) {
            if (entity instanceof LivingEntity && !entity.isCrouching()) {
                toggleDoor(level, blockPos, blockState, true);
                return;
            }
        }

        toggleDoor(level, blockPos, blockState, false);

    }

    /**
     * Toggles the state of the associated BulkHeadDoorBlock, opening or closing it.
     *
     * @param level      The current world level.
     * @param blockPos   The position of the block entity in the world.
     * @param blockState The current state of the associated BulkHeadDoorBlock.
     * @param isOpen     The current open state of the door.
     */
    public void toggleDoor(Level level, BlockPos blockPos, BlockState blockState, boolean isOpen) {

        if (level.getBlockState(blockPos).hasProperty(OPEN) && level.getBlockState(blockPos).getValue(OPEN) != isOpen) {
            level.playSound(null, blockPos, !isOpen ? SoundEvents.PISTON_EXTEND : SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 1, 1);
        }
        level.setBlock(blockPos, blockState.setValue(OPEN, isOpen), Block.UPDATE_CLIENTS);
    }

    public InteractionResult onRightClick(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemInHand = player.getMainHandItem();

        if (itemInHand.getItem() == TRItemRegistry.PATTERN_MANIPULATOR.get()) {
            if (blockState.hasProperty(TYPE)) {
                BlockState nextType = blockState.cycle(TYPE);
                level.setBlock(blockPos, nextType, 3);
                level.playSound(player, blockPos, TRSoundRegistry.PATTERN_MANIPULATOR.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        if (itemInHand.getItem() == Items.NAME_TAG) {
            this.doorName = itemInHand.getDisplayName().getString();
            System.out.println("Set the name");
            sendUpdates();
        }


        return InteractionResult.SUCCESS;
    }

    public void sendUpdates() {
        level.sendBlockUpdated(this.getBlockPos(), level.getBlockState(this.getBlockPos()), level.getBlockState(this.getBlockPos()), Block.UPDATE_CLIENTS);
        setChanged();
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {

        if (doorName != null) {
            compoundTag.putString("bulkhead_door_name", doorName);
        }


        super.saveAdditional(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        doorName = compoundTag.getString("bulkhead_door_name");
        super.load(compoundTag);
    }

    public String getDoorName() {
        return doorName;
    }
}
