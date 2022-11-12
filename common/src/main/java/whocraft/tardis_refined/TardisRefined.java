package whocraft.tardis_refined;

import whocraft.tardis_refined.registry.TardisBlocks;
import whocraft.tardis_refined.registry.TardisItems;

public class TardisRefined {
    public static final String MODID = "tardis_refined";
    public static final String PLATFORM_ERROR = "Something has gone critically wrong with platform definitions. Please contact the mod author.";

    public static void init() {
        TardisBlocks.BLOCKS.register();
        TardisItems.ITEMS.register();
    }
}