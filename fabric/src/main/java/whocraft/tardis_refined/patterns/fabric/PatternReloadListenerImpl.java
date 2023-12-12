package whocraft.tardis_refined.patterns.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import whocraft.tardis_refined.common.network.NetworkManager;
import whocraft.tardis_refined.patterns.BasePattern;
import whocraft.tardis_refined.patterns.PatternCollection;
import whocraft.tardis_refined.patterns.PatternReloadListener;

import java.util.List;
import java.util.function.Function;

public class PatternReloadListenerImpl {

    public static <P extends PatternCollection, B extends BasePattern> PatternReloadListener<P, B> createListener(String folderName, Codec<P> codec, final Function<List<P>, List<B>> merger) {
        return new Impl(folderName, codec, merger);
    }

    public static class Impl<T extends PatternCollection, B extends BasePattern> extends PatternReloadListener<T, B> {
        public Impl(String folderName, Codec<T> codec, final Function<List<T>, List<B>> merger) {
            super(folderName, codec, merger);
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
