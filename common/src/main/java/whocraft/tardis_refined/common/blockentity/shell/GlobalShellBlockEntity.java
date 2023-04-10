package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.BasePattern;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

public class GlobalShellBlockEntity extends ShellBaseBlockEntity {

    private ShellPattern basePattern = pattern();

    public ShellPattern pattern() {
        ShellTheme console = getBlockState().getValue(GlobalShellBlock.SHELL);
        ShellPattern defaultBasePattern = ShellPatterns.getPatternFromString(console, new ResourceLocation(TardisRefined.MODID, console.getSerializedName() + "/default"));
        return basePattern == null ? defaultBasePattern : basePattern;
    }

    public GlobalShellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_SHELL_BLOCK.get(), blockPos, blockState);
    }

    public GlobalShellBlockEntity setPattern(ShellPattern basePattern) {
        this.basePattern = basePattern;
        return this;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains(NbtConstants.PATTERN)) {
            ShellTheme shellTheme = getBlockState().getValue(GlobalShellBlock.SHELL);
            ResourceLocation currentPattern = new ResourceLocation(pTag.getString(NbtConstants.PATTERN));
            if (ShellPatterns.doesPatternExist(shellTheme, currentPattern)) {
                basePattern = ShellPatterns.getPatternFromString(shellTheme, currentPattern);
            }
        }

        if (basePattern == null) {
            basePattern = pattern();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (basePattern != null) {
            pTag.putString(NbtConstants.PATTERN, basePattern.id().toString());
        }
    }

    public void onRightClick(BlockState blockState, ItemStack stack) {

        if (blockState.getValue(ShellBaseBlock.REGEN)) {return;}

        if (getLevel() instanceof ServerLevel serverLevel) {
            ServerLevel interior = DimensionHandler.getExistingLevel(serverLevel, TARDIS_ID.toString());
            if (interior != null) {
                TardisLevelOperator.get(interior).ifPresent(cap -> {
                    if (cap.getControlManager().isInFlight()) return;
                    ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(TardisRefined.MODID, TARDIS_ID.toString()));

                    boolean validKey = KeyItem.keychainContains(stack, dimension);
                    if(validKey) {
                        boolean locked = !cap.getExteriorManager().locked();
                        cap.getExteriorManager().setLocked(locked);
                        cap.getInternalDoor().setLocked(locked);
                        cap.setDoorClosed(true);
                        return;
                    }
                    if(cap.getExteriorManager().locked()) return;
                    cap.setDoorClosed(blockState.getValue(GlobalShellBlock.OPEN));
                });
            }
        }
    }

    public void sendUpdates() {
        level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 3);
        setChanged();
    }
}
