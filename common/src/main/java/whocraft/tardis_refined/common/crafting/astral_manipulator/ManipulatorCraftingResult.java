package whocraft.tardis_refined.common.crafting.astral_manipulator;

import com.mojang.serialization.Codec;
import whocraft.tardis_refined.registry.TRManipulatorRecipeResultTypes;

import java.util.function.Function;

/**
 * Base Object for different types of recipe results, such as itemstack or blockstate or block structure in the recipe json.
 * Example: Subclasses of this object determines whether to place the result at the center position or create an item entity.
 * <br> This is required because since we use Codecs to de/serialise recipes, we will need to use sub codecs depending on the result type, hence this is needed*/
public abstract class ManipulatorCraftingResult {

    /** Defines a Codec which shows what data should be used to make up the result object based on the "type" field defined by the master codec*/
    public abstract Codec<? extends ManipulatorCraftingResult> type();

    /** Master Codec object for the recipe result. We use dispatch codec to create a key-value ledger of the different Result types, and the data format (in the form of Codecs) for each type.
     * Each type of recipe result is defined as a registry object, registered to a custom registry in {@link TRManipulatorRecipeResultTypes }*/
    public static final Codec<ManipulatorCraftingResult> RESULT_CODEC = TRManipulatorRecipeResultTypes.RESULT_TYPES.getCodec().get().dispatch(ManipulatorCraftingResult::type, Function.identity());


}
