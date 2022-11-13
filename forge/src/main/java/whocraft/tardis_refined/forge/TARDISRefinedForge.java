package whocraft.tardis_refined.forge;

import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import whocraft.tardis_refined.TardisRefined;
import net.minecraftforge.fml.common.Mod;

@Mod(TardisRefined.MODID)
public class TARDISRefinedForge {
    public TARDISRefinedForge() {
        TardisRefined.init();
    }
}