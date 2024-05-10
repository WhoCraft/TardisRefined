package whocraft.tardis_refined.common.crafting.astral_manipulator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.registry.TRManipulatorRecipeResultTypes;

public class ManipulatorItemResult extends ManipulatorCraftingResult{

    //Creates a MapCodec. We must use MapCodecs because the "type" field in our json will need to use MapCodecs
    // to determine what fields to deserialise.
    // In 1.20.5+ MapCodecs will also become mandatory so it's better to future-proof for it now
    public static final MapCodec<ManipulatorItemResult> MAP_CODEC =  RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ItemStack.CODEC.fieldOf("itemstack").forGetter(ManipulatorItemResult::recipeOutput)
            ).apply(instance, ManipulatorItemResult::new)
    );

    // Output to be summoned when the recipe is confirmed.
    public ItemStack recipeOutputItem;

    public ManipulatorItemResult(ItemStack recipeOutputItem) {
        this.recipeOutputItem = recipeOutputItem;
    }


    public ItemStack recipeOutput() {
        return this.recipeOutputItem;
    }

    @Override
    public Codec<? extends ManipulatorCraftingResult> type() {
        return TRManipulatorRecipeResultTypes.ITEM_RESULT.get();
    }

}
