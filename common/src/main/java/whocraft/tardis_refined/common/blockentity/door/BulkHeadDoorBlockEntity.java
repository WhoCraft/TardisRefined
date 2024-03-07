package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.SoundRegistry;

import static whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock.LOCKED;
import static whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock.OPEN;

public class BulkHeadDoorBlockEntity extends BlockEntity implements BlockEntityTicker<BulkHeadDoorBlockEntity> {

    public BulkHeadDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.BULK_HEAD_DOOR.get(), blockPos, blockState);
    }


    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, BulkHeadDoorBlockEntity blockEntity) {

        if (blockState.getValue(LOCKED)) {
            return;
        }

        Player player = level.getNearestPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2.5f, false);
        toggleDoor(level, blockPos, blockState, player != null);

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

       /* if(blockState.getValue(LOCKED) && !blockState.getValue(OPEN)){
            level.playSound(null, blockPos, SoundRegistry.BULKHEAD_LOCKED.get(), SoundSource.BLOCKS, 1, 1);
            return;
        }*/

        if(level.getBlockState(blockPos).hasProperty(OPEN) && level.getBlockState(blockPos).getValue(OPEN) != isOpen) {
            level.playSound(null, blockPos, !isOpen ? SoundEvents.PISTON_EXTEND :  SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 1, 1);
        }
        level.setBlock(blockPos, blockState.setValue(OPEN, isOpen), Block.UPDATE_CLIENTS);
    }

}
