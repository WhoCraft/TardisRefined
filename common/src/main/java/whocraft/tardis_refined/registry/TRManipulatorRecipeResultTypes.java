package whocraft.tardis_refined.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorBlockResult;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorCraftingResult;
import whocraft.tardis_refined.common.crafting.astral_manipulator.ManipulatorItemResult;

/** 50ap5ud5 11/05/2024: Register our own recipe types to allow for different data to be defined depending on the recipe type*/
public class TRManipulatorRecipeResultTypes {

    /** Registry Key for the Astral Manipulator Recipe Result type registry. For addon mods, use this as the registry key*/
    public static final ResourceLocation MANIPULATOR_RECIPE_TYPE = new ResourceLocation(TardisRefined.MODID, "manipulator_recipe_result");

    /** Tardis Refined instance of the Astral Manipulator Recipe Result type registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only*/
    public static CustomRegistry<Codec<? extends ManipulatorCraftingResult>> RESULT_TYPES = CustomRegistry.create(MANIPULATOR_RECIPE_TYPE);

    /** Global instance of the Astral Manipulator Recipe Result type custom registry created by Tardis Refined*/
    public static final DeferredRegistry<Codec<? extends ManipulatorCraftingResult>> RESULT_TYPES_DEFERRED_REGISTER = DeferredRegistry.create(TardisRefined.MODID, RESULT_TYPES);

    /* Register the codec derived from the MapCodec for each entry since the MapCodec isn't actually inheriting from Codec.
    This is needed because for registration, NBT and JSON read/writing, we need the Codec. However, when we need to find the sub-codec based off the "type" key, we need to use the MapCodec
    */
    public static final RegistrySupplier<Codec<ManipulatorItemResult>> ITEM_RESULT = RESULT_TYPES_DEFERRED_REGISTER.register("item_result", () -> ManipulatorItemResult.MAP_CODEC.codec());
    public static final RegistrySupplier<Codec<ManipulatorBlockResult>> BLOCK_RESULT = RESULT_TYPES_DEFERRED_REGISTER.register("block_result", () -> ManipulatorBlockResult.MAP_CODEC.codec());
}
