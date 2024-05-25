package whocraft.tardis_refined.registry.fabric;


import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class DeferredRegistryImpl {

    public static <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static class Impl<T> extends DeferredRegistry<T> {

        private final String modid;
        private final WritableRegistry<T> registry;
        private ResourceKey<? extends Registry<T>> registryKey;
        private final List<RegistrySupplier<T>> entries;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this.modid = modid;
            this.registryKey = resourceKey;
            this.registry = (WritableRegistry<T>) BuiltInRegistries.REGISTRY.get(resourceKey.location());
            this.entries = new ArrayList<>();
        }

        @Override
        public void register() {

        }

        @Override
        public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
            ResourceLocation registeredId = new ResourceLocation(this.modid, id);
            RegistrySupplier<R> registrySupplier = new RegistrySupplier<>(registeredId, Registry.register(this.registry, registeredId, supplier.get()));
            this.entries.add((RegistrySupplier<T>) registrySupplier);
            return registrySupplier;
        }

        @Override
        public List<RegistrySupplier<T>> getEntries() {
            return this.entries;
        }
    }
}
