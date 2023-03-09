package whocraft.tardis_refined.common.tardis;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns;
import whocraft.tardis_refined.common.network.messages.SyncConsolePatternsMessage;
import whocraft.tardis_refined.common.network.messages.SyncDesktopsMessage;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.util.Platform;

import java.util.*;

public class TardisDesktops extends SimpleJsonResourceReloadListener {

    public static List<DesktopTheme> DESKTOPS = new ArrayList<>();

    public static final DesktopTheme DEFAULT_OVERGROWN_THEME = new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "default_overgrown"), new ResourceLocation(TardisRefined.MODID, "cave/cave_generation_one"));
    public static final DesktopTheme FACTORY_THEME = new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "factory"), new ResourceLocation(TardisRefined.MODID, "desktop/factory"));

    public TardisDesktops() {
        super(TardisRefined.GSON, "desktop");
    }


    public static DesktopTheme getDesktopById(ResourceLocation location) {
        return DESKTOPS.stream().filter(theme -> Objects.equals(theme.getIdentifier(), location)).findAny().or(() -> Optional.of(FACTORY_THEME)).get();
    }

    public static void clear() {
        DESKTOPS.clear();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        DESKTOPS.clear();
        object.forEach((resourceLocation, jsonElement) -> {
            try {

                JsonObject desktopObject = jsonElement.getAsJsonObject();

                // Build the DesktopTheme
                String id = desktopObject.get("id").getAsString();
                String structure = desktopObject.get("structure").getAsString();
                String name = desktopObject.get("name_component").getAsString();
                ResourceLocation resourceLocationId = new ResourceLocation(id);

                if (!resourceLocationId.equals(TardisDesktops.DEFAULT_OVERGROWN_THEME.getIdentifier())) {
                    DesktopTheme theme = new DesktopTheme(resourceLocationId, new ResourceLocation(structure));
                    theme.setName(name);
                    addDesktop(theme);
                }


            } catch (JsonParseException jsonParseException) {
                TardisRefined.LOGGER.debug("Issue parsing Desktop {}! Error: {}", resourceLocation, jsonParseException.getMessage());
            }
        });

        if (Platform.getServer() != null) {
            new SyncDesktopsMessage(DESKTOPS).sendToAll();
        }

    }

    public static DesktopTheme addDesktop(DesktopTheme theme) {
        TardisRefined.LOGGER.info("Adding Desktop {}", theme.getIdentifier());
        DESKTOPS.add(theme);
        return theme;
    }
}
