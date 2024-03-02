package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Comparator;
import java.util.List;

public class ManipulatorCraftingRecipe {
    public List<ManipulatorCraftingRecipeItem> itemList;
    public Item recipeOutput;

    public ManipulatorCraftingRecipe(List<ManipulatorCraftingRecipeItem> itemList, Item recipeOutput) {
        this.itemList = itemList;
        this.recipeOutput = recipeOutput;
    }

    public boolean isSameAs(ManipulatorCraftingRecipe manipulatorCraftingRecipe) {

        if (recipeOutput != manipulatorCraftingRecipe.recipeOutput) {
            return false;
        }


        return hasSameItems(manipulatorCraftingRecipe.itemList);
    }

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
