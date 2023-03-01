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
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.patterns.Pattern;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

import static whocraft.tardis_refined.patterns.ConsolePatterns.addPattern;

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
            boolean hasDefaultEmission = consoleTheme == ConsoleTheme.COPPER || consoleTheme == ConsoleTheme.CRYSTAL|| consoleTheme == ConsoleTheme.CORAL || consoleTheme == ConsoleTheme.FACTORY || consoleTheme == ConsoleTheme.INITIATIVE || consoleTheme == ConsoleTheme.TOYOTA || consoleTheme == ConsoleTheme.VICTORIAN;
            addPattern(consoleTheme, new Pattern<>(consoleTheme, new ResourceLocation(TardisRefined.MODID, "default"), createBasePatternLocation(themeName + "/" + themeName + "_console"))).setEmissive(hasDefaultEmission);
        }

        /*Coral*/
        addPattern(ConsoleTheme.CORAL, new Pattern<>(ConsoleTheme.CORAL, new ResourceLocation(TardisRefined.MODID, "blue"), createBasePatternLocation("coral/coral_console_blue"))).setEmissive(true);
        addPattern(ConsoleTheme.CORAL, new Pattern<>(ConsoleTheme.CORAL, new ResourceLocation(TardisRefined.MODID, "war"), createBasePatternLocation("coral/coral_console_war"))).setEmissive(true);

        /*Factory*/
        addPattern(ConsoleTheme.FACTORY, new Pattern<>(ConsoleTheme.FACTORY, new ResourceLocation(TardisRefined.MODID, "vintage"), createBasePatternLocation("factory/factory_console_vintage"))).setEmissive(true);
        addPattern(ConsoleTheme.FACTORY, new Pattern<>(ConsoleTheme.FACTORY, new ResourceLocation(TardisRefined.MODID, "mint"), createBasePatternLocation("factory/factory_console_mint"))).setEmissive(true);
        addPattern(ConsoleTheme.FACTORY, new Pattern<>(ConsoleTheme.FACTORY, new ResourceLocation(TardisRefined.MODID, "wood"), createBasePatternLocation("factory/factory_console_wood"))).setEmissive(true);

        /*Toyota*/
        addPattern(ConsoleTheme.TOYOTA, new Pattern<>(ConsoleTheme.TOYOTA, new ResourceLocation(TardisRefined.MODID, "violet"), createBasePatternLocation("toyota/toyota_texture_purple"))).setEmissive(true);
        addPattern(ConsoleTheme.TOYOTA, new Pattern<>(ConsoleTheme.TOYOTA, new ResourceLocation(TardisRefined.MODID, "blue"), createBasePatternLocation("toyota/toyota_texture_blue"))).setEmissive(true);

        /*Crystal*/
        addPattern(ConsoleTheme.CRYSTAL, new Pattern<>(ConsoleTheme.CRYSTAL, new ResourceLocation(TardisRefined.MODID, "corrupted"), createBasePatternLocation("crystal/crystal_console_corrupted"))).setEmissive(true);

        /*Myst*/
        addPattern(ConsoleTheme.MYST, new Pattern<>(ConsoleTheme.MYST, new ResourceLocation(TardisRefined.MODID, "molten"), createBasePatternLocation("myst/myst_console_molten")));

        /*Victorian*/
        addPattern(ConsoleTheme.VICTORIAN, new Pattern<>(ConsoleTheme.VICTORIAN, new ResourceLocation(TardisRefined.MODID, "smissmass"), createBasePatternLocation("victorian/victorian_console_smissmass")));
        addPattern(ConsoleTheme.VICTORIAN, new Pattern<>(ConsoleTheme.VICTORIAN, new ResourceLocation(TardisRefined.MODID, "grant"), createBasePatternLocation("victorian/victorian_console_grant")));

        /*Initiative*/
        addPattern(ConsoleTheme.INITIATIVE, new Pattern<>(ConsoleTheme.INITIATIVE, new ResourceLocation(TardisRefined.MODID, "aperture"), createBasePatternLocation("initiative/initiative_console_aperture"))).setEmissive(true);
        addPattern(ConsoleTheme.INITIATIVE, new Pattern<>(ConsoleTheme.INITIATIVE, new ResourceLocation(TardisRefined.MODID, "blue"), createBasePatternLocation("initiative/initiative_console_blue"))).setEmissive(true);

    }

    public static ResourceLocation createBasePatternLocation(String path){
        return new ResourceLocation(TardisRefined.MODID, path);
    }

    @Override
    public void run(CachedOutput arg) throws IOException {
        registerPatterns();

        for (ConsoleTheme consoleTheme : ConsoleTheme.values()) {

            JsonArray patternArray = new JsonArray();

            for (Pattern<ConsoleTheme> pattern : ConsolePatterns.getPatterns().get(consoleTheme)) {
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

    private Path getPath(ConsoleTheme theme) {
        String themeName = theme.getSerializedName();
        return generator.getOutputFolder().resolve("data/" + TardisRefined.MODID + "/patterns/console/" + themeName + ".json");
    }

    @Override
    public String getName() {
        return "Console Patterns";
    }
}
