package whocraft.tardis_refined.common.network.messages.screens;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.network.NetworkManager;
import whocraft.tardis_refined.common.util.DimensionUtil;

public class C2SMonitorClosed implements CustomPacketPayload, NetworkManager.Handler<C2SMonitorClosed> {

    public static final CustomPacketPayload.Type<C2SMonitorClosed> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "monitor_closed"));

    public static final StreamCodec<ByteBuf, C2SMonitorClosed> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.DIMENSION), msg -> msg.level,
            C2SMonitorClosed::new
    );

    private ResourceKey<Level> level;

    public C2SMonitorClosed(ResourceKey<Level> level) {
        this.level = level;
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void receive(C2SMonitorClosed value, NetworkManager.Context context) {
        TardisLevelOperator.get(DimensionUtil.getLevel(level)).ifPresent(operator -> {
            operator.updatingMonitors.remove(context.getPlayer());
        });
    }
}
