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
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.util.MiscHelper;

import java.io.IOException;

import static whocraft.tardis_refined.common.tardis.TardisDesktops.DESKTOPS;
import static whocraft.tardis_refined.common.tardis.TardisDesktops.addDesktop;

public class DesktopProvider implements DataProvider {

    protected final DataGenerator generator;


    public DesktopProvider(DataGenerator generator) {
        Preconditions.checkNotNull(generator);
        this.generator = generator;
    }

    public static void registerDesktops() {

        addDesktop(TardisDesktops.DEFAULT_OVERGROWN_THEME);
        addDesktop(TardisDesktops.FACTORY_THEME);
        addDesktop(new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "coral"), new ResourceLocation(TardisRefined.MODID, "desktop/coral"), true));
        addDesktop(new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "victorian"), new ResourceLocation(TardisRefined.MODID, "desktop/victorian"), true));
        addDesktop(new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "copper"), new ResourceLocation(TardisRefined.MODID, "desktop/copper"), true));
        addDesktop(new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "toyota"), new ResourceLocation(TardisRefined.MODID, "desktop/toyota"), true));
        addDesktop(new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "crystal"), new ResourceLocation(TardisRefined.MODID, "desktop/crystal"), true));
        addDesktop(new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "nuka"), new ResourceLocation(TardisRefined.MODID, "desktop/nuka"), true));
        addDesktop(new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "future_nostalgia"), new ResourceLocation(TardisRefined.MODID, "desktop/future_nostalgia"), true));
        addDesktop(new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "violet_eye"), new ResourceLocation(TardisRefined.MODID, "desktop/violet_eye"), true));

    }

    @Override
    public void run(CachedOutput arg) throws IOException {
        registerDesktops();

        DESKTOPS.forEach(desktop -> {
            try {
                JsonObject currentDesktop = new JsonObject();
                currentDesktop.addProperty("id", desktop.identifier.toString());
                currentDesktop.addProperty("structure", desktop.location.toString());
                currentDesktop.addProperty("name_component", TardisRefined.GSON.toJson(Component.literal(formatName(desktop.identifier.getPath())).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))));


                DataProvider.saveStable(arg, currentDesktop, generator.getOutputFolder().resolve("data/" + TardisRefined.MODID + "/desktop/" +  desktop.identifier.getPath().replace("/", "_") + ".json"));
            }catch (Exception exception) {
                TardisRefined.LOGGER.debug("Issue writing Desktop {}! Error: {}", desktop.identifier, exception.getMessage());
            }
        });
    }

    @Override
    public String getName() {
        return "Desktops";
    }

    private String formatName(String name) {
        return MiscHelper.getCleanName(name);
    }
}
