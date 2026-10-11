package whocraft.tardis_refined.patterns;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.MergeableCodecJsonReloadListener;
import whocraft.tardis_refined.common.util.MiscHelper;

import java.io.Reader;
import java.util.*;
import java.util.function.Function;

/**
 * Slightly modified version of CodecJsonReloadListener to account for needing to call setThemeId on the PatternCollection
 */
public class PatternReloadListener<T extends PatternCollection, B extends BasePattern> extends MergeableCodecJsonReloadListener<T, List<B>> {

    protected PatternReloadListener(String folderName, Codec<T> codec, final Function<List<T>, List<B>> merger) {
        super(folderName, codec, merger);
    }

    public static <B extends BasePattern, C extends PatternCollection> ArrayList<B> processPatternCollections(final List<C> patternCollections) {
        return patternCollections.stream().reduce(new ArrayList<B>(), PatternReloadListener::mergeOrReplacePatterns, MiscHelper::unionList);
    }

    public static <B extends BasePattern, C extends PatternCollection> ArrayList<B> mergeOrReplacePatterns(ArrayList<B> set, final C nextPatternCollection) {
        return addPatterns(nextPatternCollection.isReplace() ? new ArrayList<B>() : set, nextPatternCollection.patterns());
    }

    public static <B extends BasePattern> ArrayList<B> addPatterns(final List<B> finalPatterns, final List<B> patterns) {
        ArrayList<B> finalPatternsModified = MiscHelper.unionList(finalPatterns, patterns);
        return finalPatternsModified;
    }

    /**
     * Need to create this static method here as the Architectuary ExpectPlatform Annotation used in CodecJsonReloadListener requires a static builder method to construct an instance
     */
    public static <P extends PatternCollection, B extends BasePattern> PatternReloadListener<P, B> createListener(String folderName, Codec<P> codec, final Function<List<P>, List<B>> merger){
        return whocraft.tardis_refined.patterns.fabric.PatternReloadListenerImpl.createListener(folderName, codec, merger);
    }

    @Override
    protected Map<Identifier, List<B>> mapValues(Map<Identifier, List<Resource>> inputs) {
        Map<Identifier, List<B>> entries = new HashMap<>();

        for (var entry : inputs.entrySet()) {

            List<T> raws = new ArrayList<>();
            Identifier fullId = entry.getKey();
            String fullPath = fullId.getPath(); // includes folderName/ and .json
            Identifier key = Identifier.fromNamespaceAndPath(fullId.getNamespace(), fullPath.substring(this.folderName.length() + 1, fullPath.length() - EXTENSION_LENGTH));

            for (Resource resource : entry.getValue()) {
                try (Reader reader = resource.openAsReader()) {
                    JsonElement element = JsonParser.parseReader(reader);

                    // if we fail to parse json, log an error and continue
                    // if we succeeded, add the resulting T to the map
                    T decoded = this.codec.parse(JsonOps.INSTANCE, element).getOrThrow();
                    raws.add((T) decoded.setThemeId(key));
                    LOGGER.info("Adding Pattern {} for {}", fullId, key);
                } catch (Exception e) {
                    LOGGER.error(String.format(Locale.ENGLISH, "Error reading resource %s in folder %s from pack %s: ", key, this.folderName, resource.sourcePackId()), e);
                }
            }
            //Apply merging function on all raw files
            entries.put(key, this.merger.apply(raws));
        }
        return entries;
    }
}
