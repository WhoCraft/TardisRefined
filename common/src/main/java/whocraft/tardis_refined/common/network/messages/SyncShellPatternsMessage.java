package whocraft.tardis_refined.common.network.messages;

import com.mojang.serialization.codecs.UnboundedMapCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.patterns.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncShellPatternsMessage extends MessageS2C {

    protected Map<ResourceLocation, ShellPatternCollection> patterns = new HashMap<>();

    protected final UnboundedMapCodec<ResourceLocation, ShellPatternCollection> MAPPER = new UnboundedMapCodec<>(ResourceLocation.CODEC, ShellPatternCollection.CODEC);

    public SyncShellPatternsMessage(Map<ResourceLocation, ShellPatternCollection> patterns) {
        this.patterns = patterns;
    }

    public SyncShellPatternsMessage(FriendlyByteBuf buf) {
        this.patterns = MAPPER.parse(NbtOps.INSTANCE, buf.readNbt()).result().orElse(ShellPatterns.registerDefaultPatterns());
    }

    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_SHELL_PATTERNS;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt((CompoundTag)(MAPPER.encodeStart(NbtOps.INSTANCE, this.patterns).result().orElse(new CompoundTag())));
    }

    @Override
    public void handle(MessageContext context) {
        ShellPatterns.getRegistry().clear();
        for (Map.Entry<ResourceLocation, ShellPatternCollection> entry : this.patterns.entrySet()) {
            ShellPatterns.getRegistry().put(entry.getKey(), entry.getValue());
        }
    }
}