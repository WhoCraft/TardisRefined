package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class RootPlantBlockEntity extends BlockEntity {

    public RootPlantBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.ROOT_PLANT.get(), blockPos, blockState);
    }


}
