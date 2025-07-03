package whocraft.tardis_refined.neoforge;

import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorRecipes;
import whocraft.tardis_refined.common.data.*;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.compat.trinkets.CuriosUtil;

@Mod(TardisRefined.MODID)
public class TardisRefinedNeoForge {
    public TardisRefinedNeoForge(ModLoadingContext context) {
        TardisRefined.init();
        IEventBus modEventBus = context.getActiveContainer().getEventBus();
        modEventBus.addListener(this::onGatherData);

        ModList.get().getModContainerById(TardisRefined.MODID).get().registerConfig(ModConfig.Type.COMMON, TRConfig.COMMON_SPEC);
        ModList.get().getModContainerById(TardisRefined.MODID).get().registerConfig(ModConfig.Type.CLIENT, TRConfig.CLIENT_SPEC);
        ModList.get().getModContainerById(TardisRefined.MODID).get().registerConfig(ModConfig.Type.SERVER, TRConfig.SERVER_SPEC);

        if (Platform.isModLoaded("curios")) {
            CuriosUtil.init();
        }

   /*     if (ModCompatChecker.immersivePortals()) {
            if(TRConfig.COMMON.COMPATIBILITY_IP.get()) {
                ImmersivePortals.init();
                PortalsCompatForge.init();
            }
        } else {
            TardisRefined.LOGGER.info("ImmersivePortals was not detected.");
        }*/
    }

    public void onGatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
;
        ManipulatorRecipes.registerRecipes();

        /*Resource Pack*/
        generator.addProvider(true, new LangProviderEnglish(generator));
        generator.addProvider(true, new ItemModelProvider(generator, existingFileHelper));
        generator.addProvider(true, new TRBlockModelProvider(generator, existingFileHelper));
        generator.addProvider(true, new SoundProvider(generator, existingFileHelper));
        generator.addProvider(true, new ParticleProvider(generator));

        /*Data Pack*/
        ProviderBlockTags blocks = generator.addProvider(true, new ProviderBlockTags(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));
        generator.addProvider(true, new ItemTagProvider(generator.getPackOutput(), e.getLookupProvider(), blocks.contentsGetter(), existingFileHelper));
        generator.addProvider(true, new WorldGenProvider(generator.getPackOutput(), e.getLookupProvider()));

        generator.addProvider(true, new ProviderLootTable(generator.getPackOutput()));
        generator.addProvider(true, new RecipeProvider(generator, e.getLookupProvider()));
        generator.addProvider(true, new ConsolePatternProvider(generator));
        generator.addProvider(true, new DesktopProvider(generator));
        generator.addProvider(true, new HumProvider(generator));
        generator.addProvider(true, new ShellPatternProvider(generator, TardisRefined.MODID));
        generator.addProvider(true, new ManipulatorRecipeProvider(generator, TardisRefined.MODID));


        //Tags
        generator.addProvider(true, new TRBiomeTagsProvider(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));

        generator.addProvider(true, new ProviderEntityTags(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));
        generator.addProvider(true, new TRPoiTypeTagsProvider(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));

    }
}