package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
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
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.constants.ResourceConstants;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

import java.util.concurrent.atomic.AtomicBoolean;

public class GlobalShellBlockEntity extends ShellBaseBlockEntity {

    private ResourceLocation shellTheme;

    private ShellPattern basePattern;


    public GlobalShellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_SHELL_BLOCK.get(), blockPos, blockState);
        this.shellTheme = ShellTheme.FACTORY.getId();
        this.basePattern = this.pattern();
    }

    public ResourceLocation theme(){
        if (this.shellTheme == null){
            this.shellTheme = ShellTheme.FACTORY.getId();
        }
        return this.shellTheme;
    }

    public ShellPattern pattern() {
        ShellPattern defaultBasePattern = ShellPatterns.getPatternOrDefault(this.shellTheme, ResourceConstants.DEFAULT_PATTERN_ID);
        return this.basePattern == null ? defaultBasePattern : this.basePattern;
    }

    public GlobalShellBlockEntity setPattern(ShellPattern basePattern) {
        this.basePattern = basePattern;
        return this;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains(NbtConstants.THEME)) {
            ResourceLocation themeId = new ResourceLocation(pTag.getString(NbtConstants.PATTERN));
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

    public boolean onRightClick(BlockState blockState, ItemStack stack) {

        if (blockState.getValue(ShellBaseBlock.REGEN)) {return false;}

        if (getLevel() instanceof ServerLevel serverLevel) {
            ServerLevel interior = DimensionHandler.getExistingLevel(serverLevel, TARDIS_ID.toString());
            if (interior != null) {
                AtomicBoolean valid = new AtomicBoolean();
                TardisLevelOperator.get(interior).ifPresent(cap -> {
                    if (cap.getPilotingManager().isInFlight()) valid.set(false);
                    ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(TardisRefined.MODID, TARDIS_ID.toString()));

                    boolean validKey = KeyItem.keychainContains(stack, dimension);
                    if(validKey) {
                        boolean locked = !cap.getExteriorManager().locked();
                        cap.getExteriorManager().setLocked(locked);
                        cap.getInternalDoor().setLocked(locked);
                        cap.setDoorClosed(true);
                        valid.set(true);
                    }
                    if(cap.getExteriorManager().locked()) valid.set(false);
                    cap.setDoorClosed(blockState.getValue(GlobalShellBlock.OPEN));
                });
                return valid.get();
            }
        }
        return false;
    }

    public void sendUpdates() {
        level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 3);
        setChanged();
    }
}
