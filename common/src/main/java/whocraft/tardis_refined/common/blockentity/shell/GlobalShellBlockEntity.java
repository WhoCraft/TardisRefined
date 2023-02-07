package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

public class GlobalShellBlockEntity extends ShellBaseBlockEntity {

    public GlobalShellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_SHELL_BLOCK.get(), blockPos, blockState);
    }

    public void onRightClick(BlockState blockState, ItemStack stack) {

        if (blockState.getValue(ShellBaseBlock.REGEN)) {return;}

        if (getLevel() instanceof ServerLevel serverLevel) {
            ServerLevel interior = DimensionHandler.getExistingLevel(serverLevel, TARDIS_ID.toString());
            if (interior != null) {
                TardisLevelOperator.get(interior).ifPresent(cap -> {
                    if (cap.getControlManager().isInFlight()) return;
                    ResourceKey<Level> dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(TardisRefined.MODID, TARDIS_ID.toString()));

                    boolean validKey = KeyItem.keychainContains(stack, dimension);
                    if(validKey) {
                        boolean locked = !cap.getExteriorManager().locked();
                        cap.getExteriorManager().setLocked(locked);
                        cap.getInternalDoor().setLocked(locked);
                        cap.setDoorClosed(true);
                        return;
                    }
                    if(cap.getExteriorManager().locked()) return;
                    cap.setDoorClosed(blockState.getValue(GlobalShellBlock.OPEN));
                });
            }
        }
    }
}
