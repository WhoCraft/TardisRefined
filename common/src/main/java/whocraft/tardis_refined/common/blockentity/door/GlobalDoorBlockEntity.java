package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.DimensionTypes;

import java.util.Optional;

public class GlobalDoorBlockEntity extends AbstractEntityBlockDoor {

    public GlobalDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_DOOR_BLOCK.get(), blockPos, blockState);
    }


    public void onRightClick(BlockState blockState, TardisInternalDoor door, Player player) {
        if (getLevel() instanceof ServerLevel serverLevel) {

            // we know that in this instance the serverlevel has a capability.
            TardisLevelOperator.get(serverLevel).ifPresent(cap -> {
                if (cap.getInternalDoor() != door) {
                    cap.setInternalDoor(door);
                }
                if(player.isCrouching() && !cap.getControlManager().isInFlight()) {
                    cap.getExteriorManager().setLocked(!door.locked());
                    door.setLocked(!door.locked());
                    cap.setDoorClosed(true);
                    return;
                }
                if (!cap.getControlManager().isInFlight() && !door.locked() && serverLevel.dimensionTypeId().equals(DimensionTypes.TARDIS)) {
                    cap.setDoorClosed(blockState.getValue(GlobalDoorBlock.OPEN));
                }
            });
        }
    }

    public void onAttemptEnter(Level level, Player player) {
        if (!level.isClientSide()) {
            Optional<TardisLevelOperator> operator = TardisLevelOperator.get((ServerLevel) level);
            operator.ifPresent(x -> {
                x.setInternalDoor(this);
                x.exitTardis(player);
            });
        }
    }
}
