package whocraft.tardis_refined.fabric;

import net.fabricmc.api.ModInitializer;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerIP;
import whocraft.tardis_refined.common.util.fabric.PlatformImpl;
import whocraft.tardis_refined.common.world.fabric.TRFabricBiomeModifiers;
import whocraft.tardis_refined.fabric.events.ModEvents;

public class TardisRefinedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        PlatformImpl.init();
        ModEvents.addCommonEvents();
        TardisRefined.init();
        setupBiomeModifications();
        if(DimensionHandler.hasIP()) {
            DimensionHandlerIP.init();
        }
    }

    /** For use with Fabric BiomeModification API*/
    public static void setupBiomeModifications() {
        TRFabricBiomeModifiers.addFeatures();
    }
}