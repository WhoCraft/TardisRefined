package whocraft.tardis_refined.common.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.Unit;;
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
import whocraft.tardis_refined.common.network.messages.SyncLevelListMessage;
import whocraft.tardis_refined.common.world.chunk.TardisChunkGenerator;
import whocraft.tardis_refined.registry.DimensionTypes;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

/*
* Majority of this code is sourced from Commoble's Hyberbox with permission.
* You can view their project here: https://github.com/Commoble/hyperbox
* */

public class DimensionHandler {

    public static ServerLevel getOrCreateInterior(Level interactionLevel, String id) {

        if (interactionLevel instanceof ServerLevel serverLevel) {
           ResourceKey<Level> levelResourceKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(TardisRefined.MODID, id));
           Map<ResourceKey<Level>, ServerLevel> levelMap = serverLevel.getServer().levels;
           @Nullable ServerLevel existingLevel = levelMap.get(levelResourceKey);

           if (existingLevel != null) {
               return existingLevel;
           }

           ServerLevel newLevel = createDimension(interactionLevel, levelResourceKey);
           return newLevel;
        }

        return null;

    }

    private static ServerLevel createDimension(Level level, ResourceKey<Level> id) {
        BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory = DimensionHandler::formLevelStem;

        MinecraftServer server = level.getServer();
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);

        ResourceKey<Level> levelKey = id;
        final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, levelKey.location());

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
                levelKey,
                dimension,
                chunkListener,
                false, // boolean: is-debug-world
                BiomeManager.obfuscateSeed(worldGenSettings.seed()),
                ImmutableList.of(), // "special spawn list"
                // phantoms, raiders, travelling traders, cats are overworld special spawns
                // the dimension loader is hardcoded to initialize preexisting non-overworld worlds with no special spawn lists
                // so this can probably be left empty for best results and spawns should be handled via other means
                false); // "tick time", true for overworld, always false for everything else

        overworld.getWorldBorder().addListener(new BorderChangeListener.DelegateBorderChangeListener(newLevel.getWorldBorder()));

        overrideLevelMapping(server, levelKey, newLevel);

        BlockPos blockpos = level.getSharedSpawnPos();
        chunkListener.updateSpawnPos(new ChunkPos(blockpos));
        ServerChunkCache serverchunkcache = (ServerChunkCache) level.getChunkSource();
        serverchunkcache.getLightEngine().setTaskPerBatch(500);
        serverchunkcache.addRegionTicket(TicketType.START, new ChunkPos(blockpos), 11, Unit.INSTANCE);

        new SyncLevelListMessage(newLevel.dimension(), true).sendToAll((ServerLevel) level);

        newLevel.tick(() -> true);

        return newLevel;
    }

    public static LevelStem formLevelStem(MinecraftServer server, ResourceKey<LevelStem> stem) {
        RegistryAccess access = server.registryAccess();
        return new LevelStem(access.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY).getHolderOrThrow(DimensionTypes.TARDIS),
                new TardisChunkGenerator(access.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY), access.registryOrThrow(Registry.BIOME_REGISTRY)));
    }

    public static void overrideLevelMapping(MinecraftServer server, ResourceKey<Level> levelKey, ServerLevel worldKey) {
        server.levels.put(levelKey, worldKey);
    }

}
