package whocraft.tardis_refined.patterns;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.constants.ResourceConstants;
import whocraft.tardis_refined.patterns.sound.ConsoleSoundProfile;
import whocraft.tardis_refined.patterns.sound.TRConsoleSoundProfiles;
import whocraft.tardis_refined.registry.TRConsoleThemes;

import java.util.*;

/**
 * Data manager for all {@link ConsolePattern}(s)
 */
public class ConsolePatterns{

    private static PatternReloadListener<ConsolePatternCollection, ConsolePattern> PATTERNS = PatternReloadListener.createListener(TardisRefined.MODID + "/patterns/console", ConsolePatternCollection.CODEC, patternCollections -> PatternReloadListener.processPatternCollections(patternCollections));

    private static Map<ResourceLocation, List<ConsolePattern>> DEFAULT_PATTERNS = new HashMap();

    public static final ConsolePattern DEFAULT = (ConsolePattern) new ConsolePattern(ResourceConstants.DEFAULT_PATTERN_ID, new PatternTexture(createConsolePatternTextureLocation(TRConsoleThemes.FACTORY.getId(), TRConsoleThemes.FACTORY.getId().getPath() + "_console"), true), Optional.of(TRConsoleSoundProfiles.DEFAULT_SOUND_PROFILE)).setThemeId(TRConsoleThemes.FACTORY.getId());

    public static PatternReloadListener<ConsolePatternCollection, ConsolePattern> getReloadListener(){
        return PATTERNS;
    }

    public static Map<ResourceLocation, List<ConsolePattern>> getRegistry() {
        return PATTERNS.getData();
    }

    /** Lookup the list of {@link ConsolePattern}(s) in a {@link ConsolePatternCollection} for a given {@link ConsoleTheme}*/
    public static List<ConsolePattern> getPatternsForTheme(ResourceLocation consoleThemeId) {
        return PATTERNS.getData().get(consoleThemeId);
    }

    /** Retrieves a pattern from a default list of patterns, for use when Capabiliteis or Cardinal Components classloads patterns before datapack loading*/
    public static List<ConsolePattern> getPatternsForThemeDefault(ResourceLocation consoleThemeId) {
        return DEFAULT_PATTERNS.get(consoleThemeId);
    }

    /** Helper method to get a {@link ConsolePatternCollection} by theme ID */
    public static List<ConsolePattern> getPatternCollectionForTheme(ResourceLocation consoleThemeId) {
        return PATTERNS.getData().get(consoleThemeId);
    }

    /** Lookup a {@link ConsoleTheme} based on a singular {@link ConsolePattern}
     * <br> As there is a many-to-one relationship between {@link ConsolePattern} and {@link ConsoleTheme}
     * <br> as well as a one-to-one relationship between a {@link ShellPatternCollection} and {@link ShellTheme},
     * we will iterate through all {@link ShellPatternCollection} (which holds the theme ID) and find matchine ones*/
    public static ConsoleTheme getThemeForPattern(ConsolePattern pattern) {
        Map<ResourceLocation, List<ConsolePattern>> entries = ConsolePatterns.getRegistry();
        for (Map.Entry<ResourceLocation, List<ConsolePattern>> entry : entries.entrySet()){
            if (pattern.getThemeId() == entry.getKey()){
                return TRConsoleThemes.CONSOLE_THEME_REGISTRY.get(entry.getKey());
            }
        }
        return TRConsoleThemes.FACTORY.get();
    }

    /** Sanity check to make sure a Pattern for a ConsoleTheme exists
     * <br> A likely use case for this is when entries for the patterns are being modified in some way, such as when something triggers datapacks to be reloaded*/
    public static boolean doesPatternExist(ResourceLocation consoleThemeId, ResourceLocation patternId) {
        List<ConsolePattern> consolePatterns = getPatternsForTheme(consoleThemeId);
        for (ConsolePattern consolePattern : consolePatterns) {
            if (Objects.equals(consolePattern.id(), patternId)) {
                return true;
            }
        }
        return false;
    }

