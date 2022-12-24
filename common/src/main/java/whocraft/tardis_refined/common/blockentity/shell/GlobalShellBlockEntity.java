package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

public class GlobalShellBlockEntity extends ShellBaseBlockEntity {

    public GlobalShellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_SHELL_BLOCK.get(), blockPos, blockState);
    }

    public void onRightClick(BlockState blockState) {
        if (getLevel() instanceof ServerLevel serverLevel) {
            ServerLevel interior = DimensionHandler.getExistingLevel(serverLevel, id.toString());
            if (interior != null) {
                TardisLevelOperator.get(interior).ifPresent(cap -> {
                    cap.setDoorClosed(blockState.getValue(GlobalShellBlock.OPEN));
                });
            }
        }
    }
}
