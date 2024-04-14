package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

import static whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock.LOCKED;
import static whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock.OPEN;

public class BulkHeadDoorBlockEntity extends BlockEntity implements BlockEntityTicker<BulkHeadDoorBlockEntity> {

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
            if (entity instanceof LivingEntity) {
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

        if(level.getBlockState(blockPos).hasProperty(OPEN) && level.getBlockState(blockPos).getValue(OPEN) != isOpen) {
            level.playSound(null, blockPos, !isOpen ? SoundEvents.PISTON_EXTEND :  SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 1, 1);
        }
        level.setBlock(blockPos, blockState.setValue(OPEN, isOpen), Block.UPDATE_CLIENTS);
    }

}
