package whocraft.tardis_refined.patterns;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.CodecJsonReloadListener;

import java.util.HashMap;
import java.util.Map;
/** Slightly modified version of CodecJsonReloadListener to account for needing to call setThemeId on the PatternCollection*/
public class PatternReloadListener<T extends PatternCollection> extends CodecJsonReloadListener<T> {

    protected PatternReloadListener(String folderName, Codec<T> codec) {
        super(folderName, codec);
    }

    @Override
    protected Map<ResourceLocation, T> mapValues(Map<ResourceLocation, JsonElement> inputs) {
        Map<ResourceLocation, T> entries = new HashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : inputs.entrySet()) {
            ResourceLocation parsedInKey = entry.getKey();

            //Hardcode the pattern collection id (uses theme id) to use the Tardis Refined namespace
            //Since the theme Id is an enum, there is no way for other mods to add to it, so we can assume it is always in the Tardis Refined modid.
            //TODO: Move away from enum system in a way that allows themes to have a namespace as well as path. It would allow for all lookup methods to not require a hardcoded namespace like we currently do.
            ResourceLocation key = new ResourceLocation(TardisRefined.MODID, parsedInKey.getPath());

            JsonElement element = entry.getValue();
            // if we fail to parse json, log an error and continue
            // if we succeeded, add the resulting PatternCollection to the map and set the theme id
            this.codec.decode(JsonOps.INSTANCE, element)
                    .get()

                    //MUST call setThemeId as the constructor won't be initialising it since it will be used for its Codec.
                    .ifLeft(result -> {

                        //Merge or replace datapack patterns for the default Tardis Refined patterns for a matching PatternCollection
                        if (entries.containsKey(key)){

                            T existingPatternCollection = entries.get(key);
                            T newPatternCollection = result.getFirst();

                            //Set the themeId of the PatternCollection (required)
                            existingPatternCollection.setThemeId(key);
                            newPatternCollection.setThemeId(key);

                            //Merge or add to the existing pattern collection
                            existingPatternCollection.addOrMergePatterns(newPatternCollection);

                            entries.put(key, existingPatternCollection);
                            TardisRefined.LOGGER.info("Modifying entry {} from {}", key, parsedInKey);
                        }
                        else {
                            entries.put(key, (T) result.getFirst().setThemeId(key));
                            TardisRefined.LOGGER.info("Adding entry {}", key);
                        }

                    })
                    .ifRight(partial -> TardisRefined.LOGGER.error("Failed to parse data json for {} due to: {}", key, partial.message()));
        }
        return entries;
    }

    /** Need to create this static method here as the Architectuary ExpectPlatform Annotation used in CodecJsonReloadListener requires a static builder method to construct an instance*/
    @ExpectPlatform
    public static <P extends PatternCollection> PatternReloadListener<P> createListener(String folderName, Codec<P> codec) {
        throw new AssertionError();
    }
}
