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
    private static final Map<ConsoleTheme, List<Pattern<ConsoleTheme>>> PATTERNS = new HashMap<>();

    public ConsolePatterns() {
        super(TardisRefined.GSON, "patterns/console");
    }

    public static Pattern<ConsoleTheme> next(ConsoleTheme consoleTheme, Pattern pattern) {
        List<Pattern<ConsoleTheme>> patterns = getPatternsForTheme(consoleTheme);
        int prevIndex = patterns.indexOf(pattern);
        if (prevIndex > patterns.size() || prevIndex + 1 >= patterns.size()) {
            return patterns.get(0);
        }
        return patterns.get(prevIndex + 1);
    }

    public static Pattern<ConsoleTheme> addPattern(ConsoleTheme theme, Pattern<ConsoleTheme> pattern) {
        TardisRefined.LOGGER.info("Adding Console Pattern {} for {}", pattern.id(), pattern.theme());
        if (PATTERNS.containsKey(theme)) {
            List<Pattern<ConsoleTheme>> patternList = new ArrayList<>(PATTERNS.get(theme));
            patternList.add(pattern);
            PATTERNS.replace(theme, patternList);
        } else {
            PATTERNS.put(theme, new ArrayList<>(List.of(pattern)));
        }
        return pattern;
    }


    public static List<Pattern<ConsoleTheme>> getPatternsForTheme(ConsoleTheme consoleTheme) {
        return PATTERNS.get(consoleTheme);
    }

    public static Map<ConsoleTheme, List<Pattern<ConsoleTheme>>> getPatterns() {
        return PATTERNS;
    }

    public static boolean doesPatternExist(ConsoleTheme consoleTheme, ResourceLocation id) {
        List<Pattern<ConsoleTheme> > patterns = getPatternsForTheme(consoleTheme);
        for (Pattern<ConsoleTheme>  pattern : patterns) {
            if (Objects.equals(pattern.id(), id)) {
                return true;
            }
        }
        return false;
    }

    public static Pattern<ConsoleTheme>  getPatternFromString(ConsoleTheme consoleTheme, ResourceLocation id) {
        List<Pattern<ConsoleTheme> > patterns = getPatternsForTheme(consoleTheme);
        for (Pattern<ConsoleTheme>  pattern : patterns) {
            if (Objects.equals(pattern.id(), id)) {
                return pattern;
            }
        }
        return patterns.get(0);
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
                    Pattern<ConsoleTheme> pattern = new Pattern<>(theme, new ResourceLocation(id), textureLocation);
                    pattern.setName(patternObject.get("name_component").getAsString());
                    pattern.setEmissive(emissive);
                    addPattern(theme, pattern);
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
