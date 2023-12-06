package whocraft.tardis_refined.registry;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * An implementation of vanilla's Holder where it is a Holder constructed only with a ResourceKey.
 * It will be populated with the underlying Holder from the registry when available.
 * @param <T> The type of object being held by this RegistryHolder
 *
 * <br> Based off NeoForge's DeferredHolder
 * <br> https://github.com/neoforged/NeoForge/blob/1.20.x/src/main/java/net/neoforged/neoforge/registries/DeferredHolder.java
 * */
public class RegistrySupplierHolder<R, T extends R> implements Holder<R>, Supplier<T> {

    /**
     * Creates a new RegistrySupplierHolder targeting the value with the specified name in the specified registry.
     *
     * @param <T>         The type of the target value.
     * @param <R>         The registry type.
     * @param registryKey The name of the registry the target value is a member of.
     * @param valueName   The name of the target value.
     */
    public static <R, T extends R> RegistrySupplierHolder<R, T> create(ResourceKey<? extends Registry<R>> registryKey, ResourceLocation valueName) {
        return create(ResourceKey.create(registryKey, valueName));
    }

    /**
     * Creates a new RegistrySupplierHolder targeting the value with the specified name in the specified registry.
     *
     * @param <T>          The registry type.
     * @param registryName The name of the registry the target value is a member of.
     * @param valueName    The name of the target value.
     */
    public static <R, T extends R> RegistrySupplierHolder<R, T> create(ResourceLocation registryName, ResourceLocation valueName) {
        return create(ResourceKey.createRegistryKey(registryName), valueName);
    }


    protected final ResourceKey<R> key;

    @Nullable
    private Holder<R> holder = null;

    /**
     * Creates a new RegistrySupplierHolder with a ResourceKey.
     * Attempts to bind immediately if possible.
     * Params:
     * @param key – The resource key of the target object.
     * @see #create(ResourceLocation, ResourceLocation)
     * @see #create(ResourceKey, ResourceLocation)
     */
    protected RegistrySupplierHolder(ResourceKey<R> key) {
        this.key = Objects.requireNonNull(key);
        this.bind(false);
    }

    public static <R, T extends R> RegistrySupplierHolder<R, T> create(ResourceKey<R> key) {
        return new RegistrySupplierHolder<>(key);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    protected Registry<R> getRegistry() {
        return (Registry<R>) BuiltInRegistries.REGISTRY.get(this.key.registry());
    }

    protected final void bind(boolean throwOnMissingRegistry) {
        if (this.holder != null) return;

        Registry<R> registry = getRegistry();
        if (registry != null) {
            this.holder = registry.getHolder(this.key).orElse(null);
        } else if (throwOnMissingRegistry) {
            throw new IllegalStateException("Registry not present for " + this + ": " + this.key.registry());
        }
    }

    /**
     * @return The ID of the object pointed to by this RegistrySupplierHolder.
     */
    public ResourceLocation getId() {
        return this.key.location();
    }

    /**
     * @return The ResourceKey of the object pointed to by this RegistrySupplierHolder.
     */
    public ResourceKey<R> getKey() {
        return this.key;
    }

    @Override
    public T value() {
        bind(true);
        if (this.holder == null) {
            throw new NullPointerException("Trying to access unbound value: " + this.key);
        }

        return (T) this.holder.value();
    }

    /**
     * {@return true if the underlying object is available}
     *
     * <p>If {@code true}, the underlying object was added to the registry,
     * and {@link #value()} or {@link #get()} can be called.
     */
    @Override
    public boolean isBound() {
        bind(false);
        return this.holder != null && this.holder.isBound();
    }

    /**
     * {@return true if the passed ResourceLocation is the same as the ID of the target object}
     */
    @Override
    public boolean is(ResourceLocation id) {
        return id.equals(this.key.location());
    }

    /**
     * {@return true if the passed ResourceKey is the same as this holder's resource key}
     */
    @Override
    public boolean is(ResourceKey<R> key) {
        return key == this.key;
    }

    /**
     * Evaluates the passed predicate against this holder's resource key.
     *
     * @return {@code true} if the filter matches {@linkplain #getKey() this DH's resource key}
     */
    @Override
    public boolean is(Predicate<ResourceKey<R>> filter) {
        return filter.test(this.key);
    }

    /**
     * {@return true if this holder is a member of the passed tag}
     */
    @Override
    public boolean is(TagKey<R> tag) {
        bind(false);
        return this.holder != null && this.holder.is(tag);
    }

    /**
     * {@return all tags present on the underlying object}
     *
     * <p>If the underlying object is not {@linkplain #isBound() bound} yet, and empty stream is returned.
     */
    @Override
    public Stream<TagKey<R>> tags() {
        bind(false);
        return this.holder != null ? this.holder.tags() : Stream.empty();
    }

    /**
     * Returns an {@link Either#left()} containing {@linkplain #getKey() the resource key of this holder}.
     *
     * @apiNote This method is implemented for {@link Holder} compatibility, but {@link #getKey()} should be preferred.
     */
    @Override
    public Either<ResourceKey<R>, R> unwrap() {
        // Holder.Reference always returns the key, do the same here.
        return Either.left(this.key);
    }

    /**
     * Returns the resource key of this holder.
     *
     * @return a present optional containing {@linkplain #getKey() the resource key of this holder}
     * @apiNote This method is implemented for {@link Holder} compatibility, but {@link #getKey()} should be preferred.
     */
    @Override
    public Optional<ResourceKey<R>> unwrapKey() {
        return Optional.of(this.key);
    }

    @Override
    public Kind kind() {
        return Kind.REFERENCE;
    }

    @Override
    public boolean canSerializeIn(HolderOwner<R> owner) {
        bind(false);
        return this.holder != null && this.holder.canSerializeIn(owner);
    }

    @Override
    public T get() {
        return this.value();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof RegistrySupplierHolder<?, ?> rh && rh.key == this.key;
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "RegistrySupplierHolder{%s}", this.key);
    }
}
