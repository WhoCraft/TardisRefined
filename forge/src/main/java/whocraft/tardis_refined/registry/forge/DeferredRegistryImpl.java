package whocraft.tardis_refined.registry.forge;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

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
            return new RegistrySupplier<>(orig.getId(), orig);
        }

        @Override
        public Registry<T> getRegistry() {
            Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location());
            if (registry == null) {
                throw new IllegalArgumentException("No registry with key " + registryKey);
            }
            return registry;
        }

        @Override
        public Supplier<Codec<T>> getCodec() {
            return () -> this.registry.byNameCodec();
        }
    }

}