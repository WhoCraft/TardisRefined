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


    @Override
    public boolean isOpen() {
        return true; //This needs to be always true so that it matches the visual look of the door having a "hole" to teleport the player through
    }

    @Override
    public void playDoorCloseSound(boolean closeDoor) {
        //Leave blank
    }

    @Override
    public void playDoorLockedSound(boolean lockDoor) {
        //Leave blank
    }
}
