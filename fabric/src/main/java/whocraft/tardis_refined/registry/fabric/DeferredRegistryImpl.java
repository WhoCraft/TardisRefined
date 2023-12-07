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
import whocraft.tardis_refined.registry.RegistrySupplierHolder;

import java.util.function.Supplier;

public class DeferredRegistryImpl {

    public static <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        return new Impl<>(modid, resourceKey);
    }

    public static <T> DeferredRegistry<T> createCustom(String modid, ResourceKey<Registry<T>> resourceKey, boolean syncToClient) {
        return new Impl<>(modid, resourceKey, syncToClient);
    }


    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static class Impl<T> extends DeferredRegistry<T> {

        private final String modid;
        private final WritableRegistry<T> registry;

        private ResourceKey<? extends Registry<T>> registryKey;

        private final boolean isCustom;

        private final boolean syncToClient;

        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this.modid = modid;
            this.registryKey = resourceKey;
            this.isCustom = false;
            this.syncToClient = false;
            this.registry = (WritableRegistry<T>) BuiltInRegistries.REGISTRY.get(resourceKey.location());
        }

        public Impl(String modid, ResourceKey<Registry<T>> resourceKey, boolean syncToClient) {
            this.modid = modid;
            this.registryKey = resourceKey;
            this.isCustom = true;
            this.syncToClient = syncToClient;
            this.registry = syncToClient ? (MappedRegistry<T>)FabricRegistryBuilder.createSimple(resourceKey).attribute(RegistryAttribute.SYNCED).buildAndRegister() : (MappedRegistry<T>)FabricRegistryBuilder.createSimple(resourceKey).buildAndRegister();
            
        }

        @Override
        public void register() {

        }

        @Override
        public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
            ResourceLocation registeredId = new ResourceLocation(this.modid, id);
            RegistrySupplier<R> registrySupplier = new RegistrySupplier<>(registeredId, Registry.register(this.registry, registeredId, supplier.get()));
            return registrySupplier;
        }

        @Override
        public <I extends T> RegistrySupplierHolder<T, I> registerHolder(String id, Supplier<I> sup) {
            ResourceLocation registeredId = new ResourceLocation(this.modid, id);
            RegistrySupplierHolder<T,I> registryHolder = RegistrySupplierHolder.create(this.registryKey, registeredId);
            return registryHolder;
        }

        @Override
        public Registry<T> getRegistry() {
            return this.registry;
        }

        @Override
        public Codec<T> getCodec() {
            return this.registry.byNameCodec();
        }
    }
}
