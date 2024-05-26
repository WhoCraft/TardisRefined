package whocraft.tardis_refined.registry.neoforge;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import whocraft.tardis_refined.registry.CustomRegistry;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class CustomRegistryImpl<T> extends CustomRegistry<T> {

    private final Supplier<Registry<T>> registry;

    private final ResourceKey<? extends Registry<T>> resourceKey;
    private final boolean syncToClient;

    private final DeferredRegister<T> deferredRegister;

    public CustomRegistryImpl(ResourceKey<? extends Registry<T>> resourceKey, DeferredRegister<T> deferredRegister, boolean syncToClient) {
        this.resourceKey = resourceKey;
        this.deferredRegister = deferredRegister;
        this.registry = deferredRegister.getRegistry(); //Binds the underlying vanilla registry object to our object. DO NOT call DeferredRegister#makeRegistry here because it will attempt to create a duplicate copy.
        this.syncToClient = syncToClient;
    }

    public static <T> CustomRegistry<T> create(ResourceKey<Registry<T>> resourceKey, boolean syncToClient) {
        //Create a deferredRegister instance to be passed on to Tardis Refined's {@link DeferredRegister} object later
        DeferredRegister<T> deferredRegister = DeferredRegister.create(resourceKey, resourceKey.location().getNamespace());
        deferredRegister.makeRegistry(builder -> builder.maxId(Integer.MAX_VALUE - 1).sync(syncToClient)); //Tell Neoforge to register our registry when NewRegistryEvent fires. DO NOT call this anywhere else.
        return new CustomRegistryImpl<T>(resourceKey, deferredRegister, syncToClient);
    }

    @Override
    public Supplier<Registry<T>> getRegistry() {
        return this.registry;
    }

    @Override
    public ResourceKey<? extends Registry<T>> key(){
        return this.resourceKey;
    }

    @Override
    public T get(ResourceLocation key) {
        return this.getRegistry().get().get(key);
    }

    @Override
    public ResourceLocation getKey(T object) {
        return this.getRegistry().get().getKey(object);
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return this.getRegistry().get().containsKey(key);
    }

    @Override
    public Set<ResourceLocation> keySet() {
        return this.getRegistry().get().keySet();
    }

    @Override
    public Set<Map.Entry<ResourceKey<T>, T>> entrySet() {
        return this.getRegistry().get().entrySet();
    }

    @Override
    public Supplier<Codec<T>> getCodec() {
        return () -> this.getRegistry().get().byNameCodec();
    }

    public DeferredRegister<T> getDeferredRegister() {
        return this.deferredRegister;
    }
}