package whocraft.tardis_refined.common.util.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import whocraft.tardis_refined.common.network.NetworkManager;
import whocraft.tardis_refined.common.util.MergeableCodecJsonReloadListener;

import java.util.List;
import java.util.function.Function;

public class MergeableCodecJsonReloadListenerImpl {

    public static <RAW, PROCESSED> MergeableCodecJsonReloadListener<RAW, PROCESSED> create(String folderName, Codec<RAW> codec, final Function<List<RAW>, PROCESSED> merger) {
        return new Impl<RAW, PROCESSED>(folderName, codec, merger);
    }

    public static class Impl<RAW, PROCESSED> extends MergeableCodecJsonReloadListener<RAW, PROCESSED> {
        public Impl(String folderName, Codec<RAW> codec, final Function<List<RAW>, PROCESSED> merger) {
            super(folderName, codec, merger);
        }

        @Override
        public MergeableCodecJsonReloadListener setSyncPacket(NetworkManager networkManager, Function packetFactory) {
            //Use ServerLifecycleEvents#SYNC_DATA_PACK_CONTENTS rather than ServerPlayConnectionEvents#JOIN as this event happens for both player logic and after server resource reload finishes
            ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
                this.handleSyncPacket(player, networkManager, packetFactory);
            });
            return this;
        }
    }

}