package whocraft.tardis_refined.common.blockentity.life;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

public class ArsEggBlockEntity extends BlockEntity {

    public ArsEggBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.ARS_EGG.get(), blockPos, blockState);
    }
}
