package whocraft.tardis_refined.common.crafting.astral_manipulator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import whocraft.tardis_refined.registry.TRManipulatorRecipeResultTypes;

public class ManipulatorItemResult extends ManipulatorCraftingResult {

    //Creates a MapCodec. We must use MapCodecs because the "type" field in our json will need to use MapCodecs
    // to determine what fields to deserialise.
    // In 1.20.5+ MapCodecs will also become mandatory so it's better to future-proof for it now
    public static final MapCodec<ManipulatorItemResult> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Identifier.CODEC.fieldOf("id").forGetter(ManipulatorItemResult::itemId),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(ManipulatorItemResult::count)
            ).apply(instance, ManipulatorItemResult::new)
    );

    // Keep only registry-safe data while datapack recipes are decoded. ItemStack construction
    // requires item components, which are not ready at this stage of registry loading.
    private final Identifier itemId;
    private final int count;

    public ManipulatorItemResult(ItemStack recipeOutputItem) {
        this(BuiltInRegistries.ITEM.getKey(recipeOutputItem.getItem()), recipeOutputItem.getCount());
    }

    public ManipulatorItemResult(Identifier itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }

    public Identifier itemId() {
        return itemId;
    }

    public int count() {
        return count;
    }


    public ItemStack recipeOutput() {
        Item item = BuiltInRegistries.ITEM.getValue(this.itemId);
        return item == null ? ItemStack.EMPTY : new ItemStack(item, this.count);
    }

    @Override
    public Codec<? extends ManipulatorCraftingResult> type() {
        return TRManipulatorRecipeResultTypes.ITEM_RESULT.get();
    }

}
