package whocraft.tardis_refined.patterns;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

import java.io.Console;
import java.util.*;
/**
 * Data manager for all ConsolePattern(s)
 */
public class ConsolePatterns{

    private static PatternReloadListener<ConsolePatternCollection> PATTERNS = PatternReloadListener.createListener(TardisRefined.MODID + "/patterns/console", ConsolePatternCollection.CODEC);

    private static Map<ResourceLocation, ConsolePatternCollection> DEFAULT_PATTERNS = new HashMap();

    public static ConsolePattern next(ConsoleTheme consoleTheme, ConsolePattern ConsolePattern) {
        List<ConsolePattern> ConsolePatterns = getPatternsForTheme(consoleTheme);
        int prevIndex = ConsolePatterns.indexOf(ConsolePattern);
        if (prevIndex > ConsolePatterns.size() || prevIndex + 1 >= ConsolePatterns.size()) {
            return ConsolePatterns.get(0);
        }
        return ConsolePatterns.get(prevIndex + 1);
    }

    public static PatternReloadListener<ConsolePatternCollection> getReloadListener(){
        return PATTERNS;
    }

    public static Map<ResourceLocation, ConsolePatternCollection> getRegistry() {
        return PATTERNS.getData();
    }

    public static void clearPatterns() {
        ConsolePatterns.PATTERNS.getData().clear();
    }


    public static List<ConsolePattern> getPatternsForTheme(ConsoleTheme consoleTheme) {
        return PATTERNS.getData().get(new ResourceLocation(consoleTheme.getSerializedName().toLowerCase(Locale.ENGLISH))).patterns();
    }

    public static boolean doesPatternExist(ConsoleTheme consoleTheme, ResourceLocation patternId) {
        List<ConsolePattern> ConsolePatterns = getPatternsForTheme(consoleTheme);
        for (ConsolePattern ConsolePattern : ConsolePatterns) {
            if (Objects.equals(ConsolePattern.id(), patternId)) {
                return true;
            }
        }
        return false;
    }

    public static ConsolePattern getPatternFromString(ConsoleTheme consoleTheme, ResourceLocation id) {
        List<ConsolePattern> ConsolePatterns = getPatternsForTheme(consoleTheme);
        for (ConsolePattern ConsolePattern : ConsolePatterns) {
            if (Objects.equals(ConsolePattern.id(), id)) {
                return ConsolePattern;
            }
        }
        return ConsolePatterns.get(0);
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

    private static ConsolePattern addDefaultPattern(ConsoleTheme theme, String patternId, String textureName, boolean hasEmissiveTexture) {
        //TODO: When moving away from enum system to a registry-like system, remove hardcoded Tardis Refined modid
        ResourceLocation themeId = new ResourceLocation(TardisRefined.MODID, theme.getSerializedName().toLowerCase(Locale.ENGLISH));
        ConsolePatternCollection collection;
        ConsolePattern pattern = (ConsolePattern) new ConsolePattern("war", new PatternTexture(createConsolePatternTextureLocation(theme,textureName), true)).setThemeId(themeId);
        if (DEFAULT_PATTERNS.containsKey(themeId)) {
            collection = DEFAULT_PATTERNS.get(themeId);
            collection.patterns().add(pattern);
            DEFAULT_PATTERNS.replace(themeId, collection);
        } else {
            collection = new ConsolePatternCollection(List.of(pattern));
            DEFAULT_PATTERNS.put(themeId, collection);
        }
        TardisRefined.LOGGER.debug("Adding Pattern ConsolePattern {} for {}", pattern.id(), themeId);
        return pattern;
    }

    private static ResourceLocation createConsolePatternTextureLocation(ConsoleTheme theme, String textureName){
        return new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/" + theme.getSerializedName().toLowerCase(Locale.ENGLISH) + "/" + textureName + ".png");
    }

    /** Gets a default list of Console Patterns added by Tardis Refined. Useful as a fallback list.*/
    public static Map<ResourceLocation, ConsolePatternCollection> getDefaultPatterns(){
        return DEFAULT_PATTERNS;
    }

    public static Map<ResourceLocation, ConsolePatternCollection> registerDefaultPatterns() {

        /*Add Base Textures*/
        for (ConsoleTheme consoleTheme : ConsoleTheme.values()) {
            String themeName = consoleTheme.name().toLowerCase(Locale.ENGLISH);
            boolean hasDefaultEmission = consoleTheme == ConsoleTheme.COPPER || consoleTheme == ConsoleTheme.CRYSTAL|| consoleTheme == ConsoleTheme.CORAL || consoleTheme == ConsoleTheme.FACTORY || consoleTheme == ConsoleTheme.INITIATIVE || consoleTheme == ConsoleTheme.TOYOTA || consoleTheme == ConsoleTheme.VICTORIAN;
            addDefaultPattern(consoleTheme, "default", themeName + "/" + themeName + "_console", hasDefaultEmission);
        }

        /*Coral*/
        addDefaultPattern(ConsoleTheme.CORAL, "war", "coral_console_war", true);
        addDefaultPattern(ConsoleTheme.CORAL, "blue", "coral_console_blue", true);

        /*Factory*/
        addDefaultPattern(ConsoleTheme.FACTORY, "vintage", "factory_console_vintage", true);
        addDefaultPattern(ConsoleTheme.FACTORY, "mint", "factory_console_mint", true);
        addDefaultPattern(ConsoleTheme.FACTORY, "wood", "factory_console_wood", true);

        /*Toyota*/
        addDefaultPattern(ConsoleTheme.TOYOTA, "violet", "toyota_texture_purple", true);
        addDefaultPattern(ConsoleTheme.TOYOTA, "blue", "toyota_texture_blue", true);

        /*Crystal*/
        addDefaultPattern(ConsoleTheme.CRYSTAL, "corrupted", "rystal_console_corrupted", true);

        /*Myst*/
        addDefaultPattern(ConsoleTheme.MYST, "molten", "myst_console_molten", true);

        /*Victorian*/
        addDefaultPattern(ConsoleTheme.VICTORIAN, "smissmass", "victorian_console_smissmass", false);
        addDefaultPattern(ConsoleTheme.VICTORIAN, "grant", "victorian_console_grant", false);

        /*Initiative*/
        addDefaultPattern(ConsoleTheme.INITIATIVE, "aperture", "initiative_console_aperture", true);
        addDefaultPattern(ConsoleTheme.INITIATIVE, "blue", "initiative_console_blue", true);
        addDefaultPattern(ConsoleTheme.INITIATIVE, "construction", "initiative_console_construction", false);

        // Nuka
        addDefaultPattern(ConsoleTheme.NUKA, "industrial", "nuka_industrial", false);
        addDefaultPattern(ConsoleTheme.NUKA, "cool", "nuka_cool", false);

        /*Copper*/
        addDefaultPattern(ConsoleTheme.COPPER, "sculk", "copper_console_sculk", false);
        return DEFAULT_PATTERNS;
    }
}
