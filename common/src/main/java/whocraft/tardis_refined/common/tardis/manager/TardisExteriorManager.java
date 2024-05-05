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
import whocraft.tardis_refined.registry.TRBlockRegistry;

import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.LOCKED;
import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.REGEN;

/**
 * External Shell data.
 **/
public class TardisExteriorManager extends BaseHandler {
    private double fuelForShellChange = 15; // Amount of fuel required to change the shell

    private final TardisLevelOperator operator;

    public boolean locked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        if (operator.getPilotingManager().isInFlight()) {
            return;
        }
        this.locked = locked;
        if (this.operator.getPilotingManager() != null) {
            TardisNavLocation currentLocation = this.operator.getPilotingManager().getCurrentLocation();
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
        if (this.operator.getPilotingManager().getCurrentLocation() != null) {
            ServerLevel lastKnownLocationLevel = this.operator.getPilotingManager().getCurrentLocation().getLevel();
            lastKnownLocationLevel.playSound(null, this.operator.getPilotingManager().getCurrentLocation().getPosition(), event, source, volume, pitch);
        }
    }

    public void setDoorClosed(boolean closed) {

        if (locked) {
            closed = true;
        }

        TardisNavLocation currentPosition = this.operator.getPilotingManager().getCurrentLocation();

        if(currentPosition == null) return;
        ServerLevel lastKnownLocationLevel = currentPosition.getLevel();

        // Get the exterior block.
        BlockState state = lastKnownLocationLevel.getBlockState(currentPosition.getPosition());
        if (state.hasProperty(ShellBaseBlock.OPEN)) {
            lastKnownLocationLevel.setBlock(currentPosition.getPosition(), state.setValue(ShellBaseBlock.OPEN, !closed), 2);
            playSoundAtShell(locked ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1, locked ? 1.4F : 1F);
        }
    }


    public void triggerShellRegenState() {
        TardisNavLocation currentPosition = this.operator.getPilotingManager().getCurrentLocation();
        if(currentPosition == null) return;
        BlockPos lastKnownLocationPosition = currentPosition.getPosition();
        ServerLevel lastKnownLocationLevel = currentPosition.getLevel();

        BlockState state = lastKnownLocationLevel.getBlockState(lastKnownLocationPosition);
        if (lastKnownLocationLevel == null) return;
        if (!lastKnownLocationLevel.getBlockState(lastKnownLocationPosition).hasProperty(REGEN)) return;
        lastKnownLocationLevel.setBlock(lastKnownLocationPosition, state.setValue(ShellBaseBlock.REGEN, true), Block.UPDATE_ALL);
    }

    public void removeExteriorBlock() {
        this.isLanding = false;

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

    public void placeExteriorBlock(TardisLevelOperator operator, TardisNavLocation location) {
        AestheticHandler aestheticHandler = operator.getAestheticHandler();
        ResourceLocation theme = (aestheticHandler.getShellTheme() != null) ? aestheticHandler.getShellTheme() : ShellTheme.HALF_BAKED.getId();
        ShellTheme shellTheme = ShellTheme.getShellTheme(theme);

        //remove the exterior block
        location.getLevel().setBlock(location.getPosition(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

        BlockState targetBlockState = TRBlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState()
                .setValue(GlobalShellBlock.FACING, location.getDirection().getOpposite())
                .setValue(GlobalShellBlock.REGEN, false)
                .setValue(LOCKED, operator.getExteriorManager().locked)
                .setValue(GlobalShellBlock.LIT, shellTheme.producesLight())
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

        this.isLanding = true;
    }


    public boolean isExitLocationSafe() {

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
