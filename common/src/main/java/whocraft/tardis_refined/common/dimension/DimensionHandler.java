package whocraft.tardis_refined.common.dimension;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.chunk.TardisChunkGenerator;
import whocraft.tardis_refined.mixin.MinecraftServerStorageAccessor;
import whocraft.tardis_refined.registry.DimensionTypes;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import static whocraft.tardis_refined.common.util.Platform.getServer;

/*
* Majority of this code is sourced from Commoble's Hyberbox with permission.
* You can view their project here: https://github.com/Commoble/hyperbox
* */

public class DimensionHandler {

    public static ArrayList<ResourceKey<Level>> LEVELS = new ArrayList<>();

    public static void addDimension(ResourceKey<Level> resourceKey){
        LEVELS.add(resourceKey);
        writeLevels();
    }

    public static Path getWorldSavingDirectory() {
        return getStorage().getDimensionPath(Level.OVERWORLD);
    }

    public static LevelStorageSource.LevelStorageAccess getStorage(){
        return ((MinecraftServerStorageAccessor) getServer()).getStorageSource();
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

    public static ServerLevel getOrCreateInterior(Level interactionLevel, ResourceLocation resourceLocation) {

        if (interactionLevel instanceof ServerLevel serverLevel) {
           ResourceKey<Level> levelResourceKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, resourceLocation);
           ServerLevel existingLevel = getExistingLevel(serverLevel, levelResourceKey);

           if (existingLevel != null) {
               return existingLevel;
           }

            return createDimension(interactionLevel, levelResourceKey);
        }

        return null;

    }

    @ExpectPlatform
    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {
        throw new AssertionError(TardisRefined.PLATFORM_ERROR);
    }

    public static LevelStem formLevelStem(MinecraftServer server, ResourceKey<LevelStem> stem) {
        RegistryAccess access = server.registryAccess();

        return new LevelStem(access.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY).getHolderOrThrow(DimensionTypes.TARDIS), new TardisChunkGenerator(access.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY), access.registryOrThrow(Registry.BIOME_REGISTRY)));
    }


    public static ServerLevel getExistingLevel(ServerLevel serverLevel, String id) {
        return getExistingLevel(serverLevel, ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(TardisRefined.MODID, id)));
    }

    public static ServerLevel getExistingLevel(ServerLevel serverLevel, ResourceKey<Level> levelResourceKey) {
        Map<ResourceKey<Level>, ServerLevel> levelMap = serverLevel.getServer().levels;
        @Nullable ServerLevel existingLevel = levelMap.get(levelResourceKey);
        return existingLevel;
    }

}
