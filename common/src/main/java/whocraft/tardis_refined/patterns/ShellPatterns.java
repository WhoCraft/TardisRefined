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
import whocraft.tardis_refined.common.network.messages.SyncShellPatternsMessage;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;

import java.util.*;

public class ShellPatterns extends SimpleJsonResourceReloadListener {
    private static Map<ShellTheme, List<Pattern<ShellTheme>>> PATTERNS = new HashMap<>();

    public ShellPatterns() {
        super(TardisRefined.GSON, "patterns/shell");
    }

    public static Pattern<ShellTheme> next(ShellTheme ShellTheme, Pattern pattern) {
        List<Pattern<ShellTheme>> patterns = getPatternsForTheme(ShellTheme);
        int prevIndex = patterns.indexOf(pattern);
        if (prevIndex > patterns.size() || prevIndex + 1 >= patterns.size()) {
            return patterns.get(0);
        }
        return patterns.get(prevIndex + 1);
    }

    public static Pattern<ShellTheme> addPattern(ShellTheme theme, Pattern<ShellTheme> pattern) {
        TardisRefined.LOGGER.info("Adding Shell Pattern {} for {}", pattern.id(), pattern.theme());
        if (PATTERNS.containsKey(theme)) {
            List<Pattern<ShellTheme>> patternList = new ArrayList<>(PATTERNS.get(theme));
            patternList.add(pattern);
            PATTERNS.replace(theme, patternList);
        } else {
            PATTERNS.put(theme, new ArrayList<>(List.of(pattern)));
        }
        return pattern;
    }


    public static List<Pattern<ShellTheme>> getPatternsForTheme(ShellTheme ShellTheme) {
        return PATTERNS.get(ShellTheme);
    }

    public static Map<ShellTheme, List<Pattern<ShellTheme>>> getPatterns() {
        return PATTERNS;
    }

    public static boolean doesPatternExist(ShellTheme ShellTheme, ResourceLocation id) {
        List<Pattern<ShellTheme> > patterns = getPatternsForTheme(ShellTheme);
        for (Pattern<ShellTheme>  pattern : patterns) {
            if (Objects.equals(pattern.id(), id)) {
                return true;
            }
        }
        return false;
    }

    public static Pattern<ShellTheme>  getPatternFromString(ShellTheme ShellTheme, ResourceLocation id) {
        List<Pattern<ShellTheme> > patterns = getPatternsForTheme(ShellTheme);
        for (Pattern<ShellTheme>  pattern : patterns) {
            if (Objects.equals(pattern.id(), id)) {
                return pattern;
            }
        }
        return patterns.get(0);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        ShellPatterns.PATTERNS.clear();
        object.forEach((resourceLocation, jsonElement) -> {

            try {
                JsonArray patternsArray = jsonElement.getAsJsonArray();
                for (JsonElement patternElement : patternsArray) {
                    JsonObject patternObject = patternElement.getAsJsonObject();
                    ShellTheme theme = ShellTheme.valueOf(findShellTheme(resourceLocation));
                    String id = patternObject.get("id").getAsString();
                    String texture = patternObject.get("texture").getAsString();

                    boolean emissive = patternObject.get("emissive").getAsBoolean();

                    ResourceLocation textureLocation = new ResourceLocation(texture);
                    Pattern<ShellTheme> pattern = new Pattern<>(theme, new ResourceLocation(id), textureLocation);
                    pattern.setName(patternObject.get("name_component").getAsString());
                    pattern.setEmissive(emissive);
                    addPattern(theme, pattern);
                }
            } catch (JsonParseException jsonParseException){
                TardisRefined.LOGGER.debug("Issue parsing {}! Error: {}", resourceLocation, jsonParseException.getMessage());
            }
        });

        if(Platform.getServer() != null) {
            new SyncShellPatternsMessage(PATTERNS).sendToAll();
        }
    }

    @NotNull
    private String findShellTheme(ResourceLocation resourceLocation) {
        String path = resourceLocation.getPath();
        int index = path.lastIndexOf("/");
        if (index == -1) {
            return path.toUpperCase(Locale.ENGLISH);
        } else {
            return path.substring(index + 1).toUpperCase(Locale.ENGLISH);
        }
    }


}
