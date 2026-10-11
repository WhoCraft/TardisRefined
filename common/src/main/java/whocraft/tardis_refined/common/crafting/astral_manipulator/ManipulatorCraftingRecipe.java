package whocraft.tardis_refined.common.crafting.astral_manipulator;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.TRCraftingRecipeSerializers;
import whocraft.tardis_refined.registry.TRCraftingRecipeTypes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Main recipe object for the Astral Manipulator.
 **/
public class ManipulatorCraftingRecipe implements CraftingRecipe {
    public static final MapCodec<ManipulatorCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                    ManipulatorCraftingIngredient.CODEC.listOf().fieldOf("ingredients").forGetter(ManipulatorCraftingRecipe::ingredients),
                    ManipulatorCraftingResult.RESULT_CODEC.fieldOf("result").forGetter(ManipulatorCraftingRecipe::result)
            ).apply(builder, ManipulatorCraftingRecipe::new)
    );
    // List of ingredient blocks for the recipe to work.
    private List<ManipulatorCraftingIngredient> ingredients;
    private ManipulatorCraftingResult result;
    private Identifier id;

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

    /**
     * Helper to get all recipes without needing to use a Container. In 1.21+ recipes will be decoupled from Containers but until them, we will need this workaround
     */
    public static List<ManipulatorCraftingRecipe> getAllRecipes(Level level) {
        if (level == null) {
            return List.of();
        }

        return ((net.minecraft.world.item.crafting.RecipeManager) level.recipeAccess()).getRecipes().stream()
                .map(holder -> holder.value())
                .filter(ManipulatorCraftingRecipe.class::isInstance)
                .map(ManipulatorCraftingRecipe.class::cast)
                .toList();
    }

    /**
     * Get the unique id for this recipe. Needed for debugging purposes, and also to make sure we keep track of the recipes in some manner
     */
    public Identifier getId() {
        return this.id;
    }

    /**
     * Sets the id for this recipe. ALWAYS call this when making a new instance of this, as the registry name is null by default
     */
    public ManipulatorCraftingRecipe setRegistryId(Identifier id) {
        this.id = id;
        return this;
    }

    /**
     * Compares a ManipulatorCraftingRecipe to another by sorting by size, then registered block entries
     *
     * @param comparedItemList The items of the recipe to compare to.
     **/
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

    /**
     * Provides the result of the recipe. This can be either a blockstate or itemstack depending on the result type
     */
    public ManipulatorCraftingResult result() {
        return this.result;
    }

    /**
     * Provides the list of ingredients for the recipe, in the form of a list of blockstate to block position offsets
     */
    public List<ManipulatorCraftingIngredient> ingredients() {
        return this.ingredients;
    }

    /**
     * Default vanilla data, we will add dummy values as we won't be using them
     */
    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    public ItemStack getResultItem() {
        if (result.type() instanceof ManipulatorItemResult manipulatorItemResult) {
            return manipulatorItemResult.recipeOutput();
        }

        if (result.type() instanceof ManipulatorBlockResult manipulatorBlockResult) {
            return new ItemStack(manipulatorBlockResult.recipeOutput().getBlock().asItem());
        }

        return ItemStack.EMPTY;
    }

    /**
     * Required to allow vanilla to parse the recipe during datapack parsing
     */
    @Override
    public RecipeSerializer<? extends CraftingRecipe> getSerializer() {
        return TRCraftingRecipeSerializers.ASTRAL_MANIPULATOR.get();
    }

    /**
     * Required to allow vanilla to parse the recipe during datapack parsing
     */
    @Override
    @SuppressWarnings("unchecked")
    public RecipeType<CraftingRecipe> getType() {
        return (RecipeType<CraftingRecipe>) (RecipeType<?>) TRCraftingRecipeTypes.ASTRAL_MANIPULATOR_RECIPE.get();
    }


}