    /** Lookup up a {@link ConsolePattern} within a particular {@link ConsoleTheme} or get the first one in the list if the input pattern id cannot be found*/
    public static ConsolePattern getPatternOrDefault(ResourceLocation consoleThemeId, ResourceLocation id) {
        List<ConsolePattern> consolePatterns = getPatternsForTheme(consoleThemeId);
        for (ConsolePattern consolePattern : consolePatterns) {
            if (Objects.equals(consolePattern.id(), id)) {
                return consolePattern;
            }
        }
        return consolePatterns.get(0);
    }

    public static ConsolePattern next(ResourceLocation consoleThemeId, ConsolePattern currentPattern) {
        List<ConsolePattern> collection = getPatternCollectionForTheme(consoleThemeId);
        return next(collection, currentPattern);
    }

    /** Helper to get the next available {@link ConsolePattern} in the current {@link ConsolePatternCollection}*/
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

    private static ConsolePattern addDefaultPattern(ResourceLocation themeId, String patternId, String textureName, boolean hasEmissiveTexture) {
        return addDefaultPattern(themeId, patternId, textureName, hasEmissiveTexture, Optional.of(TRConsoleSoundProfiles.DEFAULT_SOUND_PROFILE));
    }


    /** Constructs and a {@link ConsolePattern}, then adds it to a {@link ConsolePatternCollection}, which is assigned to a {@link ConsoleTheme}.
     * <br> The {@link ConsolePatternCollection} is then added to an internal default map
     * <br> Also assigns the {@link ConsolePattern} its parent {@link ConsoleTheme}'s ID
     * @implSpec INTERNAL USE ONLY
     * */
    private static ConsolePattern addDefaultPattern(ResourceLocation themeId, String patternId, String textureName, boolean hasEmissiveTexture, Optional<ConsoleSoundProfile> soundProfile) {
        List<ConsolePattern> consolePatternList;
        ConsolePattern pattern = (ConsolePattern) new ConsolePattern(patternId, new PatternTexture(createConsolePatternTextureLocation(themeId,textureName), hasEmissiveTexture), soundProfile).setThemeId(themeId);

        if (DEFAULT_PATTERNS.containsKey(themeId)) {
            consolePatternList = DEFAULT_PATTERNS.get(themeId);
            List<ConsolePattern> currentList = new ArrayList<>();
            currentList.addAll(consolePatternList);
            currentList.add(pattern);
            DEFAULT_PATTERNS.replace(themeId, currentList);
        } else {
            consolePatternList = List.of(pattern);
            DEFAULT_PATTERNS.put(themeId, consolePatternList);
        }
        if (!Platform.isProduction()) //Enable Logging in development environment
            TardisRefined.LOGGER.info("Adding ConsolePattern {} for {}", pattern.id(), themeId);
        return pattern;
    }

    /** @implSpec INTERNAL USE ONLY */
    private static ResourceLocation createConsolePatternTextureLocation(ResourceLocation themeId, String textureName){
        ResourceLocation texture = new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/" + themeId.getPath().toLowerCase(Locale.ENGLISH) + "/" + textureName + ".png");
       
        return texture;
    }

    /** Gets a default list of {@link ConsolePattern} added by Tardis Refined. Useful as a fallback list.
     * <br> Requires calling {@link ConsolePatterns#registerDefaultPatterns} first
     * @implNote Used for datagen providers when we may need to lookup the map multiple times, but only need to register default entries once.
     * */
    public static Map<ResourceLocation, List<ConsolePattern>> getDefaultPatterns(){
        return DEFAULT_PATTERNS;
    }

