package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

public class EngineInterfaceBlockEntity extends BlockEntity {

    private ItemStack component = ItemStack.EMPTY;

    public EngineInterfaceBlockEntity( BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.ENGINE_INTERFACE.get(), blockPos, blockState);
    }

    public ItemStack getComponent() {
        return component;
    }

    public void dropComponentIfPresent(Player player) {
        ItemStack localComponenet = getComponent();
        if (!localComponenet.isEmpty()) {
            if (player != null) {
                if (!player.addItem(localComponenet)) {
                    Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(), localComponenet);
                    clearContent();
                }
            } else {
                Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(), localComponenet);
                clearContent();
            }
        }
        sendUpdates();
    }

    public void clearContent() {
        this.setComponent(ItemStack.EMPTY);
    }

    public void setComponent(ItemStack component) {
        this.component = component;
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag.contains("Upgrade", 10)) {
            this.setComponent(ItemStack.of(compoundTag.getCompound("Upgrade")));
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag compoundTag = new CompoundTag();
        saveAdditional(compoundTag);
        return compoundTag;
    }

    public void sendUpdates() {
        level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 3);
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.put("Upgrade", this.getComponent().save(new CompoundTag()));

    }
}
