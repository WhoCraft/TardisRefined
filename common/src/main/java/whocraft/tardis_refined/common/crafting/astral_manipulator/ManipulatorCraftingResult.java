package whocraft.tardis_refined.common.crafting.astral_manipulator;

import com.mojang.serialization.Codec;
import whocraft.tardis_refined.registry.TRManipulatorRecipeResultTypes;

import java.util.function.Function;

/**
 * Base Object for different types of recipe results, such as itemstack or blockstate or block structure in the recipe json.
 * Example: Subclasses of this object determines whether to place the result at the center position or create an item entity.
 * <br> This is required because since we use Codecs to de/serialise recipes, we will need to use sub codecs depending on the result type, hence this is needed*/
public abstract class ManipulatorCraftingResult {

    public abstract Codec<? extends ManipulatorCraftingResult> type();

    public static final Codec<ManipulatorCraftingResult> RESULT_CODEC = TRManipulatorRecipeResultTypes.RESULT_TYPES.getCodec().get().dispatch(ManipulatorCraftingResult::type, Function.identity());


}
