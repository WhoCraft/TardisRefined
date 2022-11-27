package whocraft.tardis_refined.fabric;

import net.fabricmc.api.ModInitializer;
import whocraft.tardis_refined.TardisRefined;

public class TardisRefinedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TardisRefined.init();
    }
}