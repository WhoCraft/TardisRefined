package whocraft.tardis_refined.common.dimension.fabric;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
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
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.network.messages.SyncLevelListMessage;
import whocraft.tardis_refined.mixin.fabric.MinecraftServerStorageAccessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

import static whocraft.tardis_refined.common.util.Platform.getServer;

public class DimensionHandlerImpl {


    public static ArrayList<ResourceKey<Level>> LEVELS = new ArrayList<>();

    public static void addDimension(ResourceKey<Level> resourceKey){
        LEVELS.add(resourceKey);
        writeLevels();
    }

    public static void loadLevels(ServerLevel serverLevel){
        File file = new File(getWorldSavingDirectory().toFile(), TardisRefined.MODID + "_tardis_info.json");
        if(!file.exists()) return;

        Reader reader = null;
        try {
            reader = Files.newBufferedReader(file.toPath());

            JsonObject jsonObject = TardisRefined.GSON.fromJson(reader, JsonObject.class);
            for (JsonElement dimension : jsonObject.get("tardis_dimensions").getAsJsonArray()) {
                TardisRefined.LOGGER.info("Attempting to load {}", dimension.getAsString());
                DimensionHandler.getOrCreateInterior(serverLevel, new ResourceLocation(dimension.getAsString()));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void writeLevels() {
        File file = new File(getWorldSavingDirectory().toFile(), TardisRefined.MODID + "_tardis_info.json");
        JsonObject jsonObject = new JsonObject();

        JsonArray dimensions = new JsonArray();
        for (ResourceKey<Level> level : LEVELS) {
            dimensions.add(level.location().toString());
        }

        jsonObject.add("tardis_dimensions", dimensions);

        TardisRefined.LOGGER.info("Writing {} to: {}", dimensions, file.getAbsolutePath());

        try (FileWriter writer = new FileWriter(file)) {
            TardisRefined.GSON.toJson(jsonObject, writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getWorldSavingDirectory() {
        return getStorage().getDimensionPath(Level.OVERWORLD);
    }

    public static LevelStorageSource.LevelStorageAccess getStorage(){
        return ((MinecraftServerStorageAccessor) getServer()).getStorageSource();
    }

    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {

        if(DimensionHandler.hasIP()) {
            return DimensionHandlerIP.createDimension(level, id);
        }

        BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory = DimensionHandler::formLevelStem;

        MinecraftServer server = getServer();
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);

        final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, id.location());

        LevelStem dimension = dimensionFactory.apply(server, dimensionKey);

        ChunkProgressListener chunkListener = server.progressListenerFactory.create(11);
        Executor executor = server.executor;
        LevelStorageSource.LevelStorageAccess levelSave = getStorage();


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


        new SyncLevelListMessage(newLevel.dimension(), true).sendToAll();

        BlockPos blockpos = new BlockPos(0, 0, 0);
        chunkListener.updateSpawnPos(new ChunkPos(blockpos));
        ServerChunkCache serverchunkcache = newLevel.getChunkSource();
        serverchunkcache.getLightEngine().setTaskPerBatch(500);
        serverchunkcache.addRegionTicket(TicketType.START, new ChunkPos(blockpos), 11, Unit.INSTANCE);

        return newLevel;
    }

    public static void clear() {
        LEVELS.clear();
    }
}
