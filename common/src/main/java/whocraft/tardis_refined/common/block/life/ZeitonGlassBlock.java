package whocraft.tardis_refined.common.block.life;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.client.ZeitonGlassTracker;
import whocraft.tardis_refined.common.blockentity.life.ZeitonGlassBlockEntity;

public class ZeitonGlassBlock extends BaseEntityBlock {

    public ZeitonGlassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any());
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);

        if (level.getBlockEntity(blockPos) instanceof ZeitonGlassBlockEntity zeitonGlassBlockEntity) {
            if (ZeitonGlassTracker.loadedGlass.contains(zeitonGlassBlockEntity)) {
                ZeitonGlassTracker.onUnload(zeitonGlassBlockEntity);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ZeitonGlassBlockEntity(blockPos, blockState);
    }
}
