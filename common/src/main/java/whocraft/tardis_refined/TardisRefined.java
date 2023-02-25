package whocraft.tardis_refined;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import whocraft.tardis_refined.client.TRParticles;
import whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.world.ChunkGenerators;
import whocraft.tardis_refined.common.world.Features;
import whocraft.tardis_refined.registry.*;

public class TardisRefined {

    // DEBUG DEV STUFF THAT MUST BE DISABLED FOR PROD
    public static final boolean KeySummonsItem = false;

    public static final String MODID = "tardis_refined";
    public static final String PLATFORM_ERROR = "Something has gone critically wrong with platform definitions. Please contact the mod author.";

    public static final Logger LOGGER = LogUtils.getLogger();
    public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void init() {
        BlockRegistry.BLOCKS.register();
        ItemRegistry.ITEMS.register();
        EntityRegistry.ENTITY_TYPES.register();
        SoundRegistry.SOUNDS.register();
        BlockEntityRegistry.BLOCK_ENTITY_TYPES.register();
        DimensionTypes.register();
        ChunkGenerators.CHUNK_GENERATORS.register();
        Features.FEATURES.register();
        TRParticles.TYPES.register();
        TagKeys.init();
        TardisNetwork.init();
    }
}