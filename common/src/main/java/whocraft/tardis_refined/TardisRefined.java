package whocraft.tardis_refined;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.LowerCaseEnumTypeAdapterFactory;
import org.slf4j.Logger;
import whocraft.tardis_refined.client.TRParticles;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.network.messages.SyncConsolePatternsMessage;
import whocraft.tardis_refined.common.network.messages.SyncDesktopsMessage;
import whocraft.tardis_refined.common.network.messages.SyncShellPatternsMessage;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.world.ChunkGenerators;
import whocraft.tardis_refined.common.world.Features;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.*;

public class TardisRefined {

    // DEBUG DEV STUFF THAT MUST BE DISABLED FOR PROD
    public static final boolean KeySummonsItem = false;

    public static final String MODID = "tardis_refined";
    public static final String PLATFORM_ERROR = "Something has gone critically wrong with platform definitions. Please contact the mod author.";

    public static final Logger LOGGER = LogUtils.getLogger();
    public static Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeHierarchyAdapter(Component.class, new Component.Serializer()).
            registerTypeHierarchyAdapter(Style.class, new Style.Serializer())
            .registerTypeAdapterFactory(new LowerCaseEnumTypeAdapterFactory()).create();

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
        TardisDesktops.getReloadListener().setSyncPacket(TardisNetwork.NETWORK, SyncDesktopsMessage::new);
        ConsolePatterns.getReloadListener().setSyncPacket(TardisNetwork.NETWORK, SyncConsolePatternsMessage::new);
        ShellPatterns.getReloadListener().setSyncPacket(TardisNetwork.NETWORK, SyncShellPatternsMessage::new);
        /* Need to register a default list of entries because on Fabric Cardinal Components classloads the TardisClientData class early on, before datapack entries have been added.
        We will use these as fallback values when looking up patterns.
         */
        ConsolePatterns.registerDefaultPatterns();
        ShellPatterns.registerDefaultPatterns();
    }
}