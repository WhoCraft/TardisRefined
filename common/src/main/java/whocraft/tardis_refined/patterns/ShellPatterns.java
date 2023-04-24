package whocraft.tardis_refined.patterns;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.constants.ResourceConstants;

import java.util.*;
/** Data Manager for all {@link ShellPattern}(s) */
public class ShellPatterns {
    public static PatternReloadListener<ShellPatternCollection> PATTERNS = PatternReloadListener.createListener(TardisRefined.MODID + "/patterns/shell", ShellPatternCollection.CODEC);

    private static Map<ResourceLocation, ShellPatternCollection> DEFAULT_PATTERNS = new HashMap();

    public static PatternReloadListener<ShellPatternCollection> getReloadListener(){
        return PATTERNS;
    }

    public static Map<ResourceLocation, ShellPatternCollection> getRegistry() {
        return PATTERNS.getData();
    }


    /** Lookup the list of {@link ShellPattern}(s) in a {@link ShellPatternCollection} for a given {@link ShellTheme}*/
    public static List<ShellPattern> getPatternsForTheme(ShellTheme shellTheme) {
        return PATTERNS.getData().get(new ResourceLocation(TardisRefined.MODID, shellTheme.getSerializedName().toLowerCase(Locale.ENGLISH))).patterns();
    }

    /** Retrieves a pattern from a default list of patterns, for use when Capabiliteis or Cardinal Components classloads patterns before datapack loading*/
    public static List<ShellPattern> getPatternsForThemeDefault(ShellTheme shellTheme) {
        return DEFAULT_PATTERNS.get(new ResourceLocation(TardisRefined.MODID, shellTheme.getSerializedName().toLowerCase(Locale.ENGLISH))).patterns();
    }

    /** Helper method to get a {@link ShellPatternCollection} by theme ID */
    public static ShellPatternCollection getPatternCollectionForTheme(ShellTheme shellTheme) {
        return PATTERNS.getData().get(new ResourceLocation(TardisRefined.MODID, shellTheme.getSerializedName().toLowerCase(Locale.ENGLISH)));
    }

    /** Lookup a {@link ShellTheme} based on a singular {@link ShellPattern}
     * <br> As there is a many-to-one relationship between {@link ShellPattern} and {@link ShellTheme}
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

    /** Sanity check to make sure a Pattern for a {@link ShellTheme} exists
     * <br> A likely use case for this is when entries for the patterns are being modified in some way, such as when something triggers datapacks to be reloaded*/
    public static boolean doesPatternExist(ShellTheme ShellTheme, ResourceLocation id) {
        List<ShellPattern> basePatterns = getPatternsForTheme(ShellTheme);
        for (ShellPattern basePattern : basePatterns) {
            if (Objects.equals(basePattern.id(), id)) {
                return true;
            }
        }
        return false;
    }

    /** Lookup up a {@link ShellPattern} within a particular {@link ShellTheme} or get the first one in the list if the input pattern id cannot be found*/
    public static ShellPattern getPatternOrDefault(ShellTheme ShellTheme, ResourceLocation id) {
        List<ShellPattern> basePatterns = getPatternsForTheme(ShellTheme);
        for (ShellPattern basePattern : basePatterns) {
            if (Objects.equals(basePattern.id(), id)) {
                return basePattern;
            }
        }
        return basePatterns.get(0);
    }

    public static ShellPattern next(ShellTheme shellTheme, ShellPattern currentPattern) {
        ShellPatternCollection collection = getPatternCollectionForTheme(shellTheme);
        return next(collection, currentPattern);
    }

    /** Helper to get the next available {@link ShellPattern} in the current {@link ShellPatternCollection}*/
    public static ShellPattern next(ShellPatternCollection collection, ShellPattern currentPattern) {
        return next(collection.patterns(), currentPattern);
    }

    public static ShellPattern next(List<ShellPattern> patterns, ShellPattern currentPattern) {
        if(currentPattern == null){
            return patterns.get(0);
        }

        int prevIndex = patterns.indexOf(currentPattern);
        if (prevIndex > patterns.size() || prevIndex + 1 >= patterns.size()) {
            return patterns.get(0);
        }
        return patterns.get(prevIndex + 1);
    }

