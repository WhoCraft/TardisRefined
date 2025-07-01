package whocraft.tardis_refined.forge;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.bus.EventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorRecipes;
import whocraft.tardis_refined.common.data.*;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.compat.trinkets.CuriosUtil;

@Mod(TardisRefined.MODID)
public class TardisRefinedForge {
    public TardisRefinedForge(FMLJavaModLoadingContext context) {
        TardisRefined.init();

        context.registerConfig(ModConfig.Type.COMMON, TRConfig.COMMON_SPEC);
        context.registerConfig(ModConfig.Type.CLIENT, TRConfig.CLIENT_SPEC);
        context.registerConfig(ModConfig.Type.SERVER, TRConfig.SERVER_SPEC);

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

}