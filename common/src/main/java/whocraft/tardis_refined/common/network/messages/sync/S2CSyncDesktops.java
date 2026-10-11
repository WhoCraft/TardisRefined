package whocraft.tardis_refined.common.network.messages.sync;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

import java.util.HashMap;
import java.util.Map;

public class S2CSyncDesktops extends MessageS2C {

    //We use an unboundedMapCodec. However it is limited in that it can only parse objects whose keys can be serialised to a string, such as Identifier
    //E.g. If you used an int as a key, the unboundedMapCodec will not parse it and will error.
    private static final Codec<Map<Identifier, DesktopTheme>> MAPPER = Codec.unboundedMap(Identifier.CODEC, DesktopTheme.getCodec());
    private Map<Identifier, DesktopTheme> desktops = new HashMap<>();


    public S2CSyncDesktops(Map<Identifier, DesktopTheme> desktops) {
        this.desktops = desktops;
    }

    public S2CSyncDesktops(FriendlyByteBuf buf) {
        //Parse our Map Codec and send the nbt data over. If there's any errors, populate with default Tardis Refined console rooms
        this.desktops = MAPPER.parse(NbtOps.INSTANCE, buf.readNbt()).result().orElse(TardisDesktops.registerDefaultDesktops());
    }

    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_DESKTOPS;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, this.desktops).result().orElse(new CompoundTag())));
    }

    @Override
    public void handle(MessageContext context) {
        TardisDesktops.getRegistry().clear();
        for (Map.Entry<Identifier, DesktopTheme> entry : this.desktops.entrySet()) {
            TardisDesktops.getRegistry().put(entry.getKey(), entry.getValue());
        }
    }
}
