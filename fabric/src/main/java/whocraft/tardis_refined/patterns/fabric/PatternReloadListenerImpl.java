package whocraft.tardis_refined.patterns.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import whocraft.tardis_refined.common.network.NetworkManager;
import whocraft.tardis_refined.patterns.PatternCollection;
import whocraft.tardis_refined.patterns.PatternReloadListener;

import java.util.function.Function;

public class PatternReloadListenerImpl{

    public static <P extends PatternCollection> PatternReloadListener<P> createListener(String folderName, Codec<P> codec) {
        return new Impl(folderName, codec);
    }

    public static class Impl<T extends PatternCollection> extends PatternReloadListener<T> {
        public Impl(String folderName, Codec<T> codec) {
            super(folderName, codec);
        }

        @Override
        public PatternReloadListener setSyncPacket(NetworkManager networkManager, Function packetFactory) {
            //Use ServerLifecycleEvents#SYNC_DATA_PACK_CONTENTS rather than ServerPlayConnectionEvents#JOIN as this event happens for both player logic and after server resource reload finishes
            ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
                this.handleSyncPacket(player, networkManager, packetFactory);
            });
            return this;
        }
    }

}
