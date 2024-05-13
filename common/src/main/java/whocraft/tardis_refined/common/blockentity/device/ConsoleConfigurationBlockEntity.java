package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class ConsoleConfigurationBlockEntity extends BlockEntity {

    private ResourceLocation consoleTheme;

    public ConsoleConfigurationBlockEntity( BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.CONSOLE_CONFIGURATION.get(), blockPos, blockState);
        this.consoleTheme = ConsoleTheme.FACTORY.getId();
    }

    public ResourceLocation theme(){
        if (this.consoleTheme == null){
            this.consoleTheme = ConsoleTheme.FACTORY.getId();
        }
        return this.consoleTheme;
    }

    public void setConsoleTheme(ResourceLocation themeId){
        System.out.println(themeId);
        this.consoleTheme = themeId;
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        this.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        if (this.consoleTheme != null) {
            compoundTag.putString(NbtConstants.THEME, theme().toString());
        }

    }

    @Override
    public void load(CompoundTag tag) {

        if (tag.contains(NbtConstants.THEME)) {
            ResourceLocation themeId = new ResourceLocation(tag.getString(NbtConstants.THEME));
            this.consoleTheme = themeId;
        }

        if (this.consoleTheme == null){
            this.consoleTheme = this.theme();
        }

        super.load(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = this.saveWithFullMetadata();
        this.saveAdditional(tag);
        return tag;
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
