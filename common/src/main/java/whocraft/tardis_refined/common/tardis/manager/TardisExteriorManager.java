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
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.ExteriorShell;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.registry.TRBlockRegistry;

import java.util.Optional;

import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.LOCKED;
import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.REGEN;

/**
 * External Shell data.
 **/
public class TardisExteriorManager extends BaseHandler {
    private double fuelForShellChange = 15; // Amount of fuel required to change the shell

    private final TardisLevelOperator operator;

    public boolean locked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {

        TardisPilotingManager pilotingManager = this.operator.getPilotingManager();


        this.locked = locked;
        if (pilotingManager != null) {

            if (pilotingManager.isInFlight()) {
                return;
            }

            TardisNavLocation currentLocation = pilotingManager.getCurrentLocation();
            Level level = currentLocation.getLevel();
            BlockPos extPos = currentLocation.getPosition();
            if (level.getBlockState(extPos) != null) {
                BlockState extState = level.getBlockState(extPos);
                if (extState.getBlock() instanceof GlobalShellBlock shellBlock) {
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

    @Override
    public void tick() {

    }
    @Override
    public CompoundTag saveData(CompoundTag tag) {

        tag.putBoolean(NbtConstants.LOCKED, locked);

        return tag;
    }
    @Override
    public void loadData(CompoundTag tag) {
        locked = tag.getBoolean(NbtConstants.LOCKED);
    }

    public void playSoundAtShell(SoundEvent event, SoundSource source, float volume, float pitch) {

        TardisPilotingManager pilotingManager = this.operator.getPilotingManager();

        if (pilotingManager != null) {
            if (pilotingManager.getCurrentLocation() != null) {
                TardisNavLocation currentLocation = pilotingManager.getCurrentLocation();
                ServerLevel lastKnownLocationLevel = currentLocation.getLevel();

                lastKnownLocationLevel.playSound(null, currentLocation.getPosition(), event, source, volume, pitch);
            }
        }


    }

    public void setDoorClosed(boolean closed) {

        if (this.locked) {
            closed = true; //If the exterior thinks the door is already locked, then this means we should automatically close the door too.
        }

        TardisNavLocation currentPosition = this.operator.getPilotingManager().getCurrentLocation();

        if(currentPosition == null) return;
        ServerLevel lastKnownLocationLevel = currentPosition.getLevel();

        // Get the exterior block.
        BlockState state = lastKnownLocationLevel.getBlockState(currentPosition.getPosition());
        if (state.hasProperty(ShellBaseBlock.OPEN)) {
            lastKnownLocationLevel.setBlock(currentPosition.getPosition(), state.setValue(ShellBaseBlock.OPEN, !closed), 2);
            playSoundAtShell(closed ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1, closed ? 1.4F : 1F);
        }
    }


    public void triggerShellRegenState(boolean startRegen) {

        TardisPilotingManager pilotingManager = this.operator.getPilotingManager();
        if (pilotingManager == null) {
            return;
        }

        TardisNavLocation currentPosition = this.operator.getPilotingManager().getCurrentLocation();
        if(currentPosition == null) return;
        BlockPos lastKnownLocationPosition = currentPosition.getPosition();
        ServerLevel lastKnownLocationLevel = currentPosition.getLevel();

        BlockState state = lastKnownLocationLevel.getBlockState(lastKnownLocationPosition);
        if (lastKnownLocationLevel == null) return;
        if (state.getBlock() instanceof ShellBaseBlock shellBaseBlock && state.hasProperty(REGEN)) { //Check if this is our shell block and that its type has a Regen block state
            BlockState updatedBlockState = state.setValue(ShellBaseBlock.REGEN, startRegen);
            this.setOrUpdateExteriorBlock(this.operator, currentPosition, Optional.of(updatedBlockState), !startRegen);
        }
    }

    public void removeExteriorBlock() {
        this.isLanding = false;

        TardisPilotingManager pilotingManager = this.operator.getPilotingManager();
        if (pilotingManager == null) {
            return;
        }

        TardisNavLocation currentPosition = this.operator.getPilotingManager().getCurrentLocation();
        if (currentPosition != null) {
            BlockPos lastKnownLocationPosition = currentPosition.getPosition();
            ServerLevel lastKnownLocationLevel = currentPosition.getLevel();
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
    /** Setup the landing data updates and physical placement of the shell block */
    public void startLanding(TardisLevelOperator operator, TardisNavLocation location) {
        ServerLevel targetLevel = location.getLevel();
        BlockPos lastKnownLocationPosition = location.getPosition();
        ChunkPos chunkPos = location.getLevel().getChunk(lastKnownLocationPosition).getPos();

        //Force load target chunk
        targetLevel.setChunkForced(chunkPos.x, chunkPos.z, true); //Set chunk to be force loaded to properly place block

        this.setOrUpdateExteriorBlock(operator, location, Optional.empty(), true);

        //Un-force load target chunk
        targetLevel.setChunkForced(chunkPos.x, chunkPos.z, false); //Set chunk to be not be force loaded after we place the block

        this.isLanding = true;
    }

    /** Common logic to set or update the exterior shell block. This is needed to ensure we preserve data on the exterior shell such as Shell Patterns*/
    public void setOrUpdateExteriorBlock(TardisLevelOperator operator, TardisNavLocation location, Optional<BlockState> targetBlockState, boolean placeNewBlock){
        AestheticHandler aestheticHandler = operator.getAestheticHandler();
        ResourceLocation theme = (aestheticHandler.getShellTheme() != null) ? aestheticHandler.getShellTheme() : ShellTheme.HALF_BAKED.getId();
        ShellTheme shellTheme = ShellTheme.getShellTheme(theme);
        ShellPattern shellPattern = aestheticHandler.getShellTheme() != null ? aestheticHandler.shellPattern() : null;

        ServerLevel targetLevel = location.getLevel();
        BlockPos lastKnownLocationPosition = location.getPosition();

        BlockState newExteriorBlock = TRBlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState()
                .setValue(GlobalShellBlock.FACING, location.getDirection().getOpposite())
                .setValue(GlobalShellBlock.REGEN, false)
                .setValue(LOCKED, operator.getExteriorManager().locked)
                .setValue(GlobalShellBlock.LIT, shellTheme.producesLight())
                .setValue(GlobalShellBlock.WATERLOGGED, location.getLevel().getBlockState(location.getPosition()).getFluidState().getType() == Fluids.WATER);

        //If the supplied blockstate somehow doesn't have a value, provide a fallback value by using a recreated blockstate
        BlockState finalBlockstate = placeNewBlock ? newExteriorBlock : (targetBlockState.orElse(newExteriorBlock));

        //Place the exterior block
        targetLevel.setBlock(lastKnownLocationPosition, finalBlockstate, Block.UPDATE_ALL);
        //Copy over important data points
        if (targetLevel.getBlockEntity(lastKnownLocationPosition) instanceof GlobalShellBlockEntity globalShell) {
            globalShell.setTardisId(operator.getLevel().dimension()); //DO NOT set the target dimension, otherwise the TARDIS_ID on the exterior will never be correct and key locking features will be broken
            globalShell.setShellTheme(theme);

            if (shellPattern != null) {
                globalShell.setPattern(shellPattern);
            }

            globalShell.sendUpdates();

            targetLevel.sendBlockUpdated(lastKnownLocationPosition, finalBlockstate, finalBlockstate, Block.UPDATE_CLIENTS);
        }
    }


    public boolean isExitLocationSafe() {

        TardisPilotingManager pilotingManager = this.operator.getPilotingManager();
        if (pilotingManager == null) {
            return false;
        }

        TardisNavLocation currentPosition = this.operator.getPilotingManager().getCurrentLocation();
        if(currentPosition == null) return false;

        BlockPos lastKnownLocationPosition = currentPosition.getPosition();
        ServerLevel lastKnownLocationLevel = currentPosition.getLevel();
        if (lastKnownLocationLevel.getBlockEntity(lastKnownLocationPosition) instanceof ExteriorShell shellBaseBlockEntity) {
            BlockPos landingArea = shellBaseBlockEntity.getExitPosition();
            if (lastKnownLocationLevel.getBlockState(landingArea).isAir()) {
                return lastKnownLocationLevel.getBlockState(landingArea.above()).isAir();
            }
        }

        return false;
    }


    /**
     * Returns whether a Tardis has enough fuel to perform an interior change
     * @return true if the Tardis has enough fuel
     */
    public boolean hasEnoughFuelForShellChange() {
        return this.operator.getPilotingManager().getFuel() >= this.getFuelForShellChange();
    }

    /**
     * The amount of fuel required to change the exterior shell
     * @return double amount of fuel to be removed
     */
    public double getFuelForShellChange() {
        return this.fuelForShellChange;
    }

    /**
     * Sets the amount of fuel required to change the exterior shell
     * @param fuel the amount of fuel
     */
    private void setFuelForShellChange(double fuel) {
        this.fuelForShellChange = fuel;
    }
}
