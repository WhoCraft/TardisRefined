package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

import java.util.UUID;

public class ShellBaseBlockEntity extends BlockEntity {

    public ShellBaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.TARDIS_SHELL.get(), blockPos, blockState);
    }

    private String id = null;

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.id = pTag.getString(NBT_ID);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString(NBT_ID, id);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void onBlockInit() {
        if (this.id != null) {
            return;
        }
        // We need some information on our first creation of this block.
        this.id = UUID.randomUUID().toString();
        System.out.println("Recorded UUID " + this.id + " to a new shell block.");
    }

    public void onRightClick(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        // Fun times happen here.
        if (!level.isClientSide()) {
            // Load the dimension stuff here:
            DimensionHandler.getOrCreateInterior(level, this.id);
        }
    }

    public static final String NBT_ID = "tardis_id";

}
