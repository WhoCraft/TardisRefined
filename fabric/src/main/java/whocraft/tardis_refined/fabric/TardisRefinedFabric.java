package whocraft.tardis_refined.fabric;

import net.fabricmc.api.ModInitializer;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.util.fabric.PlatformImpl;
import whocraft.tardis_refined.common.world.fabric.TRFabricBiomeModifiers;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.fabric.compat.PortalsCompatFabric;
import whocraft.tardis_refined.fabric.events.ModEvents;

public class TardisRefinedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        PlatformImpl.init();
        ModEvents.addCommonEvents();
        TardisRefined.init();
        setupBiomeModifications();
        ModLoadingContext.registerConfig(TardisRefined.MODID, ModConfig.Type.COMMON, TRConfig.COMMON_SPEC);
        ModLoadingContext.registerConfig(TardisRefined.MODID, ModConfig.Type.CLIENT, TRConfig.CLIENT_SPEC);

        if (ModCompatChecker.immersivePortals()) {
            if(TRConfig.COMMON.COMPATIBILITY_IP.get()) {
                ImmersivePortals.init();
                PortalsCompatFabric.init();
            }
        } else {
            TardisRefined.LOGGER.info("ImmersivePortals was not detected.");
        }

    }

    /** For use with Fabric BiomeModification API*/
    public static void setupBiomeModifications() {
        TRFabricBiomeModifiers.addFeatures();
    }
}