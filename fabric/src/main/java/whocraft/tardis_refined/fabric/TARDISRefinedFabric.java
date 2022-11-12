package whocraft.tardis_refined.fabric;

import whocraft.tardis_refined.TARDISRefined;
import net.fabricmc.api.ModInitializer;

public class TARDISRefinedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TARDISRefined.init();
    }
}