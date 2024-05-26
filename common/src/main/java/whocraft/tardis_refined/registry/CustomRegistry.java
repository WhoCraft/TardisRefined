package whocraft.tardis_refined.registry;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/** This is a dedicated wrapper object for object types that require a custom registry to be added to
 * <br> This object stores a vanilla {@link Registry} instance which means all addon mods will need to use this as the source of truth.
 * <br> For example, here is what the main Tardis Refined mod's registry class might look like:
 * <pre>{@code
 * public class TRControlRegistry {
 *
 *     public static final ResourceKey<Registry<Control>> CONTROL_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "control"));
 *
 *     public static final CustomRegistry<Control> GLOBAL_CONTROL_REGISTRY =  CustomRegistry.create(CONTROL_REGISTRY_KEY);
 *
 *     public static final DeferredRegistry<Control> TR_CONTROLS =  DeferredRegistry.createCustom(TardisRefined.MODID, GLOBAL_CONTROL_REGISTRY);
 *
 *     public static final RegistrySupplier<Control> THROTTLE = TR_CONTROLS.register("throttle", () -> new GenericControl(new ResourceLocation(TardisRefined.MODID, "throttle"));
 *}
 *
 * public class TardisConsoleBlock {
 *
 *     public Set<ResourceLocation> getAllControls() {
 *         return TRControlRegistry.GLOBAL_CONTROL_REGISTRY.keySet();
 *     }
 * }
 *
 * public class TardisRefined {
 *     public TardisRefined() {
 *         TR_CONTROLS.registerToModBus();
 *     }
 * }
 * }</pre>
 * <br> And here is how the addon mod's registry class may look like:
 * <pre>{@code
 * public class AddonModControlRegistry {
 *     private static final DeferredRegistry<Control> MY_ADDON_CONTROLS =  DeferredRegistry.createCustom(MyAddonMod.MODID, TRControlRegistry.GLOBAL_CONTROL_REGISTRY);
 *
 *     public static final RegistrySupplier<Control> SPEED = MY_ADDON_CONTROLS.register("speed", () -> new GenericControl(new ResourceLocation(MyAddonMod.MODID, "speed"));
 * }
 *
 * public class MyAddonMod {
 *     public MyAddonMod() {
 *         MY_ADDON_CONTROLS.registerToModBus();
 *     }
 * }
 * }</pre>
 * <br> As you can see, the Addon Mod just needs to reference TardisRefined's original CustomRegistry instance for when it needs to append new entries to an existing registry
 * <br> This ensures there is a global source of truth for all our TardisRefined object types, and we don't accidentally restrict the mod to only use Tardis Refined's entries in areas where addon mods are likely to add additional content. E.g. Upgrades
 * */
public abstract class CustomRegistry<T> {

    public static <T> CustomRegistry<T> create(ResourceKey<Registry<T>> resourceKey) {
        return create(resourceKey, true);
    }

    @ExpectPlatform
    public static <T> CustomRegistry<T> create(ResourceKey<Registry<T>> resourceKey, boolean syncToClient) {
        throw new AssertionError();
    }

    /** Get the underlying registry, which includes all entries added by any mod that has a DeferredRegistry that registers to this CustomRegistry instance.
     * Any lookup methods that are not already exposed, such as getTag can be called from this object
     * <br> We use a supplier here because on Neoforge the custom registry may not be initialised until the NewRegistry event is fired*/
    public abstract Supplier<Registry<T>> getRegistry();

    /** Below are exposing of the methods in Registry for convenience. This reduces verbosity for lookup operations which we will often be performing*/

    public abstract ResourceKey<? extends Registry<T>> key();

    public abstract T get(ResourceLocation key);

    public abstract ResourceLocation getKey(T object);

    public abstract Set<ResourceLocation> keySet();

    /** Gets the values in the CustomRegistry ordered by key. This is sufficient for most purposes
     * <br> If you need to get values in order of registration, use {@link CustomRegistry#getRegistry} then {@link Registry#holders} */
    public abstract Set<Map.Entry<ResourceKey<T>, T>> entrySet();

    public abstract boolean containsKey(ResourceLocation key);


    /** Gets the underlying Codec for the registry object type, if defined.
     * <br> Required for reading/writing data to different formats for data-driven features*/
    public abstract Supplier<Codec<T>> getCodec();

}
