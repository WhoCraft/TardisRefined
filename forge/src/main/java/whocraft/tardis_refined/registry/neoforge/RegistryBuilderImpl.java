package whocraft.tardis_refined.registry.neoforge;

import net.minecraft.core.Registry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = TardisRefined.MODID)
public class RegistryBuilderImpl {

    private static final List<Registry<?>> REGISTRIES = new ArrayList<>();

    public static <T> Registry<T> createRegistry(RegistryBuilder<T> registryBuilder) {
        var forgeBuilder = new net.neoforged.neoforge.registries.RegistryBuilder<>(registryBuilder.getResourceKey()).sync(
                registryBuilder.isSynced()
        );
        if (registryBuilder.getDefaultKey() != null) {
            forgeBuilder.defaultKey(registryBuilder.getDefaultKey());
        }
        var registry = forgeBuilder.create();
        REGISTRIES.add(registry);
        return registry;
    }

    @SubscribeEvent
    public static void register(NewRegistryEvent event) {
        REGISTRIES.forEach(event::register);
    }

}
