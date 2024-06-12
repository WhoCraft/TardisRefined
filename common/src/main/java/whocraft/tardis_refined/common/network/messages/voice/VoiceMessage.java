package whocraft.tardis_refined.common.network.messages.voice;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.experiment.voice.VoiceActions;

public class VoiceMessage extends MessageC2S{

    private String message;

    public VoiceMessage(String message) {
        this.message = message;
    }

    public VoiceMessage(FriendlyByteBuf buffer) {
        this.message = buffer.readUtf();
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.VOICE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.message);
    }

    @Override
    public void handle(MessageContext context) {
        VoiceActions.processCommand(this.message, context.getPlayer());
    }
}
