package whocraft.tardis_refined.client.model.blockentity.console;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

import java.util.*;

public class ConsolePatterns extends SimpleJsonResourceReloadListener {


    public static final ResourceLocation CRYSTAL_CORRUPTED = new ResourceLocation(TardisRefined.MODID, "crystal/corrupted");
    private static final Map<ConsoleTheme, List<Pattern>> PATTERNS = new HashMap<>();

    public ConsolePatterns() {
        super(TardisRefined.GSON, "patterns/console");
    }

    public static Pattern next(ConsoleTheme consoleTheme, Pattern pattern) {
        List<Pattern> patterns = getPatternsForTheme(consoleTheme);
        int prevIndex = patterns.indexOf(pattern);
        if (prevIndex > patterns.size() || prevIndex + 1 >= patterns.size()) {
            return patterns.get(0);
        }
        return patterns.get(prevIndex + 1);
    }

    public static Pattern addPattern(ConsoleTheme theme, Pattern pattern) {
        TardisRefined.LOGGER.info("Adding Console Pattern {} for {}", pattern.identifier, pattern.theme);
        if (PATTERNS.containsKey(theme)) {
            List<Pattern> patternLiat = new ArrayList<>(PATTERNS.get(theme));
            patternLiat.add(pattern);
            PATTERNS.replace(theme, patternLiat);
            return pattern;
        }
        PATTERNS.put(theme, List.of(pattern));
        return pattern;
    }

    public static List<Pattern> getPatternsForTheme(ConsoleTheme consoleTheme) {
        return PATTERNS.get(consoleTheme);
    }


    public static boolean doesPatternExist(ConsoleTheme consoleTheme, ResourceLocation id) {
        List<Pattern> patterns = getPatternsForTheme(consoleTheme);
        for (Pattern pattern : patterns) {
            if (Objects.equals(pattern.id(), id)) {
                return true;
            }
        }
        return false;
    }

    public static Pattern getPatternFromString(ConsoleTheme consoleTheme, ResourceLocation id) {
        List<Pattern> patterns = getPatternsForTheme(consoleTheme);
        for (Pattern pattern : patterns) {
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

            JsonArray patternsArray = jsonElement.getAsJsonArray();
            for (JsonElement patternElement : patternsArray) {
                JsonObject patternObject = patternElement.getAsJsonObject();
                ConsoleTheme theme = ConsoleTheme.valueOf(findConsoleTheme(resourceLocation));
                String id = patternObject.get("id").getAsString();
                String texture = patternObject.get("texture").getAsString();

                ResourceLocation textureLocation = new ResourceLocation(texture);
                Pattern pattern = new Pattern(theme, new ResourceLocation(id), textureLocation);
                pattern.setName(patternObject.get("name").getAsString());
                addPattern(theme, pattern);
            }
        });
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



    public static class Pattern {

        private final ResourceLocation textureLocation;
        private final ResourceLocation identifier;
        private String name;

        public ConsoleTheme theme() {
            return theme;
        }

        private final ConsoleTheme theme;

        public Pattern(ConsoleTheme consoleTheme, ResourceLocation identifier, ResourceLocation texture) {
            this.identifier = identifier;
            this.textureLocation = texture;
            this.theme = consoleTheme;
            this.name = identifier.getPath().substring(0, 1).toUpperCase() + identifier.getPath().substring(1).replace("_", "");
        }

        public Pattern(ConsoleTheme consoleTheme, ResourceLocation identifier, String texture) {
            this.identifier = new ResourceLocation(identifier.getNamespace(), consoleTheme.getSerializedName() + "/" + identifier.getPath());
            this.textureLocation = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/" + texture + ".png");
            this.theme = consoleTheme;
            this.name = identifier.getPath().substring(0, 1).toUpperCase() + identifier.getPath().substring(1).replace("_", "");
        }

        public String name() {
            return name;
        }

        public Pattern setName(String name) {
            this.name = name;
            return this;
        }

        public ResourceLocation textureLocation() {
            return textureLocation;
        }

        public ResourceLocation id() {
            return identifier;
        }

        public ConsoleTheme getTheme() {
            return theme;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pattern pattern = (Pattern) o;
            return Objects.equals(textureLocation, pattern.textureLocation) && Objects.equals(identifier, pattern.identifier) && theme == pattern.theme;
        }

        @Override
        public int hashCode() {
            return Objects.hash(textureLocation, identifier, theme);
        }
    }

}
