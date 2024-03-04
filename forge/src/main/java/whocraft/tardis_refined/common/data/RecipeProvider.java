package whocraft.tardis_refined.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    public RecipeProvider(DataGenerator arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(arg.getPackOutput(), completableFuture);
    }


    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.BULK_HEAD_DOOR.get()).pattern(" L ").pattern("PDP").pattern("RDR").define('L', Blocks.REDSTONE_LAMP).define('P', Blocks.PISTON).define('D', Blocks.IRON_DOOR).define('R', Blocks.REDSTONE_WIRE).unlockedBy("has_crafting_table", has(Blocks.TARGET)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.SCREWDRIVER.get()).pattern(" A ").pattern("BR ").pattern(" L ").define('L', Items.LAPIS_LAZULI).define('B', Items.STONE_BUTTON).define('R', Items.BLAZE_ROD).define('A', Items.AMETHYST_SHARD).unlockedBy("has_crafting_table", has(Items.LAPIS_LAZULI)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get()).pattern("IAA").pattern("ILA").pattern("RII").define('L', Items.REDSTONE_LAMP).define('I', Items.IRON_INGOT).define('R', Items.REDSTONE_TORCH).define('A', Blocks.AMETHYST_BLOCK).unlockedBy("has_crafting_table", has(Items.REDSTONE_LAMP)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PATTERN_MANIPULATOR.get()).pattern("RCL").pattern("EAE").pattern(" S ").define('S', Items.STICK).define('E', Items.REDSTONE).define('A', Items.IRON_INGOT).define('R', Items.RED_DYE).define('C', Items.GREEN_DYE).define('L', Items.LAPIS_LAZULI).unlockedBy("has_crafting_table", has(BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.DRILL.get()).pattern(" P ").pattern("PCP").pattern("IRI").define('P', Items.IRON_PICKAXE).define('R', Items.REDSTONE).define('I', Items.IRON_INGOT).define('C', Items.COBBLESTONE).unlockedBy("has_crafting_table", has(Items.REDSTONE)).save(consumer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.KEY.get()).requires(Items.IRON_INGOT).requires(Items.TRIPWIRE_HOOK).unlockedBy("has_crafting_table", has(Blocks.TRIPWIRE_HOOK)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlockRegistry.GLOBAL_DOOR_BLOCK.get()).requires(Items.IRON_INGOT).requires(Items.TRIPWIRE_HOOK).requires(Items.IRON_DOOR).unlockedBy("has_crafting_table", has(Blocks.IRON_DOOR)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, BlockRegistry.ZEITON_FUSED_COPPER_BLOCK.get()).requires(ItemRegistry.ZEITON_INGOT.get()).requires(Items.COPPER_BLOCK).unlockedBy("has_crafting_table", has(ItemRegistry.ZEITON_INGOT.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, BlockRegistry.ZEITON_FUSED_IRON_BLOCK.get()).requires(ItemRegistry.ZEITON_INGOT.get()).requires(Items.IRON_BLOCK).unlockedBy("has_crafting_table", has(ItemRegistry.ZEITON_INGOT.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ZEITON_BLOCK.get()).pattern("ZZZ").pattern("ZZZ").pattern("ZZZ").define('Z',  ItemRegistry.ZEITON_INGOT.get()).unlockedBy("has_crafting_table", has(ItemRegistry.ZEITON_INGOT.get())).save(consumer);

        // Smelting
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.RAW_ZEITON.get()), RecipeCategory.MISC, ItemRegistry.ZEITON_INGOT.get(), 0.7F, 300).unlockedBy("has_any_zeiton", has(ItemRegistry.RAW_ZEITON.get())).save(consumer, new ResourceLocation(TardisRefined.MODID, "smelt_zeiton"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemRegistry.RAW_ZEITON.get()), RecipeCategory.MISC, ItemRegistry.ZEITON_INGOT.get(), 0.8F, 150).unlockedBy("has_any_zeiton", has(ItemRegistry.RAW_ZEITON.get())).save(consumer, new ResourceLocation(TardisRefined.MODID, "blast_zeiton"));
    }
}
