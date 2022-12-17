package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

public class ConsoleConfigurationBlockEntity extends BlockEntity {

    public ConsoleConfigurationBlockEntity( BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.CONSOLE_CONFIGURATION.get(), blockPos, blockState);
    }
}
