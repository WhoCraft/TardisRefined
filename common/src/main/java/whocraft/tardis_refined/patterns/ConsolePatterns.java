package whocraft.tardis_refined.patterns;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.network.messages.SyncConsolePatternsMessage;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.Platform;

import java.util.*;

public class ConsolePatterns extends SimpleJsonResourceReloadListener {
    private static final Map<ConsoleTheme, List<BasePattern<ConsoleTheme>>> PATTERNS = new HashMap<>();

    public ConsolePatterns() {
        super(TardisRefined.GSON, TardisRefined.MODID + "/patterns/console");
    }

    public static BasePattern<ConsoleTheme> next(ConsoleTheme consoleTheme, BasePattern basePattern) {
        List<BasePattern<ConsoleTheme>> basePatterns = getPatternsForTheme(consoleTheme);
        int prevIndex = basePatterns.indexOf(basePattern);
        if (prevIndex > basePatterns.size() || prevIndex + 1 >= basePatterns.size()) {
            return basePatterns.get(0);
        }
        return basePatterns.get(prevIndex + 1);
    }

    public static BasePattern<ConsoleTheme> addPattern(ConsoleTheme theme, BasePattern<ConsoleTheme> basePattern) {
        TardisRefined.LOGGER.info("Adding Console BasePattern {} for {}", basePattern.id(), basePattern.theme());
        if (PATTERNS.containsKey(theme)) {
            List<BasePattern<ConsoleTheme>> basePatternList = new ArrayList<>(PATTERNS.get(theme));
            basePatternList.add(basePattern);
            PATTERNS.replace(theme, basePatternList);
        } else {
            PATTERNS.put(theme, new ArrayList<>(List.of(basePattern)));
        }
        return basePattern;
    }


    public static List<BasePattern<ConsoleTheme>> getPatternsForTheme(ConsoleTheme consoleTheme) {
        return PATTERNS.get(consoleTheme);
    }

    public static Map<ConsoleTheme, List<BasePattern<ConsoleTheme>>> getPatterns() {
        return PATTERNS;
    }

    public static boolean doesPatternExist(ConsoleTheme consoleTheme, ResourceLocation id) {
        List<BasePattern<ConsoleTheme>> basePatterns = getPatternsForTheme(consoleTheme);
        for (BasePattern<ConsoleTheme> basePattern : basePatterns) {
            if (Objects.equals(basePattern.id(), id)) {
                return true;
            }
        }
        return false;
    }

    public static BasePattern<ConsoleTheme> getPatternFromString(ConsoleTheme consoleTheme, ResourceLocation id) {
        List<BasePattern<ConsoleTheme>> basePatterns = getPatternsForTheme(consoleTheme);
        for (BasePattern<ConsoleTheme> basePattern : basePatterns) {
            if (Objects.equals(basePattern.id(), id)) {
                return basePattern;
            }
        }
        return basePatterns.get(0);
    }

    public static void clearPatterns() {
        ConsolePatterns.PATTERNS.clear();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        ConsolePatterns.PATTERNS.clear();
        object.forEach((resourceLocation, jsonElement) -> {

            try {
                JsonArray patternsArray = jsonElement.getAsJsonArray();
                for (JsonElement patternElement : patternsArray) {
                    JsonObject patternObject = patternElement.getAsJsonObject();
                    ConsoleTheme theme = ConsoleTheme.valueOf(findConsoleTheme(resourceLocation));
                    String id = patternObject.get("id").getAsString();
                    String texture = patternObject.get("texture").getAsString();

                    boolean emissive = patternObject.get("emissive").getAsBoolean();

                    ResourceLocation textureLocation = new ResourceLocation(texture);
                    BasePattern<ConsoleTheme> basePattern = new BasePattern<>(theme, new ResourceLocation(id), textureLocation);
                    basePattern.setName(patternObject.get("name_component").getAsString());
                    basePattern.setEmissive(emissive);
                    addPattern(theme, basePattern);
                }
            } catch (JsonParseException jsonParseException){
                TardisRefined.LOGGER.debug("Issue parsing {}! Error: {}", resourceLocation, jsonParseException.getMessage());
            }
        });

        if(Platform.getServer() != null) {
            new SyncConsolePatternsMessage(PATTERNS).sendToAll();
        }
    }

    @NotNull
    private String findConsoleTheme(ResourceLocation resourceLocation) {
        String path = resourceLocation.getPath();
        int index = path.lastIndexOf("/");
        if (index == -1) {
            return path.toUpperCase(Locale.ENGLISH);
        } else {
            return path.substring(index + 1).toUpperCase(Locale.ENGLISH);
        }
    }


}
