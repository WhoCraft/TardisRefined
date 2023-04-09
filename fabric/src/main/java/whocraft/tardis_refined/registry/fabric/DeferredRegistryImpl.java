package whocraft.tardis_refined.registry.fabric;


import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.BuiltinRegistries;
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

    public static <T> DeferredRegistry<T> createCustom(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey, true);
    }


    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static class Impl<T> extends DeferredRegistry<T> {

        private final String modid;
        private final WritableRegistry<T> registry;
        private final List<RegistrySupplier<T>> entries;

        private final boolean isCustom;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this.modid = modid;
            this.registry = (WritableRegistry<T>) MoreObjects.firstNonNull(Registry.REGISTRY.get(resourceKey.location()), BuiltinRegistries.REGISTRY.get(resourceKey.location()));
            this.entries = new ArrayList<>();
            this.isCustom = false;
        }

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey, boolean isCustom) {
            this.modid = modid;
            this.registry = FabricRegistryBuilder.from(new MappedRegistry<T>(resourceKey, Lifecycle.stable(), null)).buildAndRegister(); //Should use createSimple, but it requires a Class Type. in 1.19.3+ Fabric API versions will remove the need for a Class type, so this is temporary
            this.entries = new ArrayList<>();
            this.isCustom = isCustom;
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
        public Collection<RegistrySupplier<T>> getEntries() {
            return this.entries;
        }

        @Override
        public Codec<T> getCodec() {
            return this.registry.byNameCodec();
        }
    }
}
