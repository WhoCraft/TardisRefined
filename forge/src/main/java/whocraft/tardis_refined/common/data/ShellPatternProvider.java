package whocraft.tardis_refined.common.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.ChatFormatting;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.patterns.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class ShellPatternProvider implements DataProvider {

    protected final DataGenerator generator;

    protected Map<ResourceLocation, ShellPatternCollection> data = new HashMap<>();

    private final boolean addDefaults;

    public ShellPatternProvider(DataGenerator generator) {
        this(generator, true);
    }

    public ShellPatternProvider(DataGenerator generator, boolean addDefaults) {
        Preconditions.checkNotNull(generator);
        this.generator = generator;
        this.addDefaults = addDefaults;
    }

    /** To be used by child classes to add new patterns after defaults are registered*/
    protected void addPatterns(){}

    @Override
    public void run(CachedOutput arg) throws IOException {
        this.data.clear();

        if(this.addDefaults){
            ShellPatterns.registerDefaultPatterns();
            data.putAll(ShellPatterns.getDefaultPatterns());
        }

        this.addPatterns();

        if (!data.isEmpty()){
            data.entrySet().forEach(entry -> {
                try {
                    ShellPatternCollection patternCollection = entry.getValue();
                    JsonObject currentPatternCollection = ShellPatternCollection.CODEC.encodeStart(JsonOps.INSTANCE, patternCollection).get()
                            .ifRight(right -> {
                                TardisRefined.LOGGER.error(right.message());
                            }).orThrow().getAsJsonObject();
                    Path output = getPath(patternCollection.themeId());
                    DataProvider.saveStable(arg, currentPatternCollection, output);
                } catch (Exception exception) {
                    TardisRefined.LOGGER.debug("Issue writing ShellPatternCollection {}! Error: {}", entry.getValue().themeId(), exception.getMessage());
                }
            });
        }
    }

    protected ShellPattern addPatternToDatagen(ShellTheme theme, ShellPattern shellPattern) {
        //TODO: When moving away from enum system to a registry-like system, remove hardcoded Tardis Refined modid
        ResourceLocation themeId = new ResourceLocation(TardisRefined.MODID, theme.getSerializedName().toLowerCase(Locale.ENGLISH));
        ShellPattern pattern = (ShellPattern) shellPattern.setThemeId(themeId);
        ShellPatternCollection collection;
        if (this.data.containsKey(themeId)) {
            collection = this.data.get(themeId);
            List<ShellPattern> currentList = new ArrayList<>();
            currentList.addAll(collection.patterns());
            currentList.add(pattern);
            collection.setPatterns(currentList);
            this.data.replace(themeId, collection);
        } else {
            collection = (ShellPatternCollection) new ShellPatternCollection(List.of(pattern)).setThemeId(themeId);
            this.data.put(themeId, collection);
        }
        TardisRefined.LOGGER.info("Adding ShellPattern {} for {}", pattern.id(), themeId);
        return pattern;
    }

    protected ResourceLocation exteriorTextureLocation(ResourceLocation themeId){
        return new ResourceLocation(themeId.getNamespace(), "textures/blockentity/shell/" + themeId.getPath() + "/" + themeId.getPath() + ".png");
    }

    protected ResourceLocation interiorTextureLocation(ResourceLocation themeId){
        return new ResourceLocation(themeId.getNamespace(), "textures/blockentity/shell/" + themeId.getPath() + "/" + themeId.getPath() + "_interior.png");
    }

    protected Path getPath(ResourceLocation themeId) {
        return generator.getOutputFolder().resolve("data/" + TardisRefined.MODID + "/" + TardisRefined.MODID + "/patterns/shell/" + themeId.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Shell Patterns";
    }
}
