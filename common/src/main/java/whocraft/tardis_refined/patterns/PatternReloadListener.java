package whocraft.tardis_refined.patterns;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.MergeableCodecJsonReloadListener;
import whocraft.tardis_refined.common.util.MiscHelper;

import java.io.Reader;
import java.util.*;
import java.util.function.Function;

/** Slightly modified version of CodecJsonReloadListener to account for needing to call setThemeId on the PatternCollection*/
public class PatternReloadListener<T extends PatternCollection, B extends BasePattern> extends MergeableCodecJsonReloadListener<T, Set<B>> {

    protected PatternReloadListener(String folderName, Codec<T> codec, final Function<List<T>, Set<B>> merger) {
        super(folderName, codec, merger);
    }

    @Override
    protected Map<ResourceLocation, Set<B>> mapValues(Map<ResourceLocation, List<Resource>> inputs) {
        Map<ResourceLocation, Set<B>> entries = new HashMap<>();

        for (var entry : inputs.entrySet()) {

            List<T> raws = new ArrayList<>();
            ResourceLocation fullId = entry.getKey();
            String fullPath = fullId.getPath(); // includes folderName/ and .json
            ResourceLocation key = new ResourceLocation(fullId.getNamespace(), fullPath.substring(this.folderName.length() + 1, fullPath.length() - EXTENSION_LENGTH));

            for (Resource resource : entry.getValue()){
                try(Reader reader = resource.openAsReader()) {
                    JsonElement element = JsonParser.parseReader(reader);

                    // if we fail to parse json, log an error and continue
                    // if we succeeded, add the resulting T to the map
                    this.codec.decode(JsonOps.INSTANCE, element)
                            .get()
                            .ifLeft(result -> {
                                raws.add((T) result.getFirst().setThemeId(key));
                                TardisRefined.LOGGER.info("Adding entry for {}", key);})
                            .ifRight(partial -> TardisRefined.LOGGER.error("Error deserializing json {} in folder {} from pack {}: {}", key, this.folderName, resource.sourcePackId(), partial.message()));
                }
                catch(Exception e) {
                    TardisRefined.LOGGER.error(String.format(Locale.ENGLISH, "Error reading resource %s in folder %s from pack %s: ", key, this.folderName, resource.sourcePackId()), e);
                }
            }
            //Apply merging function on all raw files
            entries.put(key, this.merger.apply(raws));
        }
        return entries;
    }

    public static <B extends BasePattern, C extends PatternCollection> HashSet<B> processPatternCollections(final List<C> patternCollections) {
        return patternCollections.stream().reduce(new HashSet<B>(), PatternReloadListener::mergeOrReplacePatterns, MiscHelper::unionSet);
    }

    public static <B extends BasePattern, C extends PatternCollection> HashSet<B> mergeOrReplacePatterns(HashSet<B> set, final C nextPatternCollection){
        return addPatterns(nextPatternCollection.isReplace() ? new HashSet<B>() : set, nextPatternCollection.patterns());
    }

    public static <B extends BasePattern> HashSet<B> addPatterns(final HashSet<B> finalPatterns, final List<B> patterns){
        finalPatterns.add((B)patterns);
        return finalPatterns;
    }

    /** Need to create this static method here as the Architectuary ExpectPlatform Annotation used in CodecJsonReloadListener requires a static builder method to construct an instance*/
    @ExpectPlatform
    public static <P extends PatternCollection, B extends BasePattern> PatternReloadListener<P, B> createListener(String folderName, Codec<P> codec, final Function<List<P>, Set<B>> merger) {
        throw new AssertionError();
    }
}
