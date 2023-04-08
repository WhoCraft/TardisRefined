package whocraft.tardis_refined.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.util.fabric.PlatformImpl;
import whocraft.tardis_refined.common.world.fabric.TRFabricBiomeModifiers;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.compat.portals.fabric.PortalsCompatFabric;
import whocraft.tardis_refined.fabric.events.ModEvents;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static net.minecraft.server.packs.PackType.SERVER_DATA;

public class TardisRefinedFabric implements ModInitializer {
    /**
     * For use with Fabric BiomeModification API
     */
    public static void setupBiomeModifications() {
        TRFabricBiomeModifiers.addFeatures();
    }

    public static void register(PackType packType, ResourceLocation id, PreparableReloadListener listener) {
        ResourceManagerHelper.get(packType).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return id;
            }

            @Override
            public String getName() {
                return listener.getName();
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2, Executor executor, Executor executor2) {
                return listener.reload(preparationBarrier, resourceManager, profilerFiller, profilerFiller2, executor, executor2);
            }
        });
    }

    @Override
    public void onInitialize() {
        PlatformImpl.init();
        ModEvents.addCommonEvents();
        TardisRefined.init();
        setupBiomeModifications();
        ModLoadingContext.registerConfig(TardisRefined.MODID, ModConfig.Type.COMMON, TRConfig.COMMON_SPEC);
        ModLoadingContext.registerConfig(TardisRefined.MODID, ModConfig.Type.CLIENT, TRConfig.CLIENT_SPEC);

        register(SERVER_DATA, new ResourceLocation(TardisRefined.MODID, "console_patterns"), new ConsolePatterns());
        register(SERVER_DATA, new ResourceLocation(TardisRefined.MODID, "desktops"), new TardisDesktops());
        register(SERVER_DATA, new ResourceLocation(TardisRefined.MODID, "shell_patterns"), new ShellPatterns());
        if (ModCompatChecker.immersivePortals()) {
            if (TRConfig.COMMON.COMPATIBILITY_IP.get()) {
                ImmersivePortals.init();
                PortalsCompatFabric.init();
            }
        } else {
            TardisRefined.LOGGER.info("ImmersivePortals was not detected.");
        }

    }
}