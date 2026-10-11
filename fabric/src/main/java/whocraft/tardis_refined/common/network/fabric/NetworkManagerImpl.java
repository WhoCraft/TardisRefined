package whocraft.tardis_refined.common.network.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.NetworkManager;
import whocraft.tardis_refined.common.util.Platform;

public class NetworkManagerImpl extends NetworkManager {

    public NetworkManagerImpl(Identifier channelName) {
        super(channelName);
        PayloadTypeRegistry.serverboundPlay().register(MessagePayload.TYPE, MessagePayload.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(MessagePayload.TYPE, MessagePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MessagePayload.TYPE, (payload, context) -> {
            if (!payload.channel().equals(this.channelName)) return;
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(payload.data()));
            var msgId = buf.readUtf();

            if (!this.toServer.containsKey(msgId)) {
                TardisRefined.LOGGER.error("Unknown message id received on server: " + msgId);
                return;
            }

            MessageType type = this.toServer.get(msgId);
            MessageC2S message = (MessageC2S) type.getDecoder().decode(buf);
            context.server().execute(() -> message.handle(context::player));
        });

        if (Platform.isClient()) {
            this.registerClient();
        }
    }

    public static NetworkManager create(Identifier channelName) {
        return new NetworkManagerImpl(channelName);
    }

    @Environment(EnvType.CLIENT)
    private void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(MessagePayload.TYPE, (payload, context) -> {
            if (!payload.channel().equals(this.channelName)) return;
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(payload.data()));
            var msgId = buf.readUtf();

            if (!this.toClient.containsKey(msgId)) {
                TardisRefined.LOGGER.error("Unknown message id received on client: " + msgId);
                return;
            }

            MessageType type = this.toClient.get(msgId);
            MessageS2C message = (MessageS2C) type.getDecoder().decode(buf);
            context.client().execute(() -> message.handle(() -> null));
        });
    }

    @Override
    public void sendToServer(MessageC2S message) {
        if (!this.toServer.containsValue(message.getType())) {
            TardisRefined.LOGGER.error("Message type not registered: " + message.getType().getId());
            return;
        }

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeUtf(message.getType().getId());
        message.toBytes(buf);
        ClientPlayNetworking.send(new MessagePayload(this.channelName, ByteBufUtil.getBytes(buf)));
    }

    @Override
    public void sendToPlayer(ServerPlayer player, MessageS2C message) {
        if (!this.toClient.containsValue(message.getType())) {
            TardisRefined.LOGGER.error("Message type not registered: " + message.getType().getId());
            return;
        }

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeUtf(message.getType().getId());
        message.toBytes(buf);
        ServerPlayNetworking.send(player, new MessagePayload(this.channelName, ByteBufUtil.getBytes(buf)));
    }

    @Override
    public void sendToTrackingAndSelf(ServerPlayer player, MessageS2C message) {
        this.sendToTracking(player, message);
        this.sendToPlayer(player, message);
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


    private record MessagePayload(Identifier channel, byte[] data) implements CustomPacketPayload {
        private static final Type<MessagePayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "network_message"));
        private static final StreamCodec<RegistryFriendlyByteBuf, MessagePayload> CODEC = new StreamCodec<>() {
            @Override
            public MessagePayload decode(RegistryFriendlyByteBuf buffer) {
                return new MessagePayload(Identifier.parse(buffer.readUtf()), buffer.readByteArray());
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, MessagePayload payload) {
                buffer.writeUtf(payload.channel().toString());
                buffer.writeByteArray(payload.data());
            }
        };

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
