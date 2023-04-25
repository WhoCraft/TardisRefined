package whocraft.tardis_refined.forge;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.data.*;
import whocraft.tardis_refined.common.data.ProviderEntityTags;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.portals.ImmersivePortals;
import whocraft.tardis_refined.compat.portals.forge.PortalsCompatForge;

@Mod(TardisRefined.MODID)
public class TardisRefinedForge {
    public TardisRefinedForge() {
        TardisRefined.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::onGatherData);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TRConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TRConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, TRConfig.SERVER_SPEC);

        if (ModCompatChecker.immersivePortals()) {
            if(TRConfig.COMMON.COMPATIBILITY_IP.get()) {
                ImmersivePortals.init();
                PortalsCompatForge.init();
            }
        } else {
            TardisRefined.LOGGER.info("ImmersivePortals was not detected.");
        }
    }

    public void onGatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();

        /*Resource Pack*/
        generator.addProvider(e.includeClient(), new LangProviderEnglish(generator));
        generator.addProvider(e.includeClient(), new ItemModelProvider(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new ModelProviderBlock(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new SoundProvider(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new ParticleProvider(generator));

        /*Data Pack*/
        generator.addProvider(e.includeServer(), new ProviderBlockTags(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));
        generator.addProvider(e.includeServer(), new ProviderLootTable(generator.getPackOutput()));
        generator.addProvider(e.includeServer(), new RecipeProvider(generator));
        generator.addProvider(e.includeServer(), new ConsolePatternProvider(generator));
        generator.addProvider(e.includeServer(), new DesktopProvider(generator));
        generator.addProvider(e.includeServer(), new ShellPatternProvider(generator));

        //Tags
        generator.addProvider(e.includeServer(), new TRBiomeTagsProvider(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));

        generator.addProvider(e.includeServer(), new ProviderEntityTags(generator.getPackOutput(), e.getLookupProvider(), e.getExistingFileHelper()));

        //World Gen
        BiomeModifierProvider.genBiomeModifiers(e);
    }
}