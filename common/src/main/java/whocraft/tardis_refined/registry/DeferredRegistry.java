package whocraft.tardis_refined.registry;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import whocraft.tardis_refined.TardisRefined;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Abstraction of a Registry handler based off the design patterns of Forge's DeferredRegister.
 */
public abstract class DeferredRegistry<T> {
    /**
     * Create a DeferredRegistry instance for vanilla registries
     *
     * @param modid       - Your Mod's unique identifier
     * @param resourceKey - Resource Key for the Registry
     * @param <T>
     * @return
     */
    @ExpectPlatform
    public static <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }

    public static <T> DeferredRegistry<T> create(String modid, CustomRegistry<T> registry) {
        return create(modid, registry.getRegistryKey());
    }

    /**
     * Call in main mod constructor to classload the registry class. On Forge/NeoForge environments, the necessary event buses for registries will be called
     */
    public abstract void register();

    /**
     * Register using a Supplier
     */
    public abstract <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier);

    public abstract List<RegistrySupplier<T>> getEntries();
}
