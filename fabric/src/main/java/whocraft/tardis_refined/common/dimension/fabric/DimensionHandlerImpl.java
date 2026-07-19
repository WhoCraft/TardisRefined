package whocraft.tardis_refined.common.dimension.fabric;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import whocraft.tardis_refined.mixin.MappedRegistryAccessor;

import static whocraft.tardis_refined.common.dimension.DimensionHandler.LEVELS;

public class DimensionHandlerImpl {

    public static <T> void unfreezeRegistry(MappedRegistry<T> registry) {
        MappedRegistryAccessor accessor = (MappedRegistryAccessor) registry;
        accessor.setFrozen(false);
    }

    public static void onDimensionLoaded(ServerLevel level) {
        ServerWorldEvents.LOAD.invoker().onWorldLoad(level.getServer(), level);
    }

    public static void onDimensionUnloaded(ServerLevel level) {
        ServerWorldEvents.UNLOAD.invoker().onWorldUnload(level.getServer(), level);
    }

    public static void clear() {
        LEVELS.clear();
    }
}
