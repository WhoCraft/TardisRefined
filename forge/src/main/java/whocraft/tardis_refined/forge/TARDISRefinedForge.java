package whocraft.tardis_refined.forge;

import whocraft.tardis_refined.TARDISRefined;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TARDISRefined.MOD_ID)
public class TARDISRefinedForge {
    public TARDISRefinedForge() {
        TARDISRefined.init();
    }
}