package whocraft.tardis_refined.registry;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import whocraft.tardis_refined.TardisRefined;

import java.util.function.Supplier;

/** Abstraction of a Registry handler based off the design patterns of Forge's DeferredRegister. */
public abstract class DeferredRegistry<T> {
    /** Call in main mod constructor to classload the registry class. On Forge/NeoForge environments, the necessary event buses for registries will be called*/
    public abstract void register();
    /** Register using a Supplier */
    public abstract <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier);

    /** Create a RegistrySupplierHolder. This has similar behaviour to a Supplier but has vanilla Holder attributes such as use in tags*/
    public abstract <I extends T> RegistrySupplierHolder<T, I> registerHolder(final String name, final Supplier<I> sup);

    /** Get the underlying registry, which includes all entries added by any mod that has a DeferredRegistry with the same ResourceKey.
     * All lookup methods should be called from here.*/
    public abstract Registry<T> getRegistry();

    /**
     * Create a DeferredRegistry instance for vanilla registries
     * @param modid - Your Mod's unique identifier
     * @param resourceKey - Resource Key for the Registry
     * @return
     * @param <T>
     */
    @ExpectPlatform
    public static <T> DeferredRegistry<T> create(String modid, ResourceKey<? extends Registry<T>> resourceKey) {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }

    /** Gets the underlying Codec for the registry object type, if defined. Currently unused and untested, but added for completeness*/
    public abstract Supplier<Codec<T>> getCodec();

    /**
     * Create a DeferredRegistry instance for custom registries
     * @param modid - Your Mod's unique identifier
     * @param resourceKey - Resource Key for the Registry
     * @param syncToClient - True if we want the objects to sync to the client.
     * @return
     * @param <T>
     */
    @ExpectPlatform
    public static <T> DeferredRegistry<T> createCustom(String modid, ResourceKey<Registry<T>> resourceKey, boolean syncToClient) {
        throw new AssertionError();
    }
}
