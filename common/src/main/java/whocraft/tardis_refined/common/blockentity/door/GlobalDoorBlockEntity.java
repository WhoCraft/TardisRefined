package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.door.InternalDoorBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

import java.util.Optional;

public class GlobalDoorBlockEntity extends InternalDoorBlockEntity {

    private ResourceLocation shellTheme = ShellTheme.HALF_BAKED.getId();
    private ShellPattern basePattern;

    public GlobalDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_DOOR_BLOCK.get(), blockPos, blockState);
    }

    public ShellPattern pattern() {
        ShellPattern defaultBasePattern = ShellPatterns.DEFAULT;
        return this.basePattern == null ? defaultBasePattern : this.basePattern;
    }

    public GlobalDoorBlockEntity setPattern(ShellPattern basePattern) {
        this.basePattern = basePattern;
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        return this;
    }

    public void sendUpdates() {
        level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 3);
        setChanged();
    }

    public ResourceLocation theme(){
        if (this.shellTheme == null){
            this.shellTheme = ShellTheme.HALF_BAKED.getId();
        }
        return this.shellTheme;
    }

    public void setShellTheme(ResourceLocation shellTheme){
        this.shellTheme = shellTheme;
        this.setChanged();
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
        }

        if (pTag.contains(NbtConstants.PATTERN)) {
            ResourceLocation currentPattern = new ResourceLocation(pTag.getString(NbtConstants.PATTERN));
            if (ShellPatterns.doesPatternExist( this.shellTheme, currentPattern)) {
                this.basePattern = ShellPatterns.getPatternOrDefault(this.shellTheme, currentPattern);
            }
        }

        if (this.shellTheme == null){
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
                    cap.setInternalDoor(door);
                }
                if(player.isShiftKeyDown() && !cap.getPilotingManager().isInFlight()) {
                    cap.getExteriorManager().setLocked(!door.locked());
                    door.setLocked(!door.locked());
                    cap.setDoorClosed(true);
                    return;
                }
                if (!cap.getPilotingManager().isInFlight() && !door.locked()) {
                    cap.setDoorClosed(blockState.getValue(GlobalDoorBlock.OPEN));
                }
            });
        }
    }
}
