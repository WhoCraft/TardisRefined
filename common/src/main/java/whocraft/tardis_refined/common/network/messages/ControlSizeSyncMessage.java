package whocraft.tardis_refined.common.network.messages;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.network.handler.ClientPacketHandler;

public class ControlSizeSyncMessage extends MessageS2C {

    public final float width;
    public final float height;
    public final int entityID;

    public ControlSizeSyncMessage(int entityID, float width, float height){
        this.entityID = entityID;
        this.width = width;
        this.height = height;
    }

    public ControlSizeSyncMessage(FriendlyByteBuf buffer) {
        this.entityID = buffer.readInt();
        float width = buffer.readFloat();
        float height = buffer.readFloat();
        this.width = width;
        this.height = height;
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_CONTROL_SIZE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeFloat(this.width);
        buf.writeFloat(this.height);

    }

    @Override
    public void handle(MessageContext context) {
        ClientPacketHandler.handleControlSizePacket(this.entityID, this.width, this.height);
    }
}
