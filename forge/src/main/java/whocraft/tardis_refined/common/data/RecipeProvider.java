package whocraft.tardis_refined.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    public RecipeProvider(DataGenerator arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(arg.getPackOutput(), completableFuture);
    }


    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.TERRAFORMER_BLOCK.get()).pattern("ISI").pattern("GEG").pattern("IRI").define('I', Items.IRON_INGOT).define('S', Items.DAYLIGHT_DETECTOR).define('G', Items.GLASS_PANE).define('E', Blocks.EMERALD_BLOCK.asItem()).define('R', Blocks.REDSTONE_BLOCK.asItem()).unlockedBy("has_crafting_table", has(Blocks.EMERALD_BLOCK)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get()).pattern("IOI").pattern("IEI").pattern("IBI").define('I', Items.IRON_INGOT).define('O', Items.OBSERVER).define('E', Items.ENDER_EYE).define('B', Items.STONE_BUTTON).unlockedBy("has_crafting_table", has(Blocks.EMERALD_BLOCK)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.LANDING_PAD.get()).pattern("ITI").pattern("RAR").pattern("SSS").define('I', Items.IRON_INGOT).define('T', Items.IRON_TRAPDOOR).define('R', Items.REDSTONE).define('A', Items.TARGET).define('S', Items.STONE).unlockedBy("has_crafting_table", has(Blocks.TARGET)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.BULK_HEAD_DOOR.get()).pattern(" L ").pattern("PDP").pattern("RDR").define('L', Blocks.REDSTONE_LAMP).define('P', Blocks.PISTON).define('D', Blocks.IRON_DOOR).define('R', Blocks.REDSTONE_WIRE).unlockedBy("has_crafting_table", has(Blocks.TARGET)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PATTERN_MANIPULATOR.get()).pattern("RCL").pattern("EAE").pattern(" S ").define('S', Items.STICK).define('E', Items.REDSTONE).define('A', Items.IRON_INGOT).define('R', Items.RED_DYE).define('C', Items.GREEN_DYE).define('L', Items.LAPIS_LAZULI).unlockedBy("has_crafting_table", has(BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.DRILL.get()).pattern(" P ").pattern("PCP").pattern("IRI").define('P', Items.IRON_PICKAXE).define('R', Items.REDSTONE).define('I', Items.IRON_INGOT).define('C', Items.COBBLESTONE).unlockedBy("has_crafting_table", has(Items.REDSTONE)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.FLIGHT_DETECTOR.get()).pattern("G G").pattern("IDI").pattern("III").define('G', Items.GOLD_INGOT).define('I', Items.IRON_INGOT).define('D', Blocks.DAYLIGHT_DETECTOR).unlockedBy("has_dalight_detector", has(Blocks.DAYLIGHT_DETECTOR)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.KEY.get()).requires(Items.IRON_INGOT).requires(Items.TRIPWIRE_HOOK).unlockedBy("has_crafting_table", has(Blocks.TRIPWIRE_HOOK)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlockRegistry.GLOBAL_DOOR_BLOCK.get()).requires(Items.IRON_INGOT).requires(Items.TRIPWIRE_HOOK).requires(Items.IRON_DOOR).unlockedBy("has_crafting_table", has(Blocks.IRON_DOOR)).save(consumer);

    }
}
