package whocraft.tardis_refined.registry.neoforge;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplierHolder;
import whocraft.tardis_refined.registry.RegistrySupplier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DeferredRegistryImpl {

    public static <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    public static <T> DeferredRegistry<T> createCustom(String modid, ResourceKey<? extends Registry<T>> resourceKey, boolean syncToClient) {
        return new Impl<>(modid, resourceKey, true, syncToClient);
    }

    @SuppressWarnings("unchecked")
    public static class Impl<T> extends DeferredRegistry<T> {

        private final DeferredRegister<T> register;

        private Registry<T> registry;

        private ResourceKey<? extends Registry<T>> registryKey;

        private Map<RegistrySupplierHolder<T, ?>, Supplier<? extends T>> entries = new LinkedHashMap<>();

        private boolean isCustom;

        private boolean syncToClient;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this(modid, resourceKey, false, false);
        }

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey, boolean isCustom, boolean sync) {
            this.registryKey = resourceKey;
            this.register = DeferredRegister.create(resourceKey, modid);
            this.isCustom = isCustom;
            this.syncToClient = sync;
        }

        @Override
        public void register() {
            this.register.register(FMLJavaModLoadingContext.get().getModEventBus());
        }


        @Override
        public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
            var orig = this.register.register(id, supplier);
            var registrySupplier = new RegistrySupplier<>(orig.getId(), orig);
            return registrySupplier;
        }

        @Override
        public <I extends T> RegistrySupplierHolder<T, I> registerHolder(String id, Supplier<I> sup) {
            DeferredHolder<T, I> deferredHolder = this.register.register(id, sup);
            RegistrySupplierHolder<T,I> registryHolder = RegistrySupplierHolder.create(this.registryKey, deferredHolder.getId());
            return registryHolder;
        }

        @Override
        public Registry<T> getRegistry() {
            this.registry = this.isCustom ? this.register.makeRegistry(builder -> builder.maxId(Integer.MAX_VALUE - 1).sync(this.syncToClient)) : (Registry<T>) BuiltInRegistries.REGISTRY.get(this.registryKey.location());
            return this.registry;
        }

        @Override
        public Codec<T> getCodec() {
            return this.registry.byNameCodec();
        }
    }

}