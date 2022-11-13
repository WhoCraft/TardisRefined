package whocraft.tardis_refined.fabric;

import whocraft.tardis_refined.TardisRefined;
import net.fabricmc.api.ModInitializer;
import whocraft.tardis_refined.common.capability.fabric.TRComponents;

public class TARDISRefinedFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        TardisRefined.init();
    }
}