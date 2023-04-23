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

public class PatternReloadListener<T extends PatternCollection> extends CodecJsonReloadListener<T> {

    protected PatternReloadListener(String folderName, Codec<T> codec) {
        super(folderName, codec);
    }

    @Override
    protected Map<ResourceLocation, T> mapValues(Map<ResourceLocation, JsonElement> inputs) {
        Map<ResourceLocation, T> entries = new HashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : inputs.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonElement element = entry.getValue();
            // if we fail to parse json, log an error and continue
            // if we succeeded, add the resulting PatternCollection to the map and set the theme id
            this.codec.decode(JsonOps.INSTANCE, element)
                    .get()
                    .ifLeft(result -> {entries.put(key, (T) result.getFirst().setThemeId(key)); TardisRefined.LOGGER.info("Adding entry {}", key);}) //Must call setThemeId
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
