package whocraft.tardis_refined.registry.fabric;


import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.registry.CustomRegistry;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.RegistrySupplierHolder;

import java.util.function.Supplier;

public class DeferredRegistryImpl {

    public static <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    public static <T> DeferredRegistry<T> createCustom(String modid, CustomRegistry<T> registry) {
        return new Impl<>(modid, registry.key());
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static class Impl<T> extends DeferredRegistry<T> {

        private final String modid;
        private final Supplier<WritableRegistry<T>> registry;
        private final ResourceKey<? extends Registry<T>> resourceKey;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this.modid = modid;
            this.resourceKey = resourceKey;
            this.registry = () -> (WritableRegistry<T>) BuiltInRegistries.REGISTRY.get(resourceKey.location());
        }

        @Override
        public void registerToModBus() {

        }

        @Override
        public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
            ResourceLocation registeredId = new ResourceLocation(this.modid, id);
            RegistrySupplier<R> registrySupplier = new RegistrySupplier<>(registeredId, Registry.register(this.registry.get(), registeredId, supplier.get()));
            return registrySupplier;
        }

        @Override
        public <I extends T> RegistrySupplierHolder<T, I> registerHolder(String id, Supplier<I> sup) {
            ResourceLocation registeredId = new ResourceLocation(this.modid, id);
            Registry.register(this.registry.get(), registeredId, sup.get()); //Need to call this explicitly to register the object
            RegistrySupplierHolder<T, I> registryHolder = RegistrySupplierHolder.create(this.resourceKey, registeredId); //Create the holder, it will automatically bind the underlying value when the object is registered
            return registryHolder;
        }

        @Override
        public Registry<T> getRegistry() {
            return this.registry.get();
        }

        @Override
        public Supplier<Codec<T>> getCodec() {
            return () -> this.registry.get().byNameCodec();
        }
    }
}
