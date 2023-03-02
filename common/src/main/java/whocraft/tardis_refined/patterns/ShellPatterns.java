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
import whocraft.tardis_refined.common.network.messages.SyncShellPatternsMessage;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;

import java.util.*;

public class ShellPatterns extends SimpleJsonResourceReloadListener {
    private static Map<ShellTheme, List<ShellPattern>> PATTERNS = new HashMap<>();

    public ShellPatterns() {
        super(TardisRefined.GSON, "patterns/shell");
    }

    public static ShellPattern next(ShellTheme ShellTheme, ShellPattern basePattern) {
        List<ShellPattern> basePatterns = getPatternsForTheme(ShellTheme);
        int prevIndex = basePatterns.indexOf(basePattern);
        if (prevIndex > basePatterns.size() || prevIndex + 1 >= basePatterns.size()) {
            return basePatterns.get(0);
        }
        return basePatterns.get(prevIndex + 1);
    }

    public static ShellPattern addPattern(ShellTheme theme, ShellPattern basePattern) {
        TardisRefined.LOGGER.info("Adding Shell BasePattern {} for {}", basePattern.id(), basePattern.theme());
        if (PATTERNS.containsKey(theme)) {
            List<ShellPattern> basePatternList = new ArrayList<>(PATTERNS.get(theme));
            basePatternList.add(basePattern);
            PATTERNS.replace(theme, basePatternList);
        } else {
            PATTERNS.put(theme, new ArrayList<>(List.of(basePattern)));
        }
        return basePattern;
    }


    public static List<ShellPattern> getPatternsForTheme(ShellTheme ShellTheme) {
        return PATTERNS.get(ShellTheme);
    }

    public static Map<ShellTheme, List<ShellPattern>> getPatterns() {
        return PATTERNS;
    }

    public static boolean doesPatternExist(ShellTheme ShellTheme, ResourceLocation id) {
        List<ShellPattern> basePatterns = getPatternsForTheme(ShellTheme);
        for (ShellPattern basePattern : basePatterns) {
            if (Objects.equals(basePattern.id(), id)) {
                return true;
            }
        }
        return false;
    }

    public static ShellPattern getPatternFromString(ShellTheme ShellTheme, ResourceLocation id) {
        List<ShellPattern> basePatterns = getPatternsForTheme(ShellTheme);
        for (ShellPattern basePattern : basePatterns) {
            if (Objects.equals(basePattern.id(), id)) {
                return basePattern;
            }
        }
        return basePatterns.get(0);
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

                    JsonObject interior = patternObject.get("interior").getAsJsonObject();
                    String interiorTexture = interior.get("texture").getAsString();

                    JsonObject exterior = patternObject.get("exterior").getAsJsonObject();
                    String exteriorTexture = exterior.get("texture").getAsString();
                    boolean emissive = exterior.get("emissive").getAsBoolean();


                    ResourceLocation textureLocation = new ResourceLocation(exteriorTexture);
                    ResourceLocation interiorTextureLocation = new ResourceLocation(interiorTexture);

                    ShellPattern basePattern = new ShellPattern(theme, new ResourceLocation(id), textureLocation, interiorTextureLocation);
                    basePattern.setName(patternObject.get("name_component").getAsString());
                    basePattern.setEmissive(emissive);
                    addPattern(theme, basePattern);
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
