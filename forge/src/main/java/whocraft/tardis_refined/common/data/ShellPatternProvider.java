package whocraft.tardis_refined.common.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.patterns.Pattern;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

public class ShellPatternProvider implements DataProvider {

    protected final DataGenerator generator;

    public ShellPatternProvider(DataGenerator generator) {
        Preconditions.checkNotNull(generator);
        this.generator = generator;
    }

    public static void registerPatterns() {

        /*Add Base Textures*/
        for (ShellTheme shellTheme : ShellTheme.values()) {
            String themeName = shellTheme.name().toLowerCase(Locale.ENGLISH);
            boolean hasDefaultEmission = shellTheme == ShellTheme.FACTORY;
            ShellPatterns.addPattern(shellTheme, new Pattern<>(shellTheme, new ResourceLocation(TardisRefined.MODID, "default"), createBasePatternLocation(themeName + "/" + themeName + "_shell"))).setEmissive(hasDefaultEmission);
        }
    }

    public static ResourceLocation createBasePatternLocation(String path) {
        return new ResourceLocation(TardisRefined.MODID, path);
    }

    @Override
    public void run(CachedOutput arg) throws IOException {
        registerPatterns();

        for (ShellTheme consoleTheme : ShellTheme.values()) {

            JsonArray patternArray = new JsonArray();

            for (Pattern<ShellTheme> pattern : ShellPatterns.getPatterns().get(consoleTheme)) {
                JsonObject currentPattern = new JsonObject();
                currentPattern.addProperty("id", pattern.id().toString());
                currentPattern.addProperty("emissive", pattern.emissive());
                currentPattern.addProperty("texture", pattern.texture().toString());
                currentPattern.addProperty("name_component", TardisRefined.GSON.toJson(Component.literal(pattern.name()).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))));
                patternArray.add(currentPattern);
            }

            DataProvider.saveStable(arg, patternArray, getPath(consoleTheme));

        }

    }

    private Path getPath(ShellTheme theme) {
        String themeName = theme.getSerializedName();
        return generator.getOutputFolder().resolve("data/" + TardisRefined.MODID + "/patterns/shell/" + themeName + ".json");
    }

    @Override
    public String getName() {
        return "Console Patterns";
    }
}
