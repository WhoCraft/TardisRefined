package whocraft.tardis_refined.forge;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.data.*;
import whocraft.tardis_refined.common.data.ItemModelProvider;
import whocraft.tardis_refined.common.data.LangProviderEnglish;
import whocraft.tardis_refined.common.data.RecipeProvider;

@Mod(TardisRefined.MODID)
public class TardisRefinedForge {
    public TardisRefinedForge() {
        TardisRefined.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::onGatherData);
    }

    public void onGatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();

        /*Resource Pack*/
        generator.addProvider(e.includeClient(), new LangProviderEnglish(generator));
        generator.addProvider(e.includeClient(), new ItemModelProvider(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new ModelProviderBlock(generator, existingFileHelper));

        /*Data Pack*/
        generator.addProvider(e.includeServer(), new ProviderBlockTags(generator, existingFileHelper));
        generator.addProvider(e.includeServer(), new ProviderLootTable(generator));
        generator.addProvider(e.includeServer(), new RecipeProvider(generator));
    }
}