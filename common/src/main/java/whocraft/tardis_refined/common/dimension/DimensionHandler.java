package whocraft.tardis_refined.common.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.PlatformOnly;
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

    @ExpectPlatform
    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {
        throw new AssertionError(TardisRefined.PLATFORM_ERROR);
    }

    public static LevelStem formLevelStem(MinecraftServer server, ResourceKey<LevelStem> stem) {
        RegistryAccess access = server.registryAccess();
        return new LevelStem(access.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY).getHolderOrThrow(DimensionTypes.TARDIS),
                new TardisChunkGenerator(access.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY), access.registryOrThrow(Registry.BIOME_REGISTRY)));
    }


    public static void addToServerList() {}
}
