package whocraft.tardis_refined.common.block.desktop;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.desktop.InternalDoorBlockEntity;
import whocraft.tardis_refined.common.tardis.interior.exit.AbstractEntityBlockDoor;

public class InternalDoorBlock extends BaseEntityBlock {

    public InternalDoorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        AbstractEntityBlockDoor door = (AbstractEntityBlockDoor) level.getBlockEntity(blockPos);
        door.onBlockPlaced();
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        InternalDoorBlockEntity door = (InternalDoorBlockEntity) level.getBlockEntity(blockPos);
        door.onRightClick(blockState, level,blockPos,player,interactionHand,blockHitResult);

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new InternalDoorBlockEntity(blockPos, blockState);
    }
}
