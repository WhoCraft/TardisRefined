package whocraft.tardis_refined.common.network.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.NetworkManager;
import whocraft.tardis_refined.common.util.Platform;

public class NetworkManagerImpl extends NetworkManager {

    public NetworkManagerImpl(ResourceLocation channelName) {
        super(channelName);
        ServerPlayNetworking.registerGlobalReceiver(channelName, (server, player, handler, buf, responseSender) -> {
            var msgId = buf.readUtf();

            if (!this.toServer.containsKey(msgId)) {
                TardisRefined.LOGGER.error("Unknown message id received on server: " + msgId);
                return;
            }

            MessageType type = this.toServer.get(msgId);
            MessageC2S message = (MessageC2S) type.getDecoder().decode(buf);
            server.execute(() -> message.handle(() -> player));
        });

        if (Platform.isClient()) {
            this.registerClient();
        }
    }

    public static NetworkManager create(ResourceLocation channelName) {
        return new NetworkManagerImpl(channelName);
    }

    @Environment(EnvType.CLIENT)
    private void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(channelName, (client, handler, buf, responseSender) -> {
            var msgId = buf.readUtf();

            if (!this.toClient.containsKey(msgId)) {
                TardisRefined.LOGGER.error("Unknown message id received on client: " + msgId);
                return;
            }

            MessageType type = this.toClient.get(msgId);
            MessageS2C message = (MessageS2C) type.getDecoder().decode(buf);
            client.execute(() -> message.handle(() -> null));
        });
    }

    @Override
    public void sendToServer(MessageC2S message) {
        if (!this.toServer.containsValue(message.getType())) {
            TardisRefined.LOGGER.error("Message type not registered: " + message.getType().getId());
            return;
        }

        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUtf(message.getType().getId());
        message.toBytes(buf);
        ClientPlayNetworking.send(this.channelName, buf);
    }

    @Override
    public void sendToPlayer(ServerPlayer player, MessageS2C message) {
        if (!this.toClient.containsValue(message.getType())) {
            TardisRefined.LOGGER.error("Message type not registered: " + message.getType().getId());
            return;
        }

        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUtf(message.getType().getId());
        message.toBytes(buf);
        ServerPlayNetworking.send(player, this.channelName, buf);
    }

    @Override
    public void sendToTracking(Entity entity, MessageS2C message) {
        PlayerLookup.tracking(entity).stream().forEach(player -> {
            this.sendToPlayer(player, message);
        });
    }

    @Override
    public void sendToTracking(BlockEntity blockEntity, MessageS2C message) {
        PlayerLookup.tracking(blockEntity).stream().forEach(player -> {
            this.sendToPlayer(player, message);
        });
    }


}