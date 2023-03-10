package whocraft.tardis_refined.common.data;

import com.google.common.base.Preconditions;
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

        addDesktop(TardisDesktops.FACTORY_THEME);
        addDesktop(new DesktopTheme("coral", new ResourceLocation(TardisRefined.MODID, "desktop/coral")));
        addDesktop(new DesktopTheme("victorian", new ResourceLocation(TardisRefined.MODID, "desktop/victorian")));
        addDesktop(new DesktopTheme("copper", new ResourceLocation(TardisRefined.MODID, "desktop/copper")));
        addDesktop(new DesktopTheme("toyota", new ResourceLocation(TardisRefined.MODID, "desktop/toyota")));
        addDesktop(new DesktopTheme("crystal", new ResourceLocation(TardisRefined.MODID, "desktop/crystal")));
        addDesktop(new DesktopTheme("nuka", new ResourceLocation(TardisRefined.MODID, "desktop/nuka")));
        addDesktop(new DesktopTheme("future_nostalgia", new ResourceLocation(TardisRefined.MODID, "desktop/future_nostalgia")));
        addDesktop(new DesktopTheme("violet_eye", new ResourceLocation(TardisRefined.MODID, "desktop/violet_eye")));
    }

    @Override
    public void run(CachedOutput arg) throws IOException {
        registerDesktops();

        DESKTOPS.forEach(desktop -> {
            try {
                JsonObject currentDesktop = new JsonObject();
                currentDesktop.addProperty("id", desktop.getIdentifier().toString());
                currentDesktop.addProperty("structure", desktop.getStructureLocation().toString());
                currentDesktop.addProperty("name_component", TardisRefined.GSON.toJson(Component.literal(formatName(desktop.getIdentifier().getPath())).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))));


                DataProvider.saveStable(arg, currentDesktop, generator.getOutputFolder().resolve("data/" + TardisRefined.MODID + "/desktop/" +  desktop.getIdentifier().getPath().replace("/", "_") + ".json"));
            }catch (Exception exception) {
                TardisRefined.LOGGER.debug("Issue writing Desktop {}! Error: {}", desktop.getIdentifier(), exception.getMessage());
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
