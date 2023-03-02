package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.tardis.ExteriorShell;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;

import java.util.UUID;

public abstract class ShellBaseBlockEntity extends BlockEntity implements ExteriorShell {

    public ShellBaseBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public UUID TARDIS_ID = null;
    public AnimationState liveliness = new AnimationState();

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.TARDIS_ID = UUID.fromString(pTag.getString(NbtConstants.TARDIS_ID));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString(NbtConstants.TARDIS_ID, TARDIS_ID.toString());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void onBlockInit() {
        if (this.TARDIS_ID != null) {
            return;
        }
        // We need some information on our first creation of this block.
        this.TARDIS_ID = UUID.randomUUID();
    }

    public void onAttemptEnter(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        if (level instanceof ServerLevel serverLevel) {
            ServerLevel interior = DimensionHandler.getOrCreateInterior(level, new ResourceLocation(TardisRefined.MODID, this.TARDIS_ID.toString()));
            TardisLevelOperator.get(interior).ifPresent(cap -> {
                if (cap.isTardisReady() && blockState.getValue(ShellBaseBlock.OPEN)) {
                    if(cap.getExteriorManager().getCurrentTheme() != null) {
                        ShellTheme theme = cap.getExteriorManager().getCurrentTheme();

                        if (ModCompatChecker.immersivePortals()) {
                            if (ImmersivePortals.exteriorHasPortalSupport(theme)) {
                                return;
                            }
                        }
                    }
                    cap.enterTardis(this, player, blockPos, serverLevel, blockState.getValue(ShellBaseBlock.FACING));
                } else {
                    if (!cap.isTardisReady()) {
                        PlayerUtil.sendMessage(player, Component.translatable(ModMessages.MSG_EXTERIOR_COOLDOWN, cap.getInteriorManager().getInteriorGenerationCooldown()), true);
                    }
                }
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

    @Override
    public DesktopTheme getAssociatedTheme() {
        return TardisDesktops.FACTORY_THEME;
    }
}
