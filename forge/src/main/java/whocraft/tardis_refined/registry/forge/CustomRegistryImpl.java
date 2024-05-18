package whocraft.tardis_refined.registry.forge;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import whocraft.tardis_refined.registry.CustomRegistry;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;

public class CustomRegistryImpl<T> extends CustomRegistry<T> {

    private final Supplier<IForgeRegistry<T>> parent;
    private final ResourceKey<? extends Registry<T>> resourceKey;
    private final boolean syncToClient;

    public CustomRegistryImpl(ResourceLocation id, Supplier<IForgeRegistry<T>> parent, boolean syncToClient) {
        this.parent = parent;
        this.resourceKey = ResourceKey.createRegistryKey(id);
        this.syncToClient = syncToClient;
    }

    public static <T> CustomRegistry<T> create(ResourceLocation id, boolean syncToClient) {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(id, id.getNamespace());
        var supplier = syncToClient ? deferredRegister.makeRegistry(RegistryBuilder::new) : deferredRegister.makeRegistry(() -> new RegistryBuilder<T>().setMaxID(Integer.MAX_VALUE - 1).disableSync());
        deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        return new CustomRegistryImpl<>(id, supplier, syncToClient);
    }

    public static <T> CustomRegistry<T> create(ResourceLocation id) {
        return create(id, true);
    }

    @Override
    public ResourceKey<? extends Registry<T>> getRegistryKey() {
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

    @Override
    public Supplier<Codec<T>> getCodec() {
        return () -> this.parent.get().getCodec();
    }
}