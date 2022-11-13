package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.shell.ShellBaseBlockEntity;
import whocraft.tardis_refined.common.dimension.DimensionHandler;

public abstract class ShellBaseBlock extends BaseEntityBlock {

    public ShellBaseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ShellBaseBlockEntity entity = (ShellBaseBlockEntity) level.getBlockEntity(blockPos);
        if (entity != null) {
            entity.onRightClick(blockState,level,blockPos,player,interactionHand,blockHitResult);
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        ShellBaseBlockEntity entity = (ShellBaseBlockEntity) level.getBlockEntity(blockPos);
        if (entity != null) {
            entity.onBlockInit();
        }

    }
}
