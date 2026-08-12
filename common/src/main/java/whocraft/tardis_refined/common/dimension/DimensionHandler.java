package whocraft.tardis_refined.common.dimension;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Lifecycle;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.Util;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.Unit;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.network.messages.sync.S2CSyncLevelList;
import whocraft.tardis_refined.common.util.DisconnectedPlayerHelper;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlatformWarning;
import whocraft.tardis_refined.common.world.ChunkGenerators;
import whocraft.tardis_refined.common.world.chunk.TardisChunkGenerator;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.mixin.MinecraftServerStorageAccessor;
import whocraft.tardis_refined.registry.TRDimensionTypes;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

/*
 * Majority of this code is sourced from Commoble's Hyberbox with permission.
 * You can view their project here: https://github.com/Commoble/hyperbox
 * */

public class DimensionHandler {

    public static ArrayList<ResourceKey<Level>> LEVELS = new ArrayList<>();

    // Keep track of dimensions that are currently being deleted to prevent things from loading them again.
    public static Set<ResourceKey<Level>> IS_BEING_DELETED = new HashSet<>();

    private static final Set<ResourceKey<Level>> SCHEDULED_DELETIONS = new HashSet<>();
    private static final Set<ResourceKey<Level>> PENDING_FINISHES = new HashSet<>();

    public static Logger LOGGER = LogManager.getLogger("TardisRefined/DimensionHandler");

    public static boolean isDimensionBeingDeleted(ResourceKey<Level> resourceKey) {
        return IS_BEING_DELETED.contains(resourceKey);
    }

    public static void addDimension(MinecraftServer server, ResourceKey<Level> resourceKey) {
        LEVELS.add(resourceKey);
        writeLevels(server);
    }

    public static void removeDimension(MinecraftServer server, ResourceKey<Level> resourceKey) {
        LEVELS.removeIf(x -> x.registry() == resourceKey.registry());
        IS_BEING_DELETED.add(resourceKey);

        writeLevels(server);
    }

    public static Path getWorldSavingDirectory(MinecraftServer server) {
        return getStorage(server).getDimensionPath(Level.OVERWORLD);
    }

    public static LevelStorageSource.LevelStorageAccess getStorage(MinecraftServer server) {
        return ((MinecraftServerStorageAccessor) server).getStorageSource();
    }

