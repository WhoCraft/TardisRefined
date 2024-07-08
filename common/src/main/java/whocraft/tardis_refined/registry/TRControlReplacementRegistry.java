package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.ControlReplacement;

/**
 * A registry for replacing generic controls ({@link TRControlRegistry#GENERIC_NO_SHOW}) for use by addon mods
 */
public class TRControlReplacementRegistry {

    public static final ResourceKey<Registry<ControlReplacement>> CONTROL_REPLACEMENT_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "control_replacements"));

    /** Instance of registry containing all ControlReplacement entries. Addon mod entries will be included in this registry as long as they are use the same ResourceKey<Registry<ObjectType>>. */
    public static final Registry<ControlReplacement> CONTROL_REPLACEMENT_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, CONTROL_REPLACEMENT_REGISTRY_KEY, true).getRegistry().get();

}
