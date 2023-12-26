package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.tardis.ExteriorShell;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;

public abstract class ShellBaseBlockEntity extends BlockEntity implements ExteriorShell {

    protected ResourceKey<Level> TARDIS_ID;
    public AnimationState liveliness = new AnimationState();

    public ShellBaseBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
    @Override
    public ResourceKey<Level> getTardisId() {
        return this.TARDIS_ID;
    }
    @Override
    public void setTardisId(ResourceKey<Level> levelKey) {
        this.TARDIS_ID = levelKey;
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains(NbtConstants.TARDIS_ID))
            this.TARDIS_ID = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(pTag.getString(NbtConstants.TARDIS_ID)));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = this.saveWithFullMetadata();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        if (this.TARDIS_ID == null) {
            TardisRefined.LOGGER.error("Error in saveAdditional: null Tardis ID (could this be an invalid block?) [" + this.getBlockPos().toShortString() + "]");
            return;
        }

        super.saveAdditional(pTag);
        if (this.TARDIS_ID != null)
            pTag.putString(NbtConstants.TARDIS_ID, TARDIS_ID.location().toString());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean shouldSetup() {
        if (!this.level.isClientSide()) {
            return this.TARDIS_ID == null;
        }
        return false;
    }

    public void onAttemptEnter(BlockState blockState, Level level, BlockPos externalShellPos, Entity entity) {
        if (!entity.level().isClientSide() && level instanceof ServerLevel serverLevel) {
            if (this.TARDIS_ID == null) {
                TardisRefined.LOGGER.error("Error in onAttemptEnter: null Tardis ID (could this be an invalid block?) [" + externalShellPos.toShortString() + "]");
                return;
            }
            ServerLevel interior = DimensionHandler.getOrCreateInterior(serverLevel, this.TARDIS_ID.location());
            TardisLevelOperator.get(interior).ifPresent(cap -> {

                UpgradeHandler upgradeHandler = cap.getUpgradeHandler();

                if (cap.isTardisReady() && (blockState.getValue(ShellBaseBlock.OPEN) || (cap.getPilotingManager().endFlight() && Upgrades.MATERIALIZE_AROUND.get().isUnlocked(upgradeHandler)))) {
                    if (cap.getAestheticHandler().getShellTheme() != null) {
                        ResourceLocation theme = cap.getAestheticHandler().getShellTheme();

                        if (ModCompatChecker.immersivePortals()) {
                            if (ImmersivePortals.exteriorHasPortalSupport(theme)) {
                                return;
                            }
                        }
                    }
                    cap.enterTardis(entity, externalShellPos, serverLevel, blockState.getValue(ShellBaseBlock.FACING));
                } else {
                    if (!cap.isTardisReady()) {
                        if (entity instanceof Player player)
                            PlayerUtil.sendMessage(player, Component.translatable(ModMessages.MSG_EXTERIOR_COOLDOWN, cap.getInteriorManager().getInteriorGenerationCooldown()), true);
                    }
                }
            });
        }

    }

    @Override
    public BlockPos getExitPosition() {
        Direction direction = getBlockState().getValue(ShellBaseBlock.FACING);
        return this.getBlockPos().offset(direction.getOpposite().getNormal());
    }

    @Override
    public DesktopTheme getAssociatedTheme() {
        return TardisDesktops.FACTORY_THEME;
    }
}
