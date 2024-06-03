package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class RootShellDoorBlockEntity extends InternalDoorBlockEntity {

    public RootShellDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.ROOT_SHELL_DOOR.get(), blockPos, blockState);
    }

    @Override
    public void onBlockPlaced() {
        // Left blank to remove parent functionality.
    }

}
