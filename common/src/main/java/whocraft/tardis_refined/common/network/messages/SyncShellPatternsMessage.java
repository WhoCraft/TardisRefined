package whocraft.tardis_refined.common.network.messages;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatternCollection;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SyncShellPatternsMessage extends MessageS2C {

    protected Map<ResourceLocation, Set<ShellPattern>> patterns = new HashMap<>();

    protected final UnboundedMapCodec<ResourceLocation, Set<ShellPattern>> MAPPER = Codec.unboundedMap(ResourceLocation.CODEC, ShellPattern.CODEC.listOf().xmap(Set::copyOf, List::copyOf));

    public SyncShellPatternsMessage(Map<ResourceLocation, Set<ShellPattern>> patterns) {
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
        buf.writeNbt(MAPPER.encodeStart(NbtOps.INSTANCE, this.patterns).result().orElse(new CompoundTag()));
    }

    @Override
    public void handle(MessageContext context) {
        ShellPatterns.getRegistry().clear();
        for (Map.Entry<ResourceLocation, Set<ShellPattern>> entry : this.patterns.entrySet()) {
            ShellPatterns.getRegistry().put(entry.getKey(), entry.getValue());
        }
    }
}