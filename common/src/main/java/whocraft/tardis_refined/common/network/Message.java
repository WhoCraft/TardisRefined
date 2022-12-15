package whocraft.tardis_refined.common.network;


import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nonnull;

public abstract class Message {

    @Nonnull
    public abstract MessageType getType();

    public abstract void toBytes(FriendlyByteBuf buf);

    public abstract void handle(MessageContext context);

}
