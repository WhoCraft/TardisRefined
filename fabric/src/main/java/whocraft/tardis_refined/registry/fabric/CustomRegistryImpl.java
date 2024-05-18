package whocraft.tardis_refined.registry.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.registry.CustomRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CustomRegistryImpl<T> extends CustomRegistry<T> {

    public static <T> CustomRegistry<T> create(ResourceLocation id, boolean syncToClient) {
        ResourceKey<Registry<T>> resourceKey = ResourceKey.createRegistryKey(id);
        return syncToClient ? new CustomRegistryImpl<>(FabricRegistryBuilder.createSimple(resourceKey).attribute(RegistryAttribute.SYNCED).buildAndRegister(), true) : new CustomRegistryImpl<>(FabricRegistryBuilder.createSimple(resourceKey).buildAndRegister(), false);
    }

    public static <T> CustomRegistry<T> create(ResourceLocation id) {
        return create(id, true);
    }

    private final WritableRegistry<T> parent;
    private final boolean syncToClient;

    public CustomRegistryImpl(WritableRegistry<T> parent, boolean syncToClient) {
        this.parent = parent;
        this.syncToClient = syncToClient;
    }

    public CustomRegistryImpl(WritableRegistry<T> parent) {
        this(parent, true);
    }

    @Override
    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return this.parent.key();
    }

    @Override
    public T get(ResourceLocation key) {
        return this.parent.get(key);
    }

    @Override
    public ResourceLocation getKey(T object) {
        return this.parent.getKey(object);
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return this.parent.containsKey(key);
    }

    @Override
    public Set<ResourceLocation> getKeys() {
        return this.parent.keySet();
    }

    @Override
    public Collection<T> getValues() {
        return this.parent.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public Supplier<Codec<T>> getCodec() {
        return this.parent::byNameCodec;
    }
}