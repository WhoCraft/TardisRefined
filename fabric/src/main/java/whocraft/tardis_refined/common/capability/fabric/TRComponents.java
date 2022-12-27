package whocraft.tardis_refined.common.capability.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;

public class TRComponents implements WorldComponentInitializer {

    public static final ComponentKey<TardisLevelOperatorImpl> TARDIS_DATA = ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation(TardisRefined.MODID, "tardis_data"), TardisLevelOperatorImpl.class);

    /**
     * Registers the appropriate world component factory for the current environment.
     *
     * @param registry the registry to register the factory to
     */
    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        // Register a dummy factory for the client environment
        registry.register(TARDIS_DATA, TardisLevelOperatorDummy::new);

        // Return immediately if the current environment is the client
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) return;

        // Register the actual factory for all other environments
        registry.register(TARDIS_DATA, TardisLevelOperatorImpl::new);
    }
}
