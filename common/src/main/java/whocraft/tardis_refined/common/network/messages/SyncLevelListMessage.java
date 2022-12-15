package whocraft.tardis_refined.common.network.messages;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.network.handler.ClientPacketHandler;

public class SyncLevelListMessage extends MessageS2C {

    public ResourceKey<Level> level;
    public boolean add;

    public SyncLevelListMessage(ResourceKey<Level> level, boolean add) {
        this.level = level;
        this.add = add;
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_LEVELS;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceKey(this.level);
        buf.writeBoolean(this.add);
    }

    public SyncLevelListMessage(FriendlyByteBuf buf) {
        this.level = buf.readResourceKey(Registry.DIMENSION_REGISTRY);
        this.add = buf.readBoolean();
    }

    @Override
    public void handle(MessageContext context) {
        ClientPacketHandler.handleDimSyncPacket(this.level, this.add);
    }
}
