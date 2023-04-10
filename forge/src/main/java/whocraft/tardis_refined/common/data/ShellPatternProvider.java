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
import whocraft.tardis_refined.patterns.BasePattern;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ShellPatternProvider implements DataProvider {

    protected final DataGenerator generator;
    protected List<CompletableFuture<?>> futures = new ArrayList<>();

    public ShellPatternProvider(DataGenerator generator) {
        Preconditions.checkNotNull(generator);
        this.generator = generator;
    }

    public static void registerPatterns() {

        /*Add Base Textures*/
        for (ShellTheme shellTheme : ShellTheme.values()) {
            String themeName = shellTheme.name().toLowerCase(Locale.ENGLISH);
            boolean hasDefaultEmission = shellTheme == ShellTheme.MYSTIC || shellTheme == ShellTheme.NUKA || shellTheme == ShellTheme.PAGODA || shellTheme == ShellTheme.PHONE_BOOTH || shellTheme == ShellTheme.POLICE_BOX || shellTheme == ShellTheme.VENDING;

            ShellPattern pattern = (ShellPattern) new ShellPattern(shellTheme, new ResourceLocation(TardisRefined.MODID, shellTheme.getSerializedName()), createBasePatternLocation("textures/blockentity/shell/" + themeName + "/" + themeName + ".png"), createBasePatternLocation("textures/blockentity/shell/" + themeName + "/" + themeName + "_interior.png")).setEmissive(hasDefaultEmission);
            pattern.setName("Default");
            ShellPatterns.addPattern(shellTheme, (ShellPattern) pattern.setEmissive(hasDefaultEmission));
        }

        create(ShellTheme.POLICE_BOX, false, "marble");
        create(ShellTheme.POLICE_BOX, false, "gaudy");
        create(ShellTheme.POLICE_BOX, false, "metal");
        create(ShellTheme.POLICE_BOX, false, "stone");
        create(ShellTheme.POLICE_BOX, false, "red");

        create(ShellTheme.PHONE_BOOTH, false, "metal");

        create(ShellTheme.PRESENT, false, "cardboard");

        create(ShellTheme.BRIEFCASE, false, "intel");
        create(ShellTheme.BRIEFCASE, false, "metal");
        create(ShellTheme.BRIEFCASE, false, "mesa");

        create(ShellTheme.MYSTIC, false, "dwarven");

        create(ShellTheme.BIG_BEN, false, "gothic");

    }

    private static void create(ShellTheme shellTheme, boolean emmsive, String name) {
        ShellPatterns.addPattern(shellTheme, new ShellPattern(shellTheme, new ResourceLocation(TardisRefined.MODID, name), createBasePatternLocation("textures/blockentity/shell/" + shellTheme.getSerializedName().toLowerCase(Locale.ENGLISH) + "/" + name + ".png"), createBasePatternLocation("textures/blockentity/shell/" + shellTheme.getSerializedName().toLowerCase(Locale.ENGLISH) + "/" + name + "_interior.png"))).setEmissive(emmsive);
    }

    public static ResourceLocation createBasePatternLocation(String path) {
        return new ResourceLocation(TardisRefined.MODID, path);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput arg) {
        registerPatterns();

        for (ShellTheme consoleTheme : ShellTheme.values()) {

            JsonArray patternArray = new JsonArray();

            for (ShellPattern basePattern : ShellPatterns.getPatterns().get(consoleTheme)) {
                JsonObject currentPattern = new JsonObject();
                currentPattern.addProperty("id", basePattern.id().toString());

                JsonObject exteriorObject = new JsonObject();
                exteriorObject.addProperty("texture", basePattern.texture().toString());
                exteriorObject.addProperty("emissive", basePattern.emissive());

                JsonObject interiorObject = new JsonObject();
                interiorObject.addProperty("texture", basePattern.interiorDoorTexture().toString());

                currentPattern.add("interior", interiorObject);
                currentPattern.add("exterior", exteriorObject);

                currentPattern.addProperty("name_component", TardisRefined.GSON.toJson(Component.literal(basePattern.name()).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))));
                patternArray.add(currentPattern);
            }

            futures.add(DataProvider.saveStable(arg, patternArray, getPath(consoleTheme)));

        }
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));

    }

    private Path getPath(ShellTheme theme) {
        String themeName = theme.getSerializedName();
        return generator.getPackOutput().getOutputFolder().resolve("data/" + TardisRefined.MODID + "/" + TardisRefined.MODID + "/patterns/shell/" + themeName + ".json");
    }

    @Override
    public String getName() {
        return "Console Patterns";
    }
}
