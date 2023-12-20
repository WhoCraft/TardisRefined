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
import whocraft.tardis_refined.registry.BlockRegistry;

import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.LOCKED;
import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.REGEN;

/**
 * External Shell data.
 **/
public class TardisExteriorManager extends BaseHandler {

    private final TardisLevelOperator operator;
    private TardisNavLocation lastKnownLocation = TardisNavLocation.ORIGIN;

    public boolean locked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        if (operator.getPilotingManager().isInFlight()) {
            return;
        }
        this.locked = locked;
        if (this.getLastKnownLocation() != null) {
            TardisNavLocation lastKnownLocation = this.getLastKnownLocation();
            Level level = lastKnownLocation.getLevel();
            BlockPos extPos = lastKnownLocation.getPosition();
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

    public void setLastKnownLocation(TardisNavLocation lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public TardisNavLocation getLastKnownLocation() {

        if(lastKnownLocation == null){
            return TardisNavLocation.ORIGIN;
        }

        return this.lastKnownLocation;
    }


    public ServerLevel getLevel() {
        return this.getLastKnownLocation().getLevel();
    }


    @Override
    public void tick() {

    }
    @Override
    public CompoundTag saveData(CompoundTag tag) {

        if (this.lastKnownLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "lk_ext", this.lastKnownLocation);
        }
        tag.putBoolean(NbtConstants.LOCKED, locked);

        return tag;
    }
    @Override
    public void loadData(CompoundTag tag) {
        this.lastKnownLocation = NbtConstants.getTardisNavLocation(tag, "lk_ext", operator);
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
        AestheticHandler aestheticHandler = operator.getAestheticHandler();
        ResourceLocation theme = (aestheticHandler.getShellTheme() != null) ? aestheticHandler.getShellTheme() : ShellTheme.FACTORY.getId();
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
