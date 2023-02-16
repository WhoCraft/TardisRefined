package whocraft.tardis_refined.common.block.life;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;

public class ArsEggBlock extends BaseEntityBlock {
    public ArsEggBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArsEggBlockEntity(blockPos, blockState);
    }

}
