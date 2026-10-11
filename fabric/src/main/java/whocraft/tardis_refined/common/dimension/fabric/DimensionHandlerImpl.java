package whocraft.tardis_refined.common.dimension.fabric;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.LevelStorageSource;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.network.messages.sync.S2CSyncLevelList;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals26.ImmersivePortals;
import whocraft.tardis_refined.mixin.MappedRegistryAccessor;

import java.util.concurrent.Executor;
import java.util.function.BiFunction;

import static whocraft.tardis_refined.common.dimension.DimensionHandler.LEVELS;
import static whocraft.tardis_refined.common.dimension.DimensionHandler.addDimension;
import static whocraft.tardis_refined.common.util.Platform.getServer;

public class DimensionHandlerImpl {

    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {

        if (ModCompatChecker.immersivePortals()) {
            return ImmersivePortals.createDimension(level, id);
        }

        BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory = DimensionHandler::formLevelStem;

        MinecraftServer server = getServer();
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);

        final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registries.LEVEL_STEM, id.identifier());

        LevelStem dimension = dimensionFactory.apply(server, dimensionKey);

        Executor executor = server.executor;
        LevelStorageSource.LevelStorageAccess levelSave = DimensionHandler.getStorage();

        DerivedLevelData derivedWorldInfo = new DerivedLevelData(server.getWorldData(), server.getWorldData().overworldData());

        //Actually register our dimension
        Registry<LevelStem> dimensionRegistry = server.registryAccess().lookupOrThrow(Registries.LEVEL_STEM);
        if (dimensionRegistry instanceof MappedRegistry<LevelStem> writableRegistry) {
            MappedRegistryAccessor accessor = (MappedRegistryAccessor) writableRegistry;
            // The registry was frozen (and its tag set bound) during normal server
            // startup. Replacing the tag set with MappedRegistry's unbound sentinel
            // is required before thawing it; otherwise the next RegistryAccess.freeze()
            // during client registry negotiation fails with "Tags already present".
            if (writableRegistry.containsKey(dimensionKey)) {
                dimension = writableRegistry.getValue(dimensionKey);
            } else {
                accessor.setFrozen(false); // Thaw only for the registration itself.
                try {
                    writableRegistry.register(dimensionKey, dimension, RegistrationInfo.BUILT_IN);
                } finally {
                    // RegistryAccess.freeze() must see this registry as frozen. Leaving it
                    // unfrozen makes 26.3 attempt to bind tags a second time during sync.
                    accessor.setFrozen(true);
                }
            }
        } else {
            throw new IllegalStateException(String.format("Unable to register dimension %s -- dimension registry not writable", dimensionKey.identifier()));
        }

        // now we have everything we need to create the world instance
        ServerLevel newLevel = new ServerLevel(
                server,
                executor,
                levelSave,
                derivedWorldInfo,
                id,
                dimension,
                false,
                overworld.getSeed(),
                ImmutableList.of(),
                false);

        addDimension(newLevel.dimension());

        server.levels.put(id, newLevel);

        ServerLevelEvents.LOAD.invoker().onLevelLoad(server, newLevel);

        new S2CSyncLevelList(newLevel.dimension(), true).sendToAll();


        return newLevel;
    }

    public static void clear() {
        LEVELS.clear();
    }
}
