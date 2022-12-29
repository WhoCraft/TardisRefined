package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.IExteriorShell;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.registry.BlockRegistry;

import java.util.UUID;

import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.LOCKED;

/**
 * External Shell data.
 **/
public class TardisExteriorManager {

    private TardisLevelOperator operator;
    private TardisNavLocation lastKnownLocation;
    private ShellTheme currentTheme;

    public boolean locked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        if (operator.getControlManager().isInFlight()) {return;}
        this.locked = locked;
    }

    private boolean locked;
    private boolean isLanding;
    public boolean isLanding() {return this.isLanding;}

    private boolean isTakingOff;
    public boolean isTakingOff() {return this.isTakingOff;}
    public void setIsTakingOff(boolean isTakingOff) {this.isTakingOff = isTakingOff; }


    public TardisExteriorManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void setLastKnownLocation(TardisNavLocation lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public TardisNavLocation getLastKnownLocation() {
        return this.lastKnownLocation;
    }

    public ShellTheme getCurrentTheme() {
        return this.currentTheme;
    }

    public ServerLevel getLevel() {
        return this.lastKnownLocation.level;
    }

    public CompoundTag saveData(CompoundTag tag) {

        if (this.lastKnownLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "lk_ext", this.lastKnownLocation);
        }
        if (this.currentTheme != null) {
            tag.putString(NbtConstants.TARDIS_EXT_CURRENT_THEME, this.currentTheme.getSerializedName());
        }

        tag.putBoolean(NbtConstants.LOCKED, locked);

        return tag;
    }

    public void loadData(CompoundTag tag) {
        TardisNavLocation location = NbtConstants.getTardisNavLocation(tag, "lk_ext", operator);
        this.lastKnownLocation = location;

        if (tag.getString(NbtConstants.TARDIS_EXT_CURRENT_THEME) != null) {
            this.currentTheme = ShellTheme.findOr(tag.getString(NbtConstants.TARDIS_EXT_CURRENT_THEME), ShellTheme.FACTORY);
        }

        locked = tag.getBoolean(NbtConstants.LOCKED);
    }

    public void playSoundAtShell(SoundEvent event, SoundSource source, float volume, float pitch) {
        if (lastKnownLocation != null) { lastKnownLocation.level.playSound(null, lastKnownLocation.position, event, source, volume, pitch);}
    }

    public void setDoorClosed(boolean closed) {

        if (locked) {
            closed = true;
        }

        // Get the exterior block.
        BlockState state = lastKnownLocation.level.getBlockState(lastKnownLocation.position);
        if (state.hasProperty(ShellBaseBlock.OPEN)) {
            lastKnownLocation.level.setBlock(lastKnownLocation.position, state.setValue(ShellBaseBlock.OPEN, !closed), 2);
            playSoundAtShell(locked ? SoundEvents.PLAYER_ATTACK_SWEEP : (closed) ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1, locked ? 1.4F : 1F);
        }
    }

    public void setShellTheme(ShellTheme theme) {
        BlockState state = lastKnownLocation.level.getBlockState(lastKnownLocation.position);

        // Check if its our default global shell.
        if (state.getBlock() instanceof GlobalShellBlock) {
            lastKnownLocation.level.setBlock(lastKnownLocation.position, state.setValue(GlobalShellBlock.SHELL, theme).setValue(GlobalShellBlock.REGEN, false), 2);
        } else {
            if (state.getBlock() instanceof RootedShellBlock) {
                lastKnownLocation.level.setBlock(lastKnownLocation.position,
                        BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.OPEN, state.getValue(RootedShellBlock.OPEN)).setValue(GlobalShellBlock.FACING, state.getValue(RootedShellBlock.FACING)).setValue(GlobalShellBlock.SHELL, theme).setValue(GlobalShellBlock.REGEN, false), 2);

                var shellBlockEntity = lastKnownLocation.level.getBlockEntity(lastKnownLocation.position);
                if (shellBlockEntity instanceof GlobalShellBlockEntity entity) {
                    entity.id = UUID.fromString((operator.getLevel().dimension().location().getPath()));
                    entity.setChanged();
                }
            }
        }

        this.currentTheme = theme;
    }

    public void triggerShellRegenState() {
        BlockState state = lastKnownLocation.level.getBlockState(lastKnownLocation.position);

        lastKnownLocation.level.setBlock(lastKnownLocation.position,
                state.setValue(ShellBaseBlock.REGEN, true), 2);
    }

    public void removeExteriorBlock() {
        this.isLanding = false;
        if (lastKnownLocation != null) {
            if (lastKnownLocation.level.getBlockState(lastKnownLocation.position).getBlock() instanceof GlobalShellBlock shellBlock) {
                lastKnownLocation.level.setBlockAndUpdate(lastKnownLocation.position, Blocks.AIR.defaultBlockState());
            }
        }
    }

    public void placeExteriorBlock(TardisLevelOperator operator, TardisNavLocation location) {

        ShellTheme theme = (this.currentTheme != null) ? this.currentTheme : ShellTheme.FACTORY;

        location.level.setBlockAndUpdate(location.position, BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.SHELL, theme)
                .setValue(GlobalShellBlock.FACING, location.rotation.getOpposite()).setValue(GlobalShellBlock.REGEN, false).setValue(LOCKED, operator.getExteriorManager().locked));

        GlobalShellBlockEntity shell = (GlobalShellBlockEntity) location.level.getBlockEntity(location.position);
        shell.id = UUID.fromString(operator.getLevel().dimension().location().getPath());

        this.lastKnownLocation = location;
        this.isLanding = true;
    }

    public boolean isExitLocationSafe() {
        if (lastKnownLocation.level.getBlockEntity(lastKnownLocation.position) instanceof IExteriorShell shellBaseBlockEntity) {
            BlockPos landingArea = shellBaseBlockEntity.getExitPosition();
            if (lastKnownLocation.level.getBlockState(landingArea) == Blocks.AIR.defaultBlockState()) {
                if (lastKnownLocation.level.getBlockState(landingArea.above()) == Blocks.AIR.defaultBlockState()) {
                    return true;
                }
            }
        }

        return false;
    }

}
