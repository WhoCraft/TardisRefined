package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.tardis.manager.AestheticHandler;
import whocraft.tardis_refined.common.tardis.manager.TardisExteriorManager;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.patterns.sound.ConfiguredSound;
import whocraft.tardis_refined.patterns.sound.TRShellSoundProfiles;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

import java.util.Optional;
import com.geckolib.animatable.GeoBlockEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;

public class GlobalShellBlockEntity extends ShellBaseBlockEntity implements GeoBlockEntity {

    private final AnimatableInstanceCache geckoCache = GeckoLibUtil.createInstanceCache(this);

    @Override public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}
    @Override public AnimatableInstanceCache getAnimatableInstanceCache() { return geckoCache; }

    private Identifier shellTheme;
    private ShellPattern basePattern;

    public GlobalShellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.GLOBAL_SHELL_BLOCK.get(), blockPos, blockState);
        this.shellTheme = ShellTheme.HALF_BAKED.getId();
        this.basePattern = this.pattern();
    }

    public Identifier theme() {
        if (this.shellTheme == null) {
            this.shellTheme = ShellTheme.HALF_BAKED.getId();
        }
        return this.shellTheme;
    }

    public Identifier getShellTheme() {
        return shellTheme;
    }

    public void setShellTheme(Identifier theme) {
        this.shellTheme = theme;
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    /** Updates appearance on a client-only preview shell without world updates. */
    public void setPreviewAppearance(Identifier theme, ShellPattern pattern) {
        this.shellTheme = theme;
        this.basePattern = pattern;
    }

    public ShellPattern pattern() {
        return this.basePattern == null ? ShellPatterns.DEFAULT : this.basePattern;
    }

    public GlobalShellBlockEntity setPattern(ShellPattern basePattern) {
        this.basePattern = basePattern;
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        return this;
    }

    @Override
    protected void loadAdditional(ValueInput pTag) {
        super.loadAdditional(pTag);

        if (pTag.getString(NbtConstants.THEME).isPresent()) {
            Identifier themeId = Identifier.parse(pTag.getStringOr(NbtConstants.THEME, ""));
            this.shellTheme = themeId;
        }

        if (pTag.getString(NbtConstants.PATTERN).isPresent()) {
            if (this.shellTheme != null) {
                Identifier currentPattern = Identifier.parse(pTag.getStringOr(NbtConstants.PATTERN, ""));
                if (ShellPatterns.doesPatternExist(this.shellTheme, currentPattern)) {
                    this.basePattern = ShellPatterns.getPatternOrDefault(this.shellTheme, currentPattern);
                }
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
    protected void saveAdditional(ValueOutput pTag) {
        super.saveAdditional(pTag);
        if (this.shellTheme != null) {
            pTag.putString(NbtConstants.THEME, this.shellTheme.toString());
        }
        if (this.basePattern != null) {
            pTag.putString(NbtConstants.PATTERN, this.basePattern.id().toString());
        }
    }

    public boolean onRightClick(BlockState blockState, ItemStack stack, Level level, BlockPos blockPos, Player player) {

        // We do not want interactions if the TARDIS is regenerating itself
        if (blockState.getValue(ShellBaseBlock.REGEN)) {
            return false;
        }

        if (level instanceof ServerLevel serverLevel) {
            ServerLevel interior = DimensionHandler.getExistingLevel(serverLevel, this.TARDIS_ID);
            Optional<TardisLevelOperator> tardisLevelOperatorOptional = TardisLevelOperator.get(interior);

            if (tardisLevelOperatorOptional.isPresent()) {
                TardisLevelOperator tardisLevelOperator = tardisLevelOperatorOptional.get();
                TardisPilotingManager tardisPilotingManager = tardisLevelOperator.getPilotingManager();
                AestheticHandler aestheticHandler = tardisLevelOperator.getAestheticHandler();
                TardisExteriorManager exteriorManager = tardisLevelOperator.getExteriorManager();

                // We do not want interactions if the Tardis is still taking off or landing
                if (tardisPilotingManager.isInFlight()) return false;

                // Shearing the TARDIS
                if (stack.is(Items.SHEARS) && ShellTheme.HALF_BAKED.getId().equals(aestheticHandler.getShellTheme())) {
                    // Use the unified update path so the shell block, interior door,
                    // persisted aesthetic, and client render state all switch together.
                    tardisLevelOperator.setShellTheme(ShellTheme.FACTORY.getId(), ShellPatterns.DEFAULT.id(),
                            whocraft.tardis_refined.api.event.ShellChangeSources.GENERIC_UPDATE);
                    level.playSound(null, blockPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1, 1);
                    spawnCoralItems();
                    return true;
                }

                boolean validKey = KeyItem.keychainContains(stack, TARDIS_ID);
                boolean locked = exteriorManager.locked();
                if (validKey) {
                    tardisLevelOperator.setDoorLocked(!locked);
                    tardisLevelOperator.setDoorClosed(!locked);
                    PlayerUtil.sendMessage(player, Component.translatable(exteriorManager.locked() ? ModMessages.DOOR_LOCKED : ModMessages.DOOR_UNLOCKED), true);
                    return true;
                } else {
                    if(locked) {
                        PlayerUtil.sendMessage(player, Component.translatable(ModMessages.DOOR_LOCKED), true);
                        return true;
                    }
                }

                if (!exteriorManager.locked()) { //If the Tardis thinks it is not locked, open this shell's door
                    level.setBlock(blockPos, blockState.cycle(GlobalShellBlock.OPEN), Block.UPDATE_ALL); //Cycle the door to open/closed
                    tardisLevelOperator.setDoorClosed(blockState.getValue(GlobalShellBlock.OPEN)); //Now update both the internal door and re-update the external shell for good measure too.
                    return true;
                }
            }
        }
        return false;
    }

    public void sendUpdates() {
        level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), Block.UPDATE_ALL);
        setChanged();
    }

    public void spawnCoralItems() {
        int numberOfItems = 2 + level.getRandom().nextInt(5);

        for (int i = 0; i < numberOfItems; i++) {
            ItemStack coralItem = new ItemStack(Items.HORN_CORAL_FAN);
            BlockPos currentPos = getBlockPos();
            Containers.dropItemStack(level, currentPos.getX(), currentPos.getY(), currentPos.getZ(), coralItem);
        }
    }

    @Override
    public void playDoorCloseSound(boolean closeDoor) {
        ShellPattern pattern = this.pattern();
        if (pattern != null) {
            Level currentLevel = this.getLevel();
            ConfiguredSound configuredSound;
            if (closeDoor) {
                configuredSound = pattern.soundProfile().get() != null ? pattern.soundProfile().get().getDoorClose() : TRShellSoundProfiles.DEFAULT_SOUND_PROFILE.getDoorClose();
            } else {
                configuredSound = pattern.soundProfile().get() != null ? pattern.soundProfile().get().getDoorOpen() : TRShellSoundProfiles.DEFAULT_SOUND_PROFILE.getDoorOpen();
            }
            if (configuredSound != null) {
                currentLevel.playSound(null, this.getBlockPos(), configuredSound.getSoundEvent(), SoundSource.BLOCKS, configuredSound.getPitch(), configuredSound.getVolume());
            }
        }
    }

    @Override
    public void playDoorLockedSound(boolean lockDoor) {
        ShellPattern pattern = this.pattern();
        if (pattern != null) {
            Level currentLevel = this.getLevel();
            ConfiguredSound configuredSound;
            if (lockDoor) {
                configuredSound = pattern.soundProfile().get() != null ? pattern.soundProfile().get().getDoorLocked() : TRShellSoundProfiles.DEFAULT_SOUND_PROFILE.getDoorLocked();
            } else {
                configuredSound = pattern.soundProfile().get() != null ? pattern.soundProfile().get().getDoorUnlocked() : TRShellSoundProfiles.DEFAULT_SOUND_PROFILE.getDoorUnlocked();
            }
            if (configuredSound != null) {
                currentLevel.playSound(null, this.getBlockPos(), configuredSound.getSoundEvent(), SoundSource.BLOCKS, configuredSound.getPitch(), configuredSound.getVolume());
            }
        }
    }


}
