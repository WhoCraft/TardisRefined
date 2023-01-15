package whocraft.tardis_refined.client.model.blockentity.console;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

import java.util.*;

public class ConsolePatterns {


    private static final Map<ConsoleTheme, List<Pattern>> PATTERNS = new HashMap<>();

    public static void registerPatterns() {

        /*Add Base Textures*/
        for (ConsoleTheme consoleTheme : ConsoleTheme.values()) {
            String themeName = consoleTheme.name().toLowerCase(Locale.ENGLISH);
            addPattern(consoleTheme, new Pattern("default", themeName + "/" + themeName + "_console"));
        }

        /*Coral*/
        addPattern(ConsoleTheme.CORAL, new Pattern("blue", "coral/coral_console_blue"));
        addPattern(ConsoleTheme.CORAL, new Pattern("war", "coral/coral_console_war"));

        /*Factory*/
        addPattern(ConsoleTheme.FACTORY, new Pattern("vintage", "factory/factory_console_vintage"));
        addPattern(ConsoleTheme.FACTORY, new Pattern("mint", "factory/factory_console_mint"));

        /*Toyota*/
        addPattern(ConsoleTheme.TOYOTA, new Pattern("violet", "toyota/toyota_texture_purple"));
        addPattern(ConsoleTheme.TOYOTA, new Pattern("blue", "toyota/toyota_texture_blue"));

        /*Crystal*/
        addPattern(ConsoleTheme.CRYSTAL, new Pattern("purple", "crystal/crystal_console_purple"));

        /*Myst*/
        addPattern(ConsoleTheme.MYST, new Pattern("molten", "myst/myst_console_molten"));

        /*Initiative*/
        addPattern(ConsoleTheme.INITIATIVE, new Pattern("aperture", "initiative/initiative_console_aperture"));

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

        pattern.setTheme(theme);

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


    public static boolean doesPatternExist(ConsoleTheme consoleTheme, String name) {
        List<Pattern> patterns = getPatternsForTheme(consoleTheme);
        for (Pattern pattern : patterns) {
            if (Objects.equals(pattern.name(), name)) {
                return true;
            }
        }
        return false;
    }

    public static Pattern getPatternFromString(ConsoleTheme consoleTheme, String name) {
        List<Pattern> patterns = getPatternsForTheme(consoleTheme);
        for (Pattern pattern : patterns) {
            if (Objects.equals(pattern.name(), name)) {
                return pattern;
            }
        }
        return patterns.get(0);
    }

    public static class Pattern {

        private final ResourceLocation textureLocation;
        private final String name;

        public ConsoleTheme theme() {
            return theme;
        }

        private ConsoleTheme theme;

        public Pattern(String name, ResourceLocation texture) {
            this.name = name.trim().toLowerCase(Locale.ENGLISH);
            this.textureLocation = texture;
        }

        public Pattern(String name, String texture) {
            this.name = name.trim().toLowerCase(Locale.ENGLISH);
            this.textureLocation = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/" + texture + ".png");
        }

        public ResourceLocation textureLocation() {
            return textureLocation;
        }

        public String name() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pattern pattern = (Pattern) o;
            return textureLocation.equals(pattern.textureLocation) && name.equals(pattern.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(textureLocation, name);
        }

        public void setTheme(ConsoleTheme theme) {
            this.theme = theme;
        }
    }

}
