package whocraft.tardis_refined.patterns;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.constants.ResourceConstants;

import java.util.*;
/**
 * Data manager for all {@link ConsolePattern}(s)
 */
public class ConsolePatterns{

    private static PatternReloadListener<ConsolePatternCollection> PATTERNS = PatternReloadListener.createListener(TardisRefined.MODID + "/patterns/console", ConsolePatternCollection.CODEC);

    private static Map<ResourceLocation, ConsolePatternCollection> DEFAULT_PATTERNS = new HashMap();

    public static ConsolePattern next(ConsoleTheme consoleTheme, ConsolePattern currentPattern) {
        ConsolePatternCollection collection = getPatternCollectionForTheme(consoleTheme);
        return next(collection, currentPattern);
    }

    /** Helper to get the next available {@link ConsolePattern} in the current {@link ConsolePatternCollection}*/
    public static ConsolePattern next(ConsolePatternCollection collection, ConsolePattern currentPattern) {
        return next(collection.patterns(), currentPattern);
    }

    public static ConsolePattern next(List<ConsolePattern> patterns, ConsolePattern currentPattern) {
        if(currentPattern == null){
            return patterns.get(0);
        }

        int prevIndex = patterns.indexOf(currentPattern);
        if (prevIndex > patterns.size() || prevIndex + 1 >= patterns.size()) {
            return patterns.get(0);
        }
        return patterns.get(prevIndex + 1);
    }

    public static PatternReloadListener<ConsolePatternCollection> getReloadListener(){
        return PATTERNS;
    }

    public static Map<ResourceLocation, ConsolePatternCollection> getRegistry() {
        return PATTERNS.getData();
    }

    /** Lookup the list of {@link ConsolePattern}(s) in a {@link ConsolePatternCollection} for a given {@link ConsoleTheme}*/
    public static List<ConsolePattern> getPatternsForTheme(ConsoleTheme consoleTheme) {
        return PATTERNS.getData().get(new ResourceLocation(TardisRefined.MODID, consoleTheme.getSerializedName().toLowerCase(Locale.ENGLISH))).patterns();
    }

    /** Retrieves a pattern from a default list of patterns, for use when Capabiliteis or Cardinal Components classloads patterns before datapack loading*/
    public static List<ConsolePattern> getPatternsForThemeDefault(ConsoleTheme consoleTheme) {
        return DEFAULT_PATTERNS.get(new ResourceLocation(TardisRefined.MODID, consoleTheme.getSerializedName().toLowerCase(Locale.ENGLISH))).patterns();
    }

    /** Helper method to get a {@link ConsolePatternCollection} by theme ID */
    public static ConsolePatternCollection getPatternCollectionForTheme(ConsoleTheme consoleTheme) {
        return PATTERNS.getData().get(new ResourceLocation(TardisRefined.MODID, consoleTheme.getSerializedName().toLowerCase(Locale.ENGLISH)));
    }

    /** Lookup a {@link ConsoleTheme} based on a singular {@link ConsolePattern}
     * <br> As there is a many-to-one relationship between {@link ConsolePattern} and {@link ConsoleTheme}
     * <br> as well as a one-to-one relationship between a {@link ShellPatternCollection} and {@link ShellTheme},
     * we will iterate through all {@link ShellPatternCollection} (which holds the theme ID) and find matchine ones*/
    public static ShellTheme getThemeForPattern(ShellPattern pattern) {
        Map<ResourceLocation, ShellPatternCollection> entries = ShellPatterns.getRegistry();
        for (Map.Entry<ResourceLocation, ShellPatternCollection> entry : entries.entrySet()){
            if (pattern.getThemeId() == entry.getKey()){
                return ShellTheme.findOr(entry.getValue().themeId().getPath(), ShellTheme.FACTORY);
            }
        }
        return ShellTheme.FACTORY;
    }

    /** Sanity check to make sure a Pattern for a ConsoleTheme exists
     * <br> A likely use case for this is when entries for the patterns are being modified in some way, such as when something triggers datapacks to be reloaded*/
    public static boolean doesPatternExist(ConsoleTheme consoleTheme, ResourceLocation patternId) {
        List<ConsolePattern> consolePatterns = getPatternsForTheme(consoleTheme);
        for (ConsolePattern consolePattern : consolePatterns) {
            if (Objects.equals(consolePattern.id(), patternId)) {
                return true;
            }
        }
        return false;
    }

