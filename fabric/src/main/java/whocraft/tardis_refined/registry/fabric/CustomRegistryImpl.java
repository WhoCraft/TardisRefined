package whocraft.tardis_refined.registry.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.registry.CustomRegistry;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class CustomRegistryImpl<T> extends CustomRegistry<T> {

    public static <T> CustomRegistry<T> create(ResourceKey<Registry<T>> resourceKey, boolean syncToClient) {
        //Add our custom registry to vanilla's list of registries
        //Must call FabricRegistryBuilder#createSimple directly and not in a supplier.
        WritableRegistry<T> registry = syncToClient ? FabricRegistryBuilder.createSimple(resourceKey).attribute(RegistryAttribute.SYNCED).buildAndRegister() : FabricRegistryBuilder.createSimple(resourceKey).buildAndRegister();
        return new CustomRegistryImpl<T>(resourceKey, syncToClient, () -> registry);
    }

    private Supplier<WritableRegistry<T>> registry;
    private final boolean syncToClient;

    private final ResourceKey<? extends Registry<T>> resourceKey;

    public CustomRegistryImpl(ResourceKey<? extends Registry<T>>  resourceKey, boolean syncToClient, Supplier<WritableRegistry<T>> registry) {
        this.resourceKey = resourceKey;
        this.registry = registry;
        this.syncToClient = syncToClient;
    }

    public CustomRegistryImpl(ResourceKey<? extends Registry<T>>  resourceKey, Supplier<WritableRegistry<T>> registry) {
        this(resourceKey,true, registry);
    }

    @Override
    public Supplier<Registry<T>> getRegistry() {
        return () -> this.registry.get();
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
}