package whocraft.tardis_refined.client.model.blockentity.console;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

import java.util.*;

public class ConsolePatterns {


    private static final Map<ConsoleTheme, List<Pattern>> PATTERNS = new HashMap<>();

    public static void registerPatterns(){
        /*Coral*/
        addPattern(ConsoleTheme.CORAL, new Pattern("blue", "coral/coral_console_blue"));
        addPattern(ConsoleTheme.CORAL, new Pattern("war", "coral/coral_console_war"));
        addPattern(ConsoleTheme.CORAL, new Pattern("white", "coral/coral_console_white"));

        /*Factory*/
        addPattern(ConsoleTheme.FACTORY, new Pattern("vintage", "factory/factory_console_vintage"));

        /*Toyota*/
        addPattern(ConsoleTheme.TOYOTA, new Pattern("violet", "toyota/toyota_texture_purple"));
        addPattern(ConsoleTheme.TOYOTA, new Pattern("blue", "toyota/toyota_texture_blue"));

    }

    public static void addPattern(ConsoleTheme theme, Pattern pattern) {
        if (PATTERNS.containsKey(theme)) {
            List<Pattern> patternLiat = new ArrayList<>(PATTERNS.get(theme));
            patternLiat.add(pattern);
            PATTERNS.replace(theme, patternLiat);
            return;
        }
        PATTERNS.put(theme, List.of(pattern));
    }

    public static List<Pattern> getPatternsForTheme(ConsoleTheme consoleTheme){
        return PATTERNS.get(consoleTheme);
    }


    public static boolean doesPatternExist(ConsoleTheme consoleTheme, String name){
        List<Pattern> patterns = getPatternsForTheme(consoleTheme);
        for (Pattern pattern : patterns) {
            if(Objects.equals(pattern.name(), name)){
                return true;
            }
        }
        return false;
    }

    public static class Pattern {

        private final ResourceLocation textureLocation;
        private final String name;

        public Pattern(String name, ResourceLocation texture) {
            this.name = name.trim().toLowerCase(Locale.ENGLISH);
            this.textureLocation = texture;
        }

        public Pattern(String name, String texture) {
            this.name = name.trim().toLowerCase(Locale.ENGLISH);
            this.textureLocation = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/"+texture+".png");
        }

        public ResourceLocation textureLocation() {
            return textureLocation;
        }

        public String name() {
            return name;
        }
    }

}
