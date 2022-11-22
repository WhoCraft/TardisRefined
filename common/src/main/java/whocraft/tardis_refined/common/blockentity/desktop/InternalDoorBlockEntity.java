package whocraft.tardis_refined.common.blockentity.desktop;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.blockentity.door.AbstractEntityBlockDoor;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

import java.util.Optional;

public class InternalDoorBlockEntity extends AbstractEntityBlockDoor {

    public InternalDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.TARDIS_INTERNAL_DOOR.get(), blockPos, blockState);
    }


    public void onRightClick(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (!level.isClientSide()) {
            Optional<TardisLevelOperator> operator = TardisLevelOperator.get((ServerLevel) level);
            operator.ifPresent(x -> {
                x.setInternalDoor(this);
                x.exitTardis(player);
            });
        }


    }

}
