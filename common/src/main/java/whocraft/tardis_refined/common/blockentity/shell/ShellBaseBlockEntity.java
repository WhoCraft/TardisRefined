package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.door.InternalDoorBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.registry.TRUpgrades;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;

import java.util.UUID;

public abstract class ShellBaseBlockEntity extends BlockEntity implements ExteriorShell, BlockEntityTicker<ShellBaseBlockEntity> {

    private final int DUPLICATION_CHECK_TIME = 1200; // A minute
    public AnimationState liveliness = new AnimationState();
    protected ResourceKey<Level> TARDIS_ID;
    /*
        Because we assume it is possible for the tardis to change location without flying there,
        the 'if (!myCurrentPosition.equals(currentLocation) && !myCurrentPosition.equals(wantedDestination))' block
        in the duplication check that runs once every so often would mistakenly remove this tardis if it just loaded
        in the same tick at a different location (if we are unlocky with tick order),
        because the operator's currentPosition will be set in the tick after.
        We therefore need a way to skip the duplication check for a single tick when loading in this blockentity.
     */
    private boolean doNotRemoveNextTick = false;

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
        updateCurrentLocation();
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        updateCurrentLocation();
    }

    private void updateCurrentLocation() {
        if (this.getLevel() instanceof ServerLevel serverLevel) {
            ServerLevel interior = serverLevel.getServer().getLevel(this.TARDIS_ID);
            TardisLevelOperator.get(interior).ifPresent(cap -> {
                cap.getPilotingManager().setCurrentLocationOnNextTick(this);
                this.doNotRemoveNextTick = true;
            });
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = this.saveWithFullMetadata();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
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

    @Override
    public void onAttemptEnter(BlockState blockState, Level level, BlockPos externalShellPos, Entity entity) {
        if (!entity.level().isClientSide() && level instanceof ServerLevel serverLevel) {
            if (this.TARDIS_ID == null) {
                TardisRefined.LOGGER.error("Error in onAttemptEnter: null Tardis ID (Invalid block or not terraformed yet?) [" + externalShellPos.toShortString() + "]");
                return;
            }
            ServerLevel interior = DimensionHandler.getOrCreateInterior(serverLevel, this.TARDIS_ID.location());
            TardisLevelOperator.get(interior).ifPresent(cap -> {

                UpgradeHandler upgradeHandler = cap.getUpgradeHandler();

                if (cap.isTardisReady() && (blockState.getValue(ShellBaseBlock.OPEN) || (cap.getPilotingManager().endFlight(false) && TRUpgrades.MATERIALIZE_AROUND.get().isUnlocked(upgradeHandler)))) {
                    if (cap.getAestheticHandler().getShellTheme() != null) {
                        ResourceLocation theme = cap.getAestheticHandler().getShellTheme();

                        if (ModCompatChecker.immersivePortals()) {
                            if (ImmersivePortals.isShellThemeSupported(theme) && ImmersivePortals.doPortalsExistForTardis(UUID.fromString(TARDIS_ID.location().getPath()))) {
                                return;
                            }
                        }
                    }
                    cap.enterTardis(entity, getBlockPos(), serverLevel, blockState.getValue(ShellBaseBlock.FACING));
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
    public DesktopTheme getAssociatedTheme() {
        return TardisDesktops.FACTORY_THEME;
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, ShellBaseBlockEntity blockEntity) {
        if (level.getGameTime() % DUPLICATION_CHECK_TIME == 0 && !level.isClientSide && !this.doNotRemoveNextTick) {
            ResourceKey<Level> tardisId = getTardisId();
            if (tardisId == null) return;
            ServerLevel tardisLevel = Platform.getServer().getLevel(tardisId);
            BlockPos myCurrentPosition = getBlockPos();

            TardisLevelOperator.get(tardisLevel).ifPresent(tardisLevelOperator -> {

                TardisPilotingManager pilotingManager = tardisLevelOperator.getPilotingManager();

                BlockPos currentLocation = pilotingManager.getCurrentLocation().getPosition();
                BlockPos wantedDestination = pilotingManager.getTargetLocation().getPosition();


                if (currentLocation == null) {
                    Direction direction = blockState.getValue(ShellBaseBlock.FACING);
                    ServerLevel serverLevel = Platform.getServer().getLevel(level.dimension());
                    pilotingManager.setCurrentLocation(new TardisNavLocation(getBlockPos(), direction != null ? direction : Direction.NORTH, serverLevel));
                }

                if (!myCurrentPosition.equals(currentLocation) && !myCurrentPosition.equals(wantedDestination)) {
                    level.removeBlock(myCurrentPosition, false);
                }

            });
        }
        this.doNotRemoveNextTick = false;
    }

    @Override
    public boolean isOpen() {
        return this.getBlockState().getValue(ShellBaseBlock.OPEN);
    }

    @Override
    public void setClosed(boolean closeDoor) {
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getLevel().getBlockState(blockPos);
        if (blockState.getBlock() instanceof ShellBaseBlock shellBaseBlock){
            this.getLevel().setBlock(blockPos, blockState.setValue(ShellBaseBlock.OPEN, !closeDoor), Block.UPDATE_ALL);
            this.playDoorCloseSound(closeDoor);
            this.setChanged();
        }
    }

    @Override
    public void onEntityExit(ServerEntity entity) {

    }

    @Override
    public void setLocked(boolean locked) {
        BlockState blockState = this.getLevel().getBlockState(this.getBlockPos());
        if (blockState.getBlock() instanceof ShellBaseBlock shellBaseBlock){
            this.getLevel().setBlock(this.getBlockPos(), blockState.setValue(ShellBaseBlock.LOCKED, locked), Block.UPDATE_ALL);
            this.playDoorLockedSound(locked);
            this.setChanged();
        }
    }

    @Override
    public boolean locked() {
        return this.getBlockState().getValue(ShellBaseBlock.LOCKED);
    }

    @Override
    public BlockPos getTeleportPosition() {
        Direction direction = getBlockState().getValue(ShellBaseBlock.FACING);
        return this.getBlockPos().offset(direction.getOpposite().getNormal());
    }

    @Override
    public Direction getRotation() {
        return this.getBlockState().getValue(ShellBaseBlock.FACING);
    }

    @Override
    public Direction getTeleportRotation() {
        return this.getBlockState().getValue(ShellBaseBlock.FACING).getOpposite();
    }

    @Override
    public BlockPos getDoorPosition() {
        return this.getBlockPos();
    }

    public void playDoorCloseSound(boolean closeDoor){
        Level currentLevel = getLevel();
        currentLevel.playSound(null, this.getBlockPos(), closeDoor ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1, closeDoor ? 1.4F : 1F);
        this.setChanged();
    }

    public void playDoorLockedSound(boolean lockDoor){
        Level currentLevel = getLevel();
        currentLevel.playSound(null, this.getBlockPos(), lockDoor ? BlockSetType.IRON.doorClose() : BlockSetType.IRON.doorOpen(), SoundSource.BLOCKS, 1, lockDoor ? 1.4F : 1F);
    }
}
