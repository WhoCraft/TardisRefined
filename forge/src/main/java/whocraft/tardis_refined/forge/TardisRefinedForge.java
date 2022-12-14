package whocraft.tardis_refined.forge;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.data.ItemModelProvider;
import whocraft.tardis_refined.common.data.LangProviderEnglish;
import whocraft.tardis_refined.common.data.ModelProviderBlock;

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
        generator.addProvider(true, new LangProviderEnglish(generator));
        generator.addProvider(true, new ItemModelProvider(generator, existingFileHelper));
        generator.addProvider(true, new ModelProviderBlock(generator, existingFileHelper));
    }

}