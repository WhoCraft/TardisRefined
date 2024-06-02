package whocraft.tardis_refined.registry.forge;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;
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
        //Create a deferredRegister instance to be passed on to Tardis Refined's {@link DeferredRegister} object later
        DeferredRegister<T> deferredRegister = DeferredRegister.create(resourceKey, modid);
        var supplier = syncToClient ? deferredRegister.makeRegistry(() -> new RegistryBuilder<T>().setMaxID(Integer.MAX_VALUE - 1)) : deferredRegister.makeRegistry(() -> new RegistryBuilder<T>().setMaxID(Integer.MAX_VALUE - 1).disableSync()); //Tell Forge to register our registry when NewRegistryEvent fires. DO NOT call this anywhere else.
        return new Impl<T>(resourceKey, deferredRegister, supplier, syncToClient);
    }

    public static class Impl<T> extends DeferredRegistry<T> {

        private final DeferredRegister<T> deferredRegister;
        private final ResourceKey<? extends Registry<T>> resourceKey;
        private final boolean isCustom; //If this deferred registry instance is for a custom registry
        private final boolean syncToClient;
        private Registry<T> registry;
        private IForgeRegistry<T> forgeRegistry;

        public Impl(ResourceKey<? extends Registry<T>> resourceKey, DeferredRegister<T> deferredRegister, Supplier<IForgeRegistry<T>> forgeRegistry, boolean isCustom, boolean syncToClient) {
            this.resourceKey = resourceKey;
            this.deferredRegister = deferredRegister;
            this.forgeRegistry = forgeRegistry.get();
            this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(this.resourceKey.location());
            this.isCustom = isCustom;
            this.syncToClient = syncToClient;
        }

        /**
         * Constructor for custom registries.
         */
        public Impl(ResourceKey<? extends Registry<T>> resourceKey, DeferredRegister<T> deferredRegister, Supplier<IForgeRegistry<T>> forgeRegistry, boolean syncToClient) {
            this(resourceKey, deferredRegister, forgeRegistry, true, syncToClient);
        }

        /**
         * Constructor for non-custom registries. Creates a Neoforge {@link DeferredRegister} instance.
         */
        public Impl(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
            this(resourceKey, DeferredRegister.create(resourceKey, modid), () -> RegistryManager.ACTIVE.getRegistry(resourceKey), false, false);
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

        /** 1.20.1: Comment out due to Forge 1.20.1 not exposing the vanilla registry, thus we need to exclude this to have a common interface for both Fabric and Forge
         @Override public <I extends T> RegistrySupplierHolder<T, I> registerHolder(String id, Supplier<I> sup) {
         RegistryObject<I> registryObject = this.deferredRegister.register(id, sup);
         RegistrySupplierHolder<T, I> registryHolder = RegistrySupplierHolder.create(this.resourceKey, registryObject.getId());
         return registryHolder;
         }
         */

        /**
         * 1.20.1: Comment out due to Forge 1.20.1 not exposing the vanilla registry, thus we need to exclude this to have a common interface for both Fabric and Forge
         *
         * @Override public Supplier<Registry<T>> getRegistry() {
         * return () -> this.registry;
         * }
         */

        public IForgeRegistry<T> getForgeRegistry() {
            if (this.forgeRegistry == null)
                this.forgeRegistry = RegistryManager.ACTIVE.getRegistry(this.resourceKey);
            return this.forgeRegistry;
        }

        @Override
        public ResourceKey<? extends Registry<T>> key() {
            return this.resourceKey;
        }

        @Override
        public T get(ResourceLocation key) {
            return this.getForgeRegistry().getValue(key);
        }

        @Override
        public ResourceLocation getKey(T object) {
            return this.getForgeRegistry().getKey(object);
        }

        @Override
        public boolean containsKey(ResourceLocation key) {
            return this.getForgeRegistry().containsKey(key);
        }

        @Override
        public Set<ResourceLocation> keySet() {
            return this.getForgeRegistry().getKeys();
        }

        @Override
        public Set<Map.Entry<ResourceKey<T>, T>> entrySet() {
            return this.getForgeRegistry().getEntries();
        }

        @Override
        public Supplier<Codec<T>> getCodec() {
            return () -> this.getForgeRegistry().getCodec();
        }
    }

}