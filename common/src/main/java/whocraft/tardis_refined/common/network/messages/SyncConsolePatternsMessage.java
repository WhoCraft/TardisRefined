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
import whocraft.tardis_refined.patterns.ConsolePatternCollection;
import whocraft.tardis_refined.patterns.ConsolePatterns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SyncConsolePatternsMessage extends MessageS2C{

    protected Map<ResourceLocation, List<ConsolePattern>> patterns = new HashMap<>();

    protected final UnboundedMapCodec<ResourceLocation, List<ConsolePattern>> MAPPER = Codec.unboundedMap(ResourceLocation.CODEC, ConsolePattern.CODEC.listOf().xmap(List::copyOf, List::copyOf));

    public SyncConsolePatternsMessage(Map<ResourceLocation, List<ConsolePattern>> patterns) {
        this.patterns = patterns;
    }

    public SyncConsolePatternsMessage(FriendlyByteBuf buf) {
        this.patterns = MAPPER.parse(NbtOps.INSTANCE, buf.readNbt()).result().orElse(ConsolePatterns.registerDefaultPatterns());
    }

    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_CONSOLE_PATTERNS;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(MAPPER.encodeStart(NbtOps.INSTANCE, this.patterns).result().orElse(new CompoundTag()));
    }

    @Override
    public void handle(MessageContext context) {
        ConsolePatterns.getRegistry().clear();
        for (Map.Entry<ResourceLocation, List<ConsolePattern>> entry : this.patterns.entrySet()) {
            ConsolePatterns.getRegistry().put(entry.getKey(), entry.getValue());
        }
    }

}