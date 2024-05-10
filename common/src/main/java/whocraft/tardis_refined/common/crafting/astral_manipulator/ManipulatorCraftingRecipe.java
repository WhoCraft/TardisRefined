package whocraft.tardis_refined.common.crafting.astral_manipulator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.TRCraftingRecipeSerializers;
import whocraft.tardis_refined.registry.TRCraftingRecipeTypes;

import java.util.*;


/**
 * Main recipe object for the Astral Manipulator.
 * **/
public class ManipulatorCraftingRecipe implements CraftingRecipe {
    // List of ingredient blocks for the recipe to work.
    private List<ManipulatorCraftingIngredient> ingredients;

    private ManipulatorCraftingResult result;

    private ResourceLocation id;


    public static final Codec<ManipulatorCraftingRecipe> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                    ManipulatorCraftingIngredient.CODEC.listOf().fieldOf("ingredients").forGetter(ManipulatorCraftingRecipe::ingredients),
                    ManipulatorCraftingResult.RESULT_CODEC.fieldOf("result").forGetter(ManipulatorCraftingRecipe::result)
            ).apply(builder, ManipulatorCraftingRecipe::new)
    );

    public ManipulatorCraftingRecipe(List<ManipulatorCraftingIngredient> itemList, ManipulatorCraftingResult recipeOutput) {
        this.ingredients = itemList;
        this.result = recipeOutput;
    }

    public ManipulatorCraftingRecipe(List<ManipulatorCraftingIngredient> ingredients, ItemStack recipeOutput) {
        this(ingredients, new ManipulatorItemResult(recipeOutput));
    }

    public ManipulatorCraftingRecipe(List<ManipulatorCraftingIngredient> ingredients, BlockState recipeOutput) {
        this(ingredients, new ManipulatorBlockResult(recipeOutput));
    }

    public ResourceLocation getId() {
        return this.id;
    }

    /** Sets the id for this recipe. ALWAYS call this when making a new instance of this, as the registry name is null by default*/
    public ManipulatorCraftingRecipe setRegistryId(ResourceLocation id) {
        this.id = id;
        return this;
    }

    /** Helper to get all recipes without needing to use a Container. In 1.21+ recipes will be decoupled from Containers but until them, we will need this workaround*/
    public static List<ManipulatorCraftingRecipe> getAllRecipes(Level level){
        List<RecipeHolder<ManipulatorCraftingRecipe>> recipeHolders = level.getRecipeManager().getAllRecipesFor(TRCraftingRecipeTypes.ASTRAL_MANIPULATOR_RECIPE.get());
        List<ManipulatorCraftingRecipe> recipeList = new ArrayList<>();
        recipeHolders.forEach(recipeHolder -> recipeList.add(recipeHolder.value().setRegistryId(recipeHolder.id()))); //Make sure to set registry id
        return recipeList;
    }



    /**
     * Compares a ManipulatorCraftingRecipe to another by sorting by size, then registered block entries
     * @param comparedItemList The items of the recipe to compare to.
     * **/
    public boolean hasSameItems(List<ManipulatorCraftingIngredient> comparedItemList) {

        if (ingredients.size() != comparedItemList.size()) {
            return false;
        }

        // Sort the items so the list comparison is better.
        List<ManipulatorCraftingIngredient> sortedIngredients = new ArrayList<ManipulatorCraftingIngredient>(this.ingredients);
        sortedIngredients.sort(Comparator.comparing(a -> a.relativeBlockPos()));

        List<ManipulatorCraftingIngredient> sortedCompareList = new ArrayList<ManipulatorCraftingIngredient>(comparedItemList);
        sortedCompareList.sort(Comparator.comparing(a -> a.relativeBlockPos()));

        for (int i = 0; i < sortedIngredients.size(); i++) {
            if (!sortedIngredients.get(i).IsSameAs(sortedCompareList.get(i))) {
                return false;
            }
        }

        return true;
    }

    public ManipulatorCraftingResult result(){
        return this.result;
    }


    public List<ManipulatorCraftingIngredient> ingredients(){
        return this.ingredients;
    }

    /** Default vanilla data, we will add dummy values as we won't be using them*/
    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }
    /** Required to allow vanilla to parse the recipe*/
    @Override
    public RecipeSerializer<?> getSerializer() {
        return TRCraftingRecipeSerializers.ASTRAL_MANIPULATOR.get();
    }
    /** Required to allow vanilla to parse the recipe*/
    @Override
    public RecipeType<?> getType() {
        return TRCraftingRecipeTypes.ASTRAL_MANIPULATOR_RECIPE.get();
    }


}
