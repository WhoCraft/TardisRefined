package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.tardis.manager.AestheticHandler;
import whocraft.tardis_refined.common.tardis.manager.TardisExteriorManager;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class GlobalShellBlockEntity extends ShellBaseBlockEntity {

    private ResourceLocation shellTheme;
    private ShellPattern basePattern;


    public GlobalShellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.GLOBAL_SHELL_BLOCK.get(), blockPos, blockState);
        this.shellTheme = ShellTheme.HALF_BAKED.getId();
        this.basePattern = this.pattern();
    }

    public ResourceLocation theme(){
        if (this.shellTheme == null){
            this.shellTheme = ShellTheme.HALF_BAKED.getId();
        }
        return this.shellTheme;
    }

    public void setShellTheme(ResourceLocation theme){
        this.shellTheme = theme;
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    public ShellPattern pattern() {
        return this.basePattern == null ? ShellPatterns.DEFAULT : this.basePattern;
    }

    public GlobalShellBlockEntity setPattern(ShellPattern basePattern) {
        this.basePattern = basePattern;
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        return this;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains(NbtConstants.THEME)) {
            ResourceLocation themeId = new ResourceLocation(pTag.getString(NbtConstants.THEME));
            this.shellTheme = themeId;
        }

        if (pTag.contains(NbtConstants.PATTERN)) {
            if (this.shellTheme != null){
                ResourceLocation currentPattern = new ResourceLocation(pTag.getString(NbtConstants.PATTERN));
                if (ShellPatterns.doesPatternExist(this.shellTheme, currentPattern)) {
                    this.basePattern = ShellPatterns.getPatternOrDefault(this.shellTheme, currentPattern);
                }
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

    public boolean onRightClick(BlockState blockState, ItemStack stack, Level level, BlockPos blockPos, Player player) {

        // We do not want interactions if the TARDIS is in Flight
        if (blockState.getValue(ShellBaseBlock.REGEN)) {
            return false;
        }

        if (level instanceof ServerLevel serverLevel) {
            ServerLevel interior = DimensionHandler.getExistingLevel(serverLevel, this.TARDIS_ID);
            Optional<TardisLevelOperator> tardisLevelOperatorOptional = TardisLevelOperator.get(interior);

            if (tardisLevelOperatorOptional.isPresent()) {
                TardisLevelOperator tardisLevelOperator = tardisLevelOperatorOptional.get();
                TardisPilotingManager tardisPilotingManager = tardisLevelOperator.getPilotingManager();
                AestheticHandler aestheticHandler = tardisLevelOperator.getAestheticHandler();
                TardisExteriorManager exteriorManager = tardisLevelOperator.getExteriorManager();
                TardisInternalDoor internalDoor = tardisLevelOperator.getInternalDoor();

                // We do not want interactions in Flight
                if (tardisPilotingManager.isInFlight()) return false;

                // Shearing the TARDIS
                if (stack.is(Items.SHEARS) && aestheticHandler.getShellTheme() == ShellTheme.HALF_BAKED.getId()) {
                    aestheticHandler.setShellTheme(ShellTheme.FACTORY.getId(), tardisPilotingManager.getCurrentLocation());
                    level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, 1);
                    spawnCoralItems();
                    return true;
                }

                boolean validKey = KeyItem.keychainContains(stack, TARDIS_ID);
                if (validKey) {
                    boolean locked = !exteriorManager.locked();
                    exteriorManager.setLocked(locked);
                    internalDoor.setLocked(locked);
                    tardisLevelOperator.setDoorClosed(locked);
                    return true;
                }

                if(!exteriorManager.locked()){
                    level.setBlock(blockPos, blockState.cycle(GlobalShellBlock.OPEN), Block.UPDATE_ALL);
                    tardisLevelOperator.setDoorClosed(blockState.getValue(GlobalShellBlock.OPEN));
                    return true;
                }
            }
        }
        return false;
    }

    public void sendUpdates() {
        level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 3);
        setChanged();
    }

    public void spawnCoralItems() {
        int numberOfItems = 2 + level.getRandom().nextInt(5);

        for (int i = 0; i < numberOfItems; i++) {
            ItemStack coralItem = new ItemStack(Items.HORN_CORAL_FAN);
            BlockPos currentPos = getBlockPos();
            Containers.dropItemStack(level, currentPos.getX(), currentPos.getY(), currentPos.getZ() , coralItem);
        }
    }
}
