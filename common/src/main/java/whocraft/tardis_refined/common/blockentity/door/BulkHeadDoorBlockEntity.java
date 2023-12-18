package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
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

public class BulkHeadDoorBlockEntity extends BlockEntity implements BlockEntityTicker<BulkHeadDoorBlockEntity> {

    public BulkHeadDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.BULK_HEAD_DOOR.get(), blockPos, blockState);
    }


    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, BulkHeadDoorBlockEntity blockEntity) {
        if (!blockState.getValue(BulkHeadDoorBlock.LOCKED) && blockState.canSurvive(level, blockPos)) {
            Player player = level.getNearestPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2.5f, false);
            if (player != null) {
                toggleDoor(level, blockPos, blockState, blockState.getValue(BulkHeadDoorBlock.OPEN));
            }
        }
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
        level.playSound(null, blockPos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 1, 1);
        level.setBlock(blockPos, blockState.setValue(BulkHeadDoorBlock.OPEN, isOpen), Block.UPDATE_CLIENTS);
    }

}