    /** Lookup up a {@link ConsolePattern} within a particular {@link ConsoleTheme} or get the first one in the list if the input pattern id cannot be found*/
    public static ConsolePattern getPatternOrDefault(ConsoleTheme consoleTheme, ResourceLocation id) {
        List<ConsolePattern> consolePatterns = getPatternsForTheme(consoleTheme);
        for (ConsolePattern consolePattern : consolePatterns) {
            if (Objects.equals(consolePattern.id(), id)) {
                return consolePattern;
            }
        }
        return consolePatterns.get(0);
    }

    //TODO: Find out what this does, currently isn't being used. Seems to have been an abandoned attempt to find the shell theme based on texture location??
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

    /** Constructs and a {@link ConsolePattern}, then adds it to a {@link ConsolePatternCollection}, which is assigned to a {@link ConsoleTheme}.
     * <br> The {@link ConsolePatternCollection} is then added to an internal default map
     * <br> Also assigns the {@link ConsolePattern} its parent {@link ConsoleTheme}'s ID
     * @implSpec INTERNAL USE ONLY
     * */
    private static ConsolePattern addDefaultPattern(ConsoleTheme theme, String patternId, String textureName, boolean hasEmissiveTexture) {
        //TODO: When moving away from enum system to a registry-like system, remove hardcoded Tardis Refined modid
        ResourceLocation themeId = new ResourceLocation(TardisRefined.MODID, theme.getSerializedName().toLowerCase(Locale.ENGLISH));
        ConsolePatternCollection collection;
        ConsolePattern pattern = (ConsolePattern) new ConsolePattern(patternId, new PatternTexture(createConsolePatternTextureLocation(theme,textureName), hasEmissiveTexture)).setThemeId(themeId);
        if (DEFAULT_PATTERNS.containsKey(themeId)) {
            collection = DEFAULT_PATTERNS.get(themeId);
            List<ConsolePattern> currentList = new ArrayList<>();
            currentList.addAll(collection.patterns());
            currentList.add(pattern);
            collection.setPatterns(currentList);
            DEFAULT_PATTERNS.replace(themeId, collection);
        } else {
            collection = (ConsolePatternCollection) new ConsolePatternCollection(List.of(pattern)).setThemeId(themeId);
            DEFAULT_PATTERNS.put(themeId, collection);
        }
        if (!Platform.isProduction()) //Enable Logging in development environment
            TardisRefined.LOGGER.info("Adding ConsolePattern {} for {}", pattern.id(), themeId);
        return pattern;
    }

    /** @implSpec INTERNAL USE ONLY */
    private static ResourceLocation createConsolePatternTextureLocation(ConsoleTheme theme, String textureName){
        return new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/" + theme.getSerializedName().toLowerCase(Locale.ENGLISH) + "/" + textureName + ".png");
    }

    /** Gets a default list of {@link ConsolePattern} added by Tardis Refined. Useful as a fallback list.
     * <br> Requires calling {@link ConsolePatterns#registerDefaultPatterns} first
     * @implNote Used for datagen providers when we may need to lookup the map multiple times, but only need to register default entries once.
     * */
    public static Map<ResourceLocation, ConsolePatternCollection> getDefaultPatterns(){
        return DEFAULT_PATTERNS;
    }

    /** Registers the Tardis Refined default Console Patterns and returns a map of them by Theme ID
     * <br> Should only be called ONCE when needed*/
    public static Map<ResourceLocation, ConsolePatternCollection> registerDefaultPatterns() {
        DEFAULT_PATTERNS.clear();
        /*Add Base Textures*/
        for (ConsoleTheme consoleTheme : ConsoleTheme.values()) {
            String themeName = consoleTheme.name().toLowerCase(Locale.ENGLISH);
            boolean hasDefaultEmission = consoleTheme == ConsoleTheme.COPPER || consoleTheme == ConsoleTheme.CRYSTAL|| consoleTheme == ConsoleTheme.CORAL || consoleTheme == ConsoleTheme.FACTORY || consoleTheme == ConsoleTheme.INITIATIVE || consoleTheme == ConsoleTheme.TOYOTA || consoleTheme == ConsoleTheme.VICTORIAN;
            addDefaultPattern(consoleTheme, ResourceConstants.DEFAULT_PATTERN_ID.getPath(), themeName + "_console", hasDefaultEmission);
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
        addDefaultPattern(ConsoleTheme.CRYSTAL, "corrupted", "crystal_console_corrupted", true);

        /*Myst*/
        addDefaultPattern(ConsoleTheme.MYST, "molten", "myst_console_molten", false);

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
