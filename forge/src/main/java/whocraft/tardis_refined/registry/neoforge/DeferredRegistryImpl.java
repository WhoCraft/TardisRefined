package whocraft.tardis_refined.registry.neoforge;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import whocraft.tardis_refined.registry.CustomRegistry;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.RegistrySupplierHolder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DeferredRegistryImpl {

    public static <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    public static <T> DeferredRegistry<T> createCustom(String modid, CustomRegistry<T> registry) {
        CustomRegistryImpl<T> impl = (CustomRegistryImpl<T>)registry;
        return new Impl<>(modid, impl.key(), impl.getDeferredRegister()); //Pass in the unique DeferredRegister instance
    }

    @SuppressWarnings("unchecked")
    public static class Impl<T> extends DeferredRegistry<T> {

        private final DeferredRegister<T> deferredRegister;

        private final Registry<T> registry;

        private final ResourceKey<? extends Registry<T>> resourceKey;

        /** Main constructor to allow custom registries to parse in their {@link DeferredRegister} instance that was already handled before*/
        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey, DeferredRegister<T> deferredRegistry) {
            this.resourceKey = resourceKey;
            this.deferredRegister = deferredRegistry;
            this.registry = deferredRegistry.getRegistry().get(); //Pass in the vanilla registry object.
        }

        /** Constructor for non-custom registries. Creates a Neoforge {@link DeferredRegister} instance. For custom registries, we will create it in {@link CustomRegistryImpl} */
        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey){
            this(modid, resourceKey, DeferredRegister.create(resourceKey, modid));
        }

        @Override
        public void registerToModBus() {
            this.deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        }


        @Override
        public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
            var orig = this.deferredRegister.register(id, supplier);
            var registrySupplier = new RegistrySupplier<>(orig.getId(), orig);
            return registrySupplier;
        }

        @Override
        public <I extends T> RegistrySupplierHolder<T, I> registerHolder(String id, Supplier<I> sup) {
            DeferredHolder<T, I> deferredHolder = this.deferredRegister.register(id, sup);
            RegistrySupplierHolder<T, I> registryHolder = RegistrySupplierHolder.create(this.resourceKey, deferredHolder.getId());
            return registryHolder;
        }

        @Override
        public Registry<T> getRegistry() {
            return this.registry;
        }

        @Override
        public Supplier<Codec<T>> getCodec() {
            return () -> this.registry.byNameCodec();
        }
    }

}