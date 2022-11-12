package whocraft.tardis_refined;

import whocraft.tardis_refined.registry.DimensionTypes;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;

public class TardisRefined {
    public static final String MODID = "tardis_refined";
    public static final String PLATFORM_ERROR = "Something has gone critically wrong with platform definitions. Please contact the mod author.";

    public static void init() {
        BlockRegistry.BLOCKS.register();
        ItemRegistry.ITEMS.register();
        DimensionTypes.register();
    }
}