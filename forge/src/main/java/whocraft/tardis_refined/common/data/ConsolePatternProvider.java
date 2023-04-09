package whocraft.tardis_refined.common.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.patterns.*;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConsolePatternProvider implements DataProvider {

    protected final DataGenerator generator;

    protected Map<ResourceLocation, ConsolePatternCollection> data = new HashMap<>();

    private final boolean addDefaults;

    public ConsolePatternProvider(DataGenerator generator) {
        this(generator, true);
    }

    public ConsolePatternProvider(DataGenerator generator, boolean addDefaults) {
        Preconditions.checkNotNull(generator);
        this.generator = generator;
        this.addDefaults = addDefaults;
    }

    protected void addPatterns(){}


    @Override
    public void run(CachedOutput arg) throws IOException {
        this.data.clear();

        if(this.addDefaults){
            ConsolePatterns.registerDefaultPatterns();
            data.putAll(ConsolePatterns.getDefaultPatterns());
        }

        this.addPatterns();

        if (!data.isEmpty()){
            data.entrySet().forEach(entry -> {
                try {
                    ConsolePatternCollection patternCollection = entry.getValue();
                    JsonObject currentPatternCollection = ConsolePatternCollection.CODEC.encodeStart(JsonOps.INSTANCE, patternCollection).get()
                            .ifRight(right -> {
                                TardisRefined.LOGGER.error(right.message());
                            }).orThrow().getAsJsonObject();
                    Path output = getPath(patternCollection.themeId());
                    DataProvider.saveStable(arg, currentPatternCollection, output);
                } catch (Exception exception) {
                    TardisRefined.LOGGER.debug("Issue writing ConsolePatternCollection {}! Error: {}", entry.getValue().themeId(), exception.getMessage());
                }
            });
        }
    }

    protected ConsolePattern addPatternToDatagen(ConsoleTheme theme, ConsolePattern consolePattern) {
        //TODO: When moving away from enum system to a registry-like system, remove hardcoded Tardis Refined modid
        ResourceLocation themeId = new ResourceLocation(TardisRefined.MODID, theme.getSerializedName().toLowerCase(Locale.ENGLISH));
        ConsolePattern pattern = (ConsolePattern) consolePattern.setThemeId(themeId);
        ConsolePatternCollection collection;
        if (this.data.containsKey(themeId)) {
            collection = this.data.get(themeId);
            collection.patterns().add(pattern);
            this.data.replace(themeId, collection);
        } else {
            collection = new ConsolePatternCollection(List.of(pattern));
            this.data.put(themeId, collection);
        }
        TardisRefined.LOGGER.info("Adding ConsolePattern {} for {}", pattern.id(), themeId);
        return pattern;
    }

    protected ResourceLocation createConsolePatternLocation(ResourceLocation path){
        return new ResourceLocation(path.getNamespace(), "textures/blockentity/console/" + path + ".png");
    }

    private ResourceLocation createConsolePatternLocation(String path){
        return new ResourceLocation(TardisRefined.MODID, "textures/blockentity/console/" + path + ".png");
    }

    protected Path getPath(ResourceLocation themeId) {
        return generator.getOutputFolder().resolve("data/" + TardisRefined.MODID + "/" + TardisRefined.MODID + "/patterns/console/" + themeId.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Console Patterns";
    }
}
