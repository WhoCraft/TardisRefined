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
import whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

import static whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns.addPattern;

public class ConsolePatternProvider implements DataProvider {

    protected final DataGenerator generator;

    public ConsolePatternProvider(DataGenerator generator) {
        Preconditions.checkNotNull(generator);
        this.generator = generator;
    }

    public static void registerPatterns() {

        /*Add Base Textures*/
        for (ConsoleTheme consoleTheme : ConsoleTheme.values()) {
            String themeName = consoleTheme.name().toLowerCase(Locale.ENGLISH);
            addPattern(consoleTheme, new ConsolePatterns.Pattern(consoleTheme, new ResourceLocation(TardisRefined.MODID, "default"), themeName + "/" + themeName + "_console"));
        }

        /*Coral*/
        addPattern(ConsoleTheme.CORAL, new ConsolePatterns.Pattern(ConsoleTheme.CORAL, new ResourceLocation(TardisRefined.MODID, "blue"), "coral/coral_console_blue"));
        addPattern(ConsoleTheme.CORAL, new ConsolePatterns.Pattern(ConsoleTheme.CORAL, new ResourceLocation(TardisRefined.MODID, "war"), "coral/coral_console_war"));

        /*Factory*/
        addPattern(ConsoleTheme.FACTORY, new ConsolePatterns.Pattern(ConsoleTheme.FACTORY, new ResourceLocation(TardisRefined.MODID, "vintage"), "factory/factory_console_vintage"));
        addPattern(ConsoleTheme.FACTORY, new ConsolePatterns.Pattern(ConsoleTheme.FACTORY, new ResourceLocation(TardisRefined.MODID, "mint"), "factory/factory_console_mint"));

        /*Toyota*/
        addPattern(ConsoleTheme.TOYOTA, new ConsolePatterns.Pattern(ConsoleTheme.TOYOTA, new ResourceLocation(TardisRefined.MODID, "violet"), "toyota/toyota_texture_purple"));
        addPattern(ConsoleTheme.TOYOTA, new ConsolePatterns.Pattern(ConsoleTheme.TOYOTA, new ResourceLocation(TardisRefined.MODID, "blue"), "toyota/toyota_texture_blue"));

        /*Crystal*/
        addPattern(ConsoleTheme.CRYSTAL, new ConsolePatterns.Pattern(ConsoleTheme.CRYSTAL, new ResourceLocation(TardisRefined.MODID, "corrupted"), "crystal/crystal_console_corrupted"));

        /*Myst*/
        addPattern(ConsoleTheme.MYST, new ConsolePatterns.Pattern(ConsoleTheme.MYST, new ResourceLocation(TardisRefined.MODID, "molten"), "myst/myst_console_molten"));

        /*Victorian*/
        addPattern(ConsoleTheme.VICTORIAN, new ConsolePatterns.Pattern(ConsoleTheme.VICTORIAN, new ResourceLocation(TardisRefined.MODID, "smissmass"), "victorian/victorian_console_smissmass"));
        addPattern(ConsoleTheme.VICTORIAN, new ConsolePatterns.Pattern(ConsoleTheme.VICTORIAN, new ResourceLocation(TardisRefined.MODID, "grant"), "victorian/victorian_console_grant"));

        /*Initiative*/
        addPattern(ConsoleTheme.INITIATIVE, new ConsolePatterns.Pattern(ConsoleTheme.INITIATIVE, new ResourceLocation(TardisRefined.MODID, "aperture"), "initiative/initiative_console_aperture"));
        addPattern(ConsoleTheme.INITIATIVE, new ConsolePatterns.Pattern(ConsoleTheme.INITIATIVE, new ResourceLocation(TardisRefined.MODID, "blue"), "initiative/initiative_console_blue"));

    }


    @Override
    public void run(CachedOutput arg) throws IOException {
        registerPatterns();

        for (ConsoleTheme consoleTheme : ConsoleTheme.values()) {

            JsonArray patternArray = new JsonArray();

            for (ConsolePatterns.Pattern pattern : ConsolePatterns.getPatternsForTheme(consoleTheme)) {
                JsonObject currentPattern = new JsonObject();
                currentPattern.addProperty("id", pattern.id().toString());
                currentPattern.addProperty("texture", pattern.textureLocation().toString());
                currentPattern.addProperty("name", TardisRefined.GSON.toJson(Component.literal(pattern.name()).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))));
                patternArray.add(currentPattern);
            }

            DataProvider.saveStable(arg, patternArray, getPath(consoleTheme));

        }

    }

    private Path getPath(ConsoleTheme theme) {
        String themeName = theme.getSerializedName();
        return generator.getOutputFolder().resolve("data/" + TardisRefined.MODID + "/patterns/console/" + themeName + ".json");
    }

    @Override
    public String getName() {
        return "Console Patterns";
    }
}
