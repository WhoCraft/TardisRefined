package whocraft.tardis_refined.common.tardis.manager;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.door.RootShellDoorBlock;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.hum.HumEntry;
import whocraft.tardis_refined.common.hum.TardisHums;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.TRBlockRegistry;

// #PimpMyTimeship
public class AestheticHandler extends BaseHandler {

    private final TardisLevelOperator tardisOperator;

    // Shell
    private ResourceLocation shellTheme = ShellTheme.HALF_BAKED.getId();
    private ShellPattern shellPattern = ShellPatterns.DEFAULT;
    private HumEntry currentHum = TardisHums.getDefaultHum();


    public AestheticHandler(TardisLevelOperator tardisLevelOperator) {
        super();
        this.tardisOperator = tardisLevelOperator;
    }


    public ShellPattern shellPattern() {
        return shellPattern;
    }

    public void setShellPattern(ShellPattern shellPattern) {
        this.shellPattern = shellPattern;
    }

    public ResourceLocation getShellTheme() {
        if(shellTheme.getNamespace().contains("minecraft")){
            return ShellTheme.HALF_BAKED.getId();
        }
        return shellTheme;
    }

    /**
     * Set the theme ID for the Exterior Shell Block assuming that the Tardis is NOT being transformed from a Root Shell
     *
     * @param theme
     */
    public void setShellTheme(ResourceLocation theme, ResourceLocation shellPattern, TardisNavLocation tardisNavLocation) {
        this.setShellTheme(theme, shellPattern, false, tardisNavLocation);
    }

    /**
     * Sets the shell theme ID for the Exterior Shell Block
     *
     * @param theme       - the Shell Theme ID
     * @param shellPattern - the Shell Theme Pattern
     * @param setupTardis - if the reason for setting the theme was because the Tardis is being converted from a Root Shell to a fully functioning one. True if that is the case.
     */
    public void setShellTheme(ResourceLocation theme, ResourceLocation shellPattern, boolean setupTardis, TardisNavLocation tardisNavLocation) {
        setShellPattern(ShellPatterns.getPatternOrDefault(theme, shellPattern));

        if (tardisNavLocation == null) return;

        this.shellTheme = theme;

        BlockPos lastKnownLocationPosition = tardisNavLocation.getPosition();
        ServerLevel lastKnownLocationLevel = tardisNavLocation.getLevel();
        BlockState state = lastKnownLocationLevel.getBlockState(lastKnownLocationPosition);


        if (setupTardis) {
            if (state.getBlock() instanceof RootedShellBlock) {
                // If the block at the last known location was originally a Root Shell Block (i.e. transforming to a proper Tardis),
                // Create a new Global Shell Block instance and copy over all attributes from the existing shell
                lastKnownLocationLevel.setBlock(lastKnownLocationPosition, TRBlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.OPEN, state.getValue(RootedShellBlock.OPEN)).setValue(GlobalShellBlock.FACING, state.getValue(RootedShellBlock.FACING)).setValue(GlobalShellBlock.REGEN, false), Block.UPDATE_ALL_IMMEDIATE);

                //Copy over important data such as Tardis ID

                updateShellBlock(theme, shellPattern, lastKnownLocationLevel, lastKnownLocationPosition);
                updateInteriorDoors(theme, shellPattern);

            }
            return;
        }


