package whocraft.tardis_refined.common.dimension.forge;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.apache.commons.lang3.Validate;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.network.messages.SyncLevelListMessage;
import whocraft.tardis_refined.common.util.Platform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

import static whocraft.tardis_refined.common.util.forge.PlatformImpl.getServer;

public class DimensionHandlerImpl {


    public static ArrayList<ResourceKey<Level>> LEVELS = new ArrayList<>();

    public static void addDimension(ResourceKey<Level> resourceKey){
        LEVELS.add(resourceKey);
        writeLevels();
    }


    /*Only present in Forge so that worlds can be opened in Fabric*/
    private static void writeLevels() {
        File file = new File(getWorldSavingDirectory().toFile(), TardisRefined.MODID + "_tardis_info.json");
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("tardis_dimensions", new JsonPrimitive(TardisRefined.GSON.toJson(LEVELS)));

        TardisRefined.LOGGER.info("Writing to: {}", file.getAbsolutePath());

        try (FileWriter writer = new FileWriter(file)) {
            TardisRefined.GSON.toJson(jsonObject, writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getWorldSavingDirectory() {
        MinecraftServer server = getServer();
        return server.storageSource.getWorldDir();
    }


    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {
        BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory = DimensionHandler::formLevelStem;

        MinecraftServer server = getServer();
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);

        final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, id.location());

        LevelStem dimension = dimensionFactory.apply(server, dimensionKey);

        ChunkProgressListener chunkListener = server.progressListenerFactory.create(11);
        Executor executor = server.executor;
        LevelStorageSource.LevelStorageAccess levelSave = server.storageSource;


        WorldData serverConfig = server.getWorldData();
        WorldGenSettings worldGenSettings = serverConfig.worldGenSettings();
        DerivedLevelData derivedWorldInfo = new DerivedLevelData(serverConfig, serverConfig.overworldData());

        Registry<LevelStem> dimensionRegistry = worldGenSettings.dimensions();
        if (dimensionRegistry instanceof WritableRegistry<LevelStem> writableRegistry) {
            writableRegistry.register(dimensionKey, dimension, Lifecycle.stable()); //set to stable to reduce issues that come with the experimental marker
        } else {
            throw new IllegalStateException("Unable to register dimension '" + dimensionKey.location() + "'! Registry not writable!");
        }

        // now we have everything we need to create the world instance
        ServerLevel newLevel = new ServerLevel(
                server,
                executor,
                levelSave,
                derivedWorldInfo,
                id,
                dimension,
                chunkListener,
                false, // boolean: is-debug-world
                BiomeManager.obfuscateSeed(worldGenSettings.seed()),
                ImmutableList.of(), // "special spawn list"
                // phantoms, raiders, travelling traders, cats are overworld special spawns
                // the dimension loader is hardcoded to initialize preexisting non-overworld worlds with no special spawn lists
                // so this can probably be left empty for best results and spawns should be handled via other means
                false); // "tick time", true for overworld, always false for everything else

        addDimension(newLevel.dimension());

        overworld.getWorldBorder().addListener(new BorderChangeListener.DelegateBorderChangeListener(newLevel.getWorldBorder()));

        server.levels.put(id, newLevel);

        server.markWorldsDirty();

        new SyncLevelListMessage(newLevel.dimension(), true).sendToAll();

        BlockPos blockpos = new BlockPos(0, 0, 0);
        chunkListener.updateSpawnPos(new ChunkPos(blockpos));
        ServerChunkCache serverchunkcache = newLevel.getChunkSource();
        serverchunkcache.getLightEngine().setTaskPerBatch(500);
        serverchunkcache.addRegionTicket(TicketType.START, new ChunkPos(blockpos), 11, Unit.INSTANCE);

        return newLevel;
    }

}
