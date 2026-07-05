package whocraft.tardis_refined.common.network.messages.screens;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.network.NetworkManager;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

public class MonitorPositionDataMessage implements CustomPacketPayload, NetworkManager.Handler<MonitorPositionDataMessage> {

    public static final CustomPacketPayload.Type<MonitorPositionDataMessage> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "monitor_position_data"));
    public static final StreamCodec<ByteBuf, MonitorPositionDataMessage> STREAM_CODEC = StreamCodec.composite(
            TardisNavLocation.STREAM_CODEC, msg -> msg.location,
            MonitorPositionDataMessage::new
    );

    public static TardisNavLocation lastLocation = TardisNavLocation.ORIGIN;

    TardisNavLocation location;

    public MonitorPositionDataMessage(TardisNavLocation location) {
        this.location = location;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void receive(MonitorPositionDataMessage value, NetworkManager.Context context) {
        lastLocation = location;
    }
}