    public static Map<ResourceLocation, ConsolePatternCollection> getDefaultPatternsDatagen(){
        Map<ResourceLocation, ConsolePatternCollection> defaults = new HashMap<>();
        DEFAULT_PATTERNS.entrySet().forEach(entry -> defaults.put(entry.getKey(), (ConsolePatternCollection) new ConsolePatternCollection(entry.getValue()).setThemeId(entry.getKey())));
        return defaults;
    }

    /** Registers the Tardis Refined default Console Patterns and returns a map of them by Theme ID
     * <br> Should only be called ONCE when needed*/
    public static Map<ResourceLocation, List<ConsolePattern>> registerDefaultPatterns() {
        DEFAULT_PATTERNS.clear();
        /*Add Base Textures*/
        for (ResourceLocation consoleTheme : TRConsoleThemes.CONSOLE_THEME_REGISTRY.keySet()) {
            boolean hasDefaultEmission = consoleTheme == TRConsoleThemes.COPPER.getId() || consoleTheme == TRConsoleThemes.CRYSTAL.getId() || consoleTheme == TRConsoleThemes.CORAL.getId() || consoleTheme == TRConsoleThemes.FACTORY.getId() || consoleTheme == TRConsoleThemes.INITIATIVE.getId() || consoleTheme == TRConsoleThemes.TOYOTA.getId() || consoleTheme == TRConsoleThemes.VICTORIAN.getId();
            addDefaultPattern(consoleTheme, ResourceConstants.DEFAULT_PATTERN_ID.getPath(), consoleTheme.getPath() + "_console", hasDefaultEmission);
        }

        /*Coral*/
        addDefaultPattern(TRConsoleThemes.CORAL.getId(), "war", "coral_console_war", true);
        addDefaultPattern(TRConsoleThemes.CORAL.getId(), "blue", "coral_console_blue", true);

        /*Factory*/
        addDefaultPattern(TRConsoleThemes.FACTORY.getId(), "vintage", "factory_console_vintage", true);
        addDefaultPattern(TRConsoleThemes.FACTORY.getId(), "mint", "factory_console_mint", true);
        addDefaultPattern(TRConsoleThemes.FACTORY.getId(), "wood", "factory_console_wood", true);

        /*Toyota*/
        addDefaultPattern(TRConsoleThemes.TOYOTA.getId(), "violet", "toyota_console_purple", true);
        addDefaultPattern(TRConsoleThemes.TOYOTA.getId(), "blue", "toyota_console_blue", true);
        addDefaultPattern(TRConsoleThemes.TOYOTA.getId(), "skulk", "toyota_console_skulk", false);

        /*Crystal*/
        addDefaultPattern(TRConsoleThemes.CRYSTAL.getId(), "corrupted", "crystal_console_corrupted", true);

        /*Myst*/
        addDefaultPattern(TRConsoleThemes.MYST.getId(), "molten", "myst_console_molten", false);

        /*Victorian*/
        addDefaultPattern(TRConsoleThemes.VICTORIAN.getId(), "smissmass", "victorian_console_smissmass", false);
        addDefaultPattern(TRConsoleThemes.VICTORIAN.getId(), "grant", "victorian_console_grant", false);

        /*Initiative*/
        addDefaultPattern(TRConsoleThemes.INITIATIVE.getId(), "aperture", "initiative_console_aperture", true);
        addDefaultPattern(TRConsoleThemes.INITIATIVE.getId(), "blue", "initiative_console_blue", true);
        addDefaultPattern(TRConsoleThemes.INITIATIVE.getId(), "construction", "initiative_console_construction", false);

        // Nuka
        addDefaultPattern(TRConsoleThemes.NUKA.getId(), "industrial", "nuka_industrial", false);
        addDefaultPattern(TRConsoleThemes.NUKA.getId(), "cool", "nuka_cool", false);

        /*Copper*/
        addDefaultPattern(TRConsoleThemes.COPPER.getId(), "sculk", "copper_console_sculk", false);

        return new HashMap<>(DEFAULT_PATTERNS);
    }
}
