package whocraft.tardis_refined.registry.fabric;


import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class DeferredRegistryImpl {

    public static <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    public static <T> DeferredRegistry<T> createCustom(String modid, ResourceKey<Registry<T>> resourceKey, boolean syncToClient) {
        //Add our custom registry to vanilla's list of registries
        //Must call FabricRegistryBuilder#createSimple directly and not in a supplier.
        WritableRegistry<T> registry = syncToClient ? FabricRegistryBuilder.create(resourceKey).attribute(RegistryAttribute.SYNCED).buildAndRegister() : FabricRegistryBuilder.create(resourceKey).buildAndRegister();
        return new Impl<>(modid, resourceKey, syncToClient, () -> registry);
    }


    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static class Impl<T> extends DeferredRegistry<T> {

        private final String modid;
        private final Supplier<WritableRegistry<T>> registry;
        private final boolean isCustom;
        private final boolean syncToClient;
        private final ResourceKey<? extends Registry<T>> resourceKey;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey, boolean isCustom, boolean syncToClient, Supplier<WritableRegistry<T>> registry) {
            this.modid = modid;
            this.resourceKey = resourceKey;
            this.isCustom = false;
            this.syncToClient = false;
            this.registry = registry;
        }

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this(modid, resourceKey, false, false, () -> (WritableRegistry<T>) BuiltInRegistries.REGISTRY.getValue(resourceKey.identifier()));
        }

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey, boolean syncToClient, Supplier<WritableRegistry<T>> registry) {
            this(modid, resourceKey, true, syncToClient, registry);
        }

        @Override
        public void registerToModBus() {

        }

        @Override
        public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
            Identifier registeredId = Identifier.fromNamespaceAndPath(this.modid, id);
            RegistrySupplier<R> registrySupplier = new RegistrySupplier<>(registeredId, Registry.register(this.registry.get(), registeredId, supplier.get()));
            return registrySupplier;
        }

        /** 1.20.1: Comment out due to Forge 1.20.1 not exposing the vanilla registry, thus we need to exclude this to have a common interface for both Fabric and Forge
         @Override public <I extends T> RegistrySupplierHolder<T, I> registerHolder(String id, Supplier<I> sup) {
         Identifier registeredId = Identifier.fromNamespaceAndPath(this.modid, id);
         Registry.register(this.registry.get(), registeredId, sup.get()); //Need to call this explicitly to register the object
         RegistrySupplierHolder<T, I> registryHolder = RegistrySupplierHolder.create(this.resourceKey, registeredId); //Create the holder, it will automatically bind the underlying value when the object is registered
         return registryHolder;
         }
         */

        /**
         * 1.20.1: Comment out due to Forge 1.20.1 not exposing the vanilla registry, thus we need to exclude this to have a common interface for both Fabric and Forge
         *
         * @Override public Supplier<Registry<T>> getRegistry() {
         * return () -> this.registry.get();
         * }
         */

        @Override
        public ResourceKey<? extends Registry<T>> key() {
            return this.resourceKey;
        }

        @Override
        public T get(Identifier key) {
            return this.registry.get().getValue(key);
        }

        @Override
        public Identifier getKey(T object) {
            return this.registry.get().getKey(object);
        }

        @Override
        public boolean containsKey(Identifier key) {
            return this.registry.get().containsKey(key);
        }

        @Override
        public Set<Identifier> keySet() {
            return this.registry.get().keySet();
        }

        @Override
        public Set<Map.Entry<ResourceKey<T>, T>> entrySet() {
            return this.registry.get().entrySet();
        }

        @Override
        public Supplier<Codec<T>> getCodec() {
            return () -> this.registry.get().byNameCodec();
        }
    }
}
