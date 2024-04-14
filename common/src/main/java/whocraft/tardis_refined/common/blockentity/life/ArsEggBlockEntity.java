package whocraft.tardis_refined.common.blockentity.life;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class ArsEggBlockEntity extends BlockEntity {

    public ArsEggBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.ARS_EGG.get(), blockPos, blockState);
    }

    private AnimationState liveliness = new AnimationState();

    public AnimationState getLiveliness() {
        return liveliness;
    }
}
