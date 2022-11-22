package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.NbtConstants;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.registry.BlockRegistry;

import java.util.UUID;

/**
 * External Shell data.
 * **/
public class TardisExteriorManager {

    private TardisLevelOperator operator;
    private TardisNavLocation lastKnownLocation;

    public TardisExteriorManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void setLastKnownLocation(TardisNavLocation lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public TardisNavLocation getLastKnownLocation() {
        return this.lastKnownLocation;
    }

    public ServerLevel getLevel() {
        return this.lastKnownLocation.level;
    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.put(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_POS, NbtUtils.writeBlockPos(this.lastKnownLocation.position));
        tag.putInt(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_ROT, this.lastKnownLocation.rotation.get2DDataValue());
        tag.putString(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_LVL_MODID, this.lastKnownLocation.level.dimension().location().getNamespace());
        tag.putString(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_LVL_LOC, this.lastKnownLocation.level.dimension().location().getPath());
        return tag;
    }

    public void loadData(CompoundTag tag) {
        BlockPos lkPosition = NbtUtils.readBlockPos(tag.getCompound(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_POS));
        int lkRotation = tag.getInt(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_ROT);
        String lkLevelModID = tag.getString(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_LVL_MODID);
        String lkLevelLoc = tag.getString(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_LVL_LOC);

        if (lkLevelLoc != null && lkLevelModID != null && lkLevelLoc != null) {
            // Fetch the level.

            ServerLevel level = operator.getLevel().getServer().levels.get(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(lkLevelModID, lkLevelLoc)));
            if (level != null) {
                this.lastKnownLocation = new TardisNavLocation(lkPosition, Direction.from2DDataValue(lkRotation), level);
            }
        }
    }

    public void playSoundAtShell(SoundEvent event, SoundSource source, float volume, float pitch) {
        lastKnownLocation.level.playSound(null, lastKnownLocation.position, event, source, volume, pitch);
    }

    public void setDoorClosed(boolean closed) {
        // Get the exterior block.
        BlockState state = lastKnownLocation.level.getBlockState(lastKnownLocation.position);
        lastKnownLocation.level.setBlock(lastKnownLocation.position, state.setValue(ShellBaseBlock.OPEN, !closed), 2);
        playSoundAtShell((closed) ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1, 1);
    }

    public void setShellTheme(ShellTheme theme) {
        BlockState state = lastKnownLocation.level.getBlockState(lastKnownLocation.position);
        // Check if its our default global shell.

        if (state.getBlock() instanceof GlobalShellBlock) {
            lastKnownLocation.level.setBlock(lastKnownLocation.position,
                    state.setValue(GlobalShellBlock.SHELL, theme).setValue(GlobalShellBlock.REGEN, false), 2);
        } else {
            if (state.getBlock() instanceof RootedShellBlock) {
                lastKnownLocation.level.setBlock(lastKnownLocation.position,
                        BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.OPEN, state.getValue(RootedShellBlock.OPEN))
                                .setValue(GlobalShellBlock.FACING, state.getValue(RootedShellBlock.FACING)).setValue(GlobalShellBlock.SHELL, theme)
                                .setValue(GlobalShellBlock.REGEN, false), 2);

                var shellBlockEntity = lastKnownLocation.level.getBlockEntity(lastKnownLocation.position);
                if (shellBlockEntity instanceof GlobalShellBlockEntity entity) {
                    entity.id = UUID.fromString((operator.getLevel().dimension().location().getPath().toString()));
                }
            }
        }
    }

    public void triggerShellRegenState() {
        BlockState state = lastKnownLocation.level.getBlockState(lastKnownLocation.position);

        lastKnownLocation.level.setBlock(lastKnownLocation.position,
                state.setValue(ShellBaseBlock.REGEN, true), 2);
    }

}
