package whocraft.tardis_refined.common.capability.fabric;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v8.level.LevelComponentFactoryRegistry;
import org.ladysnake.cca.api.v8.level.LevelComponentInitializer;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.tardis.fabric.TardisLevelOperatorImpl;
import whocraft.tardis_refined.registry.TRDimensionTypes;

public class TRComponents implements LevelComponentInitializer {

    public static final ComponentKey<TardisLevelOperatorImpl> TARDIS_DATA = ComponentRegistryV3.INSTANCE.getOrCreate(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "tardis_data"), TardisLevelOperatorImpl.class);

    /**
     * Registers the appropriate world component factory for the current environment.
     *
     * @param registry the registry to register the factory to
     */
    @Override
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {
        registry.register(TARDIS_DATA, level -> {

            if (level instanceof ServerLevel serverLevel && level.dimensionTypeRegistration().is(TRDimensionTypes.TARDIS)) {
                return new TardisLevelOperatorImpl(serverLevel);
            }
            return new TardisLevelOperatorDummy(level);
        });
    }
}
