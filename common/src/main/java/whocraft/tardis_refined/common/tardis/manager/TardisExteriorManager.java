package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.ExteriorShell;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.BlockRegistry;

import java.util.UUID;

import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.LOCKED;
import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.REGEN;

/**
 * External Shell data.
 **/
public class TardisExteriorManager {

    private final TardisLevelOperator operator;
    private TardisNavLocation lastKnownLocation = TardisNavLocation.ORIGIN;
    private ShellTheme currentTheme;

    private ShellPattern shellPattern = null;

    public boolean locked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        if (operator.getPilotingManager().isInFlight()) {
            return;
        }
        this.locked = locked;
    }

    private boolean locked;
    private boolean isLanding;

    public boolean isLanding() {
        return this.isLanding;
    }

    private boolean isTakingOff;

    public boolean isTakingOff() {
        return this.isTakingOff;
    }

    public void setIsTakingOff(boolean isTakingOff) {
        this.isTakingOff = isTakingOff;
    }


    public TardisExteriorManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void setLastKnownLocation(TardisNavLocation lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public TardisNavLocation getLastKnownLocation() {

        if(lastKnownLocation == null){
            return TardisNavLocation.ORIGIN;
        }

        return this.lastKnownLocation;
    }

    public ShellTheme getCurrentTheme() {
        return this.currentTheme;
    }

    public ServerLevel getLevel() {
        return this.getLastKnownLocation().getLevel();
    }

    public ShellPattern shellPattern() {
        return shellPattern;
    }

    public TardisExteriorManager setShellPattern(ShellPattern shellPattern) {
        this.shellPattern = shellPattern;
        return this;
    }

    public CompoundTag saveData(CompoundTag tag) {

        if (this.lastKnownLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "lk_ext", this.lastKnownLocation);
        }
        if (this.currentTheme != null) {
            tag.putString(NbtConstants.TARDIS_EXT_CURRENT_THEME, this.currentTheme.getSerializedName());
        }

        if (this.shellPattern != null) {
            tag.putString(NbtConstants.TARDIS_EXT_CURRENT_PATTERN, shellPattern.id().toString());
        }

        tag.putBoolean(NbtConstants.LOCKED, locked);

        return tag;
    }

    public void loadData(CompoundTag tag) {

        this.lastKnownLocation = NbtConstants.getTardisNavLocation(tag, "lk_ext", operator);

        if (tag.contains(NbtConstants.TARDIS_EXT_CURRENT_THEME) && tag.getString(NbtConstants.TARDIS_EXT_CURRENT_THEME) != null) {
            this.currentTheme = ShellTheme.findOr(tag.getString(NbtConstants.TARDIS_EXT_CURRENT_THEME), ShellTheme.FACTORY);
        }

        if (tag.contains(NbtConstants.TARDIS_EXT_CURRENT_PATTERN) && tag.getString(NbtConstants.TARDIS_EXT_CURRENT_PATTERN) != null) {
            this.shellPattern = ShellPatterns.getPatternOrDefault(currentTheme, new ResourceLocation(tag.getString(NbtConstants.TARDIS_EXT_CURRENT_PATTERN)));
        }

        locked = tag.getBoolean(NbtConstants.LOCKED);


    }

    public void playSoundAtShell(SoundEvent event, SoundSource source, float volume, float pitch) {
        if (lastKnownLocation != null) {
            ServerLevel lastKnownLocationLevel = lastKnownLocation.getLevel();
            lastKnownLocationLevel.playSound(null, lastKnownLocation.getPosition(), event, source, volume, pitch);
        }
    }

    public void setDoorClosed(boolean closed) {

        if (locked) {
            closed = true;
        }

        if(lastKnownLocation == null) return;
        ServerLevel lastKnownLocationLevel = lastKnownLocation.getLevel();

        // Get the exterior block.
        BlockState state = lastKnownLocationLevel.getBlockState(lastKnownLocation.getPosition());
        if (state.hasProperty(ShellBaseBlock.OPEN)) {
            lastKnownLocationLevel.setBlock(lastKnownLocation.getPosition(), state.setValue(ShellBaseBlock.OPEN, !closed), 2);
            playSoundAtShell(locked ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1, locked ? 1.4F : 1F);
        }
    }

    public void setShellTheme(ShellTheme theme) {

        if(lastKnownLocation == null) return;

        BlockPos lastKnownLocationPosition = lastKnownLocation.getPosition();
        ServerLevel lastKnownLocationLevel = lastKnownLocation.getLevel();
        BlockState state = lastKnownLocationLevel.getBlockState(lastKnownLocationPosition);

        // Check if its our default global shell.
        if (state.getBlock() instanceof GlobalShellBlock) {
            lastKnownLocationLevel.setBlock(lastKnownLocationPosition, state.setValue(GlobalShellBlock.SHELL, theme).setValue(GlobalShellBlock.REGEN, false), 2);
        } else {
            if (state.getBlock() instanceof RootedShellBlock) {
                lastKnownLocationLevel.setBlock(lastKnownLocationPosition,
                        BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.OPEN, state.getValue(RootedShellBlock.OPEN)).setValue(GlobalShellBlock.FACING, state.getValue(RootedShellBlock.FACING)).setValue(GlobalShellBlock.SHELL, theme).setValue(GlobalShellBlock.REGEN, false), 2);

                var shellBlockEntity = lastKnownLocationLevel.getBlockEntity(lastKnownLocationPosition);
                if (shellBlockEntity instanceof GlobalShellBlockEntity entity) {
                    entity.TARDIS_ID = UUID.fromString((operator.getLevel().dimension().location().getPath()));
                    if(shellPattern != null) {
                        entity.setPattern(ShellPatterns.getThemeForPattern(this.shellPattern) != theme ? shellPattern : ShellPatterns.getPatternsForTheme(theme).get(0));
                        entity.setChanged();
                    }
                }
            }
        }

        this.currentTheme = theme;
    }

    public void triggerShellRegenState() {
        if(lastKnownLocation == null) return;
        BlockPos lastKnownLocationPosition = lastKnownLocation.getPosition();
        ServerLevel lastKnownLocationLevel = lastKnownLocation.getLevel();

        BlockState state = lastKnownLocationLevel.getBlockState(lastKnownLocationPosition);
        if (lastKnownLocationLevel == null) return;
        if (!lastKnownLocationLevel.getBlockState(lastKnownLocationPosition).hasProperty(REGEN)) return;
        lastKnownLocationLevel.setBlock(lastKnownLocationPosition, state.setValue(ShellBaseBlock.REGEN, true), Block.UPDATE_ALL);
    }

    public void removeExteriorBlock() {
        this.isLanding = false;
        if (lastKnownLocation != null) {
            BlockPos lastKnownLocationPosition = lastKnownLocation.getPosition();
            ServerLevel lastKnownLocationLevel = lastKnownLocation.getLevel();
            if (lastKnownLocationLevel.getBlockState(lastKnownLocationPosition).getBlock() instanceof GlobalShellBlock shellBlock) {
                lastKnownLocationLevel.setBlockAndUpdate(lastKnownLocationPosition, Blocks.AIR.defaultBlockState());
            }
        }
    }

    public void placeExteriorBlock(TardisLevelOperator operator, TardisNavLocation location) {
        ShellTheme theme = (this.currentTheme != null) ? this.currentTheme : ShellTheme.FACTORY;
        BlockState targetBlockState = BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState()
                .setValue(GlobalShellBlock.SHELL, theme)
                .setValue(GlobalShellBlock.FACING, location.getDirection().getOpposite())
                .setValue(GlobalShellBlock.REGEN, false)
                .setValue(LOCKED, operator.getExteriorManager().locked)
                .setValue(GlobalShellBlock.WATERLOGGED, location.getLevel().getBlockState(location.getPosition()).getFluidState().getType() == Fluids.WATER);

        location.getLevel().setBlock(location.getPosition(), targetBlockState, 2);

        if (location.getLevel().getBlockEntity(location.getPosition()) instanceof GlobalShellBlockEntity globalShell) {
            globalShell.TARDIS_ID = UUID.fromString(operator.getLevel().dimension().location().getPath());
            location.getLevel().sendBlockUpdated(location.getPosition(), targetBlockState, targetBlockState, 2);
        }

        setLastKnownLocation(location);
        this.isLanding = true;
    }


    public boolean isExitLocationSafe() {
        BlockPos lastKnownLocationPosition = lastKnownLocation.getPosition();
        ServerLevel lastKnownLocationLevel = lastKnownLocation.getLevel();
        if (lastKnownLocationLevel.getBlockEntity(lastKnownLocationPosition) instanceof ExteriorShell shellBaseBlockEntity) {
            BlockPos landingArea = shellBaseBlockEntity.getExitPosition();
            if (lastKnownLocationLevel.getBlockState(landingArea).isAir()) {
                return lastKnownLocationLevel.getBlockState(landingArea.above()).isAir();
            }
        }

        return false;
    }

}
