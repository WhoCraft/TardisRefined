package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Comparator;
import java.util.List;


/**
 * Entry item for the Astral Manipulator.
 * **/
public class ManipulatorCraftingRecipe {
    // List of ingredient blocks for the recipe to work.
    public List<ManipulatorCraftingRecipeItem> itemList;
    // Output to be summoned when the recipe is confirmed.
    public Item recipeOutput;

    public ManipulatorCraftingRecipe(List<ManipulatorCraftingRecipeItem> itemList, Item recipeOutput) {
        this.itemList = itemList;
        this.recipeOutput = recipeOutput;
    }


    /**
     * Compares a ManipulatorCraftingRecipe to another by sorting by size, then registered block entries
     * @param comparedItemList The items of the recipe to compare to.
     * **/
    public boolean hasSameItems(List<ManipulatorCraftingRecipeItem> comparedItemList) {

        if (itemList.size() != comparedItemList.size()) {
            return false;
        }

        // Sort the items so the list comparison is better.
        this.itemList.sort(Comparator.comparing(a -> a.relativeBlockPos));
        comparedItemList.sort(Comparator.comparing(a -> a.relativeBlockPos));

        for (int i = 0; i < this.itemList.size(); i++) {
            if (!this.itemList.get(i).IsSameAs(comparedItemList.get(i))) {
                return false;
            }
        }

        return true;
    }
}
