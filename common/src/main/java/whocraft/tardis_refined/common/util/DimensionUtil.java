package whocraft.tardis_refined.common.util;

import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.TRDimensionTypes;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class DimensionUtil {

    private static final String REGEX_PREFIX = "[regex]";
    private static final String SUBSTRING_PREFIX = "[substring]";
    private static final String NAMESPACE_PREFIX = "[namespace]";

    public static Predicate<ResourceKey<Level>> getDimensionPredicate(String configEntry) {
        if (configEntry.startsWith(REGEX_PREFIX)) {
            String regex = configEntry.substring(REGEX_PREFIX.length());
            return dim -> dim.location().toString().matches(regex);
        }
        if (configEntry.startsWith(SUBSTRING_PREFIX)) {
            String substring = configEntry.substring(SUBSTRING_PREFIX.length());
            return dim -> dim.location().toString().contains(substring);
        }
        if (configEntry.startsWith(NAMESPACE_PREFIX)) {
            String namespace = configEntry.substring(NAMESPACE_PREFIX.length());
            return dim -> dim.location().getNamespace().equals(namespace);
        }
        return dim -> dim.location().toString().equals(configEntry);
    }

    public static Predicate<ResourceKey<Level>> getDimensionPredicate(List<? extends String> configEntries) {
        return configEntries.stream().map(DimensionUtil::getDimensionPredicate).reduce(Predicate::or).orElse(dim -> false);
    }

    public static boolean isAllowedDimension(ResourceKey<Level> level) {
        String namespace = level.location().getNamespace();
        var bannedDimensions = getDimensionPredicate(TRConfig.SERVER.BANNED_DIMENSIONS.get());
        return !namespace.contains(TardisRefined.MODID) && !bannedDimensions.test(level);
    }
    public static Set<ResourceKey<Level>> getTardisLevels(MinecraftServer server) {
        Set<ResourceKey<Level>> set = Sets.newHashSet();
        for (ServerLevel level : server.getAllLevels()) {
            if (TRDimensionTypes.isTARDISDimension(level)) {
                set.add(level.dimension());
            }
        }
        return set;
    }

    public static Set<ResourceKey<Level>> getAllowedDimensions(MinecraftServer server) {
        Set<ResourceKey<Level>> set = Sets.newHashSet();
        for (ServerLevel level : server.getAllLevels()) {
            if (isAllowedDimension(level.dimension())) {
                set.add(level.dimension());
            }
        }
        return set;
    }

    public static ServerLevel getLevel(ResourceKey<Level> levelResourceKey) {
        if(Platform.getServer() == null) {
            TardisRefined.LOGGER.error("Null Server when looking for {} - Can be ignored if logging out", levelResourceKey);
            return null;
        }
        return Platform.getServer().getLevel(levelResourceKey);
    }
}
