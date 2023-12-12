package whocraft.tardis_refined.common.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatternCollection;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ShellPatternProvider implements DataProvider {

    protected final DataGenerator generator;
    private final boolean addDefaults;
    protected Map<ResourceLocation, ShellPatternCollection> data = new HashMap<>();

    public ShellPatternProvider(DataGenerator generator) {
        this(generator, true);
    }

    public ShellPatternProvider(DataGenerator generator, boolean addDefaults) {
        Preconditions.checkNotNull(generator);
        this.generator = generator;
        this.addDefaults = addDefaults;
    }

    /**
     * To be used by child classes to add new patterns after defaults are registered
     */
    protected void addPatterns() {
    }

    @Override
    public CompletableFuture<?> run(CachedOutput arg) {
        this.data.clear();

        final List<CompletableFuture<?>> futures = new ArrayList<>();

        if (this.addDefaults) {
            ShellPatterns.registerDefaultPatterns();
            data.putAll(ShellPatterns.getDefaultPatternsDatagen());
        }

        this.addPatterns();

        if (!data.isEmpty()) {
            data.entrySet().forEach(entry -> {
                try {
                    ShellPatternCollection patternCollection = entry.getValue();
                    JsonObject currentPatternCollection = ShellPatternCollection.CODEC.encodeStart(JsonOps.INSTANCE, patternCollection).get()
                            .ifRight(right -> {
                                TardisRefined.LOGGER.error(right.message());
                            }).orThrow().getAsJsonObject();
                    Path output = getPath(patternCollection.themeId());
                    futures.add(DataProvider.saveStable(arg, currentPatternCollection, output));
                } catch (Exception exception) {
                    TardisRefined.LOGGER.debug("Issue writing ShellPatternCollection {}! Error: {}", entry.getValue().themeId(), exception.getMessage());
                }
            });
        }
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    protected ShellPattern addPatternToDatagen(ResourceLocation themeId, ShellPattern shellPattern) {
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

    protected ResourceLocation exteriorTextureLocation(ResourceLocation themeId) {
        return new ResourceLocation(themeId.getNamespace(), "textures/blockentity/shell/" + themeId.getPath() + "/" + themeId.getPath() + ".png");
    }

    protected ResourceLocation interiorTextureLocation(ResourceLocation themeId) {
        return new ResourceLocation(themeId.getNamespace(), "textures/blockentity/shell/" + themeId.getPath() + "/" + themeId.getPath() + "_interior.png");
    }

    protected Path getPath(ResourceLocation themeId) {
        return generator.getPackOutput().getOutputFolder().resolve("data/" + TardisRefined.MODID + "/" + TardisRefined.MODID + "/patterns/shell/" + themeId.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Shell Patterns";
    }
}
