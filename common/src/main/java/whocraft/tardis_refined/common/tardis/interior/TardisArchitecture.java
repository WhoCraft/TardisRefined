package whocraft.tardis_refined.common.tardis.interior;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.interior.arctypes.DesktopTheme;

// TODO: Reimplement datapack system.
public class TardisArchitecture {

    public static final DesktopTheme DEFAULT_OVERGROWN_THEME = new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "cave/cave_generation_one"), "Overgrown", new BlockPos(8,2,8), true);

    public static final DesktopTheme FACTORY_THEME = new DesktopTheme(new ResourceLocation(TardisRefined.MODID, "desktop/factory"), "Factory", new BlockPos(8,2,8), true);

}