        // Check if its our default global shell.
        if (state.getBlock() instanceof GlobalShellBlock globalShellBlock) {

            ShellTheme shellTheme = ShellTheme.getShellTheme(theme);
            boolean shouldProduceLight = shellTheme.producesLight();

            lastKnownLocationLevel.setBlock(lastKnownLocationPosition, state.setValue(GlobalShellBlock.REGEN, false).setValue(GlobalShellBlock.LIT, shouldProduceLight), Block.UPDATE_CLIENTS);

            // Update Exterior (We should make this a method tbh)
            updateShellBlock(theme, shellPattern, lastKnownLocationLevel, lastKnownLocationPosition);
            updateInteriorDoors(theme, shellPattern);
        }

    }

    public void updateInteriorDoors(ResourceLocation theme, ResourceLocation shellPattern) {
        if (tardisOperator.getInternalDoor() != null) {
            BlockPos internalDoorPos = tardisOperator.getInternalDoor().getDoorPosition();
            BlockState state = tardisOperator.getLevel().getBlockState(internalDoorPos);
            BlockEntity blockEntity = tardisOperator.getLevel().getBlockEntity(internalDoorPos);

            if (state.getBlock() instanceof RootShellDoorBlock) {
                // If the block at the last known location was originally a Root Shell Door (i.e. transforming to a proper Tardis),
                // Create a new Global Shell Door instance and copy over all attributes from the existing shell
                tardisOperator.getLevel().setBlock(internalDoorPos,
                        TRBlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.OPEN, state.getValue(RootedShellBlock.OPEN))
                                .setValue(GlobalShellBlock.FACING, state.getValue(RootedShellBlock.FACING)), Block.UPDATE_ALL);

                var potentialDoor = tardisOperator.getLevel().getBlockEntity(internalDoorPos);
                if (potentialDoor instanceof GlobalDoorBlockEntity doorBlockEntity) {
                    doorBlockEntity.setShellTheme(theme);
                    doorBlockEntity.setPattern(ShellPatterns.getPatternOrDefault(shellTheme, shellPattern));
                    tardisOperator.setInternalDoor(doorBlockEntity);
                    doorBlockEntity.sendUpdates();
                }
            } else {
                // Check if its our default global shell.
                if (blockEntity instanceof GlobalDoorBlockEntity doorBlockEntity) {
                    doorBlockEntity.setShellTheme(theme);
                    doorBlockEntity.setPattern(ShellPatterns.getPatternOrDefault(shellTheme, shellPattern));
                    doorBlockEntity.sendUpdates();
                }
            }


        }
    }

    private void updateShellBlock(ResourceLocation theme, ResourceLocation shellPattern, ServerLevel lastKnownLocationLevel, BlockPos lastKnownLocationPosition) {
        var shellBlockEntity = lastKnownLocationLevel.getBlockEntity(lastKnownLocationPosition);
        if (shellBlockEntity instanceof GlobalShellBlockEntity entity) {
            entity.setTardisId(tardisOperator.getLevel().dimension());
            entity.setShellTheme(theme);
            entity.setPattern(ShellPatterns.getPatternOrDefault(theme, shellPattern));
            entity.sendUpdates();
            entity.setChanged();
        }
    }

    @Override
    public CompoundTag saveData(CompoundTag baseTag) {
        CompoundTag aestheticTag = new CompoundTag();

        // Shell
        CompoundTag shellInfo = new CompoundTag();
        if (this.shellTheme != null) {
            shellInfo.putString(NbtConstants.TARDIS_EXT_CURRENT_THEME, shellTheme.toString());
        }
        if (this.shellPattern != null) {
            shellInfo.putString(NbtConstants.TARDIS_EXT_CURRENT_PATTERN, shellPattern.id().toString());
        }


        aestheticTag.put("shell", shellInfo);
        baseTag.put("aesthetic", aestheticTag);

        return baseTag;
    }

    @Override
    public void loadData(CompoundTag tag) {
        if (tag.contains("aesthetic", NbtType.COMPOUND)) {
            CompoundTag aestheticTag = tag.getCompound("aesthetic");

            // Shell
            if (aestheticTag.contains("shell", NbtType.COMPOUND)) {
                CompoundTag shellInfo = aestheticTag.getCompound("shell");

                if (shellInfo.contains(NbtConstants.TARDIS_EXT_CURRENT_THEME, NbtType.STRING) && shellInfo.contains(NbtConstants.TARDIS_EXT_CURRENT_PATTERN, NbtType.STRING)) {
                    ResourceLocation themeID = new ResourceLocation(shellInfo.getString(NbtConstants.TARDIS_EXT_CURRENT_THEME));
                    this.shellTheme = themeID;
                    String patternId = shellInfo.getString(NbtConstants.TARDIS_EXT_CURRENT_PATTERN);
                    this.shellPattern = ShellPatterns.getPatternOrDefault(themeID, new ResourceLocation(patternId));
                }
            }
        }
    }

}
