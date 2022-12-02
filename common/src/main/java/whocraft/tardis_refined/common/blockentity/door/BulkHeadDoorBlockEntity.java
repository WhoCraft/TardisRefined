package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.BlockRegistry;

public class BulkHeadDoorBlockEntity extends BlockEntity implements BlockEntityTicker<BulkHeadDoorBlockEntity> {

    public BulkHeadDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.BULK_HEAD_DOOR.get(), blockPos, blockState);
    }


    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, BulkHeadDoorBlockEntity blockEntity) {
        System.out.println("PING");
        Player player = level.getNearestPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2.5f, false);
        System.out.println(player);
        if (player != null) {
            if (!blockState.getValue(BulkHeadDoorBlock.OPEN)) {
                level.playSound(null, blockPos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 1, 1);
                level.setBlock(blockPos, blockState.setValue(BulkHeadDoorBlock.OPEN, true), 2);
            }
        } else {
            if (blockState.getValue(BulkHeadDoorBlock.OPEN)) {
                level.playSound(null, blockPos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 1, 1);
                level.setBlock(blockPos, blockState.setValue(BulkHeadDoorBlock.OPEN, false), 2);
            }
        }
    }
}
