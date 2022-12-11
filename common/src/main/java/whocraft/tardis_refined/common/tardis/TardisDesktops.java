package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO: Reimplement datapack system.
public class TardisDesktops {

    public static List<DesktopTheme> DESKTOPS = new ArrayList<>();

    public static final DesktopTheme DEFAULT_OVERGROWN_THEME = registerDesktop(new DesktopTheme("default_overgrown" ,new ResourceLocation(TardisRefined.MODID, "cave/cave_generation_one"), false));

    public static final DesktopTheme FACTORY_THEME = registerDesktop(new DesktopTheme("factory", new ResourceLocation(TardisRefined.MODID, "desktop/factory"), true));
    public static final DesktopTheme CORAL_THEME = registerDesktop(new DesktopTheme("coral", new ResourceLocation(TardisRefined.MODID, "desktop/coral"), true));

    public static DesktopTheme registerDesktop(DesktopTheme theme) {
        DESKTOPS.add(theme);
        return theme;
    }

    public static DesktopTheme getDesktopThemeById(String id) {
        return DESKTOPS.stream().filter(theme -> Objects.equals(theme.id, id)).findAny().get();
    }

}