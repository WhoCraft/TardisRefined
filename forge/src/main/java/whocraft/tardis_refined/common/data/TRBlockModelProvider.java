package whocraft.tardis_refined.common.data;

import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.device.TerraformerBlock;
import whocraft.tardis_refined.registry.BlockRegistry;

import static whocraft.tardis_refined.registry.BlockRegistry.BLOCKS;

public class TRBlockModelProvider extends BlockStateProvider {

    public TRBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), TardisRefined.MODID, existingFileHelper);
    }

    public JsonObject emptyBlockState(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block).forAllStates(blockState -> ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TardisRefined.MODID, "block/terraformer"))).build());
        return builder.toJson();
    }



    public JsonObject terraformer(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block).forAllStates(blockState -> blockState.getValue(TerraformerBlock.ACTIVE) ?
                ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TardisRefined.MODID, "block/terraformer_on"))).build() :
                ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TardisRefined.MODID, "block/terraformer"))).build());

        return builder.toJson();
    }

    // Paul McGann is...
    public JsonObject threeDeeRotating(Block block, ResourceLocation location) {
        VariantBlockStateBuilder builder = getVariantBuilder(block).forAllStates(blockState -> ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(location)).rotationY((int) blockState.getValue(HorizontalDirectionalBlock.FACING).toYRot()).build());


        return builder.toJson();
    }

    public ResourceLocation getBlockResourceLocation(Block block) {
        return BLOCKS.getRegistry().getKey(block);
    }

    public ResourceLocation getBlockTextureResourceLocation(Block block) {
        ResourceLocation blockTex = BLOCKS.getRegistry().getKey(block);
        return new ResourceLocation(TardisRefined.MODID, "block/" + blockTex.getPath());
    }

    public JsonObject customLocation(Block block, ResourceLocation resourceLocation) {
        return getVariantBuilder(block).partialState().modelForState().modelFile(models().getExistingFile(resourceLocation)).addModel().toJson();
    }

    public JsonObject customLocation(Block block) {
        return getVariantBuilder(block).partialState().modelForState().modelFile(cubeAll(block)).addModel().toJson();
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceLocation leavesTexture = new ResourceLocation("tardis_refined:block/ars_leaves");
        ResourceLocation growthStoneTexture = new ResourceLocation("tardis_refined:block/growth_stone");

/*
        Blocks that are rendered in code
*/
        emptyBlockState(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get());
        emptyBlockState(BlockRegistry.BULK_HEAD_DOOR.get());
        emptyBlockState(BlockRegistry.GLOBAL_DOOR_BLOCK.get());
        emptyBlockState(BlockRegistry.GLOBAL_SHELL_BLOCK.get());
        emptyBlockState(BlockRegistry.ROOT_SHELL_DOOR.get());
        emptyBlockState(BlockRegistry.ROOT_PLANT_BLOCK.get());
        emptyBlockState(BlockRegistry.ROOT_SHELL_BLOCK.get());
        emptyBlockState(BlockRegistry.ARS_EGG.get());



        threeDeeRotating(BlockRegistry.LANDING_PAD.get(), new ResourceLocation(TardisRefined.MODID, "block/landing_pad"));
        threeDeeRotating(BlockRegistry.FLIGHT_DETECTOR.get(), new ResourceLocation(TardisRefined.MODID, "block/flight_detector"));

        terraformer(BlockRegistry.TERRAFORMER_BLOCK.get());

        /*
         **Basic Blocks**
         */
        customLocation(BlockRegistry.ARS_LEAVES.get());
        customLocation(BlockRegistry.AIR_LOCK_GENERATION_BLOCK.get());
        customLocation(BlockRegistry.FOOLS_STONE.get());

        customLocation(BlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get(), new ResourceLocation(TardisRefined.MODID, "block/astral_manipulator"));
        customLocation(BlockRegistry.ZEITON_FUSED_COPPER_BLOCK.get());
        customLocation(BlockRegistry.ZEITON_FUSED_IRON_BLOCK.get());
        customLocation(BlockRegistry.ZEITON_ORE.get());
        customLocation(BlockRegistry.ZEITON_ORE_DEEPSLATE.get());
        customLocation(BlockRegistry.ZEITON_BLOCK.get());


        /*
         **Fences**
         */
        fenceBlock(BlockRegistry.ARS_LEAVES_FENCE.get(), leavesTexture);

        /*
         **Slabs**
         */
        slabBlock(BlockRegistry.ARS_LEAVES_SLAB.get(), leavesTexture, leavesTexture, leavesTexture, leavesTexture);


        threeDeeRotating(BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get(), new ResourceLocation(TardisRefined.MODID, "block/console_configuration"));


    }



}