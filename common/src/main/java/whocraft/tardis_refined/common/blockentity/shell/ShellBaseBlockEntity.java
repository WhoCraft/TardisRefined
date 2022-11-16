package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import whocraft.tardis_refined.NbtConstants;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.tardis.interior.shell.IExteriorShell;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

import java.util.UUID;

public class ShellBaseBlockEntity extends BlockEntity implements IExteriorShell {

    public ShellBaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.TARDIS_SHELL.get(), blockPos, blockState);
    }

    private UUID id = null;

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.id = pTag.getUUID(NbtConstants.TARDIS_ID);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putUUID(NbtConstants.TARDIS_ID, id);
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
        this.id = UUID.randomUUID();
    }

    public void onRightClick(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {

            ServerLevel interior = DimensionHandler.getOrCreateInterior(level, this.id.toString());

            TardisLevelOperator.get(interior).ifPresent(cap -> {
                 cap.enterTardis(player, blockPos, level, blockState.getValue(ShellBaseBlock.FACING));
            });

        }
    }

    @Override
    public BlockPos getExitPosition() {
        int direction = getBlockState().getValue(ShellBaseBlock.FACING).get2DDataValue();
        switch (direction) {
            case 3:
                return new BlockPos(getBlockPos().getX()-1, getBlockPos().getY(), getBlockPos().getZ() );
            case 2:
                return new BlockPos(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ() +1);
            case 1:
                return new BlockPos(getBlockPos().getX()+1, getBlockPos().getY(), getBlockPos().getZ());
            case 0:
                return new BlockPos(getBlockPos().getX() , getBlockPos().getY(), getBlockPos().getZ()-1);
        }

        return getBlockPos().above();
    }
}
