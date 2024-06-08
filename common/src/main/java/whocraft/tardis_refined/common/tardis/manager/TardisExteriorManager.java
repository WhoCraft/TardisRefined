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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.door.AbstractDoorBlockEntity;
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
                if (extState.getBlock() instanceof ShellBaseBlock shellBlock) {
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

    public void setDoorClosed(boolean closeDoor) {

        TardisNavLocation currentPosition = this.operator.getPilotingManager().getCurrentLocation();

        if(currentPosition == null) return;
        ServerLevel lastKnownLocationLevel = currentPosition.getLevel();

        // Get the exterior block.
        BlockEntity blockEntity = lastKnownLocationLevel.getBlockEntity(currentPosition.getPosition());
        if (blockEntity instanceof AbstractDoorBlockEntity doorBlockEntity) {
            doorBlockEntity.setClosed(closeDoor);
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

        this.setOrUpdateExteriorBlock(operator, location, Optional.empty());

        //Un-force load target chunk
        targetLevel.setChunkForced(chunkPos.x, chunkPos.z, false); //Set chunk to be not be force loaded after we place the block

        this.isLanding = true;
    }

    /** Common logic to set or update the exterior shell block. This is needed to ensure we preserve data on the exterior shell such as Shell Patterns.
     *
     * @param operator - The TardisLevelOperator instance
     * @param location - target position we are performing block updates on.
     * @param targetBlockState - Optional value if we want to pass in a blockstate that will override a newly created blockstate
     */
    public void setOrUpdateExteriorBlock(TardisLevelOperator operator, TardisNavLocation location, Optional<BlockState> targetBlockState){
        AestheticHandler aestheticHandler = operator.getAestheticHandler();
        ResourceLocation theme = (aestheticHandler.getShellTheme() != null) ? aestheticHandler.getShellTheme() : ShellTheme.HALF_BAKED.getId();
        ShellTheme shellTheme = ShellTheme.getShellTheme(theme);
        ShellPattern shellPattern = aestheticHandler.getShellTheme() != null ? aestheticHandler.shellPattern() : null;

        ServerLevel targetLevel = location.getLevel();
        BlockPos targetLocation = location.getPosition();
        //Check the target location and update the existing blockstate if needed. Otherwise, utilise a new blockstate instance of the exterior block
        BlockState newExteriorBlock = TRBlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState();

        //If the supplied blockstate is empty, utilise a new blockstate. Otherwise, simply update the values of the passed-in blockstate so that we don't need to change things we don't want.
        BlockState selectedBlockState = targetBlockState.orElse(newExteriorBlock);

        BlockState updatedBlockState = selectedBlockState.setValue(ShellBaseBlock.FACING, location.getDirection().getOpposite())
                .setValue(ShellBaseBlock.REGEN, false)
                .setValue(LOCKED, this.locked)
                .setValue(ShellBaseBlock.WATERLOGGED, location.getLevel().getBlockState(targetLocation).getFluidState().getType() == Fluids.WATER);

        if (updatedBlockState.hasProperty(GlobalShellBlock.LIT)){ //Special logic to account for RootedShellBlock not having the LIT blockstate property
            updatedBlockState.setValue(GlobalShellBlock.LIT, shellTheme.producesLight());
        }


        //Place the exterior block
        targetLevel.setBlock(targetLocation, updatedBlockState, Block.UPDATE_ALL);
        //Copy over important data points
        if (targetLevel.getBlockEntity(targetLocation) instanceof GlobalShellBlockEntity globalShell) {
            globalShell.setTardisId(operator.getLevel().dimension()); //DO NOT set the target dimension, otherwise the TARDIS_ID on the exterior will never be correct and key locking features will be broken
            globalShell.setShellTheme(theme);

            if (shellPattern != null) {
                globalShell.setPattern(shellPattern);
            }

            globalShell.sendUpdates();

            targetLevel.sendBlockUpdated(targetLocation, updatedBlockState, updatedBlockState, Block.UPDATE_CLIENTS);
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
