package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.shell.RedirectBlock;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.patterns.sound.ConfiguredSound;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

import java.util.Set;

public class GlobalDoorBlockEntity extends InternalDoorBlockEntity implements BlockEntityTicker<InternalDoorBlockEntity> {

    private ResourceLocation shellTheme = ShellTheme.HALF_BAKED.getId();
    private ShellPattern basePattern;
    private Set<RedirectBlock.Entry> redirectBlocks = Set.of();
    private boolean refreshRedirectsOnLevel = false;

    public GlobalDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.GLOBAL_DOOR_BLOCK.get(), blockPos, blockState);
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        if (refreshRedirectsOnLevel) {
            redirectBlocks = RedirectBlock.Entry.getRedirectsForShape(GlobalDoorBlock.getShapeFromTheme(shellTheme, getBlockState()));
            refreshRedirectsOnLevel = false;
        } else {
            refreshRedirectsOnLevel = true;
        }
    }

    private void onThemeUpdated() {
        if (hasLevel()) {
            // Usually these blocks will remove themselves by the block update, but shell-theme changes don't trigger a block update.
            RedirectBlock.Entry.remove(level, worldPosition, redirectBlocks);
            redirectBlocks = RedirectBlock.Entry.getRedirectsForShape(GlobalDoorBlock.getShapeFromTheme(shellTheme, getBlockState()));
            RedirectBlock.Entry.place(level, worldPosition, redirectBlocks);
        } else {
            refreshRedirectsOnLevel = true;
        }
    }

    @Override
    public void setBlockState(@NotNull BlockState blockState) {
        super.setBlockState(blockState);
        onThemeUpdated();
    }

    public boolean isValidRedirectBlock(BlockPos pos, BlockState state) {
        return RedirectBlock.Entry.isValidRedirect(pos, state, worldPosition, redirectBlocks);
    }

    public ShellPattern pattern() {
        ShellPattern defaultBasePattern = ShellPatterns.DEFAULT;
        return this.basePattern == null ? defaultBasePattern : this.basePattern;
    }

    public GlobalDoorBlockEntity setPattern(ShellPattern basePattern) {
        this.basePattern = basePattern;
        sendUpdates();
        return this;
    }

    public void sendUpdates() {
        level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), Block.UPDATE_ALL);
        setChanged();
    }

    public ResourceLocation theme() {
        if (this.shellTheme == null) {
            this.shellTheme = ShellTheme.HALF_BAKED.getId();
        }
        return this.shellTheme;
    }

    public void setShellTheme(ResourceLocation shellTheme) {
        this.shellTheme = shellTheme;
        this.setChanged();
        onThemeUpdated();
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag = new CompoundTag();
        saveAdditional(compoundTag);
        return compoundTag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains(NbtConstants.THEME)) {
            ResourceLocation themeId = new ResourceLocation(pTag.getString(NbtConstants.THEME));
            this.shellTheme = themeId;
            onThemeUpdated();
        }

        if (pTag.contains(NbtConstants.PATTERN)) {
            ResourceLocation currentPattern = new ResourceLocation(pTag.getString(NbtConstants.PATTERN));
            if (ShellPatterns.doesPatternExist(this.shellTheme, currentPattern)) {
                this.basePattern = ShellPatterns.getPatternOrDefault(this.shellTheme, currentPattern);
            }
        }

        if (this.shellTheme == null) {
            this.shellTheme = this.theme();
        }

        if (this.basePattern == null) {
            this.basePattern = pattern();
        }
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (this.shellTheme != null) {
            pTag.putString(NbtConstants.THEME, this.shellTheme.toString());
        }
        if (this.basePattern != null) {
            pTag.putString(NbtConstants.PATTERN, this.basePattern.id().toString());
        }
    }


    public void onRightClick(BlockState blockState, TardisInternalDoor door, Player player) {
        if (getLevel() instanceof ServerLevel serverLevel) {

            // we know that in this instance the serverlevel has a capability.
            TardisLevelOperator.get(serverLevel).ifPresent(cap -> {
                if (cap.getInternalDoor() != door) {
                    cap.setInternalDoor(door); //Set the main door and also tell this door block that it is the main door.
                }
                if (player.isShiftKeyDown() && !cap.getPilotingManager().isInFlight()) {
                    /*When multiple internal doors are in a Tardis, and the player is locking a different door, use the door block's data to update the Tardis' data */
                    cap.setDoorLocked(!door.locked()); //Tell the Tardis that the door is locked
                    if (door.locked())
                        cap.setDoorClosed(true); //Tell the Tardis that the door should be closed only if the door is being locked
                    return;
                }
                if (!door.locked()) {
                    cap.setDoorClosed(door.isOpen());//Tell the Tardis that the door should be closed if currently open, and should be open if currently closed.
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (door.isOpen()) {
                            //  IPStencil.loadServerChunks(serverPlayer, cap.getPilotingManager().getCurrentLocation());
                        } else {
                            //  IPStencil.unloadServerChunks(serverPlayer);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void playDoorCloseSound(boolean closeDoor) {
        ShellPattern pattern = this.pattern();
        if (pattern != null) {
            Level currentLevel = this.getLevel();

            pattern.soundProfile().ifPresent(shellSoundProfile -> {
                ConfiguredSound configuredSound = shellSoundProfile.getDoorClose();
                if (configuredSound != null) {
                    currentLevel.playSound(null, this.getBlockPos(), configuredSound.getSoundEvent(), SoundSource.BLOCKS, configuredSound.getPitch(), configuredSound.getVolume());
                }
            });
        }
    }

    @Override
    public void playDoorLockedSound(boolean lockDoor) {
        ShellPattern pattern = this.pattern();
        if (pattern != null) {
            Level currentLevel = this.getLevel();
            pattern.soundProfile().ifPresent(shellSoundProfile -> {
                ConfiguredSound configuredSound = shellSoundProfile.getDoorClose();
                if (configuredSound != null) {
                    currentLevel.playSound(null, this.getBlockPos(), configuredSound.getSoundEvent(), SoundSource.BLOCKS, configuredSound.getPitch(), configuredSound.getVolume());
                }
            });
        }
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, InternalDoorBlockEntity blockEntity) {
        //noinspection ConstantValue IntelliJ got confused by this for some reason...
        if (
                !level.isClientSide() && level instanceof ServerLevel sl &&
                ModCompatChecker.valkyrienSkies() && VSHelper.isBlockInShipyard(level, blockPos) &&
                ModCompatChecker.immersivePortals() && ImmersivePortals.doPortalsExistForTardis(level.dimension())
        ) {
            TardisLevelOperator.get(sl).ifPresent(ImmersivePortals::updatePortalPositions);
        }
  /*      if (level instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
                if (blockEntity.isOpen() && tardisLevelOperator.getPilotingManager().isInFlight()) {
                    int throttleStage = tardisLevelOperator.getPilotingManager().getThrottleStage();
                    if (throttleStage > 3) {
                        double pullRadius = 10.0 * throttleStage;
                        List<LivingEntity> wolves = serverLevel.getEntitiesOfClass(
                                LivingEntity.class,
                                new AABB(blockPos).inflate(pullRadius)
                        );

                        for (LivingEntity wolf : wolves) {
                            double dx = blockPos.getX() + 0.5 - wolf.getX();
                            double dy = blockPos.getY() + 0.5 - wolf.getY();
                            double dz = blockPos.getZ() + 0.5 - wolf.getZ();

                            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                            if (distance > 0) {
                                double pullStrength = throttleStage;
                                wolf.setDeltaMovement(
                                        wolf.getDeltaMovement().add(
                                                dx / distance * pullStrength,
                                                dy / distance * pullStrength,
                                                dz / distance * pullStrength
                                        )
                                );
                            }
                        }
                    }
                }
            });
        }*/
    }

}
