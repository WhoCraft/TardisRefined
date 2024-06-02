package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class FlightDetectorBlockEntity extends BlockEntity {

    public FlightDetectorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.FLIGHT_DETECTOR.get(), blockPos, blockState);
    }
}