    private static void writeLevels(MinecraftServer server) {
        File file = new File(getWorldSavingDirectory(server).toFile(), TardisRefined.MODID + "_tardis_info.json");
        JsonObject jsonObject = new JsonObject();

        JsonArray activeDimensions = new JsonArray();
        for (ResourceKey<Level> level : LEVELS) {
            activeDimensions.add(level.location().toString());
        }

        jsonObject.add("tardis_dimensions", activeDimensions);

        LOGGER.info("Writing dimension data to: {}", file.getAbsolutePath());

        try (FileWriter writer = new FileWriter(file)) {
            TardisRefined.GSON.toJson(jsonObject, writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ServerLevel getOrCreateInterior(Level interactionLevel, ResourceLocation resourceLocation) {

        ResourceKey<Level> levelResourceKey = ResourceKey.create(Registries.DIMENSION, resourceLocation);

        if (isDimensionBeingDeleted(levelResourceKey)) {
            return null;
        }

        if (ModCompatChecker.immersivePortals() && TRConfig.SERVER.IP_DIMENSION_ADDER.get()) {
            return ImmersivePortals.createDimension(interactionLevel, levelResourceKey);
        }

        if (interactionLevel instanceof ServerLevel serverLevel) {
            ServerLevel existingLevel = getExistingLevel(serverLevel, levelResourceKey);

            if (existingLevel != null) {
                return existingLevel;
            }

            return createDimension(interactionLevel, levelResourceKey);
        }

        return null;

    }

    public static void finishDeletion(MinecraftServer server, ResourceKey<Level> id, boolean isShutdown) {
        Util.ioPool().execute(() -> {
            try {
                FileUtils.deleteDirectory(getStorage(server).getDimensionPath(id).toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            server.execute(() -> {
                IS_BEING_DELETED.remove(id);
                // Fallback just in case.
                DisconnectedPlayerHelper.forAllDisconnectedPlayers(server, id, data -> {
                    DisconnectedPlayerHelper.moveToSpawn(data, server, isShutdown);
                    return true;
                });
                if (ModCompatChecker.immersivePortals()) {
                    ImmersivePortals.onDimensionsModified(server);
                }
            });
        });
    }

    public static void loadLevels(ServerLevel serverLevel) {
        File file = new File(getWorldSavingDirectory(serverLevel.getServer()).toFile(), TardisRefined.MODID + "_tardis_info.json");
        if (!file.exists()) return;

        Reader reader = null;
        try {
            reader = Files.newBufferedReader(file.toPath());

            JsonObject jsonObject = TardisRefined.GSON.fromJson(reader, JsonObject.class);
            for (JsonElement dimension : jsonObject.get("tardis_dimensions").getAsJsonArray()) {
                LOGGER.info("Attempting to load {}", dimension.getAsString());
                ResourceLocation id = new ResourceLocation(dimension.getAsString());
                ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, id);
                if (getExistingLevel(serverLevel, levelKey) == null) {
                    LOGGER.warn("Level {} not found! Creating new level instance", dimension.getAsString());
                    if (DimensionHandler.getOrCreateInterior(serverLevel, id) != null)
                        LOGGER.warn("Successfully created and loaded new level {}", dimension.getAsString());
                } else {
                    LOGGER.info("Successfully loaded existing level {}", dimension.getAsString());
                    LEVELS.add(levelKey);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @ExpectPlatform
    protected static <T> void unfreezeRegistry(MappedRegistry<T> registry) {
        throw new RuntimeException(PlatformWarning.addWarning(DimensionHandler.class));
    }

    @ExpectPlatform
    protected static void onDimensionLoaded(ServerLevel level) {
        throw new RuntimeException(PlatformWarning.addWarning(DimensionHandler.class));
    }

    @ExpectPlatform
    protected static void onDimensionUnloaded(ServerLevel level) {
        throw new RuntimeException(PlatformWarning.addWarning(DimensionHandler.class));
    }

    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {
        if (ModCompatChecker.immersivePortals() && TRConfig.SERVER.IP_DIMENSION_ADDER.get()) {
            return ImmersivePortals.createDimension(level, id);
        }

        BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory = DimensionHandler::formLevelStem;

        MinecraftServer server = level.getServer();
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);

        final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registries.LEVEL_STEM, id.location());

        LevelStem dimension = dimensionFactory.apply(server, dimensionKey);

        ChunkProgressListener chunkListener = server.progressListenerFactory.create(11);
        Executor executor = server.executor;
        LevelStorageSource.LevelStorageAccess levelSave = server.storageSource;


        WorldData serverConfig = server.getWorldData();
        DerivedLevelData derivedWorldInfo = new DerivedLevelData(serverConfig, serverConfig.overworldData());

        //Actually register our dimension
        Registry<LevelStem> dimensionRegistry = server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);
        if (dimensionRegistry instanceof MappedRegistry<LevelStem> writableRegistry) {
            unfreezeRegistry(writableRegistry); //Must unfreeze registry to allow our dimension to persist.
            writableRegistry.register(dimensionKey, dimension, Lifecycle.stable());
        } else {
            throw new IllegalStateException(String.format("Unable to register dimension %s -- dimension registry not writable", dimensionKey.location()));
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
                BiomeManager.obfuscateSeed(serverConfig.worldGenOptions().seed()),
                ImmutableList.of(), // "special spawn list"
                // phantoms, raiders, travelling traders, cats are overworld special spawns
                // the dimension loader is hardcoded to initialize preexisting non-overworld worlds with no special spawn lists
                // so this can probably be left empty for best results and spawns should be handled via other means
                false, // "tick time", true for overworld, always false for everything else
                new RandomSequences(BiomeManager.obfuscateSeed(serverConfig.worldGenOptions().seed())));

        DimensionHandler.addDimension(newLevel.getServer(), newLevel.dimension());

        overworld.getWorldBorder().addListener(new BorderChangeListener.DelegateBorderChangeListener(newLevel.getWorldBorder()));

        server.levels.put(id, newLevel);

        onDimensionLoaded(newLevel);

        new S2CSyncLevelList(newLevel.dimension(), true).sendToAll();

        BlockPos blockPos = new BlockPos(0, 0, 0);
        ChunkPos chunkPos = new ChunkPos(blockPos);
        chunkListener.updateSpawnPos(chunkPos);
        ServerChunkCache serverchunkcache = newLevel.getChunkSource();
        serverchunkcache.getLightEngine().checkBlock(blockPos); //Runs lighting update
        serverchunkcache.addRegionTicket(TicketType.START, chunkPos, 11, Unit.INSTANCE);
        server.tell(server.wrapRunnable(chunkListener::stop));

        if (ModCompatChecker.immersivePortals()) {
            ImmersivePortals.onDimensionsModified(server);
        }

        return newLevel;
    }

    public static void deleteDimension(ResourceKey<Level> id) {
        if (DimensionHandler.isDimensionBeingDeleted(id)) return;
        SCHEDULED_DELETIONS.add(id);
    }

    private static void performDelete(MinecraftServer server, boolean isShutdown) {
        SCHEDULED_DELETIONS.forEach(dim -> performDelete(server, dim, isShutdown));
        SCHEDULED_DELETIONS.clear();
    }

    public static void tick(MinecraftServer server) {
        if (TRConfig.SERVER.DIMENSION_DELETE_MODE.get() == TRConfig.Server.DeleteMode.IMMEDIATE) {
            performDelete(server, false);
        }
    }

    public static void onServerStopping(MinecraftServer server) {
        if (TRConfig.SERVER.DIMENSION_DELETE_MODE.get() == TRConfig.Server.DeleteMode.NEXT_SHUTDOWN) {
            performDelete(server, true);
        }
        LEVELS.clear();
    }

    public static void onServerStopped(MinecraftServer server) {
        if (TRConfig.SERVER.DIMENSION_DELETE_MODE.get() == TRConfig.Server.DeleteMode.NEXT_SHUTDOWN) {
            PENDING_FINISHES.forEach(dim -> finishDeletion(server, dim, true));
            PENDING_FINISHES.clear();
        }
    }

    private static void performDelete(MinecraftServer server, ResourceKey<Level> id, boolean isShutdown) {
        try {
            if (ModCompatChecker.immersivePortals() && TRConfig.SERVER.IP_DIMENSION_REMOVER.get() && !isShutdown) {
                ImmersivePortals.deleteDimension(id);
                return;
            }

            if (id == ServerLevel.OVERWORLD) return;
            var world = server.getLevel(id);
            if (world == null) return;
            if (DimensionHandler.isDimensionBeingDeleted(id)) return;

            world.noSave = true;
            DimensionHandler.removeDimension(server, id);

            //Actually unregister our dimension
            LayeredRegistryAccess<RegistryLayer> registries = server.registries();
            Registry<LevelStem> oldDimensions = registries.compositeAccess().registryOrThrow(Registries.LEVEL_STEM);
            Registry<LevelStem> newDimensions = new MappedRegistry<>(oldDimensions.key(), oldDimensions.registryLifecycle());
            var stemKey = Registries.levelToLevelStem(id);
            for (var dim : oldDimensions.entrySet()) {
                if (dim.getKey() != stemKey) {
                    Registry.register(newDimensions, dim.getKey(), dim.getValue());
                }
            }
            newDimensions.freeze();
            List<Registry<?>> list = new ArrayList<>();
            list.add(newDimensions);
            registries.getLayer(RegistryLayer.DIMENSIONS).registries().forEach(reg -> {
                if (reg.key() != newDimensions.key()) {
                    list.add(reg.value());
                }
            });
            server.registries = registries.replaceFrom(
                    RegistryLayer.DIMENSIONS, new RegistryAccess.ImmutableRegistryAccess(list).freeze()
            );

            new S2CSyncLevelList(world.dimension(), false).sendToAll();

            if (isShutdown) {
                // When shutdown we can't access the level, so try once before the actual shutdown begins.
                // Must happen early to prevent issues.
                DisconnectedPlayerHelper.forAllDisconnectedPlayers(server, id, data -> {
                    DisconnectedPlayerHelper.moveToSpawn(data, server, false);
                    return true;
                });

                PENDING_FINISHES.add(id);
            }

            world.getServer().tell(world.getServer().wrapRunnable(() -> {
                for (var player : new ArrayList<>(world.players())) {
                    player.connection.disconnect(Component.translatable(ModMessages.DELETED_TARDIS));
                }
                if (!isShutdown) {
                    server.levels.remove(id);
                    onDimensionUnloaded(world);

                    Util.backgroundExecutor().execute(() -> {
                        try {
                            world.close();
                        } catch (IOException ignored) {}
                        DimensionHandler.finishDeletion(server, id, isShutdown);
                    });
                }
            }));
        } catch (Throwable e) {
            String message = "Failed to delete TARDIS dimension " + id;
            boolean config = false;
            if (TRConfig.SERVER.DIMENSION_DELETE_MODE.get() == TRConfig.Server.DeleteMode.IMMEDIATE) {
                message += ". Try changing dimension_delete_mode to " + TRConfig.Server.DeleteMode.NEXT_SHUTDOWN.name() + " in " + TardisRefined.MODID + "-server.toml";
                config = true;
            }
            if (config) {
                message += ". You can find this file in the config directory in .minecraft or in the serverconfig directory inside the world you're playing";
            }
            if (Platform.isForge() && Platform.isClient()) {
                message += ". Put the config in the defaultconfigs folder to apply to all worlds";
            }
            throw new RuntimeException(message, e);
        }
    }

    public static LevelStem formLevelStem(MinecraftServer server, ResourceKey<LevelStem> stem) {
        RegistryAccess access = server.registryAccess();

        return new LevelStem(access.registryOrThrow(Registries.DIMENSION_TYPE).getHolderOrThrow(TRDimensionTypes.TARDIS), new TardisChunkGenerator(access.registryOrThrow(Registries.BIOME).getHolderOrThrow(ChunkGenerators.TARDIS_BIOME)));
    }


    public static ServerLevel getExistingLevel(ServerLevel serverLevel, String id) {
        return getExistingLevel(serverLevel, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(TardisRefined.MODID, id)));
    }

    public static ServerLevel getExistingLevel(ServerLevel serverLevel, ResourceKey<Level> levelResourceKey) {
        Map<ResourceKey<Level>, ServerLevel> levelMap = serverLevel.getServer().levels;
        @Nullable ServerLevel existingLevel = levelMap.get(levelResourceKey);
        return existingLevel;
    }

}
