package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class BulkHeadDoorExtensionBlock extends BlockEntity {
    public BulkHeadDoorExtensionBlock( BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.BULK_HEAD_DOOR_EXT.get(), blockPos, blockState);
    }
}
