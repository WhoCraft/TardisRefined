package whocraft.tardis_refined.fabric;

import whocraft.tardis_refined.TardisRefined;
import net.fabricmc.api.ModInitializer;

public class TardisRefinedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TardisRefined.init();
    }
}