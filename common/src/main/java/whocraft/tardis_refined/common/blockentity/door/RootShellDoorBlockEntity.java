package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

import java.util.Optional;

public class RootShellDoorBlockEntity extends InternalDoorBlockEntity {

    public RootShellDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.ROOT_SHELL_DOOR.get(), blockPos, blockState);
    }

    @Override
    public boolean isOpen() {
        return true;
    }
}
