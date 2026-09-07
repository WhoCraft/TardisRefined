package whocraft.tardis_refined.forge;

import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorRecipes;
import whocraft.tardis_refined.common.data.*;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.create.CreateIntergrationsInit;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.compat.portals.neoforge.PortalsCompatForge;
import whocraft.tardis_refined.compat.trinkets.CuriosUtil;

import java.util.concurrent.CompletableFuture;

@Mod(TardisRefined.MODID)
public class TardisRefinedForge {
    public TardisRefinedForge() {
        TardisRefined.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onGatherData);
        modEventBus.addListener(this::onPostInit);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TRConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TRConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, TRConfig.SERVER_SPEC);

        if (Platform.isModLoaded("curios")) {
            CuriosUtil.init();
        }

       if (ModCompatChecker.immersivePortals()) {
            if(TRConfig.COMMON.COMPATIBILITY_IP.get()) {
                ImmersivePortals.init();
                PortalsCompatForge.init();
            }
        } else {
            TardisRefined.LOGGER.info("ImmersivePortals was not detected.");
        }
    }

    public void onPostInit(InterModProcessEvent event) {
        if (ModCompatChecker.immersivePortals() && TRConfig.COMMON.COMPATIBILITY_IP.get()) {
            ImmersivePortals.postInit();
        }
        if (ModCompatChecker.create()) {
            CreateIntergrationsInit.initAssignments();
        }
    }

    public void onGatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();
        ManipulatorRecipes.registerRecipes();

        /*Resource Pack*/
        generator.addProvider(e.includeClient(), new LangProviderEnglish(generator));
        generator.addProvider(e.includeClient(), new ItemModelProvider(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new TRBlockModelProvider(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new SoundProvider(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new ParticleProvider(generator));

        /*Data Pack*/
        var worldgen = new WorldGenProvider(generator.getPackOutput(), e.getLookupProvider());
        ProviderBlockTags blocks = generator.addProvider(e.includeServer(), new ProviderBlockTags(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));
        generator.addProvider(e.includeServer(), new ItemTagProvider(generator.getPackOutput(), e.getLookupProvider(), blocks.contentsGetter(), existingFileHelper));
        generator.addProvider(e.includeServer(), worldgen);

        generator.addProvider(e.includeServer(), new ProviderLootTable(generator.getPackOutput()));
        generator.addProvider(e.includeServer(), new RecipeProvider(generator.getPackOutput()));
        generator.addProvider(e.includeServer(), new ConsolePatternProvider(generator));
        generator.addProvider(e.includeServer(), new DesktopProvider(generator));
        generator.addProvider(e.includeServer(), new HumProvider(generator));
        generator.addProvider(e.includeServer(), new ShellPatternProvider(generator, TardisRefined.MODID));
        generator.addProvider(e.includeServer(), new ManipulatorRecipeProvider(generator, TardisRefined.MODID));


        //Tags
        generator.addProvider(e.includeServer(), new TRBiomeTagsProvider(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));

        generator.addProvider(e.includeServer(), new ProviderEntityTags(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));
        generator.addProvider(e.includeServer(), new TRPoiTypeTagsProvider(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));
        generator.addProvider(e.includeServer(), new TRDamageTypeTagProvider(generator.getPackOutput(), worldgen.getRegistryProvider(), e.getExistingFileHelper()));

    }
}