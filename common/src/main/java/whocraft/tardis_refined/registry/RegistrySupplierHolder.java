package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A lazy supplier for a value addressed by a registry key.
 *
 * Minecraft 26.x seals Holder, so mod code cannot implement a custom holder.
 */
public final class RegistrySupplierHolder<R, T extends R> implements Supplier<T> {
    private final ResourceKey<R> key;

    private RegistrySupplierHolder(ResourceKey<R> key) {
        this.key = Objects.requireNonNull(key);
    }

    public static <R, T extends R> RegistrySupplierHolder<R, T> create(ResourceKey<? extends Registry<R>> registryKey, Identifier valueName) {
        return create(ResourceKey.create(registryKey, valueName));
    }

    public static <R, T extends R> RegistrySupplierHolder<R, T> create(Identifier registryName, Identifier valueName) {
        return create(ResourceKey.createRegistryKey(registryName), valueName);
    }

    public static <R, T extends R> RegistrySupplierHolder<R, T> create(ResourceKey<R> key) {
        return new RegistrySupplierHolder<>(key);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Registry<R> registry() {
        return (Registry<R>) BuiltInRegistries.REGISTRY.get(key.registry()).map(holder -> holder.value()).orElse(null);
    }

    public Identifier getId() {
        return key.identifier();
    }

    public ResourceKey<R> getKey() {
        return key;
    }

    public boolean isBound() {
        Registry<R> registry = registry();
        return registry != null && registry.getValue(key.identifier()) != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        Registry<R> registry = registry();
        R value = registry == null ? null : registry.getValue(key.identifier());
        if (value == null) {
            throw new IllegalStateException("Trying to access unbound registry value: " + key);
        }
        return (T) value;
    }
}
