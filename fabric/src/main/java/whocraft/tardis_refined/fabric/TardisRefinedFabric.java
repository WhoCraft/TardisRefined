package whocraft.tardis_refined.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier;
import net.minecraft.server.packs.resources.PreparableReloadListener.SharedState;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.fml.config.ModConfig;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.soundscape.hum.TardisHums;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorRecipes;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.fabric.PlatformImpl;
import whocraft.tardis_refined.common.world.fabric.TRFabricBiomeModifiers;
import whocraft.tardis_refined.compat.CuriosTrinketsUtil;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.create26.CreateIntergrationsFabric;
import whocraft.tardis_refined.compat.portals26.ImmersivePortals;
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

    public static void register(PackType packType, Identifier id, PreparableReloadListener listener) {
        ResourceManagerHelper.get(packType).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return id;
            }

            @Override
            public String getName() {
                return listener.getName();
            }

            @Override
            public CompletableFuture<Void> reload(SharedState sharedState, Executor prepareExecutor, PreparationBarrier preparationBarrier, Executor applyExecutor) {
                return listener.reload(sharedState, prepareExecutor, preparationBarrier, applyExecutor);
            }
        });
    }

    @Override
    public void onInitialize() {
        PlatformImpl.init();
        ModEvents.addCommonEvents();
        TardisRefined.init();
        // Recipe construction creates ItemStacks, which need the item
        // component registry to be bound. Register after bootstrap completes.
        ServerLifecycleEvents.SERVER_STARTING.register(server -> ManipulatorRecipes.registerRecipes());
        setupBiomeModifications();
        ConfigRegistry.INSTANCE.register(TardisRefined.MODID, ModConfig.Type.COMMON, TRConfig.COMMON_SPEC);
        ConfigRegistry.INSTANCE.register(TardisRefined.MODID, ModConfig.Type.CLIENT, TRConfig.CLIENT_SPEC);
        ConfigRegistry.INSTANCE.register(TardisRefined.MODID, ModConfig.Type.SERVER, TRConfig.SERVER_SPEC);


        register(SERVER_DATA, Identifier.fromNamespaceAndPath(TardisRefined.MODID, TardisRefined.MODID + "/" + "console_patterns"), ConsolePatterns.getReloadListener());

        register(SERVER_DATA, Identifier.fromNamespaceAndPath(TardisRefined.MODID, TardisRefined.MODID + "/" + "desktops"), TardisDesktops.getReloadListener());
        register(SERVER_DATA, Identifier.fromNamespaceAndPath(TardisRefined.MODID, TardisRefined.MODID + "/" + "shell_patterns"), ShellPatterns.getReloadListener());
        register(SERVER_DATA, Identifier.fromNamespaceAndPath(TardisRefined.MODID, TardisRefined.MODID + "/" + "hums"), TardisHums.getReloadListener());

        if (ModCompatChecker.immersivePortals()) {
            if (TRConfig.COMMON.COMPATIBILITY_IP.get()) {
                ImmersivePortals.init();
            }
        } else {
            TardisRefined.LOGGER.info("ImmersivePortals was not detected.");
        }

        if(ModCompatChecker.create()){
            CreateIntergrationsFabric.init();
        }

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(TardisRefined.MODID, "ore_zeiton")));
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(TardisRefined.MODID, "ore_zeiton_small")));

    }
}
