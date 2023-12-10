package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
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
import whocraft.tardis_refined.common.util.RegistryHelper;
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
    private ResourceLocation currentTheme;

    private ShellPattern shellPattern = null;

    public boolean locked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        if (operator.getPilotingManager().isInFlight()) {
            return;
        }
        this.locked = locked;
        if (this.getLastKnownLocation() != null){
            TardisNavLocation lastKnownLocation = this.getLastKnownLocation();
            Level level = lastKnownLocation.getLevel();
            BlockPos extPos = lastKnownLocation.getPosition();
            if (level.getBlockState(extPos) != null){
                BlockState extState = level.getBlockState(extPos);
                if (extState.getBlock() instanceof GlobalShellBlock shellBlock){
                    level.setBlock(extPos, extState.setValue(LOCKED, locked), Block.UPDATE_ALL);
                }
            }
        }

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

    public ResourceLocation getCurrentTheme() {
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
            tag.putString(NbtConstants.TARDIS_EXT_CURRENT_THEME, this.currentTheme.toString());
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
            this.currentTheme = new ResourceLocation(tag.getString(NbtConstants.TARDIS_EXT_CURRENT_THEME));
        }

        if (tag.contains(NbtConstants.TARDIS_EXT_CURRENT_PATTERN) && tag.getString(NbtConstants.TARDIS_EXT_CURRENT_PATTERN) != null) {
            this.shellPattern = ShellPatterns.getPatternOrDefault(currentTheme, new ResourceLocation(tag.getString(NbtConstants.TARDIS_EXT_CURRENT_PATTERN)));
        }

        locked = tag.getBoolean(NbtConstants.LOCKED);


    }

    public void playSoundAtShell(SoundEvent event, SoundSource source, float volume, float pitch) {
        ServerLevel lastKnownLocationLevel = lastKnownLocation.getLevel();
        if (lastKnownLocation != null) {
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

    /**
     * Set the theme ID for the Exterior Shell Block assuming that the Tardis is NOT being transformed from a Root Shell or changing desktops
     * @param theme
     */
    public void setShellTheme(ResourceLocation theme){
        this.setShellTheme(theme, false);
    }

    /**
     * Sets the shell theme ID for the Exterior Shell Block
     * @param theme - the Shell Theme ID
     * @param setupTardis - if the reason for setting the theme was because the Tardis is being converted from a Root Shell to a fully functioning one, or changing desktops. True if that is the case.
     */
    public void setShellTheme(ResourceLocation theme, boolean setupTardis) {

        if(lastKnownLocation == null) return;

        this.currentTheme = theme;

        BlockPos lastKnownLocationPosition = lastKnownLocation.getPosition();
        ServerLevel lastKnownLocationLevel = lastKnownLocation.getLevel();
        BlockState state = lastKnownLocationLevel.getBlockState(lastKnownLocationPosition);


        if (setupTardis){
            // Check if we're updating an existing GlobalShellBlock instance, set the shell's REGEN property to false to indicate it's no longer regenerating
            if (state.getBlock() instanceof GlobalShellBlock) {
                lastKnownLocationLevel.setBlock(lastKnownLocationPosition, state.setValue(GlobalShellBlock.REGEN, false), 2);
            }
            else {
                //Otherwise, check if we're trying to setup a new Tardis beginning from a root shell
                if (state.getBlock() instanceof RootedShellBlock) {
                    // If the block at the last known location was originally a Root Shell Block (i.e. transforming to a proper Tardis),
                    // Create a new Global Shell Block instance and copy over all attributes from the existing shell
                    lastKnownLocationLevel.setBlock(lastKnownLocationPosition,
                            BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.OPEN, state.getValue(RootedShellBlock.OPEN)).setValue(GlobalShellBlock.FACING, state.getValue(RootedShellBlock.FACING)).setValue(GlobalShellBlock.REGEN, false), Block.UPDATE_ALL);
                }
            }
        }

        //After accounting for logic such as desktop generation, copy over important data such as Tardis ID and actually set the theme for the block entity
        var shellBlockEntity = lastKnownLocationLevel.getBlockEntity(lastKnownLocationPosition);
        if (shellBlockEntity instanceof GlobalShellBlockEntity entity) {
            entity.setTardisId(operator.getLevel().dimension());
            // Make sure to set the shell theme so that any pattern lookups by theme Id won't fail
            entity.setShellTheme(theme);
            // Also update the shell pattern
            // If the exterior manager has a shell pattern already set, make sure the theme being updated doesn't mismatch the theme ID of the current shell Pattern
            //TODO: This has a performance implication because ShellPatterns#getThemeForPattern does an expensive lookup, do we still need this?
            if(shellPattern != null) {
                entity.setPattern(ShellPatterns.getThemeForPattern(this.shellPattern) != theme ? shellPattern : ShellPatterns.getPatternsForTheme(theme).get(0));
            }
        }

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
            ChunkPos chunkPos = lastKnownLocationLevel.getChunk(lastKnownLocationPosition).getPos();
            //Force load chunk
            lastKnownLocationLevel.setChunkForced(chunkPos.x, chunkPos.z, true); //Set chunk to be force loaded to properly remove block
            //Remove block
            if (lastKnownLocationLevel.getBlockState(lastKnownLocationPosition).getBlock() instanceof GlobalShellBlock shellBlock) {
                lastKnownLocationLevel.destroyBlock(lastKnownLocationPosition, false); //Set block to air with drop items flag to false
            }
            //Un-force load chunk
            lastKnownLocationLevel.setChunkForced(chunkPos.x, chunkPos.z, false); //Set chunk to not be force loaded after we remove the block
        }
    }

    public void placeExteriorBlock(TardisLevelOperator operator, TardisNavLocation location) {
        ResourceLocation theme = (this.currentTheme != null) ? this.currentTheme : ShellTheme.FACTORY.getId();
        BlockState targetBlockState = BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState()
                .setValue(GlobalShellBlock.FACING, location.getDirection().getOpposite())
                .setValue(GlobalShellBlock.REGEN, false)
                .setValue(LOCKED, operator.getExteriorManager().locked)
                .setValue(GlobalShellBlock.WATERLOGGED, location.getLevel().getBlockState(location.getPosition()).getFluidState().getType() == Fluids.WATER);

        ServerLevel targetLevel = location.getLevel();
        BlockPos lastKnownLocationPosition = location.getPosition();
        ChunkPos chunkPos = location.getLevel().getChunk(lastKnownLocationPosition).getPos();

        //Force load target chunk
        targetLevel.setChunkForced(chunkPos.x, chunkPos.z, true); //Set chunk to be force loaded to properly place block

        //Place the exterior block
        location.getLevel().setBlock(location.getPosition(), targetBlockState, Block.UPDATE_ALL);
        //Copy over important data points
        if (location.getLevel().getBlockEntity(location.getPosition()) instanceof GlobalShellBlockEntity globalShell) {
            globalShell.setTardisId(operator.getLevel().dimension());
            globalShell.setShellTheme(theme);
            location.getLevel().sendBlockUpdated(location.getPosition(), targetBlockState, targetBlockState, Block.UPDATE_CLIENTS);
        }

        //Un-force load target chunk
        targetLevel.setChunkForced(chunkPos.x, chunkPos.z, false); //Set chunk to be not be force loaded after we place the block

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
