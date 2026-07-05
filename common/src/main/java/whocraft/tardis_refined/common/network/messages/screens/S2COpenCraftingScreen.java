package whocraft.tardis_refined.common.network.messages.screens;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.ScreenHandler;
import whocraft.tardis_refined.common.network.*;

public class S2COpenCraftingScreen implements CustomPacketPayload, NetworkManager.Handler<S2COpenCraftingScreen> {

    public static final CustomPacketPayload.Type<S2COpenCraftingScreen> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "open_crafting_screen"));

    public static final StreamCodec<ByteBuf, S2COpenCraftingScreen> STREAM_CODEC = StreamCodec.unit(new S2COpenCraftingScreen());

    public S2COpenCraftingScreen() {
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void receive(S2COpenCraftingScreen value, NetworkManager.Context context) {
        ScreenHandler.openCraftingScreen();
    }
}
