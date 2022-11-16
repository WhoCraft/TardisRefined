package whocraft.tardis_refined;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.world.ChunkGenerators;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.DimensionTypes;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;

public class TardisRefined {
    public static final String MODID = "tardis_refined";
    public static final String PLATFORM_ERROR = "Something has gone critically wrong with platform definitions. Please contact the mod author.";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        BlockRegistry.BLOCKS.register();
        ItemRegistry.ITEMS.register();
        BlockEntityRegistry.BLOCK_ENTITY_TYPES.register();
        DimensionTypes.register();
        ChunkGenerators.CHUNK_GENERATORS.register();
        TardisNetwork.init();
    }
}