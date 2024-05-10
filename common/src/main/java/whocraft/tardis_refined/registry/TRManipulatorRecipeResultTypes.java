package whocraft.tardis_refined.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorBlockResult;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorCraftingResult;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorItemResult;

public class TRManipulatorRecipeResultTypes {

    /** Registry Key for the Astral Manipulator Recipe Result type registry. For addon mods, use this as the registry key*/
    public static final ResourceKey<Registry<Codec<? extends ManipulatorCraftingResult>>> MANIPULATOR_RECIPE_TYPE_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "manipulator_recipe_result"));

    /** Tardis Refined instance of the Astral Manipulator Recipe Result type registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only*/
    public static final DeferredRegistry<Codec<? extends ManipulatorCraftingResult>> RESULT_TYPES = DeferredRegistry.createCustom(TardisRefined.MODID, MANIPULATOR_RECIPE_TYPE_KEY, true);

    /** Global instance of the Astral Manipulator Recipe Result type custom registry created by Tardis Refined*/
    public static final Registry<Codec<? extends ManipulatorCraftingResult>> RESULT_TYPE_REGISTRY = RESULT_TYPES.getRegistry();

    public static final RegistrySupplier<Codec<ManipulatorItemResult>> ITEM_RESULT = RESULT_TYPES.register("item_result", () -> ManipulatorItemResult.MAP_CODEC.codec());
    public static final RegistrySupplier<Codec<ManipulatorBlockResult>> BLOCK_RESULT = RESULT_TYPES.register("block_result", () -> ManipulatorBlockResult.MAP_CODEC.codec());
}
