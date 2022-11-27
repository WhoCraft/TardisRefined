package whocraft.tardis_refined.common.dimension;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.chunk.TardisChunkGenerator;
import whocraft.tardis_refined.registry.DimensionTypes;

import javax.annotation.Nullable;
import java.util.Map;

/*
* Majority of this code is sourced from Commoble's Hyberbox with permission.
* You can view their project here: https://github.com/Commoble/hyperbox
* */

public class DimensionHandler {

    public static ServerLevel getOrCreateInterior(Level interactionLevel, String id) {

        if (interactionLevel instanceof ServerLevel serverLevel) {
           ResourceKey<Level> levelResourceKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(TardisRefined.MODID, id));
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
        return new LevelStem(access.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY).getHolderOrThrow(DimensionTypes.TARDIS),
                new TardisChunkGenerator(access.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY), access.registryOrThrow(Registry.BIOME_REGISTRY)));
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
