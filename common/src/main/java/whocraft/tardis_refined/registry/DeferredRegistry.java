package whocraft.tardis_refined.registry;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import whocraft.tardis_refined.TardisRefined;

import java.util.function.Supplier;

/** Abstraction of a Registry handler based off the design patterns of Forge's DeferredRegister.
 * <br> For {@link CustomRegistry} added by TardisRefined, we will be appending new registry entries to the entries in {@link CustomRegistry}
 * <br> This object is to be used for registering objects to a specific registry for this mod
 * */
public abstract class DeferredRegistry<T> {
    /** Call in main mod constructor to classload the registry class. On Forge/NeoForge environments, the necessary event buses for registries will be called*/
    public abstract void registerToModBus();
    /** Register using a Supplier */
    public abstract <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier);

    /** Create a RegistrySupplierHolder. This has similar behaviour to a Supplier but has vanilla Holder attributes such as use in tags*/
    public abstract <I extends T> RegistrySupplierHolder<T, I> registerHolder(final String name, final Supplier<I> sup);

    /** Get the underlying registry, which includes all entries added by any mod that has a DeferredRegistry with the same ResourceKey.
     * <br> lookup methods should be called from here.
     * <br> For custom registries created by Tardis Refined, use {@link CustomRegistry#getRegistry()} */
    public abstract Registry<T> getRegistry();

    /** Gets the underlying Codec for the registry object type, if defined. Required for reading/writing data to different formats for data-driven*/
    public abstract Supplier<Codec<T>> getCodec();

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

    /**
     * Create a DeferredRegistry instance for custom registries
     * Required as a separate method for both readability and to account for specific mod loader behaviours.
     * <br> In Fabric, the registration of custom registries happens immediately. In Neoforge/Forge, registration is delayed until the NewRegistriesEvent is fired
     * @param modid - Your Mod's unique identifier
     * @param registry - Custom Registry to use for registering entries to it
     * @return
     * @param <T>
     */
    @ExpectPlatform
    public static <T> DeferredRegistry<T> createCustom(String modid, CustomRegistry<T> registry) {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }
}
