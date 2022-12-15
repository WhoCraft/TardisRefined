package whocraft.tardis_refined.common.capability.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentFactory;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TardisRefined;

public class TRComponents implements WorldComponentInitializer {

    public static final ComponentKey<TardisLevelOperatorImpl> TARDIS_DATA =
            ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation(TardisRefined.MODID, "tardis_data"), TardisLevelOperatorImpl.class);

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(TARDIS_DATA, TardisLevelOperatorImpl::new);
    }
}
