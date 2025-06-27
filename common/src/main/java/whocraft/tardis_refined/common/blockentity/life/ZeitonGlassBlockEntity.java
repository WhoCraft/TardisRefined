package whocraft.tardis_refined.common.blockentity.life;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class ZeitonGlassBlockEntity extends BlockEntity {
    public ZeitonGlassBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.ZEITON_GLASS.get(), blockPos, blockState);
    }
}