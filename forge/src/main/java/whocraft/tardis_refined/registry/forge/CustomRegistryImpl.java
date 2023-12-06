package whocraft.tardis_refined.registry.forge;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.IForgeRegistry;
import net.neoforged.neoforge.registries.RegistryBuilder;
import whocraft.tardis_refined.registry.CustomRegistry;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;

public class CustomRegistryImpl<T> extends CustomRegistry<T> {



    public static <T> CustomRegistry<T> createInternal(Class<T> clazz, ResourceLocation id) {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(id, id.getNamespace());
        var supplier = deferredRegister.makeRegistry(RegistryBuilder::new);
        deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        return new CustomRegistryImpl<T>(id, supplier);
    }

    private final Supplier<IForgeRegistry<T>> parent;
    private final ResourceKey<Registry<T>> resourceKey;

    public CustomRegistryImpl(ResourceLocation id, Supplier<IForgeRegistry<T>> parent) {
        this.parent = parent;
        this.resourceKey = ResourceKey.createRegistryKey(id);
    }

    @Override
    public ResourceKey<Registry<T>> getRegistryKey() {
        return this.resourceKey;
    }

    @Override
    public T get(ResourceLocation key) {
        return this.parent.get().getValue(key);
    }

    @Override
    public ResourceLocation getKey(T object) {
        return this.parent.get().getKey(object);
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return this.parent.get().containsKey(key);
    }

    @Override
    public Set<ResourceLocation> getKeys() {
        return this.parent.get().getKeys();
    }

    @Override
    public Collection<T> getValues() {
        return this.parent.get().getValues();
    }
}