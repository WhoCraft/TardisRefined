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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
            boolean hasDefaultEmission = consoleTheme == ConsoleTheme.COPPER || consoleTheme == ConsoleTheme.CRYSTAL|| consoleTheme == ConsoleTheme.CORAL || consoleTheme == ConsoleTheme.FACTORY || consoleTheme == ConsoleTheme.INITIATIVE || consoleTheme == ConsoleTheme.TOYOTA || consoleTheme == ConsoleTheme.VICTORIAN;
            addPattern(consoleTheme, new ConsolePatterns.Pattern(consoleTheme, new ResourceLocation(TardisRefined.MODID, "default"), themeName + "/" + themeName + "_console")).setEmissive(hasDefaultEmission);
        }

        /*Coral*/
        addPattern(ConsoleTheme.CORAL, new ConsolePatterns.Pattern(ConsoleTheme.CORAL, new ResourceLocation(TardisRefined.MODID, "blue"), "coral/coral_console_blue")).setEmissive(true);
        addPattern(ConsoleTheme.CORAL, new ConsolePatterns.Pattern(ConsoleTheme.CORAL, new ResourceLocation(TardisRefined.MODID, "war"), "coral/coral_console_war")).setEmissive(true);

        /*Factory*/
        addPattern(ConsoleTheme.FACTORY, new ConsolePatterns.Pattern(ConsoleTheme.FACTORY, new ResourceLocation(TardisRefined.MODID, "vintage"), "factory/factory_console_vintage")).setEmissive(true);
        addPattern(ConsoleTheme.FACTORY, new ConsolePatterns.Pattern(ConsoleTheme.FACTORY, new ResourceLocation(TardisRefined.MODID, "mint"), "factory/factory_console_mint")).setEmissive(true);
        addPattern(ConsoleTheme.FACTORY, new ConsolePatterns.Pattern(ConsoleTheme.FACTORY, new ResourceLocation(TardisRefined.MODID, "wood"), "factory/factory_console_wood")).setEmissive(true);

        /*Toyota*/
        addPattern(ConsoleTheme.TOYOTA, new ConsolePatterns.Pattern(ConsoleTheme.TOYOTA, new ResourceLocation(TardisRefined.MODID, "violet"), "toyota/toyota_texture_purple")).setEmissive(true);
        addPattern(ConsoleTheme.TOYOTA, new ConsolePatterns.Pattern(ConsoleTheme.TOYOTA, new ResourceLocation(TardisRefined.MODID, "blue"), "toyota/toyota_texture_blue")).setEmissive(true);

        /*Crystal*/
        addPattern(ConsoleTheme.CRYSTAL, new ConsolePatterns.Pattern(ConsoleTheme.CRYSTAL, new ResourceLocation(TardisRefined.MODID, "corrupted"), "crystal/crystal_console_corrupted")).setEmissive(true);

        /*Myst*/
        addPattern(ConsoleTheme.MYST, new ConsolePatterns.Pattern(ConsoleTheme.MYST, new ResourceLocation(TardisRefined.MODID, "molten"), "myst/myst_console_molten"));

        /*Victorian*/
        addPattern(ConsoleTheme.VICTORIAN, new ConsolePatterns.Pattern(ConsoleTheme.VICTORIAN, new ResourceLocation(TardisRefined.MODID, "smissmass"), "victorian/victorian_console_smissmass"));
        addPattern(ConsoleTheme.VICTORIAN, new ConsolePatterns.Pattern(ConsoleTheme.VICTORIAN, new ResourceLocation(TardisRefined.MODID, "grant"), "victorian/victorian_console_grant"));

        /*Initiative*/
        addPattern(ConsoleTheme.INITIATIVE, new ConsolePatterns.Pattern(ConsoleTheme.INITIATIVE, new ResourceLocation(TardisRefined.MODID, "aperture"), "initiative/initiative_console_aperture")).setEmissive(true);
        addPattern(ConsoleTheme.INITIATIVE, new ConsolePatterns.Pattern(ConsoleTheme.INITIATIVE, new ResourceLocation(TardisRefined.MODID, "blue"), "initiative/initiative_console_blue")).setEmissive(true);

    }


    @Override
    public CompletableFuture<?> run(CachedOutput arg) {

       return CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                registerPatterns();

                for (ConsoleTheme consoleTheme : ConsoleTheme.values()) {

                    JsonArray patternArray = new JsonArray();

                    for (ConsolePatterns.Pattern pattern : ConsolePatterns.getPatterns().get(consoleTheme)) {
                        JsonObject currentPattern = new JsonObject();
                        currentPattern.addProperty("id", pattern.id().toString());
                        currentPattern.addProperty("emissive", pattern.emissive());
                        currentPattern.addProperty("texture", pattern.texture().toString());
                        currentPattern.addProperty("name_component", TardisRefined.GSON.toJson(Component.literal(pattern.name()).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))));
                        patternArray.add(currentPattern);
                    }
                    try {
                        DataProvider.saveStable(arg, patternArray, getPath(consoleTheme)).get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
    }

    private Path getPath(ConsoleTheme theme) {
        String themeName = theme.getSerializedName();
        return generator.getPackOutput().getOutputFolder().resolve("data/" + TardisRefined.MODID + "/patterns/console/" + themeName + ".json");
    }

    @Override
    public String getName() {
        return "Console Patterns";
    }
}
