package whocraft.tardis_refined.common.tardis;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// TODO: Reimplement datapack system.
public class TardisDesktops {

    public static List<DesktopTheme> DESKTOPS = new ArrayList<>();

    public static final DesktopTheme DEFAULT_OVERGROWN_THEME = registerDesktop(new DesktopTheme("default_overgrown" ,new ResourceLocation(TardisRefined.MODID, "cave/cave_generation_one"), false));

    public static final DesktopTheme FACTORY_THEME = registerDesktop(new DesktopTheme("factory", new ResourceLocation(TardisRefined.MODID, "desktop/factory"), true));
    public static final DesktopTheme CORAL_THEME = registerDesktop(new DesktopTheme("coral", new ResourceLocation(TardisRefined.MODID, "desktop/coral"), true));
    public static final DesktopTheme TOYOTA_THEME = registerDesktop(new DesktopTheme("toyota", new ResourceLocation(TardisRefined.MODID, "desktop/toyota"), true));
    public static final DesktopTheme COPPER = registerDesktop(new DesktopTheme("copper", new ResourceLocation(TardisRefined.MODID, "desktop/copper"), true));


    public static DesktopTheme registerDesktop(DesktopTheme theme) {
        DESKTOPS.add(theme);
        return theme;
    }

    public static DesktopTheme getDesktopThemeById(String id) {
        return DESKTOPS.stream().filter(theme -> Objects.equals(theme.id, id)).findAny().or(() -> Optional.of(FACTORY_THEME)).get();
    }

}