    /** Constructs and a {@link ShellPattern}, then adds it to a {@link ShellPatternCollection}, which is assigned to a {@link ShellTheme}.
     * <br> The {@link ShellPatternCollection} is then added to an internal default map
     * <br> Also assigns the {@link ShellPattern} its parent {@link ShellTheme}'s ID
     * @implSpec INTERNAL USE ONLY
     * */
    private static ShellPattern addDefaultPattern(ShellTheme theme, ShellPattern datagenPattern) {
        ResourceLocation themeId = new ResourceLocation(TardisRefined.MODID, theme.getSerializedName().toLowerCase(Locale.ENGLISH));
        ShellPatternCollection collection;
        ShellPattern pattern = (ShellPattern) datagenPattern.setThemeId(themeId);
        if (DEFAULT_PATTERNS.containsKey(themeId)) {
            collection = DEFAULT_PATTERNS.get(themeId);
            List<ShellPattern> currentList = new ArrayList<>();
            currentList.addAll(collection.patterns());
            currentList.add(pattern);
            collection.setPatterns(currentList);
            DEFAULT_PATTERNS.replace(themeId, collection);
        } else {
            collection = (ShellPatternCollection) new ShellPatternCollection(List.of(pattern)).setThemeId(themeId);
            DEFAULT_PATTERNS.put(themeId, collection);
        }
        if (!Platform.isProduction()) //Enable Logging in development environment
            TardisRefined.LOGGER.info("Adding Shell Pattern {} for {}", pattern.id(), themeId);
        return pattern;
    }

    private static ShellPattern addDefaultPattern(ShellTheme theme, String patternId, boolean hasEmissiveTexture) {
        //TODO: When moving away from enum system to a registry-like system, remove hardcoded Tardis Refined modid
        ResourceLocation themeId = new ResourceLocation(TardisRefined.MODID, theme.getSerializedName().toLowerCase(Locale.ENGLISH));
        ShellPattern pattern = (ShellPattern) new ShellPattern(patternId, new PatternTexture(exteriorTextureLocation(theme, patternId), hasEmissiveTexture)
                , new PatternTexture(interiorTextureLocation(theme, patternId), hasEmissiveTexture)).setThemeId(themeId);
        return addDefaultPattern(theme, pattern);
    }

    private static ResourceLocation exteriorTextureLocation(ShellTheme shellTheme, String textureName){
        return new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/" + shellTheme.getSerializedName().toLowerCase(Locale.ENGLISH) + "/" + textureName + ".png");
    }

    private static ResourceLocation interiorTextureLocation(ShellTheme shellTheme, String textureName){
        return new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/" + shellTheme.getSerializedName().toLowerCase(Locale.ENGLISH) + "/" + textureName + "_interior.png");
    }

    /** Gets a default list of Shell Patterns added by Tardis Refined. Useful as a fallback list.
     * <br> Requires calling {@link ShellPatterns#registerDefaultPatterns} first
     * @implNote Used for datagen providers when we may need to lookup the map multiple times, but only need to register default entries once.
     * */
    public static Map<ResourceLocation, ShellPatternCollection> getDefaultPatterns(){
        return DEFAULT_PATTERNS;
    }

    /** Registers the Tardis Refined default Shell Patterns and returns a map of them by Theme ID
     * <br> Should only be called ONCE when needed*/
    public static Map<ResourceLocation, ShellPatternCollection> registerDefaultPatterns() {
        DEFAULT_PATTERNS.clear();
        /*Add Base Textures*/
        for (ShellTheme shellTheme : ShellTheme.values()) {
            boolean hasDefaultEmission = shellTheme == ShellTheme.MYSTIC || shellTheme == ShellTheme.NUKA || shellTheme == ShellTheme.PAGODA || shellTheme == ShellTheme.PHONE_BOOTH || shellTheme == ShellTheme.POLICE_BOX || shellTheme == ShellTheme.VENDING;
            String textureName = shellTheme.getSerializedName().toLowerCase(Locale.ENGLISH);
            //Use an overload version of the method for default shells because the texture files were named based on shell theme name
            ShellPattern pattern = new ShellPattern(ResourceConstants.DEFAULT_PATTERN_ID.getPath(), new PatternTexture(exteriorTextureLocation(shellTheme, textureName), hasDefaultEmission)
                    , new PatternTexture(interiorTextureLocation(shellTheme, textureName), hasDefaultEmission));
            addDefaultPattern(shellTheme, pattern);
        }

        addDefaultPattern(ShellTheme.POLICE_BOX, "marble", false);
        addDefaultPattern(ShellTheme.POLICE_BOX, "gaudy", false);
        addDefaultPattern(ShellTheme.POLICE_BOX, "metal", false);
        addDefaultPattern(ShellTheme.POLICE_BOX, "stone", false);
        addDefaultPattern(ShellTheme.POLICE_BOX, "red", false);

        addDefaultPattern(ShellTheme.PHONE_BOOTH, "metal", false);

        addDefaultPattern(ShellTheme.PRESENT, "cardboard", false);

        addDefaultPattern(ShellTheme.BRIEFCASE, "intel", false);
        addDefaultPattern(ShellTheme.BRIEFCASE, "metal", false);
        addDefaultPattern(ShellTheme.BRIEFCASE, "mesa", false);

        addDefaultPattern(ShellTheme.MYSTIC, "dwarven", false);

        addDefaultPattern(ShellTheme.BIG_BEN, "gothic", false);

        return DEFAULT_PATTERNS;
    }

}
