package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class ConsoleConfigurationBlockEntity extends BlockEntity {

    private Identifier consoleTheme;
    private Identifier consolePattern;

    public ConsoleConfigurationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.CONSOLE_CONFIGURATION.get(), blockPos, blockState);
        this.consoleTheme = ConsoleTheme.FACTORY.getId();
    }

    public Identifier theme() {
        if (this.consoleTheme == null) {
            this.consoleTheme = ConsoleTheme.FACTORY.getId();
        }
        return this.consoleTheme;
    }

    public void setConsoleTheme(Identifier themeId) {
        this.consoleTheme = themeId;
        var patterns = ConsolePatterns.getPatternsForTheme(themeId);
        if (patterns != null && !patterns.isEmpty()
                && patterns.stream().noneMatch(pattern -> pattern.id().equals(this.consolePattern))) {
            this.consolePattern = patterns.get(0).id();
        }
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        this.setChanged();
    }

    public ConsolePattern pattern() {
        var patterns = ConsolePatterns.getPatternsForTheme(this.theme());
        if (patterns == null || patterns.isEmpty()) return ConsolePatterns.DEFAULT;
        if (this.consolePattern != null) {
            for (ConsolePattern pattern : patterns) {
                if (pattern.id().equals(this.consolePattern)) return pattern;
            }
        }
        return patterns.get(0);
    }

    public void setConsolePattern(ConsolePattern pattern) {
        if (pattern == null) return;
        this.consolePattern = pattern.id();
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput compoundTag) {
        super.saveAdditional(compoundTag);

        if (this.consoleTheme != null) {
            compoundTag.putString(NbtConstants.THEME, theme().toString());
        }
        if (this.consolePattern != null) {
            compoundTag.putString(NbtConstants.PATTERN, this.consolePattern.toString());
        }

    }

    @Override
    protected void loadAdditional(ValueInput tag) {

        if (tag.getString(NbtConstants.THEME).isPresent()) {
            Identifier themeId = Identifier.parse(tag.getStringOr(NbtConstants.THEME, ""));
            this.consoleTheme = themeId;
        }

        if (tag.getString(NbtConstants.PATTERN).isPresent()) {
            this.consolePattern = Identifier.parse(tag.getStringOr(NbtConstants.PATTERN, ""));
        }

        if (this.consoleTheme == null) {
            this.consoleTheme = this.theme();
        }

        super.loadAdditional(tag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
