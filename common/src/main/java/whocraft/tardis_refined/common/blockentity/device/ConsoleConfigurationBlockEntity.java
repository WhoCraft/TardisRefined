package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

public class ConsoleConfigurationBlockEntity extends BlockEntity {

    private ResourceLocation consoleTheme;

    public ConsoleConfigurationBlockEntity( BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.CONSOLE_CONFIGURATION.get(), blockPos, blockState);
        this.consoleTheme = ConsoleTheme.FACTORY.getId();
    }

    public ResourceLocation theme(){
        if (this.consoleTheme == null){
            this.consoleTheme = ConsoleTheme.FACTORY.getId();
        }
        return this.consoleTheme;
    }

    public void setConsoleTheme(ResourceLocation themeId){
        this.consoleTheme = themeId;
        this.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        if (this.consoleTheme != null) {
            compoundTag.putString(NbtConstants.THEME, this.consoleTheme.toString());
        }

    }

    @Override
    public void load(CompoundTag tag) {

        if (tag.contains(NbtConstants.THEME)) {
            ResourceLocation themeId = new ResourceLocation(tag.getString(NbtConstants.PATTERN));
            this.consoleTheme = themeId;
        }

        if (this.consoleTheme == null){
            this.consoleTheme = this.theme();
        }

        super.load(tag);
    }
}
